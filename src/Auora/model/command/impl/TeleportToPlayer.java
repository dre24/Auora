package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.net.Packets;

/**
 * @author Jonny
 */
public class TeleportToPlayer extends Command {

    public TeleportToPlayer(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are currently teleport blocked and cannot teleport for another "
                    + p.teleblockDelay / 2 + " seconds.");
            return;
        }
        if(args == null) {
            p.sendMessage("You must use this command as ::ban-username");
            return;
        }
        String name = args[0].toLowerCase();
        if (!PlayerLoading.accountExists(name)) {
            p.sendMessage("The player " + name + " does not exist.");
            return;
        }
        final Player to = Packets.getPlayerByName(name);
        if (to == null) {
            p.getFrames().sendChatMessage(0, "The player " + name + " is offline.");
            return;
        }

        final int x = to.getLocation().getX();
        final int y = to.getLocation().getY();
        p.getMask().getRegion().teleport(x, y, 0, 0);
        p.getFrames().sendChatMessage(0, "You have teleported to " + name + ".");
    }
}
