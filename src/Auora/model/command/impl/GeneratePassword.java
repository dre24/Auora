package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

import java.util.Random;

/**
 * @author Jonny
 */
public class GeneratePassword extends Command {

    public GeneratePassword(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        String pin = randomString(4);

        p.sm("<col=ff0000>SECURITY!!! WRITE THIS Password DOWN OR YOU CAN'T ACCESS YOUR ACCOUNT!");
        p.sm("<col=ff0000>SECURITY!!! WRITE THIS Password DOWN OR YOU CAN'T ACCESS YOUR ACCOUNT!");
        p.sm("<col=ff0000>Your new Password is - " + pin);
        p.sm("<col=ff0000>Your new Password is - " + pin);
        p.sm("<col=ff0000>Your new Password is - " + pin);
        p.sm("<col=ff0000>Your new Password is - " + pin);
        p.sm("<col=ff0000>Your new Password is - " + pin);
        p.sm("<col=ff0000>Do not lose this new PW or you will not be able to access your account!");
    }

    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
