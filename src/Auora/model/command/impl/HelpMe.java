package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class HelpMe extends Command {

    public HelpMe(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getFrames().sendInterface(275);
        for (int i = 0; i < 316; i++) {
            p.getFrames().sendString("", 275, i);
        }
        p.getFrames().sendString("", 275, 14);
        p.getFrames().sendString("", 275, 16);
        p.getFrames().sendString("<col=ff0000>Quick Start Guide", 275, 2);

        p.getFrames().sendString("<col=ff0000>Quick money making tips", 275, 17);
        p.getFrames().sendString("Vote for 10M cash and 10 Vote points!", 275, 18);
        p.getFrames().sendString("Safe PvP will reward you with random cash stacks!", 275, 19);
        p.getFrames().sendString("Kill your Bounty Hunter target for a reward!", 275, 20);
        p.getFrames().sendString("Donate for awesome perks and benefits!", 275, 21);
        p.getFrames().sendString("", 275, 22);
        p.getFrames().sendString("<col=ff0000>Tips", 275, 23);
        p.getFrames().sendString("Luring is allowed (not via ::yell) so be careful!", 275, 24);
        p.getFrames().sendString("", 275, 25);
        p.getFrames().sendString("<col=ff0000>Important Commands", 275, 26);
        p.getFrames().sendString("::ticket -> Request a ticket to speak to a staff member", 275, 27);
        p.getFrames().sendString("::search -> Search for an item ID and spawn via ::item [itemID] [amount]", 275, 28);
        p.getFrames().sendString("::bank -> Opens your bank, anywhere but in the wilderness", 275, 29);
        p.getFrames().sendString("::vengrunes -> Spawns the required runes for Vengeance", 275, 30);
        p.getFrames().sendString("::barragerunes -> Spawns the required runes for Ice Barrage", 275, 31);
        p.getFrames().sendString("::pvp -> Teleports you to Falador PvP [<col=ff0000>Unsafe</col>]", 275, 32);
        p.getFrames().sendString("::cw -> Teleports you to Clanwars [<col=ff0000>Multi/Unsafe</col>]", 275, 33);
    }
}
