package Auora.model.player.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Auora.GameSettings;
import Auora.model.Item;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.util.Misc;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class PlayerLoading {

    public static boolean accountExists(String name) {
        File player = new File("./characters/" + Misc.formatPlayerNameForDisplay(name) + ".json");
        if (!player.exists()) {
            return false;
        }
        return true;
    }

    public static int getResult(Player player) {

        // Create the path and file objects.
        Path path = Paths.get("./characters/", Misc.formatPlayerNameForDisplay(player.getUsername()) + ".json");
        File file = path.toFile();

        // If the file doesn't exist, we're logging in for the first
        // time and can skip all of this.
        if (!file.exists()) {
            return GameSettings.DISCONNECT;
        }

        // Now read the properties from the json parser.
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = new GsonBuilder().create();
            JsonObject reader = (JsonObject) fileParser.parse(fileReader);

            if (reader.has("username")) {
                player.setUsername(reader.get("username").getAsString());
            }

            if (reader.has("display-name")) {
                player.setDisplayName(reader.get("display-name").getAsString());
            }


            if (reader.has("password")) {
                player.setPassword(reader.get("password").getAsString());
            }

            if (reader.has("gotforcepw")) {
                player.gotforcepw = (reader.get("gotforcepw").getAsInt());
            }

            if (reader.has("doublecash")) {
                player.doublecash = (reader.get("doublecash").getAsInt());
            }
            if (reader.has("Rigour")) {
                player.Rigour = (reader.get("Rigour").getAsInt());
            }
            if (reader.has("Augury")) {
                player.Augury = (reader.get("Augury").getAsInt());
            }
            if (reader.has("staff-rights")) {
                try {
                    player.setPlayerRights(StaffRights.valueOf(reader.get("staff-rights").getAsString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    player.setPlayerRights(StaffRights.PLAYER);
                }
            }
            if (reader.has("special-player")) {
                player.specialPlayer = reader.get("special-player").getAsBoolean();
            }
            if (reader.has("position-x")) {
                player.getLocation().setX(reader.get("position-x").getAsShort());
            }
            if (reader.has("position-y")) {
                player.getLocation().setY(reader.get("position-y").getAsShort());
            }

            if (reader.has("titleset2")) {
                if (reader.has("title")) {
                    player.getTitles().setTitle(reader.get("title").getAsString());
                }
                if (reader.has("title-color")) {
                    player.getTitles().setColor(reader.get("title-color").getAsString());
                }
                if (reader.has("title-shad")) {
                    player.getTitles().setShad(reader.get("title-shad").getAsString());
                }


            }
            if (reader.has("weekendDonor")) {
                if (player.weekendDonor == 1 && player.getDonatorRights() == DonatorRights.PLAYER) {
                    player.setDonatorRights(DonatorRights.WEEKENDDONATOR);
                } else if (player.weekendDonor == 0 && player.getDonatorRights() == DonatorRights.WEEKENDDONATOR) {
                    player.setDonatorRights(DonatorRights.PLAYER);
                }
            }

            if (reader.has("clan")) {
                player.currentClan = reader.get("clan").getAsString();
            }
            if (reader.has("position-z")) {
                player.getLocation().setZ(reader.get("position-z").getAsByte());
            }
//            if (reader.has("running")) {
//                player.getWalk().setIsRunning(reader.get("running").getAsBoolean());
//            }
            if (reader.has("donator-rank")) {
                try {
                    player.setDonatorRights(DonatorRights.valueOf(reader.get("donator-rank").getAsString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    player.setDonatorRights(DonatorRights.PLAYER);
                }
            }
            if (reader.has("donator-rank-ordinal")) {
                player.setDonatorRights(DonatorRights.forId(reader.get("donator-rank-ordinal").getAsInt()));
            }
            if (reader.has("dicer-rank")) {
                player.dicerRank = reader.get("dicer-rank").getAsInt();
            }
            if (reader.has("item-reset-1")) {
                player.item_reset_1 = reader.get("item-reset-1").getAsInt();
            }
            if (reader.has("item-reset-3")) {
                player.item_reset_3 = reader.get("item-reset-3").getAsInt();
            }
            if (reader.has("item-reset-4")) {
                player.item_reset_4 = reader.get("item-reset-4").getAsInt();
            }
            if (reader.has("item-reset-5")) {
                player.item_reset_5 = reader.get("item-reset-5").getAsInt();
            }
            if (reader.has("item-reset-6")) {
                player.item_reset_6 = reader.get("item-reset-6").getAsInt();
            }
            if (reader.has("item-reset-7")) {
                player.item_reset_7 = reader.get("item-reset-7").getAsInt();
            }
            if (reader.has("item-reset-8")) {
                player.item_reset_8 = reader.get("item-reset-8").getAsInt();
            }
            //adding
            if (reader.has("has-auth")) {
                player.hasAuth = reader.get("has-auth").getAsBoolean();
            }
            if (reader.has("auth-entered")) {
                player.enteredAuth = reader.get("auth-entered").getAsBoolean();
            }
            if (reader.has("auth-code")) {
                player.authCode = reader.get("auth-code").getAsString();
            }
            //auth
            if (reader.has("special-percentage")) {
                player.getCombatDefinitions().setSpecpercentage(reader.get("special-percentage").getAsByte());
            }
            if (reader.has("toggle-name")) {
                player.setToggleName(reader.get("toggle-name").getAsBoolean());
            }
            if (reader.has("pk-points")) {
                player.pkPoints = reader.get("pk-points").getAsInt();
            }
            if (reader.has("donation-points")) {
                player.donationPoints = reader.get("donation-points").getAsInt();
            }
            if (reader.has("vote-points")) {
                player.votePoints = reader.get("vote-points").getAsInt();
            }
            if (reader.has("skilling-points")) {
                player.skillingPoints = reader.get("skilling-points").getAsInt();
            }
            if (reader.has("dung-points")) {
                player.dungTokens = reader.get("dung-points").getAsInt();
            }
            if (reader.has("killstreak")) {
                player.killstreak = reader.get("killstreak").getAsInt();
            }
            if (reader.has("dangerous-kills")) {
                player.dangerousKills = reader.get("dangerous-kills").getAsInt();
            }
            if (reader.has("deaths")) {
                player.deaths = reader.get("deaths").getAsInt();
            }
            if (reader.has("highest-killstreak")) {
                player.highestKs = reader.get("highest-killstreak").getAsInt();
            }
            if (reader.has("unlimited-prayer")) {
                player.unlimitedPrayer = reader.get("unlimited-prayer").getAsInt();
            }
            if (reader.has("hours-played")) {
                player.hoursPlayed = reader.get("hours-played").getAsInt();
            }
            if (reader.has("total-votes")) {
                player.totalVotes = reader.get("total-votes").getAsInt();
            }
            if (reader.has("total-stakes")) {
                player.totalStakes = reader.get("total-stakes").getAsInt();
            }
            if (reader.has("stake-wins")) {
                player.stakeWins = reader.get("stake-wins").getAsInt();
            }
            if (reader.has("stake-losses")) {
                player.stakeLosses = reader.get("stake-losses").getAsInt();
            }
            if (reader.has("stake-ties")) {
                player.stakeTies = reader.get("stake-ties").getAsInt();
            }
            if (reader.has("teleblock-delay")) {
                player.teleblockDelay = reader.get("teleblock-delay").getAsInt();
            }
            if (reader.has("teleblock-immune-delay")) {
                player.teleblockimmuneDelay = reader.get("teleblock-immune-delay").getAsInt();
            }
            if (reader.has("tab-start-slots")) {
                player.getBank().tabStartSlot = builder.fromJson(reader.get("tab-start-slots").getAsJsonArray(), int[].class);
            }
            if (reader.has("equipment")) {
                Item[] items = builder.fromJson(reader.get("equipment").getAsJsonArray(), Item[].class);
                player.getEquipment().getEquipment().setItems(items);
            }
            if (reader.has("inventory")) {
                Item[] items = builder.fromJson(reader.get("inventory").getAsJsonArray(), Item[].class);
                player.getInventory().inventory.setItems(items);
            }
            if (reader.has("hitpoints")) {
                player.getSkills().setHitPoints(reader.get("hitpoints").getAsShort());
            }
            if (reader.has("skill-levels")) {
                player.getSkills().level = builder.fromJson(reader.get("skill-levels").getAsJsonArray(), short[].class);
            }
            if (reader.has("skill-xp")) {
                player.getSkills().xp = builder.fromJson(reader.get("skill-xp").getAsJsonArray(), double[].class);
            }
            if (reader.has("bank")) {
                Item[] items = builder.fromJson(reader.get("bank").getAsJsonArray(), Item[].class);
                player.getBank().bank.setItems(items);
            }
            if (reader.has("new-player")) {
                player.newPlayer = reader.get("new-player").getAsBoolean();
            }
            if (reader.has("yell-disabled")) {//newstuff
                player.yellDisabled = reader.get("yell-disabled").getAsBoolean();
            }
            if (reader.has("yell-timer")) {
                player.yellTimer = reader.get("yell-timer").getAsInt();
            }
            if (reader.has("update-stall")) {//newstuff
                player.updateStall = reader.get("update-stall").getAsBoolean();
            }
            if (reader.has("male")) {
                player.getAppearence().setMale(reader.get("male").getAsBoolean());
            }
            if (reader.has("gender")) {
                player.getAppearence().setGender(reader.get("gender").getAsByte());
            }
            if (reader.has("look")) {
                player.getAppearence().setLook(builder.fromJson(reader.get("look").getAsJsonArray(), byte[].class));
            }
            if (reader.has("colour")) {
                player.getAppearence().setColour(builder.fromJson(reader.get("colour").getAsJsonArray(), byte[].class));
            }
            if (reader.has("quick-prayers")) {
                player.getPrayer().quickPrayers = builder.fromJson(reader.get("quick-prayers").getAsJsonArray(), boolean[][].class);
            }
            if (reader.has("ancient-curses")) {
                player.getPrayer().ancientcurses = reader.get("ancient-curses").getAsBoolean();
            }
            if (reader.has("spellbook")) {
                player.spellbook = reader.get("spellbook").getAsInt();
            }
            if (reader.has("friends-list")) {
                player.setFriends(builder.fromJson(reader.get("friends-list").getAsJsonArray(), List.class));
            }
            if (reader.has("spec-timer")) {
                player.specTimer = reader.get("spec-timer").getAsInt();
            }
            if (reader.has("ep-amount")) {
                player.epAmount = reader.get("ep-amount").getAsDouble();
            }
            if (reader.has("xp-gained")) {
                player.xpGained = reader.get("xp-gained").getAsInt();
            }
            if (reader.has("dragonfire-hit")) {
                player.dfsHit = reader.get("dragonfire-hit").getAsInt();
            }
            if (reader.has("last-dfs")) {
                player.lastDfs = reader.get("dragonfire-hit").getAsLong();
            }
            if (reader.has("fountain")) {
                player.fountain = reader.get("fountain").getAsInt();
            }
            if (reader.has("primal-timer")) {
                player.primalTimer = reader.get("primal-timer").getAsInt();
            }
            if (reader.has("unlimited-prayer")) {
                player.unlprayDelay = reader.get("unlimited-prayer").getAsInt();
            }
            if (reader.has("spec-pot")) {
                player.specPot = reader.get("spec-pot").getAsInt();
            }
            if (reader.has("tome-timer")) {
                player.tomeTimer = reader.get("tome-timer").getAsInt();
            }
            if (reader.has("magic-resist")) {
                player.magicResist = reader.get("magic-resist").getAsInt();
            }
            if (reader.has("safe-kills")) {
                player.safeKills = reader.get("safe-kills").getAsInt();
            }
            if (reader.has("overload")) {
                player.overload = reader.get("overload").getAsInt();
            }
            if (reader.has("bonus-magic-damage")) {
                player.bonusMagicDamage = reader.get("bonus-magic-damage").getAsInt();
            }
            if (reader.has("primal-hit-points")) {
                player.primalHitpoints = reader.get("primal-hit-points").getAsInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return GameSettings.LOGIN_OK;
        }
        return GameSettings.LOGIN_OK;
    }
}