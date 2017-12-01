package Auora.skills.prayer;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.player.Player;

import java.io.Serializable;

public class Prayer implements Serializable {

    public final static int[][][] closePrayers = {{ // normal prayer book
            {0, 5, 13}, // Skin prayers 0
            {1, 6, 14}, // Strength prayers 1
            {2, 7, 15}, // Attack prayers 2
            {3, 11, 20, 27, 28}, // Range prayers 3
            {4, 12, 21, 29}, // Magic prayers 4
            {8, 9, 26}, // Restore prayers 5
            {10}, // Protect item prayers 6
            {17, 18, 19}, // Protect prayers 7
            {16}, // Other protect prayers 8
            {22, 23, 24}, // Other special prayers 9
            {25, 27} // Other prayers 10
    }, { // ancient prayer book
            {0}, // Protect item prayers 0
            {1, 2, 3, 4}, // sap prayers 1
            {5}, // other prayers 2
            {7, 8, 9, 17, 18}, // protect prayers 3
            {6}, // other protect prayers 4
            {10, 11, 12, 13, 14, 15, 16}, // leech prayers 5
            {19}, // other prayers
    }};
    private static final long serialVersionUID = -3850249845394162505L;
    private final static int[] CONFIG_VALUES = {
            1, 2, 4, 262144, 524288, 8,
            16, 32, 64, 128, 256, 1048576, 2097152, 512, 1024, 2048, 16777216,
            4096, 8192, 16384, 4194304, 8388608, 32768, 65536, 131072,
            33554432, 134217728, 67108864, 536870912, 268435456};
    private final static int[][] prayerLvls = {
            // normal prayer book
            {1, 4, 7, 8, 9, 10, 13, 16, 19, 22, 25, 26, 27, 28, 31, 34, 35,
                    37, 40, 43, 44, 45, 46, 49, 52, 60, 65, 70, 74, 77},
            // ancient prayer book
            {50, 50, 52, 54, 56, 59, 62, 65, 68, 71, 74, 76, 78, 80, 82,
                    84, 86, 89, 92, 95}};
    /*private final static int[] prayerSlotValues = {1, 2, 4, 262144, 524288, 8,
            16, 32, 64, 128, 256, 1048576, 2097152, 512, 1024, 2048, 16777216,
            4096, 8192, 16384, 4194304, 8388608, 32768, 65536, 131072,
            33554432, 134217728, 67108864, 268435456};*/
    private final static int[] prayerSlotValues = {1, 2, 4, 262144, 524288, 8,
            16, 32, 64, 128, 256, 1048576, 2097152, 512, 1024, 2048, 16777216,
            4096, 8192, 16384, 4194304, 8388608, 32768, 65536, 131072,
            33554432, 134217728, 67108864, 536870912, 268435456};
    public boolean ancientcurses;
    public boolean[][] quickPrayers = {new boolean[30], new boolean[20]};
    //public int prayerDelay = 0;
    public transient boolean[][] onPrayers;
    private transient Player player;
    private transient boolean usingQuickPrayer;
    private transient boolean[] boostingPray;
    private transient boolean drainingprayer;
    private boolean quickPrayersOn;

    public Prayer() {

    }

