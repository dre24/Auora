package Auora.model.command.impl;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class Arena extends Command {

    public Arena(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        if (player.teleblockDelay > 0) {
            player.getFrames().sendChatMessage(0, "You are currently teleport blocked and cannot teleport for another "
                    + player.teleblockDelay / 2 + " seconds.");
            return;
        }
        player.animate(8939);
        GameServer.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                player.animate(8941);
                player.getMask().getRegion().teleport(3086, 3934, 0, 0);
            }
        }, 1801);
    }
}
