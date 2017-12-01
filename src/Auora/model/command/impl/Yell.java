package Auora.model.command.impl;

import Auora.GameSettings;
import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.PlayerPunishment;
import Auora.model.player.StaffRights;
import Auora.util.Misc;

/**
 * @author Jonny
 */
public class Yell extends Command {

    public Yell(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must use the command as ::yell stasdfjasdf");
            return;
        }
        if (!GameSettings.YELL_STATUS || !GameSettings.YELL_STATUS) {
            p.getFrames().sendMessage("Yell is currently turned off.");
            return;
        }
        if (PlayerPunishment.isMuted(p.getUsername()) || PlayerPunishment.isIpMuted(p.getIpAddress())) {
            p.getFrames().sendChatMessage(0, "You are muted and cannot talk.");
            return;
        }
        if (p.isYellDisabled()) {
            p.sm("You are unable to yell as your yell has been disabled.");
            return;
        }

        if (p.yellTimer > 0) {
            p.sm("You must wait another <col=ff0000>" + (p.yellTimer / 2) + "</col> seconds before you can yell again!");
            return;
        }
        if (p.getStaffRights() == StaffRights.PLAYER) {


            switch (p.getDonatorRights().ordinal()) {
                case 1:
                    p.yellTimer = 5;
                    break;
                case 2:
                    p.yellTimer = 5;
                    break;
                case 3:
                case 4:
                    p.yellTimer = 5;
                    break;

            }
        }
        String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        int len = args.length;
        String yelled = "";
        String seperator;
        for (int i = 0; i < len; i++) {
            if (args[i].contains(">") || args[i].contains("<") || args[i].contains("\u00ae") || args[i].contains("\u00a9")
                    || args[i].contains("\u20ac") || args[i].contains("\u00d7") || args[i].contains("\u00a0")) {
                p.getFrames().sendChatMessage(0, "You cannot use those symbols.");
                return;
            } else {
                seperator = i == 1 ? "" : " ";
                yelled = yelled + seperator + args[i];
            }
        }
        for (Player d : World.getPlayers()) {
            if (d == null)
                continue;
            if (p.getTitles().getTitle() == null) {
                d.getFrames().sendChatMessage(0, p.getDonatorRights().getColor() + p.getDonatorRights().getShad() + "[<img=" + (p.getDonatorRights().getClientCrownId() - 1) + ">"
                        + p.getDonatorRights().getTitle() + "<img=" + (p.getDonatorRights().getClientCrownId() - 1) + ">] [" + name + "] : " + yelled);
            } else if (p.getStaffRights() == StaffRights.PLAYER) {
                d.getFrames().sendChatMessage(0, p.getTitles().getColor() + p.getTitles().getShad() + "["
                        + p.getTitles().getTitle() + "] [" + name + "] : " + yelled);
            } else {
                if (!p.getToggleName()) {
                    d.getFrames().sendChatMessage(0, p.getTitles().getColor() + p.getTitles().getShad() + "[<img=" + p.getStaffRights().getCrownId() + ">"
                            + p.getTitles().getTitle() + "<img=" + p.getStaffRights().getCrownId() + ">] [" + name + "] : " + yelled);
                } else {
                    d.getFrames().sendChatMessage(0, p.getStaffRights().getColor() + p.getStaffRights().getShad() + "[<img=" + p.getStaffRights().getCrownId() + ">"
                            + p.getStaffRights().getTitle() + "<img=" + p.getStaffRights().getCrownId() + ">] [" + name + "] : " + yelled);

                }
            }
        }
    }
}
