package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

import java.util.Random;

/**
 * @author Jonny
 */
public class GeneratePin extends Command {

    public GeneratePin(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.getCombat().hasTarget() || p.getCombat().combatWithDelay > 0 || p.getSkills().isDead() || p.getCombat().delay > 0) {
            p.getFrames().sendChatMessage(0, "You can't do this here.");
            return;
        }
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't do this here.");
            return;
        }
        if (p.hasAuth) {
            p.sm("You already have an authentication code. Do ::removeauth to delete it.");
            return;
        }
        String pin = randomString(4);
        p.authCode = pin;
        p.hasAuth = true;
        p.enteredAuth = true;
        p.sm("<col=ff0000>YOU MUST WRITE THIS CODE DOWN OR YOU CAN'T ACCESS YOUR ACCOUNT!");
        p.sm("<col=ff0000>Your authentication code is - " + pin);
        p.sm("<col=ff0000>Your authentication code is - " + pin);
        p.sm("<col=ff0000>Your authentication code is - " + pin);
        p.sm("<col=ff0000>Your authentication code is - " + pin);
        p.sm("<col=ff0000>Your authentication code is - " + pin);
        p.sm("<col=ff0000>Do not lose this code or you will not be able to access your account!");
    }

    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
