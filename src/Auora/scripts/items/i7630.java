package Auora.scripts.items;

import Auora.model.Container;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.itemScript;
import Auora.util.Misc;

import java.util.ArrayList;
import java.util.List;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;
import Auora.model.Container;
import Auora.model.Item;
import Auora.model.player.Player;
import Auora.model.player.Prices;
import Auora.rscache.ItemDefinitions;


public class i7630 extends itemScript {	

	
    public void option1(Player player, int itemId, int interfaceId, int slot) {    
    	 //player.getFrames().sendInterface(364);
    	player.getFrames().sendInterface(478);
    	       
    	        
    	     //   player.getFrames().sendItems(141, new Container , false);       
    	
     
        //  World.getShopManager().openShop(player, 1);
    
    
      int i = 0 ;     
      while( i < 250 )
      {
    	 player.getFrames().sendItems(141, player.getInventory().getContainer(), false);
    	 // player.getFrames().sendString("line" + i, 478, i);          
          i++ ;
      }

    }
}