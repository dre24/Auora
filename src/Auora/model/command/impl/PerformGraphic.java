package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class PerformGraphic extends Command {

    public PerformGraphic(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::gfx id");
            return;
        }
        int graphic = Integer.parseInt(args[0]);
        p.graphics(graphic);
        p.getFrames().sendMessage("You have used the graphic id " + graphic + ".");
    }
}
