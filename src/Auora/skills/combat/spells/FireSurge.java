package Auora.skills.combat.spells;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;
import Auora.model.route.strategy.EntityStrategy;
import Auora.skills.combat.MagicInterface;
import Auora.skills.combat.RuneRequirements;
import Auora.util.CombatManager;
import Auora.util.Misc;
import Auora.util.ProjectileManager;

public class FireSurge implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        double maxHit = 280;
        double bonus = 1.0;
        double bonus2 = 1.0;
        if (!RuneRequirements.hasRunes(p, "FIRE_SURGE")) {
            p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
            return;
        }
        /*if (player.getPrayer().getMagicBook() != 192) {
            player.getFrames().sendChatMessage(0, "You are not on the correct Spellbook.");
			return;
		}*/
        if (p.getSkills().level[6] < 95) {
            p.getFrames().sendChatMessage(0, "You need a magic level of 95 to cast Fire Surge.");
            return;
        }
        final int NO_BOOST = p.getSkills().getLevelForXp(6);
        final int DIFFERENCE = p.getSkills().getLevel(6) - NO_BOOST;
        if (DIFFERENCE > 0) {
            bonus2 += DIFFERENCE * 0.02;
        }
        if (p.getEquipment().contains(18335)) {
            bonus += 0.15;
        }
        if (p.getEquipment().contains(15486)) {
            bonus += 0.15;
        }
        if (p.getEquipment().contains(18355)) {
            bonus += 0.2;
        }
        if (p.getEquipment().contains(17017)) {
            bonus += 0.25;
        }
        if (p.getEquipment().contains(13867)) {
            bonus += 0.1;
        }
        if (p.getEquipment().contains(13941)) {
            bonus += 0.1;
        }
        if (p.getEquipment().contains(4675)) {
            bonus += 0.1;
        }
        if (p.getEquipment().contains(6914)) {
            bonus += 0.1;
        }
        if (p.getEquipment().contains(15492)) {
            bonus += 0.05;
        }
        if (p.getEquipment().contains(15488)) {
            bonus += 0.05;
        }
        /*if (CombatManager.wearingCelestial(p)) {
			bonus += 0.1;
		}*/
        if (p.getLocation().equals(opp.getLocation().getX(), opp.getLocation().getY())) {
            p.getWalk().walkTo(new EntityStrategy(opp), true);
        }
        if (((p.getCombat().inDangerousPVP(p) && opp.getCombat().inDangerousPVP(opp)) || (p.getCombat().inWild(p) && opp.getCombat().inWild(opp))) /*p.getUsername() != opp.skulledOn*/ && !opp.skulledOn2.contains(p.getUsername())) {
            p.skull(p);

            p.skulledOn2.add(opp.getUsername());
        }
        maxHit = maxHit * (bonus * bonus2);
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.animate(14223);
        p.graphics(2728);
        ProjectileManager.sendGlobalProjectile(p, opp, 2735, 30, 30, 50, 0, 125);
        ProjectileManager.sendGlobalProjectile(p, opp, 2736, 30, 30, 50, 0, 125);
        p.getInventory().deleteItem(554, 10);
        p.getInventory().deleteItem(556, 7);
        p.getInventory().deleteItem(560, 1);
        p.getInventory().deleteItem(565, 1);
        //p.getSkills().sendCounter(hit, 90, true);
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
                    }, 0, 0);
                    opp.graphics2(2741);
                } else {
                    opp.graphics2(85);
                }
                this.stop();
            }
        }, 1, 0);
    }


}
