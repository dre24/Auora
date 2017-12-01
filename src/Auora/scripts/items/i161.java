package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.scripts.itemScript;

public class i161 extends itemScript {

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
        p.getInventory().deleteItem(161, 1, slot);
        p.getSkills().set(Skills.STRENGTH, p.getSkills().getLevelForXp(Skills.STRENGTH) + 5 + Math.round(p.getSkills().getLevelForXp(Skills.STRENGTH) * 15 / 100));
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
    }
}
