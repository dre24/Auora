package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class SetGender extends Command {

    public SetGender(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::setgender gender");
            return;
        }
        int gender = Integer.parseInt(args[0]);
        if (gender < 0) {
            gender = 0;
        }
        if (gender > 1) {
            gender = 1;
        }
        if (gender == 1) {
            p.getAppearence().setGender((byte) 1);
            p.getAppearence().female();
            p.getMask().setApperanceUpdate(true);
        }
        if (gender == 0) {
            p.getAppearence().setGender((byte) 0);
            p.getAppearence().resetAppearence();
            p.getMask().setApperanceUpdate(true);
        }
    }
}
