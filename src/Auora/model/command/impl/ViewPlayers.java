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
public class ViewPlayers extends Command {

    public ViewPlayers(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.hoursPlayed > 1) {
            int number = 0;
            for (int i = 0; i < 316; i++) {
                p.getFrames().sendString("", 275, i);
            }

            for (Player other_players : World.getPlayers()) {
                if (other_players == null)
                    continue;
                number++;
                String titles = "";
                String name = Misc.formatPlayerNameForDisplay(other_players.getUsername().replaceAll("_", " "));

                if (other_players.getStaffRights() == StaffRights.PLAYER) {
                    p.getFrames().sendString(other_players.getDonatorRights().getColor() + other_players.getDonatorRights().getShad() + name + " - " + "<col=289C39>Safe Kills: <col=289C39>[" + other_players.safeKills + "]" + "<col=289C39> Unsafe Kills: <col=289C39>[" + other_players.dangerousKills + "]" + "<col=289C39> Deaths: <col=289C39>[" + other_players.deaths + "]", 275, (16 + number));
                } else {
                    p.getFrames().sendString("<img=" + other_players.getStaffRights().getCrownId() + ">" + other_players.getStaffRights().getColor() + other_players.getStaffRights().getShad() + name + " - " + "<col=289C39>Kills: <col=289C39>[" + other_players.safeKills + "]" + "<col=289C39> Unsafe Kills: <col=289C39>[" + other_players.dangerousKills + "]" + "<col=289C39> Deaths: <col=289C39>[" + other_players.deaths + "]", 275, (16 + number));
                }
            }
            p.getFrames().sendString("<u=000080>Players</u>", 275, 14);
            p.getFrames().sendString("Players Online: " + number, 275, 16);
            p.getFrames().sendString("Player's Online", 275, 2);
            p.getMask().setLastChatMessage(
                    new ChatMessage(0, 0, "There are currently " + World.getPlayers().size() + " players online!"));
            p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage(0, "<col=2EA73D><shad=0>There are currently <col=ffff00><shad=ffffff>"
                    + World.getPlayers().size() + " <col=2EA73D><shad=0>players online!");
            p.getFrames().sendInterface(275);
            p.lastRandomizationName = "";

        }
    }
}
