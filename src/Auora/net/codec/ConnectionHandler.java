package Auora.net.codec;

import Auora.io.OutStream;
import Auora.model.player.Player;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import java.util.LinkedList;
import java.util.Queue;

public class ConnectionHandler {

    public ConnectionHandler(Channel channel) {
        this.channel = channel;
    }
    
    public transient Queue<OutStream> packetQueue = new LinkedList<OutStream>();
    
    public void processPacketQueue() {
    	if(packetQueue.isEmpty())
    		return;
    	for(OutStream packet : packetQueue) {
    		writeInstant(packet);
    	}
    	packetQueue.clear();
    }
    
    public transient boolean disconnected = false;
    
    public void disconnect(Boolean value) {
    	this.disconnected = value;
    }

    //private transient InStream inStream;
    private transient Channel channel;
    private transient byte ConnectionStage;
    private transient byte NameHash;
    private transient long SessionKey;
    private transient Player player;
	private transient byte displayMode = -1;
	private transient String name = "";
	public long lastResponce;

    public Channel getChannel() {
        return channel;
    }

    public void write(OutStream outStream) {
    	//packetQueue.add(outStream);
    	writeInstant(outStream);
    }
    
    public ChannelFuture writeInstant(OutStream outStream) {
        if (channel != null && outStream.offset() > 0 && channel.isConnected()) {
            return channel.write(outStream);
        }
        return null;
    }
    
    
    public void setConnectionStage(byte connectionStage) {
        ConnectionStage = connectionStage;
    }

    public byte getConnectionStage() {
        return ConnectionStage;
    }

    /*public InStream getInStream() {
        return inStream;
    }*/

    public void setNameHash(byte nameHash) {
        NameHash = nameHash;
    }

    public byte getNameHash() {
        return NameHash;
    }

    public void setSessionKey(long sessionKey) {
        SessionKey = sessionKey;
    }

    public long getSessionKey() {
        return SessionKey;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isDisconnected() {
        return !getChannel().isConnected();
    }

	public int getDisplayMode() {
		return displayMode;
	}
	
	public void setDisplayMode(int mode) {
		this.displayMode = (byte) mode;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
