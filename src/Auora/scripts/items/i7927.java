package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i7927 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        p.getAppearence().setNpcType((short) 3689);
        p.getFrames().sendChatMessage(0, "<col=0000FF>You</col><col=00FF00> are</col><col=0000FF> turned</col><col=00FF00> into</col><col=0000FF> an</col><col=00FF00><u> Easter Egg!");
    }
}