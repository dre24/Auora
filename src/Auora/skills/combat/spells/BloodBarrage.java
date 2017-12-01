package Auora.skills.combat.spells;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.model.route.strategy.EntityStrategy;
import Auora.skills.combat.MagicInterface;
import Auora.skills.combat.RuneRequirements;
import Auora.util.CombatManager;
import Auora.util.Misc;

import java.util.ArrayList;
import java.util.List;

public class BloodBarrage implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        int difference = Math.abs(p.getSkills().getCombatLevel() - opp.getSkills().getCombatLevel());
        if (difference > 15 || difference < -15) {
            p.getCombat().removeTarget();
            p.getCombat().queuedSet = false;
            return;
        }
        double maxHit = 280 + p.bonusMagicDamage;
        if (p.getSkills().getLevel(6) < 94) {
            p.getFrames().sendChatMessage(0, "You don't have the required magic level to cast this spell.");
            return;
        }
            /*if(player.jailTimer > 0) {
            player.getFrames().sendChatMessage(0, "You are jailed.");
			return;
			}*/
        if (opp.getCombat().isSafe(opp)) {
            return;
        }
        if (!RuneRequirements.hasRunes(p, "BLOOD_BARRAGE")) {
            p.getFrames().sendChatMessage(0,
                    "You don't have enough runes to cast this spell.");
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
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.animate(1979);
        p.getInventory().deleteItem(556, 1);
        p.getInventory().deleteItem(560, 4);
        p.getInventory().deleteItem(565, 4);
        if (((p.getCombat().inDangerousPVP(p) && opp.getCombat().inDangerousPVP(opp)) || (p.getCombat().inWild(p) && opp.getCombat().inWild(opp))) /*p.getUsername() != opp.skulledOn*/ && !opp.skulledOn2.contains(p.getUsername())) {
            p.skull(p);

            p.skulledOn2.add(opp.getUsername());
        }
        if (!opp.getCombat().Multi(opp)) {
            opp.graphics(-1);
            p.graphics(-1);
            p.graphics2(-1);
            p.getSkills().sendCounter(hit, true);
            if (hit != 0) {
                p.getSkills().heal(hit / 4);
                opp.getFrames().sendChatMessage(0, "You feel the life sucked out of you.");
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
                                opp.hit(hit, p);
                                if (opp.getCombat().vengeance) {
                                    if (hit > 0) {
                                        opp.getCombat().applyVengeance1(
                                                Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                                    }
                                }
                            }
                        }, 0, 0);
                        opp.graphics(377);
                    } else {
                        opp.graphics2(85);
                    }
                    this.stop();
                }
            }, 2, 0);
        } else {
            p.graphics(-1);
            p.graphics2(-1);
            List<Player> players = new ArrayList<Player>(9);
            double distance;
            players.add(opp);
            for (Player d : World.getPlayers()) {
                if (players.size() == 9)
                    break;
                if (d == null)
                    continue;
                distance = Math.ceil(d.getLocation().getDistance(opp.getLocation()));
                if (distance > 2)
                    continue;
                if (d.getCombat().isSafe(d))
                    continue;
                if (d.getUsername().equalsIgnoreCase(opp.getUsername())) {
                    continue;
                }
                if (d.getDisplayName().equalsIgnoreCase(p.getDisplayName()))
                    continue;
                int difference1 = Math.abs(p.getSkills().getCombatLevel() - d.getSkills().getCombatLevel());
                if ((difference1 > 15 || difference1 < -15)) {
                    continue;
                }
                players.add(d);
            }
            int hit3 = 0;
            try {
                for (final Player d : players) {
                    if (d == null)
                        continue;

                    if (d.isDead())
                        continue;
                    hit3 = p.getCombat().getMagicHit(p, d,
                            Misc.random((int) maxHit));
                    d.graphics(-1);
                    d.graphics2(-1);
                    if (hit3 != 0) {
                        p.getSkills().heal(hit / 4);
                        opp.getFrames().sendChatMessage(0, "You feel the life sucked out of you.");
                    }
                    final boolean sucessfulCast = hit3 != 0;
                    final int hit2 = hit3;
                    p.getSkills().sendCounter(hit2, true);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {
                            d.animate(CombatManager.getDefenceEmote(d));
                            if (sucessfulCast) {
                                GameLogicTaskManager.schedule(new GameLogicTask() {
                                    @Override
                                    public void run() {
                                        d.hit(hit2, p);
                                        if (opp.getCombat().vengeance) {
                                            if (hit2 > 0) {
                                                opp.getCombat().applyVengeance(
                                                        Math.floor(hit2 * 0.75), p, d);
                                            }
                                        }
                                    }
                                }, 0, 0);
                                d.graphics(377);
                            } else {
                                d.graphics2(85);
                            }
                            this.stop();
                        }
                    }, 2, 0);
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();

            }
            players.clear();
        }
    }

}
