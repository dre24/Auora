package Auora.net.packethandlers;

import Auora.GameServer;
import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.events.Task;
import Auora.model.Teleporter;
import Auora.model.World;
import Auora.model.minigames.FightPits;
import Auora.model.npc.Npc;
import Auora.model.player.Player;
import Auora.model.route.RouteFinder;
import Auora.model.route.strategy.EntityStrategy;
import Auora.rsobjects.RSObjectsRegion;
import Auora.util.Misc;
import Auora.util.RSObject;
import Auora.util.RSTile;

import java.io.IOException;

/**
 * @developer Jonathan spawnscape
 */
public class ObjectPacketHandler {

    public static void manageOption1(final Player p, int objectId, RSTile location, int coordX, int coordY, int height)
            throws IOException {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();

        if (p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0,
                    "ObjectId: " + objectId + ", X: " + coordX + ", Y: " + coordY + ", Height: " + height + "");
        }
        if (p.getCombat().stunDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are stunned");
            return;
        }
        if (p.getCombat().freezeDelay > 0 && p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0, "lol1");
            return;
        }
        if (p.getCombat().freezeDelay > 0) {
            p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                    + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
            return;
        }
        p.getWalk().reset(true);
        p.turnTemporarilyTo(location);
        p.getMask().setApperanceUpdate(true);
        switch (objectId) {
            case 1:
                //p.getWalk().addStepToWalkingQueue(3268, 3692);
                p.getFrames().sendInterface(583);
                p.getFrames().sendString("<col=AA44AA><shad=0>Junior Cadet", 583, 51);
                p.getFrames().sendString("<col=AA44AA><shad=0>Serjeant", 583, 52);
                p.getFrames().sendString("<col=AA44AA><shad=0>Commander", 583, 53);
                p.getFrames().sendString("<col=AA44AA><shad=0>War-Chief", 583, 54);
                p.getFrames().sendString("<col=0><shad=ffeb0f>1337", 583, 55);
                p.getFrames().sendString("<col=38d8bb><shad=ffeb0f>Nolife Nerd", 583, 56);
                p.getFrames().sendString("<col=394aba><shad=ffeb0f>Killstreak God", 583, 57);
                p.getFrames().sendString("<col=394aba><shad=0>PKP", 583, 58);
                p.getFrames().sendString("<shad=cc0ff><col=9900CC>Dicer", 583, 59);
                p.getFrames().sendString("<shad=cc0ff><col=9900CC>RICH KID", 583, 60);
                p.getFrames().sendString("-", 583, 61);
                p.getFrames().sendString("-", 583, 62);
                p.getFrames().sendString("-", 583, 63);
                p.getFrames().sendString("-", 583, 64);
                p.getFrames().sendString("-", 583, 65);
                p.getFrames().sendString("-", 583, 66);
                p.getFrames().sendString("-", 583, 67);
                p.getFrames().sendString("-", 583, 68);
                p.getFrames().sendString("-", 583, 69);
                p.getFrames().sendString("-", 583, 70);
                p.getFrames().sendString("-", 583, 71);
                p.getFrames().sendString("-", 583, 72);
                p.getFrames().sendString("-", 583, 73);
                p.getFrames().sendString("-", 583, 74);
                p.getFrames().sendString("-", 583, 75);
                p.getFrames().sendString("-", 583, 76);
                p.getFrames().sendString("-", 583, 77);
                p.getFrames().sendString("-", 583, 78);
                p.getFrames().sendString("-", 583, 79);
                p.getFrames().sendString("-", 583, 80);
                p.getFrames().sendString("-", 583, 81);
                p.getFrames().sendString("-", 583, 82);
                p.getFrames().sendString("-", 583, 83);
                p.getFrames().sendString("Titles", 583, 50);


                break;

            case 4721:
            	int x = 3088;
            	int y = 3503;
            	int loopcrate = 0;
            	while (loopcrate < 100) {
            	p.highlightCrate(184, 0, 0, x, y, 6);	
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	loopcrate++;
            	}
            	
            	break;
            case 38698:
                //p.getWalk().addStepToWalkingQueue(3268, 3692);
                p.getMask().getRegion().teleport(2815, 5511, 0, 0);
                break;
            case 38699:
                //p.getWalk().addStepToWalkingQueue(3274, 3692);
                p.getMask().getRegion().teleport(3007, 5511, 0, 0);
                break;


            case 10251:
                RSObjectsRegion.removeObject(new RSObject(10251, 3182, 3433, 0, 10, 2));
                break;


            case 3485:
            /*p.sm("<col=A10000>[BeggarBonus] <img=2><col=ff0000>"+Misc.formatPlayerNameForDisplay(p.getUsername())+"</col>"
					+ "<col=0079C9> has activated BeggarBonus for 30 minutes!");
			*/

                p.animate(1895);
                GameLogicTaskManager.schedule(new GameLogicTask() {
                    @Override
                    public void run() {
                        p.getDialogue().startDialogue("WellObject");
                        Npc npc = World.getNpcs().get(1);
                        p.getWalk().walkTo(new EntityStrategy(npc), true);
                        RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(),
                                p.getLocation().getZ(), 1, new EntityStrategy(npc), false);
                        this.stop();
                    }
                }, 3, 0);
                break;

            case 9368:
            case 9369:
                FightPits pits = new FightPits();
                pits.pitsObject(p, objectId);
                break;

            case 13623:
            	p.getDialogue().startDialogue("Auoratele");
                break;
            case 733:


                int cut = Misc.random(3);
                p.animate(451);
                if (cut == 1) {
                    p.sm("u cut the webs successfully");
                    if (p.getLocation().getX() == 3092 && p.getLocation().getY() == 3957) {
                        RSObjectsRegion.putObject(new RSObject(734, 3092, 3957, 0, 10, 2), 60000);
                        RSObjectsRegion.removeObject(new RSObject(733, 3092, 3957, 0, 10, 2));


                    }
                }

                break;


            case 2557:
                // 1523
                p.turnTemporarilyTo(location);
                if (!p.getInventory().contains(1523) || (p.getSkills().getCombatLevel() < 138)) {
                    p.sm("You need to be cb level 138 and have a lockpick to enter this hut");
                } else {
                    if (p.getSkills().getLevel(17) >= 1 && p.getSkills().getLevel(17) <= 49) {
                        int enter = Misc.random(8);
                        p.animate(2246);
                        if (enter == 2) {
                            if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3957) {
                                p.getMask().getRegion().teleport(3190, 3958, 0, 0);
                            } else if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3958) {
                                p.getMask().getRegion().teleport(3190, 3957, 0, 0);
                            }

                        } else {
                            p.hit(75);
                        }
                    } else if (p.getSkills().getLevel(17) >= 50 && p.getSkills().getLevel(17) <= 84) {
                        int enter = Misc.random(6);
                        p.animate(2246);
                        if (enter == 2) {
                            if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3957) {
                                p.getMask().getRegion().teleport(3190, 3958, 0, 0);
                            } else if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3958) {
                                p.getMask().getRegion().teleport(3190, 3957, 0, 0);
                            }

                        } else {
                            p.hit(50);
                        }
                    } else if (p.getSkills().getLevel(17) >= 85 && p.getSkills().getLevel(17) <= 98) {
                        int enter = Misc.random(3);
                        p.animate(2246);
                        if (enter == 2) {
                            if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3957) {
                                p.getMask().getRegion().teleport(3190, 3958, 0, 0);
                            } else if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3958) {
                                p.getMask().getRegion().teleport(3190, 3957, 0, 0);
                            }

                        } else {

                            p.hit(25);
                        }

                    } else if (p.getSkills().getLevel(17) == 99) {
                        int enter = Misc.random(2);
                        p.animate(2246);
                        if (enter == 2) {
                            if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3957) {
                                p.getMask().getRegion().teleport(3190, 3958, 0, 0);
                            } else if (p.getLocation().getX() == 3190 && p.getLocation().getY() == 3958) {
                                p.getMask().getRegion().teleport(3190, 3957, 0, 0);
                            }

                        } else {
                            p.hit(25);
                        }
                    }


                }


                break;


            case 23271:
                if (p.getWalk().getWalkDir() != -1 || p.getWalk().getRunDir() != -1)
                    return;
                if (p.getLocation().getY() == 3520) {
                    p.animate(6132);
                    GameServer.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {

                            p.getMask().getRegion().teleport(p.getLocation().getX(), 3523, 0, 0);
                            // player.getFrames().walkTo(player.getXcoord(),
                            // player.getYcoord()+3,
                            // 6132, 0);
                        }
                    }, 900);
                } else if (p.getLocation().getY() == 3523) {
                    p.animate(6132);
                    GameServer.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getMask().getRegion().teleport(p.getLocation().getX(), 3520, 0, 0);
                            // player.getFrames().walkTo(player.getXcoord(),
                            // player.getYcoord()-3,
                            // 6132, 0);
                        }
                    }, 900);
                }
                break;
            case 11402:
                p.getBank().openBank();
                break;
            case 49018:
            case 2213:
                p.getBank().openBank();
                break;


            case 2295:
                p.animate(1660);
                p.getSkills().addXp(16, 5000);
                p.getMask().getRegion().teleport(2474, 3429, 0, 0);
                break;
            case 11758:
                p.getBank().openBank();
                p.getInventory().refresh();
                break;
            case 11739:
                p.animate(828);
                p.getMask().getRegion().teleport(3156, 3486, 2, 0);
                break;

            case 13629:
                if (p.getCombat().CombatDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You must wait 5 seconds to go home.");
                    return;
                }
                p.getMask().getRegion().teleport(3087, 3490, 0, 0);
                break;

            case 13631:
                if (p.teleblockDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are currently teleport blocked and cannot teleport for another "
                            + p.teleblockDelay / 2 + " seconds.");
                    return;
                }
                p.warningTeleport = 2;
                p.getFrames().sendInterface(574);
                p.getFrames().sendString("Teleport to Falador (Unsafe PvP)", 574, 17);
                p.getFrames().sendString("Stay here", 574, 18);
                break;

            case 1530:
                if (absX == 2816 && absY == 2438) {
                    p.getMask().getRegion().teleport(2816, 2439, 0, 0);
                    return;
                }
                if (absX == 2816 && absY == 2439) {
                    p.getMask().getRegion().teleport(2816, 2438, 0, 0);
                    return;
                }
                break;

            case 14829: //done
                //p.preTeleport(p, object);
                p.sm("You activate the obelisk and hear a faint rumbling sound.");
                p.playersInOb.add(p);
                p.preTeleport(p, 14829);

                GameServer.getEntityExecutor().schedule(new Task() {

                    @Override
                    public void run() {

                        RSObjectsRegion.putObject(new RSObject(14825, 3154, 3618, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3158, 3618, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3158, 3622, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3154, 3622, 0, 10, 2), 6500);


                    }
                }, 0);


                break;
            case 14826: //done
                //p.preTeleport(p, object);
                p.sm("You activate the obelisk and hear a faint rumbling sound.");
                p.playersInOb.add(p);
                p.preTeleport(p, 14826);
                GameServer.getEntityExecutor().schedule(new Task() {

                    @Override
                    public void run() {

                        RSObjectsRegion.putObject(new RSObject(14825, 2982, 3864, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 2982, 3868, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 2978, 3868, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 2978, 3864, 0, 10, 2), 6500);

                        this.stop();


                    }
                }, 0);


                break;
            case 14828: //done
                //p.preTeleport(p, object);
                p.sm("You activate the obelisk and hear a faint rumbling sound.");
                p.playersInOb.add(p);
                p.preTeleport(p, 14828);

                GameServer.getEntityExecutor().schedule(new Task() {

                    @Override
                    public void run() {

                        RSObjectsRegion.putObject(new RSObject(14825, 3108, 3796, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3108, 3792, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3104, 3792, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3104, 3796, 0, 10, 2), 6500);


                    }
                }, 0);


                break;
            case 14831: //done
                //p.preTeleport(p, object);
                p.sm("You activate the obelisk and hear a faint rumbling sound.");
                p.playersInOb.add(p);
                p.preTeleport(p, 14831);

                GameServer.getEntityExecutor().schedule(new Task() {

                    @Override
                    public void run() {

                        RSObjectsRegion.putObject(new RSObject(14825, 3309, 3914, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3305, 3914, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3305, 3918, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3309, 3918, 0, 10, 2), 6500);


                    }
                }, 0);


                break;
            case 14827: //done //part 2 of the update
                //p.preTeleport(p, object);
                p.sm("You activate the obelisk and hear a faint rumbling sound.");
                p.playersInOb.add(p);
                p.preTeleport(p, 14827);

                GameServer.getEntityExecutor().schedule(new Task() {

                    @Override
                    public void run() {

                        RSObjectsRegion.putObject(new RSObject(14825, 3037, 3730, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3033, 3730, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3033, 3734, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3037, 3734, 0, 10, 2), 6500);


                    }
                }, 0);


                break;
            case 14830: //done
                //p.preTeleport(p, object);
                p.sm("You activate the obelisk and hear a faint rumbling sound.");
                p.playersInOb.add(p);
                p.preTeleport(p, 14830);

                GameServer.getEntityExecutor().schedule(new Task() {

                    @Override
                    public void run() {

                        RSObjectsRegion.putObject(new RSObject(14825, 3221, 3658, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3221, 3654, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3217, 3654, 0, 10, 2), 6500);
                        RSObjectsRegion.putObject(new RSObject(14825, 3217, 3658, 0, 10, 2), 6500);


                    }
                }, 0);


                break;


            case 20377:
                p.animate(645);
                int increase = 7 + (p.getSkills().getLevelForXp(5) / 4);
                p.getSkills().RestorePray(increase);
                p.getFrames().sendChatMessage(0, "<col=00A1B3><shad=002896><img=3>Your prayer has been refilled!");
                break;
            case 16577:

                break;

            case 20348:
                int randomMessage = Misc.random(400);
                int botstopMessage = Misc.random(2);
                if (p.getCombat().thievDelay > 0) {
                    return;
                }
                if (p.botStop) {
                    if (botstopMessage == 0) {
                        p.getFrames().requestStringInput(11, "Random Message: Are you botting?");
                        return;
                    } else if (botstopMessage == 2) {
                        p.getFrames().requestStringInput(13, "Random Message: Type the word 'thieving'.");
                        return;
                    }
                    return;
                }
                if (randomMessage == 0) {
                    p.botStop = true;
                    p.getFrames().requestStringInput(11, "Random Message: Are you botting?");
                    return;
                } else if (randomMessage == 20) {
                    p.botStop = true;
                    p.getFrames().requestStringInput(13, "Random Message: Type the word 'thieving'.");
                    return;
                }
                if (p.getSkills().getLevel(17) >= 1 && p.getSkills().getLevel(17) <= 39) {
                    int amount = Misc.random(2500, 10000);
                    p.getFrames().sendChatMessage(0, "You steal " + amount + " coins from the stall.");
                    p.getSkills().addXp(17, 5000);
                    p.getInventory().addItem(995, amount);
                    p.skillingPoints += 1;
                } else if (p.getSkills().getLevel(17) >= 40 && p.getSkills().getLevel(17) <= 59) {
                    int amount = Misc.random(5000, 20000);
                    p.getFrames().sendChatMessage(0, "You steal " + amount + " coins from the stall.");
                    p.getSkills().addXp(17, 10000);
                    p.getInventory().addItem(995, amount);
                    p.skillingPoints += 1;
                } else if (p.getSkills().getLevel(17) >= 60 && p.getSkills().getLevel(17) <= 79) {
                    int amount = Misc.random(20000, 40000);
                    p.getFrames().sendChatMessage(0, "You steal " + amount + " coins from the stall.");
                    p.getSkills().addXp(17, 15000);
                    p.getInventory().addItem(995, amount);
                    p.skillingPoints += 1;
                } else if (p.getSkills().getLevel(17) >= 80 && p.getSkills().getLevel(17) <= 98) {
                    int amount = Misc.random(27500, 50000);
                    p.getFrames().sendChatMessage(0, "You steal " + amount + " coins from the stall.");
                    p.getSkills().addXp(17, 20000);
                    p.getInventory().addItem(995, amount);
                    p.skillingPoints += 1;
                } else if (p.getSkills().getLevel(17) == 99) {
                    int amount = Misc.random(35000, 100000);
                    p.getFrames().sendChatMessage(0, "You steal " + amount + " coins from the stall.");
                    p.getSkills().addXp(17, 35000);
                    p.getInventory().addItem(995, amount);
                    p.skillingPoints += 2;
                }
                p.getCombat().thievDelay = 6;
                p.animate(881);
                break;


            case 26972:
                p.getBank().openBank();
                p.getInventory().refresh();
                break;

            case 4415:
                if (p.getLocation().getLocalX() == 2416 && p.getLocation().getLocalY() == 3075)
                    p.getMask().getRegion().teleport(2417, 3078, 0, 0);

                if (p.getLocation().getLocalX() == 2417 && p.getLocation().getLocalY() == 3078)
                    p.getMask().getRegion().teleport(2416, 3075, 0, 0);

                break;

            case 4423:
                p.getWalk().addStepToWalkingQueue(2426, 3089);
                break;

            case 4424:
                p.getWalk().addStepToWalkingQueue(2426, 3089);
                break;

		/*
		 * case 38698: if(p.teleblockDelay > 0) {
		 * p.getFrames().sendChatMessage(0,
		 * "You are currently teleport blocked and cannot teleport for another "
		 * +p.teleblockDelay / 2 +" seconds."); return; }
		 * p.getMask().getRegion().teleport(2815, 5511,0, 0);
		 * p.getFrames().sendChatMessage(0,
		 * "<col=FFF0000>Welcome to Safe PvP, your items are safe here.");
		 * break; case 38699: if(p.teleblockDelay > 0) {
		 * p.getFrames().sendChatMessage(0,
		 * "You are currently teleport blocked and cannot teleport for another "
		 * +p.teleblockDelay / 2 +" seconds."); return; }
		 * p.getMask().getRegion().teleport(3007, 5511,0, 0);
		 * p.getFrames().sendChatMessage(0,
		 * "<col=FFF0000>Welcome to the Red Portal!"); break;
		 */
            case 38700:
                p.getMask().getRegion().teleport(3272, 3686, 0, 0);
                break;

            case 12719:
            case 12717:
            case 12716:
            case 12718:
            case 12720:
                p.getFrames().sendChatMessage(0, "<col=FF0000>This is where the last guy that broke rules ended up.");
                break;


            case 5959:
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
                            p.getMask().getRegion().teleport(2539, 4712, 0, 0);
                        else {
                            p.animate(8941);
                            p.graphics(1577);
                            this.stop();
                        }
                    }

                }, 3, 0, 0);
                break;
            case 5960:
                p.getMask().getRegion().teleport(3090, 3956, 0, 0);
                break;
            case 28213:

                Teleporter.tele(p, 2730, 5511);

                break;
            case 28139:
                p.getMask().getRegion().teleport(3271, 3685, 0, 0);
                p.getFrames().sendChatMessage(0, "<col=00A1B3><shad=002896><img=3>You safely return back to Clan Wars!");
                break;
            case 2965:
                if (p.getSkills().getLevel(17) >= 1 && p.getSkills().getLevel(17) <= 84) {
                    p.getFrames().sendChatMessage(0,
                            "<img=3><col=ff0000>Zaro's sent you a message: You need 85 Thieving to steal my Golden Pot!");
                    return;
                }
                if (p.getCombat().thievDelay > 0) {
                    return;
                }
                p.getCombat().thievDelay = 7;
                p.animate(881);
                p.getInventory().addItem(2948, 1);
                p.getSkills().addXp(17, 13000);
                p.getFrames().sendChatMessage(0,
                        "<img=3><col=ff0000>You have found Zaros' hidden pot! I wonder what it does?");
                break;
            case 2966:
                if (absX >= 2427 && absX <= 2430 && absY >= 4720 && absY <= 4725) {
                    p.getMask().getRegion().teleport(2523, 3070, 0, 0);
                    p.animate(7376);
                    p.getFrames().sendChatMessage(0,
                            "<img=3><col=ff0000>You step into the cave as Zaros burns you up and spits you back out.");
                }
                break;
            case 2468:
            case 2466:
            case 2469:
            case 2470:
            case 2471:
            case 2472:
            case 2473:
            case 2474:
            case 2475:
                Teleporter.tele(p, 3494, 3490);
                p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome Back To Canifis!");
                break;
            case 2478:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(556, 28);
                    p.getSkills().addXp(20, 19600);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(556, 27);
                    p.getSkills().addXp(20, 18900);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(556, 26);
                    p.getSkills().addXp(20, 18200);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(556, 25);
                    p.getSkills().addXp(20, 17500);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(556, 24);
                    p.getSkills().addXp(20, 16800);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(556, 23);
                    p.getSkills().addXp(20, 16100);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(556, 22);
                    p.getSkills().addXp(20, 15400);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(556, 21);
                    p.getSkills().addXp(20, 14700);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(556, 20);
                    p.getSkills().addXp(20, 14000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(556, 19);
                    p.getSkills().addXp(20, 13300);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(556, 18);
                    p.getSkills().addXp(20, 12600);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(556, 17);
                    p.getSkills().addXp(20, 11900);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(556, 16);
                    p.getSkills().addXp(20, 11200);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(556, 15);
                    p.getSkills().addXp(20, 10500);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(556, 14);
                    p.getSkills().addXp(20, 9800);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(556, 13);
                    p.getSkills().addXp(20, 9100);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(556, 12);
                    p.getSkills().addXp(20, 8400);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(556, 11);
                    p.getSkills().addXp(20, 7700);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(556, 10);
                    p.getSkills().addXp(20, 7000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(556, 9);
                    p.getSkills().addXp(20, 6300);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(556, 8);
                    p.getSkills().addXp(20, 5600);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(556, 7);
                    p.getSkills().addXp(20, 4900);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(556, 6);
                    p.getSkills().addXp(20, 4200);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(556, 5);
                    p.getSkills().addXp(20, 3500);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(556, 4);
                    p.getSkills().addXp(20, 2800);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(556, 3);
                    p.getSkills().addXp(20, 2100);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(556, 2);
                    p.getSkills().addXp(20, 1400);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(556, 1);
                    p.getSkills().addXp(20, 700);
                }
                break;
            case 13291:
                if (p.getInventory().contains(990, 1)) {
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Please un-note your keys!");
                    return;
                }
                break;
            case 21764:
                if (p.getDonatorRights().ordinal() == 0) {
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>This is a Donator only feature.");
                    return;
                }
                if (p.fountain > 0) {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff000><img=3>You can use the fountain again in " + p.fountain / 2 + " seconds.");
                    return;
                }
                int specincrease = 7 + (p.getSkills().getLevelForXp(5));
                p.animate(881);
                p.getSkills().heal(990);
                p.getSkills().RestorePray(specincrease);
                p.getCombatDefinitions().specpercentage = 100;
                p.getFrames().sendChatMessage(0,
                        "<col=00A1B3><shad=002896><img=3>You have refilled your special, healed to full and restored prayer!");
                p.fountain = 20;
                break;
            case 2479:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(558, 28);
                    p.getSkills().addXp(20, 25200);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(558, 27);
                    p.getSkills().addXp(20, 24300);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(558, 26);
                    p.getSkills().addXp(20, 23400);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(558, 25);
                    p.getSkills().addXp(20, 22500);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(558, 24);
                    p.getSkills().addXp(20, 21600);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(558, 23);
                    p.getSkills().addXp(20, 20700);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(558, 22);
                    p.getSkills().addXp(20, 19800);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(558, 21);
                    p.getSkills().addXp(20, 18900);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(558, 20);
                    p.getSkills().addXp(20, 18000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(558, 19);
                    p.getSkills().addXp(20, 17100);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(558, 18);
                    p.getSkills().addXp(20, 16200);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(558, 17);
                    p.getSkills().addXp(20, 15300);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(558, 16);
                    p.getSkills().addXp(20, 14400);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(558, 15);
                    p.getSkills().addXp(20, 13500);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(558, 14);
                    p.getSkills().addXp(20, 12600);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(558, 13);
                    p.getSkills().addXp(20, 11700);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(558, 12);
                    p.getSkills().addXp(20, 10800);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(558, 11);
                    p.getSkills().addXp(20, 9900);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(558, 10);
                    p.getSkills().addXp(20, 9000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(558, 9);
                    p.getSkills().addXp(20, 8100);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(558, 8);
                    p.getSkills().addXp(20, 7200);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(558, 7);
                    p.getSkills().addXp(20, 6300);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(558, 6);
                    p.getSkills().addXp(20, 5400);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(558, 5);
                    p.getSkills().addXp(20, 4500);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(558, 4);
                    p.getSkills().addXp(20, 3600);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(558, 3);
                    p.getSkills().addXp(20, 2700);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(558, 2);
                    p.getSkills().addXp(20, 1800);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(558, 1);
                    p.getSkills().addXp(20, 900);
                }
                break;
            case 2482:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(554, 28);
                    p.getSkills().addXp(20, 42000);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(554, 27);
                    p.getSkills().addXp(20, 40500);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(554, 26);
                    p.getSkills().addXp(20, 39000);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(554, 25);
                    p.getSkills().addXp(20, 37500);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(554, 24);
                    p.getSkills().addXp(20, 36000);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(554, 23);
                    p.getSkills().addXp(20, 34500);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(554, 22);
                    p.getSkills().addXp(20, 33000);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(554, 21);
                    p.getSkills().addXp(20, 31500);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(554, 20);
                    p.getSkills().addXp(20, 30000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(554, 19);
                    p.getSkills().addXp(20, 28500);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(554, 18);
                    p.getSkills().addXp(20, 27000);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(554, 17);
                    p.getSkills().addXp(20, 25500);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(554, 16);
                    p.getSkills().addXp(20, 24000);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(554, 15);
                    p.getSkills().addXp(20, 22500);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(554, 14);
                    p.getSkills().addXp(20, 21000);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(554, 13);
                    p.getSkills().addXp(20, 19500);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(558, 12);
                    p.getSkills().addXp(20, 18000);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(554, 11);
                    p.getSkills().addXp(20, 16500);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(554, 10);
                    p.getSkills().addXp(20, 15000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(554, 9);
                    p.getSkills().addXp(20, 13500);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(554, 8);
                    p.getSkills().addXp(20, 12000);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(554, 7);
                    p.getSkills().addXp(20, 10500);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(554, 6);
                    p.getSkills().addXp(20, 9000);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(554, 5);
                    p.getSkills().addXp(20, 7500);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(554, 4);
                    p.getSkills().addXp(20, 6000);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(554, 3);
                    p.getSkills().addXp(20, 4500);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(554, 2);
                    p.getSkills().addXp(20, 3000);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(554, 1);
                    p.getSkills().addXp(20, 1500);
                }
                break;
            case 2483:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(559, 28);
                    p.getSkills().addXp(20, 47600);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(559, 27);
                    p.getSkills().addXp(20, 45900);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(559, 26);
                    p.getSkills().addXp(20, 44200);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(559, 25);
                    p.getSkills().addXp(20, 42500);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(559, 24);
                    p.getSkills().addXp(20, 40800);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(559, 23);
                    p.getSkills().addXp(20, 39100);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(559, 22);
                    p.getSkills().addXp(20, 37400);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(559, 21);
                    p.getSkills().addXp(20, 35700);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(559, 20);
                    p.getSkills().addXp(20, 34000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(559, 19);
                    p.getSkills().addXp(20, 32300);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(559, 18);
                    p.getSkills().addXp(20, 30600);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(559, 17);
                    p.getSkills().addXp(20, 28900);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(559, 16);
                    p.getSkills().addXp(20, 27200);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(559, 15);
                    p.getSkills().addXp(20, 25500);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(559, 14);
                    p.getSkills().addXp(20, 23800);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(559, 13);
                    p.getSkills().addXp(20, 22100);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(558, 12);
                    p.getSkills().addXp(20, 20400);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(559, 11);
                    p.getSkills().addXp(20, 18700);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(559, 10);
                    p.getSkills().addXp(20, 17000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(559, 9);
                    p.getSkills().addXp(20, 15300);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(559, 8);
                    p.getSkills().addXp(20, 13600);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(559, 7);
                    p.getSkills().addXp(20, 11900);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(559, 6);
                    p.getSkills().addXp(20, 10200);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(559, 5);
                    p.getSkills().addXp(20, 8500);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(559, 4);
                    p.getSkills().addXp(20, 6800);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(559, 3);
                    p.getSkills().addXp(20, 5100);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(559, 2);
                    p.getSkills().addXp(20, 3400);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(559, 1);
                    p.getSkills().addXp(20, 1700);
                }
                break;
            case 2484:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(564, 28);
                    p.getSkills().addXp(20, 53200);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(564, 27);
                    p.getSkills().addXp(20, 51300);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(564, 26);
                    p.getSkills().addXp(20, 49400);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(564, 25);
                    p.getSkills().addXp(20, 47500);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(564, 24);
                    p.getSkills().addXp(20, 45600);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(564, 23);
                    p.getSkills().addXp(20, 43700);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(564, 22);
                    p.getSkills().addXp(20, 41800);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(564, 21);
                    p.getSkills().addXp(20, 39900);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(564, 20);
                    p.getSkills().addXp(20, 38000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(564, 19);
                    p.getSkills().addXp(20, 36100);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(564, 18);
                    p.getSkills().addXp(20, 34200);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(564, 17);
                    p.getSkills().addXp(20, 32300);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(564, 16);
                    p.getSkills().addXp(20, 30400);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(564, 15);
                    p.getSkills().addXp(20, 28500);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(564, 14);
                    p.getSkills().addXp(20, 26600);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(564, 13);
                    p.getSkills().addXp(20, 24700);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(558, 12);
                    p.getSkills().addXp(20, 22800);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(564, 11);
                    p.getSkills().addXp(20, 20900);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(564, 10);
                    p.getSkills().addXp(20, 17000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(564, 9);
                    p.getSkills().addXp(20, 17100);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(564, 8);
                    p.getSkills().addXp(20, 15200);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(564, 7);
                    p.getSkills().addXp(20, 13300);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(564, 6);
                    p.getSkills().addXp(20, 11400);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(564, 5);
                    p.getSkills().addXp(20, 9500);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(564, 4);
                    p.getSkills().addXp(20, 7600);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(564, 3);
                    p.getSkills().addXp(20, 5700);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(564, 2);
                    p.getSkills().addXp(20, 3800);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(564, 1);
                    p.getSkills().addXp(20, 1900);
                }
                break;
            case 2485:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(563, 28);
                    p.getSkills().addXp(20, 67200);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(563, 27);
                    p.getSkills().addXp(20, 64800);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(563, 26);
                    p.getSkills().addXp(20, 62400);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(563, 25);
                    p.getSkills().addXp(20, 60000);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(563, 24);
                    p.getSkills().addXp(20, 57600);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(563, 23);
                    p.getSkills().addXp(20, 55200);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(563, 22);
                    p.getSkills().addXp(20, 52800);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(563, 21);
                    p.getSkills().addXp(20, 50400);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(563, 20);
                    p.getSkills().addXp(20, 48000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(563, 19);
                    p.getSkills().addXp(20, 45600);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(563, 18);
                    p.getSkills().addXp(20, 43200);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(563, 17);
                    p.getSkills().addXp(20, 40800);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(563, 16);
                    p.getSkills().addXp(20, 38400);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(563, 15);
                    p.getSkills().addXp(20, 36000);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(563, 14);
                    p.getSkills().addXp(20, 33600);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(563, 13);
                    p.getSkills().addXp(20, 31200);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(563, 12);
                    p.getSkills().addXp(20, 28800);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(563, 11);
                    p.getSkills().addXp(20, 26400);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(563, 10);
                    p.getSkills().addXp(20, 24000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(563, 9);
                    p.getSkills().addXp(20, 21600);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(563, 8);
                    p.getSkills().addXp(20, 19200);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(563, 7);
                    p.getSkills().addXp(20, 16800);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(563, 6);
                    p.getSkills().addXp(20, 14400);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(563, 5);
                    p.getSkills().addXp(20, 12000);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(563, 4);
                    p.getSkills().addXp(20, 9600);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(563, 3);
                    p.getSkills().addXp(20, 7200);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(563, 2);
                    p.getSkills().addXp(20, 4800);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(563, 1);
                    p.getSkills().addXp(20, 2400);
                }
                break;
            case 2486:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(561, 28);
                    p.getSkills().addXp(20, 60600);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(561, 27);
                    p.getSkills().addXp(20, 58400);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(561, 26);
                    p.getSkills().addXp(20, 56200);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(561, 25);
                    p.getSkills().addXp(20, 54000);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(561, 24);
                    p.getSkills().addXp(20, 51800);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(561, 23);
                    p.getSkills().addXp(20, 49600);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(561, 22);
                    p.getSkills().addXp(20, 47400);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(561, 21);
                    p.getSkills().addXp(20, 45200);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(561, 20);
                    p.getSkills().addXp(20, 43000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(561, 19);
                    p.getSkills().addXp(20, 41800);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(561, 18);
                    p.getSkills().addXp(20, 39600);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(561, 17);
                    p.getSkills().addXp(20, 37400);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(561, 16);
                    p.getSkills().addXp(20, 35200);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(561, 15);
                    p.getSkills().addXp(20, 33000);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(561, 14);
                    p.getSkills().addXp(20, 30800);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(561, 13);
                    p.getSkills().addXp(20, 28600);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(561, 12);
                    p.getSkills().addXp(20, 26400);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(561, 11);
                    p.getSkills().addXp(20, 24200);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(561, 10);
                    p.getSkills().addXp(20, 22000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(561, 9);
                    p.getSkills().addXp(20, 19800);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(561, 8);
                    p.getSkills().addXp(20, 17600);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(561, 7);
                    p.getSkills().addXp(20, 15400);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(561, 6);
                    p.getSkills().addXp(20, 13200);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(561, 5);
                    p.getSkills().addXp(20, 11000);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(561, 4);
                    p.getSkills().addXp(20, 8800);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(561, 3);
                    p.getSkills().addXp(20, 6600);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(561, 2);
                    p.getSkills().addXp(20, 4400);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(561, 1);
                    p.getSkills().addXp(20, 2200);
                }
                break;
            case 2487:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(562, 28);
                    p.getSkills().addXp(20, 56000);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(562, 27);
                    p.getSkills().addXp(20, 54000);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(562, 26);
                    p.getSkills().addXp(20, 52000);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(562, 25);
                    p.getSkills().addXp(20, 50000);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(562, 24);
                    p.getSkills().addXp(20, 48000);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(562, 23);
                    p.getSkills().addXp(20, 46000);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(562, 22);
                    p.getSkills().addXp(20, 44000);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(562, 21);
                    p.getSkills().addXp(20, 42000);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(562, 20);
                    p.getSkills().addXp(20, 40000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(562, 19);
                    p.getSkills().addXp(20, 38000);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(562, 18);
                    p.getSkills().addXp(20, 36000);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(562, 17);
                    p.getSkills().addXp(20, 34000);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(562, 16);
                    p.getSkills().addXp(20, 32000);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(562, 15);
                    p.getSkills().addXp(20, 30000);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(562, 14);
                    p.getSkills().addXp(20, 28000);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(562, 13);
                    p.getSkills().addXp(20, 26000);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(552, 12);
                    p.getSkills().addXp(20, 24000);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(562, 11);
                    p.getSkills().addXp(20, 22000);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(562, 10);
                    p.getSkills().addXp(20, 20000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(562, 9);
                    p.getSkills().addXp(20, 18000);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(562, 8);
                    p.getSkills().addXp(20, 16000);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(562, 7);
                    p.getSkills().addXp(20, 14000);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(562, 6);
                    p.getSkills().addXp(20, 12000);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(562, 5);
                    p.getSkills().addXp(20, 10000);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(562, 4);
                    p.getSkills().addXp(20, 8000);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(562, 3);
                    p.getSkills().addXp(20, 6000);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(562, 2);
                    p.getSkills().addXp(20, 4000);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(562, 1);
                    p.getSkills().addXp(20, 2000);
                }
                break;
            case 2488:
                if (p.getInventory().contains(1436, 28)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 28);
                    p.getInventory().addItem(560, 28);
                    p.getSkills().addXp(20, 72800);
                }
                if (p.getInventory().contains(1436, 27)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 27);
                    p.getInventory().addItem(560, 27);
                    p.getSkills().addXp(20, 70200);
                }
                if (p.getInventory().contains(1436, 26)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 26);
                    p.getInventory().addItem(560, 26);
                    p.getSkills().addXp(20, 67600);
                }
                if (p.getInventory().contains(1436, 25)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 25);
                    p.getInventory().addItem(560, 25);
                    p.getSkills().addXp(20, 65000);
                }
                if (p.getInventory().contains(1436, 24)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 24);
                    p.getInventory().addItem(560, 24);
                    p.getSkills().addXp(20, 62400);
                }
                if (p.getInventory().contains(1436, 23)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 23);
                    p.getInventory().addItem(560, 23);
                    p.getSkills().addXp(20, 59800);
                }
                if (p.getInventory().contains(1436, 22)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 22);
                    p.getInventory().addItem(560, 22);
                    p.getSkills().addXp(20, 57200);
                }
                if (p.getInventory().contains(1436, 21)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 21);
                    p.getInventory().addItem(560, 21);
                    p.getSkills().addXp(20, 54600);
                }
                if (p.getInventory().contains(1436, 20)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 20);
                    p.getInventory().addItem(560, 20);
                    p.getSkills().addXp(20, 52000);
                }
                if (p.getInventory().contains(1436, 19)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 19);
                    p.getInventory().addItem(560, 19);
                    p.getSkills().addXp(20, 49400);
                }
                if (p.getInventory().contains(1436, 18)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 18);
                    p.getInventory().addItem(560, 18);
                    p.getSkills().addXp(20, 46800);
                }
                if (p.getInventory().contains(1436, 17)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 17);
                    p.getInventory().addItem(560, 17);
                    p.getSkills().addXp(20, 44200);
                }
                if (p.getInventory().contains(1436, 16)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 16);
                    p.getInventory().addItem(560, 16);
                    p.getSkills().addXp(20, 41600);
                }
                if (p.getInventory().contains(1436, 15)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 15);
                    p.getInventory().addItem(560, 15);
                    p.getSkills().addXp(20, 39000);
                }
                if (p.getInventory().contains(1436, 14)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 14);
                    p.getInventory().addItem(560, 14);
                    p.getSkills().addXp(20, 36400);
                }
                if (p.getInventory().contains(1436, 13)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 13);
                    p.getInventory().addItem(560, 13);
                    p.getSkills().addXp(20, 33800);
                }
                if (p.getInventory().contains(1436, 12)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 12);
                    p.getInventory().addItem(552, 12);
                    p.getSkills().addXp(20, 31200);
                }
                if (p.getInventory().contains(1436, 11)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 11);
                    p.getInventory().addItem(560, 11);
                    p.getSkills().addXp(20, 28600);
                }
                if (p.getInventory().contains(1436, 10)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 10);
                    p.getInventory().addItem(560, 10);
                    p.getSkills().addXp(20, 26000);
                }
                if (p.getInventory().contains(1436, 9)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 9);
                    p.getInventory().addItem(560, 9);
                    p.getSkills().addXp(20, 23400);
                }
                if (p.getInventory().contains(1436, 8)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 8);
                    p.getInventory().addItem(560, 8);
                    p.getSkills().addXp(20, 20800);
                }
                if (p.getInventory().contains(1436, 7)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 7);
                    p.getInventory().addItem(560, 7);
                    p.getSkills().addXp(20, 18200);
                }
                if (p.getInventory().contains(1436, 6)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 6);
                    p.getInventory().addItem(560, 6);
                    p.getSkills().addXp(20, 15600);
                }
                if (p.getInventory().contains(1436, 5)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 5);
                    p.getInventory().addItem(560, 5);
                    p.getSkills().addXp(20, 13000);
                }
                if (p.getInventory().contains(1436, 4)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 4);
                    p.getInventory().addItem(560, 4);
                    p.getSkills().addXp(20, 10400);
                }
                if (p.getInventory().contains(1436, 3)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 3);
                    p.getInventory().addItem(560, 3);
                    p.getSkills().addXp(20, 7800);
                }
                if (p.getInventory().contains(1436, 2)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 2);
                    p.getInventory().addItem(560, 2);
                    p.getSkills().addXp(20, 5200);
                }
                if (p.getInventory().contains(1436, 1)) {
                    p.animate(791);
                    p.getInventory().deleteItem(1436, 1);
                    p.getInventory().addItem(560, 1);
                    p.getSkills().addXp(20, 2600);
                }
                break;
            case 2804:
                p.getSkills().addXp(16, 10000);
                p.animate(7376);
                p.getMask().getRegion().teleport(2772, 9341, 0, 0);
                p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Mining Cave!");
                break;
            case 4465:
                if (p.getLocation().getX() == 2415 && p.getLocation().getY() == 3073) {
                    p.getMask().getRegion().teleport(2414, 3073, 0, 0);
                } else if (p.getLocation().getX() == 2414 && p.getLocation().getY() == 3073) {
                    p.getMask().getRegion().teleport(2415, 3073, 0, 0);
                }
                break;
            case 4467:
                if (p.getLocation().getX() == 2384 && p.getLocation().getY() == 3134) {
                    p.getMask().getRegion().teleport(2385, 3134, 0, 0);
                } else if (p.getLocation().getX() == 2385 && p.getLocation().getY() == 3134) {
                    p.getMask().getRegion().teleport(2384, 3134, 0, 0);
                }
                break;
            case 36574:
                p.getFrames().sendChatMessage(0, "<col=00FFFF>You take Rocktails from the table!");
                int Spaces = p.getInventory().getFreeSlots();
                p.getInventory().addItem(391, Spaces); // Rocktails
                break;
            case 26081:
            case 26082:
                if (p.getLocation().getX() == 2757 && p.getLocation().getY() == 3483) {
                    p.getMask().getRegion().teleport(2757, 3482, 0, 0);
                    p.getFrames().sendChatMessage(0, "<col=FF0000>THIS IS DANGEROUS - ALL ITEMS LOST ON DEATH!");
                    return;
                }
                if (p.getLocation().getX() == 2758 && p.getLocation().getY() == 3483) {
                    p.getMask().getRegion().teleport(2758, 3482, 0, 0);
                    p.getFrames().sendChatMessage(0, "<col=FF0000>THIS IS DANGEROUS - ALL ITEMS LOST ON DEATH!");
                    return;
                }
                if (p.getLocation().getX() == 2757 && p.getLocation().getY() == 3482) {
                    p.getMask().getRegion().teleport(2757, 3483, 0, 0);
                    return;
                }
                if (p.getLocation().getX() == 2758 && p.getLocation().getY() == 3482) {
                    p.getMask().getRegion().teleport(2758, 3483, 0, 0);
                    return;
                }
                break;
            case 2971:
                if (!p.getInventory().contains(1925, 1)) {
                    p.getFrames().sendChatMessage(0, "You need a bucket to collect the water from the waterfall!");
                    return;
                }
                if (p.getLocation().getX() == 2421 && p.getLocation().getY() == 4691) {
                    p.getMask().getRegion().teleport(2421, 4690, 0, 0);
                    p.getFrames().sendChatMessage(0,
                            "<col=0000FF>You hold the bucket over your head making a gap for you to walk through!");
                    p.getInventory().deleteItem(1925, 1);
                    p.getInventory().addItem(1929, 1);
                    return;
                }
                if (p.getLocation().getX() == 2420 && p.getLocation().getY() == 4691) {
                    p.getMask().getRegion().teleport(2420, 4690, 0, 0);
                    p.getFrames().sendChatMessage(0,
                            "<col=0000FF>You hold the bucket over your head making a gap for you to walk through!");
                    p.getInventory().deleteItem(1925, 1);
                    p.getInventory().addItem(1929, 1);
                    return;
                }
                if (p.getLocation().getX() == 2420 && p.getLocation().getY() == 4690) {
                    p.getMask().getRegion().teleport(2420, 4691, 0, 0);
                    p.getFrames().sendChatMessage(0,
                            "<col=0000FF>You hold the bucket over your head making a gap for you to walk through!");
                    p.getInventory().deleteItem(1925, 1);
                    p.getInventory().addItem(1929, 1);
                    return;
                }
                if (p.getLocation().getX() == 2421 && p.getLocation().getY() == 4690) {
                    p.getMask().getRegion().teleport(2421, 4691, 0, 0);
                    p.getFrames().sendChatMessage(0,
                            "<col=0000FF>You hold the bucket over your head making a gap for you to walk through!");
                    p.getInventory().deleteItem(1925, 1);
                    p.getInventory().addItem(1929, 1);
                    return;
                }
                break;
            case 4469:
                if (p.getLocation().getX() == 2423 && p.getLocation().getY() == 3076) {
                    p.animate(6132);
                    GameServer.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getMask().getRegion().teleport(2422, 3076, 1, 0);
                        }
                    }, 900);
                } else if (p.getLocation().getX() == 2422 && p.getLocation().getY() == 3076) {
                    p.animate(6132);
                    GameServer.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getMask().getRegion().teleport(2423, 3076, 1, 0);
                        }
                    }, 900);
                } else if (p.getLocation().getX() == 2426 && p.getLocation().getY() == 3080) {
                    p.animate(6132);
                    GameServer.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getMask().getRegion().teleport(2426, 3079, 1, 0);
                        }
                    }, 900);
                } else if (p.getLocation().getX() == 2426 && p.getLocation().getY() == 3079) {
                    p.animate(6132);
                    GameServer.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getMask().getRegion().teleport(2426, 3080, 1, 0);
                        }
                    }, 900);
                }
                break;
            case 14369:
            case 4483:
                p.getBank().openBank();
                p.getInventory().refresh();
                break;
            case 7129:
                if (p.getSkills().getLevel(20) >= 14 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2585, 4833);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Fire Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 14 Runecrafting to teleport to the Fire Altar!");
                    return;
                }
                break;
            case 7130:
                if (p.getSkills().getLevel(20) >= 9 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2656, 4829);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Earth Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 9 Runecrafting to teleport to the Earth Altar!");
                    return;
                }
                break;
            case 7131:
                if (p.getSkills().getLevel(20) >= 20 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2522, 4833);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Body Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 20 Runecrafting to teleport to the Earth Altar!");
                    return;
                }
                break;
            case 7132:
                if (p.getSkills().getLevel(20) >= 27 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2142, 4813);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Cosmic Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 27 Runecrafting to teleport to the Cosmic Altar!");
                    return;
                }
                break;
            case 7133:
                if (p.getSkills().getLevel(20) >= 44 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2400, 4835);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Nature Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 44 Runecrafting to teleport to the Nature Altar!");
                    return;
                }
                break;
            case 7134:
                if (p.getSkills().getLevel(20) >= 35 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2271, 4835);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Chaos Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 35 Runecrafting to teleport to the Chaos Altar!");
                    return;
                }
                break;
            case 7135:
                if (p.getSkills().getLevel(20) >= 54 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2464, 4821);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Law Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 54 Runecrafting to teleport to the Law Altar!");
                    return;
                }
                break;
            case 7136:
                if (p.getSkills().getLevel(20) >= 65 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2206, 4834);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Death Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 65 Runecrafting to teleport to the Death Altar!");
                    return;
                }
                break;
            case 7137:
                p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>The Water Altar is currently being added!");
                break;
            case 7138:
                p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>The Soul Altar is current being added!");
                break;
            case 7139:
                Teleporter.tele(p, 2841, 4829);
                p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Air Altar!");
                break;
            case 2491:
                p.animate(-1);
                if (p.getInventory().contains(1275, 1) || p.getInventory().contains(1265, 1)
                        || p.getInventory().contains(1267, 1) || p.getInventory().contains(1269, 1)
                        || p.getInventory().contains(1271, 1) || p.getInventory().contains(1273, 1)
                        || p.getInventory().contains(15260, 1) || p.getEquipment().contains(1275)
                        || p.getEquipment().contains(1265) || p.getEquipment().contains(1267)
                        || p.getEquipment().contains(1269) || p.getEquipment().contains(1271)
                        || p.getEquipment().contains(1273) || p.getEquipment().contains(15260)) {
                } else {
                    p.getFrames().sendChatMessage(0, "You need a pickaxe to mine this rock!");
                    return;
                }
                if (p.getSkills().getLevel(14) >= 1 && p.getSkills().getLevel(14) <= 49) {
                    p.animate(625);
                    p.getSkills().addXp(14, 1500);
                    p.getInventory().addItem(1436, 1);
                    return;
                }
                if (p.getSkills().getLevel(14) >= 50 && p.getSkills().getLevel(14) <= 99) {
                    p.animate(625);
                    p.getSkills().addXp(14, 2500);
                    p.getInventory().addItem(1436, 1);
                    p.getInventory().addItem(7936, 1);
                }
                break;
            case 2492:
                Teleporter.tele(p, 3249, 3402);
                p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>You have been teleported back to Aubury's Location!");
                break;
            case 7140:
                if (p.getSkills().getLevel(20) >= 2 && p.getSkills().getLevel(20) <= 99) {
                    Teleporter.tele(p, 2793, 4828);
                    p.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Welcome to the Mind Altar!");
                } else {
                    p.getFrames().sendChatMessage(0,
                            "<col=ff0000><img=3>You need at least level 2 Runecrafting to teleport to the Mind Altar!");
                    return;
                }
                break;
            case 7141:
                p.getFrames().sendChatMessage(0,
                        "<col=ff0000><img=3>The Blood Altar is currently being added! Please use the Death Altar instead!");
                break;
            case 12731:
                p.getBank().openBank();
                p.getInventory().refresh();
                break;
        }
    }

    public static void manageOption2(final Player p, int objectId, RSTile location, int coordX, int coordY, int height)
            throws IOException {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        if (p.getUsername().equals("jonny")) {
            p.getFrames().sendChatMessage(0,
                    "ObjectId: " + objectId + ", X: " + coordX + ", Y: " + coordY + ", Height: " + height + "");
        }
        p.getWalk().reset(true);
        p.turnTemporarilyTo(location);
        p.getMask().setApperanceUpdate(true);
        switch (objectId) {
            case 12731:
            case 4483:
            case 11758:
            case 49018:
            case 2213:
            case 14369:
            case 26972:
                p.getBank().openBank();
                p.getInventory().refresh();
                break;
        }
    }
}
