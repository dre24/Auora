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
public class DisableYell extends Command {

    public DisableYell(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        String name = args[0];
        if (!PlayerLoading.accountExists(name)) {
            p.sendMessage("The player " + name + " does not exist.");
            return;
        }
        Player other = Packets.getPlayerByName(name);
        if (other == null) {
            other = new Player(name);
            PlayerLoading.getResult(other);
        }
        other.setYellDisabled(true);
        PlayerSaving.save(other, false);
    }
}
