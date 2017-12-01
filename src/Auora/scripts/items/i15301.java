package Auora.scripts.items;

import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i15301 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getDonatorRights().ordinal() < 3) {
            p.getFrames().sendMessage("You must be an Extreme Donator to drink this potion.");
            return;
        }
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't drink this potion here.");
            return;
        }
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (p.specPot != 0) {
            p.getFrames().sendChatMessage(0, "Please try to use this again in <col=ffff00><shad=ffffff>" + p.specPot / 2 + "</col></shad> seconds.");
            return;
        }
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0)
            return;
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.DRINKS)) {
            p.getFrames().sendChatMessage(0, "You cannot use potions during this duel.");
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.DRINKS)) {
            p.getFrames().sendChatMessage(0, "You cannot use potions during this duel.");
            return;
        }
        p.specPot = 60;
        p.getCombat().removeTarget();
        p.getInventory().deleteItem(15301, 1, slot);
        p.getInventory().addItem(15302, 1, slot);
        p.getCombatDefinitions().specpercentage += 25;
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
        if (p.getCombat().delay <= 3) {
            p.getCombat().delay = 3;
        }
    }
}
