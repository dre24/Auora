package Auora.skills.combat.spells;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;
import Auora.model.route.strategy.EntityStrategy;
import Auora.skills.combat.MagicInterface;
import Auora.skills.combat.RuneRequirements;
import Auora.util.CombatManager;
import Auora.util.Misc;

public class IceBlitz implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        double maxHit = 260 + p.bonusMagicDamage;
        if (opp.getCombat().isSafe(opp)) {
            return;
        }
        //if(p.jailTimer > 0) {
        //p.getFrames().sendChatMessage(0, "You are jailed.");
        //return;
        //}
        if (!p.getEquipment().contains(18346)) {
            if (!RuneRequirements.hasRunes(p, "ICE_BLITZ")) {
                p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
                return;
            }
        }
        if (p.getSkills().getLevel(6) < 82) {
            p.getFrames().sendChatMessage(0, "You don't have the required magic level to cast this spell.");
            return;
        }
        if (p.getEquipment().contains(4675)) {
            maxHit *= 1.1;
        }
        if (p.getEquipment().contains(18335)) {
            maxHit *= 1.15;
        }
        if (p.getEquipment().contains(18355)) {
            maxHit *= 1.20;
        }
        if (p.getEquipment().contains(6914)) {
            maxHit *= 1.20;
        }
        if (p.getEquipment().contains(15486)) {
            maxHit *= 1.15;
        }
        if (p.getEquipment().contains(19710)) {
            maxHit *= 1.05;
        }
        if (p.getEquipment().contains(18741)) {
            maxHit *= 1.01;
        }
        if (p.getEquipment().contains(18740)) {
            maxHit *= 1.03;
        }
        if (p.getEquipment().contains(18742)) {
            maxHit *= 1.04;
        }
        if (p.getEquipment().contains(18743)) {
            maxHit *= 1.06;
        }
        if (p.getLocation().equals(opp.getLocation().getX(), opp.getLocation().getY())) {
            p.getWalk().walkTo(new EntityStrategy(opp), true);
        }
        if (((p.getCombat().inDangerousPVP(p) && opp.getCombat().inDangerousPVP(opp)) || (p.getCombat().inWild(p) && opp.getCombat().inWild(opp))) /*p.getUsername() != opp.skulledOn*/ && !opp.skulledOn2.contains(p.getUsername())) {
            p.skull(p);

            p.skulledOn2.add(opp.getUsername());
        }
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.graphics(-1);
        p.graphics2(-1);
        p.animate(1978);
        p.graphics(366);
        p.getInventory().deleteItem(555, 3);
        p.getInventory().deleteItem(560, 2);
        p.getInventory().deleteItem(565, 2);
        if (opp.getCombat().immuneDelay == 0 & hit != 0) {
            opp.getCombat().removeTarget();
            opp.getWalk().reset(true);
            opp.getCombat().freezeDelay = 20;
            opp.getCombat().immuneDelay = 28;
            opp.getFrames().sendChatMessage(0, "You have been frozen!");
        } else {
            p.getSkills().sendCounter(52, true);
        }
        p.getSkills().sendCounter(hit, true);
        final boolean sucessfulCast = hit != 0;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.animate(CombatManager.getDefenceEmote(opp));
                if (sucessfulCast) {
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {
                            opp.hit(hit, p);
                            if (opp.getCombat().vengeance) {
                                if (hit > 0) {
                                    opp.getCombat().applyVengeance1(
                                            Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                                }
                            }
                        }
                    }, 1, 0);
                    opp.graphics(367);
                } else {
                    opp.graphics2(85);
                }
                this.stop();
            }
        }, 2, 0);
    }


}
