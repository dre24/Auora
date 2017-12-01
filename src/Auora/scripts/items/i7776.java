package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i7776 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;

        /*if (p.getDonatorRights() == DonatorRights.SUPER)
        {
        p.getFrames().sendMessage("You're already a Super donator!");	
        } else {
        p.getInventory().deleteItem(itemId, 1);
        p.setDonatorRights(DonatorRights.SUPER); 
        p.getFrames().sendMessage("You are now a <col=ff0000><shad=0>Super Donator</col></shad>!");
    }*/
    }
}
