package Auora.model.player.account;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.util.Logger;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Leon 4/2/2017
 */

public class ServerServices {

    public static void initiateServices() {
        //	tickLeaderboards();
        tickPlayerSaving();
        tickPlayerBackup();
        tickMessage();

    }

    public static void tickPlayerSaving() {
        GameServer.getWorldExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (World.saveTimer > 0) {
                    World.saveTimer--;
                }
                //System.out.println(World.saveTimer);
                if (World.saveTimer == 0) {
                    savePlayers(false);
                    World.saveTimer = 90; // 1min 30
                    Logger.log(this, "All player files have been saved automatically.");
                }
            }
        }, 1000, 1000);
    }

    public static void tickPlayerBackup() {
        GameServer.getWorldExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (World.backupTimer > 0) {
                    World.backupTimer--;
                }
                if (World.backupTimer == 0) {
                    savePlayers(true);
                    for (Player all : World.getPlayers()) {
                        if (all == null)
                            continue;
                        //all.sendMessage("<col=ff0000>Player files have automatically been backed up by the server.");
                    }
                    Logger.log(this, "All player files have been backed up automatically.");
                    World.backupTimer = 3600; // 1 hr
                }
            }
        }, 1000, 1000);
    }

    public static void savePlayers(boolean backup) {
        DateFormat df = new SimpleDateFormat("dd-MM-yy");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        File bkup = new File("./backups/" + date);
        if (!bkup.exists()) {
            try {
                bkup.mkdirs();
            } catch (SecurityException e) {
                System.out.println("Unable to create directory for player data!");
            }
        }
        int amountInDir = bkup.list().length;
        World.saveInt = amountInDir + 1;
        for (Player newSave : World.getPlayers()) {
            if (newSave == null)
                continue;
            PlayerSaving.save(newSave, backup);
        }
    }

	

	/*public static void tickLeaderboards() {
		GameServer.getWorldExecutor().schedule(new Task() {
			@Override
			public void run() {
				if (World.mysql_logging_timer > 0) {
					World.mysql_logging_timer--;
				}
				if (World.mysql_logging_timer == 0) {
					for (Player d : World.getPlayers()) {
						try {
							Leaderboards.update(d);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					World.mysql_logging_timer = 180;
				}
			}
		}, 1000, 1000);
	}*/

    public static void tickMessage() {
        GameServer.getWorldExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (World.message_timer > 0) {
                    World.message_timer--;
                }
                if (World.message_timer == 0) {
                    for (Player d : World.getPlayers()) {
                        if (d == null)
                            continue;
                        d.sm("<col=0000FF>[<img=1>Auora]: <col=FF0000>Vote for 10 VotePoints and 10m Cash at www.Auora614.org/vote");
                        d.sm("<col=FF0000>Donate at www.Auora614.org/store for extra benefits and features!");
                    }
                    World.message_timer = 300;
                }
            }
        }, 1000, 1000);
    }


}
