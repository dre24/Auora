package Auora.model.shops;

import Auora.model.Container;
import Auora.model.Item;
import Auora.model.player.Player;
import Auora.model.player.Prices;
import Auora.rscache.ItemDefinitions;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Shop {

    public static final int[] unsellableItems = new int[]{995};

    private static final int RESTOCK_TIME = 70;
    private final Object[] params = new Object[]{"Sell X", "Sell 10", "Sell 5", "Sell 1", "Value", -1, 1, 7, 4, 93, 40697856};
    private int STORE_SIZE = 40;
    Container shop = new Container(STORE_SIZE, true);
    private ArrayList<Player> playersViewing = new ArrayList<Player>();
    private boolean generalStore = false;
    @SuppressWarnings("unused")
    private int shopId = 0;
    private String shop_title = "Shop";

    public Shop(int id, String shopTitle, boolean isGeneralStore, int[] items, int[] amounts) {
        this.shopId = id;
        this.shop_title = shopTitle;
        this.generalStore = isGeneralStore;
        for (int itemSlot = 0; itemSlot < items.length; itemSlot++) {
            shop.set(shop.getFreeSlot(), new Item(
                    items[itemSlot], amounts[itemSlot]));
        }
        startRestocking();
    }

    private void startRestocking() {
        for (int i = 0; i < shop.getSize(); i++) {
            if (shop.get(i) != null) {
                shop.add(new Item(shop.get(i).getId(), 1));
            }
        }
        update();
    }

    public void open(Player player) {
        player.getFrames().sendConfig(118, 3);
        player.getFrames().sendConfig(1496, -1);
        player.getFrames().sendConfig(532, 995);
        player.getFrames().sendBConfig(199, -1);
        player.getFrames().sendInterface(620);
        player.getFrames().sendInventoryInterface(621);
        player.getFrames().sendClientScript(149, params, "IviiiIsssss");
        player.getFrames().sendAMask(0, 27, 621, 0, 36, 1086);
        player.getFrames().sendAMask(0, 12, 620, 26, 0, 1150);
        player.getFrames().sendAMask(0, 240, 620, 25, 0, 1150);
        player.getFrames().sendItems(93, player.getInventory().getContainer(), false);
        player.getFrames().sendItems(3, shop, false);
        player.getFrames().sendString(shop_title, 620, 20);
        /*int i = 0 ;     
        while( i < 15 )
        {
      	// player.getFrames().sendItems(141, player.getInventory().getContainer(), false);
      	  player.getFrames().sendString("line" + i, 620, i);          
            i++ ;
        }*/
        updatePoints(player);
    }

    public void updatePoints(Player player) {
        if (shopId == 3) {
            player.getFrames().sendString("You currently have " + player.votePoints + " Vote Points", 620, 24);
        } else if (shopId == 1 || shopId == 11) {
            player.getFrames().sendString("You currently have " + player.pkPoints + " Pk Points", 620, 24);
        } else if (shopId == 5) {
            player.getFrames().sendString("You currently have " + player.dungTokens + " Dungeoneering Tokens", 620, 24);
        } else if (shopId == 4) {
            player.getFrames().sendString("You currently have " + player.skillingPoints + " Skilling Points", 620, 24);
        }
    }


    public void addPlayer(Player player) {
        playersViewing.add(player);
    }

    public void removePlayer(Player player) {
        playersViewing.remove(player);
    }

    public void handleOption(Player p, int interfaceId, int buttonId,
                             int buttonId2, int packetId, int itemIdd) {
        switch (interfaceId) {
            case 620:
                switch (buttonId) {
                    case 18:
                        removePlayer(p);
                        break;
                    case 25:
                    case 26:
                        if (buttonId2 > 0) {
                            buttonId2 /= 6;
                        }
                        switch (packetId) {
                            case 79:
                                int price = Prices.getPrice(p, shop.get(buttonId2).getId());
                                switch (shopId) {
                                    /*case 15:
                                        price = price / 10;
                                        p.getFrames().sendChatMessage(0, "That item costs <col=ffff00><shad=ffffff>" + price + "</col></shad> Donator tokens.");
                                        break;*/
                                    case 1:
                                        price = price / 1000000;
                                        p.getFrames().sendChatMessage(0, "That item costs " + price + " Pk Points.");
                                        break;
                                    case 3:
                                        price = price / 1000000;
                                        p.getFrames().sendChatMessage(0, "That item costs " + price + " Vote Points.");
                                        break;
                                    case 11:
                                        price = price / 1000000;
                                        p.getFrames().sendChatMessage(0, "That item costs " + price + " Pk Points.");
                                        break;
                                    case 5:
                                        price = price / 1000;
                                        p.getFrames().sendChatMessage(0, "That item costs " + price + " Dungeoneering Tokens.");
                                        break;
                                    case 16:
                                        price = price / 500000;
                                        p.getFrames().sendChatMessage(0, "That item costs " + price + "M.");
                                        break;


                                    default:
                                        int price2;
                                        price2 = price / 1000000;
                                        p.getFrames().sendChatMessage(0, "That item costs " + price / 1000000 + "M.");
                                        // ("+ price2 +" Million)
                                }
                                break;
                            case 24:

                                buyItem(p, shop.get(buttonId2).getId(), 1);
                                break;
                            case 48:
                                buyItem(p, shop.get(buttonId2).getId(), 5);
                                break;
                            case 40:
                                buyItem(p, shop.get(buttonId2).getId(), 10);
                                break;
                            case 13:
                                buyItem(p, shop.get(buttonId2).getId(), 50);
                                break;
                            case 55:
                                buyItem(p, shop.get(buttonId2).getId(), 500);
                                break;
                            default:
                                p.getFrames().sendChatMessage(0, "packetId: " + packetId);
                                //case 14:
                                //player.getFrames().sendMessage(player, ItemDefinitionsinition.forId( shop.get(buttonId2).getId()).get)
                        }
                        break;
                }
                break;
            case 621:
                Item definition = p.getInventory().getContainer().get(buttonId2);
                int itemId = definition.getId();
                switch (buttonId) {
                    case 0:
                        switch (packetId) {
                            case 24:
                                sellItem(p, itemId, 1);
                                break;
                            case 48:
                                sellItem(p, itemId, 1);
                                break;
                            case 40:
                                sellItem(p, itemId, 1);
                                break;
                            case 13:
                                sellItem(p, itemId, 1);
                                break;
                            case 55:
                                sellItem(p, itemId, 1);
                                break;
                        }
                }
                break;
        }
    }

    private void sellItem(Player p, int itemId, int amount) {
        if (shopId == 1 || shopId == 11) {
            p.getFrames().sendMessage("You can't sell items to this shop!");
            return;
        }
        int price = Prices.mulAndCheck(Prices.getPrice(p, itemId), amount);
        if (!p.getInventory().contains(itemId)) {
            return;
        }
        /*if (!Prices.unspawnable_item(p, itemId)) {
            p.getFrames().sendMessage("You can't sell this item.");
            return;
        }*/
        if (p.getInventory().getContainer().getNumberOff(995) + price < 0) {
            p.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            return;
        }
        if (p.getInventory().getContainer().getNumberOff(itemId) < amount) {
            if (ItemDefinitions.forID(itemId).isNoted() || ItemDefinitions.forID(itemId).isStackable()) {
                amount = p.getInventory().lookup(itemId).getAmount();
            } else {
                amount = p.getInventory().getContainer().getNumberOff(itemId);
            }
            price = Prices.mulAndCheck(Prices.getPrice(p, itemId), amount);
            p.getFrames().sendChatMessage(0, "You don't have enough to complete this offer.");
        }
        if (shop.freeSlots() <= 0) {
            p.getFrames().sendChatMessage(0, "The shop is full.");
        }
        if (!shop.contains(new Item(itemId, 1)) && !generalStore) {
            p.getFrames().sendChatMessage(0, "You cannot sell that item to this shop.");
            return;
        }
        shop.add(new Item(itemId, amount));
        p.getInventory().deleteItem(itemId, amount);

        if (price > 0) {
            p.getInventory().addItem(995, price);
        }
        update();
    }

    private void update() {
        for (Player player : playersViewing) {
            player.getFrames().sendItems(93, player.getInventory().getContainer(),
                    false);
            player.getFrames().sendItems(3, shop, false);
        }
    }

    private void buyItem(Player p, int id, int amount) {
        Item item = new Item(id, 1);
        int price = Prices.mulAndCheck(Prices.getPrice(p, id), amount);
        if (p.getInventory().getFreeSlots() == 0) {
            p.getFrames().sendMessage("You do not have any inventory space.");
            return;
        }
        if (!p.getInventory().hasRoomFor(id, amount)) {
            if (price == 0) {
                p.getBank().addItem(id, amount, 0);
                p.getFrames().sendMessage("You have added (" + amount + ") of " + ItemDefinitions.forID(id).name + " to your bank.");
            } else {
                p.getFrames().sendChatMessage(0, "You don't have enough space in your inventory.");
            }
            return;
        }
        if (!p.getInventory().contains(995, 1) && price != 0 && shopId != 1 && shopId != 3 && shopId != 4 && shopId != 11 && shopId != 5) {
            p.getFrames().sendMessage("You don't have any money!");
            return;
        }

        if (shop.getNumberOff(id) < amount) {
            p.getFrames().sendChatMessage(0, "The shop has run out of stock of that item.");
            amount = shop.getNumberOff(id);
            price = Prices.mulAndCheck(Prices.getPrice(p, id), amount);
        }
        if (shopId == 1 || shopId == 3 || shopId == 11) {
            price = price / 1000000;
        }
        if (shopId == 4) {
            price = price / 250000;
        }
        if (shopId == 5) {
            price = price / 1000;
        }
        if (shopId == 16) {
            price = price * 2;
        }
        /*if (shopId == 15) {
            price = price / 10;
        }*/
        if ((shopId == 1 || shopId == 11) && p.getPkPoints() < price) {
            p.getFrames().sendMessage("You don't have enough Pk Points to purchase this item!");
            return;
        } else if (shopId == 3 && p.votePoints < price) {
            p.getFrames().sendMessage("You don't have enough Vote Points to purchase this item!");
            return;
        /*} else if (shopId == 15 && p.getInventory().getContainer().getNumberOff(4278) < price) {
            p.getFrames().sendMessage("You don't have enough Donator tokens to purchase this item!");
            return;*/
        } else if (shopId == 4 && p.skillingPoints < price) {
            p.getFrames().sendMessage("You don't have enough Skilling Points to purchase this item!");
            return;
        } else if (shopId == 5 && p.dungTokens < price) {
            p.getFrames().sendMessage("You don't have enough Dungeoneering Tokens to purchase this item!");
            return;
        } else if (p.getInventory().getContainer().getNumberOff(995) < price && shopId != 1 && shopId != 3 && shopId != 4 && shopId != 11 && shopId != 5) {
            p.getFrames().sendChatMessage(0, "You do not have enough coins for that many.");
            amount = p.getInventory().getContainer().getNumberOff(995) / price;
            price = Prices.mulAndCheck(Prices.getPrice(p, id), amount);
            return;
        }
        if (price > 0) {
            if (shopId == 1 || shopId == 11) {
                p.removePkPoints(price);
            } else if (shopId == 3) {
                p.votePoints -= price;
            } else if (shopId == 4) {
                p.skillingPoints -= price;
            } else if (shopId == 5) {
                p.dungTokens -= price;
            } else {
                p.getInventory().deleteItem(995, price);
            }
            updatePoints(p);
        }
        p.getInventory().addItem(id, amount);
        shop.remove(new Item(id, amount));
        if (!generalStore) {
            if (shop.getNumberOff(id) == 0) {
                shop.add(new Item(id, 1));
            }
        }
        update();
    }

    public String formatPrice(int price) {
        return NumberFormat.getInstance().format(price);
    }

}