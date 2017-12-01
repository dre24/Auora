package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.net.Packets;

/**
 * @author Jonny
 */
public class TeleportPlayerToMe extends Command {

    public TeleportPlayerToMe(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
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
            p.sendMessage("The player " + name + " does not exist.");
            return;
        }
        final Player to = Packets.getPlayerByName(name);
        if (to == null) {
            p.sendMessage("The player " + name + " is currently offline.");
            return;
        }
        if (!p.getCombat().isSafe(p) && !p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0, "You can only use this command in a safe area.");
            return;
        }
        if (p.getStaffRights().isHigherRank(to.getStaffRights()) && !p.getUsername().equals("leon")) {
            p.sendMessage("You can't use a command on " + name + " because he is a higher rank than you.");
            return;
        }

        int x = p.getLocation().getX();
        int y = p.getLocation().getY();
        int z = p.getLocation().getZ();
        to.getMask().getRegion().teleport(x, y, z, 0);
        p.getFrames().sendChatMessage(0, "You have teleported " + name + " to you.");
    }
}
