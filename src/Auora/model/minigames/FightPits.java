package Auora.model.minigames;

import Auora.events.Event;
import Auora.events.EventManager;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @Author : Crezzy 
 * 
 */

public class FightPits {
    public static List<Player> playersLobby = new ArrayList<Player>(), playersInPits = new ArrayList<Player>();
    private static int timeUntillGame = 60;
    private static boolean gameInProcess = false;
    private final ExecutorService taskExecutor = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private String winnerName = "None";
    private final String winnerString = "Current Champion is: " + winnerName;
    /*
     * this section of the class will handle the rewards/winner system. Rewards
     * will be generated randomly depending on which system you choose
     * Explanation: If you have won a FightPitgame between 1-9 times the rewards
     * will be generated using the low system. If you have won a FightPits game
     * more than between 10-49 times then will be generated using the med system
     * If you have won a fightpits game 50+ times rewards will then will be
     * generated using the high system.
     */
    /*
     * Rewards id's:
	 */
    private int rewardsLow[] = {6570, 1127,};
    private int rewardsMed[] = {6570};
    private int rewardsHigh[] = {6570};

    /*
     * @note: checks if the player is in fight pits lobby
     */
    public static boolean inFightPitsLobby(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        return (absX >= 2394 && absX <= 2405 && absY >= 5169 && absY <= 5176);
    }

    public void addRemovePlayerLobbyList(Player p, boolean adding) {
        synchronized (playersLobby) {
            if (adding) {
                playersLobby.add(p);
            } else {
                playersLobby.remove(p);
            }
        }
    }

    public void removePlayerPitsList(Player p) {
        synchronized (playersInPits) {
            playersInPits.remove(p);
        }
    }

