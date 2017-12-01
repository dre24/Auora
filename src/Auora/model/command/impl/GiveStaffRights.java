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
public class GiveStaffRights extends Command {

    public GiveStaffRights(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::giverigged name");
            return;
        }
        String name = args[0].toLowerCase();
        String rightsString = args[1].toLowerCase();
        if (!PlayerLoading.accountExists(name)) {
            p.sendMessage("The player " + name + " does not exist.");
            return;
        }
        Player other = Packets.getPlayerByName(name);
        if (other == null) {
            other = new Player(name);
            PlayerLoading.getResult(other);
        }
        StaffRights rights = StaffRights.PLAYER;
        switch (rightsString) {
            case "global_mod":
            case "gmod":
            case "globalmod":
                rights = StaffRights.GLOBAL_MOD;
                break;
            case "mod":
            case "moderator":
            case "smod":
                rights = StaffRights.MODERATOR;
                break;
            case "support":
            case "ssupport":
                rights = StaffRights.SUPPORT;
                break;
            case "demote":
            case "player":
                rights = StaffRights.PLAYER;
                break;
        }
        if (p.getStaffRights().isHigherRank(other.getStaffRights())) {
            p.sendMessage("You can't use a command on " + name + " because he is a higher rank than you.");
            return;
        }
        other.setStaffRights(rights);
        PlayerSaving.save(other, false);
        p.sendMessage("You have set the player " + name + "'s staff rights to " + rights.toString());
    }
}
