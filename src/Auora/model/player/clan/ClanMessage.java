package Auora.model.player.clan;

import Auora.io.OutStream;
import Auora.model.player.Player;
import Auora.util.Misc;

import java.util.Random;


/**
 * @author crezzy
 */
public class ClanMessage {

    public static final Random r = new Random();
    public static int messageCounter = 1;
    public static int id = 0;

    public static void sendClanChatMessage(Player from, Player pl, String roomName, String user, String message) {
        int messageCounter = getNextUniqueId();
        OutStream bldr = new OutStream();
        bldr.writePacketVarByte(64);
        bldr.writeByte(0);
        bldr.writeRS2String(Misc.formatPlayerNameForDisplay((from.getUsername())));
        bldr.writeLong(Misc.stringToLong(roomName));
        bldr.writeShort(r.nextInt());
        byte[] bytes = new byte[256];
        bytes[0] = (byte) message.length();
        int len = 1 + Misc.huffmanCompress(message, bytes, 1);
        bldr.writeMediumInt(messageCounter);
        bldr.writeByte(from.getClientCrownId());
        bldr.writeBytes(bytes, 0, len);
        bldr.endPacketVarByte();
        if (pl != null)
            pl.getConnection().write(bldr);
        else
            from.getConnection().write(bldr);
    }

    public static int getNextUniqueId() {
        if (messageCounter >= 16000000) {
            messageCounter = 0;
        }
        return messageCounter++;
    }
}
