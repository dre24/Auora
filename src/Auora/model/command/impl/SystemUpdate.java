package Auora.model.command.impl;

import Auora.GameServer;
import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerSaving;

/**
 * @author Jonny
 */
public class SystemUpdate extends Command {

    public SystemUpdate(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        GameServer.updateTime = Integer.parseInt(args[0]);
        for (Player d : World.getPlayers()) {
            if(d == null) {
                continue;
            }

            PlayerSaving.save(d, false);
            d.getFrames().sendSystemUpdate(Integer.parseInt(args[0]));
        }
        player.sendMessage("You have initiated a game update for " + args[0] + " seconds.");
    }
}
