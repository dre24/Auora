package Auora.model.command.impl;

import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.net.Packets;

/**
 * @author Jonny
 */
public class KickPlayer extends Command {

    public KickPlayer(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must use this command as ::kick-name");
            return;
        }
        String name = args[0].toLowerCase();
        Player other = Packets.getPlayerByName(name);
        if (!PlayerLoading.accountExists(name)) {
            p.sendMessage("The player " + name + " does not exist.");
            return;
        }
        if (other == null) {
            p.sendMessage("The player " + name + " is currently offline.");
            return;
        }
        if (p.getStaffRights().isHigherRank(other.getStaffRights())) {
            p.sendMessage("You can't use a command on " + name + " because he is a higher rank than you.");
            return;
        }
        other.sure = false;
        other.enteredAuth = false;
        p.getFrames().sendMessage("You have kicked the player " + other.getDisplayName() + ".");
        World.unRegisterConnection(other.getConnection());
    }
}