    private static int drainRate(int Prayer) {
        switch (Prayer) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 22:
                return 12;
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 23:
                return 6;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 28:
            case 29:
                return 3;
            case 8:
                return 26;
            case 9:
            case 10:
            case 26:
                return 18;
            case 24:
                return 2;
            case 25:
            case 27:
            default:
                return 1;
        }
    }

    public boolean usingPrayer(int book, int prayerId) {
        return this.onPrayers[book][prayerId];
    }

    public boolean usingBoost(int prayer) {
        return this.boostingPray[prayer];
    }

    public boolean setBoost(int prayer, boolean boost) {
        return this.boostingPray[prayer] = boost;
    }

    public void startDrain() {
        if (drainingprayer)
            return;
        if (player.unlimitedPrayer > 0) {
            return;
        }
        this.drainingprayer = true;
        final int drainrate = this.getDrainRate();
        GameServer.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (player == null || !player.isOnline()) {
                    stop();
                    return;
                }
                if (!checkPrayer() || player.isDead()) {
                    closeAllPrayers();
                    drainingprayer = false;
                    stop();
                    return;
                }
                int newrate = getDrainRate();
                if (newrate == -1) {
                    drainingprayer = false;
                    stop();
                    return;
                }

                if (newrate != drainrate) {
                    drainingprayer = false;
                    stop();
                    if (hasPrayersOn())
                        player.getSkills().drainPray(1);
                    if (!checkPrayer()) {
                        closeAllPrayers();
                        drainingprayer = false;
                        return;
                    }
                    startDrain();
                    return;
                }
                if (!checkPrayer()) {
                    closeAllPrayers();
                    drainingprayer = false;
                    stop();
                    return;
                }
                player.getSkills().drainPray(1);
            }

        }, drainrate < 0 ? drainrate : 2400, drainrate < 0 ? drainrate : 2400);
    }

    public void switchSettingQuickPrayer() {
        if (!this.usingQuickPrayer) {
            player.getFrames().sendBConfig(181, 1);
            player.getFrames().sendBConfig(168, 6);
            player.getFrames().sendAMask(0, 30, 271, 42, 0, 2);
            this.usingQuickPrayer = true;
        } else {
            player.getFrames().sendBConfig(181, 0);
            player.getFrames().sendBConfig(149, 6);
            this.usingQuickPrayer = false;
        }
        this.recalculatePrayer();
    }

    public int getDrainRate() {
        int rate = 0;
        int index = 0;
        int numberofprays = 0;
        for (boolean prayer : this.onPrayers[this.getPrayerBook()]) {
            if (prayer) {
                rate += drainRate(index);
                numberofprays++;
            }
            index++;
        }
        int bonushere = 0;
        if (rate == 0)
            return -1;
        rate = (int) (rate * (0.0035 * bonushere + 1) * 1000) / numberofprays
                - ((numberofprays - 1) * 600);
        return rate;
    }

    public int getHeadIcon() {
        int value = -1;
        if (this.usingPrayer(0, 16))
            value += 8;
        if (this.usingPrayer(0, 17))
            value += 3;
        else if (this.usingPrayer(0, 18))
            value += 2;
        else if (this.usingPrayer(0, 19))
            value += 1;
        else if (this.usingPrayer(0, 22))
            value += 4;
        else if (this.usingPrayer(0, 23))
            value += 6;
        else if (this.usingPrayer(0, 24))
            value += 5;
        else if (this.usingPrayer(1, 6)) {
            value += 16;
            if (this.usingPrayer(1, 8))
                value += 2;
            else if (this.usingPrayer(1, 7))
                value += 3;
            else if (this.usingPrayer(1, 9))
                value += 1;
        } else if (this.usingPrayer(1, 7))
            value += 14;
        else if (this.usingPrayer(1, 8))
            value += 15;
        else if (this.usingPrayer(1, 9))
            value += 13;
        else if (this.usingPrayer(1, 17))
            value += 20;
        else if (this.usingPrayer(1, 18))
            value += 21;
        return value;
    }
    /*public void switchQuickPrayers() {
        if(prayerDelay > 0) {
		return;
		}
		if (!checkPrayer())
			return;
		if (this.hasPrayersOn())
			this.closeAllPrayers();
		else {
			this.player.getFrames().sendBConfig(182, 1);
			int index = 0;
			for (boolean prayer : this.quickPrayers[this.getPrayerBook()]) {
				if (prayer)
					this.usePrayer(index);
				index++;
			}
			this.recalculatePrayer();
		}
	}*/

    public void switchQuickPrayers() {
        if (!checkPrayer())
            return;
        if (this.quickPrayersOn) {
            this.closeAllPrayers();
            player.getFrames().sendBConfig(182, 0);
            this.quickPrayersOn = false;
        } else {
            int index = 0;
            int on = 0;
            for (boolean prayer : this.quickPrayers[this.getPrayerBook()]) {
                if (prayer) {
                    if (!onPrayers[getPrayerBook()][index])
                        usePrayer(index);
                    on++;
                }
                index++;
            }
            if (on > 0) {
                this.recalculatePrayer();
                this.quickPrayersOn = true;
                player.getFrames().sendBConfig(182, 1);
            } else {
                player.getFrames().sendChatMessage(0, "You haven't selected any quick prayers!");
            }
        }
    }

    public void switchPrayer(int prayerId) {
        if (!this.usingQuickPrayer)
            if (!this.checkPrayer())
                return;
        this.usePrayer(prayerId);
        this.recalculatePrayer();
    }

    public void switchPrayer(int prayerId, boolean b) {
        if (!usingQuickPrayer)
            if (!checkPrayer())
                return;
        this.usePrayer(prayerId);
        this.recalculatePrayer();
    }

    public void closeAllPrayers() {
        boolean[][] onPrayers = {new boolean[30], new boolean[20]};
        this.boostingPray = new boolean[9];
        if (this.player.protectItem == true && !this.player.isDead()) {
            if (onPrayers[1][0])
                this.player.protectItem = false;
            else if (onPrayers[0][10])
                this.player.protectItem = false;
        }
        this.onPrayers = onPrayers;
        this.player.getFrames().sendBConfig(182, 0);
        this.player.getFrames().sendConfig(ancientcurses ? 1582 : 1395, 0);
        this.player.getMask().setApperanceUpdate(true);
    }

    private boolean hasPrayersOn() {
        for (boolean prayer : this.onPrayers[this.getPrayerBook()])
            if (prayer)
                return true;

        return false;
    }

    private boolean checkPrayer() {
        if (!hasPrayersOn()) {
            this.player.getFrames().sendBConfig(182, 0);
            return true;
        } else if (hasPrayersOn()) {
            this.player.getFrames().sendBConfig(182, 1);
        }
        if (this.player.getSkills().getLevel(5) == 0) {
            this.player.getFrames().sendChatMessage((byte) 0,
                    "Your prayer has ran out.");
            this.player.getFrames().sendBConfig(182, 0);
            return false;
        }
        return true;
    }

    private boolean usePrayer(int prayerId) {
        if (prayerId < 0 || prayerId >= prayerLvls[this.getPrayerBook()].length)
            return false;
        //System.out.println(prayerId);

        if (this.player.getSkills().getLevelForXp(5) < prayerLvls[this
                .getPrayerBook()][prayerId]) {
            this.player.getFrames().sendChatMessage(
                    (byte) 0,
                    "You need " + prayerLvls[this.getPrayerBook()][prayerId]
                            + " Prayer to activate this.");
            return false;
        }
        if (!this.usingQuickPrayer) {
            if (this.onPrayers[this.getPrayerBook()][prayerId]) {
                this.onPrayers[this.getPrayerBook()][prayerId] = false;
                this.player.getFrames().sendBConfig(182, 0);
                this.player.getMask().setApperanceUpdate(true);
                return true;
            }
        } else {
            if (this.quickPrayers[this.getPrayerBook()][prayerId]) {
                this.player.getFrames().sendBConfig(182, 0);
                this.quickPrayers[this.getPrayerBook()][prayerId] = false;
                return true;
            }
        }
        if (this.getPrayerBook() == 0) {
            switch (prayerId) {
                case 0:
                case 5:
                case 13:
                    this.closePrayers(closePrayers[this.getPrayerBook()][0],
                            closePrayers[this.getPrayerBook()][10]);
                    break;
                case 1:
                case 6:
                case 14:
                    this.closePrayers(closePrayers[this.getPrayerBook()][1],
                            closePrayers[this.getPrayerBook()][3],
                            closePrayers[this.getPrayerBook()][4],
                            closePrayers[this.getPrayerBook()][10]);
                    break;
                case 2:
                case 7:
                case 15:
                    this.closePrayers(closePrayers[this.getPrayerBook()][2],
                            closePrayers[this.getPrayerBook()][3],
                            closePrayers[this.getPrayerBook()][4],
                            closePrayers[this.getPrayerBook()][10]);
                    break;
                case 3:
                case 11:
                case 20:
                    this.closePrayers(closePrayers[this.getPrayerBook()][0],
                            closePrayers[this.getPrayerBook()][1],
                            closePrayers[this.getPrayerBook()][2],
                            closePrayers[this.getPrayerBook()][3],
                            closePrayers[this.getPrayerBook()][10]);
                    break;
        /*case 28:
				this.closePrayers(closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][2],
						closePrayers[this.getPrayerBook()][3],
						closePrayers[this.getPrayerBook()][10]);
				break;
			case 4:
			case 12:
			case 21:
		case 29:
				this.closePrayers(closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][2],
						closePrayers[this.getPrayerBook()][4],
						closePrayers[this.getPrayerBook()][10]);
				break;*/
                case 8:
                case 9:
                case 26:
                    this.closePrayers(closePrayers[this.getPrayerBook()][5]);
                    break;
                case 10:
                    this.closePrayers(closePrayers[this.getPrayerBook()][6]);
                    this.player.protectItem = true;
                    //this.player.getFrames().sendChatMessage(0, "You are now protecting one item.");
                    break;
                case 17:
                case 18:
                case 19:
                    this.closePrayers(closePrayers[this.getPrayerBook()][7],
                            closePrayers[this.getPrayerBook()][9]);
                    this.player.getMask().setApperanceUpdate(true);
                    break;
                case 16:
                    this.closePrayers(closePrayers[this.getPrayerBook()][8],
                            closePrayers[this.getPrayerBook()][9]);
                    this.player.getMask().setApperanceUpdate(true);
                    break;
                case 22:
                case 23:
                case 24:
                    this.closePrayers(closePrayers[this.getPrayerBook()][7],
                            closePrayers[this.getPrayerBook()][8],
                            closePrayers[this.getPrayerBook()][9]);
                    this.player.getMask().setApperanceUpdate(true);
                    break;
                case 25:
                case 27:
                    this.closePrayers(closePrayers[this.getPrayerBook()][0],
                            closePrayers[this.getPrayerBook()][1],
                            closePrayers[this.getPrayerBook()][2],
                            closePrayers[this.getPrayerBook()][3],
                            closePrayers[this.getPrayerBook()][4],
                            closePrayers[this.getPrayerBook()][10]);
                    break;
                case 28:
                    this.closePrayers(closePrayers[this.getPrayerBook()][0],
                            closePrayers[this.getPrayerBook()][1],
                            closePrayers[this.getPrayerBook()][2],
                            closePrayers[this.getPrayerBook()][3],
                            closePrayers[this.getPrayerBook()][10]);
                    break;
                case 4:
                case 12:
                case 21:
                case 29:
                    this.closePrayers(closePrayers[this.getPrayerBook()][0],
                            closePrayers[this.getPrayerBook()][1],
                            closePrayers[this.getPrayerBook()][2],
                            closePrayers[this.getPrayerBook()][4],
                            closePrayers[this.getPrayerBook()][10]);
                    break;


                default:
                    return false;
            }
        } else {
            switch (prayerId) {
                case 0:
                    player.animate(12567);
                    player.graphics(2213);
                    this.closePrayers(closePrayers[this.getPrayerBook()][0]);
                    this.player.protectItem = true;
                    break;
                case 1:
                    player.getFrames().sendChatMessage(0, "You can't use this prayer.");
                    return false;
                case 2:
                    player.getFrames().sendChatMessage(0, "You can't use this prayer.");
                    return false;
                case 3:
                    player.getFrames().sendChatMessage(0, "You can't use this prayer.");
                    return false;
                case 4:
                    player.getFrames().sendChatMessage(0, "You can't use this prayer.");
                    return false;
                case 5:
                    player.animate(12589);
                    player.graphics(2266);
                    this.closePrayers(closePrayers[this.getPrayerBook()][2]);
                    break;
                case 7:
                case 8:
                case 9:
                case 17:
                case 18:
                    this.closePrayers(closePrayers[this.getPrayerBook()][3]);
                    this.player.getMask().setApperanceUpdate(true);
                    break;
                case 6:
                    this.closePrayers(closePrayers[this.getPrayerBook()][4]);
                    this.player.getMask().setApperanceUpdate(true);
                    break;
                case 10:
                    this.boostingPray[3] = false;
                case 11:
                    this.boostingPray[4] = false;
                case 12:
                    this.boostingPray[5] = false;
                case 13:
                    this.boostingPray[6] = false;
                case 14:
                    this.boostingPray[7] = false;
                case 15:
                case 16:
                    this.closePrayers(closePrayers[this.getPrayerBook()][1],
                            closePrayers[this.getPrayerBook()][6]);
                    break;
                case 19:
                    this.boostingPray[8] = false;
                    player.animate(12565);
                    player.graphics(2226);
                    this.closePrayers(closePrayers[this.getPrayerBook()][5],
                            closePrayers[this.getPrayerBook()][6]);
                    this.player.getMask().setApperanceUpdate(true);
                    break;
                default:
                    return false;
            }
        }
        if (!this.usingQuickPrayer) {
            this.player.getFrames().sendBConfig(182, 1);
            this.onPrayers[this.getPrayerBook()][prayerId] = true;
            this.startDrain();
        } else
            this.quickPrayers[this.getPrayerBook()][prayerId] = true;
        return true;
    }

    public void closePrayers(int[]... prayers) {
        for (int[] prayer : prayers)
            for (int prayerId : prayer)
                if (usingQuickPrayer) {
                    this.quickPrayers[this.getPrayerBook()][prayerId] = false;
                    this.player.getFrames().sendBConfig(182, 0);
                } else {
                    this.onPrayers[this.getPrayerBook()][prayerId] = false;
                    this.player.getFrames().sendBConfig(182, 0);
                }
    }

    public void recalculatePrayer() {
        player.getFrames().sendConfig(ancientcurses ? (usingQuickPrayer ? 1587 : 1582) : (usingQuickPrayer ? 1397 : 1395), 0);
        int value = 0;
        int index = 0;
        for (boolean prayer : (!usingQuickPrayer ? onPrayers[getPrayerBook()] : quickPrayers[getPrayerBook()])) {
            if (prayer) {
                value |= ancientcurses ? 1 << index : CONFIG_VALUES[index];
            }
            index++;
        }
        player.getFrames().sendConfig(ancientcurses ? (usingQuickPrayer ? 1587 : 1582) : (usingQuickPrayer ? 1397 : 1395), value);
    }

    public boolean[][] getQuickPrayers() {
        return quickPrayers;
    }

    public void setQuickPrayers() {
        player.getFrames().sendBConfig(181, 0);
        player.getFrames().sendBConfig(149, 6);
        this.player.getPrayer().usingQuickPrayer = false;
    }

    public int getPrayerBook() {
        return !ancientcurses ? 0 : 1;
    }

    public void setPlayer(Player player) {
        this.player = player;
        boolean[][] onPrayers = {new boolean[30], new boolean[20]};
        boostingPray = new boolean[9];
        this.onPrayers = onPrayers;
    }

    public boolean isAncientCurses() {
        return this.ancientcurses;
    }

    public void switchPrayBook(boolean book) {
        this.ancientcurses = book;
        this.player.getFrames().sendConfig(1584, book ? 1 : 0);
        this.player.getFrames().sendAMask(0, 27, 271, 6, 0, 2);
        this.drainingprayer = false;
    }
}