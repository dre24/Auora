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
public class ResetAuthenticator extends Command {

    public ResetAuthenticator(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
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
        p.sm("You have reset auth - " + other.getUsername());
        other.sm("Your auth code has been reset.");
        other.hasAuth = false;
        other.enteredAuth = false;
        other.authCode = "";
        PlayerSaving.save(other, false);
    }
}
