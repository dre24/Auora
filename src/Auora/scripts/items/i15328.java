package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.scripts.itemScript;

public class i15328 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0)
            return;
        if (p.getDonatorRights().ordinal() < 3) {
            p.getFrames().sendMessage("You must be an Extreme Donator to drink this potion.");
            return;
        }
        p.getCombat().removeTarget();
        p.getInventory().deleteItem(15328, 1, slot);
        p.getInventory().addItem(15329, 1, slot);
        int increase = 8 + (p.getSkills().getLevelForXp(5) / 3);
        for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
            if (p.getSkills().getLevel(i) >= p.getSkills().getLevelForXp(i)) {
                continue;
            }
            if (i != 5) {
                p.getSkills().set(i, p.getSkills().getLevelForXp(i));
            } else {
                p.getSkills().RestorePray(increase);
            }
        }
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
        if (p.getCombat().delay <= 3) {
            p.getCombat().delay = 3;
        }
    }
}