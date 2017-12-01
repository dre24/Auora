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
public class CheckBank extends Command {

    public CheckBank(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
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
        other.initialiseBank();
        p.getBank().openBank(other);
        p.getFrames().sendChatMessage(0, "You have opened " + other.getDisplayName() + "'s bank.");
    }
}
