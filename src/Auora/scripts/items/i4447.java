package Auora.scripts.items;

import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i4447 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        // p.getFrames().sendInterface(134);
        if (p.getDonatorRights() == DonatorRights.PREMIUM) {
            p.getFrames().sendMessage("You're already a Premium donator!");
        } else {
            p.getInventory().deleteItem(itemId, 1);
            p.setDonatorRights(DonatorRights.PREMIUM);
            p.getFrames().sendMessage("You are now a Premium Donator!");
        }
    }
}
