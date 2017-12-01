package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.util.Constants;

/**
 * @author Jonny
 */
public class ViewCoordinates extends Command {

    public ViewCoordinates(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE,
                "Your position is: " + p.getLocation().toString());

        p.getFrames().sendChatMessage(0,
                "Your position is: " + p.getLocation().toString());
    }
}
