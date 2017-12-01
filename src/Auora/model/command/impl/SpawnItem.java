package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.Prices;
import Auora.model.player.StaffRights;
import Auora.rscache.ItemDefinitions;

/**
 * @author Jonny
 */
public class SpawnItem extends Command {

    public SpawnItem(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null || args.length <= 1) {
            p.sendMessage("You must do this command as ::item id or ::item id amount");
            return;
        }
        int id = Integer.parseInt(args[0]);
        int amount = 1;
        if(args.length > 1) {
            amount = Integer.parseInt(args[1]);
        }
        if (amount > 100_000_000) {
            p.sendMessage("You cannot spawn more than 100M of an item at once.");
            return;
        }
        if (amount < 1) {
            p.getFrames().sendMessage("Sorry you can't spawn this amount");
            return;
        }
        if (id == 11855) {
            p.sm("You cannot spawn this item.");
            return;
        }
        int item_price;
        try {
            item_price = Prices.getPrice(p, id);
        } catch (Exception e) {
            e.printStackTrace();
            item_price = 0;
        }
        if (!Prices.unspawnable_item(p, id)) {
            p.getFrames().sendMessage("This item is unspawnable.");
            return;
        }

        if (p.getStaffRights() == StaffRights.OWNER) {
            item_price = 0;
        } else {
            item_price = Prices.mulAndCheck(item_price, amount);
        }
        if (!p.getInventory().contains(995, item_price) && item_price > 0) {
            p.getFrames().sendChatMessage(0, "You need atleast <col=ff0000><shad=0>" + item_price
                    + "</col></shad> coins to purchase these item(s).");
            return;
        }
        if (!p.getInventory().hasRoomFor(id, amount) && item_price != 0) {
            p.getFrames().sendChatMessage(0,
                    "<col=ff0000>Sorry, You do not have enough inventory space to spawn this item.");
            return;
        }
        if (item_price > 0) {
            p.getInventory().deleteItem(995, item_price);
        }
        if (p.getInventory().hasRoomFor(id, amount)) {
            p.getInventory().addItem(id, amount);
            p.getFrames().sendChatMessage(0,
                    "You purchase " + amount + " of <col=3300ff>["
                            + ItemDefinitions.forID(id).name + "]</col> for <col=000000>"
                            + item_price + "</col> coins.");
        } else {
            p.getBank().addItem(id, amount, 0);
            p.getFrames().sendChatMessage(0, "You have added " + id + " of <col=3300ff>["
                    + ItemDefinitions.forID(id).name + "]</col> to your bank!");
        }
    }
}
