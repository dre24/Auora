package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class Ancients extends Command {

    public Ancients(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player player, String[] args, StaffRights privilege) {
        player.getFrames().sendInterface(1, 548, 205, 193);
        player.getFrames().sendInterface(1, 746, 93, 193);
        player.getFrames().sendChatMessage(0, "You switch your spellbook to: Ancients");
        player.spellbook = 1;
        player.getCombat().vengeance = false;
    }
}
