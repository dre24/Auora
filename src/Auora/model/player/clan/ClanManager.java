package Auora.model.player.clan;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;
import Auora.util.Logger;
import Auora.util.Misc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author crezzy
 */
public class ClanManager {

    private static Map<String, Clan> clans;

    public ClanManager() {
        Logger.log("ClanManager", "Loading clans....");
        try {
            clans = XMLHandler.fromXML("./Data/clans.xml");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (clans == null) {
            clans = new HashMap<String, Clan>();
        }
        for (Map.Entry<String, Clan> entries : clans.entrySet()) {
            entries.getValue().setTransient();
        }
        Logger.log("ClanManager", "Loaded " + clans.size() + " clans.");
    }

    public static void refresh(Clan clan) {
        for (Player p : clan.getMembers()) {
            if (p == null) {
                continue;
            }
            p.getFrames().sendString("Clan Chat - " + clan.getMembers().size() + " members", 589, 10);
            ClanPacket.sendClanList(p, clan);
        }
    }

    public void joinClan(final Player p, final String user, boolean loginJoin) {
        if (!loginJoin) {
            p.getFrames().sendChatMessage(0, "Attempting to join channel...");
        }
        if (p == null)
            return;
        final Clan clan = clans.get(Misc.formatPlayerNameForProtocol(user));
        if (clan == null) {
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {
                    if (!loginJoin) {
                        p.getFrames().sendChatMessage(0, "Then channel you tried to join does not exist.");
                    }
                    this.stop();
                }
            }, 1, 0);
            return;
        }
        if (clan.getMembers().contains(p)) {
            return;
        }
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                if (clan.canJoin(p)) {
                    //player.getSettings().setCurrentClan(clan);
                    p.currentlyTalkingIn = clan;
                    clan.addMember(p);
                    refresh(clan);
                    p.getFrames().sendString("Clan Chat - " + clan.getMembers().size() + " members", 589, 10);
                    p.getFrames().sendConfig(1083, clan.isLootsharing() ? 1 : 0);
                    p.lastClan = clan.getOwner();
                    if (!loginJoin) {
                        p.getFrames().sendChatMessage(0, "Now talking in the clan channel " + Misc.formatPlayerNameForDisplay(clan.getName()));
                        p.getFrames().sendChatMessage(0, "To talk, start each line of chat with the / symbol.");
                    }
                    p.currentClan = user;
                } else {
                    if (!loginJoin) {
                        p.getFrames().sendChatMessage(0, "You don't have a high enough rank to join this channel.");
                    }
                }
                this.stop();
            }
        }, 1, 0);
    }

	/*public void saveClan(Clan c) {
        try {
		XMLHandler.toXML("Data/clans.xml", c);
		} catch(Exception e) {
			
		}
	}*/

    public Clan getClan(String s) {
        return clans.get(Misc.formatPlayerNameForProtocol(s));
    }

    public void saveClans() {
        try {
            XMLHandler.toXML("./Data/clans.xml", clans);
        } catch (Exception e) {

        }
    }

    public Clan getClans(String s) {
        return clans.get(Misc.formatPlayerNameForProtocol(s));
    }

    public Map<String, Clan> getClans() {
        return clans;
    }

    public void createClan(Player p, String name) {
        if (name.equals("")) {
            return;
        }
        String user = Misc.formatPlayerNameForProtocol(p.getUsername());
        //child 32 = coinshare below
        if (!clans.containsKey(user)) {
            Clan clan = new Clan(user, name);
            clans.put(Misc.formatPlayerNameForProtocol(p.getUsername()), clan);
            refresh(clan);
            saveClans();
        } else {
            Clan clan = clans.get(user);
            clan.setName(name);
            refresh(clan);
        }
    }

    public void destroy(Player player, String username) {
        Clan c = clans.get(Misc.formatPlayerNameForProtocol(username));
        if (c != null) {
            for (Player p : c.getMembers()) {
                if (p == null) {
                    continue;
                }
                p.currentlyTalkingIn = null;
                ClanPacket.sendClanList(p, null);
                p.getFrames().sendString("Clan Chat", 589, 10);
            }
        }
        player.getFrames().sendString("Chat disabled", 590, 22);
        clans.remove(username);
        saveClans();
    }

    public void leaveClan(Player player) {
        player.lastClan = null;
        Clan c = player.currentlyTalkingIn;
        if (c != null) {
            c.removeMember(player);
            player.currentlyTalkingIn = null;
            refresh(c);
            ClanPacket.sendClanList(player, null);
        }
        player.getFrames().sendConfig(1083, 0);
        player.getFrames().sendString("Clan Chat", 589, 10);
    }

    public void kickMember(Player player) {
        Clan c = player.currentlyTalkingIn;
        if (c != null) {
            c.removeMember(player);
            player.currentlyTalkingIn = null;
            refresh(c);
            ClanPacket.sendClanList(player, null);
        }
        player.getFrames().sendConfig(1083, 0);
        player.getFrames().sendChatMessage(0, "You have been kicked from the channel.");
    }

    public void rankMember(Player player, String user, int rank) {
        Clan c = clans.get(player.getUsername());
        if (c == null) {
            return;
        }
        if (rank == 0)
            rank = -1;
        c.rankUser(user, rank);
        refresh(c);
        //saveClan(c);
    }

    public int getRank(Player owner, Player p) {
        Clan c = clans.get(owner.getUsername());
        if (c == null) {
            return -1;
        }
        return (int) c.getRank(p);
    }

    public int getRank(Player owner, String member) {
        Clan c = clans.get(owner.getUsername());
        if (c == null) {
            return -1;
        }
        return c.getRank(member);
    }

    public String getClanName(String user) {
        Clan c = clans.get(user);
        if (c == null) {
            return "Chat disabled";
        }
        return c.getName();
    }

    public void sendClanMessage(Player player, String text) {
        Clan c = player.currentlyTalkingIn;
        if (c == null) {
            return;
        }
        for (Player pl : c.getMembers()) {
            if (pl.getIndex() == player.getIndex()) {
                continue;
            }
            ClanMessage.sendClanChatMessage(player, pl, c.getName(), c.getOwner(), text);
        }
        ClanMessage.sendClanChatMessage(player, null, c.getName(), c.getOwner(), text);
    }

    public void sendDiceMsg(Player player, int roll) {
        Clan c = player.currentlyTalkingIn;
        if (c == null) {
            return;
        }
        for (Player pl : c.getMembers()) {
            if (pl == null)
                continue;
            pl.sm("Clan-mate <col=ff0000>" + Misc.formatPlayerNameForDisplay(player.getUsername()) + "</col> rolled <col=ff0000>" + roll + "</col> on the dice!");
        }

    }

    public void toggleLootshare(Player player) {
        Clan c = clans.get(player.getUsername());
        if (c != null) {
            c.toggleLootshare();
        } else {
            player.getFrames().sendChatMessage(0, "You don't have a clan to active lootshare with.");
        }
    }

    public void initiateClanName(Player player) {
        player.clanOption = true;

        Object[] obj = null;
        obj = new Object[]{"Enter clan prefix:"};
        player.getFrames().sendClientScript(109, obj, "s");
    }

    public void finalizeClanName(Player player, String name) {
        Clan c = clans.get(player.getUsername());
        if (c == null)
            return;
        c.setName(name);
        refresh(c);
        player.getFrames().sendString(Misc.formatPlayerNameForDisplay(name), 590, 22);
        player.clanOption = false;
        //saveClan(c);
    }

}
