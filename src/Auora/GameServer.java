package Auora;

import com.motivoters.motivote.service.MotivoteRS;
import Auora.events.GameLogicTaskManager;
import Auora.events.TaskManager;
import Auora.model.GlobalDropManager;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.model.player.account.PlayerSaving;
import Auora.model.player.account.ServerServices;
import Auora.model.player.content.BountyHunterManager;
import Auora.net.Packets;
import Auora.net.mysql.DatabaseInformationStore;
import Auora.net.mysql.DatabaseInformationVoting;
import Auora.net.mysql.MySQLDatabaseConfiguration;
import Auora.net.mysql.ThreadedSQL;
import Auora.network.ChannelBinder;
import Auora.rscache.Cache;
import Auora.service.logic.LogicService;
import Auora.service.login.LoginService;
import Auora.skills.combat.MagicManager;
import Auora.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameServer {

    public final static MotivoteRS motivote = new MotivoteRS("ss614", "901fe698becb52c87b31b183f47c63cf");
    private static final TaskManager entityExecutor = new TaskManager();
    private static final TaskManager worldExecutor = new TaskManager();
    public static int updateTime = -1;
    public static MYSQL database = null;
    public static int worldId = 1;
    public static int voteDisabled = 0;
    public static boolean systemFlood = false;
    public static List<String> onlinePlayers = new ArrayList<String>();
    public static long tickTimer;
    public static double minFloat = 0;
    public static double maxFloat = 0;
    public static long UPTIME;
    public static boolean weekendBonus = false;
    private static ThreadedSQL voting_sql = null;
    private static ThreadedSQL store_sql = null;

    public GameServer() {
        UPTIME = System.currentTimeMillis();
        voteDisabled = 0; // change to 1 to disable
        updateTime = 0;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                for (Player p : World.getPlayers()) {
                    if (p != null) {
                        if (p.getPriceCheck().getContainer() != null) {
                            p.getPriceCheck().close();
                        }
                        if (p.getTradeSession() != null) {
                            p.getTradeSession().tradeFailed();
                        }
                        if (p.getTradePartner() != null) {
                            p.getTradePartner().getTradeSession().tradeFailed();
                        }
                        PlayerSaving.save(p, false);
                    }
                }
            }
        });
        GameSettings.load();
        // Voting SQL
        MySQLDatabaseConfiguration voting = new MySQLDatabaseConfiguration();
        voting.setHost(DatabaseInformationVoting.host);
        voting.setPort(DatabaseInformationVoting.port);
        voting.setUsername(DatabaseInformationVoting.username);
        voting.setPassword(DatabaseInformationVoting.password);
        voting.setDatabase(DatabaseInformationVoting.database);
        voting_sql = new ThreadedSQL(voting, 4);

        // Store SQL
        MySQLDatabaseConfiguration store = new MySQLDatabaseConfiguration();
        store.setHost(DatabaseInformationStore.host);
        store.setPort(DatabaseInformationStore.port);
        store.setUsername(DatabaseInformationStore.username);
        store.setPassword(DatabaseInformationStore.password);
        store.setDatabase(DatabaseInformationStore.database);
        store_sql = new ThreadedSQL(store, 4);

        /*Logger.log(this, "Loading pvm...");
        new LoadNPCList();*/

        Logger.log(this, "Cache Loading...");
        new Cache();
        Logger.log(this, "MapData Loading...");
        new MapData();
        Logger.log(this, "Packets Loading...");
        new Packets();
        Logger.log(this, "magic spells Loading...");
        new MagicManager();
        Logger.log(this, "Custom drops Loading...");
        new GlobalDropManager();
        Logger.log(this, "World Initiating...");
        new World();
        worldId = 1;
        Logger.log(this, "Game Logic TaskManager Initiating...");
        new GameLogicTaskManager();
        Logger.log(this, "Game Logic Thread Initiating...");
        try {
            LogicService.getInstance().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.log(this, "Starting login service...");
        LoginService.getSingleton().init();
        Logger.log(this, "Starting GameServer Channel Handler...");
        ChannelBinder binder = new ChannelBinder(43594);
        binder.bind();
        Logger.log(this, "GameServer Finished Loading.");
        ServerServices.initiateServices();
        Logger.log(this, "Server Services have been initialised.");
        BountyHunterManager.init();
        /*MapViewer viewer = null;
        try {
			viewer = new MapViewer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(Player p : World.getPlayers()){
        viewer.add(new JMapPlayer(p));
        viewer.getMap().add(new JMapPlayer(p));
        
        }*/


    }

    public static void main(String[] args) {
        try {
            System.setErr(new PrintStream(new ErrorFile("errorlogs", "ErrorLog"), true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new GameServer();
    }

    public static void close() {
        System.exit(0);
    }

    public static ThreadedSQL getVotingPool() {
        return voting_sql;
    }

    public static final boolean restart(String message, long delay) {
        for (Player player : World.getPlayers()) {
            synchronized (player) {
                player.getFrames().sendChatMessage(0, message);
            }
        }
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                try {//that aint debugf idgaf 
                    if (Misc.isWindows()) {
                        File file = new File("BootServer.bat");
                        Runtime.getRuntime().exec("cmd.exe /C start " + file.getPath());
                    } else {
                        Runtime.getRuntime().exec(new String[]{"cd Dropbox", "cd Dem*", "./run.sh", ""});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.exit(1);
            }
        }, delay, TimeUnit.MILLISECONDS);
        return true;
    }

    public static TaskManager getWorldExecutor() {
        return worldExecutor;
    }

    public static TaskManager getEntityExecutor() {
        return entityExecutor;
    }

    public static ThreadedSQL getStorePool() {
        return store_sql;
    }

    /**
     * @return the savetaskmanager
     */
    /*
     * public static SaveTaskManager getSaveTaskmanager() { return
	 * saveTaskManager; }
	 */
}