    /*
     * the pits process which checks everything todo with fightpits every 1
     * second || 1000ms
     */
    private final void pitsProcess() {
        EventManager.submit(new Event(1000) {
            @Override
            public void execute() {
                taskExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        processLobbyTimer();
                        for (Player pLobby : playersLobby) {
                            handleOverlay(pLobby);
                            checkIfCanStartGame(pLobby);
                            continue;
                        }
                    }
                });
            }
        });
    }

    private void processLobbyTimer() {
        if (timeUntillGame >= 1 /*&& playersLobby.size() >= 2*/) {
            timeUntillGame--;
        }
    }

    /*
     * Handles the overlay strings
     */
    private void handleOverlay(Player p) {
        for (Player pl : playersLobby) {
            if (playersLobby.size() < 2) {
                p.getFrames().sendString("Need 2 Players to Begin The Game", 373, 0);
                p.getFrames().sendString(winnerString, 373, 1);
                continue;
            }
            if (playersLobby.size() >= 2 && playersLobby.contains(p) && !playersInPits.contains(p)) {
                pl.getFrames().sendString("Time until nextgame : " + timeUntillGame, 373, 0);
                pl.getFrames().sendString(winnerString, 373, 1);
                continue;

            }
            if (playersLobby.contains(p) && gameInProcess == true && !playersInPits.contains(p)) {
                pl.getFrames().sendString("Currently a game being Played. Players Remaining: " + playersInPits.size(),
                        373, 0);
                pl.getFrames().sendString(winnerString, 373, 1);
                continue;
            }
        }
    }

    /*
     * @note:Handles the in pitsgame overlay strings
     */
    private void sendInGameOverlayStrings(Player p) {
        for (Player pl : playersInPits) {
            pl.getFrames().sendString("There are currently " + (playersInPits.size() - 1) + " Foes Remaining",
                    373, 0);
            pl.getFrames().sendString(winnerString, 373, 1);
            continue;
        }
    }

    /*
     * @note:Checks if game can be started
     */
    private void checkIfCanStartGame(Player p) {
        for (Player pl : playersLobby)
            if (timeUntillGame == 0 && !playersInPits.contains(pl) && gameInProcess == false
                    && playersLobby.contains(pl) && playersLobby.size() >= 2) {
                startPitsGame(pl);
                gameInProcess = true;
            } else {
                handleOverlay(pl);
//				if (playersLobby.size() < 2) {
//					taskExecutor.shutdownNow();
//				}
            }
    }

    /*
     * @note: once the game process reaches the req the game is started
     */
    private void startPitsGame(Player p) {
        for (Player pl : playersLobby) {
            while (!playersInPits.contains(pl)) {
                getRandomTeleports(pl);
                playersInPits.add(pl);
            }
            sendInGameOverlayStrings(pl);
        }
        playersLobby.clear();
    }

    /*
     * @note: checks if pits game can end during a kill
     */
    private void checkIfCanEndPitsGame(Player p) {
        for (Player pl : playersInPits) {
            if (playersInPits.size() <= 1 && playersInPits.contains(pl) && gameInProcess == true) {
                gameInProcess = false;
                winnerName = pl.getUsername();
                giveReward(pl);
                taskExecutor.shutdownNow();
                playersInPits.clear();
                waitLobby(pl);
            }
        }
    }

    /*
     * @note:checks if the player is in fight pits game area
     */
    private boolean inFightPitsGame(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        return (absX >= 2368 && absX <= 2425 && absY >= 5130 && absY <= 5167);
    }

    /*
     * @note:Handles when a player logs into pits area
     */
    public void handleLogInPits(Player p) {
        if (needsReward(p) == true) {
            giveReward(p);
        }
        if (inFightPitsLobby(p) || inFightPitsGame(p)) {
            p.getMask().getRegion().teleport(2399, 5177, 0, 0);
        }
    }

    /*
     * @note:Handles when a player logs out while in the pits area
     */
    public void handleLogOutPits(Player p) {
        if (playersLobby.contains(p)) {
            addRemovePlayerLobbyList(p, false);
            handleOverlay(p);
        } else if (playersInPits.contains(p)) {
            removePlayerPitsList(p);
            for (Player pl : playersInPits) {
                handleOverlay(pl);
                checkIfCanEndPitsGame(pl);
            }
        }
    }

    /*
     * @note:Called once a player dies in fight pits
     */
    public void playerPitsDeath(Player p) {
        if (playersInPits.contains(p)) {
            p.getMask().getRegion().teleport(2399, 5175, 0, 0);
            removePlayerPitsList(p);
            addRemovePlayerLobbyList(p, true);
            p.getFrames().sendOverlay(373);
            handleOverlay(p);
            for (Player pl : playersInPits) {
                sendInGameOverlayStrings(pl);
                checkIfCanEndPitsGame(pl);
                continue;
            }
        }
    }

    /*
     *
     * @note:the random teleports which occurs once called in the start game
     * metod
     */
    private void getRandomTeleports(Player p) {
        switch (Misc.random(4)) {
            case 0:
                p.getMask().getRegion().teleport(2384 + Misc.random(29), 5133 + Misc.random(4), 0, 0);
                break;
            case 1:
                p.getMask().getRegion().teleport(2410 + Misc.random(4), 5140 + Misc.random(18), 0, 0);
                break;
            case 2:
                p.getMask().getRegion().teleport(2392 + Misc.random(11), 5141 + Misc.random(26), 0, 0);
                break;
            case 3:
                p.getMask().getRegion().teleport(2383 + Misc.random(3), 5141 + Misc.random(15), 0, 0);
                break;
            case 4:
                p.getMask().getRegion().teleport(2392 + Misc.random(12), 5145 + Misc.random(20), 0, 0);
                break;
        }
    }

    // /////////////////////////////////////rewards
    // Start////////////////////////////////////////////////

    /*
     * @note:Resets pits varribles when called Via command what I'm using for.
     */
    public void resetPitsVars() {
        winnerName = "None";
        timeUntillGame = 0;
        playersLobby.clear();
        playersInPits.clear();
        gameInProcess = false;
        for (Player pl : World.getPlayers()) {
            checkIfCanEndPitsGame(pl);
        }
    }

    /*
     * the lobby
     */
    private void waitLobby(Player p) {
        if (playersLobby.contains(p)) {
            playersLobby.remove(p);
            return;
        }
        p.getFrames().sendOverlay(373);
        addRemovePlayerLobbyList(p, true);
        handleOverlay(p);
        if (p.getLocation().getX() == 2399 && p.getLocation().getY() == 5167) {
            p.getMask().getRegion().teleport(2399, 5169, 0, 0);
        } else if (p.getLocation().getX() == 2399 && p.getLocation().getY() == 5177) {
            p.getMask().getRegion().teleport(2399, 5175, 0, 0);
        } else {
            p.getMask().getRegion().teleport(2399, 5175, 0, 0);
        }
        if (playersLobby.size() >= 2 && gameInProcess == false) {
            //	timeUntillGame = 60;
            pitsProcess();
        }
    }

    /*
     * Handles the pits objects.
     */
    public void pitsObject(Player p, int object) {
        switch (object) {
            case 9369:
                if (p.getLocation().getX() == 2399 && p.getLocation().getY() == 5177) {
                    waitLobby(p);
                } else if (p.getLocation().getX() == 2399 && p.getLocation().getY() == 5175) {
                    p.getMask().getRegion().teleport(2399, 5177, 0, 0);
                    addRemovePlayerLobbyList(p, false);
                    p.getFrames().sendCloseOverlay();
                    for (Player pl : playersLobby) {
                        handleOverlay(pl);
                    }
                }
                break;
            case 9368:
                if (p.getLocation().getX() == 2399 && p.getLocation().getY() == 5169) {
                    p.getFrames().sendChatMessage(0, "Magical heat prevents you from passing.");
                } else if (p.getLocation().getX() == 2399 && p.getLocation().getY() == 5167) {
                    removePlayerPitsList(p);
                    waitLobby(p);
                    p.getFrames().sendChatMessage(0, "You forfit the game.");
                    for (Player pl : playersInPits) {
                        sendInGameOverlayStrings(pl);
                    }
                }
        }
    }

    /*
     * Randomly generates a low reward. Items are in an array displayed at the
     * top of the class
     */
    private int rewardsLow() {
        return rewardsLow[(int) (Math.random() * rewardsLow.length)];
    }

    /*
     * Randomly generates a medium reward. Items are in an array displayed at
     * the top of the class
     */
    private int rewardsMed() {
        return rewardsMed[(int) (Math.random() * rewardsMed.length)];
    }

    /*
     * Randomly generates a High reward. Items are in an array displayed at the
     * top of the class
     */
    private int rewardsHigh() {
        return rewardsHigh[(int) (Math.random() * rewardsHigh.length)];
    }

    /*
     * Checks if the player needs reward or not.
     */
    private boolean needsReward(Player p) {
        return p.needPitsReward;
    }

    /*
     * Sends the a message to people on the low rewards system.
     */
    private void lowMessage(Player p) {
        p.getFrames().sendChatMessage(0, "You have currently won : " + p.fightPitsWins + " FightPits Games!");
        p.getFrames().sendChatMessage(0, "You are currently on the Low Reward System.");
        p.getFrames().sendChatMessage(0, "Win another" + (10 - p.fightPitsWins) + "To progress onto Medium rewards!");
    }

    /*
     * Sends the a message to people on the medium rewards system.
     */
    private void medMessage(Player p) {
        if (p.fightPitsWins == 10) {
            p.getFrames().sendChatMessage(0, "Congratulations you have now won 10 games!");
            p.getFrames().sendChatMessage(0, "You have now progressed onto the Medium rewards System!");
        } else {
            p.getFrames().sendChatMessage(0, "You have currently won : " + p.fightPitsWins + " FightPits Games!");
            p.getFrames().sendChatMessage(0, "You are currently on the Medium Reward System.");
            p.getFrames().sendChatMessage(0,
                    "Win another" + (50 - p.fightPitsWins) + "To progress onto Medium rewards!");
        }
    }

    /*
     * Sends the a message to people on the High rewards system.
     */
    private void HighMessage(Player p) {
        if (p.fightPitsWins == 50) {
            p.getFrames().sendChatMessage(0, "Congratulations you have now won 50 games!");
            p.getFrames().sendChatMessage(0, " You have now progressed onto the High Rewards System!");
        } else {
            p.getFrames().sendChatMessage(0, "You have currently won : " + p.fightPitsWins + " FightPits Games!");
            p.getFrames().sendChatMessage(0, "You are currently on the High Reward System.");
        }
    }

    private void invoSpace(Player p) {
        p.needPitsReward = true;
        p.getFrames().sendChatMessage(0, "Sorry! You currently do not have enough Inventory space to be Rewarded.");
        p.getFrames().sendChatMessage(0, "Please make sure you have Atleast 3 Slots available.");
        p.getFrames().sendChatMessage(0, "Then please Relog with atleast 3 Inventory spaces to be Rewarded!");
    }

    /*
     * checks which reward player will receive I.e Low,Med or High depending on
     * how many FightPit Games they have won. checks which message to be sent
     */
    private void giveReward(Player p) {
        p.fightPitsWins++;
        p.getFrames().sendChatMessage(0, "Congratulations! You have won the game of Fight Pits!");
        if (p.getInventory().getFreeSlots() <= 3) {
            invoSpace(p);
            return;
        }
        p.needPitsReward = false;
        if (p.fightPitsWins >= 1 && p.fightPitsWins <= 9) {
            p.getInventory().addItem(rewardsLow(), 1);
            p.getInventory().addItem(6529, 100);
            p.getInventory().addItem(995, 100000);
            lowMessage(p);
        } else if (p.fightPitsWins >= 10 && p.fightPitsWins <= 49) {
            p.getInventory().addItem(rewardsMed(), 1);
            p.getInventory().addItem(6529, 500);
            p.getInventory().addItem(995, 500000);
            medMessage(p);
        } else if (p.fightPitsWins >= 50) {
            p.getInventory().addItem(rewardsHigh(), 1);
            p.getInventory().addItem(6529, 1000);
            p.getInventory().addItem(995, 1000000);
            HighMessage(p);
        }
    }
}
