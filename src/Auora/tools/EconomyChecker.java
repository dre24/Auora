package Auora.tools;

import Auora.model.Item;
import Auora.model.player.Player;
import Auora.model.player.Prices;
import Auora.model.player.StaffRights;
import Auora.model.player.account.PlayerLoading;
import Auora.rscache.Cache;

import java.io.File;

/**
 * Checks the economy for dupes
 *
 * @Author Jonny
 */
public class EconomyChecker {

    public static void main(String[] args) {
        System.out.println("Starting loading characters...");
        new Cache();
        File folder = new File("./characters");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().contains(".json")) {
                    long bankValue = 0;
                    Player other = new Player(listOfFiles[i].getName().replace(".json", ""));
                    PlayerLoading.getResult(other);
                    if (other.getStaffRights() == StaffRights.OWNER || other.getStaffRights() == StaffRights.ADMINISTRATOR || other.getStaffRights() == StaffRights.GLOBAL_ADMIN || other.getStaffRights() == StaffRights.COMMUNITY_MANAGER || other.getStaffRights() == StaffRights.STAFF_MANAGER || other.getStaffRights() == StaffRights.DEVELOPER) {
                        continue;
                    }
                    for (Item item : other.getBank().bank.getItems()) {
                        if (item == null) {
                            continue;
                        }
                        if (Prices.unspawnable_item(other, item.getId())) {
                            long amount = (long) Prices.getPrice(other, item.getId());
                            amount *= (long) item.getAmount();
                            bankValue += amount;
                        }
                    }
                    for (Item item : other.getInventory().inventory.getItems()) {
                        if (item == null) {
                            continue;
                        }
                        if (Prices.unspawnable_item(other, item.getId())) {
                            long amount = (long) Prices.getPrice(other, item.getId());
                            amount *= (long) item.getAmount();
                            bankValue += amount;
                        }
                    }
                    if (bankValue >= 500_000_000_000L) {
                        System.out.println(other.getUsername() + " has a crazy bank value");
                    }
                }
            }
        }
        System.out.println("Finished checking characters for duped items.");
    }
}
