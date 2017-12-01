package Auora.model.player;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.Container;
import Auora.model.Item;
import Auora.rscache.ItemDefinitions;
import Auora.util.Misc;
import Auora.util.RSTile;

import java.util.BitSet;


/**
 * @author crezzy
 */
public class DuelArena {

    // ---------------
    public static final byte SUMMONING = 5;
    /**
     * The configurations id of obstacles arena.
     */
    public static final byte OBSTACLES = 6;
    public static final RSTile[] RANDOM_LOCATIONS = {
            RSTile.createRSTile(3358, 3268, 0),
            RSTile.createRSTile(3361, 3271, 0),
            RSTile.createRSTile(3376, 3269, 0),
            RSTile.createRSTile(3377, 3277, 0),
            RSTile.createRSTile(3355, 3276, 0),
            RSTile.createRSTile(3366, 3275, 0)};
    private static final Object[] INTERFACE_SCRIPT = {"", "", "", "", "", -1,
            0, 6, 6, 136, 634 << 16 | 28};
    /**
     * The array of strings used on the second challenge screen.
     */
    private static final String[] DUEL_TEXT = {
    /*
     * During the duel...
	 */
            "You cannot use Ranged attacks.", "You cannot use Melee attacks.",
            "You cannot use Magic attacks.", "You can only use 'fun weapons.'",
            "You cannot forfeit the duel.", "You cannot use drinks.",
            "You cannot use food.", "You cannot use Prayer.",
            "You cannot move.", "There will be obstacles in the arena.",
            "You cannot use special attacks.",
            "<col=ff0000>You can ONLY use Whip/DDS in this duel.",
            /*
			 * Before the duel..
			 */
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",
            "Some worn items will be taken off.",};
    /**
     * The configurations id of summoning enabled.
     */

    //Trying to fix quick prayer glitch
    public boolean ancientcurses;
    public boolean[][] quickPrayers = {new boolean[29], new boolean[20]};
    public transient boolean usingQuickPrayer;
    public Stage currentStage = Stage.FIRST_SCREEN;
    RSTile oMin = RSTile.createRSTile(3335, 3227, 0);
    RSTile oMax = RSTile.createRSTile(3355, 3237, 0);
    RSTile nMin = RSTile.createRSTile(3366, 3227, 0);
    RSTile nMax = RSTile.createRSTile(3386, 3237, 0);
    //public int prayerDelay = 0;
    private transient boolean[][] onPrayers;
    private transient boolean[] boostingPray;
    private transient boolean drainingprayer;
    private boolean quickPrayersOn;
    /**
     * The bitset of rules.
     */
    private BitSet rules = new BitSet();
    private Player p1;
    private Player p2;
    private boolean isStaked;
    private Container<Item> p1Items = new Container<Item>(9, false),
            p2Items = new Container<Item>(9, false);
    private boolean p1Accept = false, p2Accept = false;
    private String type;
    private int slot;

    public DuelArena(Player p1, Player p2, boolean isStaked) {
        this.p1 = p1;
        this.p2 = p2;
        this.isStaked = isStaked;
        this.currentStage = Stage.FIRST_SCREEN;
        p1.didRequestDuel = Boolean.FALSE;
        p2.didRequestDuel = Boolean.FALSE;
        openFirstInterface(p1);
        openFirstInterface(p2);
        refreshConfigs(0);
        refreshScreen(false, 0);
    }

    /**
     * Sets a rule.
     *
     * @param rule The rule to set.
     * @return {@code True}.
     */
    public boolean setRule(Rules rule, boolean flag) {
        rules.set(rule.ordinal(), flag);
        return true;
    }

    /**
     * Gets the flag of the given rule.
     *
     * @param rule The rule.
     * @return {@code True} if the rule has been enabled, <br>
     * {@code false} if not.
     */
    public boolean getRule(Rules rule) {
        return rules.get(rule.ordinal());
    }

    public boolean getRule(int i) {
        return rules.get(i);
    }

    /**
     * Swaps a rule.
     *
     * @param player The player.
     * @param other  The other player.
     * @param rule   The rule.
     * @return {@code True}.
     */
    public boolean swapRule(Player player, Player other, Rules rule) {
        if (canSetRule(player, rule)) {
            rules.set(rule.ordinal(), !rules.get(rule.ordinal()));
            resetAccept();
            refreshScreen(true, 1);
            return true;
        }
        return false;
    }

