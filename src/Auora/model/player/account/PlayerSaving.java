package Auora.model.player.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.util.Misc;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerSaving {

    public static void save(Player player, boolean backup) {
        if (player.newPlayer)
            return;
        // Create the path and file objects.
        //String backupPath = "./backups/";
/*        int amountInDir = new File(backupPath).list().length;
        World.saveInt = amountInDir;*/
        DateFormat df = new SimpleDateFormat("dd-MM-yy");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        Path path;
        // System.out.println(World.saveInt+" SAVEINT "+player.getUsername());
        if (backup) {
            path = Paths.get("./backups/" + date + "/backup-" + World.saveInt, Misc.formatPlayerNameForDisplay(player.getDisplayName()) + ".json");
        } else {
            path = Paths.get("./characters/", Misc.formatPlayerNameForDisplay(player.getDisplayName()) + ".json");
        }

        File file = path.toFile();
        file.getParentFile().setWritable(true);

        // Attempt to make the player save directory if it doesn't
        // exist.
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (SecurityException e) {
                System.out.println("Unable to create directory for player data!");
            }
        }
        try (FileWriter writer = new FileWriter(file)) {

            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();

            object.addProperty("username", player.getUsername());
            object.addProperty("doublecash", new Integer(player.doublecash));
            object.addProperty("Augury", new Integer(player.Augury));
            object.addProperty("Rigour", new Integer(player.Rigour));
            object.addProperty("display-name", Misc.formatPlayerNameForDisplay(player.getDisplayName()));
            object.addProperty("password", player.getPassword());
            object.addProperty("gotforcepw", new Integer(player.gotforcepw));
            object.addProperty("staff-rights", player.getStaffRights().toString());
            object.addProperty("special-player", player.specialPlayer);
            object.addProperty("position-x", new Integer(player.getLocation().getX()));
            object.addProperty("position-y", new Integer(player.getLocation().getY()));
            object.addProperty("position-z", new Integer(player.getLocation().getZ()));
            object.addProperty("donator-rank", player.getDonatorRights().toString());
            object.addProperty("dicer-rank", new Integer(player.dicerRank));
            object.addProperty("ip-address", player.getIpAddress());
            object.addProperty("serial-address", player.getSerialAddress());
            object.addProperty("toggle-name", player.getToggleName());
            object.addProperty("item-reset-1", new Integer(player.item_reset_1));
            object.addProperty("item-reset-3", new Integer(player.item_reset_3));
            object.addProperty("item-reset-4", new Integer(player.item_reset_4));
            object.addProperty("item-reset-5", new Integer(player.item_reset_5));
            object.addProperty("item-reset-6", new Integer(player.item_reset_6));
            object.addProperty("item-reset-7", new Integer(player.item_reset_7));
            object.addProperty("item-reset-8", new Integer(player.item_reset_8));
            object.addProperty("has-auth", new Boolean(player.hasAuth));
            object.addProperty("weekendDonor", new Integer(player.weekendDonor));
            object.addProperty("entered-auth", new Boolean(player.enteredAuth));
            object.addProperty("auth-code", new String(player.authCode));
            object.addProperty("clan", player.currentClan);
            object.addProperty("titleset", "");
            object.addProperty("titleset2", "");
            object.addProperty("title", player.getTitles().getTitle());
            object.addProperty("title-color", player.getTitles().getColor());
            object.addProperty("title-shad", player.getTitles().getShad());
            object.addProperty("pk-points", new Integer(player.pkPoints));
            object.addProperty("donation-points", new Integer(player.donationPoints));
            object.addProperty("vote-points", new Integer(player.votePoints));
            object.addProperty("skilling-points", new Integer(player.skillingPoints));
            object.addProperty("dung-points", new Integer(player.dungTokens));
            object.addProperty("killstreak", new Integer(player.killstreak));
            object.addProperty("dangerous-kills", new Integer(player.dangerousKills));
            object.addProperty("deaths", new Integer(player.deaths));
            object.addProperty("highest-killstreak", new Integer(player.highestKs));
            object.addProperty("unlimited-prayer", new Integer(player.unlimitedPrayer));
            object.addProperty("hours-played", new Integer(player.hoursPlayed));
            object.addProperty("total-votes", new Integer(player.totalVotes));
            object.addProperty("total-stakes", new Integer(player.totalStakes));
            object.addProperty("stake-wins", new Integer(player.stakeWins));
            object.addProperty("stake-losses", new Integer(player.stakeLosses));
            object.addProperty("stake-ties", new Integer(player.stakeTies));
            object.addProperty("teleblock-delay", new Integer(player.teleblockDelay));
            object.addProperty("teleblock-immune-delay", new Integer(player.teleblockimmuneDelay));
            object.addProperty("new-player", new Boolean(player.newPlayer));
            object.addProperty("yell-disabled", new Boolean(player.yellDisabled));//newstuff
            object.addProperty("yell-timer", new Integer(player.yellTimer));
            object.addProperty("update-stall", new Boolean(player.updateStall));//newstuff
            object.add("equipment", builder.toJsonTree(player.getEquipment().getEquipment().getItems()));
            object.add("inventory", builder.toJsonTree(player.getInventory().inventory.getItems()));
            object.addProperty("hitpoints", new Short(player.getSkills().getHitPoints()));
            object.add("skill-levels", builder.toJsonTree(player.getSkills().level));
            object.add("skill-xp", builder.toJsonTree(player.getSkills().xp));
            object.add("bank", builder.toJsonTree(player.getBank().bank.getItems()));
            object.add("tab-start-slots", builder.toJsonTree(player.getBank().tabStartSlot));
            object.addProperty("male", new Boolean(player.getAppearence().isMale()));
            object.addProperty("gender", new Byte(player.getAppearence().getGender()));
            object.add("look", builder.toJsonTree(player.getAppearence().getLook()));
            object.add("colour", builder.toJsonTree(player.getAppearence().getColour()));
            object.add("quick-prayers", builder.toJsonTree(player.getPrayer().getQuickPrayers()));
            object.add("friends-list", builder.toJsonTree(player.getFriends()));
            object.addProperty("ancient-curses", new Boolean(player.getPrayer().isAncientCurses()));
            object.addProperty("spellbook", new Integer(player.spellbook));
            object.addProperty("spec-timer", new Integer(player.specTimer));
            object.addProperty("ep-amount", new Double(player.epAmount));
            object.addProperty("xp-gained", new Integer(player.xpGained));
            object.addProperty("dragonfire-hit", new Integer(player.dfsHit));
            object.addProperty("last-dfs", new Long(player.lastDfs));
            object.addProperty("fountain", new Integer(player.fountain));
            object.addProperty("primal-timer", new Integer(player.primalTimer));
            object.addProperty("spec-pot", new Integer(player.specPot));
            object.addProperty("tome-timer", new Integer(player.tomeTimer));
            object.addProperty("magic-resist", new Integer(player.magicResist));
            object.addProperty("safe-kills", new Integer(player.safeKills));
            object.addProperty("overload", new Integer(player.overload));
            object.addProperty("bonus-magic-damage", new Integer(player.bonusMagicDamage));
            object.addProperty("primal-hit-points", new Integer(player.primalHitpoints));
            object.addProperty("special-percentage", new Integer(player.getCombatDefinitions().getSpecpercentage()));
            if (player.getWalk() != null) {
                object.addProperty("running", new Boolean(player.getWalk().isRunning()));
            }
            writer.write(builder.toJson(object));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            // An error happened while saving.
            //GameServer.getLogger().log(Level.WARNING,
            //"An error has occured while saving a character file!", e);
        }
    }

    public static boolean playerExists(String p) {
        p = Misc.formatPlayerName(p);
        return new File("./characters/" + Misc.formatPlayerNameForDisplay(p) + ".json").exists();
    }
}
