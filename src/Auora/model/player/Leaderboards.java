package Auora.model.player;

import Auora.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

public class Leaderboards {

    public static void update(Player player) throws IOException {
        // player.sm("Doing");
        if (player == null) {
            return;
        }
        if (GameServer.voteDisabled == 1) {
            System.out.println("Leaderboards disabled.");
            return;
        }//FUCK I KNOW WHY IM SUCH A RETARDFUCK u 105 sure? almost certain... why would i get a null
        int rank = 0;

        // 1 = mod, 2 = admin, 3 = reg, 4 = extreme, 5 = super, 6 = godlike, 7 =
        // support
        // ^^ also dont know what you use for your rankings but i left the
        // inputs here.
        try {
            if (player.getStaffRights() == StaffRights.ADMINISTRATOR) {
                rank = 2;
            } else if (player.getStaffRights() == StaffRights.MODERATOR) {
                rank = 1;
            } else if (player.getStaffRights() == StaffRights.SUPPORT) {
                rank = 7; // urm actually admins wont show anyway, not needed.
                // Nice.
            } else if (player.getDonatorRights() == DonatorRights.PREMIUM) {
                rank = 3;
            } else if (player.getDonatorRights() == DonatorRights.EXTREME) {
                rank = 4;
            } else if (player.getDonatorRights() == DonatorRights.LEGENDARY) {
                rank = 5;

            }
        } catch (Exception e) {
            System.err.println("Error setting rank for : " + player.getUsername());
        }

        String name = player.getUsername().replaceAll(" ", "_");
        int kills = player.safeKills + player.dangerousKills;
        int deaths = player.deaths;
        double kdr;
        try {
            kdr = kills / deaths;
        } catch (ArithmeticException e) {
            //System.err.println("Error calculating KDR: " + player.getUsername());
            kdr = 0;
        }
        String kd = kdr + "";

        // Can you make this display like 5.25 instead of 5
        // it doesn't show decimals
        int safe = player.safeKills;
        if (safe < 0)
            safe = 0;
        int combatLevel = player.getSkills().getCombatLevel();
        String uniquePass = "SSleaderboards2k16";

        try {
            String urlStr = "";

            urlStr = "http://spawnscape.net/hiscores/updatehighscores.php?" + "pass=" + uniquePass + "&username=" + name
                    + "&kills=" + kills + "&deaths=" + deaths + "&kdr=" + kd.substring(0, 3) + "&pkpts="
                    + player.getPkPoints() + "&safepk=" + safe + "&cblvl=" + combatLevel + "&rights=" + rank

            ;
            int total = 0;
            int totalxp = 0;
            // fix ranged input ######
            for (int i = 0; i < player.getSkills().SKILL_COUNT - 4; i++) {
                String skill = player.getSkills().SKILL_NAME[i].toLowerCase();
                int skillLvl = player.getSkills().getLevel(i);
                if (skillLvl > 99)
                    skillLvl = 99;
                int skillXp = player.getSkills().getXPForLevel(skillLvl);
                if (skill.equals("hitpoints"))
                    skill = "constitution";
                total = total + skillLvl;
                totalxp = totalxp + skillXp;
                urlStr = urlStr + "&" + skill + "=" + skillLvl + "&" + skill + "xp=" + skillXp;
            }
            urlStr = urlStr + "&total=" + total + "&totalxp=" + totalxp;
            // System.out.println(urlStr);
            URL url = new URL(urlStr);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String string = null;
                string = reader.readLine();
                //System.out.println("HiScores reading for user: " + player.getUsername() + " - " + string);
                switch (string) {
                    case "2":
                        // System.out.println("HiScores updated for user:
                        // "+player.getUsername());
                        break;
                    case "1":
                        System.out.println("HiScores missing field, check url string");
                        break;
                    case "0":
                        System.out.println(
                                "Incorrect password (uniquePass) - Make sure both passwords are the same on webhost and server.");
                        break;
                }
            } catch (ConnectException e) {
                /*e.printStackTrace();*/
                System.err.println("Error connecting - Leaderboards");
            }


        } catch (MalformedURLException e) {
            System.out.println("Error updating leaderboards for " + player.getUsername());
        }

    }

}
