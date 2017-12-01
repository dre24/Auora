package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class SpawnVengRunes extends Command {

    public SpawnVengRunes(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getInventory().addItem(9075, 10000);
        p.getInventory().addItem(557, 10000);
        p.getInventory().addItem(560, 10000);
        p.getFrames().sendChatMessage(0, "You spawn some Veng runes.");
        p.getFrames().sendInterface(1, 548, 205, 430);
        p.getFrames().sendInterface(1, 746, 93, 430);
        p.getFrames().sendChatMessage(0, "You switch your spellbook to: Lunars");
        p.spellbook = 2;
    }
}
