package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i385 extends itemScript {

    @Override
    public void option1(final Player p, int itemId, int interfaceId, final int slot) {
        if (p == null)
            return;
        if (p.isDead())
            return;
        if (p.getInventory().getContainer().get(slot) == null) {
            //p.getFrames().sendChatMessage(0,"Inventory null, relog or bank.");
            return;
        }
        if (p.getInventory().getContainer().get(slot).getId() != itemId) {
            //p.getFrames().sendChatMessage(0,"Item null, relog or bank.");
            return;
        }
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastFood() < 0)
            return;
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.FOOD)) {
            p.getFrames().sendChatMessage(0, "You cannot use food during this duel.");
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.FOOD)) {
            p.getFrames().sendChatMessage(0, "You cannot use food during this duel.");
            return;
        }
        p.getCombat().removeTarget();
        p.animate(829);
        p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1500);
        p.getCombat().delay += 2;
        p.getInventory().deleteItem(385, 1, slot);
        p.getSkills().heal(200);
        p.getFrames().sendChatMessage(0, "You eat the Shark.");
    }
}
