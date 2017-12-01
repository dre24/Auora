package Auora.model.command.impl;

import Auora.GameSettings;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.net.mysql.impl.Voting;

/**
 * @author Jonny
 */
public class Auth extends Command {

    public Auth(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        if (args == null) {
            player.sendMessage("Please use the command as ::auth authcode");
            return;
        }
        if (!GameSettings.VOTING_ENABLED) {
            player.sendMessage("Voting connections are currently turned off, try again in 30 minutes!");
            return;
        }
        if (!player.getVoteTimer().elapsed(1000)) {
            player.sendMessage("You have to wait 1 seconds in order to use ::auth!");
            return;
        }
        player.getVoteTimer().reset();
        Voting.useAuth(player, args[0]);
    }
}
