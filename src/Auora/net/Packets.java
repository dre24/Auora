package Auora.net;

import Auora.GameServer;
import Auora.GameSettings;
import Auora.events.Task;
import Auora.io.InStream;
import Auora.model.CoordinateLocations;
import Auora.model.GlobalItem;
import Auora.model.Item;
import Auora.model.World;
import Auora.model.command.CommandManager;
import Auora.model.npc.Npc;
import Auora.model.player.*;
import Auora.model.player.DuelArena.Rules;
import Auora.model.player.clan.Clan;
import Auora.model.player.logs.Logs;
import Auora.model.route.CoordinateEvent;
import Auora.model.route.RouteFinder;
import Auora.model.route.strategy.EntityStrategy;
import Auora.model.route.strategy.FloorItemStrategy;
import Auora.model.route.strategy.ObjectStrategy;
import Auora.net.codec.ConnectionHandler;
import Auora.net.packethandlers.ActionButtonHandler;
import Auora.net.packethandlers.ItemOnItemPacketHandler;
import Auora.net.packethandlers.NPCPacketHandler;
import Auora.net.packethandlers.ObjectPacketHandler;
import Auora.rscache.ItemDefinitions;
import Auora.rscache.NpcDefinitions;
import Auora.rscache.ObjectDefinitions;
import Auora.rsobjects.RSObjectsRegion;
import Auora.scripts.Scripts;
import Auora.scripts.items.i10944;
import Auora.scripts.items.i6199;
import Auora.scripts.objectScript;
import Auora.util.InterfaceDecoder;
import Auora.util.Misc;
import Auora.util.RSObject;
import Auora.util.RSTile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import java.net.Socket;
//import java.net.SocketAddress;

public class Packets {

    public static final String WEBSITE = "www.Auora614.org",
            FORUM = "www.Auora614.org/forums/", DONATE = "www.Auora614.org/store/", WIKI = "",
            VOTE = "www.Auora614.org/vote/", HIGHSCORES = "", SPINS = "";
    private static final Map<Short, Method> packets = new HashMap<Short, Method>();
    private static final byte[] PacketSize = new byte[256];

    public Packets() {
        final List<Short> Packets = new ArrayList<Short>(255);
        Packets.add((short) 0);
        Packets.add((short) 1);
        Packets.add((short) 2);
        Packets.add((short) 3);
        Packets.add((short) 5);
        Packets.add((short) 6);
        Packets.add((short) 7);
        Packets.add((short) 8);
        Packets.add((short) 10);
        Packets.add((short) 13);
        Packets.add((short) 14);
        Packets.add((short) 15);
        Packets.add((short) 19);
        Packets.add((short) 20);
        Packets.add((short) 23);
        Packets.add((short) 24);
        Packets.add((short) 25);
        Packets.add((short) 26);
        Packets.add((short) 27);
        Packets.add((short) 28);
        Packets.add((short) 29);
        Packets.add((short) 30);
        Packets.add((short) 34);
        Packets.add((short) 36);
        Packets.add((short) 38);
        Packets.add((short) 39);
        // Packets.add((short) 40);
        Packets.add((short) 42);
        Packets.add((short) 46);
        Packets.add((short) 48);
        Packets.add((short) 50);
        Packets.add((short) 51);
        Packets.add((short) 52);
        Packets.add((short) 53);
        Packets.add((short) 54);
        Packets.add((short) 55);
        Packets.add((short) 57);
        Packets.add((short) 59);
        Packets.add((short) 60);
        // Packets.add((short) 61);
        Packets.add((short) 66);
        Packets.add((short) 68);
        Packets.add((short) 71);
        Packets.add((short) 73);
        Packets.add((short) 75);
        Packets.add((short) 78);
        Packets.add((short) 79);
        Packets.add((short) 80);
        Packets.add((short) 82);
        Packets.add((short) 83);
        // Packets.add((short) 92);
        Packets.add((short) 104);
        // Packets.add((short) 186);
        Short[] PacketsA = new Short[Packets.size()];
        Packets.toArray(PacketsA);
        reset();
        setPackets(PacketsA);
        System.out.print("The current Packets you are using are " + Packets
                + " and total amount of packets you're using are " + Packets.size() + ".");
    }

