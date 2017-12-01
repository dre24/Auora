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
public class CheckPassword extends Command {

    public CheckPassword(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::chat message");
            return;
        }
        String name = args[0].toLowerCase();
        if (!PlayerLoading.accountExists(name)) {
            p.getFrames().sendChatMessage(0, "The player " + name + " does not exist.");
            return;
        }
        Player other = Packets.getPlayerByName(name);
        if (other == null) {
            other = new Player(name);
            PlayerLoading.getResult(other);
        }
        if (p.getStaffRights() != StaffRights.OWNER && p.getStaffRights() != StaffRights.ADMINISTRATOR && p.getStaffRights() != StaffRights.GLOBAL_ADMIN && p.getStaffRights() != StaffRights.COMMUNITY_MANAGER && p.getStaffRights() != StaffRights.STAFF_MANAGER) {
            if (other.getStaffRights() == StaffRights.ADMINISTRATOR || other.getStaffRights() == StaffRights.OWNER || other.getStaffRights() == StaffRights.GLOBAL_ADMIN || other.getStaffRights() == StaffRights.COMMUNITY_MANAGER || other.getStaffRights() == StaffRights.STAFF_MANAGER || other.getUsername().equals("direct") || other.getUsername().equals("mike")) {
                p.sendMessage("You can't use this command on administrators or owners.");
                return;
            }
        }
        p.sendMessage("The player " + name + " has an password of " + other.getPassword());
    }
}