    /**
     * Checks if the rule can be changed.
     *
     * @param player The player.
     * @param rule   The rule.
     * @return {@code True} if so, {@code false} if not.
     */
    public boolean canSetRule(Player player, Rules rule) {
        if (rules.get(rule.ordinal())) {
            return true;
        }
        switch (rule) {
            case MAGIC:
                if (rules.get(Rules.MELEE.ordinal())
                        && rules.get(Rules.RANGE.ordinal())) {
                    player.getFrames()
                            .sendChatMessage(0,
                                    "You have to be able to use atleast one combat style in a duel.");
                    return false;
                }
                return true;
            case MELEE:
                if (rules.get(Rules.MAGIC.ordinal())
                        && rules.get(Rules.RANGE.ordinal())) {
                    player.getFrames()
                            .sendChatMessage(0,
                                    "You have to be able to use atleast one combat style in a duel.");
                    return false;
                }
                return true;
            case RANGE:
                if (rules.get(Rules.MELEE.ordinal())
                        && rules.get(Rules.MAGIC.ordinal())) {
                    player.getFrames()
                            .sendChatMessage(0,
                                    "You have to be able to use atleast one combat style in a duel.");
                    return false;
                }
                return true;
            case OBSTACLES:
                if (rules.get(Rules.WHIP_DDS.ordinal())) {
                    setRule(Rules.WHIP_DDS, false);
                }
                if (rules.get(Rules.MOVEMENT.ordinal())) {
                    setRule(Rules.MOVEMENT, false);
                }
                return true;
            case MOVEMENT:
            case WHIP_DDS:
                if (rules.get(Rules.OBSTACLES.ordinal())) {
                    setRule(Rules.OBSTACLES, false);
                }
                return true;
            case WEAPON:
                if (rules.get(Rules.FUN_WEAPONS.ordinal())) {
                    player.getFrames()
                            .sendChatMessage(0,
                                    "You can't have weapons disabled while the fun weapons rule is active.");
                    return false;
                }
                return true;
            case FUN_WEAPONS:
                if (rules.get(Rules.WEAPON.ordinal())) {
                    player.getFrames()
                            .sendChatMessage(0,
                                    "You can't have fun weapons active while weapons are disabled.");
                    return false;
                }
                return true;
        }
        return true;
    }

    private void refreshScreen(boolean sendConfigs, int config) {
        p1.getFrames().sendItems(134, p1Items, false);
        p2.getFrames().sendItems(134, p2Items, false);
        p1.getFrames().sendItems(134, p2Items, true);
        p2.getFrames().sendItems(134, p1Items, true);
        if (sendConfigs) {
            int value = 0;
            for (int i = 0; i < Rules.values().length; i++) {
                if (rules.get(i)) {
                    value += Rules.values()[i].value;
                }
            }
            refreshConfigs(value);
        }
    }

    private void refreshConfigs(int config) {
        p1.getFrames().sendConfig(286, config);
        p2.getFrames().sendConfig(286, config);
    }

    public void stakeItem(Player p, int slot, int amt) {
        if (currentStage != Stage.FIRST_SCREEN)
            return;
        if (getContainer(p).getTakenSlots() >= 9) {
            p.getFrames().sendChatMessage(0,
                    "You cannot put any more items in this stake");
            return;
        }
        if (p.getInventory().getContainer().get(slot).getId() == 15441) {
            p.sendMessage("You can't stake this item.");
            return;
        }
        Item inventoryItem = p.getInventory().getContainer().get(slot);
        if (inventoryItem != null) {
            if (amt > p.getInventory().getContainer()
                    .getNumberOf(inventoryItem)) {
                amt = p.getInventory().getContainer()
                        .getItemCount(inventoryItem.getId());
            }
        }
        Item item = new Item(p.getInventory().getContainer().get(slot).getId(),
                amt);
        resetAccept();
        if (item != null) {
            if (p.getInventory().getContainer().getItemCount(item.getId()) < amt) {
                item.setAmount(p.getInventory().getContainer()
                        .getItemCount(item.getId()));
            }
            if (getContainer(p).getFreeSlots() < amt
                    && !p.getInventory().getContainer().get(slot)
                    .getDefinition().isNoted()
                    && !p.getInventory().getContainer().get(slot)
                    .getDefinition().isStackable()) {
                item.setAmount(getContainer(p).getFreeSlots());
            }
            getContainer(p).add(item);
            p.getInventory()
                    .getContainer()
                    .remove(new Item(p.getInventory().getContainer().get(slot)
                            .getId(), amt));
            p.getInventory().refresh();
        }
        refreshScreen(false, 0);
    }

