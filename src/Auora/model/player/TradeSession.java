package Auora.model.player;

import Auora.model.Container;
import Auora.model.Item;
import Auora.model.player.logs.Logs;
import Auora.rscache.ItemDefinitions;
import Auora.util.Misc;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;


public class TradeSession implements Serializable {

    private static final long serialVersionUID = -9176611950582062556L;
    private final Player trader, partner;
    @SuppressWarnings("rawtypes")
    private final Container traderItemsOffered = new Container(28, false),
            partnerItemsOffered = new Container(28, false);
    public boolean traderDidAccept, partnerDidAccept;
    private TradeState currentState = TradeState.STATE_ONE;

    /*
     * Some info for the future, 44 = wealth transfer 43 = left limit 45 = right
     * limit
     */
    public TradeSession(Player trader, Player partner) {
        this.trader = trader;
        this.partner = partner;
        try {
            trader.didRequestTrade = Boolean.FALSE;
            partner.didRequestTrade = Boolean.FALSE;
            trader.trader = "";
            partner.trader = "";
            openFirstTradeScreen(trader);
            openFirstTradeScreen(partner);
        } catch (Exception ignored) {

        }

    }

    @SuppressWarnings("unchecked")
    public void openFirstTradeScreen(Player p) {
        try {

            p.getFrames().sendTradeOptions();
            p.getFrames().sendInterface(335);
            p.getFrames().sendInventoryInterface(336);
            p.getFrames().sendItems(90, traderItemsOffered, false);
            p.getFrames().sendItems(90, partnerItemsOffered, true);
            p.getFrames().sendString("", 335, 38);
            p.getFrames().sendString("Trading with: " + Misc.formatPlayerNameForDisplay(p.equals(trader) ? partner.getUsername() : trader.getUsername()), 335, 15);
            refreshScreen();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public String getItemList(Container list1) {
        String list = "";
        for (Item item : list1.getItems()) {
            if (item == null) {
                return list;
            }
            list = list + "<col=FF9040>" + item.getDefinition().name;
            if (item.getAmount() > 1) {
                list = list + "<col=FFFFFF> x <col=FFFFFF>" + item.getAmount() + "<br>";
                list = list + "<br>";
            } else {
                list = list + "<br>";
            }
        }
        if (list.equals("")) {
            list = "<col=FFFFFF>Absolutely nothing!";
        }
        return list;
    }

    public void openSecondTradeScreen(Player p) {
        try {
            p.secondScreen = 2;
            currentState = TradeState.STATE_TWO;
            partnerDidAccept = false;
            traderDidAccept = false;
            trader.getFrames().closeInventoryInterface();
            partner.getFrames().closeInventoryInterface();
            p.getFrames().sendInterface(334);
            p.getFrames().sendString("<col=00FFFF>Trading with:<br><col=FF0000>" + Misc.formatPlayerNameForDisplay(p.equals(trader) ? partner.getUsername() : trader.getUsername()), 334, 45);
            p.getFrames().sendInterfaceConfig(334, 37, false);
            p.getFrames().sendInterfaceConfig(334, 41, false);
            p.getFrames().sendInterfaceConfig(334, 45, false);
            p.getFrames().sendInterfaceConfig(334, 46, false);
            p.getFrames().sendString("", 334, 33);
            if (p.getUsername().equalsIgnoreCase(trader.getUsername())) {
                p.getFrames().sendString(getItemList(this.traderItemsOffered), 334, 37);
                p.getFrames().sendString(getItemList(this.partnerItemsOffered), 334, 41);
            } else {
                p.getFrames().sendString(getItemList(this.partnerItemsOffered), 334, 37);
                p.getFrames().sendString(getItemList(this.traderItemsOffered), 334, 41);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void offerItem(Player pl, int slot, int amt) {
        try {
            if (slot < 0 || slot > 28)
                return;
            if (pl.equals(trader)) {
                Item i2 = pl.getInventory().getContainer().get(slot);
                if (i2 == null)
                    return;
                if ((i2.getDefinition().lendTemplateID != -1))
                    return;
                Item item = new Item(i2.getId(), pl.getInventory()
                        .getContainer().getNumberOff(
                                pl.getInventory().getContainer().get(slot)
                                        .getId()));
                if (item.getId() == 2422) {
                    pl.getFrames().sendChatMessage(0, "This item isn't trade-able.");
                    return;
                }
                if (item.getId() == 15441) {
                    pl.getFrames().sendChatMessage(0, "This item isn't trade-able.");
                    return;
                }
                if (item != null) {
                    if (item.getAmount() > amt) {
                        item.setAmount(amt);
                    }
                    if (traderItemsOffered.getFreeSlots() < item.getAmount()
                            && !pl.getInventory().getContainer().get(slot)
                            .getDefinition().isNoted()
                            && !pl.getInventory().getContainer().get(slot)
                            .getDefinition().isStackable()) {
                        item.setAmount(traderItemsOffered.getFreeSlots());
                    }
                    partnerDidAccept = false;
                    traderDidAccept = false;
                    traderItemsOffered.add(item);
                    pl.getInventory().getContainer().remove(item);
                    pl.getInventory().refresh();
                }
            } else if (pl.equals(partner)) {
                Item d = pl.getInventory().getContainer().get(slot);
                if (d == null)
                    return;
                if ((d.getDefinition().lendTemplateID != -1))
                    return;
                Item item = new Item(d.getId(), pl.getInventory()
                        .getContainer().getNumberOff(
                                pl.getInventory().getContainer().get(slot)
                                        .getId()));
                if (item.getId() == 2422) {
                    pl.getFrames().sendChatMessage(0, "This item isn't trade-able.");
                    return;
                }
                if (item.getId() == 15441) {
                    pl.getFrames().sendChatMessage(0, "This item isn't trade-able.");
                    return;
                }

                if (item != null) {
                    if (item.getAmount() > amt) {
                        item.setAmount(amt);
                    }
                    if (partnerItemsOffered.getFreeSlots() < item.getAmount()
                            && !pl.getInventory().getContainer().get(slot)
                            .getDefinition().isNoted()
                            && !pl.getInventory().getContainer().get(slot)
                            .getDefinition().isStackable()) {
                        item.setAmount(partnerItemsOffered.getFreeSlots());
                    }
                    trader.getFrames().sendChatMessage(0, "The other player has added the item <col=ff0000>[" + ItemDefinitions.forID(item.getId()).name + "]</col>.");
                    trader.getFrames().sendString("The other player has added an item [" + ItemDefinitions.forID(item.getId()).name + "].", 335, 38);
                    partner.getFrames().sendString("You added the item [" + ItemDefinitions.forID(item.getId()).name + "].", 335, 38);
                    partner.getFrames().sendChatMessage(0, "You added the item <col=ff0000>[" + ItemDefinitions.forID(item.getId()).name + "]</col>.");
                    partnerDidAccept = false;
                    traderDidAccept = false;
                    partnerItemsOffered.add(item);
                    pl.getInventory().getContainer().remove(item);
                    pl.getInventory().refresh();
                }
            }
            refreshScreen();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeItem(Player pl, int slot, int amt) {
        if (currentState == TradeState.STATE_TWO)
            return;
        if (pl.equals(trader)) {
            Item i2 = traderItemsOffered.get(slot);
            if (i2 == null)
                return;
            Item item = new Item(i2.getId(), traderItemsOffered.getNumberOff(traderItemsOffered.get(slot).getId()));
            if (item != null) {
                if (pl.getInventory().getFreeSlots() < item.getAmount() && !traderItemsOffered.get(slot).getDefinition().isNoted() && !traderItemsOffered.get(slot).getDefinition().isStackable()) {
                    item.setAmount(pl.getInventory().getFreeSlots());
                }
                if (item.getAmount() > amt) {
                    item.setAmount(amt);
                }
                trader.getInventory().getContainer().add(item);
                trader.getInventory().refresh();
                traderItemsOffered.remove(item);
                partner.getFrames().TradeIcons(slot);
                resetAccept();
                trader.tradeTimer = 10;
            }
        } else if (pl.equals(partner)) {
            Item i3 = partnerItemsOffered.get(slot);
            if (i3 == null)
                return;
            Item item = new Item(i3.getId(), partnerItemsOffered.getNumberOff(partnerItemsOffered.get(slot).getId()));
            if (item != null) {
                if (pl.getInventory().getFreeSlots() < item.getAmount() && !partnerItemsOffered.get(slot).getDefinition().isNoted() && !partnerItemsOffered.get(slot).getDefinition().isStackable()) {
                    item.setAmount(pl.getInventory().getFreeSlots());
                }
                if (item.getAmount() > amt) {
                    item.setAmount(amt);
                }
                partner.getInventory().getContainer().add(item);
                partner.getInventory().refresh();
                partnerItemsOffered.remove(item);
                trader.getFrames().TradeIcons(slot);
                resetAccept();
                partner.tradeTimer = 10;
            }
        }
        refreshScreen();
    }

    private void refreshScreen() {
        try {
            partnerDidAccept = false;
            traderDidAccept = false;
            trader.getFrames().sendItems(90, traderItemsOffered, false);
            partner.getFrames().sendItems(90, partnerItemsOffered, false);
            trader.getFrames().sendItems(90, partnerItemsOffered, true);
            partner.getFrames().sendItems(90, traderItemsOffered, true);
            trader.getFrames().sendString(Misc.formatPlayerNameForDisplay(partner.getUsername()), 335, 22);
            trader.getFrames().sendString(" has " + partner.getInventory().getFreeSlots() + " free inventory slots.", 335, 21);
            partner.getFrames().sendString(Misc.formatPlayerNameForDisplay(trader.getUsername()), 335, 22);
            partner.getFrames().sendString(" has " + trader.getInventory().getFreeSlots() + " free inventory slots.", 335, 21);
            partner.getFrames().sendString("Value: " + formatPrice(getPartnersItemsValue()), 335, 43);
            trader.getFrames().sendString("Value: " + formatPrice(getTradersItemsValue()), 335, 43);
            trader.getFrames().sendString("Value: " + formatPrice(getPartnersItemsValue()), 335, 45);
            partner.getFrames().sendString("Value: " + formatPrice(getTradersItemsValue()), 335, 45);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getTradersItemsValue() {
        return 0x0;
    }

    private long getPartnersItemsValue() {
        return 0x0;
    }

    @SuppressWarnings("unused")
    private void flashSlot(Player player, int slot) {
    }

    public void acceptPressed(Player pl) {
        try {
            if (pl.tradeTimer > 0) {
                pl.getFrames().sendChatMessage(0, "Please wait 5 seconds to accept.");
                return;
            }
            //DIsable Trade here
            if (!traderDidAccept && pl.equals(trader)) {
                traderDidAccept = true;
            } else if (!partnerDidAccept && pl.equals(partner)) {
                partnerDidAccept = true;
            }
            if (!partnerDidAccept && !traderDidAccept) {
                return;
            }
            switch (currentState) {
                case STATE_ONE:
                    if (pl.equals(trader)) {
                        if (partnerDidAccept && traderDidAccept) {
                            openSecondTradeScreen(trader);
                            openSecondTradeScreen(partner);
                        } else {
                            trader.getFrames().sendString(
                                    "Waiting for other player...", 335, 38);
                            partner.getFrames().sendString(
                                    "The other player has accepted", 335, 38);
                        }
                    } else if (pl.equals(partner)) {
                        if (partnerDidAccept && traderDidAccept) {
                            openSecondTradeScreen(trader);
                            openSecondTradeScreen(partner);
                        } else {
                            partner.getFrames().sendString(
                                    "Waiting for other player...", 335, 38);
                            trader.getFrames().sendString(
                                    "The other player has accepted", 335, 38);
                        }
                    }
                    break;
                case STATE_TWO:
                    if (pl.equals(trader)) {
                        if (partnerDidAccept && traderDidAccept) {
                            giveItems();
                        } else {
                            trader.getFrames().sendString(
                                    "Waiting for other player...", 334, 33);
                            partner.getFrames().sendString(
                                    "The other player has accepted", 334, 33);
                        }
                    } else if (pl.equals(partner)) {
                        if (partnerDidAccept && traderDidAccept) {
                            giveItems();
                        } else {
                            partner.getFrames().sendString(
                                    "Waiting for other player...", 334, 33);
                            trader.getFrames().sendString(
                                    "The other player has accepted", 334, 33);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void tradeFailed() {
        try {
            for (Item itemAtIndex : traderItemsOffered.getItems()) {
                if (itemAtIndex != null) {
                    trader.getInventory().getContainer().add(itemAtIndex);
                }
            }
            for (Item itemAtIndex : partnerItemsOffered.getItems()) {
                if (itemAtIndex != null) {
                    partner.getInventory().getContainer().add(itemAtIndex);
                }
            }
            endSession();
            trader.getInventory().refresh();
            partner.getInventory().refresh();
            trader.requested = "";
            trader.trader = "";
            partner.trader = "";
            partner.requested = "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void endSession() {
        try {
            trader.setTradeSession(null);
            partner.setTradePartner(null);
            trader.getFrames().closeInter();
            partner.getFrames().closeInter();
            trader.getFrames().closeInventoryInterface();
            partner.getFrames().closeInventoryInterface();
            trader.trader = "";
            partner.trader = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void giveItems() {
        try {
            if (!trader.getInventory().getContainer().hasSpaceFor(partnerItemsOffered)) {
                partner.getFrames().sendChatMessage(0, "The other player does not have enough space in their inventory.");
                trader.getFrames().sendChatMessage(0, "You do not have enough space in your inventory.");
                tradeFailed();
                return;
            } else if (!partner.getInventory().getContainer().hasSpaceFor(traderItemsOffered)) {
                trader.getFrames().sendChatMessage(0, "The other player does not have enough space in their inventory.");
                partner.getFrames().sendChatMessage(0, "You do not have enough space in your inventory.");
                tradeFailed();
                return;
            }
            ArrayList<String> array_items_traded1 = new ArrayList<String>();
            ArrayList<String> array_items_traded2 = new ArrayList<String>();
            for (Item itemAtIndex : traderItemsOffered.getItems()) {
                if (itemAtIndex != null) {
                    int id = itemAtIndex.getDefinition().getId();
                    int amt = itemAtIndex.getAmount();
                    String idName = ItemDefinitions.forID(id).name;
                    if (partner.getInventory().hasRoomFor(id, amt)) {
                        partner.getInventory().addItem(id, amt);
                    } else {
                        partner.getBank().addItem(id, amt, 0);
                        partner.getFrames().sendMessage("[<col=ff0000><shad=0>" + idName + "</col></shad>] has been added to your bank.");
                        partner.getBank().refresh();
                    }
                    Logs.log(trader, "trades",
                            new String[]{
                                    "Owner: " + trader.getFormattedName() + "",
                                    "Collector: " + partner.getFormattedName() + "",
                                    "Item: " + new Item(id, amt).getDefinition().getName() + " (" + id + ")",
                                    "Amount: " + amt + "",
                            });
                }
            }
            for (Item itemAtIndex : partnerItemsOffered.getItems()) {
                if (itemAtIndex != null) {
                    int id = itemAtIndex.getDefinition().getId();
                    int amt = itemAtIndex.getAmount();
                    String idName = ItemDefinitions.forID(id).name;
                    if (trader.getInventory().hasRoomFor(id, amt)) {
                        trader.getInventory().addItem(id, amt);
                    } else {
                        trader.getBank().addItem(id, amt, 0);
                        trader.getFrames().sendMessage("[<col=ff0000><shad=0>" + idName + "</col></shad>] has been added to your bank.");
                        trader.getBank().refresh();
                    }
                    Logs.log(trader, "trades",
                            new String[]{
                                    "Owner: " + trader.getFormattedName() + "",
                                    "Collector: " + partner.getFormattedName() + "",
                                    "Item: " + new Item(id, amt).getDefinition().getName() + " (" + id + ")",
                                    "Amount: " + amt + "",
                            });
                }
            }
            endSession();
            String items1 = array_items_traded1.toString();
            String items2 = array_items_traded2.toString();
            partner.getInventory().refresh();
            trader.getInventory().refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Container getPlayerItemsOffered(Player p) {
        return (p.equals(trader) ? traderItemsOffered : partnerItemsOffered);
    }

    public String formatPrice(long price) {
        return NumberFormat.getInstance().format(price);
    }

    public void resetAccept() {
        partnerDidAccept = traderDidAccept = false;
        partner.getFrames().sendString("", 335, 38);
        trader.getFrames().sendString("", 335, 38);
    }

    public enum TradeState {
        STATE_ONE, STATE_TWO
    }
}
