package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i3144 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p == null)
            return;
        if (p.isDead())
            return;
        if (p.getInventory().getContainer().get(slot) == null) {
            return;
        }
        if (p.getInventory().getContainer().get(slot).getId() != itemId) {
            return;
        }
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastKarambwam() < 0)
            return;
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.FOOD)) {
            p.getFrames().sendChatMessage(0, "You cannot use food during this duel.");
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.FOOD)) {
            p.getFrames().sendChatMessage(0, "You cannot use food during this duel.");
            return;
        }
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0) {
            return;
        }
        p.getCombat().removeTarget();
        p.getInventory().deleteItem(3144, 1, slot);
        p.animate(829);
        p.getSkills().heal(180);
        if (p.getCombat().delay <= 3) {
            p.getCombat().delay = 3;
        }
        p.getCombatDefinitions().setLastKarambwam(System.currentTimeMillis() + 1800);
        p.getFrames().sendChatMessage(0, "You eat the Cooked karambwan.");
    }

}