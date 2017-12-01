package Auora.model.player.clan;

import Auora.io.OutStream;
import Auora.model.player.Player;
import Auora.util.Misc;

/**
 * @author crezzy 20%
 */
public class ClanPacket {

    public static void sendClanList(Player p, Clan clan) {
        OutStream bldr = new OutStream();
        bldr.writePacketVarShort(23);
        if (clan != null) {
            bldr.writeString(Misc.formatPlayerNameForDisplay(clan.getOwner()));
            bldr.writeByte(0);
            bldr.writeLong(Misc.stringToLong(clan.getName()));
            bldr.writeByte(clan.getKickReq());
            bldr.writeByte(clan.getMembers().size());
            for (Player pl : clan.getMembers()) {
                bldr.writeString(Misc.formatPlayerNameForDisplay(pl.getUsername()));
                bldr.writeShort(1);
                bldr.writeByte(0);
                bldr.writeByte(clan.getRank(pl));
                bldr.writeString("Auora");
            }
        }
        bldr.endPacketVarShort();
        p.getConnection().write(bldr);
    }
}
