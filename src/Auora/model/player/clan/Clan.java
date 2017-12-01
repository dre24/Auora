package Auora.model.player.clan;

import Auora.model.World;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.util.Misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author crezzy
 */
public class Clan {
    private String roomName;
    private String roomOwner;
    private int joinReq = -1;
    private int talkReq = -1;
    private int kickReq = 7;
    private HashMap<String, Byte> ranks;
    private transient List<Player> members;
    private transient boolean lootsharing;

    public Clan() {
    }

    public Clan(String owner, String name) {
        this.roomName = name;
        this.roomOwner = owner;
        setTransient();
    }

    public void setTransient() {
        setLootsharing(false);
        if (kickReq == 0) {
            kickReq = 7;
        }
        if (members == null) {
            this.members = new ArrayList<Player>();
        }
        if (ranks == null) {
            this.ranks = new HashMap<String, Byte>();
        }
    }

    public String getName() {
        return roomName;
    }

    public void setName(String name) {
        this.roomName = name;
    }

    public String getOwner() {
        return roomOwner;
    }

    public void rankUser(String name, int rank) {
        if (!ranks.containsKey(name)) {
            if (rank != -1)
                ranks.put(name, (byte) rank);
        } else {
            if (rank == -1) {
                ranks.remove(name);
            } else {
                ranks.remove(name);
                ranks.put(name, (byte) rank);
            }
        }
    }

    public int getRank(String user) {
        user = Misc.formatPlayerNameForProtocol(user);
        if (ranks.containsKey(user)) {
            return ranks.get(user);
        }
        return 0;
    }

    public Byte getRank(Player player) {
        if (player.getUsername().equals(roomOwner)) {
            return 7;
        } else if (player.getStaffRights() == StaffRights.ADMINISTRATOR || player.getStaffRights() == StaffRights.OWNER || player.getStaffRights() == StaffRights.GLOBAL_ADMIN || player.getStaffRights() == StaffRights.FORUM_ADMIN) {
            return 127;
        } else if (ranks.containsKey(player.getUsername())) {
            return ranks.get(player.getUsername());
        }
        return -1;
    }

    public boolean canJoin(Player player) {
        byte rank = getRank(player);
        return rank >= joinReq;
    }

    public boolean canTalk(Player player) {
        byte rank = getRank(player);
        return rank >= talkReq;
    }

    public void toggleLootshare() {
        lootsharing = !lootsharing;
        String message = "";
        if (lootsharing) {
            message = "Lootshare has been enabled.";
        } else {
            message = "Lootshare has been disabled.";
        }
        for (Player pl : members) {
            pl.getFrames().sendChatMessage(0, message);
            pl.getFrames().sendConfig(1083, lootsharing ? 1 : 0);
        }
    }

    public void addMember(Player member) {
        members.add(member);
    }

    public List<Player> getMembers() {
        return members;
    }

    public void removeMember(Player player) {
        members.remove(player);
    }

    public HashMap<String, Byte> getRanks() {
        return ranks;
    }

    public boolean isLootsharing() {
        return lootsharing;
    }

    public void setLootsharing(boolean lootsharing) {
        this.lootsharing = lootsharing;
    }

    public int getTalkReq() {
        return talkReq;
    }

    public void setTalkReq(int talkReq) {
        this.talkReq = talkReq;
    }

    public int getJoinReq() {
        return joinReq;
    }

    public void setJoinReq(int joinReq) {
        this.joinReq = joinReq;
    }

    public int getKickReq() {
        return kickReq;
    }

    public String rankName(int r) {
        String rank = "";
        switch (r) {
            case -1:
                rank = "Anyone";
                break;
            case 0:
                rank = "Any Friends";
                break;
            case 1:
                rank = "Recruit+";
                break;
            case 2:
                rank = "Corporal+";
                break;
            case 3:
                rank = "Sergeant+";
                break;
            case 4:
                rank = "Lieutenant+";
                break;
            case 5:
                rank = "Captain+";
                break;
            case 6:
                rank = "General+";
                break;
            case 7:
                rank = "Only me";
                break;
        }
        return rank;
    }

    public Player getPlayerByName(String name) {
        name = name.replaceAll(" ", "_");
        for (Player p : World.getPlayers()) {
            if (p.getUsername().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

}
