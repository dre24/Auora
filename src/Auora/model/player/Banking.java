package Auora.model.player;

import Auora.model.Container;
import Auora.model.Item;
import Auora.rscache.ItemDefinitions;

import java.io.Serializable;

public class Banking implements Serializable {

    private static final long serialVersionUID = -9176611950582062556L;
    public static int SIZE = 468;
    public static int TAB_SIZE = 11;
    public Container<Item> bank = new Container<Item>(468, false);
    public int[] tabStartSlot = new int[TAB_SIZE];
    private Player player;
    private String type;
    private int slot;

    public static int getTabByButtonId(int buttonId) {
        switch (buttonId) {
            case 61:
                return 0;
            case 59:
                return 1;
            case 57:
                return 2;
            case 55:
                return 3;
            case 53:
                return 4;
            case 51:
                return 5;
            case 49:
                return 6;
            case 47:
                return 7;
            case 45:
                return 8;
        }
        return 0;
    }

    public static int getArrayIndex(int tabId) {
        if (tabId == 62 || tabId == 74) {
            return 10;
        }
        int base = 60;
        for (int i = 2; i < 10; i++) {
            if (tabId == base) {
                return i;
            }
            base -= 2;
        }
        base = 74;
        for (int i = 1; i < 10; i++) {
            if (tabId == base) {
                return i;
            }
            base++;
        }
        return -1;
    }

    public Container<Item> getContainer() {
        return bank;
    }

    public void setPlayer(Player player) {
        this.player = player;
        for (Item item : bank.getItems()) {
            if (item != null)
                item.setDefinition(ItemDefinitions.forID(item.getId()));
        }
        if (this.tabStartSlot == null) {
            this.tabStartSlot = new int[11];
        }
    }

    public void addItem(int slot, int amount) {
        addItem(slot, amount, true, false);
    }

    public void addItem(int slot, int amount, boolean special, int wtf) {
        addItem(slot, amount, true, special);
    }

    public void addItem(int slot, int amount, boolean refresh, boolean special) {
        if (!player.inBank)
            return;

        // player.getFrames().sendCloseChatBox();
        Item item = player.getInventory().get(slot);
        if (!special) {
            if (item == null) {
                return;
            }
            int playerAmount = player.getInventory().getContainer().getNumberOf(item);
            // int currentTab = player.getCurrentBankTab();
            if (playerAmount < amount) {
                amount = playerAmount;
            }
            if (item.getDefinition().isNoted()) {
                item = new Item(item.getId() == 10843 ? 10828 : item.getId() - 1, item.getAmount());
                player.getInventory().deleteItem(item.getId() == 10828 ? 10843 : item.getId() + 1, amount, slot);

            } else {
                player.getInventory().deleteItem(item.getId(), amount);
            }
        }
        if (special)
            item = new Item(slot, amount);
        int currentTab = player.getCurrentBankTab();
        int index = bank.indexOf(item);
        if (index > -1) {
            Item item2 = bank.get(index);
            if (item2 != null) {
                if (item2.getId() == item.getId()) {
                    bank.set(index, new Item(item.getId(), amount + item2.getAmount()));
                }
            }
        } else {
            int freeSlot;
            if (currentTab == 10) {
                freeSlot = bank.getFreeSlot();
            } else {
                freeSlot = tabStartSlot[currentTab] + getItemsInTab(currentTab);
            }
            if (item.getAmount() > 0) {
                if (currentTab != 10) {
                    insert(bank.getFreeSlot(), freeSlot);
                    increaseTabStartSlots(currentTab);
                }
                bank.set(freeSlot, new Item(item.getId(), amount));
            }
        }
        if (refresh) {
            refresh();
        }

    }

