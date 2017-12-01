package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.Tickets;

/**
 * @author Jonny
 */
public class AnswerTicket extends Command {

    public AnswerTicket(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        if(args == null) {
            player.sendMessage("You must use this command as ::answer username");
            return;
        }
        String other = args[0];
        Tickets.answer(player, other);
    }
}
