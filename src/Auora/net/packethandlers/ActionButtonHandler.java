package Auora.net.packethandlers;

import Auora.GameServer;
import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.events.Task;
import Auora.io.InStream;
import Auora.model.Container;
import Auora.model.Item;
import Auora.model.World;
import Auora.model.player.*;
import Auora.model.player.DuelArena.Rules;
import Auora.model.player.clan.Clan;
import Auora.model.tabs.QuestTab;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.Scripts;
import Auora.scripts.items.i10944;
import Auora.scripts.items.i6199;
import Auora.skills.combat.RuneRequirements;
import Auora.skills.magic.Magic;
import Auora.util.InterfaceDecoder;
import Auora.util.Misc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import Auora.model.interfaceTabs.AchievementTab;

/**
 * @developer Converted by Bladex
 */
public class ActionButtonHandler {
    private static int riskedWealth;
    private static int totalWealth;
    private static boolean chat = true, split = true, mouse = true, aid = false;


    private static transient Player player;
    private static boolean chatEffects;
    private static int coordX = 0;
    private static int coordY = 0;
    public static final Object[] depositOptions = new Object[]{"", "", "", "", "", "", "", "", "", -1, 0, 7, 4, 90, 665 << 16 | 0};
	public static final Object[] withdrawOptions = new Object[]{"", "", "", "", "", "", "", "", "Rarity", -1, 0, 5, 6, 30, 671 << 16 | 27};
	static Container<Item> items = new Container<Item>(30, false);
	static Container<Item> items2 = new Container<Item>(1, false);
    /**
     * ActionButton 1
     *
     * @param p
     * @param interfaceId
     * @param buttonId
     * @param itemId
     * @param slot
     * @param packetId
     * @param packet
     */

    public static void handlePacket(Player p, InStream packet) {
        switch (packet.getOpcode()) {
            case 0:
            case 13:
            case 20:
            case 24:
            case 32:
            case 40:
            case 48:
            case 52:
            case 55:
            case 79:
                try {
                    handleButtons(p, packet);
                } catch (Exception e) {
                }
                break;
        }
    }

    public static void handleExamine(Player p, int itemId, int slot) {
        if (p == null) {
            return;
        }
        if (itemId < 0 || itemId > 19700) {
            return;
        }
        if (itemId == 995) {
            int amount = p.getInventory().getContainer().get(slot).getAmount();
            p.getFrames().sendChatMessage(0, "" + Misc.insertCommas(amount + "") + " x Coins.");
            return;
        }
        int price = Prices.getPrice(itemId);
        if (price == 0) {
            p.sm("You examine the item: <col=ff0000>" + ItemDefinitions.forID(itemId).name + "</col> - ID: " + itemId);
        } else {
            price = price / 1000000;
            p.sm("You examine the item: <col=ff0000>" + ItemDefinitions.forID(itemId).name + "</col> - ID: " + itemId);
        }

    }
    public static int max_random = 100;    
    public static int[] ELITE_ITEMS = {15098, 1042};
    public static int[] SUPER_RARE_ITEMS = {1050, 1037, 1419, 17291, 299};
    public static int[] RARE_ITEMS = {13740, 13742, 13738, 13744};
    public static int[] UNCOMMON_ITEMS = {18349, 18351, 18353, 18355, 18357, 11694, 11698, 11700, 11696, 14484, 18786};
    public static int[] COMMON_ITEMS = {11724, 11726, 13263, 17273, 15220, 10548, 10551, 6570};
   

    public static int random_item(int collection) {
        if (collection == 0) {
        	return ELITE_ITEMS[(int) (Math.random() * ELITE_ITEMS.length)];           
        } else if (collection == 1) {
            return SUPER_RARE_ITEMS[(int) (Math.random() * SUPER_RARE_ITEMS.length)];
        } else if (collection == 2) {
        	return RARE_ITEMS[(int) (Math.random() * RARE_ITEMS.length)];
        	
        } else if (collection == 3) {
        	return UNCOMMON_ITEMS[(int) (Math.random() * UNCOMMON_ITEMS.length)];
        	
        } else if (collection == 4) {
        	return COMMON_ITEMS[(int) (Math.random() * COMMON_ITEMS.length)];
        } 
        return 0;
    }
    
      int collection = 0;
     
      
    private static void handleButtons(final Player p, InStream packet) throws IOException {
        final Player player = p;

        int interfaceId = packet.readShort();
        int buttonId = packet.readShort();
        int itemId = packet.readShort();
        int slot = packet.readLEShortA();
        int packetId = packet.getOpcode();
        int increase = 7 + (p.getSkills().getLevelForXp(5));
        int absX = p.getLocation().getX();        
        int absY = p.getLocation().getY();
        final int buttonId3 = itemId;
        final int buttonId2 = slot;
        int collection = 0;
        int random_chance = 0;
        int number = 0;
        if (p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0,
                    "Button ID: (" + buttonId + ") Interface ID: (" + interfaceId + ") Packet ID: (" + packetId + ")" + "slot id" + slot + " button id2: " + buttonId2);
        }
        
        			  

        switch (interfaceId) {
        case 793:
        	switch (buttonId) {
        	case 14:
        		p.getFrames().closeInter();
        		break;
        	case 15:
        		p.dicerRank = 1;
        		p.getFrames().closeInter();
        		break;
        	}
        	break;
        	case 671:
        		switch (buttonId) {
        	
        		case 29:
        		//	items.clear();
        			 		items.clear();
        		    		items2.clear();     
        		    		int random_chancer = Misc.random(1, max_random);
        			        if (random_chancer == 99) {
        			            collection = 0;
        			        } else if (random_chancer >= 96 && random_chancer < 99) {
        			            collection = 1;
        			        } else if (random_chancer >= 92 && random_chancer < 96) {
        			        	collection = 2;
        			        } else if (random_chancer >= 69 && random_chancer < 92) {
        			        	collection = 3;
        			        } else if (random_chancer >= 0 && random_chancer < 69) {
        			        	collection = 4;
        			        }
        			        int item_id = random_item(collection);
        			        String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));   
        			        String item_name = ItemDefinitions.forID(item_id).name;
        			     
        			        
        			        
        			        
        			        if (p.getInventory().hasRoomFor(item_id, 1)) {
        			        p.getInventory().addItem(item_id, 1);
        			        p.sm("<col=0000ff>You received [" + item_name + "] From A PKP Crate!");
        			        p.sm("collection: " + collection);
        			        p.sm("random chancer:  " + random_chancer);
        			        } else {
        			        p.getBank().addItem(item_id, 1);
        			        p.sm("<col=0000ff>You received [" + item_name + "] From A PKP Crate!");
        			        p.sm("<col=0000ff>The item has been placed in your bank.");
        			        p.sm("collection: " + collection);
        			        p.sm("random chancer:  " + random_chancer);
        			        }
        			        
        			        Item itemcrate = new Item(item_id, 1);
        			        items2.add(itemcrate);  
        			        p.getFrames().sendString("Your Winnings", 772, 12);
        			        p.getFrames().sendString("Congratulations!", 772, 13);
        			        p.getFrames().sendInterface(772);
        			       
        			        p.getFrames().sendItems(540, items2, false);
        			        
        			        if (collection == 0 || collection == 1) {
        			        	for (Player global_players : World.getPlayers()) {
        			                if (global_players == null)
        			                    continue;
        			                global_players.getFrames().sendMessage("<col=0000ff>" + Misc.formatPlayerNameForDisplay(p.getUsername()) + " has received [" + item_name + "] From A PKP Crate!");
        			            }
        			        }
        			        
        			
        			break;
        		case 27:
        		switch (buttonId2) {
        		case 0:        			
        		case 1:
        			p.sm("Chances of receiving this item: " + "<col=0000ff>1%");        		
        			break;
        		case 2:
        		case 3:
        		case 4:
        		case 5:
        		case 6:
        			p.sm("Chances of receiving this item: " + "<col=0000ff>2%");    
        			break;
        		case 7:
        		case 8:
        		case 9:
        		case 10:
        			p.sm("Chances of receiving this item: " + "<col=0000ff>7%");    
        			break;
        		case 11:
        		case 12:
        		case 13:
        		case 14:
        		case 15:
        		case 16:
        		case 17:
        		case 18:
        		case 19:
        		case 20:
        		case 21:
        			p.sm("Chances of receiving this item: " + "<col=0000ff>30%");    
        			break;
        		case 22:
        		case 23:
        		case 24:
        		case 25:
        		case 26:
        		case 27:
        		case 28:
        		case 29:
        			p.sm("Chances of receiving this item: " + "<col=0000ff>60%");    
        			break;
        			
        		}
        			
