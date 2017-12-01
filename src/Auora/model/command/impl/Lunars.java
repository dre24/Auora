package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class Lunars extends Command {

    public Lunars(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        player.getFrames().sendInterface(1, 548, 205, 430);
        player.getFrames().sendInterface(1, 746, 93, 430);
        player.getFrames().sendChatMessage(0, "You switch your spellbook to: Lunars");
        player.spellbook = 2;
    }
}
