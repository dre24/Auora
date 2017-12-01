package Auora.model;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;

public class Teleporter {

    public static void tele(final Player p, final int x, final int y) {
        if (p.getSkills().playerDead || p.isDead()) {
            return;
        }
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot use commands while tele blocked.");
            return;
        }
        if (p.getCombat().hasTarget()) {
            p.getFrames().sendChatMessage(0, "You cannot tele while in combat.");
            return;
        }
        p.animate(9606);
        p.graphics(1685);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            int count = 0;

            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.stop();
                    return;
                }
                if (count++ == 0)
                    p.getMask().getRegion().teleport(x, y, 0, 0);
                else {
                    p.animate(-1);
                    p.graphics(-1);
                    this.stop();
                }
            }

        }, 5, 0, 0);
    }

}
