package Auora.network;

import Auora.net.codec.ConnectionHandler;
import Auora.network.codec.js5.CacheFileRequestProtocolDecoder;
import Auora.network.codec.login.LoginDecoder;
import org.jboss.netty.channel.*;


/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class NetworkEventHandler extends SimpleChannelHandler {
	
	 @Override
	    public final void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
	//	 System.out.println("Open");
	    }

	    @Override
	    public final void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
			ConnectionHandler handler = (ConnectionHandler)ctx.getAttachment();
			handler.disconnect(true);
	    }

	    @Override
	    public final void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent ee)
	            throws Exception {
	    	//ee.getCause().printStackTrace();
	    }

	    @Override
	    public final void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
	    	if(ctx.getPipeline().get(CacheFileRequestProtocolDecoder.class) != null) {
	    		e.getChannel().write(e.getMessage());
	    	}
	    	if(ctx.getPipeline().get(LoginDecoder.class) != null) {
	    		e.getChannel().write(e.getMessage());
	    	}
	    }

}