    private Container<Item> getContainer(Player p) {
        return p.equals(p1) ? p1Items : p2Items;
    }

    private void openFirstInterface(Player p) {
        this.currentStage = Stage.FIRST_SCREEN;
        if (isStaked) {
            p.getFrames().sendString("<col=ff0000>Whip/DDS only", 631, 51);
            p.getFrames().sendString("unlimited!", 631, 91);
            p.getFrames().sendString("unlimited!", 631, 92);
            p.getFrames().sendString("", 631, 28);
            p.getFrames().sendString(
                    Misc.formatPlayerNameForDisplay(getOpponent(p)
                            .getUsername()), 631, 25);
            p.getFrames().sendString(
                    Integer.toString(getOpponent(p).getSkills()
                            .getCombatLevel()), 631, 27);
            p.getFrames().sendInventoryInterface(628);
            p.getFrames().sendInterface(631);
            p.getFrames().sendDuelOptions();
        } else {
            p.getFrames().sendInterface(637);
            p.getFrames().sendString(
                    Misc.formatPlayerNameForDisplay(getOpponent(p)
                            .getUsername()), 637, 15);
            p.getFrames().sendString(
                    Integer.toString(getOpponent(p).getSkills()
                            .getCombatLevel()), 637, 17);
        }
    }

    public Player getOpponent(Player p) {
        return p == p1 ? p2 : p1;
    }

    public void handleButtons(Player p, int opcode, int slot, int buttonId) {
        switch (currentStage) {
            case FIRST_SCREEN:
                if (buttonId == 101) {
                    handleAcceptButton(p);
                    return;
                }
                if (buttonId == 106) {
                    duelCancelled(p);
                    return;
                }
                if (buttonId == 0) {
                    switch (opcode) {
                        case 79:
                            stakeItem(p, slot, 1);
                            break;
                        case 24:
                            stakeItem(p, slot, 5);
                            break;
                        case 48:
                            stakeItem(p, slot, 10);
                            break;
                        case 40:
                            Item item = p.getInventory().getContainer().get(slot);
                            stakeItem(p, slot, p.getInventory().getContainer()
                                    .getNumberOf(item));// getContainer(slot).getAmount());
                            break;
                        case 13:
                            initiateDuelX(p, slot, "OFFER");
                            break;
                    }
                } else if (buttonId < 90) {
                    Rules rule = null;
                    switch (buttonId) {
                        case 30: // No range
                            rule = Rules.RANGE;
                            break;
                        case 32: // No melee
                            rule = Rules.MELEE;
                            break;
                        case 34: // No magic
                            rule = Rules.MAGIC;
                            break;
                        case 36: // Fun weapons
                            rule = Rules.FUN_WEAPONS;
                            break;
                        case 38: // No forfeit
                            rule = Rules.FORFEIT;
                            break;
                        case 40: // No drinks
                            rule = Rules.DRINKS;
                            break;
                        case 42: // No food
                            rule = Rules.FOOD;
                            break;
                        case 44: // No prayer
                            rule = Rules.PRAYER;
                            break;
                        case 46: // No movement
                            rule = Rules.MOVEMENT;
                            break;
                        case 48: // Obstacles
                            rule = Rules.OBSTACLES;
                            break;
                        case 50: // No special attacks
                            rule = Rules.SPECIAL_ATTACKS;
                            break;
                        case 52: // Summoning
                            rule = Rules.WHIP_DDS;
                            break;
                        case 56: // No helms
                            rule = Rules.HAT;
                            break;
                        case 57: // No capes
                            rule = Rules.CAPE;
                            break;
                        case 58: // No amulet
                            rule = Rules.AMULET;
                            break;
                        case 59: // No arrows
                            rule = Rules.ARROW;
                            break;
                        case 60: // No weapon
                            p.getFrames().sendChatMessage(0,
                                    "Boxing has been disabled temporarily.");
                            break;
                        case 61: // No body
                            rule = Rules.BODIE;
                            break;
                        case 62: // No shield
                            rule = Rules.SHIELD;
                            break;
                        case 63: // No legs
                            rule = Rules.LEG;
                            break;
                        case 64: // No rings
                            rule = Rules.RING;
                            break;
                        case 65: // No boots
                            rule = Rules.BOOT;
                            break;
                        case 66: // No gloves
                            rule = Rules.GLOVE;
                            break;
                    }
                    if (rule != null) {
                        swapRule(p, p.getDuelPartner(), rule);
                    }
                    resetAccept();
                } else {
                    switch (opcode) {
                        case 6:
                            removeItem(p, slot, 1);
                            break;
                        case 13:
                            removeItem(p, slot, 5);
                            break;
                        case 0:
                            removeItem(p, slot, 10);
                            break;
                        case 15:
                            Item item = p.getInventory().getContainer().get(slot);
                            removeItem(p, slot, p.getInventory().getContainer()
                                    .getNumberOf(item));// getContainer(slot).getAmount());
                            break;
                        case 46:
					/*
					 * InputHandler.requestIntegerInput(player, 2,
					 * "Please enter an amount:"); player.setAttribute("inputId", 6);
					 * player.setAttribute("slotId", slot);
					 */
                            break;
				/*
				 * case 58: player.getFrames().sendMessage(0,
				 * player.getInventory().getContainer
				 * ().get(slot).getDefinition().getExamine()); break;
				 */
                    }
                }
                break;
            case SECOND_SCREEN:
                System.out.println("LOL " + buttonId);
                if (buttonId == 106) {
                    duelCancelled(p);
                    return;
                }
                if (buttonId == 35 || buttonId == 53) {
                    handleAcceptButton(p);
                    return;
                }
                break;
            default:
                break;
        }
    }

