package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class TogglePrayerBook extends Command {

    public TogglePrayerBook(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.getPrayer().ancientcurses) {
            p.getPrayer().closeAllPrayers();
            p.getPrayer().switchPrayBook(Boolean.parseBoolean(String.valueOf(false)));
            p.getFrames().sendChatMessage(0, "Prayer book set to normal.");
        } else {
            p.getPrayer().closeAllPrayers();
            p.getPrayer().switchPrayBook(Boolean.parseBoolean(String.valueOf(true)));
            p.getFrames().sendChatMessage(0, "Prayer book set to curses.");
        }
    }
}
