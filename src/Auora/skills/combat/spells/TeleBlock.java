package Auora.skills.combat.spells;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;
import Auora.skills.combat.MagicInterface;
import Auora.skills.combat.RuneRequirements;
import Auora.util.CombatManager;
import Auora.util.Misc;
import Auora.util.ProjectileManager;

public class TeleBlock implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        double maxHit = 100;
        if (opp.getCombat().isSafe(opp)) {
            return;
        }
       /* if (p.spellbook == 1 || p.spellbook == 2
                || p.spellbook == 3) {
            return;
        }*/
        if (!p.getCombat().Multi(p) && !opp.getCombat().Multi(opp)) {
        }
        if (!RuneRequirements.hasRunes(p, "TELEPORT_BLOCK")) {
            p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
            return;
        }
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.animate(10503);
        p.graphics(1841);
        p.getInventory().deleteItem(562, 1);
        p.getInventory().deleteItem(563, 1);
        p.getInventory().deleteItem(560, 1);
        ProjectileManager.sendGlobalProjectile(p, opp, 1842, 42, 35, 55, 23, 0);
        p.getSkills().sendCounter(81, true);
        if (hit != 0) {
            if (opp.teleblockimmuneDelay > 0) {
            } else {
                opp.teleblockDelay = 600;
                opp.teleblockimmuneDelay = 600;
            }
        } else {
            p.getSkills().sendCounter(52, true);
        }
        final boolean sucessfulCast = hit != 0;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.animate(CombatManager.getDefenceEmote(opp));
                if (sucessfulCast) {
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {
                            opp.hit(50, p);
                            if (opp.getCombat().vengeance) {
                                if (hit > 0) {
                                    opp.getCombat().applyVengeance1(
                                            Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                                }
                            }
                        }
                    }, 1, 0);
                    opp.graphics(1843);
                } else {
                    opp.graphics2(85);
                }
                this.stop();
            }
        }, 2, 0);
    }


}
