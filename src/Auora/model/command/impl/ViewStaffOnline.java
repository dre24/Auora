package Auora.model.command.impl;

import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.player.ChatMessage;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.util.Misc;

/**
 * @author Jonny
 */
public class ViewStaffOnline extends Command {

    public ViewStaffOnline(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        int number = 0;
        for (int i = 0; i < 316; i++) {
            p.getFrames().sendString("", 275, i);
        }

        for (Player other_players : World.getPlayers()) {
            if (other_players == null)
                continue;
            if (other_players.getStaffRights() == StaffRights.PLAYER)
                continue;
            number++;
            String name = Misc.formatPlayerNameForDisplay(other_players.getUsername().replaceAll("_", " "));
            if (other_players.getStaffRights() == StaffRights.PLAYER) {
                p.getFrames().sendString(other_players.getDonatorRights().getColor() + other_players.getDonatorRights().getShad() + name + " - " + "<col=289C39>Safe Kills: <col=289C39>[" + other_players.safeKills + "]" + "<col=289C39> Unsafe Kills: <col=289C39>[" + other_players.dangerousKills + "]" + "<col=289C39> Deaths: <col=289C39>[" + other_players.deaths + "]", 275, (16 + number));
            } else {
                p.getFrames().sendString("<img=" + other_players.getStaffRights().getCrownId() + ">" + other_players.getStaffRights().getColor() + other_players.getStaffRights().getShad() + name + " - " + "<col=289C39>Kills: <col=289C39>[" + other_players.safeKills + "]" + "<col=289C39> Unsafe Kills: <col=289C39>[" + other_players.dangerousKills + "]" + "<col=289C39> Deaths: <col=289C39>[" + other_players.deaths + "]", 275, (16 + number));
            }
        }
        p.getFrames().sendString("<u=000080>Staff Online</u>", 275, 14);
        p.getFrames().sendString("Staff Online: " + number, 275, 16);
        p.getFrames().sendString("Staff Online", 275, 2);
        p.getMask().setLastChatMessage(
                new ChatMessage(0, 0, "There are currently " + number + " staff online!"));
        p.getMask().setChatUpdate(true);
        p.getFrames().sendChatMessage(0, "<col=2EA73D><shad=0>There are currently <col=ffff00><shad=ffffff>"
                + number + " <col=2EA73D><shad=0>staff online!");
        p.getFrames().sendInterface(275);
    }
}
