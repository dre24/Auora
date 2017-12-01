package Auora.network.codec.login;

import Auora.net.codec.ConnectionHandler;
import Auora.service.login.LoginService;
import Auora.service.login.LoginTask;
import Auora.util.BufferUtils;
import Auora.util.Misc;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;


/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class LoginDecoder extends ReplayingDecoder<LoginStage> {

	/**
	 * 
	 */
	public LoginDecoder() {
		checkpoint(LoginStage.PRE_STATE);
	}
	
	/**
	 * ConnectionHandler
	 */
	private ConnectionHandler handler;
	
	/**
	 * Readable bytes
	 * @param buffer
	 * @return
	 */
	public int readableBytes(ChannelBuffer buffer) {
		return buffer.writerIndex() - buffer.readerIndex();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer in, LoginStage ls) throws Exception {
		if(handler == null) {
			handler = new ConnectionHandler(channel);
			ctx.setAttachment(handler);
			channel.getPipeline().getContext("handler").setAttachment(handler);
		}
		switch(ls) {
		case PRE_STATE:
			int bytesLeft = readableBytes(in);
			if(bytesLeft >= 3) {
				int type = in.readUnsignedByte();
				int size = in.readUnsignedShort();
				if (size != readableBytes(in)) {
					throw new Exception("Mismatched login packet size.");
				}
				int version = in.readInt();
				if(version != 614) {
					throw new Exception("Incorrect revision read");
				}
				if(type == 16 || type == 18) {
					checkpoint(LoginStage.LOGIN_STAGE);
				} 
			}
			break;
		case LOGIN_STAGE:
			in.readUnsignedByte();
			int mode = in.readUnsignedByte();
			if(handler.getDisplayMode() == -1) {
				handler.setDisplayMode(mode);
			}
			in.readUnsignedShort();
			in.readUnsignedShort();
			in.readUnsignedByte();
			in.skipBytes(24);
			BufferUtils.readRS2String(in);
			in.readInt();
			int size = in.readUnsignedByte();
			in.skipBytes(size);
			in.skipBytes(6 + (33 * 4) + 8 + 2 + 14);
			if(in.readUnsignedByte() != 10) {
				throw new Exception("Invalid RSA header.");
			}
			in.readLong();
			in.readLong();
			long l = in.readLong();
			//String name = BufferUtils.readRS2String(in);
			String name = Misc.formatPlayerNameForProtocol(Misc.longToString(l));
			String password = BufferUtils.readRS2String(in);

			boolean wrongSecret = false;

			if(in.readLong() != 0L) {
				wrongSecret = true;
			}

			String secret = BufferUtils.readRS2String(in);
			if(!secret.equals("s4353s34")) {
				wrongSecret = true;
			}

			in.readLong();

			String serial = BufferUtils.readRS2String(in);
			String version = BufferUtils.readRS2String(in);

			if(serial.length() < 200 && !serial.equalsIgnoreCase("invalid_serial")) {
				System.out.println("Login attempt from a serial shorter than 200 characters on account "+name);
				wrongSecret = true;
			}

			if(!verifySecret(serial) && !serial.equalsIgnoreCase("invalid_serial")) {
				System.out.println("Wrong secret identified inside serial for account "+name);
				wrongSecret = true;
			}

			LoginTask task = new LoginTask(new String[] { name, password, serial, version}, wrongSecret, channel,ctx, handler);
			LoginService.getSingleton().submitLogin(task);
			break;
		}
		return null;
	}

	public static boolean verifySecret(String serial) {
		for(String secret : SECRETS) {
			if(serial.contains(secret.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public static String[] SECRETS = new String[] {
			"a0e79d8c5ce9da254103e8995a9d0bc6b5a12b",
			"536fd3d58fccf5342ac64066a8839d1ec7cc22a9",
			"745fc5575b6698cac655536fd3d58",
			"47ce2ffac27b46d32e2894c5c4e5e1399ef191",
			"bf0c305e38342876f2f3b0c777f9ed0d9b2f90aff",
			"da7c79e0dbfad7c685ad66faabdb2563aa44d0a60ab0b89fb8386b",
			"2854d99a7c5debb470d023afe5fcda7c79e0db",
			"5639a5d5190941232673b0b61447792a77ca",
			"792a77caae0b98eeefaf22c55b7a19670ff46dd0d0161",
			"d8396148a9732cdfcb4b792dd342cbf15639a",
	};
}