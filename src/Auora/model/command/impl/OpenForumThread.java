package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class OpenForumThread extends Command {

    public OpenForumThread(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        if (args == null) {
            player.sendMessage("Please use the command as ::thread thread id");
            return;
        }
        int thread = Integer.parseInt(args[0]);
        player.sendMessage("You have opened the forum thread: "+thread);
        player.getFrames().sendChatMessage(12, "http://Auora614.org/forums/showthread.php?"+thread);
    }
}
