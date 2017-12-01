package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class OpenStorePage extends Command {

    public OpenStorePage(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        player.getFrames().sendChatMessage(0, "You have opened the Auora store page.");
        player.getFrames().sendChatMessage(12, "http://Auora614.org/store");
    }
}
