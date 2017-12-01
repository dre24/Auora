package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.scripts.itemScript;

public class i15335 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getDonatorRights().ordinal() < 3) {
            p.getFrames().sendMessage("You must be an Extreme Donator to drink this potion.");
            return;
        }
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
        p.getInventory().deleteItem(15335, 1, slot);
        p.getSkills().set(Skills.DEFENCE, p.getSkills().getLevelForXp(Skills.DEFENCE) + 6 + Math.round(p.getSkills().getLevelForXp(Skills.DEFENCE) * 14 / 100));
        p.getSkills().set(Skills.ATTACK, p.getSkills().getLevelForXp(Skills.ATTACK) + 6 + Math.round(p.getSkills().getLevelForXp(Skills.ATTACK) * 14 / 100));
        p.getSkills().set(Skills.STRENGTH, p.getSkills().getLevelForXp(Skills.STRENGTH) + 6 + Math.round(p.getSkills().getLevelForXp(Skills.STRENGTH) * 14 / 100));
        p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC) + 4 + Math.round(p.getSkills().getLevelForXp(Skills.MAGIC) * 3 / 100));
        p.getSkills().set(Skills.RANGE, p.getSkills().getLevelForXp(Skills.RANGE) + 5 + Math.round(p.getSkills().getLevelForXp(Skills.RANGE) * 12 / 100));
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
        if (p.getCombat().delay <= 3) {
            p.getCombat().delay = 3;
        }
    }
}