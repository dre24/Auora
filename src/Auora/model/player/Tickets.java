package Auora.model.player;

import Auora.model.World;
import Auora.net.Packets;
import Auora.util.Misc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tickets {

    public static List<Player> needingHelp = new ArrayList<Player>();

    public static void check(Player p) {
        p.getFrames().sendInterface(275);
        int number = 0;
        for (int i = 0; i < 316; i++) {
            p.getFrames().sendString("", 275, i);
        }
        for (Player p5 : World.getPlayers()) {
            if (p5 == null)
                continue;
            if (!p5.needshelp)
                continue;
            number++;
            p.getFrames().sendString("<col=ff0000>" + Misc.formatPlayerNameForDisplay(p5.getUsername()) + "</col> requested help at <col=ff0000>" + p.helpRequestedAt + "</col>.", 275, (16 + number));

        }
        p.getFrames().sendString("Currently <col=ff0000>" + number + "</col> tickets open.", 275, 2);
    }

    public static void request(Player p) {
        if (p == null)
            return;
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.helpwait > 0) {
            p.getFrames().sendChatMessage(0, "Please wait another <col=ff0000>" + p.helpwait / 2 + "</col> seconds to use this again!");
            return;
        }
        if (p.getSkills().playerDead)
            return;
        p.getFrames().sendChatMessage(0, "<img=3>You have submitted a ticket.");

        Date d1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");
        String formattedDate = df.format(d1);

        for (Player e : World.getPlayers()) {
            if (e == null)
                continue;
            if (e.getStaffRights() != StaffRights.PLAYER) {
                p.needshelp = true;
                p.helpwait = 90;
                needingHelp.add(p);
                p.helpRequestedAt = formattedDate;
                e.getFrames().sendChatMessage(0, "<col=ff0000>" + Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " ")) + "</col> has submitted a ticket, type <col=ff0000>::answerticket [name]</col> to assist them!");
            }
        }
    }

    public static void answer(Player p, String name) {
        if (p == null)
            return;
        if (p.getSkills().playerDead)
            return;
        if (!p.getCombat().isSafe(p)) {
            p.sm("You cannot answer tickets whilst in the wilderness.");
            return;
        }
        Player d = Packets.getPlayerByName(name);
        String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        if (d == null) {
            p.getFrames().sendChatMessage(0, "That player is offline.");
            return;
        }
        if (!d.needshelp) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>This player currently does not need help.");
        }
        if (!d.getCombat().isSafe(d)) {
            p.sm("You cannot accept " + d.getUsername() + " as they're in the wilderness.");
            return;
        }
        if (d.needshelp) {
            for (Player f : World.getPlayers()) {
                if (f == null)
                    continue;
                if (f.getStaffRights() != StaffRights.PLAYER) {
                    f.getFrames().sendChatMessage(0, "The assist request by <col=ff0000>" + d.getDisplayName() + "</col> has been answered by <col=ff0000>" + p.getDisplayName() + "</col>");
                }
            }
            p.getFrames().sendChatMessage(0, "You have just teleported " + d.getDisplayName() + " to you for assistance.");
            d.getFrames().sendChatMessage(0, "You have been teleported to " + myName + " for assistance.");
            int place = Misc.random(1);
            if (place == 0) {
                d.getMask().getRegion().teleport(2066, 4377, 1, 0);
                p.getMask().getRegion().teleport(2066, 4377, 1, 0);
            } else {
                d.getMask().getRegion().teleport(2064, 4379, 2, 0);
                p.getMask().getRegion().teleport(2064, 4379, 2, 0);
            }

            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "How may i assist you?"));
            p.getMask().setChatUpdate(true);
            d.needshelp = false;
            needingHelp.remove(d);

        }
    }


}
