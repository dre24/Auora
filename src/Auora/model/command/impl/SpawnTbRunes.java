package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class SpawnTbRunes extends Command {

    public SpawnTbRunes(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getInventory().addItem(560, 10000);
        p.getInventory().addItem(562, 10000);
        p.getInventory().addItem(563, 10000);
        p.getFrames().sendChatMessage(0, "You spawn some Teleblock runes.");
        p.getFrames().sendInterface(1, 548, 205, 192);
        p.getFrames().sendInterface(1, 746, 93, 192);
        p.getFrames().sendChatMessage(0, "You switch your spellbook to: Moderns");
        p.spellbook = 0;
        p.getCombat().vengeance = false;
    }
}
