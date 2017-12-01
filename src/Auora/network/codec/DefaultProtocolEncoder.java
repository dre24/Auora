package Auora.network.codec;

import Auora.io.OutStream;
import Auora.network.packet.Packet;
import Auora.network.packet.Packet.PacketType;
import Auora.network.packet.PacketBuilder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;


/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class DefaultProtocolEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel chan, Object msg) throws Exception {
		if(msg instanceof OutStream) {
			OutStream oldMsg = (OutStream) msg;
			ChannelBuffer buffer = ChannelBuffers.copiedBuffer(oldMsg.buffer(), 0, oldMsg.offset());
			return buffer;
		}
		Packet packet = null;
		if(msg instanceof PacketBuilder) {
			packet = ((PacketBuilder) msg).build();
		} else {
			packet = (Packet) msg;
		}
		if(!packet.isRaw()) {
			int packetLength = 1 + packet.getLength() + packet.getType().getSize();
			ChannelBuffer response = ChannelBuffers.buffer(packetLength);
			response.writeByte((byte) packet.getOpcode());
			if(packet.getType() == PacketType.VAR_BYTE) {
				response.writeByte((byte) packet.getLength());
			} else if(packet.getType() == PacketType.VAR_SHORT) {
				response.writeShort((short) packet.getLength());
			}
			response.writeBytes(packet.getBuffer());
			return response;
		}
		return packet.getBuffer();
	}

}