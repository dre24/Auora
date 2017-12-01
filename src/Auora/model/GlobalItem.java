package Auora.model;

import Auora.model.player.Player;
import Auora.net.Packets;

/**
 * @author Vex
 * @version 1.0
 */

public class GlobalItem {

    public boolean global = false;
    public boolean taken = false;
    private Player author;
    private int itemID;
    private int amount;
    private int itemX;
    private int itemY;
    private int itemZ;
    private int tracking;
    private long timer;

    public GlobalItem(String author, int itemID, int amount, int itemX, int itemY, int itemZ, int tracking_number) {
        this.author = Packets.getPlayerByName(author);
        this.itemID = itemID;
        this.amount = amount;
        this.itemX = itemX;
        this.itemY = itemY;
        this.itemZ = itemZ;
        this.timer = System.currentTimeMillis();
        this.tracking = tracking_number;

    }

    public GlobalItem() {
        this.taken = true;
    }

    protected int getID() {
        return this.itemID;
    }

    protected int getTracking() {
        return this.tracking;
    }

    protected int getAmount() {
        return this.amount;
    }

    protected int getItemX() {
        return this.itemX;
    }

    protected int getItemY() {
        return this.itemY;
    }

    protected int getItemZ() {
        return this.itemZ;
    }

    protected long getTime() {
        return this.timer;
    }

    protected Player getAuthor() {
        return this.author;
    }

}