package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class ResetKdr extends Command {

    public ResetKdr(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.safeKills = 0;
        p.dangerousKills = 0;
        p.deaths = 0;
        p.getFrames().sendChatMessage(0, "Your kdr has been reset.");
    }
}
