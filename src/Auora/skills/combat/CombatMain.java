package Auora.skills.combat;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.Entity;
import Auora.model.player.Player;
import Auora.skills.combat.types.MeleeCombat;
import Auora.skills.combat.types.RangedCombat;

public class CombatMain {

    /*
     * Holds the instances of the Combat types
     */
    public final CombatType[] COMBAT_TYPES;
    /*
     * An array of ranged weapons
     */
    public final int[] RANGED_WEAPONS = {16873, 861, 9851, 4734, 4212, 13879, 13953};

    /*
     * Constructor Sets the combat types when executed
     */
    public CombatMain() {
        COMBAT_TYPES = new CombatType[3];
        COMBAT_TYPES[0] = new MeleeCombat();
        COMBAT_TYPES[1] = new RangedCombat();
    }

    /**
     * Starts attacking with the specified Entity instances
     *
     * @param p
     * @param opp
     */
    public void startAttack(Entity p, Entity opp) {
        if (p == null || opp == null) {
            return;
        }
        if (p instanceof Player) {
            if (opp instanceof Player) {
                startAttackPlayer((Player) p, (Player) opp);
                return;
            }
        }
    }

    /*
     * Starts the combat processing and attacking
     */
    public void startAttackPlayer(final Player p, final Player opp) {
        if (p.isAttacking()) {
            return;
        }
        p.setAttacking(true);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                if (p.getCombatDelay() > 0) {
                    p.setCombatDelay(p.getCombatDelay() - 1);
                }
                if (p.isAttacking()) {
                    executePlayerAttack(p, opp);
                }
                if (p.getCombatDelay() < 1) {
                    if (!p.isAttacking()) {
                        this.stop();
                    }
                }
            }
        }, 0, 600, 1);
    }

    /**
     * Gets the combat type and executes it.
     *
     * @param p
     * @param opp
     */
    public void executePlayerAttack(Player p, Player opp) {
        if (p == null || opp == null) {
            return;
        }
        if (p.getCombatDelay() > 0) {
            return;
        }
        COMBAT_TYPES[getCombatType(p)].execute(p, opp);
    }

    /*
     * Checks to see if the player is using a ranged weapon
     */
    public boolean usingRangedWeapon(Player p) {
        for (int i : RANGED_WEAPONS) {
            if (p.getEquipment().get(3).getId() == i) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks to see what combat type the player is using
     */
    public int getCombatType(Player p) {
        if (usingRangedWeapon(p)) {
            return 1;
        }
        return 0;
    }

}
