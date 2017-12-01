package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.scripts.itemScript;

public class i6687 extends itemScript {

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
        p.getInventory().deleteItem(6687, 1, slot);
        p.getInventory().addItem(6689, 1, slot);
        p.getSkills().healBrew(160);
        p.getSkills().set(Skills.DEFENCE, (int) (p.getSkills().getLevelForXp(Skills.DEFENCE) * 1.25));
        p.getSkills().set(Skills.STRENGTH, (int) (p.getSkills().getLevel(2) * 0.9));
        p.getSkills().set(Skills.ATTACK, (int) (p.getSkills().getLevel(0) * 0.9));
        p.getSkills().set(Skills.RANGE, (int) (p.getSkills().getLevel(Skills.RANGE) * 0.9));
        p.getSkills().set(Skills.MAGIC, (int) (p.getSkills().getLevel(Skills.MAGIC) * 0.9));
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
        if (p.getCombat().delay <= 3) {
            p.getCombat().delay = 3;
        }
    }
}