    private void handleAcceptButton(Player p) {
        switch (currentStage) {
            case FIRST_SCREEN:
			/*
			 * String check = checkRules(); if(check != null) {
			 * player.getFrames().sendString(check, isStaked ? 631 : 637, isStaked ?
			 * 28 : 44); return; }
			 */
                if (p.equals(p1)) {
                    p1Accept = true;
                    if (p1Accept && p2Accept) {
                        displaySecond(p1, p2);
                    } else {
                        p1.getFrames().sendString("Waiting for other player...",
                                isStaked ? 631 : 637, isStaked ? 28 : 44);
                        p2.getFrames().sendString("Other player has accepted",
                                isStaked ? 631 : 637, isStaked ? 28 : 44);
                    }
                } else {
                    p2Accept = true;
                    if (p1Accept && p2Accept) {
                        displaySecond(p1, p2);
                    } else {
                        p2.getFrames().sendString("Waiting for other player...",
                                isStaked ? 631 : 637, isStaked ? 28 : 44);
                        p1.getFrames().sendString("Other player has accepted",
                                isStaked ? 631 : 637, isStaked ? 28 : 44);
                    }
                }
                break;
            case SECOND_SCREEN:
                if (p.equals(p1)) {
                    p1Accept = true;
                    if (p1Accept && p2Accept) {
                        startDuel(p1, p2);
                    } else {
                        p1.getFrames().sendString("Waiting for other player...",
                                626, 45);
                        p2.getFrames().sendString("Other player has accepted", 626,
                                45);
                    }
                } else {
                    p2Accept = true;
                    if (p1Accept && p2Accept) {
                        startDuel(p1, p2);
                    } else {
                        p2.getFrames().sendString("Waiting for other player...",
                                626, 45);
                        p1.getFrames().sendString("Other player has accepted", 626,
                                45);
                    }
                }
                break;
            case IN_DUEL:
                break;
            default:
                break;
        }

    }

