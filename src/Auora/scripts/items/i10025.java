package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.scripts.itemScript;


public class i10025 extends itemScript {
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        p.getFrames().sendMessage("You will be banned shortly.");
    }
}