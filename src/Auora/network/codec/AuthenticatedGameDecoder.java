package Auora.network.codec;

import Auora.io.InStream;
import Auora.net.Packets;
import Auora.net.codec.ConnectionHandler;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;


/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class AuthenticatedGameDecoder extends FrameDecoder {
	
	/**
	 * 
	 * @param handler
	 */
	public AuthenticatedGameDecoder(ConnectionHandler handler) {
		this.handler = handler;
		handler.getChannel().getPipeline().getContext("handler").setAttachment(handler);
	}
	
	/**
	 * Handleer
	 */
	private ConnectionHandler handler;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel chan, ChannelBuffer buffer) throws Exception {
		buffer.markReaderIndex();
		int avail = buffer.readableBytes();
		if (avail > 5000) {
			chan.close();
			return null;
		}
		byte[] b = new byte[avail];
		buffer.readBytes(b);
		InStream in = new InStream(b);
		ConnectionHandler hand = handler;
		if(hand == null) {
			//System.out.println("l0lwat");
			return null;
		}
		//synchronized(LogicService.getInstance().thread) {
			Packets.run(hand, in);
		//}
		return null;
	}

}
