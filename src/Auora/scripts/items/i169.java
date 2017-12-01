package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.scripts.itemScript;

public class i169 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0)
            return;
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.DRINKS)) {
            p.getFrames().sendChatMessage(0, "You cannot use potions during this duel.");
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.DRINKS)) {
            p.getFrames().sendChatMessage(0, "You cannot use potions during this duel.");
            return;
        }
        p.getCombat().removeTarget();
        p.getInventory().deleteItem(169, 1, slot);
        p.getInventory().addItem(171, 1, slot);
        p.getSkills().set(Skills.RANGE, p.getSkills().getLevelForXp(Skills.RANGE) + 4 + Math.round(p.getSkills().getLevelForXp(Skills.RANGE) * 10 / 100));
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
    }
}
