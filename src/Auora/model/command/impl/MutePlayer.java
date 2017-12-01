package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.PlayerPunishment;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.net.Packets;

/**
 * @author Jonny
 */
public class MutePlayer extends Command {

    public MutePlayer(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must use this command as ::ban-username");
            return;
        }
        String name = args[0].toLowerCase();
        if (!PlayerLoading.accountExists(name)) {
            p.getFrames().sendChatMessage(0, "The player " + name + " does not exist.");
            return;
        }
        Player other = Packets.getPlayerByName(name);
        if (other != null) {
            other.sendMessage("You have been muted, if you feel that this is a mistake you can");
            other.sendMessage("always appeal on our forum at www.Auora614.org/forums");
        }
        if (p.getStaffRights().isHigherRank(other.getStaffRights())) {
            p.sendMessage("You can't use a command on " + name + " because he is a higher rank than you.");
            return;
        }
        p.getFrames().sendMessage("<col=ff0000>You have muted " + name + ".");
        PlayerPunishment.mute(name);
    }
}