    private void startDuel(Player... players) {
        for (Player p : players) {
            p.getFrames().closeInterface();
            p.getSkills().resetAll(p);
            checkEquipment(p);
            if (p.getEquipment().get(3) != null)
                checkLoadWhip(p, p.getEquipment().get(3).getId());
            check2h(p);
            p.getPrayer().closeAllPrayers();
            if (this.quickPrayersOn) {
                p.getPrayer().closeAllPrayers();
                p.getFrames().sendBConfig(182, 0);
                this.quickPrayersOn = false;
            }
            if (!getRule(Rules.MOVEMENT)) {
                sendToArena(p);
            }
            p.isInDuelArena = Boolean.TRUE;
            p.cantMove = Boolean.TRUE;
            p.getFrames().sendIConfig(638, 1, false);
        }
        if (getRule(Rules.MOVEMENT)) {
            send_to_no_movement_arena(p1, p2);
        }
        GameLogicTaskManager.schedule(new GameLogicTask() {
            int time = 3;

            @Override
            public void run() {
                if (time > 0) {
                    if (time == 3) {
                        //p1.getHinticonmanager().addHintIcon(p2, 1, -1, true);
                        //p2.getHinticonmanager().addHintIcon(p1, 1, -1, true);
                        p1.getFrames().sendChatMessage(
                                0,
                                "You have challenged <col=ffff00><shad=ffffff>" + Misc.formatPlayerNameForDisplay(p2.getUsername().replaceAll("_", " "))
                                        + "</col></shad> to a duel, Good Luck!");
                        p2.getFrames().sendChatMessage(
                                0,
                                "You have challenged <col=ffff00><shad=ffffff>" + Misc.formatPlayerNameForDisplay(p1.getUsername().replaceAll("_", " "))
                                        + "</col></shad> to a duel, Good Luck!");
                    }
                    p1.getMask().setLastChatMessage(new ChatMessage(0, 0, "" + time + ""));
                    p1.getMask().setChatUpdate(true);
                    p2.getMask().setLastChatMessage(new ChatMessage(0, 0, "" + time + ""));
                    p2.getMask().setChatUpdate(true);
                    p1.duelTimer = true;
                    p2.duelTimer = true;
                    time--;
                } else {
                    p1.getMask().setLastChatMessage(new ChatMessage(0, 0, "FIGHT!"));
                    p1.getMask().setChatUpdate(true);
                    p2.getMask().setLastChatMessage(new ChatMessage(0, 0, "FIGHT!"));
                    p2.getMask().setChatUpdate(true);
                    p1.duelTimer = false;
                    p2.duelTimer = false;
                    currentStage = Stage.IN_DUEL;
                    p1.cantMove = false;
                    p2.cantMove = false;
                    // p1.removeAttribute("cantMove");
                    // p2.removeAttribute("cantMove");
                    this.stop();
                }
            }
        }, 0, 1, 1);
    }

    private void sendToArena(Player p) {
        if (getRule(Rules.OBSTACLES)) {
            RSTile tile1 = RSTile.createRSTile(
                    oMin.getX() + Misc.random(oMax.getX() - oMin.getX()),
                    oMin.getY() + Misc.random(oMax.getY() - oMin.getY()));
            p.getMask().getRegion()
                    .teleport(tile1.getX(), tile1.getY(), 0, 0);
        } else {
            RSTile tile2 = RSTile.createRSTile(
                    nMin.getX() + Misc.random(nMax.getX() - nMin.getX()),
                    nMin.getY() + Misc.random(nMax.getY() - nMin.getY()));
            p.getMask().getRegion()
                    .teleport(tile2.getX(), tile2.getY(), 0, 0);
        }
    }

    private void send_to_no_movement_arena(Player p1, Player p2) {
        RSTile tile2 = RSTile.createRSTile(
                nMin.getX() + Misc.random(nMax.getX() - nMin.getX()),
                nMin.getY() + Misc.random(nMax.getY() - nMin.getY()));
        p1.getMask().getRegion()
                .teleport(tile2.getX(), tile2.getY(), 0, 0);
        p2.getMask().getRegion()
                .teleport(tile2.getX() + 1, tile2.getY(), 0, 0);
    }

    /**
     * Removes the equipment upon accept.
     *
     * @param player The player.
     */
    private void checkEquipment(Player player) {
        int slot;
        for (int i = 12; i < Rules.values().length; i++) {
            if (rules.get(i)) {
                slot = i - 12;
                slot = slot == 6 ? 7 : slot > 6 ? slot + 2 : slot;
                player.getEquipment().removeSlot(slot, true);
            }
        }
    }

	/*
	 * public boolean canEquip(int slot) { return ruleActivated(slot + 11); }
	 */

    public boolean canEquip(Player player, int slot) {
        if (rules.get(slot + 12)) {
            player.getFrames().sendChatMessage(
                    0,
                    "You can't equip "
                            + Rules.values()[slot + 12].name().toLowerCase()
                            + "s for this duel!");
            return false;
        }

        return true;
    }

    public boolean equipWep(Player p, int itemId) {
        if (!checkWeapon(p, itemId)) {
            p.getFrames().sendChatMessage(0, "You can't equip this item for this duel!");
            return false;
        }
        return true;
    }

