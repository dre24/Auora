/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.model;

import Auora.model.player.Player;
import Auora.util.RSTile;

import java.util.ArrayList;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class GlobalDropManager {

    public static ArrayList<GlobalDropItem> GlobalItemList = new ArrayList<GlobalDropItem>(500);

    public GlobalDropManager() {
    }

    /*public static void processGlobalItemTimers() {
        if(GlobalItemList.isEmpty()) return;
        for(int i = 0; i < GlobalItemList.size(); i++) {
            GlobalDropItem dropitem = GlobalItemList.get(i);
            if(dropitem == null) continue;
            dropitem.timer --;
            if(dropitem.timer == 0)
                deleteGlobalDropItem(dropitem);
        }
    }*/

    public static GlobalDropItem getDropItem(int id, int x, int y) {
        if (GlobalItemList.isEmpty())
            return null;
        for (int i = 0; i < GlobalItemList.size(); i++) {
            GlobalDropItem dropitem = GlobalItemList.get(i);
            if (dropitem == null) continue;
            if (dropitem.x == x & dropitem.y == y & dropitem.id == id) {
                return dropitem;
            } else {
                continue;
            }
        }
        return null;
    }

    public static void dropGlobalItem(RSTile tile, Item item, boolean uniqueDrop) {
        GlobalItemList.add(new GlobalDropItem((int) item.getId(), (int) tile.getX(), (int) tile.getY(), (int) tile.getZ(), 50));
        for (Player player : World.getPlayers()) {
            player.getFrames().sendGroundItem(tile, item, uniqueDrop);
        }
    }

    public static void dropItem(Player p, RSTile tile, Item item, boolean uniqueDrop) {
        GlobalItemList.add(new GlobalDropItem((int) item.getId(), (int) tile.getX(), (int) tile.getY(), (int) tile.getZ(), 50));
        p.getFrames().sendGroundItem(tile, item, uniqueDrop);
    }

    public static void dropPvpItem(Player p, RSTile tile, Item item, boolean uniqueDrop) {
        GlobalItemList.add(new GlobalDropItem((int) item.getId(), (int) tile.getX(), (int) tile.getY(), (int) tile.getZ(), 50));
        p.getFrames().sendGroundPvpItem(tile, item, uniqueDrop);
    }

    public static void deleteGlobalDropItem(Player p, GlobalDropItem dropitem) {
        if (!p.getInventory().hasRoomFor(dropitem.id, 1)) {
            p.getFrames().sendChatMessage(0, "You don't have enough inventory space to hold that item.");
            return;
        }
        int i = GlobalItemList.indexOf(dropitem);
        if (i == -1) return;
        GlobalItemList.remove(i);
        p.getInventory().addItem(dropitem.id, 1);
        if (World.getPlayers().isEmpty()) return;
        for (Player player : World.getPlayers()) {
            if (player == null) continue;
            if (player.getFrames() == null) continue;
            player.getFrames().removeGroundItem(dropitem.x, dropitem.y, dropitem.z, dropitem.id);
        }
    }
}