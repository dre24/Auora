package Auora.model;

import Auora.rscache.ItemDefinitions;

import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = -6485003878697568087L;

    private short id;
    private int amount;
    private int tracking;
    private transient ItemDefinitions itemDefinition;

    public Item(int id) {
        this.id = (short) id;
        this.amount = 1;
        this.itemDefinition = ItemDefinitions.forID(id);
    }

    public Item(int id, int amount) {
        this.id = (short) id;
        this.amount = amount;
        if (this.amount <= 0) {
            this.amount = 2147483647;
        }
        this.itemDefinition = ItemDefinitions.forID(id);
    }

    public Item(int id, int amount, int tracking_number) {
        this.id = (short) id;
        this.amount = amount;
        this.tracking = tracking_number;
        if (this.amount <= 0) {
            this.amount = 2147483647;
        }
        this.itemDefinition = ItemDefinitions.forID(id);
    }

    public Item(int id, int amount, boolean amt0) {
        this.id = (short) id;
        this.amount = amount;
        if (this.amount <= 0) {
            this.amount = 2147483647;
        }
        this.itemDefinition = ItemDefinitions.forID(id);
    }

    public short getId() {
        return id;
    }

    public int getTracking() {
        return tracking;
    }

    public Item clone() {
        return new Item(id, amount);
    }

    public ItemDefinitions getDefinition() {
        return itemDefinition;
    }

    public void setDefinition(ItemDefinitions itemDefinition) {
        this.itemDefinition = itemDefinition;
    }

    public int getAmount() {
        return amount;
    }

    /**
     * ONLY CALL THIS FROM THE SHOPITEM CLASS.
     *
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }


}
