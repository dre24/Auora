package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i18344 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;

        p.Augury = 1;
        p.getInventory().deleteItem(itemId, 1);
        p.getFrames().sendMessage("You have now unlocked the prayer Augury.");
    }

}
