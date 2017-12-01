package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class Moderns extends Command {

    public Moderns(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getFrames().sendInterface(1, 548, 205, 192);
        p.getFrames().sendInterface(1, 746, 93, 192);
        p.getFrames().sendChatMessage(0, "You switch your spellbook to: Moderns");
        p.spellbook = 0;
        p.getCombat().vengeance = false;
    }
}
