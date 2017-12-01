package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i7629 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        p.getInventory().deleteItem(itemId, 1);
        p.dicerRank = 1;
        p.getFrames().sendMessage("You are now a <col=ff0000><shad=0>Dicer</col></shad>!");
    }
}
