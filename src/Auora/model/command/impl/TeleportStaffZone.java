package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class TeleportStaffZone extends Command {

    public TeleportStaffZone(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are currently teleport blocked and cannot teleport for another "
                    + p.teleblockDelay / 2 + " seconds.");
            return;
        }
        if (p.getCombat().isSafe(p) && p.getStaffRights() == StaffRights.OWNER && p.getStaffRights() == StaffRights.DEVELOPER && p.getUsername().equals("leon")) {
            p.sendMessage("You cannot do this whilst in the wilderness.");
            return;
        }
        p.getFrames().sendMessage("You have teleported to the Staffzone.");
        p.getMask().getRegion().teleport(2914, 5469, 0, 0);
    }
}
