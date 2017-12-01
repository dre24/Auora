package Auora.network.codec.handshake;

import Auora.network.codec.js5.CacheFileRequestProtocolDecoder;
import Auora.network.codec.login.LoginDecoder;
import Auora.network.packet.PacketBuilder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import java.security.SecureRandom;


/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class DefaultProtocolDecoder extends FrameDecoder {
	
	/**
	 * Revision
	 */
	private final static int REVISION = 614;

	/**
	 * Decodes the packet
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if(ctx.getPipeline().get(DefaultProtocolDecoder.class) != null) {
			ctx.getPipeline().remove(this);
		}
		int protocolRequest = buffer.readByte() & 0xFF;
		PacketBuilder response = new PacketBuilder();
		switch(protocolRequest) {
		case 15:
			int version = buffer.readInt();
			if(version != REVISION) {
				response.writeByte(6);
			} else {
				response.writeByte(0);
				ctx.getPipeline().addBefore("encoder", "decoder", new CacheFileRequestProtocolDecoder());
			}
			break;
		case 14:
			buffer.readByte();
			response.writeByte((byte)0);
			response.writeLong(new SecureRandom().nextLong());
			ctx.getPipeline().addBefore("encoder", "decoder", new LoginDecoder());
			break;
		default:
			channel.close();
			break;
		}
		return response.build();
	}
}