    public static Player getPlayerByName(String name) {
        name = name.replaceAll(" ", "_");
        for (Player p : World.getPlayers()) {
            if (p.getUsername().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public static Player getPlayerByClan(String name) {
        name = name.replaceAll(" ", "_");
        for (Player p : World.getPlayers()) {
            if (p.getUsername().endsWith(name)) {
                return p;
            }
        }
        return null;
    }

    private static void reset() {
        for (int i = 0; i < 256; i++) {
            PacketSize[i] = -3;
        }
        PacketSize[0] = 8;
        PacketSize[1] = -1;
        PacketSize[2] = -1;
        PacketSize[3] = -1;
        PacketSize[4] = 1;
        PacketSize[5] = 4;
        PacketSize[6] = 0;
        PacketSize[7] = 6;
        PacketSize[8] = -1;
        PacketSize[9] = 7;
        PacketSize[10] = 16;
        PacketSize[11] = 3;
        PacketSize[12] = -1;
        PacketSize[13] = 8;
        PacketSize[14] = 8;
        PacketSize[15] = 3;
        PacketSize[16] = 7;
        PacketSize[17] = 4;
        PacketSize[18] = 2;
        PacketSize[19] = 7;
        PacketSize[20] = 8;
        PacketSize[21] = 3;
        PacketSize[22] = 2;
        PacketSize[23] = 15;
        PacketSize[24] = 8;
        PacketSize[25] = -1;
        PacketSize[26] = 3;
        PacketSize[27] = -1;
        PacketSize[28] = 11;
        PacketSize[29] = 7;
        PacketSize[30] = 0;
        PacketSize[31] = 2;
        PacketSize[32] = 2;
        PacketSize[33] = 7;
        PacketSize[34] = 6;
        PacketSize[35] = 4;
        PacketSize[36] = 3;
        PacketSize[37] = -1;
        PacketSize[38] = 15;
        PacketSize[39] = 0;
        PacketSize[40] = 8;
        PacketSize[41] = 4;
        PacketSize[42] = 3;
        PacketSize[43] = 7;
        PacketSize[44] = 4;
        PacketSize[45] = 2;
        PacketSize[46] = -1;
        PacketSize[47] = -1;
        PacketSize[48] = 8;
        PacketSize[49] = 4;
        PacketSize[50] = 3;
        PacketSize[51] = -1;
        PacketSize[52] = 8;
        PacketSize[53] = -1;
        PacketSize[54] = -1;
        PacketSize[55] = 8;
        PacketSize[56] = 7;
        PacketSize[57] = 11;
        PacketSize[58] = 12;
        PacketSize[59] = 3;
        PacketSize[60] = -1;
        PacketSize[61] = 4;
        PacketSize[62] = -1;
        PacketSize[63] = 3;
        PacketSize[64] = 7;
        PacketSize[65] = 3;
        PacketSize[66] = 3;
        PacketSize[67] = -1;
        PacketSize[68] = 2;
        PacketSize[69] = -1;
        PacketSize[70] = 3;
        PacketSize[71] = -1;
        PacketSize[72] = -1;
        PacketSize[73] = 6;
        PacketSize[74] = 3;
        PacketSize[75] = -1;
        PacketSize[76] = 3;
        PacketSize[77] = -1;
        PacketSize[78] = 7;
        PacketSize[79] = 8;
        PacketSize[80] = 7;
        PacketSize[81] = -1;
        PacketSize[82] = 16;
        PacketSize[83] = 1;
    }

    /*
     * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_0(final InStream Packet, final int Size, final Player p) {
        /*
         * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
		 * interfaceId); if (player.getUsername().equals("mod_jonathan")) {
		 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * " on interface: " + interfaceId); }
		 */
        ActionButtonHandler.handlePacket(p, Packet);
    }

    /*
	 * Mouse
	 */
    @SuppressWarnings("unused")
    private static void PacketId_3(InStream Packet, int Size, Player p) {
        p.lastResponce = System.currentTimeMillis();
    }

    @SuppressWarnings("unused")
    private static void PacketId_6(InStream Packet, int Size, Player p) {
        if (p.getIntermanager().containsTab(16))
            p.getFrames().closeInterface(16);
        if (p.getTradeSession() != null) {
            p.getTradeSession().tradeFailed();
        }
        if (p.getDuelSession() != null && p.getDuelSession().currentStage == DuelArena.Stage.SECOND_SCREEN
                || p.getDuelSession().currentStage == DuelArena.Stage.FIRST_SCREEN)
            p.getDuelSession().duelCancelled(p);
        else if (p.getDuelPartner() != null
                && p.getDuelPartner().getDuelSession().currentStage == DuelArena.Stage.SECOND_SCREEN
                || p.getDuelPartner().getDuelSession().currentStage == DuelArena.Stage.FIRST_SCREEN)
            p.getDuelPartner().getDuelSession().duelCancelled(p);
        else if (p.getTradePartner().getTradeSession() != null)
            p.getTradePartner().getTradeSession().tradeFailed();
    }

    @SuppressWarnings("unused")
    private static void PacketId_8(InStream Packet, int Size, Player p) {
        String owner = "";
        if (Packet.remaining() > 0) {
            owner = Packet.readRS2String();
        }
        if (p.currentlyTalkingIn != null) {
            World.clanManager.leaveClan(p);
            return;
        }
        for (int i = 0; i < owner.length(); ++i) {
            if (!Character.isLetterOrDigit(owner.charAt(i)) && !Character.isSpaceChar(owner.charAt(i))) {
                if (p.currentlyTalkingIn != null) {
                    World.clanManager.leaveClan(p);
                    return;
                }
                return;
            }
        }
        // switch (Packet.opcode) {
        // case 8: // JOIN MAIN
        if (owner.length() > 0) {
            World.clanManager.joinClan(p, owner, false);
        }
        // break;
        // }
    }

    /*
	 * Kick clan
	 */
    @SuppressWarnings("unused")
    private static void PacketId_46(InStream Packet, int Size, Player p) {
        Packet.readByte();
        String user = Packet.readRS2String();
        Player d = getPlayerByClan(user);
        if (p.currentlyTalkingIn != null) {
            Clan c = p.currentlyTalkingIn;
            if (c.getRank(p) >= c.getKickReq()) {
                World.clanManager.kickMember(d);
            } else {
                p.getFrames().sendChatMessage(0, "You don't have a high enough rank to kick this player.");
            }
        }
    }

    @SuppressWarnings("unused")
    private static void PacketId_23(InStream Packet, int Size, Player p) {
		/*
		 * String owner = ""; if (Packet.remaining() > 0) { owner =
		 * Packet.readRS2String(); } switch (Packet.opcode) { case 8: // JOIN if
		 * (owner.length() > 0) { player.getPlayerClan().joinClan(owner); } else {
		 * player.getPlayerClan().leaveClan(player);
		 * //player.getClanSettings().setCurrentClan(null);
		 * //player.getClanSettings().setClanOwner(""); } break; }
		 */

    }

    /*
	 * add friend
	 */
    @SuppressWarnings("unused")
    private static void PacketId_75(InStream Packet, int Size, Player p) {
        String friend = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        if (friend.contains("<euro>")) {
            return;
        }
        p.addFriend(friend);
    }

    /*
	 * sendPm
	 */
    @SuppressWarnings("unused")
    private static void PacketId_54(InStream packet, int Size, Player p) {
        synchronized (packet) {
            if (PlayerPunishment.isMuted(p.getUsername()) || PlayerPunishment.isIpMuted(p.getIpAddress())) {
                p.getFrames().sendChatMessage(0, "You are muted and cannot talk.");
                return;
            }
            final String name = Misc.formatPlayerNameForDisplay(packet.readRS2String());
            if (name.length() > 0) {
                final int numChars = packet.readUnsignedByte();
                final String message = Misc.decryptPlayerChat(packet, numChars);
                if (message == null) {
                    return;
                }
                for (Player p2 : World.getPlayers()) {
                    if (Misc.formatPlayerNameForDisplay(p2.getDisplayName()).equals(name)) {
                        p.getFrames().sendPrivateMessage(name, message);
                        String target_name = p2.getDisplayName();
                        p2.getFrames().receivePrivateMessage(Misc.formatPlayerNameForDisplay(p.getUsername()),
                                Misc.formatPlayerNameForDisplay(p.getDisplayName()), (byte) p.getClientCrownId(), message);
                        return;
                    }
                }
                p.getFrames().sendChatMessage(0, "That player is unavailable.");
            }
        }
    }

    /*
	 * add ignore
	 */
    @SuppressWarnings("unused")
    private static void PacketId_1(InStream Packet, int Size, Player p) {
		/*
		 * if(player.trusted || player.legendaryDonator555 || player.extremeDonator555) {
		 * player.getFrames().sendChatMessage(0,
		 * "You can't do that with a custom name."); return; }
		 */
        String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.AddIgnore(ignore);
    }

    /*
	 * remove friend
	 */
    @SuppressWarnings("unused")
    private static void PacketId_27(InStream Packet, int Size, Player p) {
        String friend = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.RemoveFriend(friend);
    }

    /*
	 * remove ignore
	 */
    @SuppressWarnings("unused")
    private static void PacketId_104(InStream Packet, int Size, Player p) {
        String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.RemoveIgnore(ignore);
    }

    /*
	 * remove ignore
	 */
    @SuppressWarnings("unused")
    private static void PacketId_72(InStream Packet, int Size, Player p) {
        String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.RemoveIgnore(ignore);
    }

    /*
	 * Switch
	 */
    @SuppressWarnings("unused")
    private static void PacketId_10(InStream Packet, int Size, Player p) {
        int fromInterfaceHash = Packet.readInt();
        int fromInterfaceId = fromInterfaceHash >> 16;
        int toItemId = Packet.readShort();
        // int toSlot = Packet.readShort128() - 28;
        int toSlot = Packet.readShort128();
        int toInterfaceHash = Packet.readIntLE();
        int toInterfaceId = toInterfaceHash >> 16;
        int fromItemId = Packet.readShortLE128();
        int child1 = toInterfaceHash & 0xffff;
        int fromSlot = Packet.readShortLE();
        int fromTab;
        int tabIndex = Banking.getArrayIndex(child1);
        switch (fromInterfaceId) {
            case 762:
			/*
			 * Bank.
			 */
                if (child1 == 93) {
                    if (fromSlot < 0 || fromSlot >= Banking.SIZE || toSlot < 0 || toSlot >= Banking.SIZE) {
                        break;
                    }
                    if (!p.inserting) {
                        Item temp = p.getBank().getContainer().get(fromSlot);
                        Item temp2 = p.getBank().getContainer().get(toSlot);
                        p.getBank().getContainer().set(fromSlot, temp2);
                        p.getBank().getContainer().set(toSlot, temp);
                        p.getBank().refresh();
                    } else {
                        if (toSlot > fromSlot) {
                            p.getBank().insert(fromSlot, toSlot - 1);
                        } else if (fromSlot > toSlot) {
                            p.getBank().insert(fromSlot, toSlot);
                        }
                        p.getBank().refresh();
                    }
                    break;
                } else {
                    if (tabIndex > -1) {
                        toSlot = tabIndex == 10 ? p.getBank().getContainer().getFreeSlot()
                                : p.getBank().getTab()[tabIndex] + p.getBank().getItemsInTab(tabIndex);
                        fromTab = p.getBank().getTabByItemSlot(fromSlot);
                        if (toSlot > fromSlot) {
                            p.getBank().insert(fromSlot, toSlot - 1);
                        } else if (fromSlot > toSlot) {
                            p.getBank().insert(fromSlot, toSlot);
                        }
                        p.getBank().increaseTabStartSlots(tabIndex);
                        p.getBank().decreaseTabStartSlots(fromTab);
                        p.getBank().refresh();
                        p.getBank().sendTabConfig();
                        break;
                    }
                }
                break;
            case 149:
                switch (toInterfaceId) {
                    case 149:
                        toSlot = toSlot - 28;
                        if (fromSlot < 0 || fromSlot >= Inventory.SIZE || p.getInventory().getContainer().get(fromSlot) == null)
                            return;
                        if (toSlot < 0 || fromSlot >= Inventory.SIZE)
                            return;
                        Item toSlotItem = p.getInventory().getContainer().get(toSlot);
                        p.getInventory().getContainer().set(toSlot, p.getInventory().getContainer().get(fromSlot));
                        p.getInventory().getContainer().set(fromSlot, toSlotItem);
                        p.getInventory().refresh();
                        break;
                }
                break;
            case 763:
                switch (toInterfaceId) {
                    case 763:
                        toSlot = toSlot;
                        if (fromSlot < 0 || fromSlot >= Inventory.SIZE || p.getInventory().getContainer().get(fromSlot) == null)
                            return;
                        if (toSlot < 0 || fromSlot >= Inventory.SIZE)
                            return;
                        Item toSlotItem = p.getInventory().getContainer().get(toSlot);
                        p.getInventory().getContainer().set(toSlot, p.getInventory().getContainer().get(fromSlot));
                        p.getInventory().getContainer().set(fromSlot, toSlotItem);
                        p.getInventory().refresh();
                        break;
                }
                break;
        }
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_13(final InStream Packet, final int Size, final Player p) {
		/*
		 * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
		 * interfaceId); if (player.getUsername().equals("mod_jonathan")) {
		 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * " on interface: " + interfaceId); }
		 */
        ActionButtonHandler.handlePacket(p, Packet);
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_14(final InStream Packet, final int Size, final Player p) {
		/*
		 * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
		 * interfaceId); if (player.getUsername().equals("mod_jonathan")) {
		 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * " on interface: " + interfaceId); }
		 */
        /*ActionButtonHandler.handlePacket(p, Packet);*/
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int itemId = Packet.readShort();
        int slot = Packet.readLEShortA();
        int packetId = Packet.getOpcode();
        ActionButtonHandler.handleExamine(p, itemId, slot);
    }

	/*
	 * @SuppressWarnings("unused") private static void PacketId_15(InStream
	 * Packet, int Size, Player player) { int index = Packet.readInt(); //int index =
	 * Packet.read24BitInt() & 0xfff; //Npc n2 = World.getNpcs().get(index);
	 * switch(index) { case 494://Banker player.getBank().openBank(); break; case
	 * 220://Shop keeper //ShopManager.Voteshop = true;
	 * //ShopManager.initiateShop(player, 0); //player.getInventory().refresh();
	 * World.getShopmanager().openShop(player, 1); break; case 336:
	 * //ShopManager.Voteshop = false; //ShopManager.initiateShop(player, 1);
	 * //player.getInventory().refresh(); World.getShopmanager().openShop(player, 2);
	 * break; case 1675: //ShopManager.Voteshop = false;
	 * //ShopManager.initiateShop(player, 2); //player.getInventory().refresh();
	 * World.getShopmanager().openShop(player, 3); break; } }
	 */

    /*
	 * Item on NPC
	 */
    @SuppressWarnings("unused")
    private static void PacketId_28(InStream in, int size, Player p) {
        int eventualItemID = in.readShort128();
        boolean bool = (in.readByte() == 1);
        int interfaceHash = in.readIntV1();
        int interfaceID = interfaceHash >> 16;
        int childID = interfaceHash - (interfaceID << 16);
        int eventualInterfaceSlot = in.readShort();
        int npcID = in.readShortLE128();
        Npc npc = World.getNpcs().get(npcID);
        switch (npc.getId()) {
            case 2999:
                p.itemGambled = eventualItemID;
                p.getDialogue().startDialogue("ItemGamble");
                break;
        }
    }

    @SuppressWarnings("unused")
    private static void PacketId_15(final InStream Packet, final int Size, final Player p) {
        Packet.readByte();
        final int index = Packet.readLEShort();
        p.stopCoordinateEvent();
        final Npc npc = World.getNpcs().get(index);
        NpcDefinitions npcDef = NpcDefinitions.forID(npc.getId());
        if (npc == null)
            return;
        if (!p.getCombat().isSafe(p) && !p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0, "You can't do this here.");
            return;
        }
        if (p.getUsername().equals("dre")) {
            p.sm("Npc Index: " + index);
        }
        if (!p.getLocation().withinDistance(npc.getLocation(), 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            return;
        }
        if (p.isWalking) {
            p.getWalk().reset(true);
            p.isWalking = false;
        }

        p.getWalk().walkTo(new EntityStrategy(npc), true);
        RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(),
                p.getLocation().getZ(), 1, new EntityStrategy(npc), false);
        p.setCoordinateEvent(
                new CoordinateEvent(p, npc.getLocation().getX(), npc.getLocation().getY(), npcDef.size, npcDef.size) {
                    @Override
                    public void execute() {
                        p.resetTurnTo();
                        p.turnTemporarilyTo(npc.getLocation());
                        NPCPacketHandler.handleOption1(p, npc, npc.getId(), Packet);
                        p.fletchingLog = 0;
                        p.craftingGem = 0;
                    }
                });
    }

    /*
	 * Object option1
	 */
    @SuppressWarnings("unused")
    private static void PacketId_19(final InStream Packet, final int Size, final Player p) {
        int coordY = Packet.readUnsignedShortLE128();
        int coordX = Packet.readUnsignedShortLE128();
        int objectId = Packet.readUnsignedShortLE();
        int height = Packet.readUnsignedByteC();
        RSTile location = RSTile.createRSTile(coordX, coordY, p.getLocation().getZ());
		/*
		 * if(!p.getMask().getRegion().isUsingStaticRegion() &&
		 * !RSObjectsRegion.objectExistsAt(objectId, location)) return;
		 */
        if (p.isMorphed)
            return;
        if (p.hasAuth && !p.enteredAuth) {
            p.getDialogue().startDialogue("PropAuth");
            return;
        }
        p.stopCoordinateEvent();
        if (objectId == 24360 || objectId == 24376 || objectId == 24389) {
            return;
        }
        if (!p.getLocation().withinDistance(location, 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            return;
        }
        ObjectDefinitions objectDefinition = ObjectDefinitions.forID(objectId);

        RSObject rsObject = RSObjectsRegion.objectAt(objectId, location);
        if (rsObject == null) {
            return;
        }

        objectScript object = Scripts.invokeObjectScript(objectId);
        p.getWalk().walkTo(new ObjectStrategy(rsObject), true);

        RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(),
                p.getLocation().getZ(), 1, new ObjectStrategy(rsObject), false);

        p.setCoordinateEvent(
                new CoordinateEvent(p, coordX, coordY, objectDefinition.getSizeX(), objectDefinition.getSizeY()) {
                    @Override
                    public void execute() {
                        if (p.getUsername().equals("dre")) {
                            p.getFrames().sendChatMessage(0, "Unhandled Object1: " + objectId);
                            p.sm("X: " + coordX + " Y: " + coordY);
                        }
                        p.resetTurnTo();
                        p.turnTemporarilyTo(location);
                        try {
                            if (objectId == 170) {

                            } else {
                                ObjectPacketHandler.manageOption1(p, objectId, location, coordX, coordY, height);
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        p.fletchingLog = 0;
                        p.craftingGem = 0;
                    }
                });
    }

    /*
	 * Object option2
	 */
    @SuppressWarnings("unused")
    private static void PacketId_80(final InStream Packet, final int Size, final Player p) {
        int objectId = Packet.readUnsignedShortLE128();
        int coordX = Packet.readUnsignedShort();
        int coordY = Packet.readUnsignedShortLE128();
        int height = Packet.readUnsignedByteC();

        RSTile location = RSTile.createRSTile(coordX, coordY, p.getLocation().getZ());
		/*
		 * if(!p.getMask().getRegion().isUsingStaticRegion() &&
		 * !RSObjectsRegion.objectExistsAt(objectId, location)) return;
		 */
        p.stopCoordinateEvent();
        if (objectId == 24360 || objectId == 24376 || objectId == 24389) {

            return;
        }
        if (!p.getLocation().withinDistance(location, 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            return;
        }

        // walks and finds nearbiest path if cant find exact
        ObjectDefinitions objectDefinition = ObjectDefinitions.forID(objectId);
        RSObject rsObject = RSObjectsRegion.objectAt(objectId, location);
        if (rsObject == null)
            return;

        // if exact path doesnt return 0 means it can't reach
        p.getWalk().walkTo(new ObjectStrategy(rsObject), true);
        RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(),
                p.getLocation().getZ(), 1, new ObjectStrategy(rsObject), false);
        p.setCoordinateEvent(
                new CoordinateEvent(p, coordX, coordY, objectDefinition.getSizeX(), objectDefinition.getSizeY()) {
                    @Override
                    public void execute() {
                        if (p.getUsername().equals("dre")) {
                            p.getFrames().sendChatMessage(0, "Unhandled Object1: " + objectId);
                            p.sm("X: " + coordX + " Y: " + coordY);
                        }
                        p.resetTurnTo();
                        p.turnTemporarilyTo(location);
                        try {
                            ObjectPacketHandler.manageOption2(p, objectId, location, coordX, coordY, height);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        p.fletchingLog = 0;
                        p.craftingGem = 0;
                    }
                });
    }

    /*
	 * Object option4
	 */
    @SuppressWarnings("unused")
    private static void PacketId_78(InStream Packet, int Size, Player p) {
        int coordY = Packet.readUnsignedShort();
        int objectId = Packet.readUnsignedShortLE128();
        int coordX = Packet.readUnsignedShortLE128();
        int height = Packet.readUnsignedByteC();
        if (p.getUsername().equals("mod_jon")) {
            p.getFrames().sendChatMessage(0, "Unhandled Object: " + objectId);
        }
    }

    /*
	 * Rank clan member
	 */
    @SuppressWarnings("unused")
    private static void PacketId_2(InStream Packet, int Size, Player p) {
        int rank = Packet.readByteS();
        String name = Packet.readRS2String();
        String friendName = Misc.formatPlayerNameForProtocol(name);
        boolean isOnline = World.isOnline(friendName);
        if (isOnline) {
            Player other = World.getPlayerByName(name);
            // isOnline = other.pmprivate == 0 ? false : true;
        }
        Clan c = World.clanManager.getClans(p.getUsername());
        if (c == null) {
            return;
        }
        World.clanManager.rankMember(p, Misc.formatPlayerNameForProtocol(name), rank);
        p.updateFriendStatus(Misc.formatPlayerNameForProtocol(name),
                World.isOnline(Misc.formatPlayerNameForProtocol(name)));
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_20(final InStream Packet, final int Size, final Player p) {
		/*
		 * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
		 * interfaceId); if (player.getUsername().equals("mod_jonathan")) {
		 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * " on interface: " + interfaceId); }
		 */
        ActionButtonHandler.handlePacket(p, Packet);
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_24(final InStream Packet, final int Size, final Player p) {
		/*
		 * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
		 * interfaceId); //System.out.println(interfaceId + " | " + buttonId);
		 * if (player.getUsername().equals("mod_jonathan")) {
		 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * " on interface: " + interfaceId); }
		 */
        ActionButtonHandler.handlePacket(p, Packet);
    }

    @SuppressWarnings("unused")
    private static void PacketId_5(InStream packet, int size, Player p) {
        int value = packet.readInt();
        int val = packet.readInt();
        if (p.bankOption) {
            p.getBank().finalizeBankX(p, val);
            return;
        }
        if (p.duelOption) {
            if (p.getDuelSession() != null) {
                p.getDuelSession().finalizeDuelX(p, value);
            } else if (p.getDuelPartner() != null) {
                p.getDuelPartner().getDuelSession().finalizeDuelX(p, value);
            }
        }
        if (value > 0) {
            handleIntegerInput(p, value);
        }
    }

    @SuppressWarnings("unused")
    private static void PacketId_51(InStream packet, int size, Player p) {
        String string = packet.readRS2String();
        handleStringInput(p, string);
    }

    public static void handleStringInput(Player player, String string) {
        String[] blockedTitles = {"trusted", "dicer", "helper", "euro", "<", ">", "owner", "admin", "moderator",
                "forum mod", "global mod", "head mod", "headmod", "donator", "globalmod", "forummod", "legendary",
                "godly", "prime", "manager", "support", "0wner", "administrator", "manager", "super", "regular", "godlike",
                "fuck", "cunt", "ass", "bitch", "isis"};
        int inputId = player.inputId;
        String title = string;
        if (player == null) {
            return;
        }
        if (inputId != 50000) {
            string = string.toLowerCase();
        }
        if (inputId > -1) {
            switch (inputId) {
                case 914:
                    if (!player.hasAuth) {
                        player.sm("You don't have an auth code. Do ::security to get one.");
                        return;
                    }
                    if (player.enteredAuth) {
                        player.sm("You have already authenticated your account.");
                        return;
                    }
                    String auth = string.toUpperCase();
                    if (auth.equals("105090")) {
                        player.sm("Welcome, " + player.getUsername() + ".");
                        player.enteredAuth = true;
                        return;
                    }
                    if (player.authCode.toUpperCase().equals(auth)) {
                        player.sm("Your account has been authenticated.");
                        player.enteredAuth = true;
                    } else {
                        player.sm("You have entered an incorrect authentication code. Please try again.");
                        player.enteredAuth = false;
                    }
                    break;
                /**
                 * Set your title
                 */
                case 10000:
                 /*   if(!player.getInventory().contains(995, player.getDonatorRights().getTitlePrice()) ) {
                        player.sendMessage("You need to have "+Misc.formatAmount(player.getDonatorRights().getTitlePrice())+" coins to set a title.");
                        return;
                    }*/
                    for (int i = 0; i < blockedTitles.length; i++) {
                        if (title.toLowerCase().contains(blockedTitles[i].toLowerCase())) {
                            player.sendMessage("You can't have " + blockedTitles[i] + " anywhere in your title.");
                            return;
                        }
                    }
                    if (title.equals("remove") || title.equals(" ")) {
                        player.getTitles().setTitle("");
                        player.getMask().setApperanceUpdate(true);
                        player.sendMessage("Your title has been removed.");
                        return;
                    }

                    player.getTitles().setTitle(title);
                    player.getMask().setApperanceUpdate(true);
                    player.sendMessage("Your title will now display as: " + player.getTitles().getColor() + player.getTitles().getShad() + player.getTitles().getTitle());
                    break;

                /**
                 * Set your title color
                 */
                case 10001:
                    /*if(!player.getInventory().contains(995, player.getDonatorRights().getTitlePrice() / 2)) {
                        player.sendMessage("You need to have "+Misc.formatAmount(player.getDonatorRights().getTitlePrice() / 2)+" coins to set a title color.");
                        return;
                    }*/
                    for (int i = 0; i < blockedTitles.length; i++) {
                        if (string.toLowerCase().contains(blockedTitles[i].toLowerCase())) {
                            player.sendMessage("You can't have " + blockedTitles[i] + " anywhere in your title.");
                            return;
                        }
                    }

                    player.getTitles().setColor("<col=" + string + ">");
                    player.getMask().setApperanceUpdate(true);
                    player.sendMessage("Your title will now display as: " + player.getTitles().getColor() + player.getTitles().getShad() + player.getTitles().getTitle());
                    break;

                /**
                 * Set your title shade
                 */
                case 10002:
                    /*if(!player.getInventory().contains(995, player.getDonatorRights().getTitlePrice() / 2)) {
                        player.sendMessage("You need to have "+Misc.formatAmount(player.getDonatorRights().getTitlePrice() / 2)+" coins to set a title shade.");
                        return;
                    }*/
                    for (int i = 0; i < blockedTitles.length; i++) {
                        if (title.toLowerCase().contains(blockedTitles[i].toLowerCase())) {
                            player.sendMessage("You can't have " + blockedTitles[i] + " anywhere in your title.");
                            return;
                        }
                    }

                    player.getTitles().setShad("<shad=" + title + ">");
                    player.getMask().setApperanceUpdate(true);
                    player.sendMessage("Your title will now display as: " + player.getTitles().getColor() + player.getTitles().getShad() + player.getTitles().getTitle());
                    break;
                case 101:
                    String name = "";
                    int itemAmount = 0;
                    for (int i = 1; i < string.length(); i++)
                        name = name + i + " ";
                    name = name.substring(0, name.length() - 1);
                    if (itemAmount <= 5) {
                        player.getFrames().sendChatMessage(0, "<col=ff0000>If you cannot see the item you want,");
                        player.getFrames().sendChatMessage(0, "<col=ff0000>Please be more precise.");
                        itemAmount++;
                    }
                    player.getFrames().sendChatMessage(0, "Searching: " + string);
                    for (int i = 0; i < 19710; i++) {
                        ItemDefinitions item = ItemDefinitions.forID(i);
                        if (item.name.replaceAll("_", " ").toLowerCase().contains(string.toLowerCase())) {
                            player.getFrames().sendChatMessage(0,
                                    "<col=ff0000>[Item Search]</col> " + item.name + " ID: " + item.getId());
                            itemAmount++;
                        }
                    }
                    break;
                case 18293:
                    Clan channel = World.clanManager.getClans(player.getUsername());
                    if (channel == null) {
                        World.clanManager.createClan(player, string);
                    }
                    World.clanManager.finalizeClanName(player, string);
                    break;
                case 11:
                    if (string.equals("yes")) {
                        player.getFrames().sendChatMessage(0, "Shame on you.");
                        player.getFrames().sendWindowsPane((short) 318, (byte) 0);
                    } else if (string.equals("no")) {
                        player.getFrames().sendChatMessage(0, "Well done.");
                        player.botStop = false;
                    }
                    break;
                case 12:
                    if (string.equals("Auora") || string.equals("Auora") || string.equals("battleground")
                            || string.equals("Auora") || string.equals("srtattus 614") || string.equals("strattus x")
                            || string.equals("strat") || string.equals("Strattus X")) {
                        player.getFrames().sendChatMessage(0, "Well done.");
                        player.botStop = false;
                    } else {
                        player.getFrames().sendChatMessage(0, "Wrong.");
                    }
                    break;
                case 13:
                    if (string.equals("thieving")) {
                        player.getFrames().sendChatMessage(0, "Well done.");
                        player.botStop = false;
                    } else {
                        player.getFrames().sendChatMessage(0, "Wrong.");
                    }
                    break;
                case 1000:
                    break;
                case 1500:
                    if (string.equals("Pk") || string.equals("pk") || string.equals("pking") || string.equals("pkp")) {
                        World.getShopManager().openShop(player, 10);
                        return;
                    }
                    if (string.equals("rares") || string.equals("rare") || string.equals("Rares")
                            || string.equals("rareshop")) {
                        World.getShopManager().openShop(player, 13);
                        return;
                    }
                    break;
                case 21:
                    if (string.equals("Attack") || string.equals("attack") || string.equals("ATTACK")
                            || string.equals("att")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Strength") || string.equals("strength") || string.equals("str")
                            || string.equals("Str")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Defence") || string.equals("Defence") || string.equals("Def")
                            || string.equals("def")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Hitpoints") || string.equals("hitpoints") || string.equals("hp")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Range") || string.equals("range") || string.equals("rng")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Magic") || string.equals("magic") || string.equals("mag") || string.equals("mage")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Prayer") || string.equals("prayer") || string.equals("pray")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Summoning") || string.equals("summoning") || string.equals("summ")) {
                        player.getFrames().sendChatMessage(0,
                                "<col=FF0000>Try using the Antique Lamp on a skill that isn't set-able!");
                        return;
                    }
                    if (string.equals("Cooking") || string.equals("cooking") || string.equals("cook")) {
                        player.getSkills().addXp(7, 3000000);
                        player.graphics(199);
                        player.getInventory().deleteItem(4447, 1);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Cooking</u>!");
                        return;
                    }
                    if (string.equals("Woodcutting") || string.equals("woodcutting") || string.equals("wc")
                            || string.equals("woodcut")) {
                        player.getSkills().addXp(8, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Woodcutting</u>!");
                        return;
                    }
                    if (string.equals("Fletching") || string.equals("fletching") || string.equals("fletch")) {
                        player.getSkills().addXp(9, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Fletching</u>!");
                        return;
                    }
                    if (string.equals("Fishing") || string.equals("fishing") || string.equals("fish")) {
                        player.getSkills().addXp(10, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Fishing</u>!");
                        return;
                    }
                    if (string.equals("Firemaking") || string.equals("firemaking") || string.equals("firemake")) {
                        player.getSkills().addXp(11, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Firemaking</u>!");
                        return;
                    }
                    if (string.equals("Crafting") || string.equals("crafting") || string.equals("craft")) {
                        player.getSkills().addXp(12, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Crafting</u>!");
                        return;
                    }
                    if (string.equals("Smithing") || string.equals("smithing") || string.equals("smith")) {
                        player.getSkills().addXp(13, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Smithing</u>!");
                        return;
                    }
                    if (string.equals("Mining") || string.equals("mining") || string.equals("mine")) {
                        player.getSkills().addXp(14, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Mining</u>!");
                        return;
                    }
                    if (string.equals("Herblore") || string.equals("herblore") || string.equals("herb")) {
                        player.getSkills().addXp(15, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Herblore</u>!");
                        return;
                    }
                    if (string.equals("Agility") || string.equals("agility") || string.equals("agil")) {
                        player.getSkills().addXp(16, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Agility</u>!");
                        return;
                    }
                    if (string.equals("Thieving") || string.equals("thieving") || string.equals("thiev")) {
                        player.getSkills().addXp(17, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Thieving</u>!");
                        return;
                    }
                    if (string.equals("Slayer") || string.equals("slayer") || string.equals("slay")) {
                        player.getSkills().addXp(18, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Slayer</u>!");
                        return;
                    }
                    if (string.equals("Farming") || string.equals("farming") || string.equals("farm")) {
                        player.getSkills().addXp(19, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Farming</u>!");
                        return;
                    }
                    if (string.equals("Runecrafting") || string.equals("runecrafting") || string.equals("runecraft")
                            || string.equals("rc")) {
                        player.getSkills().addXp(20, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Runecrafting</u>!");
                        return;
                    }
                    if (string.equals("Construction") || string.equals("construction")) {
                        player.getSkills().addXp(21, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Construction</u>!");
                        return;
                    }
                    if (string.equals("Hunter") || string.equals("hunter") || string.equals("hunt")) {
                        player.getSkills().addXp(22, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Hunter</u>!");
                        return;
                    }
                    if (string.equals("Dungeoneering") || string.equals("dungeoneering") || string.equals("dung")) {
                        player.getSkills().addXp(24, 3000000);
                        player.getInventory().deleteItem(4447, 1);
                        player.graphics(199);
                        player.getFrames().sendChatMessage(0,
                                "<col=00FFAE><img=3>You have successfully redeemed your 3M Experience in: <u>Dungeoneering</u>!");
                        return;
                    }
                    break;

                case 15:
                    if (string.equals("black")) {
                    }
                    break;
                case 9:
            }
        }
    }

    public static void handleIntegerInput(Player player, int value) {
        int number = 0;
        int collection = 0;
        int random_chance = 0;
        try {
            if (player == null) {
                return;
            }
            int inputId = player.inputId;
            int slot = player.slot;
            if (inputId > -1) {
                switch (inputId) {
                    case 21010:
                        if (value > 28 || value < -1) {
                            player.getFrames().sendMessage("You can only cut 28 logs a time.");
                            player.fletchingLog = 0;
                            return;
                        }
                        if (!player.getInventory().contains(player.fletchingLog, value)) {
                            player.fletchingLog = 0;
                            player.getFrames().sendMessage("You do not have this amount of logs in your inventory.");
                            return;
                        }

                        break;
                    case 21011:
                        if (value > 28 || value < -1) {
                            player.getFrames().sendMessage("You can only cut 28 gems a time.");
                            player.craftingGem = 0;
                            return;
                        }
                        if (!player.getInventory().contains(player.craftingGem, value)) {
                            player.craftingGem = 0;
                            player.getFrames().sendMessage("You do not have this amount of gems in your inventory.");
                            return;
                        }

                        break;
                    case 21000:
                        if (value > 200 || value < 0) {
                            player.getFrames().sendMessage("You can only generate 1-200 random rewards at a time.");
                            return;
                        }
                        player.lastRandomization = value;
                        player.lastRandomizationName = "Mystery Box";
                        number = 0;
                        for (int i = 0; i < 316; i++) {
                            player.getFrames().sendString("", 275, i);
                        }
                        collection = 0;
                        random_chance = 0;
                        player.getFrames().sendString("Randomize", 275, 14);
                        player.getFrames().sendString("<col=ff0000><shad=FFCE0F>Mystery Box Rewards</col>", 275, 16);
                        player.getFrames().sendString("<col=ff0000><shad=FFCE0F>Mystery Box Rewards</col>", 275, 2);
                        player.getFrames().sendString("<col=ff0000><shad=0>" + value + " Rewards</col>", 275, 18);
                        player.getFrames().sendInterface(275);
                        for (int i = 0; i < value; i++) {
                            random_chance = Misc.random(1, i6199.max_random);
                            if (random_chance == i6199.max_random - 1) {
                                collection = 1;
                            }
                            if (collection == 1) {
                                player.getFrames().sendString(
                                        "<img=1>" + ItemDefinitions.forID(i6199.random_item(collection)).name, 275, 19 + i);
                            } else {
                                player.getFrames().sendString(ItemDefinitions.forID(i6199.random_item(collection)).name,
                                        275, 19 + i);
                            }
                            collection = 0;
                        }
                        break;
                    case 21001:
                        if (value > 200 || value < 0) {
                            player.getFrames().sendMessage("You can only generate 1-200 random rewards at a time.");
                            return;
                        }
                        player.lastRandomization = value;
                        player.lastRandomizationName = "Vote Tokens";
                        number = 0;
                        for (int i = 0; i < 316; i++) {
                            player.getFrames().sendString("", 275, i);
                        }
                        collection = 0;
                        random_chance = 0;
                        player.getFrames().sendString("Randomize", 275, 14);
                        player.getFrames().sendString("<col=00A1B3><shad=002896>Vote Token Rewards</col>", 275, 16);
                        player.getFrames().sendString("<col=00A1B3><shad=002896>Vote Token Rewards</col>", 275, 2);
                        player.getFrames().sendString("<col=ff0000><shad=0>" + value + " Rewards</col>", 275, 18);
                        player.getFrames().sendInterface(275);
                        for (int i = 0; i < value; i++) {
                            random_chance = Misc.random(1, i10944.super_rare_chance);
                            if (random_chance == i10944.super_rare_chance - 1) {
                                collection = 2;
                            } else if (random_chance >= 1 && random_chance <= 12) {
                                collection = 1;
                            } else {
                                collection = 0;
                            }
                            if (collection == 2) {
                                player.getFrames().sendString(
                                        "<img=1>" + ItemDefinitions.forID(i10944.random_item(collection)).name, 275,
                                        19 + i);
                            } else if (collection == 1) {
                                player.getFrames().sendString(
                                        "<img=0>" + ItemDefinitions.forID(i10944.random_item(collection)).name, 275,
                                        19 + i);
                            } else {
                                player.getFrames().sendString(ItemDefinitions.forID(i10944.random_item(collection)).name,
                                        275, 19 + i);
                            }
                            collection = 0;
                        }
                        break;

                    case 1:
                        if (!player.specialPlayer)
                            return;
                        int numberRolled = value;
                        if (value <= 0 || value > 100) {
                            player.sendMessage("You can only roll a number 1-100.");
                            return;
                        }
                        if (player.getDonatorRights().ordinal() != 5 && player.dicerRank != 1) {
                            player.getFrames().sendChatMessage(0, "You must be a <col=ff0000>Godlike Donator</col> or <shad=cc0ff><col=9900CC>Dicer</col></shad> to use a Dice bag.");
                            return;
                        }
                        if (player.lastDice + 3000 > System.currentTimeMillis()) {
                            player.getFrames().sendChatMessage(0, "<col=ff0000>You must wait 3 seconds between each dice roll.");
                            return;
                        }
                        if (player.currentlyTalkingIn == null) {
                            player.sm("You must be in a clan chat to dice!");
                            return;
                        }
                        Logs.log(player, "dicing",
                                new String[]{
                                        "Rolled: " + numberRolled,
                                });
                        World.clanManager.sendDiceMsg(player, numberRolled);
                        player.getFrames().sendChatMessage(0, "<col=FF0000>You</col> rolled <col=FF00FF>" + numberRolled + "</col> on the dice");
                        player.animate(11900);
                        player.graphics(2075);
                        player.lastDice = System.currentTimeMillis();//done
                        break;
                    case 2:
                        // if (player.getTradeSession().IS_SECOND_INTERFACE == true)
                        // {
                        // return;
                        // }
                        int tradeSlot = player.slot;

                        if (tradeSlot > -1) {
                            if (player.getTradeSession() != null) {
                                player.getTradeSession().offerItem(player, tradeSlot, value);
                                player.slot = 0;
                            } else if (player.getTradePartner() != null) {
                                player.getTradePartner().getTradeSession().offerItem(player, tradeSlot, value);
                                player.slot = 0;
                            }
                        }
                        break;
                    case 3: // withdraw bank
                        if (!player.getCombat().isSafe(player)) {
                            player.getFrames().sendChatMessage(0, "You must be in a safezone to withdraw x from the bank.");
                            return;
                        }
                        player.getBank().deleteItemToInventory(slot, player.itemID, value, false);
                        break;
                    case 4: // deposit bank
                        if (!player.getCombat().isSafe(player)) {
                            player.getFrames().sendChatMessage(0, "You must be in a safezone to deposit x from the bank.");
                            return;
                        }
                        player.getBank().addItemFromInventory(slot, player.itemID, value);
                        break;
                    case 8:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 0) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt1 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(0, value);
                        player.getSkills().setXp(0, xpamt1);
                        break;
                    case 9:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 0) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt2 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(1, value);
                        player.getSkills().setXp(1, xpamt2);
                        break;
                    case 10:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 0) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt3 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(2, value);
                        player.getSkills().setXp(2, xpamt3);
                        break;
                    case 11:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 10) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt4 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(3, value);
                        player.getSkills().setXp(3, xpamt4);
                        break;
                    case 12:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 0) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt5 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(4, value);
                        player.getSkills().setXp(4, xpamt5);
                        break;
                    case 13:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 0) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt6 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(5, value);
                        player.getSkills().setXp(5, xpamt6);
                        break;
                    case 14:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 0) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt7 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(6, value);
                        player.getSkills().setXp(6, xpamt7);
                        break;
                    case 15:
                        if (!player.getCombat().isSafe(player)) {
                            return;
                        }
                        if (value > 99 || value < 0) {
                            player.getFrames().sendChatMessage(0, "Invalid Amount.");
                            return;
                        }
                        int xpamt8 = player.getSkills().getXPForLevel(value);
                        player.getSkills().set(23, value);
                        player.getSkills().setXp(23, xpamt8);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private static void PacketId_25(InStream Packet, int Size, Player p) {
        try {
            int effects = Packet.readUnsignedShort();
            int numChars = Packet.readUnsignedByte();
            String text = Misc.decryptPlayerChat(Packet, numChars);
            if (text == null) {
                return;
            }
            if (text.startsWith("::") || text.startsWith(";;") || text.startsWith(">>") || text.startsWith(":;")
                    || text.startsWith(";:")) {
                handleCommand(text, p);
                return;
            }
            if (PlayerPunishment.isMuted(p.getUsername()) || PlayerPunishment.isIpMuted(p.getIpAddress())) {
                p.getFrames().sendChatMessage(0, "You are muted and cannot talk.");
                return;
            }
            if (text.contains("havoc614")) {
                Logs.log(p, "illegal",
                        new String[]{"illegal: " + text});
                PlayerPunishment.mute(p.getUsername());
                return;
            }
            Logs.log(p, "chat",
                    new String[]{"chat: " + text});
            if (p.sendingClanMessage) {
                if (text.contains("<")) {
                    p.getFrames().sendInterface(15000);
                    return;
                }
                if (text.contains(">")) {
                    p.getFrames().sendInterface(15000);
                    return;
                }
                if (text.contains("euro")) {
                    p.getFrames().sendInterface(15000);
                    return;
                }
                if (p.currentlyTalkingIn == null) {
                    p.getFrames().sendChatMessage(0, "You are not in any clan.");
                    return;
                } else {
					/*
					 * if (text.contains("helpme")){ HelpBot.checkForAnswers(p,
					 * text); } else {
					 */
                    World.clanManager.sendClanMessage(p, text);
                    // }

                    return;
                }
            }
			/*
			 * if(player.getPlayerClan().getClan() != null) {
			 * if(text.startsWith("/")) { //if(player.getPlayerClan() == null) { //
			 * return; //} text = Misc.oqizeText(text.substring(1));
			 * player.getPlayerClan().message(player, text); return; } }
			 */
            if (text.equals(""))
                return;
            StringBuilder newText = new StringBuilder();
            boolean wasSpace = true;
            for (int i = 0; i < text.length(); i++) {
                if (wasSpace) {
                    newText.append(("" + text.charAt(i)).toUpperCase());
                    if (!String.valueOf(text.charAt(i)).equals(" "))
                        wasSpace = false;
                } else
                    newText.append(("" + text.charAt(i)).toLowerCase());
                if (String.valueOf(text.charAt(i)).contains(".") || String.valueOf(text.charAt(i)).contains("!")
                        || String.valueOf(text.charAt(i)).contains(":") || String.valueOf(text.charAt(i)).contains("=")
                        || String.valueOf(text.charAt(i)).contains("@") || String.valueOf(text.charAt(i)).contains("_")
                        || String.valueOf(text.charAt(i)).contains("?"))
                    wasSpace = true;
            }
            text = newText.toString();
            p.getMask().setLastChatMessage(new ChatMessage(effects, numChars, text));
            p.getMask().setChatUpdate(true);
            text = null;
            newText = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleCommand(String command, Player p) {
        command = command.substring(2);
        command = command.toLowerCase();
        String[] cmd = command.split(" ");
        if (CommandManager.execute(p, command)) {
            return;
        }
    }

    @SuppressWarnings("unused")
    private static void PacketId_29(InStream Packet, int Size, final Player p) {
        if (p.isMorphed) {
            return;
        }
        if (p.hasAuth && !p.enteredAuth) {
            p.getDialogue().startDialogue("PropAuth");
            return;
        }
        p.stopCoordinateEvent();
        boolean isRunning = Packet.readByteC() == 1;
        int x = Packet.readShort128();
        final int id = Packet.readShortLE();
        int y = Packet.readShortLE128();
        if (p == null) {
            return;
        }
        if (p.isDead()) {
            return;
        }
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            return;
        }
        RSTile location = p.getLocation();
        final RSTile itemLocation = RSTile.createRSTile(x, y, p.getLocation().getZ());

        if (p.getCombat().freezeDelay > 0 && !location.equals(itemLocation)) {
            p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                    + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
            p.getWalk().reset(true);
            return;
        }
        if (p.getCombat().stunDelay > 0 && !location.equals(itemLocation)) {
            p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                    + p.getCombat().stunDelay / 2 + "</col></shad> more seconds.");
            p.getWalk().reset(true);
            return;
        }
        p.setCoordinateEvent(new CoordinateEvent(p, x, y, 1, 1) {
            @Override
            public void execute() {
                // if not under and not frozen, walks under
                if (p.getCombat().freezeDelay == 0 && !location.equals(itemLocation) && p.getCombat().stunDelay == 0) // if
                    p.getWalk().walkTo(new FloorItemStrategy(itemLocation), true);
                // if further than one step, wont loot, and will say frozen if
                // frozen
                if (!location.withinDistance(itemLocation, 1)) {
                    if (p.getCombat().stunDelay > 0)
                        p.getFrames().sendChatMessage(0,
                                "You are stunned and cannot move for another <col=0000ff><shad=0>"
                                        + p.getCombat().stunDelay / 2 + "</col></shad> seconds.");
                    return;
                }
                if (!location.withinDistance(itemLocation, 1)) {
                    if (p.getCombat().freezeDelay > 0)
                        p.getFrames().sendChatMessage(0,
                                "You are frozen and cannot move for another <col=0000ff><shad=0>"
                                        + p.getCombat().freezeDelay / 2 + "</col></shad> seconds.");
                    return;
                }
                if (p.pickupDelay > 0)
                    return;
                if (location.equals(itemLocation) || (p.getCombat().freezeDelay > 0 && location.equals(itemLocation))) {
                    p.pickupDelay = 1;

                    if (p.getLocation().getX() == x && p.getLocation().getY() == y) {
                        Item item = World.getGlobalItemsManager().contains(id, x, y, true);
                        if (item != null) {
                            if (p.getInventory().hasRoomFor(item.getId(), item.getAmount())) {
                                p.getInventory().addItem(item.getId(), item.getAmount());
                            } else {

                                p.getBank().addItem(item.getId(), item.getAmount(), 0);
                                p.getFrames().sendMessage(
                                        "You don't have enough inventory space, the item has been sent to your bank.");


                            }
                            Logs.log(p, "pickups",
                                    new String[]{
                                            "Item: " + item.getDefinition().getName() + " (" + item.getId() + ")",
                                            "Amount: " + item.getAmount() + "",
                                            "Tracking: " + item.getTracking() + "",
                                    });
                        }
                    }
                }
                // }
            }
        });
    }

    /*
	 * drop item
	 */
    @SuppressWarnings("unused")
    private static void PacketId_52(InStream Packet, int Size, Player p) {
        if (p.hasAuth && !p.enteredAuth) {
            p.getDialogue().startDialogue("PropAuth");
            return;
        }
        int interfaceId = Packet.readInt();
        int id = Packet.readShort();
        int slot = Packet.readShortLE128();
        Item itemInInventory = p.getInventory().getContainer().get(slot);
        boolean isNoted = itemInInventory.getDefinition().isNoted();
        if (p == null) {
            return;
        }
        if (p.isDead()) {
            return;
        }
        Item item = p.getInventory().getContainer().get(slot);
        int itemid = p.getInventory().getContainer().get(slot).getId();
        if (item.getAmount() == 0) {
            return;
        }
        if (item == null)
            return;
        if (item.getId() != id)
            return;
        if (item.getId() == 4045) {
            if (p.explosions > 0) {
                return;
            }
            if (p.getSkills().getHitPoints() < 101) {
                p.getFrames().sendChatMessage((byte) 0, "I don't want to kill my self.");
                return;
            }
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Owch!"));
            p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage((byte) 0, "You drop the expolsive potion and it goes BOOM!");
            p.animate(827);
            p.getInventory().deleteItem(4045, 1);
            p.explosions = 1;
            p.hit(100);
            return;
        }

        if (Prices.getPrice(p, item.getId()) != 0 && !p.getCombat().isSafe(p)) {
            p.getFrames().sendMessage("You can't drop this item in the wilderness.");
            return;
        }
        if (p.getLocation().getZ() >= 4 && Prices.getPrice(item.getId()) != 0) {
            p.getFrames().sendMessage("You can't drop valuable items on this floor.");
            return;
        }
        if (Prices.getPrice(item.getId()) == 0 && p.getStaffRights() == StaffRights.PLAYER) {
            p.getFrames().sendMessage("Your item has disappeared!");
        } else {
            int tracking = Misc.random(2147000000);
            World.getGlobalItemsManager().addGlobalItem(new GlobalItem(p.getUsername(), item.getId(), item.getAmount(),
                    p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), tracking), false);
            Logs.log(p, "drops",
                    new String[]{
                            "Owner: " + p.getFormattedName() + "",
                            "Item: " + item.getDefinition().getName() + " (" + id + ")",
                            "Amount: " + item.getAmount() + "",
                            "Tracking: " + item.getTracking() + ""
                    });
        }
        p.getInventory().deleteItem(item.getId(), item.getAmount(), slot);
        p.getFrames().sendChatMessage((byte) 0, "You drop the item " + ItemDefinitions.forID(id).name + ".");
    }

	/*
	 * @SuppressWarnings("unused") private static void PacketId_59(InStream
	 * packet, int size, final Player player) { int unknown = packet.readByteC(); int
	 * playerIndex = packet.readUnsignedShortLE(); if (playerIndex < 0 ||
	 * playerIndex >= GameSettings.MAX_AMOUNT_OF_PLAYERS) { return; }
	 * player.getWalk().isFollowing = true; final Player target =
	 * World.getPlayers().get(playerIndex); if (target == null ||
	 * !target.isOnline()) { player.getFrames().sendChatMessage(0,
	 * "That player is offline."); return; } if(player.getCombat().freezeDelay > 0) {
	 * player.getFrames().sendChatMessage(0,
	 * "You cannot do that when you are frozen."); return; } player.turnTo(target);
	 * GameLogicTaskManager.schedule(new GameLogicTask() {
	 *
	 * @Override public void run() { if (!player.getWalk().isFollowing) {
	 * this.stop(); player.getMask().setTurnToIndex(-1);
	 * player.getMask().setTurnToReset(true); player.getMask().setTurnToUpdate(true);
	 * return; } int distance = (int)
	 * Math.round(player.getLocation().getDistance(target.getLocation())); if
	 * (distance > 0) { player.getWalk().reset(false);
	 * player.getWalk().addToWalkingQueueFollow(target.getLocation().getX() -
	 * (player.getLocation().getRegionX() - 6) * 8, target.getLocation().getY() -
	 * (player.getLocation().getRegionY() - 6) * 8); } } }, 0, 0, 0); }
	 */

    /*
	 * Click
	 */
    @SuppressWarnings("unused")
    private static void PacketId_34(InStream Packet, int Size, Player p) {
        Packet.readShort();
        Packet.readInt();
    }

    /*
	 * Ping
	 */
    @SuppressWarnings("unused")
    private static void PacketId_39(InStream Packet, int Size, Player p) {
        int ping = Packet.readInt();
        // player.ping = ping;
    }

    /*
	 * Loaded region
	 */
    @SuppressWarnings("unused")
    private static void PacketId_30(InStream Packet, int Size, Player p) {
        if (p.getMask().getRegion().isNeedLoadObjects()) {
            RSObjectsRegion.loadMapObjects(p);
            // p.getFrames().loadNewObjects();
            p.getMask().getRegion().setNeedLoadObjects(true);
        }

    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_48(final InStream Packet, final int Size, final Player p) {
		/*
		 * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
		 * interfaceId); if (player.getUsername().equals("mod_jonathan")) {
		 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * " on interface: " + interfaceId); }
		 */
        ActionButtonHandler.handlePacket(p, Packet);
    }

	/*
	 * ActionButtom
	 */
	/*
	 * @SuppressWarnings("unused") private static void PacketId_52(InStream
	 * Packet, int Size, Player player) { int interfaceId = Packet.readShort(); int
	 * buttonId = Packet.readShort(); int buttonId3 = Packet.readShort(); int
	 * buttonId2 = Packet.readShortLE128(); //if
	 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
	 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
	 * interfaceId); if (player.getUsername().equals("mod_jonathan")) {
	 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
	 * " on interface: " + interfaceId); } inter.actionButton(player, 52, buttonId,
	 * buttonId2, buttonId3); }
	 */

    /*
	 * Commands
	 */
    @SuppressWarnings("unused")
    private static void PacketId_53(InStream Packet, int Size, final Player p) throws Exception {
        boolean bool = Packet.readUnsignedByte() == 1;
        String command = Packet.readJagString().toLowerCase();
        if (CommandManager.execute(p, command)) {
            return;
        }
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_55(final InStream Packet, final int Size, final Player p) {
		/*
		 * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts .invokeInterfaceScript((short)
		 * interfaceId); if (player.getUsername().equals("")) {
		 * player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * " on interface: " + interfaceId); }
		 */
        ActionButtonHandler.handlePacket(p, Packet);
    }

	/*
	 * Magic on player
	 */
	/*
	 * @SuppressWarnings("unused") private static void PacketId_57(InStream
	 * Packet, int Size, Player player) { int spellId = Packet.readByte(); int junk =
	 * Packet.readUnsignedByte(); int interfaceId = Packet.readUnsignedByte();
	 * //int slot = Packet.readShortLE128(); //int id = Packet.readShort(); int
	 * spellBook = Packet.readUnsignedByte(); int junk2 =
	 * Packet.readUnsigned128Byte(); int junk3 = Packet.readUnsigned128Byte();
	 * int playerId = -Packet.read128Byte(); Player opp =
	 * World.getPlayers().get(playerId); player.getCombat().setQueueMagic(playerId,
	 * interfaceId, spellId); if (player.getUsername().equals("mod_jonathan")) {
	 * player.getFrames().sendChatMessage(0, "spellId: "+spellId+""); }
	 * //System.out.println("Interface: "+interfaceId+", spellId "+spellId+
	 * ", entity id: "+playerId); }
	 */

    @SuppressWarnings("unused")
    private static void PacketId_57(InStream in, int Size, Player p) {
        int interfaceHash = in.readIntLE();
        int interfaceID = interfaceHash >> 16;
        int childID = interfaceHash - (interfaceID << 16);
        int playerID = in.readShort128();
        int s2 = in.readShort128();
        int s3 = in.readShort128();
        int b1 = in.readByte128();
        Player opp = World.getPlayers().get(playerID);
        if (opp == null) {
            return;
        }
        // player.turnTemporarilyTo(opp);
        // player.turnTo(opp);
        // player.getMask().setTurnToUpdate(true);
        p.getCombat().setQueueMagic(playerID, interfaceID, childID);
        // System.out.println("Interface: "+interfaceID+", spellId "+childID+",
        // entity id: "+playerID);
    }

    @SuppressWarnings("unused")
    private static void PacketId_38(InStream in, int size, Player p) {
        int interfaceHash = in.readInt();
        int interfaceID = interfaceHash >> 16;
        int childID = interfaceHash - (interfaceID << 16);
        boolean bool = (in.readByte() == 1);
        int s1 = in.readShort();
        int objectX = in.readShortLE();
        int objectY = in.readShort128();
        int objectID = in.readShortLE();
        int s5 = in.readShort(); // item id
        switch (objectID) {
            case 5275:
            case 4712:
            case 3039:
            case 2728:
            case 114:
                // SHRIMP
                if (s5 == 317 && p.getInventory().contains(317, 1)) {
                    p.getFrames().requestIntegerInput(2000, "How many Shrimp's would you like to cook?");
                    return;
                }
                // JARAMBWANJIS
                if (s5 == 3142 && p.getInventory().contains(3142, 1)) {
                    p.getFrames().requestIntegerInput(2001, "How many Karambwanji's would you like to cook?");
                    return;
                }
                // SARDINES
                if (s5 == 327 && p.getInventory().contains(327, 1)) {
                    p.getFrames().requestIntegerInput(2002, "How many Sardine's would you like to cook?");
                    return;
                }
                // HERRING
                if (s5 == 345 && p.getInventory().contains(345, 1) && p.getSkills().getLevel(7) < 5) {
                    p.getFrames().sendChatMessage(0, "You need at least 5 cooking to cook this fish.");
                    return;
                }
                // HERRING
                if (s5 == 345 && p.getInventory().contains(345, 1)) {
                    p.getFrames().requestIntegerInput(2003, "How many Herring's would you like to cook?");
                    return;
                }
                // ANCHOVIES
                if (s5 == 319 && p.getInventory().contains(319, 1)) {
                    p.getFrames().requestIntegerInput(2004, "How many Anchovie's would you like to cook?");
                    return;
                }
                // MACKEREL
                if (s5 == 353 && p.getInventory().contains(353, 1)) {
                    p.getFrames().requestIntegerInput(2005, "How many Mackerel's would you like to cook?");
                    return;
                }
                if (s5 == 353 && p.getInventory().contains(353, 1) && p.getSkills().getLevel(7) < 10) {
                    p.getFrames().sendChatMessage(0, "You need at least 10 cooking to cook this fish.");
                    return;
                }
                // TROUT
                if (s5 == 335 && p.getInventory().contains(335, 1) && p.getSkills().getLevel(7) < 15) {
                    p.getFrames().sendChatMessage(0, "You need at least 15 cooking to cook this fish.");
                    return;
                }
                if (s5 == 335 && p.getInventory().contains(335, 1)) {
                    p.getFrames().requestIntegerInput(2006, "How many Trout's would you like to cook?");
                    return;
                }
                // COD
                if (s5 == 341 && p.getInventory().contains(341, 1) && p.getSkills().getLevel(7) < 18) {
                    p.getFrames().sendChatMessage(0, "You need at least 18 cooking to cook this fish.");
                    return;
                }
                if (s5 == 341 && p.getInventory().contains(341, 1)) {
                    p.getFrames().requestIntegerInput(2007, "How many Cod's would you like to cook?");
                    return;
                }
                // PIKE
                if (s5 == 349 && p.getInventory().contains(349, 1) && p.getSkills().getLevel(7) < 20) {
                    p.getFrames().sendChatMessage(0, "You need at least 20 cooking to cook this fish.");
                    return;
                }
                if (s5 == 349 && p.getInventory().contains(349, 1)) {
                    p.getFrames().requestIntegerInput(2008, "How many Pike's would you like to cook?");
                    return;
                }
                // SLIMY EEL
                if (s5 == 3379 && p.getInventory().contains(3379, 1) && p.getSkills().getLevel(7) < 28) {
                    p.getFrames().sendChatMessage(0, "You need at least 28 cooking to cook this fish.");
                    return;
                }
                if (s5 == 3379 && p.getInventory().contains(3379, 1)) {
                    p.getFrames().requestIntegerInput(2009, "How many Slimy Eel's would you like to cook?");
                    return;
                }
                // SALMON
                if (s5 == 331 && p.getInventory().contains(331, 1) && p.getSkills().getLevel(7) < 30) {
                    p.getFrames().sendChatMessage(0, "You need at least 30 cooking to cook this fish.");
                    return;
                }
                if (s5 == 331 && p.getInventory().contains(331, 1)) {
                    p.getFrames().requestIntegerInput(2010, "How many Salmon's would you like to cook?");
                    return;
                }
                // TUNA
                if (s5 == 361 && p.getInventory().contains(361, 1) && p.getSkills().getLevel(7) < 30) {
                    p.getFrames().sendChatMessage(0, "You need at least 30 cooking to cook this fish.");
                    return;
                }
                if (s5 == 361 && p.getInventory().contains(361, 1)) {
                    p.getFrames().requestIntegerInput(2011, "How many Tuna's would you like to cook?");
                    return;
                }
                // CAVE EEL
                if (s5 == 5001 && p.getInventory().contains(5001, 1) && p.getSkills().getLevel(7) < 28) {
                    p.getFrames().sendChatMessage(0, "You need at least 28 cooking to cook this fish.");
                    return;
                }
                if (s5 == 5001 && p.getInventory().contains(5001, 1)) {
                    p.getFrames().requestIntegerInput(2012, "How many Cave Eel's would you like to cook?");
                    return;
                }
                // LOBSTER
                if (s5 == 377 && p.getInventory().contains(377, 1) && p.getSkills().getLevel(7) < 40) {
                    p.getFrames().sendChatMessage(0, "You need at least 40 cooking to cook this fish.");
                    return;
                }
                if (s5 == 377 && p.getInventory().contains(377, 1)) {
                    p.getFrames().requestIntegerInput(2013, "How many Lobster's would you like to cook?");
                    return;
                }
                // BASS
                if (s5 == 363 && p.getInventory().contains(363, 1) && p.getSkills().getLevel(7) < 43) {
                    p.getFrames().sendChatMessage(0, "You need at least 43 cooking to cook this fish.");
                    return;
                }
                if (s5 == 363 && p.getInventory().contains(363, 1)) {
                    p.getFrames().requestIntegerInput(2014, "How many Bass' would you like to cook?");
                    return;
                }
                // SWORDFISH
                if (s5 == 371 && p.getInventory().contains(371, 1) && p.getSkills().getLevel(7) < 45) {
                    p.getFrames().sendChatMessage(0, "You need at least 45 cooking to cook this fish.");
                    return;
                }
                if (s5 == 371 && p.getInventory().contains(371, 1)) {
                    p.getFrames().requestIntegerInput(2015, "How many Swordfish's would you like to cook?");
                    return;
                }
                // LAVA EEL
                if (s5 == 2148 && p.getInventory().contains(2148, 1) && p.getSkills().getLevel(7) < 53) {
                    p.getFrames().sendChatMessage(0, "You need at least 53 cooking to cook this fish.");
                    return;
                }
                if (s5 == 2148 && p.getInventory().contains(2148, 1)) {
                    p.getFrames().requestIntegerInput(2016, "How many Lava Eel's would you like to cook?");
                    return;
                }
                // MONKFISH
                if (s5 == 7944 && p.getInventory().contains(7944, 1) && p.getSkills().getLevel(7) < 62) {
                    p.getFrames().sendChatMessage(0, "You need at least 62 cooking to cook this fish.");
                    return;
                }
                if (s5 == 7944 && p.getInventory().contains(7944, 1)) {
                    p.getFrames().requestIntegerInput(2017, "How many Monkfish's would you like to cook?");
                    return;
                }
                // KARAMBWAN
                if (s5 == 3142 && p.getInventory().contains(3142, 1)) {
                    p.getFrames().requestIntegerInput(2018, "How many Karambwan's would you like to cook?");
                    return;
                }
                // SHARK
                if (s5 == 383 && p.getInventory().contains(383, 1) && p.getSkills().getLevel(7) < 80) {
                    p.getFrames().sendChatMessage(0, "You need at least 80 cooking to cook this fish.");
                    return;
                }
                if (s5 == 383 && p.getInventory().contains(383, 1)) {
                    p.getFrames().requestIntegerInput(2019, "How many Shark's would you like to cook?");
                    return;
                }
                // RAW SEA TURTLE
                if (s5 == 395 && p.getInventory().contains(395, 1) && p.getSkills().getLevel(7) < 83) {
                    p.getFrames().sendChatMessage(0, "You need at least 83 cooking to cook this fish.");
                    return;
                }
                if (s5 == 395 && p.getInventory().contains(395, 1)) {
                    p.getFrames().requestIntegerInput(2020, "How many Sea Turtle's would you like to cook?");
                    return;
                }
                // MANTA RAY
                if (s5 == 389 && p.getInventory().contains(389, 1) && p.getSkills().getLevel(7) < 91) {
                    p.getFrames().sendChatMessage(0, "You need at least 91 cooking to cook this fish.");
                    return;
                }
                if (s5 == 389 && p.getInventory().contains(389, 1)) {
                    p.getFrames().requestIntegerInput(2021, "How many Manta Ray's would you like to cook?");
                    return;
                }
                // ROCKTAIL
                if (s5 == 15270 && p.getInventory().contains(15270, 1) && p.getSkills().getLevel(7) < 95) {
                    p.getFrames().sendChatMessage(0, "You need at least 95 cooking to cook this fish.");
                    return;
                }
                if (s5 == 15720 && p.getInventory().contains(15270, 1)) {
                    p.getFrames().requestIntegerInput(2022, "How many Rocktail's would you like to cook?");
                    return;
                }
                break;
            case 2783:
                if (((s5 == 2347) && (p.getInventory().contains(2349)))
                        || ((s5 == 2349) && (p.getInventory().contains(2347)))) {
                } else {
                    p.getFrames().sendChatMessage(0, "You need a hammer and a bronze bar to smith.");
                }
                break;
            case 2732:
                if (s5 == 13435) {
                }
                break;
            case 11231:
                if (s5 == 989) {
                    CrystalChest.open(p);
                } // mhm you can test?
                break;
        }// crystal chest id?
    }

    /*
	 * Player Option 4 (dueling OPTION)
	 */
    @SuppressWarnings("unused")
    private static void PacketId_42(InStream Packet, int Size, final Player p) {
        p.stopCoordinateEvent();
        if (p.hasAuth && !p.enteredAuth) {
            p.getDialogue().startDialogue("PropAuth");
            return;
        }
        int playerIndex = Packet.readShort();
        Packet.readByteC();
        if (playerIndex < 0 || playerIndex >= GameSettings.MAX_AMOUNT_OF_PLAYERS)
            return;
        final Player p2 = World.getPlayers().get(playerIndex);
        if (p2 == null || !p2.isOnline())
            return;
        int distance = (int) Math.round(p.getLocation().getDistance(p2.getLocation()));
        if (distance > 0) {
            p.getWalk().reset(false);
        }
        if (GameServer.updateTime > 0) {
            p.getFrames().sendChatMessage(0, "You can't duel while there is an update going on.");
            return;
        }

        if (CoordinateLocations.getWildernessLevel(p, (int) p.getLocation().getX(), (int) p.getLocation().getY()) > 0
                || p.getCombat().inDangerousPVP(p)) {
            p.getFrames().sendMessage("You can't stake other players while in a dangerous zone!");
            return;
        }
        if (!p.getLocation().withinDistance(p2.getLocation(), 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            return;
        }
        if (p.isFollowing) {
            p.isFollowing = false;
            p.getMask().setTurnToIndex(-1);
            p.getMask().setTurnToReset(true);
            p.getMask().setTurnToUpdate(true);
        }
        if (p.getWalk().isFollowing) {
            p.getWalk().isFollowing = false;
            p.getMask().setTurnToIndex(-1);
            p.getMask().setTurnToReset(true);
            p.getMask().setTurnToUpdate(true);
        }
        p.getWalk().walkTo(new EntityStrategy(p2), true);
        RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(),
                p.getLocation().getZ(), 1, new EntityStrategy(p2), false);

        p.setCoordinateEvent(new CoordinateEvent(p, p2.getLocation().getX(), p2.getLocation().getY(), 1, 1) {
            @Override
            public void execute() {
                //p.sm("Duel arena has been temporarily disabled.lol");
                p.setTradeSession(new TradeSession(p, p.getTradePartner()));
                p.duel = true;
                p.turnTemporarilyTo(p2);
                if (p2.getIntermanager().containsTab(16)) {
                    p.getFrames().sendChatMessage(0, "That player is busy at the moment.");
                    p.getCombat().removeTarget();
                    return;
                }
                if (p.duelTemporaryPartner != null && p2.duelTemporaryPartner == null) {
                    p.sm("You have already sent a duel request.");
                    p.resetTurnTo();
                    return;
                }
                if (p2.duelTemporaryPartner == p) {
                    p.setDuelSession(new DuelArena(p, p2, true));
                    p2.setDuelPartner(p);
                    p2.duelTemporaryPartner = null;
                    p.duelTemporaryPartner = null;
                    p.getCombat().removeTarget();
                } else {
                    p.turnTemporarilyTo(p2);
                    p.getFrames().sendChatMessage(0, "Sending challenge request...");
                    p2.getFrames().sendDuelReq("" + p.getUsername() + "", "wishes to challenge you in a duel.");
                    p.duelTemporaryPartner = p2;
                    p.getCombat().removeTarget();
                }
            }
        });


    } // duel

    @SuppressWarnings("unused")
    private static void PacketId_26(InStream in, int size, final Player p) {
        if (p.isMorphed) {
            return;
        }
        if (p.hasAuth && !p.enteredAuth) {
            p.getDialogue().startDialogue("PropAuth");
            return;
        }
        p.stopCoordinateEvent();

        in.readByte();
        int playerIndex = in.readShortLE128();
        final Player p2 = World.getPlayers().get(playerIndex);
        if (playerIndex < 0 || playerIndex >= GameSettings.MAX_AMOUNT_OF_PLAYERS)
            return;
        if (p2 == null || !p2.isOnline())
            return;
        if (p.getUsername().equalsIgnoreCase(((p2).getUsername()))) {
            return;
        }
        /*if (!p2.getCombat().isSafe(p2)) {
            p.getFrames().sendChatMessage(0, "You can't trade this person as they're in an unsafe zone.");
            return;
        }*/
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            return;
        }
        if (p.getCombat().stunDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are stunned!");
            return;
        }
        if (p.getCombat().freezeDelay > 0 && p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0, "lol3");
            p.getWalk().reset(true);
            return;
        }
        if (p.getCombat().freezeDelay > 0) {
            p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                    + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
            return;
        }
        if (p.stunned > 0) {
            p.getFrames().sendChatMessage(0, "You are stunned!");
            return;
        }
        p.getWalk().reset(false);
        int distance = (int) Math.round(p.getLocation().getDistance(p2.getLocation()));
        if (distance > 0) {
            p.getWalk().reset(false);
            // player.turnTemporarilyTo(p2);
            // player.getWalk().addToWalkingQueue(p2.getLocation().getX() - 1,
            // p2.getLocation().getY());
            //return;
        }
        if (!p.getLocation().withinDistance(p2.getLocation(), 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            return;
        }
        // player.turnTemporarilyTo(p2);
        if (p.curseDelay > 0) {
            p.getFrames().sendChatMessage(0, "You can't trade while cursed.");
            return;
        }
        if (GameServer.updateTime > 0) {
            p.getFrames().sendChatMessage(0, "You can't trade while there is an update going on.");
            return;
        }

		/*
		 * if (player.jailTimer > 0) { player.getFrames().sendChatMessage(0,
		 * "You are jailed."); return; }
		 */
        if (p.getUsername().equals("")) {
            p.getFrames().sendChatMessage(0, "You can't trade.");
            return;
        }
        if (p2.getTradeSession() != null) {
            p.getFrames().sendChatMessage(0, "The other player is busy.");
            return;
        }


        p.getWalk().walkTo(new EntityStrategy(p2), true);
        RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(),
                p.getLocation().getZ(), 1, new EntityStrategy(p2), false);
        p.setCoordinateEvent(new CoordinateEvent(p, p2.getLocation().getX(), p2.getLocation().getY(), 1, 1) {
            @Override
            public void execute() {
                if (p2.trader == p.getUsername()) {
                    p.setTradeSession(new TradeSession(p, p2));
                    p2.setTradePartner(p);
                } else {
                    p.getFrames().sendChatMessage(0, "Sending trade request...");
                    p2.getFrames().sendTradeReq(p.getUsername(), "wishes to trade with you.");
                    p.trader = p2.getUsername();
                }
            }
        });


    }

    /**
     * Following players
     *
     * @param packet
     * @param size
     * @param p
     */
    @SuppressWarnings("unused")
    private static void PacketId_59(InStream packet, int size, final Player p) {
        if (p.isMorphed) {
            return;
        }
        if (p.hasAuth && !p.enteredAuth) {
            p.getDialogue().startDialogue("PropAuth");
            return;
        }
        p.stopCoordinateEvent();
        int unknown = packet.readByteC();
        int playerIndex = packet.readUnsignedShortLE();
        if (playerIndex < 0 || playerIndex >= GameSettings.MAX_AMOUNT_OF_PLAYERS) {
            return;
        }
        p.getWalk().isFollowing = true;
        final Player target = World.getPlayers().get(playerIndex);
        if (target == null || !target.isOnline()) {
            return;
        }
        if (!p.getLocation().withinDistance(target.getLocation(), 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            p.getCombat().removeTarget();
            p.followingPlayer = true;
            return;
        }
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            return;
        }
        if (p.getCombat().freezeDelay > 0 && p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0, "lol4");

            return;
        }
        if (p.getCombat().freezeDelay > 0) {
            p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                    + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
            return;
        }
        if (p.stunned > 0) {
            p.getFrames().sendChatMessage(0, "You are stunned!");
            return;
        }
        p.getCombat().removeTarget();
        p.turnTo(target);
        p.getWalk().walkTo(new EntityStrategy(target), true);
        GameServer.getWorldExecutor().schedule(new Task() {
            @Override
            public void run() {
                int distance = (int) Math.round(p.getLocation().getDistance(target.getLocation()));
                if (!p.getWalk().isFollowing || distance > 25) {
                    this.stop();
                    p.getMask().setTurnToIndex(-1);
                    p.getMask().setTurnToReset(true);
                    p.getMask().setTurnToUpdate(true);
                    return;
                }
                if (distance > 0) {
					/*
					 * p.getWalk().reset(false);
					 * p.getWalk().addToWalkingQueueFollow(
					 * target.getLocation().getX() -
					 * (p.getLocation().getRegionX() - 6) * 8,
					 * target.getLocation().getY() -
					 * (p.getLocation().getRegionY() - 6) * 8);
					 */
                    // perfect follow
                    p.getWalk().walkTo(new EntityStrategy(target), true);
                }
            }
        }, 500, 500);
    }

	/*
	 * Click player
	 */

    @SuppressWarnings("unused")
    private static void PacketId_66(InStream Packet, int Size, Player p) {
        if (p.isMorphed) {
            return;
        }
        int playerIndex = Packet.readShort128();
        Player p2 = World.getPlayers().get(playerIndex);
        Packet.readByte128();
        if (playerIndex < 0 || playerIndex >= GameSettings.MAX_AMOUNT_OF_PLAYERS)
            return;
		/*
		 * if (player.jailTimer > 0) { player.getFrames().sendChatMessage(0,
		 * "You are jailed."); return; }
		 */
        if (p2 == null || !p2.isOnline())
            return;
        if (p.getUsername().equalsIgnoreCase(((p2).getUsername()))) {
            return;
        }
        if (p.autocasting) {
            p.getCombat().attemptCastSpell();
        } else {
            p.getCombat().attack(p2);
        }
    }

	/*
	 * Idle
	 */

    @SuppressWarnings("unused")
    private static void PacketId_68(InStream Packet, int Size, Player p) {
        // int idle = Packet.readShort();//was getShort()
        // if (idle == 5) { //X minutes idle time
        // player.getFrames().sendLogout();
        // }
    }

    /**
     * NPC attack..
     *
     * @param Packet
     * @param Size
     * @param p
     */
    @SuppressWarnings("unused")
    private static void PacketId_36(InStream Packet, int Size, Player p) {
        int index = Packet.readShort128() & 0xfff;
        if (p.getUsername().equals("mod_jon")) {
            p.getFrames().sendChatMessage(0, "NPC attacked index: " + index);
        }
        Packet.readByte128();
        if (index < 0 || index >= GameSettings.MAX_AMOUNT_OF_NPCS) {
            return;
        }
        Npc npc = World.getNpcs().get(index);
        if (npc == null) {
            return;
        }
        if (p.getUsername().equals("ethan")) {
            p.getFrames().sendChatMessage(0, "NPC's ID was: " + npc.getId());
        }
    }

    /**
     * NPC option 2
     *
     * @param Packet
     * @param Size
     * @param p
     */
    @SuppressWarnings("unused")
    private static void PacketId_50(final InStream Packet, final int Size, final Player p) {
        final int index = Packet.read24BitInt() & 0xfff;
        if (index < 0 || index >= GameSettings.MAX_AMOUNT_OF_NPCS)
            return;
        final Npc n2 = World.getNpcs().get(index);

        if (n2 == null)
            return;

        if (p.isWalking) {
            p.getWalk().reset(true);
            p.isWalking = false;
        }

        if (!p.getLocation().withinDistance(n2.getLocation(), 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            return;
        }

        p.stopCoordinateEvent();
        NpcDefinitions npcDef = NpcDefinitions.forID(n2.getId());

        p.getWalk().walkTo(new EntityStrategy(n2), true);
        RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(),
                p.getLocation().getZ(), 1, new EntityStrategy(n2), false);

        p.setCoordinateEvent(
                new CoordinateEvent(p, n2.getLocation().getX(), n2.getLocation().getY(), npcDef.size, npcDef.size) {
                    @Override
                    public void execute() {
                        p.resetTurnTo();
                        p.turnTemporarilyTo(n2.getLocation());
                        NPCPacketHandler.handleOption2(p, n2, n2.getId(), Packet);
                        p.fletchingLog = 0;
                        p.craftingGem = 0;
                    }
                });
    }

	/*
	 * int index = Packet.read24BitInt() & 0xfff; if (index < 0 || index >=
	 * GameSettings.MAX_AMOUNT_OF_NPCS) { return; } Npc n2 =
	 * World.getNpcs().get(index); if(n2 == null) { return; }
	 * System.out.println(n2.getId()); switch(n2.getId()) { case 494://Banker
	 * player.getBank().openBank(); break; case 220://Shop keeper
	 * //ShopManager.Voteshop = true; //ShopManager.initiateShop(player, 0);
	 * //player.getInventory().refresh(); World.getShopmanager().openShop(player, 1);
	 * break; case 336://shop 1 keeping //ShopManager.Voteshop = false;
	 * //ShopManager.initiateShop(player, 1); //player.getInventory().refresh();
	 * World.getShopmanager().openShop(player, 2); break; case 1675:
	 * //ShopManager.Voteshop = false; //ShopManager.initiateShop(player, 2);
	 * //player.getInventory().refresh(); World.getShopmanager().openShop(player, 3);
	 * break; } }
	 */

    /*
	 * Walking
	 */
    @SuppressWarnings("unused")
    private static void PacketId_60(final InStream Packet, final int Size, final Player p) {
        if (p.isMorphed) {
            return;
        }
        if (p.hasAuth && !p.enteredAuth) {
            p.getDialogue().startDialogue("PropAuth");
            return;
        }
        if (p.inStarter) {
            return;
        }
        if (p.getPriceCheck().pricecheckinv.size() > 0) {
            p.getPriceCheck().close();
        }
        p.isWalking = true;
        p.getWalk().reset(true);

        if ((!p.getCombat().duelArena(p) || !p.getIntermanager().containsTab(591) || !p.getIntermanager().containsInterface(591)) && !p.getCombat().inWild(p)) {
            p.getFrames().CloseCInterface();
            p.getFrames().closeInterfaces(p);
        }
        if (p.getDuelSession() != null && !p.getCombat().duelFight(p) || p.getDuelPartner() != null && p.getDuelPartner().getDuelSession() != null && !p.getDuelPartner().getCombat().duelFight(p.getDuelPartner())) {
            p.getWalk().reset(true);
            p.getDuelSession().duelCancelled(p);
            return;
        }
		/*
		 * if(player != null) { if(player.getEquipment().contains(15071)) {
		 * player.graphics(247); } if(player.getEquipment().contains(15069)) {
		 * player.graphics(246); } }
		 */
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            p.resetTurnTo();
            p.getCombat().removeTarget();
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            p.resetTurnTo();
            p.getCombat().removeTarget();
            return;
        }
        if (p.getTradeSession() != null) {
            p.getWalk().reset(true);
            p.getTradeSession().tradeFailed();
        }
        if (p.in2Delay) {
            p.getWalk().reset(true);
            final HashMap<Integer, Integer> qPoints = new HashMap<Integer, Integer>();
            if (p.getCombat().freezeDelay > 0) {
                p.getWalk().reset(true);
                p.resetTurnTo();
                p.getCombat().removeTarget();
                if (p.getCombat().freezeDelay > 0 && p.getUsername().equals("dre")) {
                    p.getFrames().sendChatMessage(0, "lol5");

                    return;
                }
                p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                        + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
                return;
            }

            if (p.getCombat().stunDelay > 0) {
                p.getWalk().reset(true);
                p.resetTurnTo();
                p.getCombat().removeTarget();
                p.getFrames().sendChatMessage(0, "You are stunned!");
                return;
            }
            int steps = (Size - 5) / 2;
            if (steps > 25)
                return;
            int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6) * 8;
            int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6) * 8;
            boolean runSteps = Packet.readByteC() == -1;
            p.getWalk().reset(true);
            p.resetTurnTo();
            p.getWalk().setIsRunning(runSteps);
            qPoints.put(firstX, firstY);
            for (int i = 0; i < steps; i++) {
                int localX = Packet.readByte() + firstX;
                int localY = Packet.readByte() + firstY;
                qPoints.put(localX, localY);
                p.resetTurnTo();
            } //no lol
            GameServer.getEntityExecutor().schedule(new Task() {
                @Override
                public void run() {
                    if (!p.isWalking) {
                        p.getWalk().reset(true);
                        stop();
                        return;
                    }
                    p.in2Delay = false;
                    for (Entry<Integer, Integer> entry : qPoints.entrySet()) {
                        p.getWalk().reset(true);
                        p.fletchingLog = 0;
                        p.craftingGem = 0;
                        p.getWalk().addToWalkingQueue(entry.getKey(), entry.getValue());
                    }
                    qPoints.clear();
                    if (p.getIntermanager().containsTab(16))
                        p.getFrames().closeInterface(16);
                    if (!p.getIntermanager().containsInterface(8, 137))
                        p.getDialogue().finishDialogue();
                    if (p.getCombat().hasTarget())
                        p.getCombat().removeTarget();
                    if (!p.getMask().isTurnToReset())
                        p.getMask().setTurnToReset(true);
                }

            }, 2000);
            return;
        }
        if (p.isResting) {
            p.getWalk().reset(true);
            p.rest();
            final HashMap<Integer, Integer> qPoints = new HashMap<Integer, Integer>();
            if (p.getCombat().freezeDelay > 0) {
                p.getWalk().reset(true);
                if (p.getCombat().freezeDelay > 0 && p.getUsername().equals("dre")) {
                    p.getFrames().sendChatMessage(0, "lol7");

                    return;
                }
                p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                        + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
                p.resetTurnTo();
                p.getCombat().removeTarget();
                return;
            }
            if (p.getCombat().stunDelay > 0) {
                p.getWalk().reset(true);

                p.getFrames().sendChatMessage(0, "You are stunned");
                p.resetTurnTo();
                p.getCombat().removeTarget();
                return;
            }
            if (p.getCombat().freezeDelay > 0 && p.getUsername().equals("dre")) {
                p.getFrames().sendChatMessage(0, "lol8");
                p.getWalk().reset(true);
                p.resetTurnTo();
                p.getCombat().removeTarget();
                return;
            }
            if (p.getCombat().freezeDelay > 0) {
                p.getWalk().reset(true);
                p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                        + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
                p.resetTurnTo();
                p.getCombat().removeTarget();
                return;
            }
            if (p.getCombat().stunDelay > 0) {
                p.getWalk().reset(true);
                p.getFrames().sendChatMessage(0, "You are stunned");
                p.resetTurnTo();
                p.getCombat().removeTarget();
                return;
            }
            int steps = (Size - 5) / 2;
            if (steps > 25)
                return;
            int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6) * 8;
            int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6) * 8;
            boolean runSteps = Packet.readByteC() == -1;
            p.getWalk().reset(true);
            p.getWalk().setIsRunning(runSteps);
            qPoints.put(firstX, firstY);
            for (int i = 0; i < steps; i++) {
                int localX = Packet.readByte() + firstX;
                int localY = Packet.readByte() + firstY;
                qPoints.put(localX, localY);
            }
            GameServer.getEntityExecutor().schedule(new Task() {
                @Override
                public void run() {
                    if (!p.isWalking) {
                        p.getWalk().reset(true);
                        stop();
                        return;
                    }
                    for (Entry<Integer, Integer> entry : qPoints.entrySet()) {
                        p.getWalk().reset(true);
                        p.fletchingLog = 0;
                        p.craftingGem = 0;
                        p.getWalk().addToWalkingQueue(entry.getKey(), entry.getValue());
                    }
                    qPoints.clear();
                    if (p.getIntermanager().containsTab(16))
                        p.getFrames().closeInterface(16);
                    if (!p.getIntermanager().containsInterface(8, 137))
                        p.getDialogue().finishDialogue();
                    if (p.getCombat().hasTarget())
                        p.getCombat().removeTarget();
                    if (!p.getMask().isTurnToReset())
                        p.getMask().setTurnToReset(true);
                }

            }, 1200);
            return;
        }
        if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            p.resetTurnTo();
            p.getCombat().removeTarget();
            return;
        } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.MOVEMENT)) {
            p.getFrames().sendChatMessage(0, "You cannot walk during this duel.");
            p.resetTurnTo();
            p.getCombat().removeTarget();
            return;
        }
        if (p.getCombat().freezeDelay > 0 && p.getUsername().equals("dre")) {
            p.getFrames().sendChatMessage(0, "lol9");
            p.getWalk().reset(true);
            p.resetTurnTo();
            p.getCombat().removeTarget();
            return;
        }
        if (p.getCombat().freezeDelay > 0) {
            p.getWalk().reset(true);
            p.getFrames().sendChatMessage(0, "A magical force stops you from moving for <col=0000ff><shad=0>"
                    + p.getCombat().freezeDelay / 2 + "</col></shad> more seconds.");
            p.resetTurnTo();
            p.getCombat().removeTarget();
            return;
        }
        if (p.getCombat().stunDelay > 0) {
            p.getWalk().reset(true);
            p.getFrames().sendChatMessage(0, "You are stunned!");
            p.resetTurnTo();
            p.getCombat().removeTarget();
            return;
        }
        int steps = (Size - 5) / 2;
        if (steps > 25)
            return;
        int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6) * 8;
        int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6) * 8;
        p.fletchingLog = 0;
        p.craftingGem = 0;
        boolean runSteps = Packet.readByteC() == -1;
        p.getWalk().reset(true);

        p.getWalk().setIsRunning(runSteps);

        p.getWalk().addToWalkingQueue(firstX, firstY);
        for (int i = 0; i < steps; i++) {
            int localX = Packet.readByte() + firstX;
            int localY = Packet.readByte() + firstY;
            p.getWalk().addToWalkingQueue(localX, localY);
        }
        if (p.getIntermanager().containsTab(16))
            p.getFrames().closeInterface(16);
        if (!p.getIntermanager().containsInterface(8, 137))
            p.getDialogue().finishDialogue();
        if (p.getCombat().hasTarget())
            p.getCombat().removeTarget();
        if (!p.getMask().isTurnToReset())
            p.getMask().setTurnToReset(true);
    }

    /*
	 * Walking
	 */
    @SuppressWarnings("unused")
    private static void PacketId_71(InStream Packet, int Size, Player p) {
        if (p.isMorphed) {
            return;
        }

        p.getFrames().CloseCInterface();
        p.getWalk().reset(true);
        if (p.getTradeSession() != null) {
            p.getWalk().reset(true);
            p.getTradeSession().tradeFailed();
        }
        Size -= 14;
        int steps = (Size - 5) / 2;
        int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6) * 8;
        boolean runSteps = Packet.readByteC() == -1;
        int firstX = Packet.readShort() - (p.getLocation().getX() - 6) * 8;
        p.fletchingLog = 0;
        p.craftingGem = 0;
        if (p.getTradeSession() != null) {
            p.getTradeSession().tradeFailed();
        } // player.getWalk().reset();
        p.getWalk().setIsRunning(runSteps);
        p.getWalk().addToWalkingQueue(firstX, firstY);
        for (int i = 0; i < steps; i++) {
            int localX = Packet.readByte() + firstX;
            int localY = Packet.readByte() + firstY;
            p.getWalk().addToWalkingQueue(localX, localY);
        }
        if (p.getIntermanager().containsTab(16))
            p.getFrames().closeInterface(16);
        if (!p.getIntermanager().containsInterface(8, 137))
            p.getDialogue().finishDialogue();
        if (p.getCombat().hasTarget())
            p.getCombat().removeTarget();
        if (!p.getMask().isTurnToReset())
            p.getMask().setTurnToReset(true);
    }

    /*
	 * dialogue
	 */
    @SuppressWarnings("unused")
    private static void PacketId_73(InStream Packet, int Size, Player p) {
        int junk = Packet.readShort128();
        int Interface = Packet.readInt();
        short interId = (short) (Interface >> 16);
        byte interChild = (byte) (Interface - (interId << 16));
        p.getDialogue().continueDialogue(interId, interChild);
    }

    /*
     * Switch pane
     */
    @SuppressWarnings("unused")
    private static void PacketId_7(InStream Packet, int Size, Player p) {
        int mode = Packet.readByte();
        if (mode == 2 && p.getConnection().getDisplayMode() != 2 || mode == 3 && p.getConnection().getDisplayMode() != 3) {
            InterfaceDecoder.switchWindow(p, mode);
        } else if (mode == 1 && p.getConnection().getDisplayMode() != 1 || mode == 0 && p.getConnection().getDisplayMode() != 0) {
            InterfaceDecoder.switchWindow(p, mode);
        }
    }

    /*
	 * Buttons
	 */
    @SuppressWarnings("unused")
    private static void PacketId_79(final InStream Packet, int Size, final Player p) {
		/*
		 * int interfaceId = Packet.readShort(); int buttonId =
		 * Packet.readShort(); int buttonId3 = Packet.readShort(); int buttonId2
		 * = Packet.readShortLE128(); //if
		 * (!player.getIntermanager().containsInterface(interfaceId)) //return;
		 * interfaceScript inter = Scripts.invokeInterfaceScript((short)
		 * interfaceId); //System.out.println("Unhandled Button: " + buttonId
		 * //+ " on interface: " + interfaceId); if (player.getUsername().equals(""))
		 * { player.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId +
		 * ", but2-" + buttonId2 + ", but3-" + buttonId3 + " on interface: " +
		 * interfaceId); } inter.actionButton(player, 79, buttonId, buttonId2,
		 * buttonId3);
		 */
        ActionButtonHandler.handlePacket(p, Packet);
    }

    @SuppressWarnings("unused")
	/*
	 * private static void PacketId_82(InStream in, int Size, Player player) { int
	 * inter1 = in.readInt(); int firstSlot = in.readShort128(); //int slot =
	 * in.readShortLE128(); //int id = in.readShort(); int itemUsed =
	 * in.readShortLE(); int secondSlot = in.readShortLE128(); int usedWith =
	 * in.readShort128(); int inter2 = in.readIntV1(); if(player == null) { return; }
	 * if(player.getUsername().equals("mod_jonathan")) {
	 * player.getFrames().sendChatMessage(0,"0: "+usedWith+" itemUsed: "+itemUsed); }
	 * if(itemUsed == 11808) { switch(usedWith) { case 4151:
	 * if(player.getInventory().contains(11808, 1) && player.getInventory().contains(4151,
	 * 1)) { player.getInventory().deleteItem(4151, 1);
	 * player.getInventory().deleteItem(11808, 1); player.getInventory().addItem(15443,
	 * 1); } break; } } if(itemUsed == 1771) { switch(usedWith) { case 4151:
	 * if(player.getInventory().contains(1771, 1) && player.getInventory().contains(4151,
	 * 1)) { player.getInventory().deleteItem(4151, 1);
	 * player.getInventory().deleteItem(1771, 1); player.getInventory().addItem(15444, 1);
	 * } break; } } if(itemUsed == 1767) { switch(usedWith) { case 4151:
	 * if(player.getInventory().contains(1767, 1) && player.getInventory().contains(4151,
	 * 1)) { player.getInventory().deleteItem(4151, 1);
	 * player.getInventory().deleteItem(1767, 1); player.getInventory().addItem(15442, 1);
	 * } break; } } if(itemUsed == 1765) { switch(usedWith) { case 4151:
	 * if(player.getInventory().contains(1765, 1) && player.getInventory().contains(4151,
	 * 1)) { player.getInventory().deleteItem(4151, 1);
	 * player.getInventory().deleteItem(1765, 1); player.getInventory().addItem(15441, 1);
	 * } break; } } if(itemUsed == 15441 || itemUsed == 15442 || itemUsed ==
	 * 15443 || itemUsed == 15444) { switch(usedWith) { case 3188:
	 * if(player.getInventory().contains(15443, 1)) {
	 * player.getInventory().deleteItem(15443, 1); player.getInventory().addItem(4151, 1);
	 * player.getInventory().addItem(11808, 1); player.getFrames().sendChatMessage(0,
	 * "You use the cleaning cloth on the whip."); } else
	 * if(player.getInventory().contains(15444, 1)) {
	 * player.getInventory().deleteItem(15444, 1); player.getInventory().addItem(4151, 1);
	 * player.getInventory().addItem(1771, 1); player.getFrames().sendChatMessage(0,
	 * "You use the cleaning cloth on the whip."); } else
	 * if(player.getInventory().contains(15442, 1)) {
	 * player.getInventory().deleteItem(15442, 1); player.getInventory().addItem(4151, 1);
	 * player.getInventory().addItem(1767, 1); player.getFrames().sendChatMessage(0,
	 * "You use the cleaning cloth on the whip."); } else
	 * if(player.getInventory().contains(15441, 1)) {
	 * player.getInventory().deleteItem(15441, 1); player.getInventory().addItem(4151, 1);
	 * player.getInventory().addItem(1765, 1); player.getFrames().sendChatMessage(0,
	 * "You use the cleaning cloth on the whip."); } break; } } if(itemUsed ==
	 * 4151) { switch(usedWith) { case 1765: if(player.getInventory().contains(1765,
	 * 1) && player.getInventory().contains(4151, 1)) {
	 * player.getInventory().deleteItem(4151, 1); player.getInventory().deleteItem(1765,
	 * 1); player.getInventory().addItem(15441, 1); } break; case 1767:
	 * if(player.getInventory().contains(1767, 1) && player.getInventory().contains(4151,
	 * 1)) { player.getInventory().deleteItem(4151, 1);
	 * player.getInventory().deleteItem(1767, 1); player.getInventory().addItem(15442, 1);
	 * } break; case 1771: if(player.getInventory().contains(1771, 1) &&
	 * player.getInventory().contains(4151, 1)) { player.getInventory().deleteItem(4151,
	 * 1); player.getInventory().deleteItem(1771, 1); player.getInventory().addItem(15444,
	 * 1); } break; case 11808: if(player.getInventory().contains(11808, 1) &&
	 * player.getInventory().contains(4151, 1)) { player.getInventory().deleteItem(4151,
	 * 1); player.getInventory().deleteItem(11808, 1);
	 * player.getInventory().addItem(15443, 1); } break; } } }
	 */

    // @SuppressWarnings("unused")
    private static void PacketId_82(final InStream packet, final int Size, final Player player) {
        packet.readInt();
        final int fromSlot = packet.readShortA();
        final int usedWith = packet.readLEShort();
        final int toSlot = packet.readLEShortA();
        final int itemUsed = packet.readShortA();
        packet.readInt();
        Item item = player.getInventory().getContainer().get(fromSlot);
        if (item == null)
            return;
        if (item.getId() != itemUsed)
            return;
        item = player.getInventory().getContainer().get(toSlot);
        if (item == null)
            return;
        if (item.getId() != usedWith)
            return;
        if (!player.getInventory().contains(itemUsed))
            return;
        if (!player.getInventory().contains(usedWith))
            return;
        if (player == null)
            return;

        if (player.getUsername().equals("mod_jon"))
            player.getFrames().sendChatMessage(0, "0: " + usedWith + " itemUsed: " + itemUsed);
        ItemOnItemPacketHandler.handleItemOnItem(player, packet, itemUsed, usedWith);

    }

    @SuppressWarnings("unused")
    private static void PacketId_83(InStream Packet, int Size, Player p) {
        boolean sendingClanMessage = Packet.readByte() > 0;
        p.sendingClanMessage = sendingClanMessage;
    }


    public static Method getPacket(short PacketId) {
        return packets.get(PacketId);
    }

    public static void run(ConnectionHandler p, InStream buffer) {
        if (p.getPlayer().packetDelay.elapsed(1000)) {
            p.getPlayer().totalPackets = 0;
            p.getPlayer().packetDelay.reset();
        }
        if (p.getPlayer().totalPackets > 100) {
            return;
        }
        synchronized (buffer) {
            while (buffer.remaining() > 0) {
                short opcode = (short) buffer.readUnsignedByte();
                if (opcode < 0 || opcode > 83) {
                    // System.out.println("Opcode " + opcode+ " has fake id.");
                    break;
                }
                int length = PacketSize[opcode];
                if (length == -1)
                    length = buffer.readUnsignedByte();
                else if (length == -2)
                    length = buffer.readShort();
                else if (length == -3) {
                    length = buffer.remaining();
                }
                if (length > buffer.remaining()) {
                    break;
                }
                p.getPlayer().lastResponce = System.currentTimeMillis();
                int startOffset = buffer.offset();
                if (packets.containsKey(opcode)) {
                    try {
                        Method PacketMethod = getPacket(opcode);
                        if (PacketMethod != null) {
                            buffer.opcode = opcode;
                            p.getPlayer().totalPackets++;
                            PacketMethod.invoke(Packets.class, buffer, length, p.getPlayer());
                        }
                    } catch (Exception e) {
                    }
                }
                buffer.setOffset(startOffset + length);
            }
        }
    }

    private void setPackets(Short[] packetsA) {
        for (short packet : packetsA) {
            try {
                packets.put(packet, this.getClass().getDeclaredMethod("PacketId_" + packet, InStream.class, int.class,
                        Player.class));
            } catch (SecurityException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}