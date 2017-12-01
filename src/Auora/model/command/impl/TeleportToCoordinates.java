package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class TeleportToCoordinates extends Command {

    public TeleportToCoordinates(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null || args.length <= 1) {
            p.sendMessage("You must do this command as ::tele x y (z is optional)");
            return;
        }
        int height = p.getLocation().getZ();
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        if (args.length == 2) {
            height = Integer.parseInt(args[2]);
        }
        p.getMask().getRegion().teleport(x, y, height, 0);
        p.getFrames().sendMessage("You have teleported to the coordinates (" + x + ", " + y + ", " + height + ")");
    }
}
