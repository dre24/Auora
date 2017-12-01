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
public class BHTeleport extends Command {

    public BHTeleport(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are currently teleport blocked and cannot teleport for another "
                    + p.teleblockDelay / 2 + " seconds.");
            return;
        }
        p.animate(8939);
        GameServer.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                p.animate(8941);
                p.getMask().getRegion().teleport(3184, 3692, 0, 0);
            }
        }, 1801);
    }
}
