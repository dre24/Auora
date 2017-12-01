package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class InitiateGodMode extends Command {

    public InitiateGodMode(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getSkills().set(Skills.ATTACK, 255);
        p.getSkills().set(Skills.STRENGTH, 255);
        p.getSkills().set(Skills.DEFENCE, 255);
        p.getSkills().set(Skills.HITPOINTS, 255);
        p.getSkills().set(Skills.PRAYER, 255);
        p.getSkills().set(Skills.RANGE, 255);
        p.getSkills().set(Skills.MAGIC, 255);
        p.autoRetaliate = false;
        p.sendMessage("You have initiated godmode.");
    }
}