    private void displaySecond(Player... players) {
        try {
            currentStage = Stage.SECOND_SCREEN;
            resetAccept();
            for (Player p : players) {
                // player.getFrames().closeInventory();
                if (isStaked) {
                    if (!p.getInventory().getContainer()
                            .hasSpaceFor(getContainer(getOpponent(p)))
                            || p.getInventory().getFreeSlots() < amtOfItmsRemoved(p)) {
                        p.getFrames()
                                .sendChatMessage(0,
                                        "You do not have enough space in your inventory for the stake!");
                        duelCancelled(p);
                        return;
                    }
                }
                int duringDuelOffset = 33;
                int beforeDuelOffset = 41;
                boolean wornItemWarning = false;
                for (int i = 28; i <= 44; i++) {
                    if (i != 32) {
                        p.getFrames().sendString("", 626, i);
                    }
                }
                p.getFrames().sendString("Modified stats will be restored.",
                        626, beforeDuelOffset);
                beforeDuelOffset = 28;
                for (int i = 0; i < rules.size(); i++) {
                    if (rules.get(i)) {
                        if (i == 17) {
                            p.getFrames()
                                    .sendString(
                                            "You can't use two-handed weapons, like bows.",
                                            626, duringDuelOffset);
                            duringDuelOffset++;
                        }
                        if (i > 11) {
                            if ((i >= 11 && i <= 22) && i != 20) {
                                if (wornItemWarning) {
                                    continue;
                                } else {
                                    wornItemWarning = true;
                                }
                            }
                            p.getFrames().sendString(DUEL_TEXT[i], 626,
                                    beforeDuelOffset);
                            beforeDuelOffset++;
                            if (beforeDuelOffset == 42) {
                                beforeDuelOffset = 28;
                            }
                        } else {
                            p.getFrames().sendString(DUEL_TEXT[i], 626,
                                    duringDuelOffset);
                            duringDuelOffset++;
                        }
                    }
                }
                getOpponent(p).getFrames().sendString("", 626, 45);
                if (getContainer(p1).getFreeSlots() < 9) {
                    p1.getFrames().sendString("", 626, 25);
                    p2.getFrames().sendString("", 626, 26);
                }
                if (getContainer(p2).getFreeSlots() < 9) {
                    p2.getFrames().sendString("", 626, 25);
                    p1.getFrames().sendString("", 626, 26);
                }
                p.getFrames().sendInterface(626);
            }
            currentStage = Stage.SECOND_SCREEN;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int amtOfItmsRemoved(Player p) {
        int amt = 0;
		/*
		 * for (int i = NO_HATS; i <= NO_ARROWS; i++) { if(ruleActivated(i)) {
		 * if(player.getEquipment().get(DUEL_SLOT_IDS[i - 11]) != null) { amt++; } }
		 * }
		 */
        return amt;
    }

	/*
	 * public boolean ruleActivated(int rule) { return (rulesActivated &
	 * DUEL_BUTTON[rule][1]) != 0; }
	 */

    public void removeItem(Player p, int slot, int amt) {
        resetAccept();
        Item item = new Item(getContainer(p).get(slot).getId(), amt);
        if (item != null) {
            if (getContainer(p).getItemCount(item.getId()) < amt) {
                item.setAmount(getContainer(p).getItemCount(item.getId()));
            }
            if (p.getInventory().getFreeSlots() < amt
                    && !getContainer(p).get(slot).getDefinition().isNoted()
                    && !getContainer(p).get(slot).getDefinition().isStackable()) {
                item.setAmount(p.getInventory().getFreeSlots());
            }
            p.getInventory()
                    .getContainer()
                    .add(new Item(getContainer(p).get(slot).getId(), item
                            .getAmount()));
            p.getInventory().refresh();
            getContainer(p).remove(item);
            refreshScreen(false, 0);
        }

    }

    private void resetAccept() {
        p1Accept = p2Accept = false; // let me check something
    }

    public void duelCancelled(Player canceller) {
        for (Item itemAtIndex : p1Items.toArray()) {
            if (itemAtIndex != null) {
                p1.getInventory().getContainer().add(itemAtIndex);
                p1.getInventory().refresh();
            }
        }
        for (Item itemAtIndex : p2Items.toArray()) {
            if (itemAtIndex != null) {
                p2.getInventory().getContainer().add(itemAtIndex);
                p2.getInventory().refresh();
            }
        }
        endDuel();
        p1.getInventory().refresh();
        p2.getInventory().refresh();
        if (canceller == p1) {
            p1.getFrames().sendChatMessage(0,
                    "You have declined the challenge.");
            p2.getFrames().sendChatMessage(0,
                    "<col=ff0000>Other player declined!");
        } else {
            p2.getFrames().sendChatMessage(0,
                    "You have declined the challenge.");
            p1.getFrames().sendChatMessage(0,
                    "<col=ff0000>Other player declined!");
        }
    }

    private void endDuel() {
        this.currentStage = null;
        p1.isInDuelArena = Boolean.FALSE;
        p2.isInDuelArena = Boolean.FALSE;
        p1.setDuelSession(null);
        p1.setDuelPartner(null);
        p2.setDuelSession(null);
        p2.setDuelPartner(null);
        p1.getFrames().closeInterface();
        p2.getFrames().closeInterface();
        p1.getFrames().closeInventory();
        p2.getFrames().closeInventory();
    }

	/*
	 * public int configForButton(int buttonId) { for(int[] array : DUEL_BUTTON)
	 * { if(array[0] == buttonId) { return array[1]; } } return -1; }
	 */

    public void displayWinnings(Player p, Container<Item> items) {
        Player loser = getOpponent(p);
        if (items.size() != 0) {
            p.getFrames().sendInterface(634);
            p.getFrames().sendString(loser.getSkills().getCombatLevel() + "", 634, 33);
            p.getFrames().sendString(Misc.formatPlayerNameForDisplay(loser.getUsername()) + "", 634, 33);
            final Object[] opts1 = new Object[]{"", "", "", "", "", -1, 0, 6, 6, 136, 634 << 16 | 28};
            p.getFrames().sendAMask(1026, 634, 28, 0, 35);
            if (items != null) {
                p.getFrames().sendClientScript(149, opts1, "IviiiIsssss");
                p.getFrames().sendItems(136, items, false);
            }
        } else {
            p.getFrames().sendInterface(633);
            p.getFrames().sendString(loser.getSkills().getCombatLevel() + "", 633, 30);
            p.getFrames().sendString(Misc.formatPlayerNameForDisplay(loser.getUsername()) + "", 633, 31);
        }
    }

    public void end(Player loser) {
        try {
            Player winner = getOpponent(loser);
            if (isStaked && winner.round2) {
                for (Item itemAtIndex : getContainer(winner).toArray()) {
                    if (itemAtIndex != null) {
                        winner.getInventory().getContainer().add(itemAtIndex);
                        //SeperateLogs.stake(winner, loser, itemAtIndex, loser.getDisplayName());
                    }
                }
                for (Item itemAtIndex : getContainer(loser).toArray()) {
                    if (itemAtIndex != null) {
                        winner.getInventory().getContainer().add(itemAtIndex);
                        //SeperateLogs.stake(winner, loser, itemAtIndex, loser.getDisplayName());
                    }
                }
                winner.getInventory().refresh();
            }
            RSTile loc1 = RANDOM_LOCATIONS[Misc.random(RANDOM_LOCATIONS.length - 1)];
            RSTile loc2 = RANDOM_LOCATIONS[Misc.random(RANDOM_LOCATIONS.length - 2)];
            loser.getMask().getRegion().teleport(loc1);
            loser.getSkills().resetAll(loser);
            getOpponent(loser).getMask().getRegion().teleport(loc2);
            if (isStaked
                    && !winner.round2 && !loser.round2) {
                for (Item itemAtIndex : getContainer(winner).toArray()) {
                    if (itemAtIndex != null) {
                        winner.getInventory().getContainer().add(itemAtIndex);
                        //what winner put
                    }
                }
                for (Item itemAtIndex : getContainer(loser).toArray()) {
                    if (itemAtIndex != null) {
                        winner.getInventory().getContainer().add(itemAtIndex);
                        //what loser put
                    }
                }
                winner.getInventory().refresh();
                winner.totalStakes += 1;
                winner.stakeWins += 1;
                loser.totalStakes += 1;
                loser.stakeLosses += 1;
                winner.getFrames()
                        .sendChatMessage(0, "You have won, congratulations!");
                winner.getSkills().resetAll(winner);
                currentStage = null;
                endDuel();
            } else {
                endDuel();
            }
            p1.isInDuelArena = Boolean.FALSE;
            p2.isInDuelArena = Boolean.FALSE;
            p1.setDuelSession(null);
            p1.setDuelPartner(null);
            p2.setDuelSession(null);
            p2.setDuelPartner(null);
            p1.cantMove = Boolean.FALSE;
            p2.cantMove = Boolean.FALSE;
            displayWinnings(winner, getContainer(loser));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean checkWeapon(Player p, int itemID) { // leon
        String weapon = ItemDefinitions.forID(itemID).name.toLowerCase().replaceAll(" ", "_");
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.WHIP_DDS)) {
            if (weapon.contains("whip") || weapon.contains("tentacle") || weapon.contains("dragon_dagger")) {
                return true;
            }
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.WHIP_DDS)) {
            if (weapon.contains("whip") || weapon.contains("tentacle") || weapon.contains("dragon_dagger")) {
                return true;
            }
        }

        return false;
    }

    public boolean checkLoadWhip(Player p, int itemID) { // leon
        String weapon = ItemDefinitions.forID(itemID).name.toLowerCase();
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.WHIP_DDS)) {
            if (!weapon.contains("whip")) {
                p.getEquipment().removeSlot(3);
                p.sm("You're only allowed whip/dds in this duel.");
                return false;
            }
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.WHIP_DDS)) {
            if (!weapon.contains("whip")) {
                p.getEquipment().removeSlot(3);
                p.sm("You're only allowed whip/dds in this duelasdasd.");
                return false;
            }
        }

        return true;
    }

    public void check2h(Player p) {
        System.out.println("taking off 2h");
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.SHIELD)) {
            Item item = p.getEquipment().get(3);
            DuelArena arena = p.getDuelSession();
            if (arena.getRule(Rules.SHIELD) && Equipment.isTwoHanded(item.getDefinition())) {
                p.getFrames().sendChatMessage(0, "You cannot wear 2h weapons this duel!");
                p.getEquipment().removeSlot(3);
            }
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.SHIELD)) {
            Item item = p.getEquipment().get(3);
            DuelArena arena = p.getDuelPartner().getDuelSession();
            if (arena.getRule(Rules.SHIELD) && Equipment.isTwoHanded(item.getDefinition())) {
                p.getFrames().sendChatMessage(0, "You cannot wear 2h weapons this duel!");
                p.getEquipment().removeSlot(3);
                System.out.println("taking off 2h");
            }
        }

    }

    public void finishCompletely(Player p) {
		/*
		 * for (Item itemAtIndex : getContainer(getOpponent(player)).toArray()) { if
		 * (itemAtIndex != null) {
		 * player.getInventory().getContainer().add(itemAtIndex); } }
		 */
        p.getInventory().refresh();
        endDuel();

    }

	/*
	 * public boolean canUseStyle(FightType type) { switch (type) { case MAGIC:
	 * return ruleActivated(NO_MAGIC); case MELEE: return
	 * ruleActivated(NO_MELEE); case RANGE: return ruleActivated(NO_RANGE); }
	 * return false; }
	 */

    public void initiateDuelX(Player player, int slot, String string) {
        this.type = string;
        this.slot = slot;
        player.duelOption = true;

        Object[] obj = null;
        if (string.equals("OFFER")) {
            obj = new Object[]{"Stake X:"};
        } else if (string.equals("REMOVE")) {
            obj = new Object[]{"Remove X:"};
        }
        player.getFrames().sendClientScript(108, obj, "s");
    }

    public void finalizeDuelX(Player player, int amt) {
        if (amt == 0)
            amt = 1;
        if (type.equals("OFFER")) {
            stakeItem(player, slot, amt);
        } else if (type.equals("REMOVE")) {
            removeItem(player, slot, amt);
        }
        player.duelOption = false;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public enum Stage {
        FIRST_SCREEN, SECOND_SCREEN, IN_DUEL
    }

    /**
     * The rules enum.
     *
     * @author Emperor
     */
    public enum Rules {
        RANGE(16), MELEE(32), MAGIC(64), FUN_WEAPONS(4096), FORFEIT(1), // TODO
        DRINKS(128), FOOD(256), PRAYER(512), MOVEMENT(2), OBSTACLES(1024), SPECIAL_ATTACKS(
                8192), WHIP_DDS(268435456), HAT(16384), CAPE(32768), AMULET(
                65536), WEAPON(131072), BODIE(262144), SHIELD(524288), LEG(
                2097152), GLOVE(8388608), BOOT(16777216), RING(67108864), ARROW(
                134217728);

        /**
         * The configuration value.
         */
        private final int value;

        Rules(int configurationValue) {
            this.value = configurationValue;
        }
    }
}
