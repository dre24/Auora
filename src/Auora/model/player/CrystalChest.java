package Auora.model.player;

import Auora.model.Item;
import Auora.model.World;
import Auora.util.Misc;

public class CrystalChest {

    public static final Item[] NORMAL_REWARDS = new Item[]{new Item(995, 500000), new Item(13744, 1), new Item(11284, 1), new Item(4716, 1), new Item(4718, 1), new Item(4720, 1), new Item(4722, 1),
            new Item(4724, 1), new Item(4726, 1), new Item(4708, 1), new Item(4712, 1), new Item(4714, 1), new Item(4710, 1),};
    public static final Item[] UNCOMMON_REWARDS = new Item[]{new Item(995, 5000000), new Item(14484, 1), new Item(11694, 1), new Item(11284, 1), new Item(11696, 1), new Item(11698, 1), new Item(11700, 1), new Item(11724, 1), new Item(11726, 1),
            new Item(15825, 1), new Item(17273, 1), new Item(15441, 1), new Item(15442, 1), new Item(15443, 1), new Item(15444, 1), new Item(15220, 1), new Item(15019, 1), new Item(15020, 1), new Item(15018, 1),
            new Item(15017, 1), new Item(11718, 1), new Item(11720, 1), new Item(11722, 1), new Item(18786, 1), new Item(5680, 1),};
    public static final Item[] RARE_REWARDS = new Item[]{new Item(995, 750000000), new Item(18349, 1), new Item(18351, 1), new Item(18355, 1), new Item(18357, 1), new Item(18353, 1), new Item(6199, 1), new Item(13740, 1), new Item(13742, 1),
            new Item(1191, 1), new Item(17361, 1), new Item(16174, 1), new Item(16174, 1)};

    public static void tryCombine(Player player) {
        if (!player.getInventory().contains(985)) {
            player.getFrames().sendChatMessage(0, "You need crystal tooth to combine the keys.");
            return;
        }
        if (!player.getInventory().contains(987)) {
            player.getFrames().sendChatMessage(0, "You need crystal loop to combine the keys.");
            return;
        }
        //ah, i see what you mean.
        player.getInventory().deleteItem(985, 1);
        player.getInventory().deleteItem(987, 1);
        player.getInventory().addItem(989, 1);
        player.getFrames().sendChatMessage(0, "You have combined the crystal loop and the crystal tooth into crystal key.");
    }

    public static void open(Player player) {
        if (!player.getInventory().contains(989)) {
            player.getFrames().sendChatMessage(0, "You need crystal key to open the chest.");
            return;
        }
        player.getInventory().deleteItem(989, 1);
        int rarity = Misc.random(100);
        //4349
        if (player.getEquipment().contains(4349)) {
            Item[] rewards = rarity > 96 ? RARE_REWARDS : rarity > 60 ? UNCOMMON_REWARDS : NORMAL_REWARDS;
            Item reward = rewards[Misc.random(rewards.length - 1)];
            player.getInventory().addItem(reward.getId(), reward.getAmount());
            player.getFrames().sendChatMessage(0,
                    "You have found " + reward.getDefinition().name + " in the crystal chest.");
            if (rarity > 96) {
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    d.sm("<col=F5A21B><shad=0><img=1>" + player.getUsername() + "<col=ff0000> received a " + reward.getDefinition().name + "</u> from the crystal chest!");

                }
            }
        } else {
            Item[] rewards = rarity > 98 ? RARE_REWARDS : rarity > 60 ? UNCOMMON_REWARDS : NORMAL_REWARDS;
            Item reward = rewards[Misc.random(rewards.length - 1)];
            player.getInventory().addItem(reward.getId(), reward.getAmount());
            player.getFrames().sendChatMessage(0,
                    "You have found " + reward.getDefinition().name + " in the crystal chest.");
            if (rarity > 98) {
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    d.sm("<col=F5A21B><shad=0><img=1>" + player.getUsername() + "<col=ff0000> received a " + reward.getDefinition().name + "</u> from the crystal chest!");

                }
            }
        }


    }
}
