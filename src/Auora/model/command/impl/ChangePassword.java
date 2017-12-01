package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class ChangePassword extends Command {

    public ChangePassword(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::changepass password");
            return;
        }
        String password = args[0];
        if (password.contains("<euro>") || password.equalsIgnoreCase("password")) {
            p.getFrames().sendChatMessage(0, "You can't have that as your password.");
            return;
        }
        if (password.length() == 1) {
            p.getFrames().sendChatMessage(0, "You can't have just 1 letter or number as a password.");
            return;
        }
        if (password.length() > 20) {
            p.getFrames().sendChatMessage(0, "Your password can't be over 20 characters.");
            return;
        }
        p.setPassword(password);
        p.getFrames().sendChatMessage(0, "Your password has been changed to; " + password);
    }
}
