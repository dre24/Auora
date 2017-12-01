package Auora.model.command.impl;

import Auora.GameSettings;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.net.mysql.impl.Store;

/**
 * @author Jonny
 */
public class Claim extends Command {

    public Claim(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        if (!GameSettings.STORE_ENABLED) {
            player.sendMessage("The store is currently offline! Try again in 30 minutes.");
            return;
        }
        if (player.claimingStoreItems) {
            player.sendMessage("You already have a active store claim process going...");
            return;
        }
        player.sendMessage("Checking for any store purchases...");
        Store.claimItem(player);
    }
}
