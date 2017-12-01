package Auora.model.tabs;

import java.text.SimpleDateFormat;
import java.util.Date;

import Auora.model.World;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.GameServer;
import Auora.events.Task;
import Auora.model.*;
import Auora.model.Hits.Hit;
import Auora.model.Hits.HitType;
import Auora.model.tabs.CommandsTab;
import Auora.model.tabs.QuestTab;
import Auora.model.player.account.PlayerSaving;
import Auora.model.player.clan.Clan;
import Auora.model.player.content.BountyHunter;
import Auora.model.player.fields.Titles;
import Auora.model.route.CoordinateEvent;
import Auora.net.Frames;
import Auora.net.Packets;
import Auora.net.codec.ConnectionHandler;
import Auora.skills.prayer.Prayer;
import Auora.util.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Jonathan spawnscape
 */

public class InfoTab {

    public static void initiate_interface(Player p) {
    	
    	 	
    	
    	
        
    	Date d1 = new Date();
       	SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
       	String formattedDate = df.format(d1);
       	
       	p.getFrames().sendString( "Players Online: <col=ffffff>"+World.getPlayers().size(),930, 10);
 	   
       	String rank = (p.getStaffRights() == StaffRights.PLAYER) ? "" + p.getDonatorRights().getColor() + p.getDonatorRights().getShad() + p.getDonatorRights().getTitle() : "<img=" + p.getStaffRights().getCrownId() + ">" + p.getStaffRights().getColor() + p.getStaffRights().getShad() + p.getStaffRights().getTitle();
        String kdr = p.deaths == 0 ? "infinite" : ((p.dangerousKills + p.safeKills) / p.deaths)+"";
        String targ = p.getBountyHunter().hasTarget() ? "<col=ff0000>BH Target: "+p.getBountyHunter().target.getDisplayName() + "<br><br>" : "none";
       	String interText =  
    			"<col=FFFF00>Server"
    			+ "<br><col=E68A00>- Time: <col=B2CCCC>"+formattedDate
    			+ "<br><col=E68A00>- Uptime: <col=B2CCCC>"+World.days+"d:"+World.hours+"h:"+World.minutes+"m:"+World.seconds+"s"
    			/*+ "<br><col=E68A00>- Players Online: <col=B2CCCC>"+World.getPlayersOnline() */
    			+ "<br><col=E68A00>- Staff Online: <col=B2CCCC>"+World.getStaffOnline()
    			+ "<br><col=E68A00>- Wilderness Count: <col=B2CCCC>"+World.getPlayersInPvP()
    			+ "<br><col=E68A00>- Talk Requirement: <col=B2CCCC>"
    			+ "<br><col=E68A00>- Weekend Bonus: <col=B2CCCC>"
    			//+ "<br><col=06C400>Ping: "+Server.tickTimer
    			+ "<br>"
    			
    			+ "<br><col=FFFF00>Player"
    			+ "<br><col=E68A00>- Name: <col=B2CCCC>"+Misc.capitiliseFirst(p.getUsername())
/*    			+ "<br><col=E68A00>- Display Name: <col=B2CCCC>"+Misc.capitiliseFirst(getDisplayName())*/
    			+ "<br><col=E68A00>- Rank: <col=B2CCCC>" +rank
/*    			+ "<br><col=E68A00>- Status: <col=B2CCCC>"*/
    			+ "<br><col=E68A00>- Play Time: <col=B2CCCC>" + p.hoursPlayed / 2 / 60 / 60 +"hrs"
    			+ "<br>"
    			
    			+ "<br><col=FFFF00>Statistics"
    			+ "<br><col=E68A00>- PK Points: <col=B2CCCC>"+Misc.insertCommas(p.getPkPoints()+"")
    			+ "<br><col=E68A00>- Vote Points: <col=B2CCCC>"+Misc.insertCommas(""+p.votePoints)
    			+ "<br><col=E68A00>- Warnings: <col=B2CCCC>"
    			+ "<br>"
    			
    			+ "<br><col=FFFF00>Combat"		
    			+ "<br><col=E68A00>- Total Kills: <col=B2CCCC>"+(Misc.insertCommas(p.dangerousKills + p.safeKills+""))
    			+ "<br><col=E68A00>- Unsafe Kills: <col=B2CCCC>"+(Misc.insertCommas(p.dangerousKills+""))
    			+ "<br><col=E68A00>- Deaths: <col=B2CCCC>"+(Misc.insertCommas(p.deaths+""))
    			+ "<br><col=E68A00>- Highest Killstreak: <col=B2CCCC>"+(Misc.insertCommas(p.dangerousKills + p.safeKills+""))
    			+ "<br><col=E68A00>- Current Killstreak: <col=B2CCCC>"+p.killstreak
    			+ "<br><col=E68A00>- KDR : <col=B2CCCC>"+kdr
    			+ "<br>"
    			
    			+ "<br><col=FFFF00>Miscellaneous"	
    			+ "<br><col=E68A00>- Total Commands: <col=B2CCCC>"
    			+ "<br><col=E68A00>- Total Clans: <col=B2CCCC>"
    			+ "<br>";

       		if (p.getBountyHunter().hasTarget()){
       			interText = targ + interText;
       		}
       	
    		p.getFrames().sendString(interText ,930, 16);
    	}
    	
        
         }
   


