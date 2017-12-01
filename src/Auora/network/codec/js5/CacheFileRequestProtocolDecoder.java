package Auora.network.codec.js5;

import Auora.network.packet.Packet;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class CacheFileRequestProtocolDecoder extends FrameDecoder {

	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel chan, ChannelBuffer buff) throws Exception {
		if(buff.readableBytes() >= 4) {
			int priority = buff.readByte() & 0xFF;
			int container = buff.readByte() & 0xFF;
			int file = buff.readShort() & 0xFFFF;
			switch(priority) {
				case 0:
				case 1:
					passToWorker(container, file, priority, chan);
					break;
			}
		}
		return null;
	}

	/**
	 * Passes the update server to the JS5 worker
	 * @param container
	 * @param file
	 * @param priority
	 * @param channel
	 * @author 'Mystic Flow - Demenethium
	 */
	public void passToWorker(final int container, final int file, final int priority, final Channel channel) {

		Packet m = dementhium.cache.Cache.INSTANCE.generateFile(container, file, priority);
		if(m != null) {
			channel.write(m);
		}

	}

}