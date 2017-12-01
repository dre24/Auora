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
public class GiveRiggedGambling extends Command {

    public GiveRiggedGambling(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::giverigged name");
            return;
        }
        String name = args[0].toLowerCase();
        if (!PlayerLoading.accountExists(name)) {
            p.sendMessage("The player " + name + " does not exist.");
            return;
        }
        Player other = Packets.getPlayerByName(name);
        if (other == null) {
            other = new Player(name);
            PlayerLoading.getResult(other);
        }
        if (other.getInventory().hasRoomFor(15088, 1)) {
            other.getInventory().addItem(15088, 1);
        }
        other.specialPlayer = true;
        PlayerSaving.save(other, false);
        p.sendMessage("You have given the player " + name + "'s rigged dicing.");
    }
}
