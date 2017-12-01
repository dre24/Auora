package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class SendString extends Command {

    public SendString(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null || args.length <= 2) {
            p.sendMessage("You must do this command as ::interface id amountmin amountmax");
            return;
        }
        int stringId = Integer.parseInt(args[0]);
        int amt = Integer.parseInt(args[1]);
        int amt2 = Integer.parseInt(args[2]);
        while (amt < amt2) {
        	 p.getFrames().sendInterface(Integer.parseInt(args[0]));
        	 //p.getFrames().sendString("Child: " + amt + "", stringId, amt);
        	 p.getFrames().sendString("line" + amt, Integer.parseInt(args[0]), amt);
        	 
        	 amt++;
        }
        
    }
}
