package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.net.Packets;

/**
 * @author Jonny
 */
public class SendHome extends Command {

    public SendHome(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::sendhome name");
            return;
        }
        if (p.getCombat().isSafe(p) && p.getStaffRights() == StaffRights.OWNER && p.getStaffRights() == StaffRights.DEVELOPER && p.getUsername().equals("leon")) {
            p.sendMessage("You cannot do this whilst in the wilderness.");
            return;
        }

        String name = args[0].toLowerCase();
        if (!PlayerLoading.accountExists(name)) {
            p.sendMessage("The player " + name + " does not exist.");
            return;
        }
        Player other = Packets.getPlayerByName(name);
        if (other == null) {
            p.sendMessage("The player " + name + " is currently offline.");
            return;
        }

        p.getFrames().sendMessage("<col=ff0000>You have successfully sent the player" + name + " home.");
        other.getMask().getRegion().teleportNoCheck(3087, 3490, 0, 0);
    }
}
