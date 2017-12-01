package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.rsobjects.RSObjectsRegion;
import Auora.util.RSObject;

/**
 * @author Jonny
 */
public class SpawnObject extends Command {

    public SpawnObject(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::object id");
            return;
        }
        p.getFrames().sendMessage("You have spawned the object (" + args[0] + ") on location (" + p.getLocation().getX() + ", " + p.getLocation().getY() + ", " + p.getLocation().getZ() + ")");
        RSObjectsRegion.putObject(new RSObject(Integer.parseInt(args[0]), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 10, 2), -1);
    }
}
