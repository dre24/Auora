package Auora.model.player;

import Auora.GameServer;
import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.events.Task;
import Auora.model.GlobalItem;
import Auora.model.Item;
import Auora.model.World;
import Auora.model.minigames.FightPits;
import Auora.model.player.logs.Logs;
import Auora.skills.Potions;
import Auora.skills.combat.Combat;
import Auora.util.Misc;
import Auora.util.RSTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the player's skills.
 *
 * @author Graham
 */
public class Skills implements Serializable {

    public static final int SKILL_COUNT = 25;
    public static final double MAXIMUM_EXP = 2000000000;
    public static final double SMALL_EXP = 13034431;
    public static final double RESET_EXP = 0;
    public static final String[] SKILL_NAME = {"Attack", "Defence", "Strength", "Hitpoints", "Range", "Prayer",
            "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining",
            "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Construction", "Hunter",
            "Summoning", "Dungeoneering"};
    public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2, HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6,
            COOKING = 7, WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11, CRAFTING = 12, SMITHING = 13,
            MINING = 14, HERBLORE = 15, AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19, RUNECRAFTING = 20,
            CONSTRUCTION = 21, HUNTER = 22, SUMMONING = 23, DUNGEONEERING = 23;
    /**
     *
     */
    private static final long serialVersionUID = -5844927980818218635L;
    public static int[] drops = new int[20];
    public static Player killer;
    public static List<Integer> ITEMS_TO_DROP = new ArrayList<Integer>();
    public static int[] PVP_DROP = {6585, 11284, 4151, 6737, 16347, 16353, 17317, 11848, 11846, 985, 987};
    public static int[] PVP_DROP2 = {15442, 15443, 15444, 11696, 11698, 11700};
    public static int[] PVP_DROP3 = {14484, 11694, 18786};
    public static int[] PVP_DROP4 = {6199, 14596, 16174, 16931};
    public static int[] PVP_DROP5 = {4716, 4718, 4720, 4722, 4708, 4710, 4712, 4714, 4745, 4747, 4749, 4751, 4724,
            4726, 4728, 4730, 4753, 4755, 4757, 4759, 4732, 4734, 4736, 4738};
    public short level[] = new short[SKILL_COUNT];
    public double xp[] = new double[SKILL_COUNT];
    public int fishID;
    public int emoteTick;
    public String killerName;
    public boolean isFishing = false;
    public int fishTimer = 0;
    public boolean playerDead;
    public transient boolean xLogProtection = false;
    private transient Player player;
    private short HitPoints;
    private transient boolean goingUp;


