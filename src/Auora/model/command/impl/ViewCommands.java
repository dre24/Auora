package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class ViewCommands extends Command {

    public ViewCommands(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getFrames().sendChatMessage(0, "::ancients, ::auth (code), ::bank, ::barragerunes, ::bh, ::changepassword");
        p.getFrames().sendChatMessage(0, "::claim, ::cw, ::easts, ::edge, ::empty, ::food, ::help");
        p.getFrames().sendChatMessage(0, "::home, ::id, ::item (id) (amount), ::kdr, ::lunar, ::modern, ::players");
        p.getFrames().sendChatMessage(0, "::pure, ::pvp, ::resetkdr, ::tag, ::tb, ::togglepray, ::vengrunes");
        p.getFrames().sendChatMessage(0, "::wests, ::yell");
    }
}
