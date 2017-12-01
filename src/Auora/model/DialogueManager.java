package Auora.model;

import Auora.model.player.Player;
import Auora.util.Misc;

public class DialogueManager {

    public static final int REALLY_SAD = 9760, SAD = 9765, DEPRESSED = 9770, WORRIED = 9775, SCARED = 9780, MEAN_FACE = 9785,
            MEAN_HEAD_BANG = 9790, EVIL = 9795, WHAT_THE_CRAP = 9800, CALM = 9805, CALM_TALK = 9810, TOUGH = 9815, SNOBBY = 9820,
            SNOBBY_HEAD_MOVE = 9825, CONFUSED = 9830, DRUNK_HAPPY_TIRED = 9835, TALKING_ALOT = 9845, HAPPY_TALKING = 9850, BAD_ASS = 9855,
            THINKING = 9860, COOL_YES = 9864, LAUGH_EXCITED = 9851, SECRELTY_TALKING = 9838;

    public static void proceedDialogue(Player player, int stage) {
        switch (stage) {
            case 1:
                sendNpcDialouge(player, "Knight", 660, 2, "Hello @PLAYER_NAME@!", "How can I help you?");
                break;
            case 2:
                sendOptionDialogue(player, "View shop", "Nevermind, thanks!");
                player.optionsStage = 1;
                break;
            case 10:
                sendDialouge(player, "Sir Lancelot", HAPPY_TALKING, 239, 11, "Hi there @PLAYER_NAME@!", "I sell you things for PKP!", "Would you like to see what you can buy?");
                break;
            case 11:
                sendDialouge(player, "Sir Lancelot", HAPPY_TALKING, -1, 12, "Sure, let's see what you have!");
                break;

            case 520:
                sendDialouge(player, "Shop-Keeper", HAPPY_TALKING, 520, 521, "Hi there @PLAYER_NAME@!", "You can sell all of your items to me.", "Here, take a look at what I have!");
                break;

            case 2000:
                sendDialouge(player, "Rune-Force Guide", HAPPY_TALKING, 260, 2001, "Welcome to Rune-Force @PLAYER_NAME@!", "I am the guide for all things Rune-Force, and", "will be happy to introduce you.");
                break;

            case 2001:
                sendDialouge(player, "Rune-Force Guide", HAPPY_TALKING, 260, 2002, "This is a Player-Killing server!", "If you prefer to not lose your stuff, just", "stay in the Safe PVP zone!");
                break;

            case 2002:
                sendDialouge(player, "Rune-Force Guide", HAPPY_TALKING, 260, 2003, "Now, would you like a melee,", "ranged, or magic set?");
                break;

            case 2003:
                sendOptionDialogue(player, "Melee set", "Ranged set", "Magic set");
                player.optionsStage = 2;
                break;

            case 2004:
                player.getInventory().addItem(995, 2500000);
                player.getInventory().addItem(392, 2500);
                sendDialouge(player, "Rune-Force Guide", HAPPY_TALKING, 260, 2005, "Excellent! Good choice.", "If you need to make any money, just", "check out the help guide!");
                break;

            case 2005:
                sendDialouge(player, "Rune-Force Guide", HAPPY_TALKING, 260, 2006, "Perhaps you should get out and start", "killing some n00bs!", "Good luck @PLAYER_NAME@!");
                break;

            case 2006:
                sendDialouge(player, "Sir Lancelot", HAPPY_TALKING, -1, 2007, "Thank you!", "How will I be able to talk", "to you again?");
                break;

            case 2007:
                sendDialouge(player, "Rune-Force Guide", HAPPY_TALKING, 260, 100, "Just type ::help!", "If you need further help, I'm sure", "players will be able to help.");
                break;

            case 521:
                player.getFrames().CloseCInterface();
                World.getShopManager().openShop(player, 9);
                break;

            case 12:
                player.getFrames().CloseCInterface();
                World.getShopManager().openShop(player, 10);
                break;

            case 100:
                player.getFrames().CloseCInterface();
                break;

            default:
                player.getFrames().CloseCInterface();
                break;
        }

    }

    public static void sendNpcDialouge(Player player, String name, int face, int nextStage, String... dialouge) {
        if (dialouge.length == 0 || dialouge.length > 4) {
            return;
        }
        int interfaceId = (face == -1 ? 63 : 240) + dialouge.length;
        int index = 4;
        player.getFrames().sendString(player, interfaceId, 3, name);
        for (String s : dialouge) {
            player.getFrames().sendString(player, interfaceId, index, s.replaceAll("@PLAYER_NAME@", Misc.formatPlayerNameForDisplay(player.getUsername())));
            index++;
        }
        player.getFrames().sendChatboxInterface(player, interfaceId);
        player.getFrames().sendNpcOnInterface(interfaceId, 2, face);
        player.getFrames().sendInterAnimation(9830, interfaceId, 2);

        player.dialogueStage = nextStage;
    }

    public static void sendDialouge(Player player, String name, int emote, int face, int nextStage, String... dialouge) {
        if (dialouge.length == 0 || dialouge.length > 4) {
            return;
        }
        int interfaceId = (face == -1 ? 63 : 240) + dialouge.length;
        int index = 4;
        player.getFrames().sendString(player, interfaceId, 3, face == -1 ? Misc.formatPlayerNameForDisplay(player.getUsername()) : name);
        for (String s : dialouge) {
            player.getFrames().sendString(player, interfaceId, index, s.replaceAll("@PLAYER_NAME@", Misc.formatPlayerNameForDisplay(player.getUsername())));
            index++;
        }
        player.getFrames().sendChatboxInterface(player, interfaceId);
        player.getFrames().sendEntityOnInterface(face == -1, face, interfaceId, 2);
        player.getFrames().sendInterAnimation(emote, interfaceId, 2);

        player.dialogueStage = nextStage;
    }

    public static void sendOptionDialogue(Player player, String... dialouge) {
        if (dialouge.length < 2 || dialouge.length > 5) { //cant have 1 option
            return;
        }
        int interfaceId = 224 + (dialouge.length * 2);
        int index = 2;
        for (String string : dialouge) {
            player.getFrames().sendString(string, interfaceId, index);
            index++;
        }
        player.getFrames().sendChatboxInterface(player, interfaceId);

        player.isOptions = true;
    }
}