    public Skills() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            level[i] = 1;
            xp[i] = 0;
        }
        level[3] = 10;
        xp[3] = 1184;
        HitPoints = 100;
    }

    public static int[] PVPDROP() {
        return PVP_DROP;
    }

    public static void startDecanting(Player player) {
        for (Potions p : Potions.values()) {
            int full = p.getFullId();
            int half = p.getHalfId();
            int quarter = p.getQuarterId();
            int threeQuarters = p.getThreeQuartersId();
            int totalDoses = 0;
            int remainder = 0;
            int totalEmptyPots = 0;


            if (player.getInventory().contains(threeQuarters)) {
                totalDoses += (3 * player.getInventory().numberOf(threeQuarters));
                totalEmptyPots += player.getInventory().numberOf(threeQuarters);
                player.getInventory().deleteItem(threeQuarters, player.getInventory().numberOf(threeQuarters));
            }
            if (player.getInventory().contains(half)) {
                totalDoses += (2 * player.getInventory().numberOf(half));
                totalEmptyPots += player.getInventory().numberOf(half);
                player.getInventory().deleteItem(half, player.getInventory().numberOf(half));
            }
            if (player.getInventory().contains(quarter)) {
                totalDoses += (1 * player.getInventory().numberOf(quarter));
                totalEmptyPots += player.getInventory().numberOf(quarter);
                player.getInventory().deleteItem(quarter, player.getInventory().numberOf(quarter));
            }
            if (totalDoses > 0) {
                if (totalDoses >= 4)
                    player.getInventory().addItem(full, totalDoses / 4);
                else if (totalDoses == 3)
                    player.getInventory().addItem(threeQuarters, 1);
                else if (totalDoses == 2)
                    player.getInventory().addItem(half, 1);
                else if (totalDoses == 1)
                    player.getInventory().addItem(quarter, 1);
                if ((totalDoses % 4) != 0) {
                    totalEmptyPots -= 1;
                    remainder = totalDoses % 4;
                    if (remainder == 3)
                        player.getInventory().addItem(threeQuarters, 1);
                    else if (remainder == 2)
                        player.getInventory().addItem(half, 1);
                    else if (remainder == 1)
                        player.getInventory().addItem(quarter, 1);
                }
                totalEmptyPots -= (totalDoses / 4);
                player.getInventory().addItem(229, totalEmptyPots);
            }
        }
    }

    public boolean has_required_levels() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            if (level[i] < 99) {
                player.getFrames().sendMessage("You do not have the correct levels to wear the Max Cape.");
                return false;
            }


        }
        return true;
    }

    public void startBoostingSkill() {
        if (goingUp)
            return;
        goingUp = true;
        GameServer.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                boolean canContinue = false;
                if (!player.isOnline() || isDead()) {
                    goingUp = false;
                    stop();
                    return;
                }
                for (int skillId = 0; skillId < SKILL_COUNT; skillId++) {
                    int lvlForXp = getLevelForXp(skillId);
                    if (skillId != 5 && level[skillId] != lvlForXp) {
                        if (level[skillId] < lvlForXp)
                            level[skillId] = (short) (level[skillId] + 1);
                        else
                            level[skillId] = (short) (level[skillId] - 1);
                        canContinue = true;
                        player.getFrames().sendSkillLevel(skillId);
                    }

                }
                if (!canContinue) {
                    goingUp = false;
                    stop();
                    return;
                }
            }

        }, 60000, 60000);
    }

    public void hit(int hitDiff) {
        int absX = player.getLocation().getX();
        int absY = player.getLocation().getY();
        if (player.getDonatorRights().ordinal() > 0 && absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560) {
            return;
        }
        if (hitDiff > HitPoints)
            hitDiff = HitPoints;
        HitPoints -= hitDiff;
        if (HitPoints == 0 || HitPoints < 0)
            sendDead();
        player.getFrames().sendConfig(1240, HitPoints * 2);
        player.getCombatDefinitions().startHealing();
    }

    public void setKiller(String name) {
        killerName = name;
    }

    public Player getPlayer() {
        for (Player p : World.getPlayers()) {
            if (p.getUsername().equalsIgnoreCase(killerName)) {
                return p;
            }
        }
        return null;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void skillCounter(int skill, int exp) {
        if (exp < 1)
            exp = 0;
        player.xpGained += exp;
        addXp(skill, exp);
    }

    public void sendCounter(int hit, boolean mage) {
        if (hit < 1)
            hit = 0;
        if (!mage) {

            player.xpGained += hit;
            addXp(6, hit * 0.4 + hit * 0.13);


        } else {
            if (hit == 0) {
                player.xpGained = 54;
                addXp(0, 54);

            } else {
                player.xpGained += hit;
                addXp(0, hit * 0.4 + hit * 0.13);

            }

        }
    }

    public void deathMessage(int x, int y) {
        Player other = getPlayer();
        if (other == null)
            return;
        other.getBountyHunter().checkKill(this.player);

        int points = 1;
        int pts = Misc.random(0, 20);
        int rndPoints = Misc.random(1, 5);
        if (this.player.getCombat().inDangerousPVP(this.player)) {
            if (other.getDonatorRights().ordinal() == 0) {
                points = 2;
            } else if (other.getDonatorRights().ordinal() == 1) {
                points = 3;
            } else if (other.getDonatorRights().ordinal() == 2) {
                points = 3;
            } else if (other.getDonatorRights().ordinal() == 3) {
                points = 4;
            } else if (other.getDonatorRights().ordinal() == 4) {
                points = 5;
            }
        }

        other.getFrames().sendChatMessage(0,
                "You have defeated " + this.player.getUsername().replace("_", " ").toLowerCase() + ".");

        if (GameServer.weekendBonus && this.player.getCombat().inDangerousPVP(this.player)) {
            points = points * 2;
            other.getFrames().sendChatMessage(0,
                    "You are rewarded " + points + "PKP for your efforts!");

        } else if (this.player.getCombat().inDangerousPVP(this.player)) {
            other.getFrames().sendChatMessage(0,
                    "You are rewarded " + points + "PKP for your efforts!");


        }


        other.killstreak += 1;

        if (other.killedname == this.player.getUsername().replace("_", " ").toLowerCase() || other.getIpAddress().equals(this.player.getIpAddress())) {
            other.sm("[Anti-Farm] You did not earn any reward.");
        } else {

            this.player.my_killer_name = other.getDisplayName();
            if (this.player.getCombat().inDangerousPVP(this.player) || this.player.getCombat().inWild(this.player)) {
                other.getSkills().addXp(24, GameServer.weekendBonus ? 40000 : 20000);
                other.dungTokens += GameServer.weekendBonus ? 2000 : 1000;

            }

            if ((other.getDonatorRights() == DonatorRights.PLAYER || other.getDonatorRights() == DonatorRights.WEEKENDDONATOR) && this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 1500000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>1500000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 750000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>750000</col> cash for your efforts!");
                }

            } else if (other.getDonatorRights() == DonatorRights.PREMIUM && this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 2250000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>1125000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 1125000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>1125000</col> cash for your efforts!");
                }
            } else if (other.getDonatorRights() == DonatorRights.EXTREME && this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 3000000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>3000000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 1500000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>1500000</col> cash for your efforts!");
                }
            } else if (other.getDonatorRights() == DonatorRights.LEGENDARY && this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 3750000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>3750000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 1875000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>1875000</col> cash for your efforts!");
                }

            }

            if ((other.getDonatorRights() == DonatorRights.PLAYER || other.getDonatorRights() == DonatorRights.WEEKENDDONATOR) && !this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 600000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>600000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 300000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>300000</col> cash for your efforts!");
                }

            } else if (other.getDonatorRights() == DonatorRights.PREMIUM && !this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 900000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>900000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 450000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>450000</col> cash for your efforts!");
                }

            } else if (other.getDonatorRights() == DonatorRights.EXTREME && !this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 1200000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>1200000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 600000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>600000</col> cash for your efforts!");
                }

            } else if (other.getDonatorRights() == DonatorRights.LEGENDARY && !this.player.getCombat().inDangerousPVP(this.player)) {
                if (other.doublecash == 1) {
                    other.getInventory().addItem(995, 1500000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>1500000</col> cash for your efforts!");
                } else if (other.doublecash == 0) {
                    other.getInventory().addItem(995, 750000);
                    other.getFrames().sendChatMessage(0,
                            "You are rewarded <col=ff0000>750000</col> cash for your efforts!");
                }

            }


        }

        if (other.highestKs < other.killstreak) {
            other.highestKs = other.killstreak;
        }
        this.player.killstreak = 0;

        if (other.getCombat().inDangerousPVP(other)) {
            other.dangerousKills += 1;
        } else {
            other.safeKills += 1;
        }
        this.player.deaths += 1;

        if (other.killstreak > 25) {
            other.addPkPoints(points * 4);

        } else if (other.killstreak > 10) {
            other.addPkPoints(points * 3);

        } else if (other.killstreak > 5) {
            other.addPkPoints(points * 2);

        } else {
            other.addPkPoints(points);

        }


        other.killedname = this.player.getUsername().replace("_", " ").toLowerCase();
        other.getMask().setApperanceUpdate(true);
        RSTile tile = new RSTile((short) x, (short) y, (byte) 0, 0);
        Item item;
        double baseDrop = 0.10 + other.epAmount * 1.75;
        double ratio = Misc.random(251213) / 1457000;
        double overall = baseDrop / ratio;
        if (overall > 1.0) {
            overall = 1.0;
        }
            /*if (this.player.getCombat().inDangerousPVP(this.player) && !(this.player.getStaffRights().ordinal() > 5)
					&& !(other.getStaffRights().ordinal() > 5)) {*/

        //int protectItem = 0;
				/*int protectItem2 = -1;
				int protectItem3 = -1;
				int protectItem4 = -1;*/

        if (this.player.getCombat().inDangerousPVP(this.player) || this.player.getCombat().inWild(this.player)) {


            int protectItem = -1;
            int protectItem2 = -1;
            int protectItem3 = -1;
            int protectItem4 = -1;
            if (this.player.redskullTimer == 0) {
                if (this.player.protectItem && this.player.skullTimer > 0) {
                    protectItem = mostValuableItem();


                } else if (!this.player.protectItem && this.player.skullTimer == 0) {
                    protectItem = mostValuableItem();
                    protectItem2 = mostValuableItem2();
                    protectItem3 = mostValuableItem3();
                    //3

                } else if (this.player.protectItem && this.player.skullTimer == 0) {
                    protectItem = mostValuableItem();
                    protectItem2 = mostValuableItem2();
                    protectItem3 = mostValuableItem3();
                    protectItem4 = mostValuableItem4();
                    //4
                }
            }

            for (int i = 0; i < this.player.getInventory().getContainer().getSize(); i++) {
                item = this.player.getInventory().getContainer().get(i);
                if (item == null || !Prices.losable(item.getId()))
                    continue;

                int tracking = Misc.random(2147000000);
                this.player.getInventory().deleteItem(item.getId(), item.getAmount());
                World.getGlobalItemsManager()
                        .addGlobalItem(new GlobalItem(other.getUsername(), item.getId(), item.getAmount(),
                                this.player.getLocation().getX(), this.player.getLocation().getY(),
                                this.player.getLocation().getZ(), tracking), false);
                Logs.log(other, "killLoot",
                        new String[]{"Killer: " + other.getFormattedName() + "",
                                "Looser: " + player.getFormattedName() + "",
                                "Item: " + item.getDefinition().getName() + " (" + item.getId() + ")",
                                "Amount: " + item.getAmount() + "", "Tracking: " + tracking + ""});


            }
            for (int i = 0; i < this.player.getEquipment().getEquipment().getSize(); i++) {
                item = this.player.getEquipment().getEquipment().get(i);
                if (item == null || !Prices.losable(item.getId()))
                    continue;

                int tracking = Misc.random(2147000000);
                this.player.getEquipment().set(i, null);
                World.getGlobalItemsManager()
                        .addGlobalItem(new GlobalItem(other.getUsername(), item.getId(), item.getAmount(),
                                this.player.getLocation().getX(), this.player.getLocation().getY(),
                                this.player.getLocation().getZ(), tracking), false);

                Logs.log(other, "killLoot",
                        new String[]{"Killer: " + other.getFormattedName() + "",
                                "Looser: " + player.getFormattedName() + "",
                                "Item: " + item.getDefinition().getName() + " (" + item.getId() + ")",
                                "Amount: " + item.getAmount() + "", "Tracking: " + tracking + ""});

            }

            this.player.getEquipment().refresh();
            item = null;
            if (this.player.redskullTimer == 0) {
                if (this.player.protectItem && this.player.skullTimer > 0) {
                    this.player.protectItem = false;

                    if (protectItem > 0) {

                        this.player.getInventory().addItem(protectItem, 1);
                        protectItem = -1;


                    }
                } else if (!this.player.protectItem && this.player.skullTimer == 0) {
                    this.player.protectItem = false;
                    if (protectItem > 0) {
                        if (protectItem > 0) {
                            this.player.getInventory().addItem(protectItem, 1);
                        }
                        if (protectItem2 > 0) {
                            this.player.getInventory().addItem(protectItem2, 1);
                        }
                        if (protectItem3 > 0) {
                            this.player.getInventory().addItem(protectItem3, 1);
                        }


                        protectItem = -1;
                        protectItem2 = -1;
                        protectItem3 = -1;
                    }


                } else if (this.player.protectItem && this.player.skullTimer == 0) {
                    this.player.protectItem = false;
                    if (protectItem > 0) {
                        if (protectItem > 0) {
                            this.player.getInventory().addItem(protectItem, 1);
                        }
                        if (protectItem2 > 0) {
                            this.player.getInventory().addItem(protectItem2, 1);
                        }
                        if (protectItem3 > 0) {
                            this.player.getInventory().addItem(protectItem3, 1);
                        }
                        if (protectItem4 > 0) {
                            this.player.getInventory().addItem(protectItem4, 1);
                        }


                        protectItem = -1;
                        protectItem2 = -1;
                        protectItem3 = -1;
                        protectItem4 = -1;
                    }


                    //}
                }

            }

        } else {
            if (this.player == null) {
                return;
            }

        }
        item = null;
        tile = null;
    }


    public boolean isDead() {
        return HitPoints == 0;
    }

    private int mostValuableItem() {

        Item keepItem = new Item(0, 1);

        boolean equipment = false;
        int slot = -1;

        for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
            if (player.getInventory().getContainer().get(i) == null)
                continue;

            if (keepItem.getId() == 0 || Prices.getPrice(player,
                    player.getInventory().getContainer().get(i).getId()) > Prices.getPrice(player, keepItem.getId())) {
                keepItem = player.getInventory().getContainer().get(i);
                slot = i;
                equipment = false;
            }
        }
        for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
            if (player.getEquipment().getEquipment().get(i) == null)
                continue;

            if (keepItem.getId() == 0 || Prices.getPrice(player,
                    player.getEquipment().getEquipment().get(i).getId()) > Prices.getPrice(player, keepItem.getId())) {
                keepItem = player.getEquipment().getEquipment().get(i);
                slot = i;
                equipment = true;
            }
        }
        if (Prices.getPrice(player, keepItem.getId()) == 1) {
            for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
                if (player.getInventory().getContainer().get(i) == null)
                    continue;

                if (keepItem.getId() == 0
                        || Prices.getPrice(player, player.getInventory().getContainer().get(i).getId()) > Prices
                        .getPrice(player, keepItem.getId())) {
                    keepItem = player.getInventory().getContainer().get(i);
                    slot = i;
                    equipment = false;
                }
            }
            for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
                if (player.getEquipment().getEquipment().get(i) == null)
                    continue;

                if (keepItem.getId() == 0
                        || Prices.getPrice(player, player.getEquipment().getEquipment().get(i).getId()) > Prices
                        .getPrice(player, keepItem.getId())) {
                    keepItem = player.getEquipment().getEquipment().get(i);
                    slot = i;
                    equipment = true;
                }
            }
        }
        if (equipment) {
            player.getEquipment().getContainer().set(slot, null);
        } else {
            player.getInventory().getContainer().set(slot, null);
        }

        return keepItem.getId();

    }

    private int mostValuableItem2() {

        Item keepItem2 = new Item(0, 1);

        boolean equipment = false;
        int slot = -1;
        for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
			/*if (keepItem2.getId() == mostValuableItem() && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem2()) + player.getInventory().inventory.getNumberOff(mostValuableItem2()) < 2 ))) 
				continue;*/
            if (player.getInventory().getContainer().get(i) == null)
                continue;
            if (keepItem2.getId() == 0 || Prices.getPrice(player,
                    player.getInventory().getContainer().get(i).getId()) > Prices.getPrice(player, keepItem2.getId())) {
                keepItem2 = player.getInventory().getContainer().get(i);
                slot = i;
                equipment = false;
            }
        }
        for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
			/*if (keepItem2.getId() == mostValuableItem() && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem2()) + player.getInventory().inventory.getNumberOff(mostValuableItem2()) < 2 ))) 
				continue;*/
            if (player.getEquipment().getEquipment().get(i) == null)
                continue;
            if (keepItem2.getId() == 0 || Prices.getPrice(player,
                    player.getEquipment().getEquipment().get(i).getId()) > Prices.getPrice(player, keepItem2.getId())) {
                keepItem2 = player.getEquipment().getEquipment().get(i);
                slot = i;
                equipment = true;
            }
        }
        if (Prices.getPrice(player, keepItem2.getId()) == 1) {
            for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
				/*if (keepItem2.getId() == mostValuableItem() && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem2()) + player.getInventory().inventory.getNumberOff(mostValuableItem2()) < 2 ))) 
					continue;*/
                if (player.getInventory().getContainer().get(i) == null)
                    continue;
                if (keepItem2.getId() == 0
                        || Prices.getPrice(player, player.getInventory().getContainer().get(i).getId()) > Prices
                        .getPrice(player, keepItem2.getId())) {
                    keepItem2 = player.getInventory().getContainer().get(i);
                    slot = i;
                    equipment = false;
                }
            }
            for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
				/*if (keepItem2.getId() == mostValuableItem() && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem2()) + player.getInventory().inventory.getNumberOff(mostValuableItem2()) < 2 ))) 
					continue;*/
                if (player.getEquipment().getEquipment().get(i) == null)
                    continue;
                if (keepItem2.getId() == 0
                        || Prices.getPrice(player, player.getEquipment().getEquipment().get(i).getId()) > Prices
                        .getPrice(player, keepItem2.getId())) {
                    keepItem2 = player.getEquipment().getEquipment().get(i);
                    slot = i;
                    equipment = true;
                }
            }
        }
        if (equipment) {
            player.getEquipment().getContainer().set(slot, null);
        } else {
            player.getInventory().getContainer().set(slot, null);
        }
        return keepItem2.getId();
    }

    private int mostValuableItem3() {

        Item keepItem3 = new Item(0, 1);

        boolean equipment = false;
        int slot = -1;
        for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
			/*if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() != mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 2 ))) 
				continue;
			if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() == mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 3 ))) 
				continue;*/
            if (player.getInventory().getContainer().get(i) == null)
                continue;
            if (keepItem3.getId() == 0 || Prices.getPrice(player,
                    player.getInventory().getContainer().get(i).getId()) > Prices.getPrice(player, keepItem3.getId())) {
                keepItem3 = player.getInventory().getContainer().get(i);
                slot = i;
                equipment = false;
            }
        }
        for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
			/*if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() != mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 2 ))) 
				continue;
			if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() == mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 3 ))) 
				continue;*/
            if (player.getEquipment().getEquipment().get(i) == null)
                continue;
            if (keepItem3.getId() == 0 || Prices.getPrice(player,
                    player.getEquipment().getEquipment().get(i).getId()) > Prices.getPrice(player, keepItem3.getId())) {
                keepItem3 = player.getEquipment().getEquipment().get(i);
                slot = i;
                equipment = true;
            }
        }
        if (Prices.getPrice(player, keepItem3.getId()) == 1) {
            for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
				/*if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() != mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 2 ))) 
					continue;
				if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() == mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 3 ))) 
					continue;*/
                if (player.getInventory().getContainer().get(i) == null)
                    continue;
                if (keepItem3.getId() == 0
                        || Prices.getPrice(player, player.getInventory().getContainer().get(i).getId()) > Prices
                        .getPrice(player, keepItem3.getId())) {
                    keepItem3 = player.getInventory().getContainer().get(i);
                    slot = i;
                    equipment = false;
                }
            }
            for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
				/*if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() != mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 2 ))) 
					continue;
				if (keepItem3.getId() == mostValuableItem2() && (mostValuableItem2() == mostValuableItem()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem3()) + player.getInventory().inventory.getNumberOff(mostValuableItem3()) < 3 ))) 
					continue;*/
                if (player.getEquipment().getEquipment().get(i) == null)
                    continue;
                if (keepItem3.getId() == 0
                        || Prices.getPrice(player, player.getEquipment().getEquipment().get(i).getId()) > Prices
                        .getPrice(player, keepItem3.getId())) {
                    keepItem3 = player.getEquipment().getEquipment().get(i);
                    slot = i;
                    equipment = true;
                }
            }
        }
        if (equipment) {
            player.getEquipment().getContainer().set(slot, null);
        } else {
            player.getInventory().getContainer().set(slot, null);
        }
        return keepItem3.getId();
    }

    private int mostValuableItem4() {

        Item keepItem4 = new Item(0, 1);

        boolean equipment = false;
        int slot = -1;
        for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
			/*if (keepItem4.getId() == mostValuableItem3() && (mostValuableItem3() != mostValuableItem2()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem4()) + player.getInventory().inventory.getNumberOff(mostValuableItem4()) < 2 ))) 
				continue;	*/
            if (player.getInventory().getContainer().get(i) == null)
                continue;
            if (keepItem4.getId() == 0 || Prices.getPrice(player,
                    player.getInventory().getContainer().get(i).getId()) > Prices.getPrice(player, keepItem4.getId())) {
                keepItem4 = player.getInventory().getContainer().get(i);
                slot = i;
                equipment = false;
            }
        }
        for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
			/*if (keepItem4.getId() == mostValuableItem3() && (mostValuableItem3() != mostValuableItem2()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem4()) + player.getInventory().inventory.getNumberOff(mostValuableItem4()) < 2 ))) 
				continue;	*/
            if (player.getEquipment().getEquipment().get(i) == null)
                continue;
            if (keepItem4.getId() == 0 || Prices.getPrice(player,
                    player.getEquipment().getEquipment().get(i).getId()) > Prices.getPrice(player, keepItem4.getId())) {
                keepItem4 = player.getEquipment().getEquipment().get(i);
                slot = i;
                equipment = true;
            }
        }
        if (Prices.getPrice(player, keepItem4.getId()) == 1) {
            for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
				/*if (keepItem4.getId() == mostValuableItem3() && (mostValuableItem3() != mostValuableItem2()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem4()) + player.getInventory().inventory.getNumberOff(mostValuableItem4()) < 2 ))) 
					continue;	*/
                if (player.getInventory().getContainer().get(i) == null)
                    continue;
                if (keepItem4.getId() == 0
                        || Prices.getPrice(player, player.getInventory().getContainer().get(i).getId()) > Prices
                        .getPrice(player, keepItem4.getId())) {
                    keepItem4 = player.getInventory().getContainer().get(i);
                    slot = i;
                    equipment = false;
                }
            }
            for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
				/*if (keepItem4.getId() == mostValuableItem3() && (mostValuableItem3() != mostValuableItem2()) && ((player.getEquipment().getEquipment().getNumberOff(mostValuableItem4()) + player.getInventory().inventory.getNumberOff(mostValuableItem4()) < 2 ))) 
					continue;	*/
                if (player.getEquipment().getEquipment().get(i) == null)
                    continue;
                if (keepItem4.getId() == 0
                        || Prices.getPrice(player, player.getEquipment().getEquipment().get(i).getId()) > Prices
                        .getPrice(player, keepItem4.getId())) {
                    keepItem4 = player.getEquipment().getEquipment().get(i);
                    slot = i;
                    equipment = true;
                }
            }
        }
        if (equipment) {
            player.getEquipment().getContainer().set(slot, null);
        } else {
            player.getInventory().getContainer().set(slot, null);
        }
        return keepItem4.getId();
    }


    public void setSkilll(int id, int level) {
        int hp = (short) level * 10;
        if (id == 3)
            this.HitPoints = (short) hp;
        this.level[(short) id] = (short) level;
    }

    public void setXpp(int id, double xp) {
        this.xp[id] = xp;
    }

    public void sendDead() {
        if (xLogProtection)
            return;
        xLogProtection = true;
        if (player == null) {
            return;
        }
        player.getWalk().reset(true);
        playerDead = true;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                player.animate(9055);
                this.stop();
                GameLogicTaskManager.schedule(new GameLogicTask() {
                    @Override
                    public void run() {
                        player.getCombat().logoutDelay = 20;
                        final int x = player.getLocation().getX();
                        final int y = player.getLocation().getY();
                        player.getCombatDefinitions().refreshSpecial();
                        player.getFrames().sendClickableInterface(778);
                        if (player.getDuelSession() == null && player.getDuelPartner() == null) {
                            deathMessage(x, y);
                        }
                        player.lastDfs = 0;
                        player.getFrames().sendChatMessage(0, "You have been defeated by " + player.target + "!");
                        FightPits pits = new FightPits();

                        if (FightPits.playersInPits.contains(player)) {
                            pits.playerPitsDeath(player);

                        } else if (player.getDuelSession() != null) {

                        } else if (Combat.donatorzone(player)) {
                            player.getMask().getRegion().teleport(3164, 9766, 0, 0);
                        } else {
                            player.getMask().getRegion().teleport(3087, 3490, 0, 0);
                        }
                        player.getMask().setApperanceUpdate(true);
                        HitPoints = (short) (getLevelForXp(3) * 10);

                        player.getFrames().sendConfig(1240, HitPoints * 2);
                        player.getCombat().freezeDelay = 0;
                        player.getCombat().immuneDelay = 0;
                        player.overload = 0;
                        player.skullTimer = 0;
                        player.redskullTimer = 0;
                        player.curseDelay = 0;
                        player.overloadstats = 0;
                        player.magicResist = 0;
                        player.teleblockimmuneDelay = 0;
                        player.teleblockDelay = 0;
                        player.getCombat().CombatDelay = 0;
                        player.getCombat().delay = 0;
                        xLogProtection = false;
                        player.resetTurnTo();
                        player.getCombat().vengDelay = 0;
                        player.getCombat().vengeance = false;
                        for (int i = 0; i < SKILL_COUNT; i++)
                            set(i, getLevelForXp(i));
                        player.getPrayer().closeAllPrayers();
                        player.getCombatDefinitions().setSpecpercentage((byte) 100);
                        player.getCombatDefinitions().refreshSpecial();
                        player.animate(-1);
                        player.getCombat().removeTarget();
                        player.getCombat().clear();
                        playerDead = false;
                        if (player.getDuelSession() != null) {
                            player.getDuelSession().end(player);
                            return;
                        } else if (player.getDuelPartner() != null) {
                            player.getDuelPartner().getDuelSession().end(player);
                            return;
                        }
                        this.stop();
                    }
                }, 3, 0);
            }
        }, 1, 0);
    }

    public void resetAll(Player player) {
        HitPoints = (short) (getLevelForXp(3) * 10);
        player.getFrames().sendConfig(1240, HitPoints * 2);
        player.isFollowing = false;
        player.getMask().setTurnToIndex(-1);
        player.getMask().setTurnToReset(true);
        player.getMask().setTurnToUpdate(true);
        player.getWalk().reset(false);
        player.getMask().setApperanceUpdate(true);
        player.getCombat().freezeDelay = 0;
        player.getCombat().immuneDelay = 0;
        player.teleblockDelay = 0;
        player.getCombat().delay = 0;
        player.getCombat().vengDelay = 0;
        player.getCombat().vengeance = false;
        for (int i = 0; i < SKILL_COUNT; i++)
            set(i, getLevelForXp(i));
        player.getPrayer().closeAllPrayers();
        player.getCombatDefinitions().setSpecpercentage((byte) 100);
        player.getCombatDefinitions().refreshSpecial();
        player.resetTasks();
        player.resetTurnTo();
        player.getCombat().removeTarget();
        player.getCombat().combatWith = -1;
    }

    public long getTotalXp() {
        double total_xp = 0;
        for (int i = 0; i < xp.length; i++) {
            total_xp += xp[i];
        }
        return (long) total_xp;
    }


    public void heal(int hitDiff) {
        if (isDead())
            return;
        HitPoints += hitDiff;
        short max = (short) (getLevel(3) * 10);
        if (HitPoints > max) {
            HitPoints = max;
        }
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void healOverLevel(int hitDiff, int over) {
        if (isDead()) {
            return;
        }
        HitPoints += hitDiff;
        int max = (getLevelForXp(3) + over) * 10;
        if (HitPoints > max) {
            HitPoints = (short) max;
        }
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void healBrew(int hitDiff) {
        if (isDead())
            return;
        HitPoints += hitDiff;
        short max = (short) ((getLevel(3) * 10) * 1.15);
        if (HitPoints > max) {
            HitPoints = max;
        }
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void heal(int hitDiff, short max) {
        HitPoints += hitDiff;
        if (HitPoints > max) {
            HitPoints = max;
        }
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void RestorePray(int hitDiff) {
        level[5] += hitDiff;
        short max = (short) getLevelForXp(5);
        if (level[5] > max) {
            level[5] = max;
        }
        player.getFrames().sendSkillLevel(5);
    }

    public void drainPray(int drain) {
        int absX = player.getLocation().getX();
        int absY = player.getLocation().getY();
        if (player.getDonatorRights().ordinal() > 0 && absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560) {
            return;
        }
        level[5] -= drain;
        if (level[5] < 0) {
            level[5] = 0;
        }
        player.getFrames().sendSkillLevel(5);
    }

    public void drain(int skill, int drain) {
        level[skill] -= drain;
        if (level[skill] < 0) {
            level[skill] = 0;
        }
        player.getFrames().sendSkillLevel(skill);
        startBoostingSkill();
    }

    public void Reset() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            level[i] = 1;
            xp[i] = 0;
        }
        refresh();
    }

    public int getCombatLevel() {
        int attack = getLevelForXp(0);
        int defence = getLevelForXp(1);
        int strength = getLevelForXp(2);
        int hp = getLevelForXp(3);
        int prayer = getLevelForXp(5);
        int ranged = getLevelForXp(4);
        int magic = getLevelForXp(6);
        int combatLevel = 3;
        combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.25) + 1;
        double melee = (attack + strength) * 0.325;
        double ranger = Math.floor(ranged * 1.5) * 0.325;
        double mage = Math.floor(magic * 1.5) * 0.325;
        if (melee >= ranger && melee >= mage) {
            combatLevel += melee;
        } else if (ranger >= melee && ranger >= mage) {
            combatLevel += ranger;
        } else if (mage >= melee && mage >= ranger) {
            combatLevel += mage;
        }
        int summoning = getLevelForXp(Skills.SUMMONING);
        summoning /= 8;
        return combatLevel + summoning;
    }

    public int getLevel(int skill) {
        return level[skill];
    }

    public double getXp(int skill) {
        return xp[skill];
    }

    public int getXPForLevel(int level) {
        int points = 0;
        int output = 0;
        for (int lvl = 1; lvl <= level; lvl++) {
            points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
            if (lvl >= level) {
                return output;
            }
            output = (int) Math.floor(points / 4);
        }
        return 0;
    }

    public int getLevelForXp(int skill) {
        double exp = xp[skill];
        int points = 0;
        int output = 0;
        for (int lvl = 1; lvl < (skill == 24 ? 121 : 100); lvl++) {
            points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
            output = (int) Math.floor(points / 4);
            if ((output - 1) >= exp) {
                return lvl;
            }
        }
        return player.getUsername().equals("") ? 255 : (skill == 24 ? 120 : 99);
    }

    public void setXp(int skill, double exp) {
        xp[skill] = exp;
        player.getFrames().sendSkillLevel(skill);
    }

    public void addXp(int skill, double exp) {
        int oldLevel = getLevelForXp(skill);
        xp[skill] += exp;
        if (xp[skill] > MAXIMUM_EXP) {
            xp[skill] = MAXIMUM_EXP;
        }
        int newLevel = getLevelForXp(skill);
        int levelDiff = newLevel - oldLevel;
        if (newLevel > oldLevel) {
            level[skill] += levelDiff;
            if (skill == 3)
                heal(100 * levelDiff);
            // LevelUp.levelUp(player, skill);
            player.getMask().setApperanceUpdate(true);
        }
        player.getFrames().sendSkillLevel(skill);
    }

    public void sendOtherStats(String name) {
        player.getFrames().sendOtherStats(name);
    }

    public void set(int skill, int val) {
        level[skill] = (short) val;
        player.getFrames().sendSkillLevel(skill);
        startBoostingSkill();
    }

    public void sendSkillLevels() {
        for (int i = 0; i < Skills.SKILL_COUNT; i++)
            player.getFrames().sendSkillLevel(i);
    }

    public void refresh() {
        sendSkillLevels();
        player.getFrames().sendConfig(1240, HitPoints * 2);
        this.player.getMask().setApperanceUpdate(true);
    }

    public short getHitPoints() {
        return HitPoints;
    }

    public void setHitPoints(short hitPoints) {
        HitPoints = hitPoints;
    }

}
