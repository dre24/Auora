package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class Master extends Command {

    public Master(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        for (int i = 0; i < 7; i++) {
            p.getSkills().setXp(i, Skills.SMALL_EXP);
            p.getSkills().set(i, 99);
            p.getSkills().set(6, 99);
            p.getSkills().setXp(6, Skills.SMALL_EXP);
            p.getSkills().set(23, 99);
            p.getSkills().setXp(23, Skills.SMALL_EXP);
            p.getSkills().heal(990);
        }
        p.sendMessage("You have been given all 99 stats.");
    }
}