    public void addItem(int item_id, int amount, int tab) {
        Item item = new Item(item_id, amount);
        if (item == null) {
            return;
        }
        int currentTab = player.getCurrentBankTab();
        if (tab != 0) {
            currentTab = tab;
        }
        int index = bank.indexOf(item);
        if (index > -1) {
            Item item2 = bank.get(index);
            if (item2 != null) {
                if (item2.getId() == item.getId()) {
                    bank.set(index, new Item(item.getId(), amount + item2.getAmount()));
                }
            }
        } else {
            int freeSlot;
            if (currentTab == 10) {
                freeSlot = bank.getFreeSlot();
            } else {
                freeSlot = tabStartSlot[currentTab] + getItemsInTab(currentTab);
            }
            if (item.getAmount() > 0) {
                if (currentTab != 10) {
                    insert(bank.getFreeSlot(), freeSlot);
                    increaseTabStartSlots(currentTab);
                }
                bank.set(freeSlot, new Item(item.getId(), amount));
            }
        }
        refresh();
    }

    public void removeItem(int slot, int amount) {
        if (!player.inBank)
            return;
        // player.getFrames().sendCloseChatBox();
        if (slot < 0 || slot > Banking.SIZE || amount <= 0) {
            return;
        }
        Item item = bank.get(slot);
        Item item2 = bank.get(slot);
        Item item3 = bank.get(slot);
        int tabId = getTabByItemSlot(slot);
        if (item == null) {
            return;
        }
        if (amount > item.getAmount()) {
            item = new Item(item.getId(), item.getAmount());
            item2 = new Item(item.getId() == 10828 ? 10843 : item.getId() + 1, item.getAmount());
            item3 = new Item(item.getId(), item.getAmount());
            if (player.withdrawNote) {
                if (item2.getDefinition().isNoted()
                        && item2.getDefinition().getName().equals(item.getDefinition().getName())
                        && !item.getDefinition().isStackable()) {
                    item = new Item(item.getId() == 10828 ? 10843 : item.getId() + 1, item.getAmount());
                } else {
                    player.getFrames().sendChatMessage(0, "You cannot withdraw this item as a note.");
                    item = new Item(item.getId(), item.getAmount());
                }
            }
        } else {
            item = new Item(item.getId(), amount);
            item2 = new Item(item.getId(), amount);
            item3 = new Item(item.getId(), amount);
            if (player.withdrawNote) {
                item2 = new Item(item.getId() == 10828 ? 10843 : item.getId() + 1, item.getAmount());
                if (item2.getDefinition().isNoted()
                        && item2.getDefinition().getName().equals(item.getDefinition().getName())
                        && !item.getDefinition().isStackable()) {
                    item = new Item(item.getId() == 10828 ? 10843 : item.getId() + 1, item.getAmount());
                } else {
                    player.getFrames().sendChatMessage(0, "You cannot withdraw this item as a note.");
                    item = new Item(item.getId(), item.getAmount());
                    return;
                }
            }
        }
        if (amount > player.getInventory().getFreeSlots() && !item3.getDefinition().isStackable()
                && !player.withdrawNote && bank.get(slot).getAmount() > player.getInventory().getFreeSlots()) {
            item = new Item(item.getId(), player.getInventory().getFreeSlots());
            item2 = new Item(item2.getId(), player.getInventory().getFreeSlots());
            item3 = new Item(item3.getId(), player.getInventory().getFreeSlots());
        }
        if (bank.contains(item3)) {
            if (player.getInventory().getFreeSlots() <= 0) {
                player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            } else {
                if (player.withdrawNote && !item.getDefinition().isNoted()) {
                    player.getInventory().addItem(item.getId(), item.getAmount());
                    bank.remove(item3);
                } else {
                    player.getInventory().addItem(item.getId(), item.getAmount());
                    bank.remove(item3);
                }
            }
        }
        if (get(slot) == null) {
            decreaseTabStartSlots(tabId);
        }
        bank.shift();
        refresh();
    }

    public void openInventory(final Player p) {

        player.getFrames().sendConfig2(563, 4194304);
        player.getFrames().sendConfig(1248, -2013265920);
        player.getFrames().sendInterface(762);
        player.getFrames().sendInventoryInterface(763);
        player.getFrames().sendAMask(0, 516, 762, 93, 40, 1278);
        player.getFrames().sendAMask(0, 27, 763, 0, 36, 1150);
        player.getFrames().sendBlankClientScript(1451);
        player.getFrames().sendItems(95, p.getInventory().inventory, false);
        p.getInventory().refresh();
        player.getFrames().sendItems(31, p.getInventory().getContainer(), false);
    }