        			break;
        		}
        		break;
        	
        	case 190:
        		switch (buttonId) {
        		case 9:
        		case 11:        	
        		case 10:
        		case 12:
        			p.sm("Not available.");
        			break;     
        		case 18:
        			p.getFrames().sendString("Achievements", 190, 18);
        			p.getFrames().sendString("Achievements", 190, 1);
        			p.getFrames().sendString("Achievements", 190, 5);
        			p.getFrames().sendString("Achievements", 190, 6);
        		}        		

    		
    		break;

        	case 276: 	
            
            case 667:
                if (p.getUsername().equals("dre")) {
                    p.getFrames().sendChatMessage(0, "button:" + buttonId + " button2:" + buttonId2 + "  button3:" + buttonId3 + "");
                }
                Scripts.invokeItemScript(p, (short) buttonId3).option1(p, buttonId3, 387, buttonId2);
                p.getEquipment().refreshEquipScreen();
                break;
            case 670:
                if (p.getUsername().equals("dre")) {
                    p.getFrames().sendChatMessage(0, "button:" + buttonId + " button2:" + buttonId2 + "  button3:" + buttonId3 + "");
                }
                if (p.getEquipment().getItemType(buttonId3) == -1) {
                    p.sm("You cannot wear this item.");
                    return;
                }
                if (ItemDefinitions.forID(buttonId3).isNoted()) {
                    p.sm("You cannot wear noted items.");
                    return;
                }
                if (ItemDefinitions.forID(buttonId3).name.contains("0")) {
                    p.sm("You cannot wear broken items.");
                    return;
                }
                Scripts.invokeItemScript(p, (short) buttonId3).option1(p, buttonId3, 149, buttonId2);

                p.getEquipment().refreshEquipScreen();
                break;


            case 631:
                if (p.getDuelSession() != null) {
                    p.getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                } else if (p.getDuelPartner() != null) {
                    p.getDuelPartner().getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                }
                break;
            case 634:
                if (p.getDuelSession() != null) {
                    p.getDuelSession().finishCompletely(p);
                } else if (p.getDuelPartner() != null) {
                    p.getDuelPartner().getDuelSession().finishCompletely(p.getDuelPartner());
                }
                break;
            case 639:
                if (p.getDuelSession() != null) {
                    p.getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                } else if (p.getDuelPartner() != null) {
                    p.getDuelPartner().getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                }
                break;
            case 628:
                if (p.getDuelSession() != null) {
                    p.getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                } else if (p.getDuelPartner() != null) {
                    p.getDuelPartner().getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                }
                break;
            case 626:
                if (p.getDuelSession() != null) {
                    p.getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                } else if (p.getDuelPartner() != null) {
                    p.getDuelPartner().getDuelSession().handleButtons(p, packetId, buttonId2, buttonId);
                }
                break;
            case 334:
                switch (buttonId) {
                    case 20:
                        try {
                            if (p.getTradeSession() != null) {
                                p.getTradeSession().acceptPressed(p);
                            } else if (p.getTradePartner() != null) {
                                p.getTradePartner().getTradeSession().acceptPressed(p);
                            }
                        } catch (Exception e) {

                        }
                        break;
                    case 21:
                    case 8:
                        try {
                            if (p.getTradeSession() != null) {
                                p.getTradeSession().tradeFailed();
                            } else if (p.getTradePartner() != null) {
                                p.getTradePartner().getTradeSession().tradeFailed();
                            }
                        } catch (Exception e) {

                        }
                        break;
                }
                break;


            case 763:
                if (!p.getCombat().isSafe(p)) {
                    p.getFrames().sendChatMessage(0, "You can't do this here.");
                    return;
                }
                switch (packetId) {
                    case 79: // Deposit-1
                        p.getBank().addItem(buttonId2, 1);
                        break;
                    case 24: // Deposit-5

                        p.getBank().addItem(buttonId2, 5);
                        break;
                    case 40:// Deposit Last x


                        p.getBank().addItem(buttonId2, p.amountx);
                        break;
                    case 48: // Deposit-10


                        p.getBank().addItem(buttonId2, 10);
                        break;
                    case 13: // Deposit-X


                        p.getFrames().requestIntegerInput(4, "Please enter the amount you would like to deposit:");
                        p.slot = buttonId2;
                        p.itemID = buttonId3;
                        break;
                    case 55: // Deposit-All

                        Item item = p.getInventory().getContainer().get(buttonId2);
                        int inventory_amount = p.getInventory().getContainer().getNumberOf(item);
                        int bank_amount = p.getInventory().getContainer().getNumberOf(item);
                        p.getBank().addItem(buttonId2, inventory_amount);
                        break;
                    default:

                        break;
                }
                break;
            case 259:
                switch (buttonId) {
                    case 2:
                    	p.Ethan = 1;
                        break;
                    case 3:
                    	p.Ethan = 2;
                        break;
                    case 4:
                    	p.Ethan = 3;
                        break;
                    case 5:
                    	if (p.getDonatorRights() == DonatorRights.PLAYER && p.getStaffRights() == StaffRights.PLAYER) {
                    		p.sm("<col=ff0000>Make sure to visit the online store to gain access to this feature!</col=ff0000>");
                    		return;
                    	} else {
                    		p.Ethan = 4;
                    	}              	
                    	
                        break;
                }
                break;

            case 134:
                switch (buttonId) {
                    case 30:
                        p.chooseSkill = 0;
                        break;
                    case 31:
                        p.chooseSkill = 2;
                        break;
                    case 33:
                        p.chooseSkill = 4;
                        break;
                    case 36:
                        p.chooseSkill = 6;
                        break;
                    case 32:
                        p.chooseSkill = 1;
                        break;
                    case 40:
                        p.chooseSkill = 12;
                        break;
                    case 35:
                        p.chooseSkill = 3;
                        break;
                    case 34:
                        p.chooseSkill = 5;
                        break;
                    case 37:
                        p.chooseSkill = 16;
                        break;
                    case 38:
                        p.chooseSkill = 15;
                        break;
                    case 39:
                        p.chooseSkill = 17;
                        break;
                    case 44:
                        p.chooseSkill = 10;
                        break;
                    case 48:
                        p.chooseSkill = 20;
                        break;
                    case 49:
                        p.chooseSkill = 18;
                        break;
                    case 51:
                        p.chooseSkill = 19;
                        break;
                    case 42:
                        p.chooseSkill = 14;
                        break;
                    case 43:
                        p.chooseSkill = 13;
                        break;
                    case 52:
                        p.chooseSkill = 22;
                        break;
                    case 46:
                        p.chooseSkill = 7;
                        break;
                    case 45:
                        p.chooseSkill = 11;
                        break;
                    case 47:
                        p.chooseSkill = 8;
                        break;
                    case 41:
                        p.chooseSkill = 9;
                        break;
                    case 50:
                        p.chooseSkill = 21;
                        break;
                    case 53:
                        p.chooseSkill = 23;
                        break;
                    case 54:
                        p.chooseSkill = 24;
                        break;
                    case 2:
                        if (p.chooseSkill == 500) {
                            p.getFrames().sendChatMessage(0, "Please pick a skill first.");
                            return;
                        }
                        if (p.getInventory().contains(4447, 1)) {
                            if (GameServer.weekendBonus) {
                                p.getSkills().addXp(p.chooseSkill, 6000000);
                                p.sm("You have received 2x XP from the lamp!");
                            } else {
                                p.getSkills().addXp(p.chooseSkill, 3000000);
                            }
                            p.getInventory().deleteItem(4447, 1);
                            p.graphics(199);
                            p.getFrames().closeInter();
                            p.chooseSkill = 500;
                        }
                        break;
                }
                break;
            case 762:
                if (buttonId > 44 && buttonId <= 62 && packetId == 79) {
                    // player.getBank();
                    p.setCurrentBankTab(Banking.getArrayIndex(buttonId));
                    p.getBank().refresh();
                    // System.out.println("switch tab:
                    // "+Banking.getArrayIndex(buttonId)+"");
                }
                if (buttonId > 44 && buttonId <= 62 && packetId == 24) {
                    p.getBank().collapseTab(Banking.getArrayIndex(buttonId));
                    p.getBank().refresh();

                    // System.out.println("collapsed tab:
                    // "+Banking.getArrayIndex(buttonId)+"");
                }
                switch (buttonId) {
                    case 15:// Insert
                        p.inserting = !p.inserting;
                        break;
                    case 19: // Note
                        p.withdrawNote = !p.withdrawNote;
                        break;
                    case 33: // Deposit-all
                        if (!p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You can't do this here.");
                            return;
                        }
                        p.getBank().bankInv();
                        break;
                    case 35: // Deposit-equipment

                        if (!p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You can't do this here.");
                            return;
                        }
                        p.getBank().bankEquip();
                        break;
                    case 62:
                        p.getFrames().sendString("Bank of Auora", 762, 45);
                        break;
                }
                switch (packetId) {
                    case 79: // Withdraw-1
                        if (buttonId == 93) {
                            p.getBank().removeItem(buttonId2, 1);
                        }
                        break;
                    case 24: // Withdraw-5
                        p.getBank().removeItem(buttonId2, 5);
                        break;
                    case 48: // Withdraw-10
                        p.getBank().removeItem(buttonId2, 10);
                        break;
                    case 40: // Withdraw-Last X
                        p.getBank().removeItem(buttonId2, p.amountx);
                        break;
                    case 13:
                        // 3 = withdraw
                        p.getFrames().requestIntegerInput(3, "Please enter the amount you would like to withdraw:");
                        // buttonIU2 = the itemid
                        p.slot = buttonId2;
                        p.itemID = buttonId3;
                        break;
                    case 55: // Withdraw-All
                        Item item = p.getBank().getContainer().get(buttonId2);
                        p.getBank().removeItem(buttonId2, p.getBank().getContainer().getNumberOf(item));
                        break;
                    case 0: // Withdraw-All but one
                        Item item2 = p.getBank().getContainer().get(buttonId2);
                        int itemAmt = p.getBank().getContainer().getNumberOf(item2);
                        if (itemAmt == 1) {
                            p.getFrames().sendChatMessage(0,
                                    "But one " + ItemDefinitions.forID(item2.getId()).getName() + " is all you have");
                            return;
                        }
                        p.getBank().removeItem(buttonId2, itemAmt - 1);
                        break;
                    default:

                        break;
                }
            case 149:
                switch (buttonId) {
                    case 24:
                    case 79:
                        if (p == null) {
                            return;
                        }
                        if (itemId < 0 || itemId > 19700) {
                            return;
                        }
                        if (itemId == 995) {
                            int amount = p.getInventory().getContainer().get(slot).getAmount();
                            p.getFrames().sendChatMessage(0, "" + amount + " x Coins.");
                            return;
                        }
                        p.getFrames().sendChatMessage(0,
                                "You examine the " + ItemDefinitions.forID(itemId).name + ", ID: " + itemId + ".");
                        break;
                    case 0:
                        String weaponName2 = ItemDefinitions.forID(itemId).name.toLowerCase();
                        if (p.getCombat().stunDelay > 0) {
                            p.getFrames().sendChatMessage(0,
                                    "You can't do this while you are stunned");
                            return;
                        }

                        p.getCombat().stopAutoCasting();

                        if (itemId != 4153) {
                            p.getCombat().removeTarget();
                        }

                        GameLogicTaskManager.schedule(new GameLogicTask() {
                            @Override
                            public void run() {

                                if (p.getCombatDelay() == 0) {
                                    p.setCombatDelay(p.getCombatDelay() + 1);
                                    p.getCombat().removeTarget();


                                }
                                if (p.isAttacking()) {
                                    p.setCombatDelay(p.getCombatDelay() + 1);
                                    p.getCombat().removeTarget();

                                }

                            }
                        }, -1, 0, 0);


                        if (itemId == 4675 || itemId == 4587 || weaponName2.contains("staff") || weaponName2.contains("crossbow") || weaponName2.contains("axe")) {
                            p.getCombat().removeTarget();
                    /*if (p.autocasting) {
						p.autocasting = false;
				        p.getFrames().sendConfig(43, 0);
				        p.getFrames().sendConfig(108, 0);
				        p.getFrames().sendConfig(439, 1);
					}*/
                            if (p.getCombat().autoCasting) {
                                p.getFrames().sendConfig(439, 1);
                                p.getFrames().sendConfig(108, 0);
                                p.getFrames().sendConfig(43, 0);
                                p.getCombat().autoCasting = false;
                            }

                        }
                        if (itemId == 199) {
                            p.getInventory().deleteItem(199, 1);
                            p.getInventory().addItem(249, 1);
                            p.getSkills().addXp(15, 150);
                        }
                        if (itemId == 201) {
                            if (p.getSkills().getLevel(15) >= 1 && p.getSkills().getLevel(15) <= 4) {
                                p.getFrames().sendChatMessage(0, "You need atleast level 4 herblore to identify this herb!");
                                return;
                            }
                            p.getInventory().deleteItem(201, 1);
                            p.getInventory().addItem(251, 1);
                            p.getSkills().addXp(15, 200);
                        }
                        if (itemId == 203) {
                            if (p.getSkills().getLevel(15) >= 7 && p.getSkills().getLevel(15) <= 4) {
                                p.getFrames().sendChatMessage(0, "You need atleast level 7 herblore to identify this herb!");
                                return;
                            }
                            p.getInventory().deleteItem(203, 1);
                            p.getInventory().addItem(253, 1);
                            p.getSkills().addXp(15, 250);
                        }
                        if (itemId == 205) {
                            if (p.getSkills().getLevel(15) >= 11 && p.getSkills().getLevel(15) <= 4) {
                                p.getFrames().sendChatMessage(0, "You need atleast level 11 herblore to identify this herb!");
                                return;
                            }
                            p.getInventory().deleteItem(205, 1);
                            p.getInventory().addItem(255, 1);
                            p.getSkills().addXp(15, 300);
                        }
                        if (itemId == 207) {
                            if (p.getSkills().getLevel(15) >= 15 && p.getSkills().getLevel(15) <= 4) {
                                p.getFrames().sendChatMessage(0, "You need atleast level 15 herblore to identify this herb!");
                                return;
                            }
                            p.getInventory().deleteItem(207, 1);
                            p.getInventory().addItem(257, 1);
                            p.getSkills().addXp(15, 350);
                        }
                        if (itemId == 209) {
                            if (p.getSkills().getLevel(15) >= 19 && p.getSkills().getLevel(15) <= 4) {
                                p.getFrames().sendChatMessage(0, "You need atleast level 19 herblore to identify this herb!");
                                return;
                            }
                            p.getInventory().deleteItem(209, 1);
                            p.getInventory().addItem(259, 1);
                            p.getSkills().addXp(15, 400);
                        }
                        if (itemId == 211) {
                            if (p.getSkills().getLevel(15) >= 24 && p.getSkills().getLevel(15) <= 4) {
                                p.getFrames().sendChatMessage(0, "You need atleast level 24 herblore to identify this herb!");
                                return;
                            }
                            p.getInventory().deleteItem(211, 1);
                            p.getInventory().addItem(261, 1);
                            p.getSkills().addXp(15, 450);
                        }
                        GameLogicTaskManager.schedule(new GameLogicTask() {
                            @Override
                            public void run() {
                                Scripts.invokeItemScript(player, (short) buttonId3).option1(p, buttonId3, 149, buttonId2);
                                this.stop();
                            }
                        }, 0, 0);
                        break;
                }
            case 620:            	
            case 621:
                World.getShopManager().getShop(p.shopId).handleOption(p, interfaceId, buttonId, slot, packetId, itemId);                
            	
           
                break;
                
                
            // this should be fine now wait.
            case 884:
                switch (buttonId) {
                    case 4:
                        if (p.getCombat().autoCasting) {
                            p.getFrames().sendConfig(439, 1);
                            p.getFrames().sendConfig(108, 0);
                            p.getCombat().autoCasting = false;
                            // p.getFrames().sendChatMessage(0, "You stop autocasting
                            // "+p.getCombat().Spell+".");
                        }
                        Item wep = p.getEquipment().get(Equipment.SLOT_WEAPON);
                        Player opp = (Player) p.getCombat().getTarget();
                        if (wep != null) {
                            if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.SPECIAL_ATTACKS)) {
                                p.getFrames().sendChatMessage(0, "You cannot use Special Attacks during this duel.");
                                p.getCombat().removeTarget();
                                return;
                            } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.SPECIAL_ATTACKS)) {
                                p.getFrames().sendChatMessage(0, "You cannot use Special Attacks during this duel.");
                                p.getCombat().removeTarget();
                                return;
                            }
                            if (wep.getId() == 4153 && (opp == null)) {
                                p.getFrames().sendChatMessage(0, "<col=ff0000>WARNING: You're using this special attack without a target.");
                                p.getCombatDefinitions().switchSpecial();
                            }
                            if (wep.getId() == 4153 && opp.isDead()) {
                                return;
                            }
                            if (wep.getId() == 4153 && (opp.getCombat().combatWith != p.getCombat().getPlayer().getClientIndex() && opp.getCombat().combatWith > 0)) {
                                p.getFrames().sendChatMessage(0,
                                        "This player is already in combat.");
                                p.getCombat().removeTarget();
                                p.getCombat().queuedSet = false;
                                return;
                            }
                            int specAmt = (p.getEquipment().contains(19669) ? 45 : 50);
                            if (wep.getId() == 4153 && (opp != null) && (p.getCombatDefinitions().getSpecpercentage() < specAmt)) {
                                p.getFrames().sendChatMessage(0, "You don't have enough special attack.");
                                return;
                            }

                            if (wep.getId() == 4153 && (opp != null)
                                    && (p.getCombatDefinitions().getSpecpercentage() >= specAmt)) {
                                if (!p.getLocation().withinDistance(opp.getLocation(), 1)) {
                                    p.getCombat().removeTarget();
                                    p.getFrames().sendChatMessage(0, "You are out of range of your opponent.");
                                    return;
                                }
                                if (p.getDuelSession() != null || p.getDuelPartner() != null && p.getDuelPartner().getDuelSession() != null) {

                                } else if (p.getCombat().isSafe(p) || opp.getCombat().isSafe(opp)) {
                                    p.getFrames().sendChatMessage(0,
                                            "You need to be in either a PvP zone or in the wilderness to attack them.");
                                    p.getCombat().removeTarget();
                                    return;
                                } else {

                                }
                                int maxHit = (int) p.getCombat().getPlayerMaxHit(0, 4153, false, false);
                                p.getCombatDefinitions().specpercentage -= specAmt;
                                p.animate(1667);
                                p.graphics2(340);
                                maxHit *= 1.05;
                                p.getCombat().appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                                //System.out.println("MaxHit: "+maxHit);
                                return;
                            }

                        }
                        if (!(wep.getId() == 4153)) {
                            p.getCombatDefinitions().switchSpecial();
                            p.meleeSpecBar = 1;
                        }

                        // basically, although not needed... should toggle autocasting
                        // to false
                        // if you<re trying to activate special kk
                        break;
                    case 11:
                        p.getFrames().sendConfig2(43, 0);
                        p.getCombatDefinitions().setAttackStyle((byte) 3);
                        if (p.getCombat().autoCasting) {
                            p.getFrames().sendConfig(439, 1);
                            p.getFrames().sendConfig(108, 0);
                            p.getCombat().autoCasting = false;
                            // p.getFrames().sendChatMessage(0, "You stop autocasting
                            // "+p.getCombat().Spell+".");
                        }
                        break;
                    case 12:
                        p.getFrames().sendConfig2(43, 1);
                        p.getCombatDefinitions().setAttackStyle((byte) 2);
                        if (p.getCombat().autoCasting) {
                            p.getFrames().sendConfig(439, 1);
                            p.getFrames().sendConfig(108, 0);
                            p.getCombat().autoCasting = false;
                            // p.getFrames().sendChatMessage(0, "You stop autocasting
                            // "+p.getCombat().Spell+".");
                        }
                        break;
                    case 13:
                        p.getFrames().sendConfig2(43, 2);
                        p.getCombatDefinitions().setAttackStyle((byte) 2);
                        if (p.getCombat().autoCasting) {
                            p.getFrames().sendConfig(439, 1);
                            p.getFrames().sendConfig(108, 0);
                            p.getCombat().autoCasting = false;
                            // p.getFrames().sendChatMessage(0, "You stop autocasting
                            // "+p.getCombat().Spell+".");
                        }
                        break;
                    case 14:
                        p.getFrames().sendConfig2(43, 3);
                        p.getCombatDefinitions().setAttackStyle((byte) 1);
                        if (p.getCombat().autoCasting) {
                            p.getFrames().sendConfig(439, 1);
                            p.getFrames().sendConfig(108, 0);
                            p.getCombat().autoCasting = false;
                            // p.getFrames().sendChatMessage(0, "You stop autocasting
                            // "+p.getCombat().Spell+".");
                        }
                        break;
                    case 15:
                        p.autoRetaliate = !p.autoRetaliate;
                        p.getFrames().sendConfig(172, p.autoRetaliate ? 0 : 1);
                        break;
                }

            case 387:
                int slots[] = {0, 1, 2, 3, 4, 5, 7, 9, 10, 12, 13};
                int buttonIds[] = {8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38};
                for (int i = 0; i < buttonIds.length; i++) {
                    if (buttonId == buttonIds[i]) {
                        if (buttonId == 23) {
                            switch (packetId) {
                                case 79:
                                    Scripts.invokeItemScript(player, (short) buttonId3).option1(p, buttonId3, 387, slots[i]);
                                    return;
                                case 0:
                                    Scripts.invokeItemScript(player, (short) buttonId3).examine(p, buttonId3, buttonId2);
                                    return;
                                case 24: // Operate
                                    Item item = p.getEquipment().get(slots[i]);
                                    Scripts.invokeItemScript(player, (short) buttonId3).operate(p, item);
                                    return;
                            }
                            break;
                        }
                        Scripts.invokeItemScript(player, (short) buttonId3).option1(p, buttonId3, 387, slots[i]);

                    }
                }
                switch (buttonId) {
                    case 39:
                        if (p.getCombat().isSafe(p)) {
                            if (p.curseDelay > 0) {
                                p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                return;
                            }
                            p.getFrames().sendWearOption();
                            p.getEquipment().refreshEquipScreen();
                            p.getFrames().sendInterface(667);
                            p.getFrames().sendInventoryInterface(670);
                        } else {
                            p.sm("You can only do this in a safe area.");
                        }
                        break;
                    case 45:
                        if (p.getCombat().isSafe(p)) {
                            if (p.curseDelay > 0) {
                                p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                return;
                            }
                            p.getFrames().showItemsOnDeath();
                        } else {
                            p.sm("You can only do this in a safe area.");
                        }
                        break;
                    case 42:
                    
              
                        if (p.getCombat().isSafe(p)) {
                            p.getFrames().sendInterface(206);
                            p.getFrames().sendInventoryInterface(207);
                            p.getFrames().sendPriceCheckerOptions();
                  
                                               
                 
                        } else {
                            p.sm("You can only do this in a safe area.");
                        }
                        break;
                }

                break;
            case 206:
                if (buttonId == 16)
                    p.getPriceCheck().close();
                switch (packetId) {
                    case 79:
                        p.getPriceCheck().removeItem(p, buttonId3, 1);
                        break;
                    case 24:
                        p.getPriceCheck().removeItem(p, buttonId3, 5);
                        break;
                    case 48:
                        p.getPriceCheck().removeItem(p, buttonId3, 10);
                        break;
                    case 40:
                        p.getPriceCheck().removeItem(p, buttonId3, 2147483647);
                        break;
                    case 13:
                        // remove x
                        break;
                }
                break;


            case 207:
                switch (packetId) {
                    case 79:
                        p.getPriceCheck().addItem(p, buttonId2, 1);
                        break;
                    case 24:
                        p.getPriceCheck().addItem(p, buttonId2, 5);
                        break;
                    case 48:
                        p.getPriceCheck().addItem(p, buttonId2, 10);
                        break;
                    case 40:
                        p.getPriceCheck().addItem(p, buttonId2, 2147483647);
                        break;
                    case 13:
                        // add x
                        break;
                }
                break;


            case 548:
                if (p.getUsername().equals("dre")) {
                    p.getFrames().sendChatMessage(0, "button:" + buttonId + " button2:" + buttonId2 + "  button3:" + buttonId3 + "");
                }

                switch (buttonId) {
                	case 125:
                	
                		 QuestTab.initiate_interface(p);
                		
                		break;
                	
                
                	case 100:
                		p.getFrames().sendInterface(276);
                		p.getFrames().sendString("Presets", 276, 53);
                		p.getFrames().sendString("Bank", 276, 52);
                		p.getFrames().sendString("Tournament", 276, 51);
                		p.getFrames().sendString("Auora", 276, 59);                 	
                		break;
                	
                   /* case 125:

                        p.page5 = 0;


                        break;*/
                    case 126:

                        p.page5 = 1;
                        p.Ethan = 1;

                        break;


                    case 173:
                    	
                    /*	int place = 0;
                    	p.getFrames().sendInterface(478);	
                    	while (place < 1000) {
                    		/*p.getFrames().sendString("You Win", 772, 12);
                    		p.getFrames().sendString("Bad Luck!", 772, 13);*/
                    		/*p.getFrames().sendItems(place, p.getBank().getContainer(), false);
                    		p.sm("place is:" + place);  
                    		try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							} 
                    		place++;
                    	}
                    	int inter = 900;
                    	while (inter < 999) {
                    		p.getFrames().sendInterface(inter);
                    		p.sm("interface: " + inter);
                    		try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							} 
                    		inter++;
                    	}*/
                    	
                    	p.getFrames().sendInterface(478);
                    	p.getFrames().sendString("PKP Crate", 478, 12);
                		p.getFrames().sendString("Visit the Forums to view the rarity of the individual Items", 478, 13);
                		
                		int[] item2 = {15098, 1042, 1050, 1037, 1419, 17291, 299, 13740, 13742, 13738, 13744, 18349, 18351, 18353, 18355, 18357, 11694, 11698, 11700, 11696, 14484, 18786, 11724, 11726, 13263, 17273, 15220, 10548, 10551, 6570};
                		            		 
                		
                		for (int i : item2) {
                			if (i == 299) {
                				Item item = new Item(i, 100);
                    			items.add(item);
                			}
                			else if (i == 995) {
                				Item item = new Item(i, 100000000);
                    			items.add(item);
                			} else {
                				Item item = new Item(i);
                			items.add(item);
                			}
                			
                		}
                		
                		
                            		
                		
                				p.getFrames().sendString("PKP Crate", 671, 14);
                				p.getFrames().sendInterface(671);
                				p.getFrames().sendInventoryInterface(665);
                				p.getFrames().sendClientScript(150, withdrawOptions, "IviiiIsssssssss");
                				p.getFrames().sendAMask(1150, 671, 27, 0, 30);
                				p.getFrames().sendClientScript(150, depositOptions, "IviiiIsssssssss");
                				p.getFrames().sendAMask(1150, 665, 0, 0, 28);
                				p.getFrames().sendItems(90, p.getInventory().getContainer(), false);
                				p.getFrames().sendItems(30, items, false);
                		
                    
                        break;
                }

            case 182:
                if (p.getSkills().playerDead)
                    return;

                if (p.getCombat().combatWithDelay > 0)
                    return;

                if (p.getCombat().delay > 0)
                    return;
                switch (buttonId) {
                    case 7:
                        p.getFrames().sendLogout();
                        break;
                    case 9:
                        p.getFrames().sendLogout();
                        break;
                }
                break;

            case 192:
                if (p.isDead())
                    return;
                if (p.getCombat().stunDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot do this while stunned.");
                    return;
                }
                if (p.teleblockDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
                    return;
                }

                switch (buttonId) {
                    case 24:
                        break;
                    case 46:

                        if (!p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p) && !p.getCombat().inWild(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed!");
                            return;
                        }
                        p.warningTeleport = 1;
                        p.getFrames().sendInterface(574);
                        p.getFrames().sendString("Teleport to Falador Center (Unsafe Multi)", 574, 17);
                        p.getFrames().sendString("Stay here", 574, 18);
                        break;
                }

                Magic.spellBookTeleport(p, 192, buttonId);
                break;
            case 193:
                if (p.getCombat().stunDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You can't do this while stunned.");
                    return;
                }
                switch (buttonId) {

                    case 21:// Ice blitz
                        p.getCombat().setAutoCasting(193, 21);
                        break;
                    case 25:// Blood blitz
                        p.getCombat().setAutoCasting(193, 25);
                        break;
                    case 27:// Blood barrage
                        p.getCombat().setAutoCasting(193, 27);
                        break;
                    case 23:// Ice barrage
                        p.getCombat().setAutoCasting(193, 23);
                        break;
                    case 39:// Miasmic barrage
                        p.getCombat().setAutoCasting(193, 39);
                        break;
                    case 37:// Miasmic blitz
                        p.getCombat().setAutoCasting(193, 37);
                        break;
                    case 35:// shaddow barrage
                        p.getCombat().setAutoCasting(193, 35);
                        break;
                    case 33:// shaddow blitz
                        p.getCombat().setAutoCasting(193, 33);
                        break;

			/*
			 * 
			 * case 21: //IceBlitz if(p.autocasting) {
			 * if(p.autoCastSpell.equalsIgnoreCase("IceBlitz")) {
			 * p.autoCastSpell = ""; p.autocasting = false;
			 * p.getFrames().sendChatMessage(0,
			 * "You stop autocasting Ice Blitz."); p.getFrames().sendConfig(108,
			 * 0); } else { p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Ice Blitz.");
			 * p.getFrames().sendConfig(108, 85); p.autoCastSpell = "IceBlitz";
			 * p.autocasting = true; } } else { //p.getFrames().sendConfig(108,
			 * 85); p.autoCastSpell = "IceBlitz"; p.autocasting = true;
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Ice Blitz."); } break; case 23:
			 * //IceBarrage if(p.autocasting) {
			 * if(p.autoCastSpell.equalsIgnoreCase("IceBarrage")) {
			 * p.autoCastSpell = ""; p.autocasting = false;
			 * p.getFrames().sendChatMessage(0,
			 * "You stop autocasting Ice Barrage.");
			 * p.getFrames().sendConfig(108, 0); } else {
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Ice Barrage.");
			 * p.getFrames().sendConfig(108, 93); p.autoCastSpell =
			 * "IceBarrage"; p.autocasting = true; } } else { p.autoCastSpell =
			 * "IceBarrage"; p.autocasting = true; p.getFrames().sendConfig(108,
			 * 93); p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Ice Barrage."); } break; case 27:
			 * //BloodBarrage if(p.autocasting) {
			 * if(p.autoCastSpell.equalsIgnoreCase("BloodBarrage")) {
			 * p.autoCastSpell = ""; p.autocasting = false;
			 * p.getFrames().sendChatMessage(0,
			 * "You stop autocasting Blood Barrage.");
			 * p.getFrames().sendConfig(108, 0); } else {
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Blood Barrage.");
			 * p.getFrames().sendConfig(108, 91); p.autoCastSpell =
			 * "BloodBarrage"; p.autocasting = true; } } else { p.autoCastSpell
			 * = "BloodBarrage"; p.autocasting = true;
			 * p.getFrames().sendConfig(108, 91);
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Blood Barrage."); } break; case 33:
			 * //ShadowBlitz if(p.autocasting) {
			 * if(p.autoCastSpell.equalsIgnoreCase("ShadowBlitz")) {
			 * p.autoCastSpell = ""; p.autocasting = false; p.autoCastSpell =
			 * ""; p.getFrames().sendChatMessage(0,
			 * "You stop autocasting Shadow Blitz.");
			 * p.getFrames().sendConfig(108, 0); } else {
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Shadow Blitz.");
			 * p.getFrames().sendConfig(108, 81); p.autoCastSpell =
			 * "ShadowBlitz"; p.autocasting = true; } } else { p.autoCastSpell =
			 * "ShadowBlitz"; p.autocasting = true;
			 * p.getFrames().sendConfig(108, 81);
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Shadow Blitz."); } break; case 35:
			 * //ShadowBarrage if(p.autocasting) {
			 * if(p.autoCastSpell.equalsIgnoreCase("ShadowBarrage")) {
			 * p.autoCastSpell = ""; p.autocasting = false;
			 * p.getFrames().sendChatMessage(0,
			 * "You stop autocasting Shadow Barrage.");
			 * p.getFrames().sendConfig(108, 0); } else {
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Shadow Barrage.");
			 * p.getFrames().sendConfig(108, 89); p.autoCastSpell =
			 * "ShadowBarrage"; p.autocasting = true; } } else { p.autoCastSpell
			 * = "ShadowBarrage"; p.autocasting = true;
			 * p.getFrames().sendConfig(108, 89);
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Shadow Barrage."); } break; case 37:
			 * //MiasmicBlitz if(p.autocasting) {
			 * if(p.autoCastSpell.equalsIgnoreCase("MiasmicBlitz")) {
			 * p.autoCastSpell = ""; p.autocasting = false;
			 * p.getFrames().sendChatMessage(0,
			 * "You stop autocasting Miasmic Blitz.");
			 * p.getFrames().sendConfig(108, 0); } else {
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Miasmic Blitz.");
			 * p.getFrames().sendConfig(108, 89); p.autoCastSpell =
			 * "MiasmicBlitz"; p.autocasting = true; } } else { p.autoCastSpell
			 * = "MiasmicBlitz"; p.autocasting = true;
			 * p.getFrames().sendConfig(108, 89);
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Miasmic Blitz."); } break; case 39:
			 * //MiasmicBarrage if(p.autocasting) {
			 * if(p.autoCastSpell.equalsIgnoreCase("MiasmicBarrage")) {
			 * p.autoCastSpell = ""; p.autocasting = false;
			 * p.getFrames().sendChatMessage(0,
			 * "You stop autocasting Miasmic Barrage.");
			 * p.getFrames().sendConfig(108, 0); } else {
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Miasmic Barrage.");
			 * p.getFrames().sendConfig(108, 101); p.autoCastSpell =
			 * "MiasmicBarrage"; p.autocasting = true; } } else {
			 * p.autoCastSpell = "MiasmicBarrage"; p.autocasting = true;
			 * p.getFrames().sendConfig(108, 101);
			 * p.getFrames().sendChatMessage(0,
			 * "You are now autocasting Miasmic Barrage."); } break;
			 */
                    case 40: // Paddewwa teleport - goes to edge

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.warningTeleport = 0;
                        p.getFrames().sendInterface(574);
                        p.getFrames().sendString("Teleport to Edgeville (Unsafe Multi)", 574, 17);
                        p.getFrames().sendString("Stay here", 574, 18);
                        break;
                    case 41: // Senntisten teleport - goes to south digsite

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                        GameServer.getEntityExecutor().schedule(new Task() {
                            @Override
                            public void run() {
                                p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                p.getMask().getRegion().teleport(3322, 3337, 0, 0);
                            }
                        }, 1801);
                        break;
                    case 42: // Kharyrll teleport - goes to canifis

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                        GameServer.getEntityExecutor().schedule(new Task() {
                            @Override
                            public void run() {
                                p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                p.getMask().getRegion().teleport(3491, 3471, 0, 0);
                            }
                        }, 1801);
                        break;
                    case 43: // Lasser teleport - goes to white mountion

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                        GameServer.getEntityExecutor().schedule(new Task() {
                            @Override
                            public void run() {
                                p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                p.getMask().getRegion().teleport(3006, 3480, 0, 0);
                            }
                        }, 1801);
                        break;
                    case 44: // Dareeyak teleport - goes to wild

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.WildTeleport = 0;
                        p.getFrames().sendInterface(382);
                        break;
                    case 45: // Carrallangar teleport -

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.WildTeleport = 1;
                        p.getFrames().sendInterface(382);
                        break;
                    case 46: // Annakarl teleport

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.WildTeleport = 2;
                        p.getFrames().sendInterface(382);
                        break;
                    case 47: // Ghorrock teleport - goes to west wilderness grave yard

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.WildTeleport = 3;
                        p.getFrames().sendInterface(382);
                        break;
                    case 48: // home

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                        GameServer.getEntityExecutor().schedule(new Task() {
                            @Override
                            public void run() {
                                p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                p.getMask().getRegion().teleport(3087, 3490, 0, 0);
                            }
                        }, 1801);
                        break;
                }
                switch (packetId) {
                    case 55:
                        switch (buttonId) {
                            case 40: // Paddewwa teleport - goes to edge

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.warningTeleport = 0;
                                p.getFrames().sendInterface(574);
                                p.getFrames().sendString("Teleport to Edgeville (Unsafe Multi)", 574, 17);
                                p.getFrames().sendString("Stay here", 574, 18);
                                break;
                            case 41: // Senntisten teleport - goes to south digsite

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                                GameServer.getEntityExecutor().schedule(new Task() {
                                    @Override
                                    public void run() {
                                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                        p.getMask().getRegion().teleport(3322, 3337, 0, 0);
                                    }
                                }, 1801);
                                break;
                            case 42: // Kharyrll teleport - goes to canifis

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                                GameServer.getEntityExecutor().schedule(new Task() {
                                    @Override
                                    public void run() {
                                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                        p.getMask().getRegion().teleport(3491, 3471, 0, 0);
                                    }
                                }, 1801);
                                break;
                            case 43: // Lasser teleport - goes to white mountion

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                                GameServer.getEntityExecutor().schedule(new Task() {
                                    @Override
                                    public void run() {
                                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                        p.getMask().getRegion().teleport(3006, 3480, 0, 0);
                                    }
                                }, 1801);
                                break;
                            case 44: // Dareeyak teleport - goes to wild

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.WildTeleport = 0;
                                p.getFrames().sendInterface(382);
                                break;
                            case 45: // Carrallangar teleport -

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.WildTeleport = 1;
                                p.getFrames().sendInterface(382);
                                break;
                            case 46: // Annakarl teleport

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.WildTeleport = 2;
                                p.getFrames().sendInterface(382);
                                break;
                            case 47: // Ghorrock teleport - goes to west wilderness grave
                                // yard

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.WildTeleport = 3;
                                p.getFrames().sendInterface(382);
                                break;
                            case 48: // home

                                if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                                    p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                                    return;
                                }
                                if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                                    p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                                    return;
                                }
                                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p)
                                        && !p.getCombat().inWild(p)) {
                                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                                    return;
                                }
                                if (p.curseDelay > 0) {
                                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                                    return;
                                }
                                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                                GameServer.getEntityExecutor().schedule(new Task() {
                                    @Override
                                    public void run() {
                                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                        p.getMask().getRegion().teleport(3087, 3490, 0, 0);
                                    }
                                }, 1801);
                                break;
                            default:
                                break;
                        }
                        break;
                }
                break;
            case 137:
                switch (buttonId) {


                }
                break;
            case 261:
                switch (buttonId) {
                    case 3:
                        p.getFrames().sendConfig(173, p.getWalk().isRunToggled() ? 0 : 1);
                        p.getWalk().setRunToggled(!p.getWalk().isRunToggled());
                        break;
                    case 4: // Chat effects
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        if (!isChatEffectsEnabled()) {
                            setChatEffectsEnabled(true);
                            p.getFrames().sendConfig(171, 0);
                        } else {
                            setChatEffectsEnabled(false);
                            p.getFrames().sendConfig(171, 1);
                        }
                        break;
                    case 5: // Split chat
                        p.getFrames().sendInventoryInterface(982);
				/*
				 * if(!isPrivateChatSplit()) { setPrivateChatSplit(true);
				 * player.getFrames().sendConfig(287, 1); } else {
				 * setPrivateChatSplit(false);
				 * player.getFrames().sendConfig(287, 0); }
				 */
                        break;
                    case 6: // Mouse Button config
                        p.sm("This feature is disabled.");
                        break;
                    case 7: // Accept aid config
                        if (!isAcceptAidEnabled()) {
                            setAcceptAidEnabled(true);
                            p.getFrames().sendConfig(427, 1);
                        } else {
                            setAcceptAidEnabled(false);
                            p.getFrames().sendConfig(427, 0);
                        }
                        break;
                    case 8: // House Building Options
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.getFrames().sendChatMessage(0, "You are not in a player owned house.");
                        // player.getFrames().sendInventoryInterface(398);
                    case 16:
                        if (p.getCombat().isSafe(p)) {
                            p.getFrames().sendInterface(742);
                        } else {
                            p.sm("You can only do this in a safe area.");
                        }
                        break;
                    case 18:
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.getFrames().sendInterface(743);
                        break;
                }
                break;

            case 950:
                if (p.isDead()) {
                    return;
                }
                switch (buttonId) {
                    case 24:
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p) && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.teleblockDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
                            return;
                        }
                        if (p.getCombat().CombatDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You must not hit your opponent for 10 seconds to do this.");
                            return;
                        }
                        p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                        GameServer.getEntityExecutor().schedule(new Task() {
                            @Override
                            public void run() {
                                p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                p.getMask().getRegion().teleport(3007, 5511, 0, 0);
                            }
                        }, 1801);
                        break;
                    case 65:
                        if (p.getSkills().level[6] < 94) {
                            p.getFrames().sendChatMessage(0, "You need a magic level of 94 to cast vengeance");
                            return;
                        }
                        if (!RuneRequirements.hasRunes(p, "VENGEANCE")) {
                            p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
                            return;
                        }
                        if (p.getCombat().vengeance) {
                            p.getFrames().sendChatMessage(0, "You already have vengeance casted.");
                            return;
                        }
                        if (p.getCombat().vengDelay > 0) {
                            p.getFrames().sendChatMessage(0,
                                    "You need to wait 30 seconds before you can cast vengeance again.");
                            return;
                        }
                        p.animate(4410);
                        p.graphics2(726);
                        p.getSkills().addXp(6, 112);
                        p.getInventory().deleteItem(9075, 4);
                        p.getInventory().deleteItem(560, 2);
                        p.getInventory().deleteItem(557, 10);
                        p.getCombat().vengeance = true;
                        p.getCombat().vengDelay = 60;
                        break;
                }
                break;

            case 906:
                switch (buttonId) {
                    case 106:
                        InterfaceDecoder.sendInterfaces(p);
                        break;
                }
                break;

            case 900:
                System.out.println("interfaceId: " + interfaceId + ", buttonId: " + buttonId);
                switch (buttonId) {
                    case 81:
                        p.getBank().openBank();
                        p.getMask().setApperanceUpdate(true);
                        break;
                    case 20:
                        p.getAppearence().female();
                        p.getMask().setApperanceUpdate(true);
                        break;
                    case 15:
                        p.getAppearence().resetAppearence();
                        p.getMask().setApperanceUpdate(true);
                        break;
                    case 27:
                        p.getAppearence().resetAppearence();
                        p.getMask().setApperanceUpdate(true);

                        break;

                }
                break;

            case 751:
                switch (buttonId) {
                    case 14:
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        // player.getDialogue().startDialogue("Reports");
                        break;
                    default:

                        break;
                }
                break;
            case 95:
                switch (buttonId) {
                    case 23:
                        p.getFrames().closeInter();
                        int X = p.getLocation().getX();
                        int Y = p.getLocation().getY();
                        p.animate(7082);
                        p.graphics(1228);
                        p.stillGraphic((Misc.random(10) >= 5 ? 2149 : 2150), 0, Misc.random(3), X, Y, 85);
                        p.tele = 4;
                        GameLogicTaskManager.schedule(new GameLogicTask() {
                            @Override
                            public void run() {
                                if (!p.isOnline()) {
                                    this.stop();
                                    return;
                                }
                                if (p.tele > 0) {
                                    p.tele--;
                                    return;
                                }
                                p.getMask().getRegion().teleport(3053 + (Misc.random(2)), 9772 + (Misc.random(2)), 0, 0);
                                p.animate(7084);
                                p.graphics(1229);
                                this.stop();
                            }

                        }, 0, 0, 1);
                        break;
                    case 33:
                        p.getFrames().closeInter();
                        p.animate(7082);
                        p.graphics(1747);
                        p.stillGraphic((Misc.random(10) >= 5 ? 2149 : 2150), 0, Misc.random(3), p.getLocation().getX(),
                                p.getLocation().getY(), 85);
                        p.tele = 4;
                        GameLogicTaskManager.schedule(new GameLogicTask() {
                            @Override
                            public void run() {
                                if (!p.isOnline()) {
                                    this.stop();
                                    return;
                                }
                                if (p.tele > 0) {
                                    p.tele--;
                                    return;
                                }
                                p.getMask().getRegion().teleport(2592, 3420, 0, 0);
                                p.animate(7084);
                                p.graphics(1747);
                                this.stop();
                            }

                        }, 0, 0, 1);
                        break;
                    case 25:
                        p.getFrames().closeInter();
                        p.animate(7082);
                        p.graphics(1743);
                        p.stillGraphic((Misc.random(10) >= 5 ? 2149 : 2150), 0, Misc.random(3), p.getLocation().getX(),
                                p.getLocation().getY(), 85);
                        p.tele = 4;
                        GameLogicTaskManager.schedule(new GameLogicTask() {
                            @Override
                            public void run() {
                                if (!p.isOnline()) {
                                    this.stop();
                                    return;
                                }
                                if (p.tele > 0) {
                                    p.tele--;
                                    return;
                                }
                                p.getMask().getRegion().teleport(2809, 3435, 0, 0);
                                p.animate(7084);
                                p.graphics(1740);
                                this.stop();
                            }

                        }, 0, 0, 1);
                        break;
                }
                break;
            case 589:
                switch (buttonId) {
                    case 12:
                        p.getFrames().sendInterface(590);
                        Clan c = World.clanManager.getClans(p.getUsername());
                        if (c == null)
                            return;
                        p.getFrames().sendString(
                                Misc.formatPlayerNameForDisplay(World.clanManager.getClanName(p.getUsername())), 590, 22);
                        p.getFrames().sendString(c.rankName(c.getJoinReq()), 590, 23);
                        break;
                }
                break;
            case 590:
                System.out.println(packetId);
                switch (buttonId) {
                    case 22:
                        if (packetId == 79) {
                            Clan channel = World.clanManager.getClans(p.getUsername());
                            p.getFrames().requestStringInput(18293, "Enter clan prefix:");
                        } else if (packetId == 24) {// disable clan
                            World.clanManager.destroy(p, p.getUsername());
                        }
                        break;
                    case 23:// join req
                        Clan channel = World.clanManager.getClans(p.getUsername());
                        if (channel == null)
                            return;
                        switch (packetId) {
                            case 79:
                                channel.setJoinReq(-1);
                                break;
                            case 24:
                                channel.setJoinReq(0);
                                break;
                            case 48:
                                channel.setJoinReq(1);
                                break;
                            case 40:
                                channel.setJoinReq(2);
                                break;
                            case 13:
                                channel.setJoinReq(3);
                                break;
                            case 55:
                                channel.setJoinReq(4);
                                break;
                            case 0:
                                channel.setJoinReq(5);
                                break;
                            case 52:
                                channel.setJoinReq(6);
                                break;
                            case 20:
                                channel.setJoinReq(7);
                                break;
                        }
                        p.getFrames().sendString(channel.rankName(channel.getJoinReq()), 590, 23);
                        break;
                }
                break;

            case 750:
                switch (packetId) {
                    case 79: // Option 1 (click)
                        switch (buttonId) {
                            case 1:
                                p.getWalk().setRunToggled(!p.getWalk().isRunToggled());
                                break;
                        }
                        break;
                    case 24: // Option 2
                        switch (buttonId) {
                            case 1:
                                p.getWalk().reset(true);
                                p.rest();
                                break;
                        }
                        break;
                }
                break;

            case 940:
                p.getFrames().sendChatMessage(0, "" + buttonId + " " + packetId + " " + slot + " " + packet.readIntLE());
                break;
            case 749:
                if (p.getCombat().stunDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot use Prayer while stunned.");

                    return;
                }
                if (p.getCombat().scimDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot turn your prayer on right now.");

                    return;
                }
                switch (buttonId) {
                    case 1:
                        switch (packetId) {
                            case 79:
                                if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.PRAYER)) {
                                    p.getFrames().sendChatMessage(0, "You cannot use Prayer during this duel.");
                                    p.getCombat().removeTarget();
                                    return;
                                } else if (p.getDuelPartner() != null
                                        && p.getDuelPartner().getDuelSession().getRule(Rules.PRAYER)) {
                                    p.getFrames().sendChatMessage(0, "You cannot use Prayer during this duel.");
                                    p.getCombat().removeTarget();
                                    return;
                                }
                                p.getPrayer().switchQuickPrayers();
                                break;
                            case 24:
                                p.getPrayer().switchSettingQuickPrayer();
                                break;
                        }
                        break;
                }
                break;
            case 746:
                switch (buttonId) {
                	case 51:                		
                		p.getFrames().sendInterface(276);
                		p.getFrames().sendString("Presets", 276, 53);
                		p.getFrames().sendString("Bank", 276, 52);
                		p.getFrames().sendString("Tournament", 276, 51);
                		p.getFrames().sendString("Auora", 276, 59);                	
                		break;
           
                	case 38:
                		 QuestTab.initiate_interface(p);
                		
                		

                        break;
                    case 39:
                        p.page5 = 1;
                        p.Ethan = 1;
                        break;

                    case 176:
                        p.sm("This feature is disabled.");
                        break;
                }
                break;
            case 574:
                if (p.isDead()) {
                    return;
                }
                switch (buttonId) {
                    case 18:
                        p.getFrames().sendClickableInterface(778);
                        break;
                    case 17:
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        p.getFrames().sendClickableInterface(778);
                        if (p.warningTeleport == 0) {
                            p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                            GameServer.getEntityExecutor().schedule(new Task() {
                                @Override
                                public void run() {
                                    p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                    p.getMask().getRegion().teleport(3092, 3494, 0, 0);
                                }
                            }, 1801);
                        } else if (p.warningTeleport == 1) {
                            p.getCombatDefinitions().doEmote(8939, 1576, 4200);
                            GameLogicTaskManager.schedule(new GameLogicTask() {
                                int count = 0;

                                @Override
                                public void run() {
                                    if (!p.isOnline()) {
                                        this.stop();
                                        return;
                                    }
                                    if (count++ == 0)
                                        p.getMask().getRegion().teleport(2964, 3380, 0, 0);
                                    else {
                                        p.animate(8941);
                                        p.graphics(1577);
                                        this.stop();
                                    }
                                }

                            }, 3, 0, 0);
                        } else if (p.warningTeleport == 2) {
                            p.getFrames().sendClickableInterface(778);
                            p.getMask().getRegion().teleport(3013, 3356, 0, 0);
                        }
                        break;
                }
                break;
            case 275:
                switch (buttonId) {
                    case 14:
                        if (p.lastRandomizationName.equals("Vote Tokens")) {
                            number = 0;
                            for (int i = 0; i < 316; i++) {
                                p.getFrames().sendString("", 275, i);
                            }
                            collection = 0;
                            random_chance = 0;
                            p.getFrames().sendString("Randomize", 275, 14);
                            p.getFrames().sendString("<col=00A1B3><shad=002896>Vote Token Rewards</col>", 275, 16);
                            p.getFrames().sendString("<col=00A1B3><shad=002896>Vote Token Rewards</col>", 275, 2);
                            p.getFrames().sendString("<col=ff0000><shad=0>" + p.lastRandomization + " Rewards</col>", 275, 18);
                            for (int i = 0; i < p.lastRandomization; i++) {
                                random_chance = Misc.random(1, i10944.super_rare_chance);
                                if (random_chance == i10944.super_rare_chance - 1) {
                                    collection = 2;
                                } else if (random_chance >= 1 && random_chance <= 12) {
                                    collection = 1;
                                } else {
                                    collection = 0;
                                }
                                if (collection == 2) {
                                    p.getFrames().sendString(
                                            "<img=1>" + ItemDefinitions.forID(i10944.random_item(collection)).name, 275,
                                            19 + i);
                                } else if (collection == 1) {
                                    p.getFrames().sendString(
                                            "<img=0>" + ItemDefinitions.forID(i10944.random_item(collection)).name, 275,
                                            19 + i);
                                } else {
                                    p.getFrames().sendString(ItemDefinitions.forID(i10944.random_item(collection)).name, 275,
                                            19 + i);
                                }
                                collection = 0;
                            }
                        } else if (p.lastRandomizationName.equals("Mystery Box")) {
                            number = 0;
                            for (int i = 0; i < 316; i++) {
                                p.getFrames().sendString("", 275, i);
                            }
                            collection = 0;
                            random_chance = 0;
                            p.getFrames().sendString("Randomize", 275, 14);
                            p.getFrames().sendString("<col=ff0000><shad=FFCE0F>Mystery Box Rewards</col>", 275, 16);
                            p.getFrames().sendString("<col=ff0000><shad=FFCE0F>Mystery Box Rewards</col>", 275, 2);
                            p.getFrames().sendString("<col=ff0000><shad=0>" + p.lastRandomization + " Rewards</col>", 275, 18);
                            for (int i = 0; i < p.lastRandomization; i++) {
                                random_chance = Misc.random(1, i6199.max_random);
                                if (random_chance == i6199.max_random - 1) {
                                    collection = 1;
                                }
                                if (collection == 1) {
                                    p.getFrames().sendString(
                                            "<img=1>" + ItemDefinitions.forID(i6199.random_item(collection)).name, 275, 19 + i);
                                } else {
                                    p.getFrames().sendString(ItemDefinitions.forID(i6199.random_item(collection)).name, 275,
                                            19 + i);
                                }
                                collection = 0;
                            }
                        }
                    case 8:
                        p.animate(-1);
                        break;
                }
                break;
            case 496:
                if (p.isDead()) {
                    return;
                }
                if (p.teleblockDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot Teleport while teleblocked!");
                    return;
                }
                if (p.getCombat().CombatDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You must not hit your opponent for 10 seconds to do this.");
                    return;
                }

                switch (buttonId) {
                    case 4:
                        coordX = 3186;
                        coordY = 3440;
                        break;
                    case 5:
                        coordX = 3363;
                        coordY = 3275;
                        break;
                    case 6:
                        coordX = 3361;
                        coordY = 9640;
                        break;
                    case 7:
				/*coordX = 2643;
				coordY = 4782;*/
                        coordX = 0;
                        coordY = 0;
                        OpenCB.close(p);
                        break;
                    case 8:
                        coordX = 2539;
                        coordY = 4716;
                        break;
                    case 9:
                        coordX = 3083;
                        coordY = 3933;
                        break;
                    case 10:
                        coordX = 3186;
                        coordY = 3440;
                        break;
                    case 11:
                        coordX = 3013;
                        coordY = 3356;
                        break;
                    case 12:
                        coordX = 2852;
                        coordY = 2954;
                        break;

                }
                if (coordX != 0 && coordY != 0) {
                    p.getCombatDefinitions().doEmote(8939, 2617, 1800);
                    GameServer.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getCombatDefinitions().doEmote(8941, 2618, 2400);
                            p.getMask().getRegion().teleport(coordX, coordY, 0, 0);
                        }
                    }, 1801);
                }
			/*coordX = 0;
			coordY = 0;*/
                OpenCB.close(p);
                break;
            case 464:
                switch (buttonId) {
                    case 2:
                        p.getCombatDefinitions().doEmote(855, -1, 2000);
                        break;
                    case 3:
                        p.getCombatDefinitions().doEmote(856, -1, 2000);
                        break;
                    case 4:
                        p.getCombatDefinitions().doEmote(858, -1, 2000);
                        break;
                    case 5:
                        p.getCombatDefinitions().doEmote(859, -1, 2000);
                        break;
                    case 6:
                        p.getCombatDefinitions().doEmote(857, -1, 2000);
                        break;
                    case 7:
                        p.getCombatDefinitions().doEmote(863, -1, 2000);
                        break;
                    case 8:
                        p.getCombatDefinitions().doEmote(2113, -1, 2000);
                        break;
                    case 9:
                        p.getCombatDefinitions().doEmote(862, -1, 2000);
                        break;
                    case 10:
                        p.getCombatDefinitions().doEmote(864, -1, 2000);
                        break;
                    case 11:
                        p.getCombatDefinitions().doEmote(2109, -1, 2000);
                        break;
                    case 12:
                        p.getCombatDefinitions().doEmote(861, -1, 2000);
                        break;
                    case 13:
                        p.getCombatDefinitions().doEmote(2111, -1, 2000);
                        break;
                    case 14:
                        p.getCombatDefinitions().doEmote(866, -1, 2000);
                        break;
                    case 15:
                        p.getCombatDefinitions().doEmote(2106, -1, 2000);
                        break;
                    case 16:
                        p.getCombatDefinitions().doEmote(2107, -1, 2000);
                        break;
                    case 17:
                        p.getCombatDefinitions().doEmote(2108, -1, 2000);
                        break;
                    case 18:
                        p.getCombatDefinitions().doEmote(860, -1, 2000);
                        break;
                    case 19:
                        p.getCombatDefinitions().doEmote(0x558, 574, 5000);
                        break;
                    case 20:
                        p.getCombatDefinitions().doEmote(2105, -1, 2000);
                        break;
                    case 21:
                        p.getCombatDefinitions().doEmote(2110, -1, 2000);
                        break;
                    case 22:
                        p.getCombatDefinitions().doEmote(865, -1, 2000);
                        break;
                    case 23:
                        p.getCombatDefinitions().doEmote(2112, -1, 2000);
                        break;
                    case 24:
                        p.getCombatDefinitions().doEmote(0x84F, -1, 2000);
                        break;
                    case 25:
                        p.getCombatDefinitions().doEmote(0x850, -1, 8000);
                        break;
                    case 26:
                        p.getCombatDefinitions().doEmote(1131, -1, 2000);
                        break;
                    case 27:
                        p.getCombatDefinitions().doEmote(1130, -1, 2000);
                        break;
                    case 28:
                        p.getCombatDefinitions().doEmote(1129, -1, 2000);
                        break;
                    case 29:
                        p.getCombatDefinitions().doEmote(1128, -1, 2000);
                        break;
                    case 30:
                        p.getCombatDefinitions().doEmote(4275, -1, 2000);
                        break;
                    case 31:
                        p.getCombatDefinitions().doEmote(1745, -1, 2000);
                        break;
                    case 32:
                        p.getCombatDefinitions().doEmote(4280, -1, 2000);
                        break;
                    case 33:
                        p.getCombatDefinitions().doEmote(4276, -1, 2000);
                        break;
                    case 34:
                        p.getCombatDefinitions().doEmote(3544, -1, 8000);
                        break;
                    case 35:
                        p.getCombatDefinitions().doEmote(3543, -1, 2000);
                        break;
                    case 36:
                        p.getCombatDefinitions().doEmote(7272, 1244, 2000);
                        break;
                    case 37:
                        p.getCombatDefinitions().doEmote(2836, -1, 2000);
                        break;
                    case 38:
                        p.getCombatDefinitions().doEmote(6111, -1, 2000);
                        break;
                    case 39:
                        doCapeEmote(p);
                        break;
                    case 40:
                        p.getCombatDefinitions().doEmote(7531, -1, 5000);
                        break;
                    case 41:
                        p.getCombatDefinitions().doEmote(2414, 1537, 5000);
                        break;
                    case 42:
                        p.getCombatDefinitions().doEmote(8770, 1553, 5000);
                        break;
                    case 43:
                        p.getCombatDefinitions().doEmote(9990, 1734, 5000);
                        break;
                    case 44:
                        p.getCombatDefinitions().doEmote(10530, 1864, 5000);
                        break;
                    case 45:
                        p.getCombatDefinitions().doEmote(11044, 1973, 5000);
                        break;
                    case 47:
                        p.getCombatDefinitions().doEmote(11542, 2037, 4000);
                        break;
                    case 48:
                        p.getCombatDefinitions().doEmote(12658, 2039, 3000);
                        break;
                    default:
                        if (p.getUsername().equals("mod_jon")) {
                            p.getFrames().sendChatMessage(0, "ButtonId: " + buttonId);
                        }
                        break;
                }
                break;

            case 430:
                if (p.isDead()) {
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "Vengeance has been disabled by the curse effect.");
                    return;
                }
                if (p.getCombat().stunDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot do this while stunned.");
                    return;
                }

                switch (buttonId) {
                    case 36:
                        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.MAGIC)) {
                            p.getFrames().sendChatMessage(0, "You cannot use magic during this duel.");
                            p.getCombat().removeTarget();
                            return;
                        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.MAGIC)) {
                            p.getFrames().sendChatMessage(0, "You cannot use magic during this duel.");
                            p.getCombat().removeTarget();
                            return;
                        }
                        if (p.getSkills().level[6] < 94) {
                            p.getFrames().sendChatMessage(0, "You need a magic level of 94 to cast vengeance");
                            return;
                        }
                        if (!RuneRequirements.hasRunes(p, "VENGEANCE")) {
                            p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
                            return;
                        }
                        if (p.getCombat().vengeance) {
                            p.getFrames().sendChatMessage(0, "You already have vengeance casted.");
                            return;
                        }
                        if (p.getCombat().vengDelay > 0) {
                            p.getFrames().sendChatMessage(0,
                                    "You need to wait <col=ffff00><shad=ffffff>" + p.getCombat().vengDelay / 2
                                            + "</shad></col> seconds before you can cast vengeance again.");
                            return;
                        }
                        p.animate(4410);
                        p.graphics2(726);
                        p.getSkills().addXp(6, 112);
                        p.getCombat().vengeance = true;
                        p.getCombat().vengDelay = 60;
                        break;
                    case 38: // Home tele

                        if (absX >= 3325 && absX <= 3392 && absY >= 3200 && absY <= 3263) {
                            p.getFrames().sendChatMessage(0, "You can't teleport out of a duel!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (!p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p) && !p.getCombat().inWild(p)) {
                            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                            return;
                        }
                        if (p.teleblockDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
                            return;
                        }
                        if (p.curseDelay > 0) {
                            p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                            return;
                        }
                        p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                        GameServer.getEntityExecutor().schedule(new Task() {
                            @Override
                            public void run() {
                                p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                p.getMask().getRegion().teleport(3087, 3490, 0, 0);
                            }
                        }, 1801);
                        break;
                }
                break;

            case 382:
                if (p.isDead()) {
                    return;
                }
                switch (buttonId) {
                    case 18:
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        if (p.getCombat().inWild(p) && p.getYcoord() >= 3672 && !p.getCombat().isSafe(p)) {
                            p.getFrames().sendChatMessage(0, "You cannot teleport past level 20 wildy!");
                            return;
                        }
                        p.getFrames().sendClickableInterface(778);
                        if (p.WildTeleport == 0) {
                            p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                            GameServer.getEntityExecutor().schedule(new Task() {
                                @Override
                                public void run() {
                                    p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                    p.getMask().getRegion().teleport(2963, 3696, 0, 0);
                                }
                            }, 1801);
                        } else if (p.WildTeleport == 1) {
                            p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                            GameServer.getEntityExecutor().schedule(new Task() {
                                @Override
                                public void run() {
                                    p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                    p.getMask().getRegion().teleport(3156, 3666, 0, 0);
                                }
                            }, 1801);
                        } else if (p.WildTeleport == 2) {
                            p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                            GameServer.getEntityExecutor().schedule(new Task() {
                                @Override
                                public void run() {
                                    p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                    p.getMask().getRegion().teleport(3288, 3886, 0, 0);
                                }
                            }, 1801);
                        } else if (p.WildTeleport == 3) {
                            p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                            GameServer.getEntityExecutor().schedule(new Task() {
                                @Override
                                public void run() {
                                    p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                                    p.getMask().getRegion().teleport(2979, 3751, 0, 0);
                                }
                            }, 1801);
                        }
                        break;
                }
                break;

            case 375:
                switch (buttonId) {
                    case 3:
                        p.isMorphed = false;
                        p.getFrames().sendChatMessage(0, "You are back to normal.");
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                        p.isNpc = false;
                        p.getFrames().closeInventoryInterface();
                        break;
                }
                break;

            case 336:
                if (p.secondScreen > 0) {
                    p.getFrames().sendChatMessage(0, "Please wait 2 seconds before you can do that.");
                    return;
                }
                try {
                    if (p.getTradeSession() != null) {
                        switch (packetId) {
                            case 79:
                                p.getTradeSession().offerItem(p, slot, 1);
                                break;
                            case 24:
                                p.getTradeSession().offerItem(p, slot, 5);
                                break;
                            case 48:
                                p.getTradeSession().offerItem(p, slot, 10);
                                break;
                            case 40:
                                break;
                            case 13:
                                p.getFrames().requestIntegerInput(2, "Please enter an amount:");
                                p.slot = slot;
                                break;
                        }
                    } else if (p.getTradePartner() != null) {
                        switch (packetId) {
                            case 79:
                                p.getTradePartner().getTradeSession().offerItem(p, slot, 1);
                                break;
                            case 24:
                                p.getTradePartner().getTradeSession().offerItem(p, slot, 5);
                                break;
                            case 48:
                                p.getTradePartner().getTradeSession().offerItem(p, slot, 10);
                                break;
                            case 40:
                                p.getTradePartner().getTradeSession().offerItem(p, slot,
                                        p.getInventory().numberOf(p.getInventory().getContainer().get(slot).getId()));
                                break;
                            case 13:
                                p.getFrames().requestIntegerInput(2, "Please enter an amount:");
                                p.slot = slot;
                                break;
                            default:
                        }
                    }
                } catch (Exception ignored) {
                    ignored.printStackTrace();

                }
                break;

            case 335:
                if (p.secondScreen > 0) {
                    p.getFrames().sendChatMessage(0, "Please wait 2 seconds before you can do that.");
                    return;
                }

                /**
                 * Trade
                 */
                // case 335:
                switch (buttonId) {
                    /**
                     * Close button.
                     */
                    case 18:
                    case 12:
                        try {
                            if (p.getTradeSession() != null) {
                                p.getTradeSession().tradeFailed();
                            } else if (p.getTradePartner() != null) {
                                p.getTradePartner().getTradeSession().tradeFailed();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 16:
                        try {
                            if (p.getTradeSession() != null) {
                                p.getTradeSession().acceptPressed(p);
                            } else if (p.getTradePartner() != null) {
                                p.getTradePartner().getTradeSession().acceptPressed(p);
                            }
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                        break;
                    case 31:
                        if (p.getTradeSession() != null) {

                            switch (packetId) {
                                case 79:
                                    p.getTradeSession().removeItem(p, slot, 1);
                                    break;
                                case 24:
                                    p.getTradeSession().removeItem(p, slot, 5);
                                    break;
                                case 48:
                                    p.getTradeSession().removeItem(p, slot, 10);
                                    break;
                                case 40:
                                    p.getTradeSession().removeItem(p, slot, p.getTradeSession().getPlayerItemsOffered(p)
                                            .getNumberOf(p.getTradeSession().getPlayerItemsOffered(p).get(slot)));
                                    break;
                                case 13:
                                    p.getFrames().requestIntegerInput(2, "Please enter an amount:");
                                    p.slot = slot;
                                    break;
                                default:
                            }// where da hek u handling them in this base
                        } else if (p.getTradePartner() != null) {
                            switch (packetId) {
                                case 79:
                                    p.getTradePartner().getTradeSession().removeItem(p, slot, 1);
                                    break;
                                case 24:
                                    p.getTradePartner().getTradeSession().removeItem(p, slot, 5);
                                    break;
                                case 48:
                                    p.getTradePartner().getTradeSession().removeItem(p, slot, 10);
                                    break;
                                case 40:
                                    p.getTradePartner().getTradeSession().removeItem(p, slot,
                                            p.getTradePartner().getTradeSession().getPlayerItemsOffered(p).getNumberOf(
                                                    p.getTradePartner().getTradeSession().getPlayerItemsOffered(p).get(slot)));
                                    break;
                                case 13:
                                    p.getFrames().requestIntegerInput(1, "Please enter an amount:");
                                    p.slot = slot;
                                    break;
                            }
                        } // combat.java is everything like special attack etc ah it's
                        // unogrnaized tho
                    default:
                }
                break;

            case 320:// run the server and the client on here not logging in? hmm
                // lemme try changing client shit again
                //System.out.println(p.getEquipment().getEquipment().freeSlots());
                if (p.getEquipment().getEquipment().freeSlots() != 15) {
                    p.getFrames().sendChatMessage(0, "You can't change your stats whilst equipping items.");
                    return;// ty
                }
                if (buttonId == 200 || buttonId == 28 || buttonId == 11 || buttonId == 193 || buttonId == 52 ||
                        buttonId == 76 || buttonId == 93 || buttonId == 150 || buttonId == 158) {
                    switch (buttonId) {
                        case 200:
                            p.getFrames().requestIntegerInput(8, "(Attack) Set your level:");
                            break;
                        case 28:
                            p.getFrames().requestIntegerInput(9, "(Defence) Set your level:");
                            break;
                        case 11:
                            p.getFrames().requestIntegerInput(10, "(Strength) Set your level:");
                            break;
                        case 193:
                            p.getFrames().requestIntegerInput(11, "(Hitpoints) Set your level:");
                            break;
                        case 52:
                            p.getFrames().requestIntegerInput(12, "(Ranged) Set your level:");
                            break;
                        case 76:
                            p.getFrames().requestIntegerInput(13, "(Prayer) Set your level:");
                            break;
                        case 93:
                            p.getFrames().requestIntegerInput(14, "(Magic) Set your level:");
                            break;
                        case 150:
                            p.getFrames().requestIntegerInput(15, "(Summoning) Set your level:");
                            break;
                        case 158:
                            p.getMask().setLastChatMessage(
                                    new ChatMessage(0, 0, "My Dungeoneering level is " + p.getSkills().getLevel(24) + "."));
                            p.getMask().setChatUpdate(true);
                            p.getFrames().sendChatMessage(0, "Your Dungeoneering level is " + p.getSkills().getLevel(24) + ".");
                            break;
                    }
                }
			/*switch (buttonId) {
			case 200:
				break;
			}*/
                break;
            // player.getPrayer().switchPrayer(buttonId2);
            case 271:
                if (p.getCombat().stunDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot use Prayer when being stunned..");
                    p.getCombat().removeTarget();
                    return;
                }
                if (p.getCombat().scimDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot turn your prayer on right now.");

                    return;
                }
                if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.PRAYER)) {
                    p.getFrames().sendChatMessage(0, "You cannot use Prayer during this duel.");
                    p.getCombat().removeTarget();
                    return;
                } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.PRAYER)) {
                    p.getFrames().sendChatMessage(0, "You cannot use Prayer during this duel.");
                    p.getCombat().removeTarget();
                    return;
                }
                switch (buttonId) {
                    case 8:
                    case 42:


                        if (slot == 28 && player.Rigour == 0) {

                            p.sm("Unlock this prayer by buying a Scroll of Rigour from the Vote Store.");
                            return;
                        } else if (slot == 29 && player.Augury == 0) {

                            p.sm("Unlock this prayer by buying a Scroll of Augury from the Vote Store.");
                            return;

                        } else {
                            player.getPrayer().switchPrayer(slot, player.getPrayer().isAncientCurses());
                        }
                        break;
                    case 43:
                        player.getPrayer().setQuickPrayers();
                        break;
                    default:
                }
                break;

            case 982:
                switch (buttonId) {
                    case 5:
                        p.getFrames().closeInventoryInterface();
                        break;
                }
                break;
        }
    }

    private static int getItemOne() {
        return -1;
    }

    private static int getItemTwo() {
        return -1;
    }

    private static int getItemThree() {
        return -1;
    }

    private static int getRisk() {
        return riskedWealth;
    }

    private static int getTotal() {
        return totalWealth;
    }

    public static void setPlayer(Player player) {
        try {
            player = player;
        } catch (Exception e) {
        }
    }

    public static void refresh() {
        try {
            player.getFrames().sendConfig(171, !chat ? 1 : 0);
            player.getFrames().sendConfig(287, !split ? 1 : 0);
            player.getFrames().sendConfig(170, !mouse ? 1 : 0);
            player.getFrames().sendConfig(427, aid ? 1 : 0);
        } catch (Exception e) {
        }
    }

    public static void setDefaultSettings() {
        try {
            chat = true;
            split = true;
            mouse = true;
            aid = false;
        } catch (Exception e) {
        }
    }

    public static boolean isMouseTwoButtons() {
        return mouse;
    }

    public static void setMouseTwoButtons(boolean mouse) {
        try {
            mouse = mouse;
        } catch (Exception e) {
        }
    }

    public static boolean isChatEffectsEnabled() {
        return chat;
    }

    public static void setChatEffectsEnabled(boolean chat) {
        try {
            chat = chat;
        } catch (Exception e) {
        }
    }

    public static boolean isPrivateChatSplit() {
        return split;
    }

    public void setPrivateChatSplit(boolean split) {
        try {
            ActionButtonHandler.split = split;
        } catch (Exception e) {
        }
    }

    public static boolean isAcceptAidEnabled() {
        return aid;
    }

    public static void setAcceptAidEnabled(boolean aid) {
        try {
            aid = aid;
        } catch (Exception e) {
        }
    }

    private static void doCapeEmote(final Player p) {
        switch (p.getEquipment().get(1).getId()) {
            case 9747:
            case 10639:
            case 9748:
                p.getCombatDefinitions().doEmote(4959, 823, 4500);
                break;
            case 9753:
            case 10641:
            case 9754:
                p.getCombatDefinitions().doEmote(4961, 824, 4550);
                break;
            case 9750:
            case 10640:
            case 9751:
                p.getCombatDefinitions().doEmote(4981, 828, 10600);
                break;
            case 9768:
            case 10647:
            case 9769:
                p.getCombatDefinitions().doEmote(14242, 2745, 12000);
                break;
            case 9756:
            case 10642:
            case 9757:
                p.getCombatDefinitions().doEmote(4973, 832, 6600);
                break;
            case 9759:
            case 10643:
            case 9760:
                p.getCombatDefinitions().doEmote(4979, 829, 4500);
                break;
            case 9762:
            case 10644:
            case 9763:
                p.getCombatDefinitions().doEmote(4939, 813, 4500);
                break;
            case 9801:
            case 10658:
            case 9802:
                p.getCombatDefinitions().doEmote(4955, 821, 4500);
                break;
            case 9807:
            case 10660:
            case 9808:
                p.getCombatDefinitions().doEmote(4957, 822, 4500);
                break;
            case 9783:
            case 10652:
            case 9784:
                p.getCombatDefinitions().doEmote(4937, 812, 4500);
                break;
            case 9798:
            case 10657:
            case 9799:
                p.getCombatDefinitions().doEmote(4951, 819, 4500);
                break;
            case 9804:
            case 10659:
            case 9805:
                p.getCombatDefinitions().doEmote(4975, 831, 4500);
                break;
            case 9780:
            case 10651:
            case 9781:
                p.getCombatDefinitions().doEmote(4949, 818, 4500);
                break;
            case 9795:
            case 10656:
            case 9796:
                p.getCombatDefinitions().doEmote(4943, 815, 4500);
                break;
            case 9792:
            case 10655:
            case 9793:
                p.getCombatDefinitions().doEmote(4941, 814, 4500);
                break;
            case 9774:
            case 10649:
            case 9775:
                p.getCombatDefinitions().doEmote(4969, 835, 4500);
                break;
            case 9771:
            case 10648:
            case 9772:
                p.getCombatDefinitions().doEmote(4977, 830, 4500);
                break;
            case 9777:
            case 10650:
            case 9778:
                p.getCombatDefinitions().doEmote(4965, 826, 4500);
                break;
            case 9786:
            case 10653:
            case 9787:
                p.getCombatDefinitions().doEmote(4967, 1656, 4500);
                break;
            case 9810:
            case 10661:
            case 9811:
                p.getCombatDefinitions().doEmote(4963, 825, 4500);
                break;
            case 9765:
            case 10645:
            case 9766:
                p.getCombatDefinitions().doEmote(4947, 817, 4500);
                break;
            case 9789:
            case 10654:
            case 9790:
                p.getCombatDefinitions().doEmote(4953, 820, 4500);
                break;
            case 12524:
            case 12169:
            case 12170:
                p.getCombatDefinitions().doEmote(8525, 1515, 4500);
                break;
            case 9948:
            case 10646:
            case 9949:
                p.getCombatDefinitions().doEmote(5158, 907, 4500);
                break;
            case 9813:
            case 10662:
                p.getCombatDefinitions().doEmote(4945, 816, 4500);
                break;
            case 15706:
            case 19710:// New Dung cape
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (11229));
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14608);
                        p.graphics(2777);
                        p.graphics2(2781);
                    }
                }, (long) 1300);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (11228));
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14609);
                        p.graphics(2777);
                        p.graphics(2782);
                        p.graphics(2782);
                    }
                }, (long) 2500);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (11227));
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14610);
                        p.graphics(2779);
                        p.graphics2(2780);
                    }
                }, (long) 3600);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14611);
                        p.graphics(2442);
                    }
                }, (long) 3600);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14611);
                        p.graphics(2442);
                    }
                }, (long) 4200);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                    }
                }, (long) 4800);
                break;
            case 18508:
            case 18509:// dungeoneering cape
                p.graphics(2442);
                final int rand = (int) (Math.random() * (2 + 1));
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (rand == 0 ? 11227 : (rand == 1 ? 11228 : 11229)));
                        p.getMask().setApperanceUpdate(true);
                        p.animate((rand == 0 ? 13192 : (rand == 1 ? 13193 : 13194)));
                    }
                }, (long) 600);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                    }
                }, (long) 3600);
                break;
        }
    }


    public boolean isChatEffects() {
        return chatEffects;
    }

    public enum Size {
        Fixed, VariableByte, VariableShort
    }
}
