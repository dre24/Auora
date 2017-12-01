package Auora.model.player;

import Auora.model.Container;
import Auora.model.Item;

/**
 * Price Checker
 *
 * @author crezzy
 */
public class PriceCheck {

    public static final int SIZE = 28;

    public Container<Item> pricecheckinv = new Container<Item>(SIZE, false);

    private Player player;

    public void setPlayer(Player player) {
        try {
            this.player = player;
        } catch (Exception e) {
        }
    }

    public Container getContainer() {
        return pricecheckinv;
    }

    public void close() {
        try {
            if (player.getInventory().hasRoomFor(1, pricecheckinv.size())) {
                player.getInventory().getContainer().addAll(pricecheckinv);
            } else {
                player.getBank().getContainer().addAll(pricecheckinv);
            }
            getContainer().reset();
            player.getInventory().refresh();
        } catch (Exception e) {
        }
    }

    public void refresh() {
        try {
            player.getFrames().sendPriceCheckerOptions();
            player.getInventory().refresh();
        } catch (Exception e) {
        }
    }

    public void removeItem(Player player, int id, int amount) {
        try {
            if (player != null) {
                Item item = pricecheckinv.lookup(id);
                if (item != null) {
                    if (amount > item.getAmount()) {
                        item = new Item(item.getId(), item.getAmount());
                    } else {
                        item = new Item(item.getId(), amount);
                    }
                    pricecheckinv.remove(item);
                    player.getInventory().getContainer().add(item);
                    refresh();
                }
            }
        } catch (Exception e) {
        }
    }

    public void addItem(Player player, int slot, int amount) {
        try {
            if (player != null) {
                Item item = player.getInventory().getContainer().get(slot);
                if (item != null) {
                    if (amount > item.getAmount()) {
                        item = new Item(item.getId(), item.getAmount());
                    } else {
                        item = new Item(item.getId(), amount);
                    }
                    player.getInventory().getContainer().remove(item);
                    pricecheckinv.add(item);
                    refresh();
                }
            }
        } catch (Exception e) {
        }
    }

}