    public void openBank(final Player p) {
        player.getFrames().sendConfig2(563, 4194304);
        player.getFrames().sendConfig(1248, -2013265920);
        player.getFrames().sendInterface(762);
        player.getFrames().sendInventoryInterface(763);
        player.getFrames().sendAMask(0, 516, 762, 93, 40, 1278);
        player.getFrames().sendAMask(0, 27, 763, 0, 36, 1150);
        player.getFrames().sendBlankClientScript(1451);
        player.getFrames().sendItems(95, p.getBank().bank, false);
        player.getFrames().sendItems(31, p.getInventory().getContainer(), false);
    }

    public void openBank() {
        if (player.hasAuth && !player.enteredAuth) {
            player.getDialogue().startDialogue("PropAuth");
            return;
        }
        if (!player.getCombat().isSafe(player) && !player.getCombat().inDangerousPVP(player)) {
            player.getFrames().sendChatMessage(0, "Please move into the safezone to bank.");
            return;
        }
        if (player.getTradeSession() != null) {
            player.getTradeSession().tradeFailed();
        } else if (player.getTradePartner() != null) {
            player.getTradePartner().getTradeSession().tradeFailed();
        }
        player.getFrames().sendString("Bank of Auora", 762, 45);
        player.getFrames().closeInter();
        player.getFrames().closeInter();
        player.getFrames().closeInventoryInterface();
        player.getFrames().closeInventoryInterface();
        player.inBank = true;
        player.getFrames().sendConfig(304, player.inserting ? 1 : 0);
        player.getFrames().sendConfig(115, player.withdrawNote ? 1 : 0);
        // player.getFrames().sendBConfig1(169, 1);
        player.getFrames().sendBConfig1(199, -1);
        player.getFrames().sendItems(95, bank, false);
        player.getFrames().sendItems(31, player.getInventory().getContainer(), false);
        player.getFrames().sendConfig2(563, 4194304);
        player.getFrames().sendConfig2(1249, player.amountx);
        player.getFrames().sendConfig(1248, -2013265920);
        player.getFrames().sendInterface(762);
        player.getFrames().sendBankOptions();
        player.getFrames().sendInventoryInterface(763);
        // player.getFrames().setTabs(player);
        sendTabConfig();
        refresh();
        player.setCurrentBankTab(getArrayIndex(62));

    }

    public void refresh() {
        player.getFrames().sendString(bank.getSize() - bank.getFreeSlots() + "", 762, 31);
        final int free = player.getBank().getContainer().getSize() - bank.getFreeSlots();
        player.getFrames().sendItems(95, bank, false);
        player.getFrames().sendBConfig2(192, free); // Total items
        player.getFrames().sendBConfig2(1038, 0); // Total free items
        sendTabConfig();
    }

    public void initiateBankX(Player player, int slot, int item, String string) {
        this.type = string;
        this.slot = slot;
        player.bankOption = true;

        Object[] obj = null;
        if (string.equals("ADD")) {
            obj = new Object[]{"Bank X:"};
        } else if (string.equals("WITHDRAW")) {
            obj = new Object[]{"Withdraw X:"};
        }
        player.getFrames().sendClientScript(108, obj, "s");
    }

    public void finalizeBankX(Player player, int amt) {
        if (type.equals("ADD")) {
            player.getFrames().sendConfig2(1249, amt);
            player.getBank().addItem(slot, amt);
            player.amountx = amt;
        } else if (type.equals("WITHDRAW")) {
            player.getFrames().sendConfig2(1249, amt);
            player.getBank().removeItem(slot, amt);
            player.amountx = amt;
        }
        player.bankOption = false;
    }

    /**
     * Banks the inventory items.
     */
    public void bankInv() {
        if (player.getInventory().getContainer().size() < 1) {
            player.getFrames().sendChatMessage(0, "You don't have any items to bank.");
            return;
        }
        player.getInventory();
        for (int i = 0; i < Inventory.SIZE; i++) {
            Item item = player.getInventory().get(i);
            if (item != null) {
                addItem(i, item.getAmount(), false, false);
            }
        }
        bankItems(player.getInventory().getContainer());
        refresh();
        player.getInventory().refresh();
    }

