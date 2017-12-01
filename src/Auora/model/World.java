package Auora.model;

import Auora.GameServer;
import Auora.GameSettings;
import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.events.Task;
import Auora.model.minigames.FightPits;
import Auora.model.npc.Npc;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerSaving;
import Auora.model.player.clan.ClanManager;
import Auora.model.player.logs.Logs;
import Auora.model.route.CoordinateEvent;
import Auora.model.shops.ShopManager;
import Auora.net.codec.ConnectionHandler;
import Auora.util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {


    public static int saveTimer = 90;
    public static int backupTimer = 1500;
    public static int saveInt = 0;

    public static boolean[] playerUpdates = new boolean[2048];

    public static int serverKills;
    public static long before, after;
    public static ClanManager clanManager;
    public static boolean antiflood = false;
    public static int mysql_logging_timer = 60;
    public static int stall_timer = 60;
    public static int hit_timer = 11;

    public static int configuration_timer = 5;
    public static int seconds, minutes, hours, days;
    public static long currentServerTime;
    public static List<RSTile> restrictedTiles = new ArrayList<RSTile>();
    public static int message_timer;
    private static EntityList<Player> players;
    private static EntityList<Npc> npcs;
    private static HashMap<Integer, Long> ips;
    private static ShopManager shopManager = new ShopManager();
    private static GlobalItemManager globalItemManager;
    private static GlobalDropManager globaldropmanager;
    private static String EventipList = "./Data/eventips.txt";
    private static String Flooders = "./Data/Flooders.txt";
    private static String GlitchipList = "./Data/glitchlist.txt";
    private static String StarteripList = "./Data/Starter_Ips.txt";
    private static String BannedipList = "./Data/bannedips.txt";
    private static String PermbannedipList = "./Data/IpBans.txt";
    private static String IpMuteList = "./Data/IpMutes.txt";

    // 0 - north, 1 - north east, 2 - east, 3 - south east, 4 - south, 5 - south west, 6 - west, 7 - north west
    public World() {
        clanManager = new ClanManager();
        shopManager.loadShops();
        players = new EntityList<Player>(GameSettings.MAX_AMOUNT_OF_PLAYERS);
        npcs = new EntityList<Npc>(GameSettings.MAX_AMOUNT_OF_NPCS);
        ips = new HashMap<Integer, Long>(GameSettings.MAX_AMOUNT_OF_IPS);
        globalItemManager = new GlobalItemManager();

        //^^^^^^^^^^^^^^^ DONT CHANGE NPC POSITION KEEP AT INDEX 1 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        getNpcs().add(new Npc((short) 605, RSTile.createRSTile(3160, 3481), 0, 0, 0, 0, 0)); //GE
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3164, 3488), 0, 0, 0, 0, 4)); //GE
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3165, 3488), 0, 0, 0, 0, 4));

        //bh
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3188, 3696), 0, 0, 0, 0, 6));//GE

        // fairy at home
        getNpcs().add(new Npc((short) 534, RSTile.createRSTile(3173, 3493), 0, 0, 0, 0, 6)); //GE


        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3191, 3443), 0, 0, 0, 0, 6)); //GE
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3191, 3441), 0, 0, 0, 0, 6));
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3191, 3439), 0, 0, 0, 0, 6));
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3191, 3437), 0, 0, 0, 0, 6));//GE
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3191, 3435), 0, 0, 0, 0, 6));

        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3180, 3444), 0, 0, 0, 0, 2));//GE
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3180, 3442), 0, 0, 0, 0, 2));
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3180, 3440), 0, 0, 0, 0, 2));//GE
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3180, 3438), 0, 0, 0, 0, 2));
        getNpcs().add(new Npc((short) 494, RSTile.createRSTile(3180, 3436), 0, 0, 0, 0, 2));//GE

        // getNpcs().add(new Npc((short) 599, RSTile.createRSTile(3173, 3493),
        // 0, 0, 0, 0, 0));
        getNpcs().add(new Npc((short) 747, RSTile.createRSTile(2287, 4695), 0, 0, 0, 0, 6));//done
        getNpcs().add(new Npc((short) 400, RSTile.createRSTile(2287, 4689), 0, 0, 0, 0, 6));//done
        getNpcs().add(new Npc((short) 659, RSTile.createRSTile(3161, 3481), 0, 0, 0, 0, 0)); //done
        //getNpcs().add(new Npc((short) 520, RSTile.createRSTile(3160, 3481), 0, 0, 0, 0, 0)); //done
        getNpcs().add(new Npc((short) 747, RSTile.createRSTile(3155, 3495, 2), 0, 0, 0, 0, 3));//done
        getNpcs().add(new Npc((short) 520, RSTile.createRSTile(3168, 3481), 0, 0, 0, 0, 0));//done
        getNpcs().add(new Npc((short) 400, RSTile.createRSTile(3156, 3496, 2), 0, 0, 0, 0, 3));//done
        getNpcs().add(new Npc((short) 6390, RSTile.createRSTile(3156, 3486), 0, 0, 0, 0, 0)); //done
        getNpcs().add(new Npc((short) 8542, RSTile.createRSTile(3157, 3497, 2), 0, 0, 0, 0, 3)); //done
        getNpcs().add(new Npc((short) 407, RSTile.createRSTile(3159, 3499, 2), 0, 0, 0, 0, 3)); //done
        getNpcs().add(new Npc((short) 2253, RSTile.createRSTile(3158, 3498, 2), 0, 0, 0, 0, 3)); //done
        getNpcs().add(new Npc((short) 546, RSTile.createRSTile(3171, 3498, 2), 0, 0, 0, 0, 5)); //done

        getNpcs().add(new Npc((short) 581, RSTile.createRSTile(3166, 3489), 0, 0, 0, 0, 2));//done
        getNpcs().add(new Npc((short) 583, RSTile.createRSTile(3166, 3490), 0, 0, 0, 0, 2));//done
        getNpcs().add(new Npc((short) 8725, RSTile.createRSTile(3169, 3481), 0, 0, 0, 0, 0));//done
        getNpcs().add(new Npc((short) 550, RSTile.createRSTile(3172, 3497, 2), 0, 0, 0, 0, 5)); //done
        getNpcs().add(new Npc((short) 2580, RSTile.createRSTile(3163, 3489), 0, 0, 0, 0, 6));
        getNpcs().add(new Npc((short) 2538, RSTile.createRSTile(3166, 3487, 2), 0, 0, 0, 0, 5)); //done
        getNpcs().add(new Npc((short) 520, RSTile.createRSTile(3174, 3495, 2), 0, 0, 0, 0, 5)); //done


        // Shops @ ::shops
        getNpcs().add(new Npc((short) 747, RSTile.createRSTile(3161, 3499, 2), 0, 0, 0, 0, 1));
        //donator shop
        getNpcs().add(new Npc((short) 8608, RSTile.createRSTile(3173, 3486, 0), 0, 0, 0, 0, 6));


        SpawnedObjectsManager.loadNewObjects();
        
    }

    public static EntityList<Player> getPlayers() {
        synchronized (players) {
            return players;
        }
    }

    // want me to fix that
    public static int getIdFromName(String playerName) {
        playerName.replaceAll("_", " ");
        for (Player p : players) {
            if (p == null) {
                continue;
            }
            if (Misc.formatPlayerNameForProtocol(p.getUsername())
                    .equalsIgnoreCase(Misc.formatPlayerNameForProtocol(playerName))) {
                return p.getIndex();
            }
        }
        return 0;
    }

    public static GlobalItemManager getGlobalItemsManager() {
        if (globalItemManager == null)
            globalItemManager = new GlobalItemManager();
        return globalItemManager;
    }

    public static GlobalDropManager getGlobalDropManager() {
        return globaldropmanager;
    }

    public static void registerConnection(ConnectionHandler p, String serial) {
        if (players.add(p.getPlayer())) {
            p.getPlayer().LoadPlayer(p);
            String name = Misc.formatPlayerNameForDisplay(p.getPlayer().getUsername().replaceAll("_", " "));
            System.out.println("[" + name + "] has logged in. Players Online - " + getPlayers().size());
            String ip = "" + p.getPlayer().getConnection().getChannel().getRemoteAddress();
            ip = ip.replaceAll("/", "");
            ip = ip.replaceAll(" ", "");
            ip = ip.substring(0, ip.indexOf(":"));
            p.getPlayer().setIpAddress(ip);
            p.getPlayer().setSerialAddress(serial);
            Logs.log(p.getPlayer(), "connections",
                    new String[]{
                            "[LOGIN]",
                            "Ip: " + ip + "",
                            "Serial Address: " + serial + ""
                    });
        }
    }

    public static int getStaffOnline() {
        int online = 0;
        for (Player d : getPlayers()) {

            if (d == null)
                continue;
            if (d.getStaffRights() != StaffRights.PLAYER) {
                online += 1;
            }
        }
        return online;
    }

    public static void updateUptime() {
        long milliseconds = System.currentTimeMillis() - GameServer.UPTIME;
        seconds = (int) (milliseconds / 1000) % 60;
        minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        // System.out.println("Uptime" +seconds+ "second" +minutes+ "minutes"
        // +hours+ "hours"); // for debugging purposes
    }

    public static boolean EventIpsContain(String host) {
        BufferedReader list = null;
        try {
            list = new BufferedReader(new FileReader(EventipList));
        } catch (Exception e) {
            System.out.println("Event IP list error.");
            return false;
        }
        String line = null;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Event IP list.");
        }
        return false;
    }

    public static boolean FloodersStartsWith(String name) {
        BufferedReader list = null;
        try {
            list = new BufferedReader(new FileReader(Flooders));
        } catch (Exception e) {
            System.out.println("Flooders list error.");
            return false;
        }
        String line = null;
        try {
            while ((line = list.readLine()) != null) {
                if (line.startsWith(name)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Flooder list.");
        }
        return false;
    }

    public static boolean Eventip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(EventipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {

                }
            }

        }
        return false;
    }

    public static boolean GlitchIpsContain(String username) {
        BufferedReader list = null;
        try {
            list = new BufferedReader(new FileReader(GlitchipList));
        } catch (Exception e) {
            System.out.println("Glitch IP list error.");
            return false;
        }
        String line = null;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Glitch IP list.");
        }
        return false;
    }

    public static boolean Glitchip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(GlitchipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    public static boolean StarterIpsContain(String host) {
        BufferedReader list = null;
        try {
            list = new BufferedReader(new FileReader(StarteripList));
        } catch (Exception e) {
            System.out.println("Starter IP list error.");
            return false;
        }
        String line = null;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Starter IP list.");
        }
        return false;
    }

    public static boolean Starterip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(StarteripList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    public static boolean BannedIpsContain(String host) {
        BufferedReader list = null;
        try {
            list = new BufferedReader(new FileReader(BannedipList));
        } catch (Exception e) {
            System.out.println("[Banned ips]: Failed to load bannedips.txt. File might be missing.");
            return false;
        }
        String line = null;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Banned ips]: Error with loading banned ips!");
        }
        return false;
    }

    public static boolean Banip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(BannedipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    public static boolean PermbannedIpsContain(String host) {
        BufferedReader list = null;
        try {
            list = new BufferedReader(new FileReader(PermbannedipList));
        } catch (Exception e) {
            System.out.println("[Banned ips]: Failed to load bannedips.txt. File might be missing.");
            return false;
        }
        String line = null;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Banned ips]: Error with loading banned ips!");
        }
        return false;
    }

    public static boolean IpMuteListContain(String host) {
        BufferedReader list = null;
        try {
            list = new BufferedReader(new FileReader(IpMuteList));
        } catch (Exception e) {
            System.out.println("[Ip Mutes]: Failed to load IpMutes.txt. File might be missing.");
            return false;
        }
        String line = null;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Ip Mutes] Error with loading Ip Mutes!");
        }
        return false;
    }

    public static boolean Destroyip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(PermbannedipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    public static boolean Muteip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(IpMuteList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    public static Player getPlayerByName(String name) {
        name = name.replaceAll(" ", "_");
        for (Player p : getPlayers()) {
            if (p.getUsername().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public static void unRegisterConnection(final ConnectionHandler p) {
        final Player player = p.getPlayer();
        if (player.getTradeSession() != null) {
            player.getTradeSession().tradeFailed();
        }
        if (player.getPriceCheck().pricecheckinv.size() > 0) {
            player.getPriceCheck().close();
        }
       /* try {
            Leaderboards.update(player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


        FightPits pits = new FightPits();
        pits.handleLogOutPits(player);
        String name = Misc.formatPlayerNameForDisplay(p.getPlayer().getUsername().replaceAll("_", " "));
        p.getPlayer().fletchingLog = 0;

        if (p.getPlayer().ipsOnline > 0) {
            p.getPlayer().ipsOnline -= 1;
        }
        if (p.getPlayer().getPriceCheck().getContainer() != null) {
            p.getPlayer().getPriceCheck().close();
        }
        if (p.getPlayer().currentlyTalkingIn != null) {
            World.clanManager.leaveClan(p.getPlayer());
        }
        Logs.log(p.getPlayer(), "connections",
                new String[]{
                        "[LOGOUT]",
                        "Ip: " + p.getPlayer().getIpAddress() + "",
                        "Serial Address: " + p.getPlayer().getSerialAddress() + ""
                });
        System.out.println("[" + name + "] has logged out.");
        PlayerSaving.save(player, false);
        if (player == null) {
            player.getConnection().getChannel().close();
            return;
        }
        if (player.getSkills().xLogProtection || player.getSkills().getHitPoints() < 1
                || player.getCombat().combatWithDelay > 0 || player.getCombat().delay > 0) {
            GameLogicTaskManager.schedule(new GameLogicTask() {

                public void run() {
                    removePlayer(player);
                    this.stop();

                }

            }, 60, 0);
        } else {
            removePlayer(player);
        }
    }

    public static ClanManager getClanManager() {
        return clanManager;
    }

    public static void removePlayer(Player p) {
        PlayerSaving.save(p, false);
        p.getFrames().updateFriendsList(false);
        GameServer.onlinePlayers.remove(p.getUsername());
        p.setOnline(false);
        players.remove(p);
        for (Player p2 : players) {
            if (p2.getFriends().contains(Misc.formatPlayerNameForDisplay(p.getUsername()))) {
                p2.updateFriendStatus(Misc.formatPlayerNameForDisplay(p.getUsername()), (short) 0, false);
            }
        }
        if (p.getConnection().getChannel() != null || !p.getConnection().isDisconnected()) {
            p.getConnection().getChannel().close();
           // System.out.println("")
        }
        p = null;
    }

    public static boolean isOnline(String Username) {
        for (Player p : players)
            if (p.getUsername().equals(Username))
                if (p.isOnline())
                    return true;

        return false;
    }

    public static boolean isOnList(String Username) {
        for (Player p : players)
            if (p.getUsername().equals(Username))
                return true;

        return false;
    }

    public static boolean isOnList(Player player) {
        return players.contains(player);
    }

    public static EntityList<Npc> getNpcs() {
        synchronized (npcs) {
            return npcs;
        }
    }

    public static HashMap<Integer, Long> getIps() {
        synchronized (ips) {
            return ips;
        }
    }

    /**
     * @return the shopmanager
     */
    public static ShopManager getShopManager() {
        return shopManager;
    }

    public static Object findPlayer(Object name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Send a message to all players online
     *
     * @param message
     */
    public static void sendMessage(String message) {
        for (Player d : World.getPlayers()) {
            if (d == null) {
                continue;
            }
            d.sendMessage(message);
        }
    }

    public static int getPlayersInPvP() {
        int players = 0;
        for (Player d : getPlayers()) {
            if (d == null)
                continue;
            if (d.getCombat() == null) {
                continue;
            }
            if (!d.getCombat().isSafe(d)) {
                players++;
            }
        }
        return players;
    }


    public static void submitCoordinateEvent(final Player mob, final CoordinateEvent coordinateEvent) {
        GameServer.getWorldExecutor().schedule(new Task() {

            @Override
            public void run() {
                //	System.out.println("We are debugging the SubmitCoordEvent: "+mob.attempts);
                if (mob == null || mob.getCoordinateEvent() == null || ++mob.attempts >= 120) {
                    mob.stopCoordinateEvent();
                    this.stop();
                    return;
                }
                if (!mob.getCoordinateEvent().equals(coordinateEvent)) {
                    mob.stopCoordinateEvent();
                    this.stop();
                    return;
                }
                if (!mob.getWalk().isMoving() && mob != null && mob.getCoordinateEvent() != null && mob.getCoordinateEvent().inArea() && mob.getCoordinateEvent() != null) {
                    mob.getCoordinateEvent().execute();
                    mob.stopCoordinateEvent();
                    this.stop();
                }
            }
        }, 0, 100);
    }

    public static void deleteObject(RSTile tile) {
        restrictedTiles.add(tile);
    }

}