package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class BarrageRunes extends Command {

    public BarrageRunes(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getInventory().addItem(555, 10000);
        p.getInventory().addItem(560, 10000);
        p.getInventory().addItem(565, 10000);
        p.getFrames().sendChatMessage(0, "You have been given some barrage runes!");
        p.getFrames().sendInterface(1, 548, 205, 193);
        p.getFrames().sendInterface(1, 746, 93, 193);
        p.getFrames().sendChatMessage(0, "You switch your spellbook to: ANCIENTS");
        p.spellbook = 1;
        p.getCombat().vengeance = false;
    }
}
