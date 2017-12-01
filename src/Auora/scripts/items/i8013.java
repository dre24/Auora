package Auora.scripts.items;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i8013 extends itemScript {

    @Override
    public void option1(final Player p, final int itemId, final int interfaceId, final int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (p.isDead()) {
            return;
        }
        if (p.tabTimer > 0) {
            return;
        }
        if (p.getDuelSession() != null) {
            p.sendMessage("You can't teleport out of a duel.");
            return;
        }
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot teleport while teleblocked.");
            return;
        }
        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
            return;
        }
        p.tabTimer = 5;
        p.animate(4069);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            int count = 0;

            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.stop();
                    return;
                }
                if (count++ == 0) {
                    p.animate(4071);
                    p.graphics(678);
                    p.getInventory().deleteItem(8013, 1, slot);
                } else {
                    p.getMask().getRegion().teleport(3087, 3490, 0, 0);
                    p.animate(-1);
                    this.stop();
                }
            }

        }, 1, 0, 0);
    }
}