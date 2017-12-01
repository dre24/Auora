package Auora.skills.combat;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.CoordinateLocations;
import Auora.model.Entity;
import Auora.model.Hits.Hit;
import Auora.model.Item;
import Auora.model.World;
import Auora.model.minigames.FightPits;
import Auora.model.npc.Npc;
import Auora.model.player.ChatMessage;
import Auora.model.player.DuelArena.Rules;
import Auora.model.player.Equipment;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.model.player.content.BountyHunterManager;
import Auora.model.route.RouteFinder;
import Auora.model.route.strategy.EntityStrategy;
import Auora.model.route.strategy.FloorItemStrategy;
import Auora.util.CombatManager;
import Auora.util.Misc;
import Auora.util.ProjectileManager;
import Auora.util.RSTile;

import java.util.Random;

public class Combat {

    public final int[] RANGED_WEAPONS = {16873, 9177, 861, 10156, 9185, 18357, 15241, 11235, 4214, 4734, 13879};
    public boolean DFS = false;
    public int solSpecWait = 0;
    public boolean queuedSet;
    public int queuedSpellID;
    public int queuedSpellbookId;
    public int queuedPlayer;
    public int magicDelay;
    public int shieldDelay;
    public double BGSDrain;
    public double SWHDrain;
    public int[] dealtDamage = new int[2048];
    public boolean boltEffect = false;
    public boolean bowSpec = false;

    public boolean autoCasting = false;

    public int autoCastingPlayer, autoCastingSpellId, autoCastingSpellbookId, ConfigId = 0;
    public String Spell = "";
    public Player killer = null;
    public int combatWith;
    public int stunDelay;
    public int scimDelay = 0;
    public int combatWithDelay;
    public int leechDelay = 0;
    public int logoutDelay = 0;
    public int CombatDelay = 0;

    public int ddsDelay = 0;
    public int thievDelay = 0;
    public int DFSdelay = 0;
    public int freezeDelay;

    public int immuneDelay;
    public int delay;
    public boolean vengeance;
    public int vengDelay;

    public boolean soulSplitting = false;
    public CombatType[] COMBAT_TYPES;
    private Entity entity;
    private Entity target;
    private byte prayerBeforeHitDelay;
    private byte hitDelay;
    private byte prayerAfterHitDelay;
    private long lastAttackedTime;
    private boolean isProcessing;
    private CombatHitDefinitions combatHitDefinitions;

    public Combat(Entity entity) {
        this.entity = entity;
        prayerBeforeHitDelay = -1;
        hitDelay = 0;
        prayerAfterHitDelay = -1;
    }

