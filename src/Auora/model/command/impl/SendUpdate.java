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
public class SendUpdate extends Command {

    public SendUpdate(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must use this command as ::update seconds");
            return;
        }
        int seconds = Integer.parseInt(args[0]);
        GameServer.updateTime = seconds;
        for (Player d : World.getPlayers()) {
            if (d == null) {
                continue;
            }

            PlayerSaving.save(d, false);
            d.getFrames().sendSystemUpdate(seconds);
        }
        p.sendMessage("You have initiated a game update for " + seconds + " seconds.");
    }
}
