package Auora.skills.combat.spells;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;
import Auora.model.route.strategy.EntityStrategy;
import Auora.skills.combat.MagicInterface;
import Auora.skills.combat.RuneRequirements;
import Auora.util.CombatManager;
import Auora.util.Misc;

public class MiasmicBarrage implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        double maxHit = 370 + p.bonusMagicDamage;
        boolean multiCombat = false;
        if (opp.getCombat().isSafe(opp)) {
            return;
        }
        if (!p.getCombat().Multi(p) && !opp.getCombat().Multi(opp)) {
            multiCombat = true;
        }
        //if (p.jailTimer > 0) {
        //	p.getFrames().sendChatMessage(0, "You are jailed.");
        //	return;
        //}

        if (p.getDonatorRights().ordinal() < 3) {
            p.sm("You have to be an Extreme Donator to cast this spell.");
        }
        if (!RuneRequirements.hasRunes(p, "MIASMIC_BARRAGE")) {
            p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
            return;
        }
        if (!p.getEquipment().contains(13867)) {
            p.getFrames().sendChatMessage(0, "You need Zuriel's Staff to cast this spell.");
            return;//hmmk do you wanna do the other stuff then i can have hanna take a look? i already did the first one incase you got it to wokr some time okay so what are the others
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
        /*
         * if(hit == 0) { p.getSkills().sendCounter(52, 52, true); }
		 */
        if (p.getLocation().equals(opp.getLocation().getX(), opp.getLocation().getY())) {
            p.getWalk().walkTo(new EntityStrategy(opp), true);
        }
        if (((p.getCombat().inDangerousPVP(p) && opp.getCombat().inDangerousPVP(opp)) || (p.getCombat().inWild(p) && opp.getCombat().inWild(opp))) /*p.getUsername() != opp.skulledOn*/ && !opp.skulledOn2.contains(p.getUsername())) {
            p.skull(p);

            p.skulledOn2.add(opp.getUsername());
        }
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.animate(10518);
        p.graphics(1853);
        opp.graphics(-1);
        boolean orbs = false;


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
                                    opp.getCombat().applyVengeance1(Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                                }
                            }
                        }
                    }, 0, 0);

                    opp.graphics(1854);

                } else {
                    opp.graphics2(85);
                }
                this.stop();
            }
        }, 4, 0);
    }

}


//1853 is the atk anim
// 1854 is the receive anim
// 1741 is receive gfx if splash

