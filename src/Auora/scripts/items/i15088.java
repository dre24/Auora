package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i15088 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (!p.specialPlayer)
            return;
        p.getFrames().requestIntegerInput(1, "What would you like to roll?");
    }
}