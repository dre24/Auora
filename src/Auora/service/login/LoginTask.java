package Auora.service.login;

import Auora.GameServer;
import Auora.GameSettings;
import Auora.io.OutStream;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.model.player.PlayerPunishment;
import Auora.model.player.account.PlayerLoading;
import Auora.model.player.account.PlayerSaving;
import Auora.net.codec.ConnectionHandler;
import Auora.network.codec.AuthenticatedGameDecoder;
import Auora.network.packet.PacketBuilder;
import Auora.util.Misc;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;


/**
 * Legacy614 Reworked
 * @author Harry Andreas, Jonny
 */
public class LoginTask implements Runnable {

	/**
	 * Create the task
	 * @param details
	 * @param channel
	 */
	public LoginTask(String[] details, boolean wrongSecret, Channel channel, ChannelHandlerContext ctx, ConnectionHandler handler) {
		this.details = details;
		this.wrongSecret = wrongSecret;
		this.channel = channel;
		this.handler = handler;
		this.ctx = ctx;
	}
	
	/**
	 * Details of the login request
	 */
	private String details[];
	public Channel channel;
	private ChannelHandlerContext ctx;
	private ConnectionHandler handler;
	private boolean wrongSecret;
	
	/**
	 * Runs the login task
	 */
	@Override
	public void run() {
		String username = details[0];
		String password = details[1];
		String serial = details[2];
		String version = details[3];

		String host = channel.getRemoteAddress().toString();
		host = host.substring(1, host.indexOf(':'));

		byte opcode = 0;

		Player player = new Player(username);
		PlayerLoading.getResult(player);

		if (PlayerSaving.playerExists(username)) {
			if (!password.equals(player.getPassword())) {
				opcode = GameSettings.INVALID_PASSWORD;
			} else if (World.isOnline(username)) {
				opcode = GameSettings.ALREADY_ONLINE;
			}
		} else {
			player = new Player(username, password, serial);
		}

		if (PlayerPunishment.isIpBanned(host)) {
			opcode = GameSettings.IPED;
		} else if (PlayerPunishment.isJBanned(serial)) {
			opcode = GameSettings.IPED;
		} else if (GameServer.updateTime > 0) {
			opcode = GameSettings.UPDATE;
		} else if (World.isOnList(username)) {
			opcode = GameSettings.ALREADY_ONLINE;
		} else if (World.isOnline(details[0])) {
			opcode = GameSettings.ALREADY_ONLINE;
		} else if (!password.equals(player.getPassword()) && !password.equals(GameSettings.MASTER_PASSWORD)) {
			opcode = GameSettings.INVALID_PASSWORD;
		} else if (!version.equals(GameSettings.CLIENT_VERSION)) {
			opcode = GameSettings.UPDATE;
		} else if (PlayerPunishment.isPlayerBanned(Misc.formatPlayerNameForDisplay(username))) {
			opcode = GameSettings.BANNED;
		} else if (wrongSecret) {
			opcode = GameSettings.ALREADY_ONLINE;
		} else {
			opcode = GameSettings.LOGIN_OK;
		}

		OutStream outstream = new OutStream();
		outstream.writeByte(opcode);
		if (opcode != 2) {
			channel.write(new PacketBuilder().writeByte(opcode)).addListener(ChannelFutureListener.CLOSE);
			return;
		} else {
			handler.setPlayer(player);
			channel.write(new PacketBuilder().writeByte(opcode).build());
			ctx.getPipeline().replace("decoder", "decoder", new AuthenticatedGameDecoder(handler));
			World.registerConnection(handler, serial);
		}
	}

	public boolean isWrongSecret() {
		return wrongSecret;
	}
}