    public static boolean donatorzone(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 3167 && absX <= 3181 && absY >= 9762 && absY <= 9772);
    }

    public int calculateMaxRangedHit() {
        int maxHit = 0;
        Player player = (Player) entity;

        double specialMultiplier = 1;
        double prayerMultiplier = 1;
        double otherBonusMultiplier = 1;

        int rangedStrength = (getRangedStrength(player) / 10);
        int rangeLevel = player.getSkills().getLevel(Skills.RANGE);
        int combatStyleBonus = 0;

        if (CombatManager.wearingVoid(player, 11664)) {
            otherBonusMultiplier = 1.35;
        }

        int effectiveRangeDamage = (int) ((rangeLevel * prayerMultiplier * otherBonusMultiplier) + combatStyleBonus);
        double baseDamage = 1.3 + (effectiveRangeDamage / 10) + (rangedStrength / 80)
                + ((effectiveRangeDamage * rangedStrength) / 640);

        maxHit = (int) (baseDamage * specialMultiplier);
        maxHit *= 12;
        return maxHit * 2;
    }

    public void stopAutoCasting() {
        autoCasting = false;
        getPlayer().getFrames().sendConfig(43, 0);
        if (this.autoCasting) {
            getPlayer().getFrames().sendConfig(439, 1);
        } else {
            getPlayer().getFrames().sendConfig(439, 0);
        }
        getPlayer().getFrames().sendConfig(108, 0);
        return;
    }

    public void setAutoCasting(int interfaceId, int spellId) {
        if (spellId == 33) {
            Spell = "Shadow Blitz";
            ConfigId = 81;
        } else if (spellId == 25) {
            Spell = "Blood Blitz";
            ConfigId = 83;
        } else if (spellId == 21) {
            Spell = "Ice Blitz";
            ConfigId = 85;
        } else if (spellId == 37) {
            Spell = "Miasmic Blitz";
            ConfigId = 99;
        } else if (spellId == 35) {
            Spell = "Shadow Barrage";
            ConfigId = 89;
        } else if (spellId == 27) {
            Spell = "Blood Barrage";
            ConfigId = 91;
        } else if (spellId == 23) {
            Spell = "Ice Barrage";
            ConfigId = 93;
        } else if (spellId == 39) {
            Spell = "Miasmic Barrage";
            ConfigId = 101;
        } else if (spellId == 84) {
            Spell = "Wind Surge";
            ConfigId = 47;
        } else if (spellId == 87) {
            Spell = "Water Surge";
            ConfigId = 49;
        } else if (spellId == 89) {
            Spell = "Earth Surge";
            ConfigId = 51;
        } else if (spellId == 91) {
            Spell = "Fire Surge";
            ConfigId = 53;
        }
        if (autoCasting && autoCastingSpellId == spellId) {
            autoCasting = false;
            getPlayer().getFrames().sendConfig(43, 0);
            if (interfaceId == 193) {
                getPlayer().getFrames().sendConfig(439, 1);
            } else {
                getPlayer().getFrames().sendConfig(439, 0);
            }
            getPlayer().getFrames().sendConfig(108, 0);
            return;
        }
        this.autoCastingSpellId = spellId;
        this.autoCastingSpellbookId = interfaceId;
        this.autoCasting = true;
        getPlayer().getFrames().sendConfig(43, 4);
        if (interfaceId == 193) {
            getPlayer().getFrames().sendConfig(439, 1);
        } else {
            getPlayer().getFrames().sendConfig(439, 0);
        }
        getPlayer().getFrames().sendConfig(108, ConfigId);
        //you're not even using this...
    }

    public int getRangedStrength(Player p) {
        p.getEquipment();
        short arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
        short weapon = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();

        switch (weapon) {

            case 13879:
                return 145;
            case 4214:
                return 70;
            case 806:
                return 1;
            case 807:
                return 3;
            case 808:
                return 4;
            case 809:
                return 7;
            case 810:
                return 10;
            case 811:
                return 14;
        }
        switch (arrows) {
            case 13879:
                return 145;
            case 15243:
                return 180;
            case 11212:
                return 60;
            case 11217:
                return 60;
            case 11222:
                return 60;
            case 9243:
                return 105;

            case 9244:
                return 117;
            case 9144:
                return 115;
            case 9143:
                return 100;
            case 9142:
                return 82;
            case 4740:
                return 55;
            case 882:
                return 7;
            case 884:
                return 10;
            case 886:
                return 16;
            case 888:
                return 22;
            case 890:
                return 31;
            case 892:
                return 49;
        }
        return -1;
    }

    /*
     * Returns the arrows allowed to be used by the bow
	 */
    public int[] arrowsAllowed(int bow) {
        switch (bow) {
            case 839:
            case 841:
                return new int[]{882, 884};
            case 843:
            case 845:
                return new int[]{882, 884, 886};
            case 847:
            case 849:
                return new int[]{882, 884, 886, 888};
            case 851:
            case 853:
                return new int[]{882, 884, 886, 888, 890};
            case 859:
            case 861:
            case 16873:
                return new int[]{882, 884, 886, 888, 890, 892};
            case 4212:
                return new int[]{882, 884, 886, 888, 890, 892};
            case 9177:
            case 9185:
            case 10156:
            case 18357:
                return new int[]{9243, 9244};
            case 11235:
                return new int[]{882, 884, 886, 888, 890, 892, 11212, 11217, 11222};
            case 4734:
                return new int[]{4740};
            case 15241:
                return new int[]{15243};
           /* case 13879:
                return new int[]{0};*/
        }
        return null;
    }

    /*
     * Checks to see if the player can use the arrows with the bow
	 */
    public boolean canUseArrows(Player p) {
        int wep = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        int arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
        int[] allowed = arrowsAllowed(wep);
        for (int i : allowed)
            if (i == arrows)
                return true;
        return false;
    }

    public boolean checkArrows(Player p) {
        int wep = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        int arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
        if (wep >= 4210 && wep <= 4225 || wep >= 13879 && wep <= 13883) {
            return true;
        }
        if (arrows < 1 && arrows > 19710 || p.getEquipment().get(13) == null) {
            p.getFrames().sendChatMessage(0, "You don't have any arrows equipt to use this bow/cannon.");
            return false;
        }
        if (!canUseArrows(p)) {
            p.getFrames().sendChatMessage(0, "You can't use these arrows in your bow/cannon.");
            return false;
        }
        return true;
    }

    public void leechStats(final Player p, final Player opp) {
        boolean don = false;
        if (leechDelay > 0)
            return;
        if (opp == null || opp.getSkills().playerDead)
            return;
        // atk = 2231
        // range = 2236 defence 2244 mage = 2248
        boolean leechingAttack = p.getPrayer().usingPrayer(1, 1) || p.getPrayer().usingPrayer(1, 10);
        boolean leechingStrength = p.getPrayer().usingPrayer(1, 14);
        boolean leechingDef = p.getPrayer().usingPrayer(1, 13);
        boolean leechingRange = p.getPrayer().usingPrayer(1, 11);
        boolean leechingMagic = p.getPrayer().usingPrayer(1, 12);
        if (leechingAttack) {
            if (this.shieldDelay < 1) {
                p.animate(12575);
            }
            ProjectileManager.sendGlobalProjectile(p, opp, 2231, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(0) > opp.getSkills().getLevelForXp(0)) {
                        double minLevel = opp.getSkills().getLevelForXp(0) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(0, opp.getSkills().getLevel(0) - 1);
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has drained your attack level!");
                        } else {
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has no effect on your attack level.");
                        }
                    }

                    opp.graphics(2232);
                    ProjectileManager.sendGlobalProjectile(opp, p, 2231, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(0) * 1.25;
                            if (p.getSkills().getLevel(0) >= maxLevel) {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse has no effect on your attack as it is too high.");
                            } else {
                                p.getFrames().sendChatMessage(0, "Your curse boosts your attack.");
                                p.getSkills().set(0, p.getSkills().getLevel(0) + 1);
                            }

                            p.graphics(2233);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        if (leechingStrength) {
            if (this.shieldDelay < 1) {
                p.animate(12575);
            }
            ProjectileManager.sendGlobalProjectile(p, opp, 2231, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(2) > opp.getSkills().getLevelForXp(2)) {
                        double minLevel = opp.getSkills().getLevelForXp(2) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(2, opp.getSkills().getLevel(2) - 1);
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has drained your strength level!");
                        } else {
                            opp.getFrames().sendChatMessage(0,
                                    "The enemy's curse has no effect on your strength level.");
                        }
                    }

                    opp.graphics(2232);
                    ProjectileManager.sendGlobalProjectile(opp, p, 2231, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(2) * 1.25;
                            if (p.getSkills().getLevel(2) >= maxLevel) {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse has no effect on your strength as it is too high.");
                            } else {
                                p.getFrames().sendChatMessage(0, "Your curse boosts your strength.");
                                p.getSkills().set(2, p.getSkills().getLevel(2) + 1);
                            }

                            p.graphics(2233);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        if (leechingMagic) {
            if (this.shieldDelay < 1) {
                p.animate(12575);
            }
            ProjectileManager.sendGlobalProjectile(p, opp, 2240, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(6) > opp.getSkills().getLevelForXp(6)) {
                        double minLevel = opp.getSkills().getLevelForXp(6) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(6, opp.getSkills().getLevel(6) - 1);
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has drained your magic level!");
                        } else {
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has no effect on your magic level.");
                        }
                    }

                    opp.graphics(2242);
                    ProjectileManager.sendGlobalProjectile(opp, p, 2240, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(6) * 1.25;
                            if (p.getSkills().getLevel(6) >= maxLevel) {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse has no effect on your magic as it is too high.");
                            } else {
                                p.getFrames().sendChatMessage(0, "Your curse boosts your magic.");
                                p.getSkills().set(6, p.getSkills().getLevel(6) + 1);
                            }

                            p.graphics(2241);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        if (leechingRange) {
            if (this.shieldDelay < 1) {
                p.animate(12575);
            }
            ProjectileManager.sendGlobalProjectile(p, opp, 2236, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(4) > opp.getSkills().getLevelForXp(4)) {
                        double minLevel = opp.getSkills().getLevelForXp(4) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(4, opp.getSkills().getLevel(4) - 1);
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has drained your range level!");
                        } else {
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has no effect on your range level.");
                        }
                    }

                    opp.graphics(2238);
                    ProjectileManager.sendGlobalProjectile(opp, p, 2236, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(4) * 1.25;
                            if (p.getSkills().getLevel(4) >= maxLevel) {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse has no effect on your range as it is too high.");
                            } else {
                                p.getFrames().sendChatMessage(0, "Your curse boosts your range.");
                                p.getSkills().set(4, p.getSkills().getLevel(4) + 1);
                            }

                            p.graphics(2237);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        if (leechingDef) {
            if (this.shieldDelay < 1) {
                p.animate(12575);
            }
            ProjectileManager.sendGlobalProjectile(p, opp, 2244, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(1) > opp.getSkills().getLevelForXp(1)) {
                        double minLevel = opp.getSkills().getLevelForXp(1) * 0.80;
                        if (!(opp.getSkills().getLevel(1) < minLevel)) {
                            opp.getSkills().set(1, opp.getSkills().getLevel(1) - 1);
                            opp.getFrames().sendChatMessage(0, "The enemy's curse has drained your strength level!");
                        } else {
                            opp.getFrames().sendChatMessage(0,
                                    "The enemy's curse has no effect on your strength level.");
                        }
                    }

                    opp.graphics(2245);
                    ProjectileManager.sendGlobalProjectile(opp, p, 2244, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(1) * 1.25;
                            if (p.getSkills().getLevel(1) >= maxLevel) {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse has no effect on your strength as it is too high.");
                            } else {
                                p.getFrames().sendChatMessage(0, "Your curse boosts your defence.");
                                p.getSkills().set(1, p.getSkills().getLevel(1) + 1);
                            }

                            p.graphics(2246);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
    }

    public void resetDamage() {
        for (int i = 0; i < dealtDamage.length; i++) {
            dealtDamage[i] = -1;
        }
    }

    public void addDamage(int damage, int slot) {
        dealtDamage[slot] += damage;
    }

    public boolean inDangerousPVP(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 2919 && absX <= 3029 && absY >= 3307 && absY <= 3438)
                || (absX >= 3029 && absX <= 3068 && absY >= 3329 && absY <= 3385)
                || (absX >= 3093 && absX <= 3117 && absY >= 3919 && absY <= 3946)
                || (absX >= 2940 && absX <= 3395 && absY >= 3520 && absY <= 4000)
                || (absX >= 2816 && absX <= 2884 && absY >= 2940 && absY <= 3008)
                || (absX >= 3048 && absX <= 3135 && absY >= 3468 && absY <= 3522)
                // inside battlegrounds
                || (absX >= 2350 && absX <= 2434 && absY >= 3070 && absY <= 3143)
                // CAMELOT PVP
                || (absX >= 2735 && absX <= 2780 && absY >= 3456 && absY <= 3482)
                // Red Portal
                || (absX >= 2948 && absX <= 3067 && absY >= 5512 && absY <= 5628)
                || (absX >= 2690 && absX <= 2748 && absY >= 5570 && absY <= 5629);

    }

    public boolean duelArena(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 3303 && absX <= 3400 && absY >= 3195 && absY <= 3285);
    }

    public boolean duelFight(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        return (absX >= 3331 && absX <= 3390 && absY >= 3205 && absY <= 3259);
    }

    public int getKiller() {
        int killer = -1;
        int killerDamage = -1;
        for (int i = 0; i < dealtDamage.length; i++) {
            if (dealtDamage[i] > killerDamage) {
                killer = i;
            }
        }
        return killer;
    }

    public boolean Multi(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 3136 && absX <= 3327 && absY >= 3520 && absY <= 3607)
                || (absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839)

				/*
				 * shilo half multi || (absX >= 2817 && absX <= 2881 && absY >=
				 * 2943 && absY <= 2969)
				 */
                // edge multi
                || (absX >= 3048 && absX <= 3138 && absY >= 3448 && absY <= 3522)
                // end
                || (absX >= 3095 && absX <= 3314 && absY >= 3583 && absY <= 3708)
                || (absX >= 2951 && absX <= 2976 && absY >= 3362 && absY <= 3391)
                || (absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967)
                || (absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560)
                || (absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967)
                || (absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831)
                || (absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903)
                || (absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711)
                || (absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647)
                // battlegrounds
                || ((absX >= 2350 && absX <= 2434 && absY >= 3070 && absY <= 3143))
                // purple portal
                || (absX >= 2690 && absX <= 2748 && absY >= 5570 && absY <= 5629);
    }

    public boolean mageArena(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        return (absX >= 3093 && absX <= 3117 && absY >= 3919 && absY <= 3946);
    }

    public boolean westsArena(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        return (absX >= 2951 && absX <= 3004 && absY >= 3576 && absY <= 3625);
    }

    public boolean homesafepk(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        // player.getFrames().sendClickableInterface(381);
        return (absX >= 3173 && absX <= 3200 && absY >= 3445 && absY < 3464);
    }

    public boolean isSafe(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        // player.getFrames().sendClickableInterface(381);
        if (absX >= 3093 && absX <= 3117 && absY >= 3919 && absY <= 3946) {
            return false;
        }
        if (!FightPits.playersInPits.contains(p) && absX >= 2390 && absY >= 5170 && absX <= 2450 && absY <= 5184)
            return true;
        if (FightPits.inFightPitsLobby(p))
            return true;
        return (absX >= 3201 && absX <= 3227 && absY >= 2301 && absY <= 3235)

                // start ;;bh!
                || (absX >= 3140 && absX <= 3146 && absY >= 3653 && absY <= 3662)
                || (absX >= 3146 && absX <= 3150 && absY >= 3656 && absY <= 3667)
                || (absX >= 3150 && absX <= 3152 && absY >= 3658 && absY <= 3672)
                || (absX >= 3152 && absX <= 3158 && absY >= 3659 && absY <= 3682)
                || (absX >= 3158 && absX <= 3163 && absY >= 3668 && absY <= 3686)
                || (absX >= 3163 && absX <= 3168 && absY >= 3668 && absY <= 3688)
                || (absX >= 3168 && absX <= 3176 && absY >= 3672 && absY <= 3693)
                || (absX >= 3176 && absX <= 3179 && absY >= 3675 && absY <= 3695)
                || (absX >= 3179 && absX <= 3183 && absY >= 3673 && absY <= 3699)
                || (absX >= 3183 && absX <= 3192 && absY >= 3669 && absY <= 3703)
                || (absX >= 3193 && absX <= 3195 && absY >= 3684 && absY <= 3697)
                //guild
                || (absX >= 3157 && absX <= 3166 && absY >= 9750 && absY <= 9785)
                || (absX >= 2904 && absX <= 2919 && absY >= 5464 && absY <= 5479) //staffzone
                || (absX >= 2366 && absX <= 2393 && absY >= 9480 && absY <= 9498) //lobby

                || (absX >= 3080 && absX <= 3128 && absY >= 3912 && absY <= 3955)
                // falador pvp fountin crash fix (currently)
                || (absX >= 3035 && absX <= 3042 && absY >= 3350 && absY <= 3357)
                //drezone
                || (absX >= 2800 && absX <= 2880 && absY >= 3325 && absY <= 3390)
                // Thiev safe
                || (absX >= 3273 && absX <= 3320 && absY >= 2752 && absY <= 2809)
                // fally bank safe spot
                || (absX >= 3019 && absX <= 3021 && absY >= 3353 && absY <= 3356)

                || (absX >= 3320 && absX <= 3400 && absY >= 3200 && absY <= 3280)// duel

                // arena
                //Tourney Zone																			// arena
                || (absX >= 2281 && absX <= 2288 && absY >= 4686 && absY <= 4697)

                || (absX >= 3349 && absX <= 3390 && absY >= 3262 && absY <= 3280)// duel
                // arena
                // Bank in shilo
                || (absX >= 2843 && absX <= 2861 && absY >= 2953 && absY <= 2955)
                || (absX >= 2851 && absX <= 2853 && absY >= 2951 && absY <= 2952) || (absX == 2845 && absY == 2952)
                || (absX == 2860 && absY == 2952) || (absX == 2850 && absY == 2952) || (absX == 2854 && absY == 2956)
                || (absX == 2853 && absY == 2957) || (absX == 2852 && absY == 2957) || (absX == 2850 && absY == 2953)
                || (absX == 2851 && absY == 2952) || (absX == 2854 && absY == 2952) || (absX == 2844 && absY == 2952)
                || (absX == 2844 && absY == 2956) || (absX == 2845 && absY == 2956) || (absX == 2850 && absY == 2956)
                || (absX == 2852 && absY == 2956) || (absX == 2851 && absY == 2956) || (absX == 2859 && absY == 2956)
                || (absX == 2860 && absY == 2952) || (absX == 2860 && absY == 2956) || (absX == 2851 && absY == 2957)
                || (absX == 2853 && absY == 2956) || (absX == 2853 && absY == 2951)
                // entrance to ladder to slayer master.
                || (absX >= 2869 && absX <= 2872 && absY >= 2966 && absY <= 2972) || (absX == 2868 && absY == 2967)
                || (absX == 2873 && absY == 2968)
                // Shilo general store
                || (absX >= 2824 && absX <= 2825 && absY >= 2956 && absY <= 2961) || (absX == 2823 && absY == 2960)
                || (absX == 2823 && absY == 2959) || (absX == 2823 && absY == 2958) || (absX == 2823 && absY == 2957)
                || (absX == 2826 && absY == 2960) || (absX == 2826 && absY == 2959) || (absX == 2826 && absY == 2958)
                || (absX == 2826 && absY == 2957)
                // PvP safe main area - Quest.
                || (absX >= 2834 && absX <= 2837 && absY >= 2980 && absY <= 2993) || (absX == 2838 && absY == 2981)
                || (absX == 2839 && absY == 2982) || (absX == 2839 && absY == 2983) || (absX == 2839 && absY == 2984)
                || (absX == 2838 && absY == 2985) || (absX == 2833 && absY == 2982) || (absX == 2833 && absY == 2983)
                || (absX == 2833 && absY == 2984) || (absX == 2838 && absY == 2982) || (absX == 2838 && absY == 2983)
                || (absX == 2838 && absY == 2984) || (absX == 2853 && absY == 2957) || (absX == 2833 && absY == 2985)
                || (absX == 2832 && absY == 2984) || (absX == 2832 && absY == 2983) || (absX == 2832 && absY == 2982)
                || (absX == 2833 && absY == 2981) || (absX == 2833 && absY == 2990) || (absX == 2833 && absY == 2991)
                || (absX == 2833 && absY == 2992) || (absX == 2826 && absY == 2957) || (absX == 2838 && absY == 2992)
                || (absX == 2838 && absY == 2991) || (absX == 2838 && absY == 2990)
                // Red Portal
                || (absX >= 2947 && absX <= 3068 && absY >= 5507 && absY <= 5511)
                // Dicezone
                // || (absX >= 3310 && absX <= 3329 && absY >= 3222 && absY <= 3260)
                // New dicezone
                || (absX >= 3340 && absX <= 3388 && absY >= 9615 && absY <= 9663)
                // Adminzone
                || (absX >= 3592 && absX <= 3640 && absY >= 5384 && absY <= 5440)
                // Chillzone
                || (absX >= 2450 && absX <= 2480 && absY >= 4765 && absY <= 4795)
                // Mining
                || (absX >= 2964 && absX <= 3090 && absY >= 9700 && absY <= 9880)
                // Chill zone
                || (absX >= 1879 && absX <= 1893 && absY >= 5015 && absY <= 5026)
                // white portal
                || (absX >= 2755 && absX <= 2876 && absY >= 5507 && absY <= 5511)
                // Done
                // edgeville
                || (absX >= 3077 && absX <= 3131 && absY >= 3485 && absY <= 3518)
                // draynor
                || (absX >= 3088 && absX <= 3097 && absY >= 3240 && absY <= 3246)
                // ardrougne
                || (absX >= 2649 && absX <= 2658 && absY >= 3280 && absY <= 3287) // bank
                // 1
                // Varrock east bank
                || (absX >= 3250 && absX <= 3257 && absY >= 3416 && absY <= 3243)
                // market
                || (absX >= 3042 && absX <= 3064 && absY >= 3470 && absY <= 3520)
                // Canafis
                || (absX >= 3463 && absX <= 3522 && absY >= 3468 && absY <= 3515)
                // Mining Dungeon
                //|| (absX >= 2750 && absX <= 2803 && absY >= 4824 && absY <= 9360)
                // Staffzone
                || (absX >= 1921 && absX <= 1934 && absY >= 4993 && absY <= 5006)
                // Altars
                || (absX >= 2830 && absX <= 2850 && absY >= 4820 && absY <= 4843) // AIR
                || (absX >= 2750 && absX <= 2805 && absY >= 4820 && absY <= 4860) // MIND
                || (absX >= 2380 && absX <= 2420 && absY >= 4832 && absY <= 4870) // NATURE
                || (absX >= 2638 && absX <= 2683 && absY >= 4816 && absY <= 4857) // EARTH
                || (absX >= 2569 && absX <= 2602 && absY >= 4822 && absY <= 4854) // FIRE
                || (absX >= 2505 && absX <= 2538 && absY >= 4827 && absY <= 4855) // BODY
                || (absX >= 2120 && absX <= 2170 && absY >= 4811 && absY <= 4870) // COSMIC
                || (absX >= 2250 && absX <= 2300 && absY >= 4824 && absY <= 4880) // CHAOS
                || (absX >= 2444 && absX <= 2480 && absY >= 4810 && absY <= 4860) // LAW
                || (absX >= 2193 && absX <= 2219 && absY >= 4821 && absY <= 4850) // DEATH
                // Abyss
                || (absX >= 3020 && absX <= 3061 && absY >= 4815 && absY <= 4851)
                // Battlegrounds
                || (absX >= 2436 && absX <= 2450 && absY >= 3080 && absY <= 3100)
                || (absX >= 3091 && absX <= 3098 && absY >= 3488 && absY <= 3499)
                || (absX >= 3147 && absX <= 3182 && absY >= 3473 && absY <= 3505) || (absX == 0 && absY == 0)
                || (absX >= 2053 && absX <= 2081 && absY >= 4369 && absY <= 4391)
                || (absX >= 2839 && absX <= 2853 && absY >= 5205 && absY <= 5224)
                || (absX >= 3227 && absX <= 3320 && absY >= 2752 && absY <= 2789)
                || (absX >= 2529 && absX <= 2549 && absY >= 4708 && absY <= 4725)// magebank
                || (absX >= 3264 && absX <= 3279 && absY >= 3672 && absY <= 3695)
                || (absX >= 2943 && absX <= 2950 && absY >= 3368 && absY <= 3373)
                || (absX >= 3264 && absX <= 2950 && absY >= 3672 && absY <= 3373)
                || (absX >= 3009 && absX <= 3018 && absY >= 3353 && absY <= 3358)
                || (absX >= 3179 && absX <= 3194 && absY >= 3432 && absY <= 3446)
                || (absX >= 2606 && absX <= 2616 && absY >= 3088 && absY <= 3097)
                || (absX >= 2670 && absX <= 2430 && absY >= 4670 && absY <= 4736)
                || (absX >= 2531 && absX <= 2446 && absY >= 3131 && absY <= 3042)
                // Zaros
                || (absX >= 2441 && absX <= 2533 && absY >= 3034 && absY <= 3117)
                || (absX >= 2367 && absX <= 2431 && absY >= 4667 && absY <= 4750)
                // Donatorzone safebits
                || (absX >= 2442 && absX <= 2444 && absY >= 5518 && absY <= 5526)
                || (absX >= 2442 && absX <= 2444 && absY >= 5529 && absY <= 5537)
                // Essence Mine
                || (absX >= 2885 && absX <= 2936 && absY >= 4802 && absY <= 4859)
                // varrock
                //|| (absX >= 3171 && absX <= 3272 && absY >= 3382 && absY <= 3446)
                // Karajama
                || (absX >= 2815 && absX <= 2980 && absY >= 3152 && absY <= 3180)
                // Catherby
                || (absX >= 2787 && absX <= 2885 && absY >= 3407 && absY <= 3476)
                // Fishing Guild
                || (absX >= 2575 && absX <= 2626 && absY >= 3383 && absY <= 3460)
                // Camelot Safe Pvp
                || (absX >= 2743 && absX <= 2777 && absY >= 3482 && absY <= 3517)

                // ditch safezones
                || (absX >= 2945 && absX <= 3331 && absY >= 3517 && absY <= 3524)

                //edge surroundings
                || (absX >= 3029 && absX <= 3116 && absY >= 3461 && absY <= 3520);
    }

    public boolean miningGuild(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 3016 && absX <= 3055 && absY >= 9732 && absY <= 9756);
    }

    public boolean inWild(Player p) {
        //p.getFrames().sendClickableInterface(591);
        //p.getBountyHunter().sendInterfaces();

        //BountyHunterManager.init();
        if (!BountyHunterManager.handlingPlayer(p))
            BountyHunterManager.addHandledPlayer(p);


        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 2940 && absX <= 3395 && absY >= 3525 && absY <= 4000);
    }

    public boolean inNoProtectionPVP(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        // player.getFrames().sendClickableInterface(381);
        return (absX >= 2940 && absX <= 3395 && absY >= 3525 && absY <= 4000);
    }

    public void setQueueMagic(int opp, int book, int id) {
        this.queuedPlayer = opp;
        this.queuedSpellID = id;
        this.queuedSpellbookId = book;
        this.queuedSet = true;
    }

    public void tick() {
        if (thievDelay > 0) {
            thievDelay--;
        }
        if (scimDelay > 0) {
            scimDelay--;
        }
        if (stunDelay > 0) {
            stunDelay--;
        }
        if (DFSdelay > 0) {
            DFSdelay--;
        }
        if (solSpecWait > 0) {
            solSpecWait--;
        }
        if (leechDelay > 0) {
            leechDelay--;
        }
        if (logoutDelay > 0) {
            logoutDelay--;
        }
        if (CombatDelay > 0) {
            CombatDelay--;
        }
        if (ddsDelay > 0) {
            leechDelay--;
        }
        if (DFSdelay == 0) {
            DFS = true;
            DFSdelay = -1;
        }
        if (leechDelay == 0) {
            leechStats((Player) entity, (Player) target);
            leechDelay = 12;
        }
        if (combatWithDelay > 0) {
            combatWithDelay--;

        }
        if (combatWithDelay == 0) {
            combatWith = -1;
        }
        if (vengDelay > 0) {
            vengDelay--;
        }
        if (shieldDelay > 0) {
            shieldDelay--;
        }
        if (freezeDelay > 0) {
            freezeDelay--;
        }
        if (immuneDelay > 0) {
            immuneDelay--;
        }
        if (delay > 0) {
            delay--;
        }
        if (magicDelay > 0) {
            magicDelay--;
        }
        if (queuedSet) {
            attemptCastSpell();
        } else if (target != null && autoCasting) {
            autoCastingPlayer = target.getIndex();
            attemptCastSpell();
        }
    }

    public void autoCast(Player opp) {
        //redundant code  thats wrong actually...
        if (getPlayer() != null) {
            if (opp != null) {
                this.target = opp;
                if (getPlayer().getCombat().delay > 0 || getPlayer().getCombat().magicDelay > 0) {
                    return;
                }
                if (getPlayer().getSkills().isDead() || opp.getSkills().isDead()) {
                    getPlayer().getCombat().removeTarget();
                    return;
                }
            } else {
                getPlayer().getCombat().removeTarget();
            }
        }
        if (!Multi(getPlayer()) || !Multi(opp)) {
            if (combatWith != opp.getClientIndex() && combatWith > 0) {
                getPlayer().getFrames().sendChatMessage(0, "I'm already under attack.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
            if (opp.getCombat().combatWith != getPlayer().getClientIndex() && opp.getCombat().combatWith > 0) {
                getPlayer().getFrames().sendChatMessage(0, "This player is already in combat.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
        }
        opp.getCombat().combatWith = getPlayer().getClientIndex();
        opp.getCombat().combatWithDelay = 12;
        castSpell(opp, getPlayer().autoCastSpell);
        getPlayer().getCombat().delay = 4;
        magicDelay = 6;
    }

    public void removeDiag() {
        Player attacked = (Player) target;
        final Player attacker = (Player) entity;
        final Entity target = this.target;
        RSTile location = locMod();
        int k = target.getLocation().getX() - (attacker.getLocation().getRegionX() - 6) * 8;
        int l = attacker.getLocation().getY() - (attacker.getLocation().getRegionY() - 6) * 8;

        int aX = attacker.getLocation().getX();
        int aY = attacker.getLocation().getY();
        int tX = target.getLocation().getX();
        int tY = target.getLocation().getY();
        // attacker.resetTurnTo();
        attacker.getWalk().reset(false);
		/* attacker.getCombat().reset */
        // System.out.println(aX+" "+tX);
        // System.out.println(aY+" "+tY);
        if (aX > tX) {
            k = k - 1;
            // System.out.println("1");
        } else if (aY > tY) {
            k = k + 1;
            // System.out.println("2");
        } else if (aX < tX) {
            k = k + 1;
            // System.out.println("3");
        } else if (aY < tY) {
            l = l + 1;
            // System.out.println("4");
        } else {
            k = k - 1;
            // System.out.println("5");
        }
        attacker.getWalk().addToWalkingQueueFollow(k, l);

    }

    private boolean isDiagonal() {

        Player attacked = (Player) target;
        final Player attacker = (Player) entity;
        final Entity target = this.target;
        return attacked.getLocation().getX() - 1 == attacker.getLocation().getX()
                && target.getLocation().getY() == attacker.getLocation().getY()
                && attacked.getLocation().getY() + 1 == attacker.getLocation().getY()
                || attacker.getLocation().getX() - 1 == attacked.getLocation().getX()
                && attacker.getLocation().getY() + 1 == attacked.getLocation().getY()
                || attacked.getLocation().getX() + 1 == attacker.getLocation().getX()
                && attacked.getLocation().getY() - 1 == attacker.getLocation().getY()
                || attacker.getLocation().getX() + 1 == attacked.getLocation().getX()
                && attacker.getLocation().getY() - 1 == attacked.getLocation().getY()
                || attacked.getLocation().getX() + 1 == attacker.getLocation().getX()
                && attacked.getLocation().getY() + 1 == attacker.getLocation().getY()
                || attacker.getLocation().getX() + 1 == attacked.getLocation().getX()
                && attacker.getLocation().getY() + 1 == attacked.getLocation().getY();
    }

    public void attemptCastSpell() {
        Player p = (Player) entity;

        Player opp = (autoCasting && !queuedSet) ? World.getPlayers().get(autoCastingPlayer)
                : World.getPlayers().get(queuedPlayer);

        String ip = "" + opp.getConnection().getChannel().getRemoteAddress();
        ip = ip.replaceAll("/", "");
        ip = ip.replaceAll(" ", "");
        ip = ip.substring(0, ip.indexOf(":"));
        if (getPlayer().getCombat().delay > 0 || getPlayer().getCombat().magicDelay > 0) {
            return;
        }
        if (!p.getLocation().withinDistance(opp.getLocation(), 25)) {
            this.removeTarget();
            queuedSet = false;
            return;
        }
        if (getPlayer().getCombat().stunDelay > 0) {
            return;
        }
        if (p.isDead() || opp.isDead()) {
            return;
        }
        if (getPlayer() == null || opp == null) {
            return;
        }

        if (onTop(p, opp) && p.getCombat().freezeDelay > 0) {
            return;
        }

        if (onTop(p, opp) && p.getCombat().freezeDelay == 0) {
            RSTile newPos = RSTile.createRSTile(opp.getLocation().getX() + 1, opp.getLocation().getY());
            p.getWalk().walkTo(new FloorItemStrategy(newPos), true);
            RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, opp.getLocation().getX() + 1, opp.getLocation().getY(), opp.getLocation().getZ(), 1, new FloorItemStrategy(newPos), true);

        }


        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.MAGIC)) {
            p.getFrames().sendChatMessage(0, "You cannot use Magic during this duel.");
            p.getCombat().removeTarget();
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.MAGIC)) {
            p.getFrames().sendChatMessage(0, "You cannot use Magic during this duel.");
            p.getCombat().removeTarget();
            return; // wtf what happened to what i added?
        }
        if (isSafe(getPlayer()) || isSafe(opp)) {
            getPlayer().getFrames().sendChatMessage(0, "You can't attack this player in the safezone.");
            p.turnTo(opp);
            this.removeTarget();
            queuedSet = false;
            return;
        }
        int difference = Math.abs(getPlayer().getSkills().getCombatLevel() - opp.getSkills().getCombatLevel());
        if (difference > 15 || difference < -15) {
            getPlayer().getFrames().sendChatMessage(0, "You can't attack this player at that combat level.");
            this.removeTarget();
            queuedSet = false;
            return;
        }
        if (!Multi(getPlayer()) || !Multi(opp)) {
            if (combatWith != opp.getClientIndex() && combatWith > 0) {
                getPlayer().getFrames().sendChatMessage(0, "I'm already under attack.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
            if (opp.getCombat().combatWith != getPlayer().getClientIndex() && opp.getCombat().combatWith > 0) {
                getPlayer().getFrames().sendChatMessage(0, "This player is already in combat.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
        }
        leechStats(getPlayer(), opp);
        this.target = (Entity) opp;
        opp.getCombat().combatWith = getPlayer().getClientIndex();
        opp.getCombat().combatWithDelay = 12;
        getPlayer().getWalk().reset(true);
        if (getPlayer().autocasting) {
            castSpell();
        }

        castSpell();


        if (queuedPlayer != -1) {
            entity.turnTemporarilyTo((Entity) ((autoCasting && !queuedSet) ? World.getPlayers().get(autoCastingPlayer)
                    : World.getPlayers().get(queuedPlayer)));
        }
        if (!getPlayer().getCombat().autoCasting && !queuedSet) {
            getPlayer().getCombat().removeTarget();
            return;
        }
        this.queuedSet = false;
        getPlayer().getCombat().delay = 4;
        magicDelay = 6;
    }

    public Player getPlayer() {
        return (Player) entity;
    }

    public void castSpell() {
        if (!autoCasting) {
            this.entity.turnTo(this.target);
        }


        Player opp = (autoCasting && !queuedSet) ? World.getPlayers().get(autoCastingPlayer)
                : World.getPlayers().get(queuedPlayer);
        int spell = (autoCasting && !queuedSet) ? autoCastingSpellId : queuedSpellID;
        int book = (autoCasting && !queuedSet) ? autoCastingSpellbookId : queuedSpellbookId;
        // opp.getFrames().sendChatMessage(0, ""+spell+"");
        switch (book) {
            case 192: // moderns
                switch (spell) {
                    case 81:
                        MagicManager.executeSpell(getPlayer(), opp, "Entangle");
                        break;
                    case 86:
                        MagicManager.executeSpell(getPlayer(), opp, "TeleBlock");
                        break;
                    case 84:
                        MagicManager.executeSpell(getPlayer(), opp, "WindSurge");
                        break;
                    case 87:
                        MagicManager.executeSpell(getPlayer(), opp, "WaterSurge");
                        break;
                    case 89:
                        MagicManager.executeSpell(getPlayer(), opp, "EarthSurge");
                        break;
                    case 91:
                        MagicManager.executeSpell(getPlayer(), opp, "FireSurge");
                        break;
                }
                break;

            case 193: // Ancients
                switch (spell) {
                    case 33:
                        MagicManager.executeSpell(getPlayer(), opp, "ShadowBlitz");
                        break;
                    case 35:
                        MagicManager.executeSpell(getPlayer(), opp, "ShadowBarrage");
                        break;
                    case 21:
                        MagicManager.executeSpell(getPlayer(), opp, "IceBlitz");
                        break;
                    case 23:
                        MagicManager.executeSpell(getPlayer(), opp, "IceBarrage");
                        break;
                    case 25:
                        MagicManager.executeSpell(getPlayer(), opp, "BloodBlitz");
                        break;
                    case 27:
                        MagicManager.executeSpell(getPlayer(), opp, "BloodBarrage");
                        break;
                    case 37:
                        MagicManager.executeSpell(getPlayer(), opp, "MiasmicBlitz");
                        break;
                    case 39:
                        MagicManager.executeSpell(getPlayer(), opp, "MiasmicBarrage");
                        break;
                }
        }
    }

    public int calculateMax() {
        Player p = (Player) entity;
        int weaponId = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        byte attackStyle = p.getCombatDefinitions().getAttackStyle();
        final boolean specialOn = p.getCombatDefinitions().isSpecialOn();
        final boolean isRanging = false;
        if (!isRanging) {
            int StrengthLvl = p.getSkills().getLevel(Skills.STRENGTH);
            double PrayerBonus = 1;
            if (p.getPrayer().usingPrayer(0, 1))
                PrayerBonus = 1.05;
            else if (p.getPrayer().usingPrayer(0, 6))
                PrayerBonus = 1.1;
            else if (p.getPrayer().usingPrayer(0, 14))
                PrayerBonus = 1.15;
            else if (p.getPrayer().usingPrayer(0, 25))
                PrayerBonus = 1.18;
            else if (p.getPrayer().usingPrayer(0, 26))
                PrayerBonus = 1.23;
            else if (p.getPrayer().usingPrayer(1, 19))
                PrayerBonus = (p.getPrayer().usingBoost(8) && target instanceof Player)
                        ? 1.26 + (((Player) target).getSkills().getLevelForXp(Skills.STRENGTH) / 1000) : 1.23;
            double OtherBonus = 1;
            int StyleBonus = 0;
            if (attackStyle == 0)
                StyleBonus = 0;
            else if (attackStyle == 2)
                StyleBonus = 1;
            else if (attackStyle == 3)
                StyleBonus = 3;
            double EffectiveStrenght = Math.round(StrengthLvl * PrayerBonus * OtherBonus) + StyleBonus;
            int StrengthBonus = p.getCombatDefinitions().getBonus()[11];
            double BaseDamage = 15 + EffectiveStrenght + (StrengthBonus / 8) + (EffectiveStrenght * StrengthBonus / 64);
            double finaldamage;
            if (specialOn)
                finaldamage = Math.floor(BaseDamage * CombatManager.getSpecDamageDoublePercentage(weaponId));
            else
                finaldamage = Math.round(BaseDamage);
            if (CombatManager.wearingDharok(p))
                finaldamage += ((p.getSkills().getLevelForXp(3) * 8) - p.getSkills().getHitPoints()) / 2;
            if (target != null) {
                // Sol stuff - Canownueasy
                if (target instanceof Player) {
                    Player defender = (Player) target;
                    Item wep = defender.getEquipment().get(Equipment.SLOT_WEAPON);
                    if (wep != null) {
                        if (wep.getId() == 15486 && target.getCombat().solSpecWait > 0) {
                            finaldamage = (finaldamage / 2);
                            target.graphics(2320);
                        }
                    }
                }
            }
            return (int) finaldamage;
        }
        return 0;

    }

    public Entity getTarget() {
        return target;
    }

    public boolean targetAvailable() {
        return target != null;
    }

    public double getMagicAccuracy(Player p, Player opp) {
        final double A = 0.705;
        double atkBonus = p.getCombatDefinitions().bonus[3];
        double defBonus = (opp.getCombatDefinitions().bonus[3 + 5]);
        if (atkBonus < 1) {
            atkBonus = 0;
        }
        if (defBonus < 1) {
            defBonus = 0;
        }
        double atk = ((atkBonus * 1.1) * p.getSkills().level[6]);
        double def = (defBonus * opp.getSkills().level[1]);
        if (opp.getPrayer().usingPrayer(0, 0)) {
            def *= 1.05;
        }
        if (opp.getPrayer().usingPrayer(0, 5)) {
            def *= 1.10;
        }
        if (opp.getPrayer().usingPrayer(0, 13)) {
            def *= 1.15;
        }
        if (opp.getPrayer().usingPrayer(0, 25)) {
            def *= 1.20;
        }
        if (opp.getPrayer().usingPrayer(0, 27)) {
            def *= 1.25;
        }
        if (p.getPrayer().usingPrayer(0, 4)) {
            atk *= 1.05;
        }
        if (p.getPrayer().usingPrayer(0, 12)) {
            atk *= 1.10;
        }
        if (p.getPrayer().usingPrayer(0, 21)) {
            atk *= 1.15;
        }
        if (CombatManager.wearingVoid(p, 11663)) {
            atk *= 1.15;
        }
        double OUTCOME = A * (atk / def);
        return OUTCOME;
    }

    public int getMagicHit(Player p, Player opp, int hit) {
        double accuracy = getMagicAccuracy(p, opp);
        Random random = new Random();
        int hitBefore = hit;
        p.bonusMagicDamage = opp.magicResist * 2;
        p.target = opp.getUsername().replace("_", " ").toLowerCase();
        if (p != null || opp != null) {
            if (p.getEquipment().contains(18346)) {
                if (!opp.getCombat().isSafe(opp)) {
                    if (p.tomeTimer == 0) {
                        if (opp.magicResist >= 100) {
                            p.getFrames().sendChatMessage(0,
                                    "You have reached your max bonus damage for magic at " + p.bonusMagicDamage + ".");
                        } else {
                            opp.magicResist += 5;
                            p.getFrames().sendChatMessage(0,
                                    "You reduce your opponents magic resist by " + opp.magicResist + "%.");
                            p.getFrames().sendChatMessage(0,
                                    "Your magic damage is increased by " + p.bonusMagicDamage + ".");
                            opp.getFrames().sendChatMessage(0,
                                    "Your magic resist has been reduced by " + opp.magicResist + "%.");
                        }
                        p.tomeTimer = 50;
                        p.animate(400);
                        opp.animate(399);
                    }
                }
            }
        }
        if (opp.getPrayer().usingPrayer(1, 7)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics(2228);
            p.hit(Misc.random(100));
        }


        hit = hitBefore;
        if (opp.getEquipment().contains(13742)) {
            if (Misc.random(10) <= 7) {
                hit *= 0.75;
                opp.graphics(2000);
            }
        }

        if (opp.getEquipment().contains(13740)) {
            int prayerLost = (int) Math.ceil(hit * .06);
            if (opp.getSkills().level[5] >= prayerLost) {
                opp.getSkills().level[5] -= prayerLost;
                opp.getFrames().sendSkillLevel(5);
                hit *= 0.7;
            } else
                opp.getSkills().level[5] = 0;
        }

        if (opp.magicResist > 90) {
            accuracy = 1;
        }
        if (accuracy > 1.0) {
            accuracy = 1;
        }
        if (hit < 0) {
            hit = 0;
        }
        if (accuracy < random.nextDouble()) {
            return 0;
        } else {
            return hit;
        }
    }

    public void removeTarget() {
        entity.getPlayer().isFollowing = false;
        entity.getPlayer().getMask().setTurnToIndex(-1);
        entity.getPlayer().getMask().setTurnToReset(true);
        entity.getPlayer().getMask().setTurnToUpdate(true);
        queuedPlayer = -1;
        queuedSet = false;
        autoCastingPlayer = -1;
        target = null;
        entity.resetTurnTo();
    }

    public void clear() {
        prayerBeforeHitDelay = -1;
        hitDelay = -1;
        prayerAfterHitDelay = -1;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public void processDelays() {
        if (hitDelay > 0) {
            hitDelay--;
        }
        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (prayerBeforeHitDelay > 0)
                prayerBeforeHitDelay--;
            if (prayerAfterHitDelay > 0)
                prayerAfterHitDelay--;
        }
    }

    public void targetEquals() {
        Player opp = (Player) target;
        Player p = (Player) entity;
        if (target == null) {
            p.getFrames().sendChatMessage(0, "Target name was unreachable.");
            return;
        }
        p.getFrames().sendChatMessage(0, "You have been defeated by " + opp.getUsername() + ".");
    }

    private RSTile locMod() {
        int i = Misc.random(3);
        RSTile loc = null;
        if (i == 0) {
            loc = RSTile.createRSTile(entity.getLocation().getX() - 1, entity.getLocation().getY(), 0);
        } else if (i == 1) {
            loc = RSTile.createRSTile(entity.getLocation().getX() + 1, entity.getLocation().getY(), 0);
        } else if (i == 2) {
            loc = RSTile.createRSTile(entity.getLocation().getX(), entity.getLocation().getY() + 1, 0);
        } else if (i == 3) {
            loc = RSTile.createRSTile(entity.getLocation().getX(), entity.getLocation().getY() - 1, 0);
        }
        return loc;
    }
    /*
     * An array of ranged weapons
     */
   

    /*
     * Constructor Sets the combat types when executed
     */

    public void attack(Entity t) {
        if (entity.isDead()) {
            return;
        }
        this.target = t;
        this.entity.turnTo(this.target);
        if (autoCasting) {
            autoCastingPlayer = target.getIndex();
            getPlayer().getWalk().reset(true);
            attemptCastSpell();
        }
        if (target.isDead())
            return;
        Player p = (Player) entity;
        Player pp = (Player) target;
       /* isProcessing = true;
        getPlayer().getWalk().reset(true);  */
        if (pp.autoRetaliate && !pp.getCombat().hasTarget() && p.getCombat().isProcessing) {
            pp.getCombat().attack(p);
        }

        if (!isProcessing) {
            isProcessing = true;
            getPlayer().getWalk().reset(true);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                long time;

                @Override
                public void run() {
                    processDelays();
                    processAttack();
                    if (entity instanceof Player)
                        processPrayerBeforeHit();
                    processHit();
                    if (entity instanceof Player)
                        processPrayerAfterHit();
                    if (delay == 0 && hitDelay == 0 && prayerBeforeHitDelay == -1 && prayerAfterHitDelay == -1
                            && target == null) {
                        this.stop();
                        isProcessing = false;
                    }
                    time = System.currentTimeMillis();
                }

            }, 0, 0, 1);
        }

    }


    @SuppressWarnings("static-access")
    public void soulSplit(final Player p, final Player opp, final int hit) {
        if (soulSplitting) {
            return;
        }
        if (hit < 1) {
            return;
        }
        Skills.killer = opp;
        if (!p.getPrayer().usingPrayer(1, 18)) {
            return;
        }
        soulSplitting = true;
        try {
            ProjectileManager.sendGlobalProjectile(p, opp, 2263, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {
                    opp.graphics(2264);
                    ProjectileManager.sendGlobalProjectile(opp, p, 2263, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {
                            p.getSkills().heal((int) Math.floor(hit / 5));
                            p.getSkills().RestorePray(-1);
                            this.stop();
                        }
                    }, 1, 1);
                    soulSplitting = false;
                    this.stop();
                }
            }, 1, 1);
        } catch (Exception ignored) {
            ignored.printStackTrace();

        }

    }

    public int getMaxHit(Player p) {
        int weaponId = p.getEquipment().get(3) == null ? -1 : p.getEquipment().get(3).getId();
        int max = getPlayerMaxHit(0, weaponId, CombatManager.isRangingWeapon(weaponId), p.getCombatDefinitions().isSpecialOn());
        return max;
    }


    private void processAttack() {

        if (target == null || autoCasting) {
            return;
        }

        Player opp = (Player) target;

        opp.getPoison().makePoisoned(48);


        if (target == null) {
            entity.resetTurnTo();
            return;
        }
        Player ptarget = null;
        Npc ntarget = null;
        if (target instanceof Player) {
            ptarget = ((Player) target);
        } else {
            ntarget = ((Npc) target);
        }
        if ((ptarget != null && (!ptarget.isOnline()) || target.isDead())) {
            entity.resetTurnTo();
            removeTarget();
            return;
        }
        if (!entity.getLocation().withinDistance(target.getLocation(), 25)) {
            entity.resetTurnTo();
            removeTarget();
            return;
        }
        if (ptarget != null && (ptarget.isDead()
                || System.currentTimeMillis() < (ptarget.getCombatDefinitions().getLastEmote() - 600))) {
            entity.resetTurnTo();
            removeTarget();
            return;
        }

        int distance = (int) Math.round(entity.getLocation().getDistance(target.getLocation()));
        int entityDistance = ptarget == null ? (byte) ((Npc) target).getNpcdefinition().size : 1;

        if (entity instanceof Player) {
            final Player p = (Player) entity;
            //dres fix to null -> incase of issues
            if (p == null)
                return;
            if (getPlayer() == null) {
                return;
            }
            if ((Player) target == null) {
                return;
            }
            if (p.isDead() || System.currentTimeMillis() < (p.getCombatDefinitions().getLastEmote() - 600)) {
                removeTarget();
                entity.resetTurnTo();
                return;
            }
            if (p.isMorphed) {
                return;
            }
            if (p.tabTimer > 0) {
                return;
            }
            boolean walk = true;
            if (((Player) entity).autocasting)
                walk = false;
            if (rangedWeapon((Player) entity))
                walk = false;
            if (distance > 8)
                walk = true;

            if (walk) {
                if ((distance == 0 || distance > entityDistance && !rangedWeapon(((Player) entity))) || (distance == 0 || distance > 8 && rangedWeapon((Player) entity))) {
                    if (this.freezeDelay > 0) {
                        return;
                    }
                    entity.getWalk().reset(true);
                    ;
                    if (target == null) {
                        removeTarget();
                        return;
                    }
                    entity.getWalk().addToWalkingQueueFollow(target.getLocation().getX() - (entity.getLocation().getRegionX() - 6) * 8, target.getLocation().getY() - (entity.getLocation().getRegionY() - 6) * 8);
                    if ((entity.getWalk().getRunDir() == -1 || distance > 2)
                            && (entity.getWalk().getWalkDir() == -1 || distance > 1))
                        return;
                }
            }


            if (delay > 0 || magicDelay > 1)
                return;

            //another change for npc combat
            int difference = getPlayer().getSkills().getCombatLevel() - ((Player) target).getSkills().getCombatLevel();

            if (inWild(p)) {
                if (difference > CoordinateLocations.getWildernessLevel(p, (int) p.getLocation().getX(), (int) p.getLocation().getY()) || difference < -(CoordinateLocations.getWildernessLevel(p, (int) p.getLocation().getX(), (int) p.getLocation().getY()))) {

                    getPlayer().getFrames().sendChatMessage(0,
                            "The difference between your Combat Level and the Combat Level of your opponent is");
                    getPlayer().getFrames().sendChatMessage(0, "too great.");
                    this.removeTarget();
                    queuedSet = false;
                    return;


                }
            }


            if (p.getCombat().stunDelay > 0) {
                getPlayer().getFrames().sendChatMessage(0,
                        "You are stunned");
                this.removeTarget();
                queuedSet = false;
                return;
            }

            int weaponId = p.getEquipment().get(3) == null ? -1 : p.getEquipment().get(3).getId();
            if (weaponId != 0)
                entityDistance += CombatManager.distForWeap(weaponId);


            if (weaponId == 4153 && p.getCombatDefinitions().isSpecialOn())
                delay = -1;
            if (weaponId == -1) {
                p.sm("Boxing has been disabled until the duel arena is fully working.");
                p.getCombat().removeTarget();
            }

            if (entity != null) {
                if (((distance > entityDistance && !rangedWeapon((Player) entity) && !(p.getEquipment().get(3).getId() == 13879))
                        || (distance > 8 && rangedWeapon((Player) entity))
                        || (onTop(entity, target) && !target.getWalk().isMoving())) && weaponId != -1) {


                    if (this.freezeDelay > 0) {
                        return;
                    }
                    if (!p.autocasting) {
                        p.getWalk().reset(true);
                        p.isWalking = true;
                    }
                    p.getWalk().walkTo(new EntityStrategy(target), true);

                    if ((entity.getWalk().getRunDir() == -1 || distance > 2)
                            && (entity.getWalk().getWalkDir() == -1 || distance > 1) && (!entity.getLocation().equals(target.getLocation().getX(), target.getLocation().getY()))) {
                        return;
                    }
                    if (entity.getLocation().equals(target.getLocation().getX(), target.getLocation().getY())) {
                        return;
                    }
                    if (distance > 2) {
                        return;
                    }


                } else {
                    p.getWalk().reset(true);
                }
            }

            if (p != null || opp != null) {
                if (p.getEquipment().contains(18346)) {
                    if (p.tomeTimer == 0) {
                        if (opp.magicResist >= 100) {
                            p.getFrames().sendChatMessage(0,
                                    "You have reached your max bonus damage for magic at " + p.bonusMagicDamage + ".");
                        } else {
                            opp.magicResist += 5;
                            p.getFrames().sendChatMessage(0,
                                    "You reduce your opponents magic resist by " + opp.magicResist + "%.");
                            p.getFrames().sendChatMessage(0,
                                    "Your magic damage is increased by " + p.bonusMagicDamage + ".");
                            opp.getFrames().sendChatMessage(0,
                                    "Your magic resist has been reduced by " + opp.magicResist + "%.");
                        }
                        p.tomeTimer = 50;
                        p.animate(400);
                        opp.animate(399);
                    }
                }
            }
            if (p != null || opp != null) {
                if (p.DFSSpecial && p.getEquipment().contains(11284)) {
                    if (p.getCombat().isSafe(opp) || p.getCombat().isSafe(p) || opp.getCombat().isSafe(opp)
                            || opp.getCombat().isSafe(p)) {
                        return;
                    }
                    if (target.getCombat().combatWith != getPlayer().getClientIndex()
                            && target.getCombat().combatWith > 0) {
                        getPlayer().getFrames().sendChatMessage(0, "This player is already in combat.");
                        this.removeTarget();
                        queuedSet = false;
                        return;
                    }

                    if (p.dfsHit == 0) {
                        p.animate(6696, 0);
                        p.graphics(1165, 0);
                        this.DFSdelay = 2;
                        p.dfsHit = 60;
                    } else {
                        p.getFrames().sendChatMessage(0,
                                "You will need to wait " + p.dfsHit + " seconds to use this again.");
                    }
                }
            }
            p.DFSSpecial = false;
            if (p != null || opp != null) {
                if (DFS && p.getEquipment().contains(11284)) {
                    if (p.getCombat().isSafe(opp) || p.getCombat().isSafe(p) || opp.getCombat().isSafe(opp)
                            || opp.getCombat().isSafe(p)) {
                        return;
                    }
                    if (target.getCombat().combatWith != getPlayer().getClientIndex()
                            && target.getCombat().combatWith > 0) {
                        getPlayer().getFrames().sendChatMessage(0, "This player is already in combat.");
                        this.removeTarget();
                        queuedSet = false;
                        return;
                    }
                    // int weaponID = player.getEquipment().get(3).getId();
                    int max = getPlayerMaxHit(0, 4151, CombatManager.isRangingWeapon(4151), false);
                    p.graphics(1167, 100);
                    ProjectileManager.sendGlobalProjectile(p, target, 1166, 46, 31, 60, 55, 0);
                    p.getCombat().appendMeleeDamage(p, opp, Misc.random(300), true);
                    p.dfsHit = 60;
                    DFS = false;
                }
            }
            if (delay > 0)
                return;

            if (entity instanceof Player)
                processPlayer();
        }
    }

    public boolean rangedWeapon(Player p) {
        for (int RANGED_WEAPON : RANGED_WEAPONS) {
            if (p.getEquipment().contains(RANGED_WEAPON)) {
                return true;
            }
        }
        return false;
    }

    public boolean canSpec(Player p, int weapon) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        if (p.getDonatorRights().ordinal() > 0 && absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560) {
            return true;
        }
        int specNeeded = 0;
        int specAmount = p.getCombatDefinitions().getSpecpercentage();
        switch (weapon) {
            case 11696:
            case 11730:
            case 7158:
                specNeeded = 100;
                if (p.getEquipment().contains(19669)) {
                    specNeeded = 90;
                }
                break;
            case 13477:
                specNeeded = 0;
                break;
            case 11235:
            case 15701:
            case 15702:
            case 15703:
            case 15704:
            case 4587:
            case 11700:
            case 18786:
                specNeeded = 60;
                if (p.getEquipment().contains(19669)) {
                    specNeeded = 54;
                }
                break;
            case 3204:
                specNeeded = 30;
                if (p.getEquipment().contains(19669)) {
                    specNeeded = 27;
                }
                break;
            case 14484:
            case 15241:
            case 11694:
            case 13902:
            case 11698:
            case 4153:
            case 13905:

            case 13883:
            case 4151:
            case 5670:
            case 15444:
            case 13954:// morrigan javelin
            case 12955:
            case 13956:
            case 13879:
            case 13880:
            case 13881:
            case 13882:
            case 15441:
            case 15442:
            case 15443:
            case 10887:
                specNeeded = 50;
                if (p.getEquipment().contains(19669)) {
                    specNeeded = 45;
                }
                break;
            case 11061:
                specNeeded = 50;// 50 ?Ye? okay tesssst
                break;
            case 5680:
                specNeeded = 50;// 50 ?Ye? okay tesssst
                break;
            case 13899:
            case 1215:
            case 5698:
            case 1305:
            case 1434:
            case 1249:
                specNeeded = 25;
                if (p.getEquipment().contains(19669)) {
                    specNeeded = 22;
                }
                break;

        }

        if (specNeeded <= specAmount) {
            p.getCombatDefinitions().specpercentage -= specNeeded;
            p.getCombatDefinitions().setSpecialOff();
            p.getCombatDefinitions().refreshSpecial();
            return true;
        }
        return false;
    }

    public void specialAttack(final Player p, Player opp) {
        int weaponID = p.getEquipment().get(3).getId();

        if (rangedWeapon(p)) {

            if (!this.checkArrows(p)) {
                p.getCombatDefinitions().setSpecialOff();
                p.getCombatDefinitions().refreshSpecial();
                this.removeTarget();
                return;
            }
            if (!this.checkArrows(p)) {
                p.getCombatDefinitions().setSpecialOff();
                p.getCombatDefinitions().refreshSpecial();
                this.removeTarget();
                return;
            }
            int arrows = p.getEquipment().getEquipment().get(13).getId();
            if (p.getEquipment().getEquipment().contains(new Item(11235))) {
                p.getEquipment().deleteArrow(arrows, 2);

            } else {
                // player.getEquipment().getEquipment().remove(new Item(arrows, 13));
                p.getEquipment().deleteArrow(arrows, 1);
            }
        }
        if (!canSpec(p, weaponID)) {
            p.getCombatDefinitions().setSpecialOff();
            p.getFrames().sendChatMessage(0, "You don't have enough power left.");
            return;
        }
        int maxHit = getPlayerMaxHit(0, weaponID, CombatManager.isRangingWeapon(weaponID), false);
        int hit = Misc.random(450);
        int distanceDelay = getDelayDistance(p, opp);
        switch (weaponID) {
            case 15241: // 2143 projectile handcannon
                if (this.mageArena(p)) {
                    p.getFrames().sendChatMessage(0, "You can't use range in this arena.");
                    return;
                }
                hit = Misc.random(this.calculateMaxRangedHit());
                p.animate(12175);
                p.graphics(2138);
                ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31, 35, 45, 23, 0);
                appendRangeDamage(p, opp, hit, distanceDelay);
                this.delay = 2;
                break;
            case 11235:
                if (this.mageArena(p)) {
                    p.getFrames().sendChatMessage(0, "You can't use range in this arena.");
                    return;
                }
                p.animate(426);
                p.graphics2(1111);
                ProjectileManager.sendGlobalProjectile(entity, target, 1099, 42, 35, 45, 23, 0);
                ProjectileManager.sendGlobalProjectile(entity, target, 1099, 42, 35, 55, 23, 0);

                int max = this.calculateMaxRangedHit();
                bowSpec = true;
                // appendDBowSpec(p, opp, hit, distanceDelay, 1100);
                appendRangeDamage(p, opp, hit, distanceDelay);
                int afterBoost = max + (max / 2) + 5;
                hit = Misc.random(afterBoost);
                // appendDBowSpec(p, opp, hit, distanceDelay + 1, 1100);

                appendRangeDamage(p, opp, hit, distanceDelay + 1);
                break;

            case 1249:

                p.animate(1064);
                p.graphics(253);
                opp.graphics(254);
                 /*maxHit *= 1.20;
                 appendMeleeDamage(p, opp, Misc.random(maxHit), true);*/
                opp.getCombat().stunDelay = 5;
                opp.getFrames().sendChatMessage(0, "You cannot move for 3 seconds!");
                if (opp.getLocation().getX() > p.getLocation().getX()) {
                    RSTile newPos = RSTile.createRSTile(opp.getLocation().getX() + 1, opp.getLocation().getY());
                    opp.getWalk().walkTo(new FloorItemStrategy(newPos), true);
                    RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, opp.getLocation().getX(), opp.getLocation().getY(), opp.getLocation().getZ(), 1, new FloorItemStrategy(newPos), false);
                } else if (opp.getLocation().getX() < p.getLocation().getX()) {
                    RSTile newPos = RSTile.createRSTile(opp.getLocation().getX() - 1, opp.getLocation().getY());
                    opp.getWalk().walkTo(new FloorItemStrategy(newPos), true);
                    RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, opp.getLocation().getX(), opp.getLocation().getY(), opp.getLocation().getZ(), 1, new FloorItemStrategy(newPos), false);

                } else if (opp.getLocation().getY() > p.getLocation().getY()) {
                    RSTile newPos = RSTile.createRSTile(opp.getLocation().getX(), opp.getLocation().getY() + 1);
                    opp.getWalk().walkTo(new FloorItemStrategy(newPos), true);
                    RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, opp.getLocation().getX(), opp.getLocation().getY(), opp.getLocation().getZ(), 1, new FloorItemStrategy(newPos), false);

                } else {
                    RSTile newPos = RSTile.createRSTile(opp.getLocation().getX(), opp.getLocation().getY() - 1);
                    opp.getWalk().walkTo(new FloorItemStrategy(newPos), true);
                    RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, opp.getLocation().getX(), opp.getLocation().getY(), opp.getLocation().getZ(), 1, new FloorItemStrategy(newPos), false);
                }


                break;// this is it? yes.


            case 13954:// morrigan javelin
            case 12955:
            case 13956:
            case 13880:
            case 13881:
            case 13882:
            case 13879:
                p.animate(10501);
                p.graphics2(1836);
                hit = Misc.random(this.calculateMaxRangedHit());

                ProjectileManager.sendGlobalProjectile(entity, target, 1837, 31, 35, 45, 23, 0);
                appendRangeDamage(p, opp, hit, distanceDelay);
                //this.delay = 2;
                break;

            case 14484:
                maxHit *= 1.2;
                if (p.getUsername().equals("jet_kai") || p.getUsername().equals("tester_kai")) {
                    p.animate(352);
                    p.graphics(-1);
                    int calcedHit = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    int hit2 = calcedHit / 2;
                    int hit3 = hit2 / 2;
                    int hit4 = hit3 + 10;
                    boolean failed = false;
                    if (calcedHit == 0) {
                        hit2 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit3 = hit2 / 2;
                        hit4 = hit3 + 10;
                    }
                    if (hit2 == 0) {
                        hit3 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit4 = hit3 + 10;
                    }
                    if (hit3 == 0) {
                        hit4 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    }
                    if (hit4 == 0) {
                        failed = true;
                    }
                    if (hit2 + hit3 + hit4 == 0) {
                        hit2 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit3 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit4 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    }
                    failed = hit2 + hit3 + hit4 > 470;
                    if (failed && !CombatManager.wearingVoid(p, 11665)) {
                        appendMeleeDamageNoDef(p, opp, 0, true);
                        appendMeleeDamageNoDef(p, opp, 10, true);
                        p.getSkills().sendCounter(10, false);
                    } else {
                        appendMeleeDamageNoDef(p, opp, calcedHit, true);
                        appendMeleeDamageNoDef(p, opp, hit2, true);
                        appendMeleeDamageNoDef(p, opp, hit3, true);
                        appendMeleeDamageNoDef(p, opp, hit4, true);
                        double gained = 0;
                        gained = hit2 + hit3 + hit4;
                        p.getSkills().sendCounter((int) gained, false);
                    }
                } else {
                    p.animate(10961);
                    p.graphics(1950);
                    int calcedHit = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    int hit2 = calcedHit / 2;
                    int hit3 = hit2 / 2;
                    int hit4 = hit3 + 10;
                    boolean failed = false;
                    if (calcedHit == 0) {
                        hit2 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit3 = hit2 / 2;
                        hit4 = hit3 + 10;
                    }
                    if (hit2 == 0) {
                        hit3 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit4 = hit3 + 10;
                    }
                    if (hit3 == 0) {
                        hit4 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    }
                    if (hit4 == 0) {
                        failed = true;
                    }
                    if (failed) {
                        appendMeleeDamageNoDef(p, opp, 0, true);
                        appendMeleeDamageNoDef(p, opp, 10, true);
                    } else {
                        appendMeleeDamageNoDef(p, opp, calcedHit, true);
                        appendMeleeDamageNoDef(p, opp, hit2, true);
                        appendMeleeDamageNoDef(p, opp, hit3, true);
                        appendMeleeDamageNoDef(p, opp, hit4, true);
                    }
                    double gained = 0;
                    gained = hit2 + hit3 + hit4;
                    p.getSkills().sendCounter((int) gained, false);
                }
                break;
            case 13899:
                p.animate(10502);
                maxHit *= 1.20;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;// this is it? yes.
            case 11061:

                p.animate(6147);
                p.graphics(1027);
                maxHit *= 1.40;


                int calcedHitMace = getMeleeHit(p, opp, Misc.random(maxHit), true);
                appendMeleeDamage(p, opp, calcedHitMace, true);


                p.getSkills().RestorePray(calcedHitMace / 10);


                opp.getSkills().drainPray(calcedHitMace / 10);


                break;
            case 11694:
                if (hit > 700) {
                    p.animate(7074);
                    p.graphics(1222);
                    maxHit *= 1.33;
                    //maxHit *= 1.33;
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                    return;
                }
                p.animate(7074);
                p.graphics(1222);
                //maxHit *= 1.33;
                maxHit *= 1.33;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 11698:
                maxHit *= 1.25;
                p.animate(7071);
                p.graphics(1220);
                p.getSkills().heal(Misc.random(maxHit) / 2);
                p.getSkills().RestorePray(Misc.random(maxHit) / 4);
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 1305:
                p.animate(1058);
                p.graphics2(248);
                maxHit *= 1.25;
                this.appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 1434:
                p.animate(1060);
                p.graphics2(251);
                maxHit *= 1.20;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 4151:
            case 5670:
                p.animate(11956);
                opp.graphics2(341);
                opp.getCombat().freezeDelay = 5;
                opp.getFrames().sendChatMessage(0, "You cannot move for 3 seconds!");
                maxHit *= 1.05;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 3204:
                p.animate(440);
                p.graphics2(282);
                maxHit *= .90;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);

                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 15444:
            case 15441:
            case 15442:
            case 15443:
                p.animate(11956);
                opp.graphics2(2108);
                opp.getCombat().freezeDelay = 8;
                opp.getFrames().sendChatMessage(0, "You cannot move for 5 seconds!");
                maxHit *= 1.0;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 10887:
                p.animate(5870);
                p.graphics2(1027);
                maxHit *= 1.35;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 7158:
                p.animate(3157);
                p.graphics2(1225);
                maxHit *= 1.25;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 4587:

                maxHit *= 1.2;
                if (hit == 0) {
                    p.animate(1872);
                    p.graphics2(347);
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                    p.getFrames().sendChatMessage(0, "Your special failed.");

                } else {


                    if (opp.getPrayer().getPrayerBook() == 0) {
                        p.animate(1872);
                        p.graphics2(347);
                        appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                        opp.getCombat().scimDelay = 6;
                        opp.getPrayer().closeAllPrayers();
                        opp.getPrayer().closePrayers(opp.getPrayer().closePrayers[opp.getPrayer().getPrayerBook()][7],
                                opp.getPrayer().closePrayers[opp.getPrayer().getPrayerBook()][8]

                        );
                    } else {
                        p.animate(1872);
                        p.graphics2(347);
                        appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                        opp.getCombat().scimDelay = 6;
                        opp.getPrayer().closeAllPrayers();
                        opp.getPrayer().closePrayers(opp.getPrayer().closePrayers[opp.getPrayer().getPrayerBook()][3],
                                opp.getPrayer().closePrayers[opp.getPrayer().getPrayerBook()][4]

                        );
                    }


                }


                break;
            case 4153:
                p.animate(1667);
                p.graphics2(340);
                maxHit *= 1.05;
                delay = -1;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 13902:// im sure it's different like i mean ye different im sure
                maxHit *= 1.30;
                SWHDrain = Misc.random(maxHit) * 0.03;
                p.animate(10505);
                p.graphics(1840);
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                if (SWHDrain < opp.getSkills().getLevel(Skills.DEFENCE)) {
                    opp.getSkills().set(Skills.DEFENCE, (int) (opp.getSkills().getLevel(Skills.DEFENCE) - SWHDrain));
                } else {
                    opp.getSkills().set(Skills.DEFENCE, 0);
                }
                break;
            case 11696:
                maxHit *= 1.20;
                p.animate(7073);
                p.graphics(1223);
                BGSDrain = Misc.random(maxHit) * 0.01;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                if (BGSDrain < opp.getSkills().getLevel(Skills.DEFENCE)) {
                    opp.getSkills().set(Skills.DEFENCE, (int) (opp.getSkills().getLevel(Skills.DEFENCE) - BGSDrain));
                } else {
                    BGSDrain = BGSDrain - opp.getSkills().getLevel(Skills.DEFENCE);
                    opp.getSkills().set(Skills.DEFENCE, 0);
                    opp.getSkills().set(Skills.STRENGTH, (int) (opp.getSkills().getLevel(Skills.STRENGTH) - BGSDrain));
                }
                break;
            case 11700:
                maxHit *= 1.20;
                if (hit == 0) {
                    p.animate(7070);
                    opp.graphics(2112);
                    p.getFrames().sendChatMessage(0, "Your special failed.");
                    return;
                }
                p.animate(7070);
                opp.graphics(2111);
                //opp.getCombat().freezeDelay = 40;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 18786:
                p.animate(1872);
                opp.graphics(1224);
                double minimumHit = maxHit * 0.3;
                double maximumHit = maxHit * 1.38;
                int totalDamage = Misc.random((int) minimumHit, (int) maximumHit);
                appendKorasiDamageNoDef(p, opp, totalDamage, true);
                break;
            case 13905:
                maxHit *= 1.00;
                p.animate(10499);
                p.graphics2(1835);
                if (hit < 300) {
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                } else {
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                    p.graphics(1740);
                    opp.graphics(1739);

                }
                break;
            case 1215:
            case 5698:
                p.animate(0x426);
                p.graphics2(252);
                maxHit *= 1.15;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);

                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 5680:
                p.animate(0x426);
                p.graphics2(252);
                maxHit *= 1.30;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);

                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
        }
        //System.out.println("Max hit: "+maxHit);
    }

    public void castSpell(Player opp, String spellName) {


        if (opp != null) {
        	 /* if (opp.getLocation().equals(opp.getLocation().getX(), opp.getLocation().getY())) {
        		  getPlayer().getWalk().walkTo(new EntityStrategy(opp), true);
        	  }*/
            getPlayer().turnTemporarilyTo(opp);
            getPlayer().getMask().setApperanceUpdate(true);
            MagicManager.executeSpell(getPlayer(), opp, spellName);
            getPlayer().getCombat().removeTarget();
            getPlayer().getWalk().reset(true);
        } else {
            getPlayer().getCombat().removeTarget();
        }

    }

    private void processPlayer() {
        try {
            final Player p = (Player) entity;
            final byte attackStyle = p.getCombatDefinitions().getAttackStyle();
            final int weaponId = p.getEquipment().get(3) == null ? -1 : p.getEquipment().get(3).getId();
            final int ammoId = p.getEquipment().get(13) == null ? -1 : p.getEquipment().get(13).getId();
            int specAmt = CombatManager.getSpecAmt(weaponId);
            final boolean specialOn = p.getCombatDefinitions().isSpecialOn()
                    && p.getCombatDefinitions().getSpecpercentage() >= specAmt;
            int maxHit = getPlayerMaxHit(attackStyle, weaponId, CombatManager.isRangingWeapon(weaponId), specialOn);
            int speed = CombatManager.getSpeedForWeapon(weaponId);
            String weaponName = new Item(weaponId).getDefinition().name.toLowerCase();

            if (p.getCombatDefinitions().getAttackStyle() == 2) {
                if (weaponName.contains("shortbow") || weaponName.contains("twisted bow")) {
                    speed -= 1;
                } else if (weaponName.contains("dart")) {
                    speed -= 1;
                } else if (weaponName.contains("knife")) {
                    speed -= 1;
                } else if (weaponName.contains("longbow")) {
                    speed -= 1;
                } else if (weaponName.contains("crossbow")) {
                    speed -= 1;

                } else if (weaponName.contains("javelin")) {
                    speed -= 1;
                }
            }

            if (p.getEquipment().contains(16909) && p.primalTimer > 0) {
                speed = 3;
            } else if (p.getEquipment().contains(16909) && p.primalTimer == 0) {
                speed = 5;
            } else if (p.getEquipment().contains(4734)) {
                speed = 5;
            }
            int distance = Math.round(entity.getLocation().getDistance(target.getLocation())) > 3 ? 2 : 1;
            if (target.getLocation().equals(entity.getLocation())) {
                entity.getWalk().addStepToWalkingQueue(target.getLocation().getX() + 1, target.getLocation().getY());
            } // dre

            if (getPlayer().isInDuelArena) {
                if (getPlayer().getDuelPartner() != null) {
                    if (getPlayer().getDuelPartner().getDuelSession().getOpponent(getPlayer()) != target) {
                        getPlayer().getFrames().sendChatMessage(0, "This is not your fight.");
                        removeTarget();
                        queuedSet = false;
                        return;
                    }
                } else if (getPlayer().getDuelSession() != null) {
                    if (getPlayer().getDuelSession().getOpponent(getPlayer()) != target) {
                        getPlayer().getFrames().sendChatMessage(0, "This is not your fight.");
                        removeTarget();
                        queuedSet = false;
                        return;
                    }
                }
            } else if (!getPlayer().isInDuelArena) {
                if (lastAttackedTime + 10000 < System.currentTimeMillis() && (isSafe(p) || isSafe((Player) target))) {
                    p.getFrames().sendChatMessage(0,
                            "You need to be in either a PvP zone or in the wilderness to attack them.");
                    this.removeTarget();
                    return;
                }
                if (!Multi(getPlayer()) || !Multi((Player) target)) {
                    if (combatWith != target.getClientIndex() && combatWith > 0) {
                        getPlayer().getFrames().sendChatMessage(0, "I'm already under attack.");
                        this.removeTarget();
                        queuedSet = false;
                        return;
                    }
                    if (target.getCombat().combatWith != getPlayer().getClientIndex()
                            && target.getCombat().combatWith > 0) {
                        getPlayer().getFrames().sendChatMessage(0, "This player is already in combat.");
                        this.removeTarget();
                        queuedSet = false;
                        return;
                    }
                }

            }


            if (getPlayer().duelTimer) {
                getPlayer().getFrames().sendMessage("Please wait until your duel starts to attack this player.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
            int difference = getPlayer().getSkills().getCombatLevel() - ((Player) target).getSkills().getCombatLevel();

            if (inWild(p)) {
                if (difference > CoordinateLocations.getWildernessLevel(p, (int) p.getLocation().getX(), (int) p.getLocation().getY()) || difference < -(CoordinateLocations.getWildernessLevel(p, (int) p.getLocation().getX(), (int) p.getLocation().getY()))) {

                    getPlayer().getFrames().sendChatMessage(0,
                            "The difference between your Combat Level and the Combat Level of your opponent is");
                    getPlayer().getFrames().sendChatMessage(0, "too great.");
                    this.removeTarget();
                    queuedSet = false;
                    return;
                }

            }


            if (!inWild(p)) {
                if (difference > 15 || difference < -15) {

                    getPlayer().getFrames().sendChatMessage(0,
                            "The difference between your Combat Level and the Combat Level of your opponent is");
                    getPlayer().getFrames().sendChatMessage(0, "too great.");
                    this.removeTarget();
                    queuedSet = false;
                    return;

                }
            }
            if (rangedWeapon(p)) {
                if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.RANGE)) {
                    p.getFrames().sendChatMessage(0, "You cannot use range during this duel.");
                    p.getCombat().removeTarget();
                    queuedSet = false;
                    return;
                } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.RANGE)) {
                    p.getFrames().sendChatMessage(0, "You cannot use range during this duel.");
                    p.getCombat().removeTarget();
                    queuedSet = false;
                    return;
                }
            }
            if (isDiagonal() && !target.getWalk().isMoving() && !rangedWeapon(p)) {
                if (p.getCombat().freezeDelay > 0) {
                    p.getWalk().reset(true);
                    // p.getFrames().sendChatMessage(0, "A magical force stops
                    // you from moving.");
                    return;
                }
                removeDiag();
                // return;
            }
            //gtg soon, is it fixed or nah? ye sec as i thought, should never be in here...
            target.getCombat().combatWith = getPlayer().getClientIndex();
            target.getCombat().combatWithDelay = 12;
            /*if ( target.getCombat().combatWith > 0 && (p.getCombat().inDangerousPVP(p) || p.getCombat().inWild(p))) {
            p.skull(p);
            }*/
            //System.out.println("Weapon speed: "+speed);
            this.delay += (byte) speed;
            leechStats(getPlayer(), (Player) target);
            if (p.getCombatDefinitions().isSpecialOn()) {
                specialAttack(p, (Player) target);
                return;
            }

            if (p.getEquipment().getEquipment().contains(new Item(13879))) {
                int arrows3 = p.getEquipment().getEquipment().get(3).getId();
                p.getEquipment().deleteArrow(arrows3, 1);
                //p.getWalk().reset(true);
            	/* rangedAttack(p, target, specialOn);
            	 return;*/
            }
            if (rangedWeapon(p)) {
                int arrows = p.getEquipment().getEquipment().get(13).getId();
                if (!checkArrows(p)) {
                    return;
                }
                p.getWalk().reset(true);
                if (p.getEquipment().getEquipment().contains(new Item(11235))) {
                    p.getEquipment().deleteArrow(arrows, 2);
                } else {
                    p.getEquipment().deleteArrow(arrows, 1);
                }
                rangedAttack(p, target, specialOn);
                return;
            }
            sendPlayerAttackEmote(specialOn, weaponId);
            sendPlayerAttackGraphic(specialOn, weaponId, ammoId, target);
            //System.out.println("Max hit: "+maxHit);
            appendMeleeDamage(p, (Player) target, Misc.random(maxHit), false);
            Player pp = (Player) target;
            if (((p.getCombat().inDangerousPVP(p)) || (p.getCombat().inWild(p))) /*p.getUsername() != opp.skulledOn*/ && !pp.skulledOn2.contains(p.getUsername())) {

                p.skull(p);
                p.skulledOn2.add(pp.getUsername());
            }
            if (pp.autoRetaliate && !pp.getCombat().hasTarget() && p.getCombat().isProcessing) {
                pp.getCombat().attack(p);
            }
        } catch (Exception e) {
        }
    }

    public void applyVengeance1(final double hit, final Player p, final Player opp, final int hp) {
        opp.getMask().setLastChatMessage(new ChatMessage(0, 0, "Taste vengeance!"));
        opp.getMask().setChatUpdate(true);
        opp.getCombat().vengeance = false;

        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {

                if (hit > hp) {

                    p.hit((int) (hit * 25) / 200, opp);
                    opp.getCombat().shieldDelay = 0;
                } else {
                    p.hit((int) hit, opp);
                }
                this.stop();
            }
        }, 0, 0);
    }

    public int getDrawbackID(Player p) {
        int arrowId = p.getEquipment().get(3).getId();
        switch (arrowId) {
            case 882:
            case 883:
                return 19;
            case 884:
            case 885:
                return 18;
            case 886:
            case 887:
                return 20;
            case 888:
            case 889:
                return 21;
            case 890:
            case 891:
                return 22;
            case 892:
            case 893:
                return 24;
            default:
                return 18;
        }
    }

    public int getDelayDistance(Player p, Player opp) {
        int dist = Misc.getDistance(p.getLocation().getX(), p.getLocation().getY(), opp.getLocation().getX(),
                opp.getLocation().getY());
        int delay = 2;
        if (dist > 2) {
            delay += 1;
        }
        return delay;
    }

    public void appendRangeDamage(final Player p, final Player opp, int hitBefore, int cycleDelay) {
        if (opp.getPrayer().usingPrayer(0, 18)) {
            hitBefore = (int) (hitBefore * 0.4);
        }
        p.getCombat().shieldDelay = 1;
        final int hit = getRangeHit(p, opp, hitBefore, false);
        if (hit > opp.getSkills().getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().delay = 15;
            opp.getCombat().shieldDelay = 0;
        }
        if (hit > 0)
            p.getSkills().sendCounter(hit, false);
        soulSplit(p, opp, hit);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                if (opp.getCombat().shieldDelay == 0) {
                    opp.animate(CombatManager.getDefenceEmote(opp));
                }
                opp.hit(hit, p);
                if (opp.getCombat().vengeance) {
                    if (hit > 0) {
                        applyVengeance1(p.getSkills().isDead() ? 0 : Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Taste vengeance!"));
                        p.getMask().setChatUpdate(true);
                    }
                }
                this.stop();
            }
        }, cycleDelay - 1, 0);
    }

    public void appendDBowSpec(final Player p, final Player opp, int hitBefore, int cycleDelay, final int end) {
        if (opp.getPrayer().usingPrayer(0, 18)) {
            hitBefore = (int) (hitBefore * 0.4);
        }
        p.getCombat().shieldDelay = 1;
        int hit = getRangeHit(p, opp, hitBefore, false);
        final int hit2 = hit;
        int hit3 = hit;
        if (hit < 80) {
            hit = 80;
        }
        if (hit2 < 80) {
            hit3 = 80;
        }
        if (hit > 480) {
            hit = 480;
        }
        if (hit2 > 480) {
            hit3 = 480;
        }
        if (hit > 0) {
            p.getSkills().sendCounter(hit, false);
        }
        if (hit2 > 0) {
            p.getSkills().sendCounter(hit, false);
        }
        soulSplit(p, opp, hit);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                if (opp.getCombat().shieldDelay == 0) {
                    opp.animate(CombatManager.getDefenceEmote(opp));
                }
                opp.hit(hit2, p);
                opp.graphics2(end);
                if (opp.getCombat().vengeance) {
                    if (hit2 > 0) {
                        applyVengeance1(p.getSkills().isDead() ? 0 : Math.floor(hit2 * 0.75), p, opp, opp.getSkills().getHitPoints());
                    }
                }
                this.stop();
            }
        }, cycleDelay - 1, 0);
    }

    public void applyVengeance(final double hit, final Player p, final Player opp) {
        opp.getMask().setLastChatMessage(new ChatMessage(0, 0, "Taste vengeance!"));
        opp.getMask().setChatUpdate(true);
        opp.getCombat().vengeance = false;
        if (hit > opp.getSkills().getHitPoints()) {
            // opp.getCombat().removeTarget();
            opp.getCombat().shieldDelay = 0;
        }

    }

    public void appendMeleeDamage(final Player p, final Player opp, int hitBefore, boolean spec) {
        opp.getPoison().makePoisoned(48);
        if (opp.getPrayer().usingPrayer(0, 19)) {
            hitBefore = (int) (hitBefore * 0.4);
        } else if (opp.getPrayer().usingPrayer(1, 9)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics2(2230);
            // hitBefore(Misc.random(100));
        }
        p.getCombat().shieldDelay = 1;
        final int hit = getMeleeHit(p, opp, hitBefore, spec);
        if (hit > 0)
            p.getSkills().sendCounter(hit, false);
        if (opp.getCombat().pendingDamage() + hit > opp.getSkills().getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().shieldDelay = 0;
        }
        soulSplit(p, opp, hit);
        if (opp.getCombat().shieldDelay == 0) {
            opp.animate(CombatManager.getDefenceEmote(opp));
        }
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {

                String weaponName = "";


                if (weaponName.length() > 0) {
                    if (p.getEquipment().contains(5698)) {
                        if (Misc.random(1) == 1)
                            opp.getPoison().makePoisoned(48);
                    } else if (weaponName.contains("(p+)")) {
                        if (Misc.random(1) == 1)
                            opp.getPoison().makePoisoned(38);
                    } else if (weaponName.contains("(p)")) {
                        if (Misc.random(1) == 1)
                            opp.getPoison().makePoisoned(28);
                    }
                }
                if (p.getEquipment().contains(16909)) {
                    p.primalHitpoints += hit;
                    if (p.primalHitpoints >= 7500) {
                        p.graphics(247);
                        p.animate(3157);
                        p.primalHitpoints = 0;
                        int ran = Misc.random(1, 4);
                        if (ran == 1) {
                            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Aaaaaaarrrrrrgggg!!!"));
                        } else if (ran == 2) {
                            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "With the power of Auora..."));
                        } else if (ran == 3) {
                            p.getMask().setLastChatMessage(
                                    new ChatMessage(0, 0, "My almighty amir's bomber vest has retrieved its power!"));
                        } else if (ran == 3) {
                            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "My sword will pwn you!"));
                        } else if (ran == 3) {
                            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "What's zezima without a sword?"));
                        }
                        p.getMask().setChatUpdate(true);
                        int timer = Misc.random(10, 20);
                        p.primalTimer += timer;
                        p.getFrames().sendMessage("Your primal 2h special has been activated after 7500 hits, you have "
                                + timer / 2 + " seconds.");
                    }
                }
                opp.hit(hit, p);
                if (opp.getCombat().vengeance) {
                    if (hit > 0) {
                        applyVengeance1(p.getSkills().isDead() ? 0 : Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                    }
                }
                this.stop();
            }
        }, 0, 0);
    }

    public void appendKorasiDamage(final Player p, final Player opp, int hitBefore, boolean spec) {
        if (opp.getPrayer().usingPrayer(0, 17)) {
            hitBefore = (int) (hitBefore * 0.4);
        } else if (opp.getPrayer().usingPrayer(1, 7)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics2(2230);
            // hitBefore(Misc.random(100));
        }
        p.getCombat().shieldDelay = 1;
        final int hit = getMeleeHit(p, opp, hitBefore, spec);
        if (hit > 0)
            p.getSkills().sendCounter(hit, false);
        if (opp.getCombat().pendingDamage() + hit > opp.getSkills().getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().shieldDelay = 0;
        }
        soulSplit(p, opp, hit);
        if (opp.getCombat().shieldDelay == 0) {
            opp.animate(CombatManager.getDefenceEmote(opp));
        }
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.hit(hit, p);
                if (opp.getCombat().vengeance) {
                    if (hit > 0) {
                        applyVengeance1(p.getSkills().isDead() ? 0 : Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                    }
                }
                this.stop();
            }
        }, 0, 0);
    }

    public int pendingDamage() {
        Player p = (Player) entity;
        int totalDamage = 0;
        for (Hit h : p.getQueuedHits()) {
            totalDamage += h.getDamage();
        }
        return totalDamage;
    }

    public void appendKorasiDamageNoDef(final Player p, final Player opp, int hitBefore, boolean spec) {
        if (opp.getPrayer().usingPrayer(0, 17)) {
            hitBefore = (int) (hitBefore * 0.4);
        } else if (opp.getPrayer().usingPrayer(1, 7)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics2(2230);
        }
        p.getCombat().shieldDelay = 1;
        final int hit = hitBefore;
        if (hit > 0)
            p.getSkills().sendCounter(hit, false);
        if (opp.getCombat().pendingDamage() + hit > opp.getSkills().getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().shieldDelay = 0;
        }
        soulSplit(p, opp, hit);
        if (opp.getCombat().shieldDelay == 0) {
            opp.animate(CombatManager.getDefenceEmote(opp));
        }
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.hit(hit);
                if (opp.getCombat().vengeance) {
                    if (hit > 0) {
                        applyVengeance1(p.getSkills().isDead() ? 0 : Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                    }
                }
                this.stop();
            }
        }, 0, 0);
    }

    public void appendMeleeDamageNoDef(final Player p, final Player opp, int hitBefore, boolean spec) {
        if (opp.getPrayer().usingPrayer(0, 19)) {
            hitBefore = (int) (hitBefore * 0.4);
        } else if (opp.getPrayer().usingPrayer(1, 9)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics2(2230);
        }
        p.getCombat().shieldDelay = 1;
        final int hit = hitBefore;
        if (opp.getCombat().pendingDamage() + hit > opp.getSkills().getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().shieldDelay = 0;
        }
        soulSplit(p, opp, hit);
        if (opp.getCombat().shieldDelay == 0) {
            opp.animate(CombatManager.getDefenceEmote(opp));
        }
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.hit(hit);
                if (opp.getCombat().vengeance) {
                    if (hit > 0) {
                        applyVengeance1(p.getSkills().isDead() ? 0 : Math.floor(hit * 0.75), p, opp, opp.getSkills().getHitPoints());
                    }
                }
                this.stop();
            }
        }, 0, 0);
    }

    public double getRangeAccuracy(Player p, Player opp, boolean spec) {
        //final double A = 0.705;
        final double A = 0.5;

        double atkBonus = p.getCombatDefinitions().bonus[4];
        double defBonus = opp.getCombatDefinitions().bonus[4 + 5];
        if (atkBonus < 1) {
            atkBonus = 0;
        }
        if (defBonus < 1) {
            defBonus = 0;
        }
        double atk = (atkBonus * p.getSkills().level[4]);
        double def = ((defBonus * opp.getSkills().level[1]) / 1.5);
        if (CombatManager.wearingVoid(p, 11664)) {
            // atk *= 1.15;
            atk *= 1.35;
        }
        if (p.getPrayer().usingPrayer(0, 3)) {
            atk *= 1.05;
        }
        if (p.getPrayer().usingPrayer(0, 11)) {
            atk *= 1.10;
        }
        if (p.getPrayer().usingPrayer(0, 20)) {
            atk *= 1.15;
        }
		/*
		 * if (player.getPrayer().usingPrayer(0, 28)) { atk *= 1.20; def *= 1.25; }
		 */
        if (opp.getPrayer().usingPrayer(0, 0)) {
            def *= 1.05;
        }
        if (opp.getPrayer().usingPrayer(0, 5)) {
            def *= 1.10;
        }
        if (opp.getPrayer().usingPrayer(0, 13)) {
            def *= 1.15;
        }
        if (opp.getPrayer().usingPrayer(0, 25)) {
            def *= 1.20;
        }
        if (opp.getPrayer().usingPrayer(0, 27)) {
            def *= 1.25;
        }
        if (boltEffect) {
            atk *= 1.25;
            boltEffect = false;
        }
        if (bowSpec) {
            atk *= 1.40;
            bowSpec = false;
        }
        return A * (atk / def);
    }

    public int getRangeHit(Player p, Player opp, int hit, boolean spec) {
        double accuracy = getRangeAccuracy(p, opp, spec);
        Random random = new Random();
        int hitBefore = hit;
        if (opp.getPrayer().usingPrayer(1, 8)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics(2229);
            p.hit(Misc.random(70));
        }
        hit = hitBefore;
        if (opp.getEquipment().contains(13740)) {
            int prayerLost = (int) Math.ceil(hit * .006);
            if (opp.getSkills().level[5] >= prayerLost) {
                opp.getSkills().level[5] -= prayerLost;
                opp.getFrames().sendSkillLevel(5);
                hit *= 0.7;
            } else
                opp.getSkills().level[5] = 0;
        }
        if (opp.getEquipment().contains(13742)) {
            if (Misc.random(10) <= 6) {
                hit *= 0.75;
                opp.graphics(2000);
            }
        }

        if (accuracy > 1.0) {
            accuracy = 1;
        }
        if (accuracy < random.nextDouble()) {
            return 0;
        } else {
            return hit;
        }
    }

    public int getMeleeHit(Player p, Player opp, int hit, boolean spec) {
        double accuracy = getMeleeAccuracy(p, opp, spec);
        Random random = new Random();
		/*
		 * int hitBefore = hit; if (opp.getPrayer().usingPrayer(1, 8)) {
		 * hitBefore = (int) (hitBefore * 0.4); if (opp.getCombat().shieldDelay
		 * == 0) opp.animate(12573); opp.graphics(2227);
		 * player.hit(Misc.random(100)); }
		 */
        if (accuracy > 1.0) {
            accuracy = 1;
        }
        if (hit < 0) {
            hit = 0;
        }
        if (opp.getEquipment().contains(13740)) {
            int prayerLost = (int) Math.ceil(hit * .06);
            if (opp.getSkills().level[5] >= prayerLost) {
                opp.getSkills().level[5] -= prayerLost;
                opp.getFrames().sendSkillLevel(5);
                hit *= 0.7;
            } else
                opp.getSkills().level[5] = 0;
        }
        if (opp.getEquipment().contains(13742)) {
            if (Misc.random(10) <= 7) {
                hit *= 0.75;
                opp.graphics(2000);
            }
        }
        if (accuracy < random.nextDouble()) {
            return 0;
        } else {
            return hit;
        }
    }

    public int getKorasiHit(Player p, Player opp, int hit, int beforeHit, boolean spec) {
        double accuracy = getMeleeAccuracy(p, opp, spec);
        Random random = new Random();
		/*
		 * int hitBefore = hit; if (opp.getPrayer().usingPrayer(1, 8)) {
		 * hitBefore = (int) (hitBefore * 0.4); if (opp.getCombat().shieldDelay
		 * == 0) opp.animate(12573); opp.graphics(2227);
		 * player.hit(Misc.random(100)); }
		 */
        if (accuracy > 1.0) {
            accuracy = 1;
        }
        if (hit < 0) {
            hit = 0;
        }
        if (accuracy < random.nextDouble()) {
            return 0;
        } else {
            return hit;
        }
    }

    public double getMeleeAccuracy(Player p, Player opp, boolean spec) {
        try {
            final double A = 0.755;
            int bonus = 1;
            double atkBonus = p.getCombatDefinitions().bonus[bonus];
            double defBonus = opp.getCombatDefinitions().bonus[bonus + 5];
            if (atkBonus < 1) {
                atkBonus = 0;
            }
            if (defBonus < 1) {
                defBonus = 0;
            }
            double atk = (atkBonus * p.getSkills().level[0]);
            double def = (defBonus * opp.getSkills().level[1]);
            atk += 2;
            if (CombatManager.wearingVoid(p, 11665)) {
                atk *= 1.70;

            }

            if (spec) {
                switch (p.getEquipment().get(3).getId()) {
                    case 4153:
                        atk *= 1.30;
                        break;
                    case 13902:
                        atk *= 1.65;
                        break;
                    case 11235:
                    case 13879:
                        atk *= 1.55;
                        break;
                    case 13905:
                        atk *= 1;
                        break;
                    case 1419:
                    case 7806:
                    case 11730:
                        atk *= 1.25;
                        break;
                    case 11694:
                        atk *= 1.50;
                        break;
                    case 11061:
                    case 10887:
                        atk *= 1.60;
                        break;
                    case 14484:
                        atk *= 1.16;
                        break;
                    case 11700:
                    case 11698:
                    case 11696:
                        atk *= 1.60;
                        break;
                    case 1215:
                    case 7158:
                    case 5698:
                        atk *= 1.25;
                        break;
                    case 5680:
                        atk *= 1.25;
                        break;
                    case 3204:
                        atk *= 1.00;
                        break;
                    case 4587:
                    case 1305:
                        atk *= 1.60;
                        break;
                    case 13899:
                    case 4151:
                    case 5670:
                    case 15444:
                    case 15443:
                    case 15442:
                    case 15441:
                        atk *= 1.50;
                    case 1434:
                        atk *= 0.945;
                        break;
                }
            } else {
                switch (p.getEquipment().get(3).getId()) {
                    case 18349:
                        atk *= 1.35;
                }
            }
            if (CombatManager.wearingDharok(p)) {
                atk *= 1.35;
            }
            if (p.getPrayer().usingPrayer(0, 2)) {
                atk *= 1.05;
            }
            if (p.getPrayer().usingPrayer(0, 7)) {
                atk *= 1.10;
            }
            if (opp.getPrayer().usingPrayer(0, 0)) {
                def *= 1.05;
            }
            if (opp.getPrayer().usingPrayer(0, 5)) {
                def *= 1.10;
            }
            if (opp.getPrayer().usingPrayer(0, 13)) {
                def *= 1.15;
            }
            if (opp.getPrayer().usingPrayer(0, 25)) {
                def *= 1.20;
            }
            if (opp.getPrayer().usingPrayer(0, 27)) {
                def *= 1.25;
            }

            final double OUTCOME = A * (atk / def);
            return OUTCOME;
        } catch (Exception e) {

        }
        return -1;
    }

    public void rangedAttack(Player p, Entity opp, boolean usingSpec) {
        int weaponID = p.getEquipment().get(3) == null ? -1 : p.getEquipment().get(3).getId();
        Player opponant = (Player) opp;
        int hit = Misc.random(this.calculateMaxRangedHit());
        int distanceDelay = getDelayDistance(p, (Player) opp);
        if (!this.checkArrows(p)) {
            return;
        }
        if (this.mageArena(p)) {
            p.getFrames().sendChatMessage(0, "You can't use range in this arena.");
            return;
        }
        if (this.westsArena(p)) {
            p.getFrames().sendChatMessage(0, "You can't use range in this area.");
            return;
        }
        if (((p.getCombat().inDangerousPVP(p)) || (p.getCombat().inWild(p))) /*p.getUsername() != opp.skulledOn*/ && !opponant.skulledOn2.contains(p.getUsername())) {

            p.skull(p);
            p.skulledOn2.add(opponant.getUsername());
        }
        switch (weaponID) {
            case 13879:
                p.animate(10501);
                p.graphics(1838);
                ProjectileManager.sendGlobalProjectile(entity, target, 1837, 31, 35, 45, 25, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);

                break;
            case 11235:
                p.animate(426);
                p.graphics2(1111);
                ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35, 45, 23, 0);
                ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35, 20, 23, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                appendRangeDamage(p, opponant, Misc.random(this.calculateMaxRangedHit()), distanceDelay + 1);
                break;
            case 15241:
                p.animate(12174);
                p.graphics(2138);
                ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31, 35, 45, 23, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                break;

            case 839:
            case 841:
            case 843:
            case 845:
            case 847:
            case 849:
            case 851:
            case 853:
            case 859:
            case 861:
            case 16873:
            case 4212:
                p.animate(426);
                p.graphics2(getDrawbackID(p));
                ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35, 45, 23, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                break;
            case 9177:
            case 9185:
            case 10156:
            case 18357:
                if (Misc.random(25) == 17) {
                    int arrows = p.getEquipment().get(13) == null ? -1 : p.getEquipment().get(13).getId();
                    switch (arrows) {
                        case 9244:
                            opp.graphics(756);
                            boltEffect = true;
                            //hit *= 1.40;
                            hit *= 1.50;
                    }
                }
                p.animate(4230);
                ProjectileManager.sendGlobalProjectile(entity, target, 27, 42, 35, 48, 18, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                break;
        }
    }

    public void processHit() {
        if (combatHitDefinitions == null)
            return;
        if (hitDelay != 0)
            return;
        Player p = (Player) entity;
        Entity target = combatHitDefinitions.getTarget();
        if (target instanceof Player) {
            if (!((Player) target).isOnline() || target.isDead()) {
                if (prayerAfterHitDelay == -1)
                    combatHitDefinitions = null;
                return;
            }
            target.getCombat().setLastAttackedTime(System.currentTimeMillis());
        }
        if (target.isDead()) {
            return;
        }
        Player pp = (Player) target;
		/*if(pp.autoRetaliate) {
			p.getCombat().attack(getPlayer());
			target.getCombat().attack(getPlayer());
		}*/

		/*
		 * Prayer while hitting
		 */
        int hit1 = PlayerProbOfHiting(target, combatHitDefinitions.getMaxDamage(), combatHitDefinitions.getBonuses(),
                combatHitDefinitions.getWeaponId(), combatHitDefinitions.isSpecialOn())
                ? combatHitDefinitions.getMaxDamage() : 0;
        if (combatHitDefinitions.isMeleeDeflectPray() || combatHitDefinitions.isRangeDeflectPray()) {
            if (hit1 * 10 / 100 > 0)
                entity.hit(hit1 * 10 / 100);
            target.animate(12573);
            entity.graphics(combatHitDefinitions.isMeleeDeflectPray() ? 2230 : 2229); // target
        } else if (combatHitDefinitions.isSoulSplitPray())
            ProjectileManager.sendGlobalProjectile(entity, target, 2263, 11, 11, 30, 20, 0);
        if (combatHitDefinitions.isLeechAttack() || combatHitDefinitions.isLeechRanged()
                || combatHitDefinitions.isLeechDefence() || combatHitDefinitions.isLeechStrength())
            ProjectileManager
                    // atk = 2231
                    // range = 2236 defence 2244 mage = 2248
                    .sendGlobalProjectile(target, entity,
                            combatHitDefinitions
                                    .isLeechAttack()
                                    ? 2231
                                    : (combatHitDefinitions.isLeechRanged() ? 2236
                                    : (combatHitDefinitions.isLeechDefence() ? 2244 : 2248)),
                            30, 30, 30, 0, 0);
        else if (combatHitDefinitions.isTurmoilPray())
            ((Player) target).getPrayer().setBoost(8, true);
        
        
        

		/*
		 * Defence Animation
		 */
        if (!combatHitDefinitions.isMeleeDeflectPray() && !combatHitDefinitions.isRangeDeflectPray())
            target.animate(CombatManager.getDefenceEmote(target));


        else {
            target.hit(hit1);
            combatHitDefinitions.setMaxDamage(hit1);
        }

        if (pp.autoRetaliate && !pp.getCombat().hasTarget() && p.getCombat().isProcessing) {
            pp.getCombat().attack(p);
        }
        hitDelay = -1;
        if (prayerAfterHitDelay == -1)
            combatHitDefinitions = null;
    }

    private boolean PlayerProbOfHiting(Entity target, int attackStyle, short[] bonus, int weaponId, boolean specialOn) {
        Player p = (Player) entity;
        if (target instanceof Player) { // TODO
            Player enemy = (Player) target;
            double att = bonus[1] + p.getSkills().getLevel(Skills.ATTACK);
            if (specialOn) {
                double multiplier = 0.25;
                multiplier += CombatManager.getSpecDamageDoublePercentage(weaponId) / 2;
                att = att * multiplier;
            }
            double def = enemy.getCombatDefinitions().getBonus()[6] + enemy.getSkills().getLevel(Skills.DEFENCE);
            double prob = att / def;
            if (prob > 0.70)
                prob = 0.70;
            return prob >= Math.random();
        }
        return true;
    }

    public void processPrayerBeforeHit() {
        if (combatHitDefinitions == null)
            return;
        if (prayerBeforeHitDelay != 0)
            return;
        Entity target = combatHitDefinitions.getTarget();
        if (target instanceof Player)
            if (!((Player) target).isOnline() || target.isDead()) {
            }

    }

    public void processPrayerAfterHit() {
        if (combatHitDefinitions == null)
            return;
        if (prayerAfterHitDelay != 0)
            return;
        Entity target = combatHitDefinitions.getTarget();
        if (target instanceof Player) {
            if (!((Player) target).isOnline() || target.isDead()) {
                combatHitDefinitions = null;
                return;
            }
        }

        if (combatHitDefinitions.isSoulSplitPray()) {
            int gain = combatHitDefinitions.getMaxDamage() / 50;
            ((Player) entity).getSkills().heal(gain * 10);
            if (target instanceof Player)
                ((Player) target).getSkills().drainPray(gain);
            target.graphics(2264);
            ProjectileManager.sendGlobalProjectile(target, entity, 2263, 11, 11, 30, 0, 0);
        }
        if (combatHitDefinitions.isSapWarrior()) {
			/*
			 * boolean bost = ((Player) entity).getPrayer().usingBoost(0);
			 * ((Player) target).getSkills().set( 0, ((Player)
			 * target).getSkills().getLevelForXp(0) (bost ? 80 : 90) / 100);
			 * ((Player) target).getSkills().set( 2, ((Player)
			 * target).getSkills().getLevelForXp(2) (bost ? 80 : 90) / 100);
			 * ((Player) target).getSkills().set( 1, ((Player)
			 * target).getSkills().getLevelForXp(1) (bost ? 80 : 90) / 100);
			 * target.graphics(2216); ((Player) entity).getPrayer().setBoost(0,
			 * true);
			 */
        }
        if (combatHitDefinitions.isSapRanger()) {
			/*
			 * boolean bost = ((Player) entity).getPrayer().usingBoost(1);
			 * ((Player) target).getSkills().set( 4, ((Player)
			 * target).getSkills().getLevelForXp(4) (bost ? 80 : 90) / 100);
			 * ((Player) target).getSkills().set( 1, ((Player)
			 * target).getSkills().getLevelForXp(1) (bost ? 80 : 90) / 100);
			 * target.graphics(2219); ((Player) entity).getPrayer().setBoost(1,
			 * true);
			 */
        }
        if (combatHitDefinitions.isSapSpirit()) {
            target.graphics(2225);
        }
        if (combatHitDefinitions.isLeechAttack()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(3);
            ((Player) target).getSkills().set(0,
                    ((Player) target).getSkills().getLevelForXp(0) * (bost ? 75 : 90) / 100);
            target.graphics(2232);
            ((Player) entity).getPrayer().setBoost(3, true);
        }
        if (combatHitDefinitions.isLeechRanged()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(5);
            ((Player) target).getSkills().set(4,
                    ((Player) target).getSkills().getLevelForXp(4) * (bost ? 75 : 90) / 100);
            target.graphics(2238);
            ((Player) entity).getPrayer().setBoost(5, true);
        }
        if (combatHitDefinitions.isLeechDefence()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(6);
            ((Player) target).getSkills().set(1,
                    ((Player) target).getSkills().getLevelForXp(1) * (bost ? 75 : 90) / 100);
            target.graphics(2246);
            ((Player) entity).getPrayer().setBoost(6, true);
        }
        if (combatHitDefinitions.isLeechStrength()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(7);
            ((Player) target).getSkills().set(2,
                    ((Player) target).getSkills().getLevelForXp(2) * (bost ? 75 : 90) / 100);
            target.graphics(2250);
            ((Player) entity).getPrayer().setBoost(7, true);
        }

        prayerAfterHitDelay = -1;
        combatHitDefinitions = null;
    }

    private void sendPlayerHit(boolean specialon, final int weaponId, final int maxHit, final int distance,
                               final Entity target) {
        int randomDamage = Misc.random(maxHit);
        if (specialon) {
            int p1 = maxHit * 5 / 10;
            double m1 = 1.25 + CombatManager.getSpecDamageDoublePercentage(weaponId);
            if (p1 > randomDamage && m1 > Math.random()) {
                int d1 = maxHit - p1;
                randomDamage = p1 + Misc.random(d1);
            }
        }
        final boolean protectPray = randomDamage > 0 && (target instanceof Player
                && (CombatManager.isRangingWeapon(weaponId) && (((Player) target).getPrayer().usingPrayer(0, 18))
                || ((Player) target).getPrayer().usingPrayer(0, 19)));
        final boolean sapWarrior = randomDamage > 0 && !CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player
                && ((Player) entity).getPrayer().usingPrayer(1, 1))
                && Misc.random(14) == 1;
        if (protectPray)
            randomDamage = target instanceof Player ? randomDamage * 60 / 100 : 0;
        final boolean sapRanger = randomDamage > 0 && CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player
                && ((Player) entity).getPrayer().usingPrayer(1, 2))
                && Misc.random(14) == 1;
        final boolean sapSpirit = !sapRanger && !sapWarrior && randomDamage > 0 && (target instanceof Player
                && entity instanceof Player && ((Player) entity).getPrayer().usingPrayer(1, 4)) && Misc.random(14) == 1;
        final boolean leechAttack = randomDamage > 0 && !CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player
                && ((Player) entity).getPrayer().usingPrayer(1, 10))
                && Misc.random(14) == 1;
        final boolean leechRanged = randomDamage > 0 && CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player
                && ((Player) entity).getPrayer().usingPrayer(1, 11))
                && Misc.random(14) == 1;
        final boolean leechDefence = !leechAttack && !leechRanged && randomDamage > 0 && (target instanceof Player
                && entity instanceof Player && ((Player) entity).getPrayer().usingPrayer(1, 13))
                && Misc.random(14) == 1;
        final boolean leechStrength = !leechAttack && !leechDefence
                && randomDamage > 0 && !CombatManager.isRangingWeapon(weaponId) && (target instanceof Player
                && entity instanceof Player && ((Player) entity).getPrayer().usingPrayer(1, 14))
                && Misc.random(14) == 1;
        final boolean rangeDeflectPray = randomDamage > 0 && (target instanceof Player
                && CombatManager.isRangingWeapon(weaponId) && ((Player) target).getPrayer().usingPrayer(1, 8));
        final boolean meleeDeflectPray = randomDamage > 0 && (target instanceof Player
                && !CombatManager.isRangingWeapon(weaponId) && ((Player) target).getPrayer().usingPrayer(1, 9));
        if (rangeDeflectPray || meleeDeflectPray)
            randomDamage = target instanceof Player ? randomDamage * 60 / 100 : 0;
        final boolean soulSplitPray = (entity instanceof Player && ((Player) entity).getPrayer().usingPrayer(1, 18));
        final boolean turmoilPray = (target instanceof Player && ((Player) target).getPrayer().usingPrayer(1, 19)
                && !((Player) target).getPrayer().usingBoost(8));

        if (sapWarrior || sapRanger || sapSpirit) {
            if (sapWarrior) {
                entity.animate(12569);
                target.graphics(2214);
            }
            if (sapRanger) {
                entity.animate(12569);
                target.graphics(2217);
            }
            if (sapSpirit) {
                entity.animate(12569);
                target.graphics(2223);
            }
        }
        combatHitDefinitions = new CombatHitDefinitions(target, weaponId, randomDamage, specialon,
                ((Player) entity).getCombatDefinitions().getBonus(), meleeDeflectPray, rangeDeflectPray, protectPray,
                soulSplitPray, sapWarrior, sapRanger, sapSpirit, leechAttack, leechRanged, leechDefence, leechStrength,
                turmoilPray);
        hitDelay = (weaponId == 4153 && specialon) ? 0 : (byte) distance;

    }

    public CombatHitDefinitions getHitDefinitions() {
        return combatHitDefinitions;
    }

    private void processNpc() {

    }

    public int getPlayerMaxHit(int attackStyle, int weaponId, boolean isRanging, boolean specialOn) {
        Player p = (Player) entity;
        if (!isRanging) {
            int StrengthLvl = p.getSkills().getLevel(Skills.STRENGTH);
            double PrayerBonus = 1;
            if (p.getPrayer().usingPrayer(0, 1))
                PrayerBonus = 1.05;
            else if (p.getPrayer().usingPrayer(0, 6))
                PrayerBonus = 1.1;
            else if (p.getPrayer().usingPrayer(0, 14))
                PrayerBonus = 1.15;
            else if (p.getPrayer().usingPrayer(0, 25))
                PrayerBonus = 1.18;
            else if (p.getPrayer().usingPrayer(0, 26))
                PrayerBonus = 1.23;
            else if (p.getPrayer().usingPrayer(1, 19))
                PrayerBonus = (p.getPrayer().usingBoost(8) && target instanceof Player)
                        ? 1.26 + (((Player) target).getSkills().getLevelForXp(Skills.STRENGTH) / 1000) : 1.23;
            double OtherBonus = 1;
            int StyleBonus = 0;
            if (attackStyle == 0)
                StyleBonus = 0;
            else if (attackStyle == 2)
                StyleBonus = 1;
            else if (attackStyle == 3)
                StyleBonus = 3;
            double EffectiveStrenght = Math.round(StrengthLvl * PrayerBonus * OtherBonus) + StyleBonus;
            int StrengthBonus = p.getCombatDefinitions().getBonus()[11];
            double BaseDamage = 15 + EffectiveStrenght + (StrengthBonus / 8) + (EffectiveStrenght * StrengthBonus / 64);
            double finaldamage;
            if (specialOn) {
                finaldamage = Math.floor(BaseDamage * CombatManager.getSpecDamageDoublePercentage(weaponId));

            } else if (CombatManager.wearingDharok(p) && p.getSkills().getHitPoints() < 10) {
                finaldamage = 920;
            } else if (CombatManager.wearingDharok(p) && p.getSkills().getHitPoints() >= 10) {
                //p.getSkills().getLevelForXp(3)
                finaldamage = 990 - (p.getSkills().getHitPoints() * 0.7);
            } else {
                finaldamage = Math.round(BaseDamage);
            }


            if (target != null) {
                // Sol stuff - Canownueasy
                if (target instanceof Player) {
                    Player defender = (Player) target;
                    Item wep = defender.getEquipment().get(Equipment.SLOT_WEAPON);
                    if (wep != null) {
                        if (wep.getId() == 15486 && target.getCombat().solSpecWait > 0) {
                            finaldamage = (finaldamage / 2);
                            target.graphics(2320);
                        }
                    }
                }
            }
            return (int) finaldamage;
        }
        return 0;
    }

    private void sendPlayerAttackEmote(boolean specialon, int weaponId) {
        Player p = (Player) entity;
        byte attackStyle = p.getCombatDefinitions().getAttackStyle();
        // int attackStyle = player.attackstyle;
        if (weaponId == -1) {
            this.entity.animate(attackStyle == 1 ? 422 : 423);
        }
        String itemName = new Item(weaponId).getDefinition().name;
        if (!specialon) {
            if (itemName.contains("scimitar"))
                this.entity.animate(12029);
            else if (itemName.contains("Chaotic rapier"))
                this.entity.animate(12310);
            else if (itemName.contains("Korasi"))
                this.entity.animate(12029);
            else if (weaponId == -1)
                this.entity.animate(422);
            else if (itemName.contains("Primal rapier"))
                this.entity.animate(12310);
            else if (itemName.contains("Rapier"))
                this.entity.animate(417);
            else if (itemName.contains("longsword"))
                this.entity.animate(12029);
            else if (itemName.contains("whip"))
                this.entity.animate(1658);
            else if (itemName.contains("Abyssal tentacle"))
                this.entity.animate(1658);
            else if (itemName.contains("Anger sword"))
                this.entity.animate(1655);
            else if (itemName.contains("Statius's warhammer"))
                this.entity.animate(401);
            else if (itemName.contains("2h sword"))
                this.entity.animate(407);
            else if (itemName.contains("Primal 2h sword"))
                this.entity.animate(407);
            else if (itemName.contains("claws"))
                this.entity.animate(393);
            else if (itemName.contains("mace"))
                this.entity.animate(401);
            else if (weaponId == 6528 || itemName.contains("Chaotic maul") || itemName.contains("Elder maul"))
                this.entity.animate(13055);
                //this.entity.animate(2661);
            else if (itemName.contains("Primal maul"))
                this.entity.animate(2661);
            else if (itemName.contains("pickaxe"))
                this.entity.animate(attackStyle == 2 ? 402 : 401);
            else if (itemName.contains("Dharok"))
                this.entity.animate(/*attackStyle == 2 ? 2067 : */2066);
            else if (itemName.contains("Verac"))
                this.entity.animate(2062);
            else if (itemName.contains("Guthan"))
                this.entity.animate(attackStyle == 2 ? 2081 : 2080);
            else if (itemName.contains("godsword") || itemName.contains("Saradomin sword"))
                this.entity.animate(attackStyle == 2 ? 7048 : 7041);
            else if (itemName.contains("Keris") || itemName.contains("dagger"))
                this.entity.animate(attackStyle == 2 ? 401 : 402);
            else if (itemName.contains("Karil"))
                this.entity.animate(2075);
            else if (itemName.contains("Scythe") || itemName.contains("scythe"))
                this.entity.animate(437);
            else if (itemName.contains("Korasi"))
                this.entity.animate(12029);
            else if (itemName.contains("halberd"))
                this.entity.animate(440);
            else if (itemName.contains("Granite maul"))
                this.entity.animate(1665);
            else if (itemName.contains("Staff of light") || itemName.contains("spear") || itemName.contains("Staff")
                    || itemName.contains("staff"))
                this.entity.animate(419);
            else if (itemName.contains("Bow-sword"))
                this.entity.animate(12028);
            else if (!itemName.contains("Karil") && itemName.contains("crossbow"))
                this.entity.animate(4230);
            else if (itemName.contains("dart"))
                this.entity.animate(582);
            else if (itemName.contains("knife"))
                this.entity.animate(806);
            else if (itemName.contains("bow"))
                this.entity.animate(426);
            else if (itemName.contains("javelin"))
                this.entity.animate(10501);
        } else {
            if (weaponId == 14484) {
                this.entity.animate(10961);
                this.entity.graphics(1950);
            } else if (weaponId == 11694) {
                this.entity.animate(7074);
                this.entity.graphics(1222);
            } else if (weaponId == 4153) {
                this.entity.animate(1667);
            }
            this.entity.animate(426);
        }
    }

    private void sendPlayerAttackGraphic(boolean specialon, int weaponId, int ammoId, Entity target) {
      /*  if (weaponId == -1)
            return;*/
        String itemName = new Item(weaponId).getDefinition().name;
        if (!specialon) {
            if (weaponId == 868) {
                entity.graphics(225, 100 << 16);
            } else if (weaponId == 867) {
                entity.graphics(224, 100 << 16);
            } else if (itemName.contains("javelin")) {
                //entity.animate(10501);
                //entity.graphics(1838);
                ProjectileManager.sendGlobalProjectile(entity, target, 1837, 31, 35, 45, 25, 0);

            } else if (weaponId == 866) {
                entity.graphics(223, 100 << 16);
            } else if (weaponId == 865) {
                entity.graphics(222, 100 << 16);
            } else if (weaponId == 864) {
                entity.graphics(221, 100 << 16);
            } else if (weaponId == 863) {
                entity.graphics(220, 100 << 16);
            } else if (weaponId == 4214) {
                entity.graphics(250, 100 << 16);
                ProjectileManager.sendGlobalProjectile(entity, target, 249, 36, 40, 41, 15, 0);
            } else if (itemName.contains("bow")) {
                if (ammoId == 882) {
                    entity.graphics(19, 100 << 16);
                    ProjectileManager.sendGlobalProjectile(entity, target, 10, 36, 40, 41, 15, 0);
                } else if (ammoId == 884) {
                    entity.graphics(18, 100 << 16);
                    ProjectileManager.sendGlobalProjectile(entity, target, 11, 36, 40, 41, 15, 0);
                } else if (ammoId == 886) {
                    entity.graphics(20, 100 << 16);
                    ProjectileManager.sendGlobalProjectile(entity, target, 12, 36, 40, 41, 15, 0);
                } else if (ammoId == 9706) {
                    entity.graphics(25, 100 << 16);
                    ProjectileManager.sendGlobalProjectile(entity, target, 25, 36, 40, 41, 15, 0);
                } else if (ammoId == 888) {
                    entity.graphics(21, 100 << 16);
                    ProjectileManager.sendGlobalProjectile(entity, target, 13, 36, 40, 41, 15, 0);
                } else if (ammoId == 890) {
                    entity.graphics(22, 100 << 16);
                    ProjectileManager.sendGlobalProjectile(entity, target, 14, 36, 40, 41, 15, 0);
                } else if (ammoId == 892) {
                    entity.graphics(24, 100 << 16);
                    ProjectileManager.sendGlobalProjectile(entity, target, 15, 36, 40, 41, 15, 0);
                }
            }
        } else {
            if (weaponId == 14484) {
                this.entity.graphics(1950);
            } else if (weaponId == 11694) {
                this.entity.graphics(1222);
            } else if (itemName.contains("javelin")) {
            	/* this.entity.animate(10501);
                 this.entity.graphics2(1836);  */
                ProjectileManager.sendGlobalProjectile(entity, target, 1837, 31, 35, 45, 23, 0);

            } else if (weaponId == 13883) {
                this.entity.graphics(1839);
            } else if (weaponId == 4153) {
                this.entity.graphics(340);
            }
        }
    }

    public boolean onTop(Entity player, Entity target) {
        if (player == null || target == null)
            return false;
        if ((player.getLocation().getX() == target.getLocation().getX())
                && (player.getLocation().getY() == target.getLocation().getY())
                && (player.getLocation().getZ() == target.getLocation().getZ())) {
            return true;
        }
        return false;
    }


    public long getLastAttackedTime() {
        return lastAttackedTime;
    }

    public void setLastAttackedTime(long lastAttackedTime) {
        this.lastAttackedTime = lastAttackedTime;
    }

}
