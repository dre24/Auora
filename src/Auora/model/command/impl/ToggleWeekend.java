package Auora.model.command.impl;

import Auora.GameServer;
import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class ToggleWeekend extends Command {

    public ToggleWeekend(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        GameServer.weekendBonus = !GameServer.weekendBonus;
        for (Player all : World.getPlayers()) {
            if (all == null)
                continue;
            all.sm("<col=2CC904><shad=5D635B>Weekend Donator has been " + (GameServer.weekendBonus ? "Activated!" : "Disabled!"));
        }
    }
}
