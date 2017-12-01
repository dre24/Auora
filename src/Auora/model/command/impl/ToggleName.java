package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class ToggleName extends Command {

    public ToggleName(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.getToggleName()) {
            p.sendMessage("You have toggled your name title off.");
            p.setToggleName(false);
        } else {
            p.sendMessage("You have toggled your name title on.");
            p.setToggleName(true);
          
        }
        p.getMask().setApperanceUpdate(true);
    }
}
