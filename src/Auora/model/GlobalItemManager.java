package Auora.model;

import Auora.model.player.Player;
import Auora.util.RSTile;

/**
 * @version 1.0
 * @getAuthor() Vex
 */

public class GlobalItemManager {

    private final int MAX_GLOBAL_ITEMS = 1000;
    private GlobalItem droppedItems[] = new GlobalItem[2048];
    private long lastProcess;

    public GlobalItemManager() {
        this.droppedItems = new GlobalItem[2048];
    }

    public void showAllGlobalItems(Player p) {
        if (p != null) {
            for (int i = 0; i < this.droppedItems.length; i++) {
                if (droppedItems[i] != null) {
                    if (droppedItems[i].taken)
                        continue;

                    else if (droppedItems[i].global || droppedItems[i].getAuthor().getUsername().equalsIgnoreCase(p.getUsername())) {
                        p.getFrames().sendGroundItem(RSTile.createRSTile(this.droppedItems[i].getItemX(), this.droppedItems[i].getItemY(), (byte) this.droppedItems[i].getItemZ()), new Item(this.droppedItems[i].getID(), this.droppedItems[i].getAmount()), false);
                    }
                }
            }
        }
    }

    public void displayGlobalItem(GlobalItem globalItem, boolean global) {
        if (globalItem != null) {
            for (Player p : World.getPlayers())
                if (p != null)
                    if (p != globalItem.getAuthor()) {
                        p.getFrames().sendGroundItem(RSTile.createRSTile(globalItem.getItemX(), globalItem.getItemY(), (byte) globalItem.getItemZ()), new Item(globalItem.getID(), globalItem.getAmount()), false);
                    }
        }
    }

    public void addGlobalItem(GlobalItem globalItem, boolean global) {
        if (globalItem != null) {
            for (int i = 0; i < this.droppedItems.length; i++) {
                if (this.droppedItems[i] == null) {
                    this.droppedItems[i] = globalItem;
                    if (this.droppedItems[i] != null) {
                        if (this.droppedItems[i].global) {
                            for (Player p : World.getPlayers())
                                if (p != null)
                                    if (p != this.droppedItems[i].getAuthor()) {
                                        p.getFrames().sendGroundItem(RSTile.createRSTile(globalItem.getItemX(), globalItem.getItemY(), (byte) globalItem.getItemZ()), new Item(globalItem.getID(), globalItem.getAmount()), false);
                                    }
                        } else {
                            for (Player p : World.getPlayers())
                                if (p != null)
                                    if (p == this.droppedItems[i].getAuthor()) {
                                        p.getFrames().sendGroundItem(RSTile.createRSTile(globalItem.getItemX(), globalItem.getItemY(), (byte) globalItem.getItemZ()), new Item(globalItem.getID(), globalItem.getAmount()), false);
                                        break;
                                    }
                        }
                    }
                    break;
                }
            }
        }
    }

    public void removeGlobalItem(GlobalItem globalItem) {
        for (int i = 0; i < this.droppedItems.length; i++) {
            if (this.droppedItems[i] != null) {
                if (this.droppedItems[i].getItemX() == globalItem.getItemX() && this.droppedItems[i].getItemY() == globalItem.getItemY() && this.droppedItems[i].getItemZ() == globalItem.getItemZ() && this.droppedItems[i].getID() == globalItem.getID()) {
                    for (Player p : World.getPlayers())
                        if (p != null) {
                            p.getFrames().removeGroundItem(this.droppedItems[i].getItemX(), this.droppedItems[i].getItemY(), this.droppedItems[i].getItemZ(), this.droppedItems[i].getID());
                        }
                    this.droppedItems[i] = null;
                    break;
                }
            }
        }
    }

    public Item contains(int itemID, int absX, int absY, boolean delete) {
        for (int i = 0; i < droppedItems.length; i++) {
            GlobalItem globalItem = get(i);
            if (globalItem != null) {
                if (globalItem.getID() == itemID)
                    if (globalItem.getItemX() == absX)
                        if (globalItem.getItemY() == absY) {
                            if (delete)
                                removeGlobalItem(globalItem);
                            return new Item(itemID, globalItem.getAmount(), globalItem.getTracking());
                        }
            } else
                continue;
        }
        return null;
    }

    private GlobalItem get(int slot) {
        if (slot > this.droppedItems.length || slot < 0)
            return null;
        return this.droppedItems[slot];
    }

    public void process() {
        for (int i = 0; i < droppedItems.length; i++) {
            GlobalItem globalItem = get(i);
            if (globalItem != null) {
                if (globalItem.taken)
                    continue;
                if (globalItem.global == false)
                    if (System.currentTimeMillis() - globalItem.getTime() > 60000) {
                        this.displayGlobalItem(globalItem, true);
                        this.droppedItems[i].global = true;
                    }
                if (System.currentTimeMillis() - globalItem.getTime() > 90000)
                    if (globalItem != null) {
                        this.droppedItems[i].taken = true;
                        removeGlobalItem(globalItem);
                    }
            }
        }
    }
}