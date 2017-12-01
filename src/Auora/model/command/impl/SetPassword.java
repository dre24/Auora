package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.model.player.account.PlayerSaving;
import Auora.net.Packets;

/**
 * @author Jonny
 */
public class SetPassword extends Command {

    public SetPassword(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null || args.length <= 1) {
            p.sendMessage("You must do this command as ::setgender gender");
            return;
        }
        String name = args[0].toLowerCase();
        String newPassword = args[1];
        Player other = Packets.getPlayerByName(name);
        if (other == null) {
            other = new Player(name);
            PlayerLoading.getResult(other);
        }
        if (p.getStaffRights().isHigherRank(other.getStaffRights())) {
            p.sendMessage("You can't use a command on " + name + " because he is a higher rank than you.");
            return;
        }
        if (p.getStaffRights() != StaffRights.OWNER && p.getStaffRights() != StaffRights.ADMINISTRATOR && p.getStaffRights() != StaffRights.GLOBAL_ADMIN) {
            if (other.getStaffRights() == StaffRights.ADMINISTRATOR || other.getStaffRights() == StaffRights.OWNER || other.getStaffRights() != StaffRights.GLOBAL_ADMIN) {
                p.sendMessage("You can't use this command on administrators or owners.");
                return;
            }
        }
        other.setPassword(newPassword);
        PlayerSaving.save(other, false);
        p.sendMessage("You have set the player " + name + "'s password to " + other.getPassword());
    }
}
