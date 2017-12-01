package Auora.network;

import Auora.network.codec.DefaultProtocolEncoder;
import Auora.network.codec.handshake.DefaultProtocolDecoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;


/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class LegacyPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = new DefaultChannelPipeline();
		pipeline.addLast("decoder", new DefaultProtocolDecoder());
		pipeline.addLast("encoder", new DefaultProtocolEncoder());
		pipeline.addLast("handler", new NetworkEventHandler());
		return pipeline;
	}

}
