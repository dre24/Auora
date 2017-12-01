package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class Authenticate extends Command {

    public Authenticate(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (!p.hasAuth) {
            p.sm("You don't have an authentication code. Do ::security to get one.");
            return;
        }
        if (p.enteredAuth) {
            p.sm("You have already authenticated your account.");
            return;
        }
        p.getFrames().requestStringInput(914, "Please enter your authentication code (4 characters)");
    }
}
