package Auora.model.command.impl;

import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.util.Misc;

/**
 * @author Jonny
 */
public class Chat extends Command {

    public Chat(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::chat message");
            return;
        }
        String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));

        String yelled = args[0];

        if(yelled.contains("<") || yelled.contains(">")) {
            p.sendMessage("You can't the following symbols: < or >");
            return;
        }

        for (Player d : World.getPlayers()) {
            if (d == null)
                continue;
            if (d.getStaffRights() != StaffRights.PLAYER) {
                d.getFrames().sendChatMessage(0, "<col=000000>[<col=00ff00>HIDDEN<col=000000>] <col=000000>[<img=" + p.getStaffRights().getCrownId() + ">" + p.getStaffRights().getColor() + p.getStaffRights().getShad() + name + "<col=000000></shad>]<col=00ff00> : <col=000000>" + yelled);
            }
        }
    }
}