    /**
     * Banks all the equipped items.
     */
    public void bankEquip() {

        if (player.getEquipment().getContainer().size() < 1) {
            player.getFrames().sendChatMessage(0, "You're not wearing anything to bank.");
            return;
        }
        bankItems(player.getEquipment().getContainer());
        refresh();
        player.getEquipment().refresh();
    }

    /**
     * Banks all the items from a certain container.
     *
     * @param container The container.
     * @return {@code True}.
     */
    private boolean bankItems(Container container) {
        int currentTab = player.getCurrentBankTab();
        try {
            for (int i = 0; i < container.getSize(); i++) {
                Item item = container.get(i);
                if (item == null) {
                    continue;
                }
                Item toBank = item;
                if (item.getDefinition().isNoted()) {
                    toBank = new Item(item.getId() == 10843 ? 10828 : item.getId() - 1, item.getAmount());
                }
                container.set(i, null);
                int index = bank.indexOf(item);
                if (index > -1) {
                    Item item2 = bank.get(index);
                    if (item2 != null) {
                        if (item2.getId() == item.getId()) {
                            bank.set(index, new Item(toBank.getId(), item.getAmount() + item2.getAmount()));
                        }
                        if ((item2.getId() + item.getId()) > 2147483647) {
                            player.sendMessage("You can't deposit over max cash.");
                            return false;
                        }
                    }
                } else {
                    int freeSlot = 0;
                    if (currentTab == 10) {
                        freeSlot = bank.getFreeSlot();
                    } else {
                        freeSlot = tabStartSlot[currentTab] + getItemsInTab(currentTab);
                    }
                    if (freeSlot == -1)
                        continue;
                    if (item.getAmount() > 0) {
                        if (currentTab != 10) {
                            insert(bank.getFreeSlot(), freeSlot);
                            increaseTabStartSlots(currentTab);
                        }
                        bank.set(freeSlot, new Item(toBank.getId(), item.getAmount()));
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return true;
    }


    public Item get(int slot) {
        return bank.get(slot);
    }

    public void set(int slot, Item item) {
        bank.set(slot, item);
    }

    public void insert(int fromId, int toId) {
        Item temp = bank.getItems()[fromId];
        if (toId > fromId) {
            for (int i = fromId; i < toId; i++) {
                set(i, get(i + 1));
            }
        } else if (fromId > toId) {
            for (int i = fromId; i > toId; i--) {
                set(i, get(i - 1));
            }
        }
        set(toId, temp);
    }

    public void sendTabConfig() {
        int config = 0;
        config += getItemsInTab(2);
        config += getItemsInTab(3) << 10;
        config += getItemsInTab(4) << 20;
        player.getFrames().sendConfig2(1246, config);
        config = 0;
        config += getItemsInTab(5);
        config += getItemsInTab(6) << 10;
        config += getItemsInTab(7) << 20;
        player.getFrames().sendConfig2(1247, config);
        int tab = player.getCurrentBankTab();
        config = -2013265920;
        config += (134217728 * (tab == 10 ? 0 : tab - 1));
        config += getItemsInTab(8);
        config += getItemsInTab(9) << 10;
        player.getFrames().sendConfig2(1248, config);
        // System.out.println(config);
    }

    public boolean addItemFromInventory(int slot, int itemID, int amount) {
        Item itemInInventory = player.getInventory().getContainer().get(slot);
        if (itemInInventory.getId() != itemID)
            return false;
        if ((itemID == 451 || itemID == 452) && player.getCombat().inDangerousPVP(player)) {
            player.getFrames().sendChatMessage(0, "You can't bank runite ore while in the wilderness!");
            return false;
        }
        int maxAmount = player.getInventory().numberOf(itemID);
        if (amount > maxAmount)
            amount = maxAmount;
        boolean isNoted = itemInInventory.getDefinition().isNoted();
        for (int i = 0; i < bank.getSize(); i++) {
            if (bank.get(i) == null) {
                bank.set(i, new Item(isNoted ? itemID - 1 : itemID, amount));
                break;
            } else if (bank.get(i).getId() == itemID && bank.get(i).getAmount() == 2147483647) {
                player.getFrames().sendChatMessage(0, "You have max of that item in your inventory.");
                return false;
            } else if (bank.get(i).getId() == (isNoted ? itemID - 1 : itemID)) {
                bank.set(i, new Item(isNoted ? itemID - 1 : itemID, bank.get(i).getAmount() + amount));
                break;
            }
        }
        player.getInventory().deleteItem(itemID, amount);
        refresh();
        return true;
    }

    public boolean deleteItemToInventory(int slot, int itemID, int amount, boolean butOne) {
        if (bank.get(slot).getId() != itemID)
            return false;
        if (player.getInventory().contains(itemID, 2147483647)) {
            player.getFrames().sendChatMessage(0, "You have max of that item in your inventory.");
            return false;
        }
        int maxAmount = bank.getNumberOff(itemID);
        if (maxAmount == 0)// logic error
            return false;
        if (amount > maxAmount) {
            if (!butOne) {
                amount = maxAmount;
            } else {
                amount = maxAmount - 1;
            }
        }
        if (amount <= 0 || maxAmount <= 0) {
            amount = 1;
        }
        boolean shouldNote = (player.withdrawNote && ItemDefinitions.forID(itemID + 1).isNoted());
        boolean stackable = ItemDefinitions.forID(shouldNote ? itemID + 1 : itemID).isStackable();
        if ((player.getInventory().getFreeSlots() == 0 && !stackable) || (player.getInventory().getFreeSlots() == 0
                && stackable && !player.getInventory().contains(shouldNote ? itemID + 1 : itemID))) {
            player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            return false;
        }
        if ((!stackable || !player.getInventory().contains(itemID)) && (amount > player.getInventory().getFreeSlots())
                && !player.withdrawNote)
            if (!ItemDefinitions.forID(itemID).isStackable()) {
                amount = player.getInventory().getFreeSlots();
            } else if (player.getInventory().getFreeSlots() > 0 && ItemDefinitions.forID(itemID).isStackable()) {

            } else {

            }
        // amount = player.getInventory().getFreeSlots();
        bank.remove(new Item(itemID, amount));
        player.getInventory().addItem(shouldNote ? itemID + 1 : itemID, amount);
        bank.shift();
        refresh();
        return true;
    }

    public void collapseTab(int tabId) {
        int size = getItemsInTab(tabId);
        Item[] tempTabItems = new Item[size];
        for (int i = 0; i < size; i++) {
            tempTabItems[i] = get(tabStartSlot[tabId] + i);
            set(tabStartSlot[tabId] + i, null);
        }
        bank.shift();
        for (int i = tabId; i < tabStartSlot.length - 1; i++) {
            tabStartSlot[i] = tabStartSlot[i + 1] - size;
        }
        tabStartSlot[10] = tabStartSlot[10] - size;
        for (int i = 0; i < size; i++) {
            int slot = bank.getFreeSlot();
            set(slot, tempTabItems[i]);
        }
        player.setCurrentBankTab(10);
        refresh();
    }

    public int getTabByItemSlot(int itemSlot) {
        int tabId = 0;
        for (int i = 0; i < tabStartSlot.length; i++) {
            if (itemSlot >= tabStartSlot[i]) {
                tabId = i;
            }
        }
        return tabId;
    }

    public void increaseTabStartSlots(int startId) {
        for (int i = startId + 1; i < tabStartSlot.length; i++) {
            tabStartSlot[i]++;
        }
    }

    public void decreaseTabStartSlots(int startId) {
        if (startId == 10)
            return;
        for (int i = startId + 1; i < tabStartSlot.length; i++) {
            tabStartSlot[i]--;
        }
        if (getItemsInTab(startId) == 0) {
            collapseTab(startId);
        }
    }

    public int[] getTab() {
        return tabStartSlot;
    }

    public int getItemsInTab(int tabId) {
        return tabStartSlot[tabId + 1] - tabStartSlot[tabId];
    }

}
