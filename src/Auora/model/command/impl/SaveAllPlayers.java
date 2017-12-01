package Auora.model.command.impl;

import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerSaving;
import Auora.util.Misc;

/**
 * @author Jonny
 */
public class SaveAllPlayers extends Command {

    public SaveAllPlayers(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        for (Player d : World.getPlayers()) {
            if (d == null) {
                continue;
            }
            PlayerSaving.save(d, false);
            PlayerSaving.save(d, true);
            d.sendMessage("<col=ff0000><img=2>" + Misc.formatPlayerName(p.getUsername()) + " has initiated a global character save and backup.");
        }
    }
}
