package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i15262 extends itemScript {

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

    }

}
