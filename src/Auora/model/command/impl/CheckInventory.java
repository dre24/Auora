package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.net.Packets;

import java.io.File;

/**
 * @author Jonny
 */
public class CheckInventory extends Command {

    public CheckInventory(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::chat message");
            return;
        }
        String name = args[0].toLowerCase();
        File file = new File("./characters/" + name + ".json");
        if (!file.exists()) {
            p.getFrames().sendChatMessage(0, "The player " + name + " does not exist.");
            return;
        }
        Player other = Packets.getPlayerByName(name);
        if (other == null) {
            other = new Player(name);
            PlayerLoading.getResult(other);
        }
        other.initialiseInventory();
        p.getBank().openInventory(other);
        p.getFrames().sendChatMessage(0, "You have opened " + other.getDisplayName() + "'s inventory.");
    }
}
