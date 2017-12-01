package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class OpenInterface extends Command {

    public OpenInterface(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::interface id");
            return;
        }
        int interfaceId = Integer.parseInt(args[0]);
        p.getFrames().sendInterface(interfaceId);
        p.getFrames().sendMessage("You have opened interface " + interfaceId + ".");
    }
}
