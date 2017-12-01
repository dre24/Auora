package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class GainHealth extends Command {

    public GainHealth(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getSkills().set(Skills.HITPOINTS, 60000);
        p.getSkills().heal(6000);
        p.sendMessage("You have gained 60,000 health.");
    }
}
