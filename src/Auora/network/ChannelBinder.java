package Auora.network;

import Auora.util.LegacyThreadFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class ChannelBinder {
	
	/**
	 * The port to bind
	 */
	private int port = 43594;
	
	/**
	 * The ServerBootstrap instance
	 */
	private ServerBootstrap bootstrap;
	
	/**
	 * The ExecutorService used for selectors
	 */
	private ExecutorService service = Executors.newCachedThreadPool(new LegacyThreadFactory("ServerBootstrapWorker"));
	
	/**
	 * Construct the object 
	 * @param port The port to bind
	 */
	public ChannelBinder(int port){
		this.port = port;
	}
	
	/**
	 * Binds the port
	 */
	public void bind() {
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(service, service));
		bootstrap.setPipelineFactory(new LegacyPipelineFactory());
		bootstrap.setOption("backlog", 100);
        bootstrap.setOption("child.connectTimeoutMillis", 10000);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive",false);
        bootstrap.bind(new InetSocketAddress("0.0.0.0", port));
	}

}