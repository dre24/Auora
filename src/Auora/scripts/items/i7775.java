package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i7775 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;

        /*if (p.getDonatorRights() == DonatorRights.REGULAR)
        {
        p.getFrames().sendMessage("You're already a Regular donator!");	
        } else{
        p.getInventory().deleteItem(itemId, 1);
        p.setDonatorRights(DonatorRights.REGULAR);
        p.getFrames().sendMessage("You are now a <col=ff0000><shad=0>Regular Donator</col></shad>!");
    }*/
    }
}
