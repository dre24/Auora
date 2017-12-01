package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i143 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0)
            return;
        int increase = 7 + (p.getSkills().getLevelForXp(5) / 4);
        p.getCombat().removeTarget();
        p.getInventory().deleteItem(143, 1, slot);
        p.getSkills().RestorePray(increase);
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
    }
}