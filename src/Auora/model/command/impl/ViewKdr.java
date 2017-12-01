package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.ChatMessage;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class ViewKdr extends Command {

    public ViewKdr(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "You have in total " + p.safeKills + " Safe Kills, " + p.dangerousKills + " Unsafe Kills, " + p.deaths + " Deaths"));
        p.getMask().setChatUpdate(true);
        p.getFrames().sendChatMessage(0, "You have in total " + p.safeKills + " Safe Kills, " + p.dangerousKills + " Unsafe Kills, " + p.deaths + " Deaths");
    }
}
