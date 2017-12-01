package Auora.model.player.content;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.World;
import Auora.model.player.Player;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Dre on 22.04.2017 11:56.
 */
public class BountyHunterManager {

    /**
     * Bounty hunter settings, times are in seconds. Auora would generate this as milliseconds.
     */

    static final int TARGET_COOLDOWN = 5 * 60; //5 minutes

    /**
     * How much ep should you gain/hour + (2.5x in hot zone). Unsure of the actual eph but it's close to rs
     */
    static final double EPH = 30;
    /**
     * How much should target likelihood increase/tick (10secs) (after likelihood is >60 bh will select a target that
     * has a likelihood of >30 regardless of the combat level (60 is also full bulls-eye (the target icon))
     */
    static final double TARGET_LIKELIHOOD_INCREASE = 300;
    /**
     * 3
     * Where should the interface be placed?
     */
    static final int RESIZABLE_TAB_ID = 16;
    static final int FIXED_TAB_ID = 8;

    private static CopyOnWriteArrayList<Player> handledPlayers = new CopyOnWriteArrayList<>();

    /**
     * Initiate the bounty hunter
     */
    public static void init() {

        GameServer.getWorldExecutor().schedule(new Task() {
            @Override
            public void run() {
                for (Player pl2 : World.getPlayers()) {

                    if (pl2.getLocation().getY() >= 3525 && !pl2.getCombat().isSafe(pl2) && pl2 != null) {
                        handleHunter(pl2);
                        //pl2.getBountyHunter().updateInterfaces();
                    } else {
                        //pl2.getBountyHunter().removeTarget(false);
                        //handledPlayers.remove(pl2);
                        handledPlayers.remove(pl2);
                    }

                }
            }
        }, 10000, 10000);

    }


    /**
     * Add a player to be handled as a bounty hunter
     *
     * @param player the hunter
     */
    public static void addHandledPlayer(Player player) {
        handledPlayers.add(player);
        player.getBountyHunter().enterBountyHunter();
    }

    /**
     * Remove a player from the handled hunters
     *
     * @param player the player
     */
    public static void removeHandledPlayer(Player player) {
        handledPlayers.remove(player);
        player.getBountyHunter().leaveBountyHunter();
    }

    /**
     * Are we handling this player? Used for adding the player to handled players on login etc.
     *
     * @param player the player
     * @return whether the player is being handled as a bounty hunter
     */
    public static boolean handlingPlayer(Player player) {
        return handledPlayers.contains(player);
    }

    /**
     * Process a player playing bounty hunter, (add targets, etc.)
     * Should get ran about once a minute
     *
     * @param player
     */
    private static void handleHunter(Player player) {
        if (player.getLocation().getY() >= 3525) {
            player.getBountyHunter().increaseLikelihood();
            player.getBountyHunter().increaseEP();
            if (!player.getBountyHunter().hasTarget() && !player.getBountyHunter().isOnTargetCooldown()) {
                findTarget(player);
            }
        } else {
            //if (!player.getBountyHunter().hasTarget()){
            player.getBountyHunter().removeTarget(true);
            handledPlayers.remove(player);
            player.getFrames().closeInterface(591);

            //}
        }
        player.getBountyHunter().updateInterfaces();
    }

    /**
     * Attempt to locate a target for the player
     *
     * @param player the player
     */


    private static void findTarget(Player player) {
        for (Player player2 : handledPlayers) {
            if (player2.getBountyHunter().hasTarget() || player2.getCombat().isSafe(player2) || player.getCombat().isSafe(player) || player2.getBountyHunter().isOnTargetCooldown()
                    || player2.getUsername().equalsIgnoreCase(player.getUsername())) continue;
            if (!(player2.getLocation().getY() >= 3525)) {
                // && !player2.getBountyHunter().hasTarget())) {
                handledPlayers.remove(player2);

                continue;
            }
            if (player.getBountyHunter().isSuitableTarget(player2)
                    && player2.getBountyHunter().isSuitableTarget(player)) {
                player.getBountyHunter().assignTarget(player2);
                player2.getBountyHunter().assignTarget(player);

                //player2.getFrames().setHintIcon(10, player, 1, 40497);
                //player.getFrames().setHintIcon(10, player2, 1, 40497);

                return;
            }
        }
    }

}
