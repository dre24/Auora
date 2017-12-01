package Auora.model.tabs;

import Auora.model.World;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonathan spawnscape
 */

public class QuestTab {

    public static void initiate_interface(Player p) {
    	
    	 	
    	
    	
    	p.getFrames().sendString("      Auora Achievements", 259, 8);
    	p.getFrames().sendString("Difficulty", 259, 1);
    	p.getFrames().sendString("Achievements", 259, 6);
    	
    	p.getFrames().sendString("     Easy", 259, 2);
    	p.getFrames().sendString("Medium", 259, 3);
    	p.getFrames().sendString("Hard", 259, 4);
    	p.getFrames().sendString("Donator", 259, 5);
    	
    	
    	// start achievements
    	
    	
    		// easy
    	
    	if (p.Ethan == 1) {
    		if ((p.dangerousKills + p.safeKills) < 6) {
    			p.getFrames().sendString("     Kill 5 Players", 259, 30);
    		} else {
    			p.getFrames().sendString("<col=00FF00>     Kill 5 Players</col=00FF00>", 259, 30);
    		}
    	
    	p.getFrames().sendString("Kill 25 Players", 259, 31);
    	p.getFrames().sendString("Get 10 Dangerous Kills", 259, 32);
    	p.getFrames().sendString("Get 25 Dangerous Kills", 259, 33);
    	
    	p.getFrames().sendString("     Get 10 Multi kills", 259, 22);
    	p.getFrames().sendString("Get 25 Multi kills", 259, 23);
    	p.getFrames().sendString("Open 1 crate", 259, 24);
    	p.getFrames().sendString("Open 5 crates", 259, 25);
    	
    	p.getFrames().sendString("     Get 5 AGS Kills", 259, 18);
    	p.getFrames().sendString("Get 5 DClaws Kills", 259, 19);
    	p.getFrames().sendString("Get 5 Korasi Kills", 259, 20);
    	p.getFrames().sendString("Get 5 DH Axe Kills", 259, 21);
    	
    	p.getFrames().sendString("     Use Vengeance Spell 10x", 259, 10);
    	p.getFrames().sendString("Reach 5 Hours of playtime", 259, 11);
    	p.getFrames().sendString("Vote 3 Times", 259, 12);
    	p.getFrames().sendString("Get a hit >600", 259, 13);
    	
    	p.getFrames().sendString("", 259, 26);
    	p.getFrames().sendString("", 259, 27);
    	p.getFrames().sendString("", 259, 28);
    	p.getFrames().sendString("", 259, 29);
    	
    	p.getFrames().sendString("", 259, 14);
    	p.getFrames().sendString("", 259, 15);
    	p.getFrames().sendString("", 259, 16);
    	p.getFrames().sendString("", 259, 17);
    	
    	} else if (p.Ethan == 2) {
    		
        	p.getFrames().sendString("     Kill 100 Players", 259, 30);
        	p.getFrames().sendString("Kill 250 Players", 259, 31);
        	p.getFrames().sendString("Get 50 Dangerous Kills", 259, 32);
        	p.getFrames().sendString("Get 100 Dangerous Kills", 259, 33);
        	
        	p.getFrames().sendString("     Get 50 Multi kills", 259, 22);
        	p.getFrames().sendString("Get 100 Multi kills", 259, 23);
        	p.getFrames().sendString("Open 10 crate", 259, 24);
        	p.getFrames().sendString("Open 25 crates", 259, 25);
        	
        	p.getFrames().sendString("     Get 20 ZGS Kills", 259, 18);
        	p.getFrames().sendString("Get 20 DBow Kills", 259, 19);
        	p.getFrames().sendString("Get 20 DDS Kills", 259, 20);
        	p.getFrames().sendString("Get 20 DH Axe Kills", 259, 21);
        	
        	p.getFrames().sendString("     Use Vengeance Spell 75x", 259, 10);
        	p.getFrames().sendString("Reach 25 Hours of playtime", 259, 11);
        	p.getFrames().sendString("Vote 20 Times", 259, 12);
        	p.getFrames().sendString("Get a hit >700", 259, 13);
        	
        	p.getFrames().sendString("", 259, 26);
        	p.getFrames().sendString("", 259, 27);
        	p.getFrames().sendString("", 259, 28);
        	p.getFrames().sendString("", 259, 29);
        	
        	p.getFrames().sendString("", 259, 14);
        	p.getFrames().sendString("", 259, 15);
        	p.getFrames().sendString("", 259, 16);
        	p.getFrames().sendString("", 259, 17);
    	} else if (p.Ethan == 3) {
    		p.getFrames().sendString("     Kill 500 Players", 259, 30);
        	p.getFrames().sendString("Kill 750 Players", 259, 31);
        	p.getFrames().sendString("Get 150 Dangerous Kills", 259, 32);
        	p.getFrames().sendString("Get 300 Dangerous Kills", 259, 33);
        	
        	p.getFrames().sendString("     Get 150 Multi kills", 259, 22);
        	p.getFrames().sendString("Get 250 Multi kills", 259, 23);
        	p.getFrames().sendString("Open 50 crate", 259, 24);
        	p.getFrames().sendString("Open 75 crates", 259, 25);
        	
        	p.getFrames().sendString("     Get 50 AGS Kills", 259, 18);
        	p.getFrames().sendString("Get 50 DClaws Kills", 259, 19);
        	p.getFrames().sendString("Get 50 DDS Kills", 259, 20);
        	p.getFrames().sendString("Get 50 DH Axe Kills", 259, 21);
        	
        	p.getFrames().sendString("     Use Vengeance Spell 250x", 259, 10);
        	p.getFrames().sendString("Reach 50 Hours of playtime", 259, 11);
        	p.getFrames().sendString("Vote 50 Times", 259, 12);
        	p.getFrames().sendString("Get a hit >850", 259, 13);
        	
        	p.getFrames().sendString("Achieve Top Donor Rank", 259, 26);
        	p.getFrames().sendString("Achieve Top Voter Rank", 259, 27);
        	p.getFrames().sendString("Achieve Top PKer Rank", 259, 28);
        	p.getFrames().sendString("", 259, 29);
        	
        	p.getFrames().sendString("", 259, 14);
        	p.getFrames().sendString("", 259, 15);
        	p.getFrames().sendString("", 259, 16);
        	p.getFrames().sendString("", 259, 17);
    	} else if (p.Ethan == 4) {
    		p.getFrames().sendString("     Kill 1500 Players", 259, 30);
        	p.getFrames().sendString("Get 500 Dangerous Kills", 259, 31);
        	p.getFrames().sendString("Get a hit >950", 259, 32);
        	p.getFrames().sendString("Open 100 crates", 259, 33);
        	
        	p.getFrames().sendString("     Roll 1 on a Dice", 259, 22);
        	p.getFrames().sendString("Roll 100 on a Dice", 259, 23);
        	p.getFrames().sendString("Plant a black or white Flower", 259, 24);
        	p.getFrames().sendString("Donate a total of 50$", 259, 25);
        	
        	p.getFrames().sendString("     Get a 10 Killstreak", 259, 18);
        	p.getFrames().sendString("Get a 50 Killstreak", 259, 19);
        	p.getFrames().sendString("", 259, 20);
        	p.getFrames().sendString("", 259, 21);
        	
        	p.getFrames().sendString("", 259, 10);
        	p.getFrames().sendString("", 259, 11);
        	p.getFrames().sendString("", 259, 12);
        	p.getFrames().sendString("", 259, 13);
        	
        	p.getFrames().sendString("", 259, 26);
        	p.getFrames().sendString("", 259, 27);
        	p.getFrames().sendString("", 259, 28);
        	p.getFrames().sendString("", 259, 29);
        	
        	p.getFrames().sendString("", 259, 14);
        	p.getFrames().sendString("", 259, 15);
        	p.getFrames().sendString("", 259, 16);
        	p.getFrames().sendString("", 259, 17);
    	}
    	
        
         }
    }


