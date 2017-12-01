package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.scripts.itemScript;

public class i145 extends itemScript {

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
        p.getInventory().deleteItem(145, 1, slot);
        p.getInventory().addItem(147, 1, slot);
        p.getSkills().set(Skills.ATTACK, p.getSkills().getLevelForXp(Skills.ATTACK) + 5 + Math.round(p.getSkills().getLevelForXp(Skills.ATTACK) * 15 / 100));
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
    }
}
