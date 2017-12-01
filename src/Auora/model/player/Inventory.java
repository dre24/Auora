package Auora.model.player;

import Auora.model.Container;
import Auora.model.Item;
import Auora.rscache.Cache;
import Auora.rscache.ItemDefinitions;

import java.io.Serializable;

/**
 * Manages the player inventory.
 *
 * @author Graham
 */
public class Inventory implements Serializable {


    public static final byte SIZE = 28;
    private static final long serialVersionUID = 5229803962314209861L;
    public Container<Item> inventory = new Container<Item>(SIZE, false);

    private transient Player player;

    public Item get(int slot) {
        return inventory.get(slot);
    }

    public void setPlayer(Player player) {
        this.player = player;
        for (Item item : inventory.getItems())
            if (item != null)
                item.setDefinition(ItemDefinitions.forID(item.getId()));
    }

    public boolean addItem(int item, int amount) {
        if (item < 0 || item > Cache.getAmountOfItems()/*19710*/) { //tmp fix for items
            return false;
        }


        boolean b = inventory.add(new Item(item, amount));
        if (!b) {
            player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            refresh();
            return false;
        }
        refresh();
        return true;
    }

    public boolean addSlotItem(int item, int amount, int slot) {
        if (item < 0 || item > Cache.getAmountOfItems()) { //tmp fix for items
            return false;
        }
        boolean b = inventory.addToSlot(new Item(item, amount), slot);
        if (!b) {
            player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            refresh();
            return false;
        }
        refresh();
        return true;
    }

    public boolean addItem(int item, int amount, int slot) {
        if (item < 0 || item > Cache.getAmountOfItems()) { //tmp fix for items
            return false;
        }
        boolean b = inventory.add(new Item(item, amount), slot);
        if (!b) {
            player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            return false;
        }
        refresh();
        return true;
    }

    public boolean contains(int item, int amount) {
        return inventory.contains(new Item(item, amount));
    }

    public boolean contains(int item) {
        return inventory.containsOne(new Item(item));
    }

    public void deleteItem(int item, int amount) {
        inventory.remove(new Item(item, amount));
        refresh();
    }

    public boolean addItem(Item item) {
        return addItem(item.getId(), item.getAmount());
    }

    public void deleteItem(int item, int amount, int slot) {
        inventory.remove(new Item(item, amount), slot);
        refresh();
    }

    public void deleteAll(int item) {
        inventory.removeAll(new Item(item));
        refresh();
    }

    public void refresh() {
        player.getFrames().sendItems(93, inventory, false);
    }

    public Container<Item> getContainer() {
        return inventory;
    }

    public int getFreeSlots() {
        return inventory.getFreeSlots();
    }

    public boolean hasRoomFor(int id, int itemAmount) {
        if (ItemDefinitions.forID(id).isStackable()) {
            int amt = itemAmount + numberOf(id);
            return getFreeSlots() >= 1 || contains(id) && (amt < 0 || amt > Integer.MAX_VALUE);
        } else {
            return getFreeSlots() >= itemAmount;
        }
    }

    public int numberOf(int id) {
        return inventory.getNumberOf(new Item(id, 1));
    }

    public Item lookup(int id) {
        return inventory.lookup(id);
    }

    public int lookupSlot(int id) {
        return inventory.lookupSlot(id);
    }

}
