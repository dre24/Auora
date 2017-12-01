package Auora.model.command.impl;

import Auora.model.Item;
import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.Skills;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class Pure extends Command {

    public Pure(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        p.getSkills().setXp(0, 273742);
        p.getSkills().set(0, 60);
        p.getSkills().setXp(2, Skills.SMALL_EXP);
        p.getSkills().set(2, 99);
        p.getSkills().setXp(3, Skills.SMALL_EXP);
        p.getSkills().set(3, 99);
        p.getSkills().setXp(4, Skills.SMALL_EXP);
        p.getSkills().set(4, 99);
        p.getSkills().setXp(5, 123660);
        p.getSkills().set(5, 52);
        p.getSkills().setXp(6, Skills.SMALL_EXP);
        p.getSkills().set(6, 99);
        p.getSkills().heal(990);

        p.getBank().bankEquip();
        p.getFrames().sendChatMessage(0, "Your current equipment has been sent to your bank!");

        p.getEquipment().set(1, new Item(10499));// Avas
        p.getEquipment().set(10, new Item(3105)); // Climbing boot
        p.getEquipment().set(0, new Item(6109)); // Ghostly Hood
        p.getEquipment().set(2, new Item(1704)); // Amulet of Glory
        p.getEquipment().set(4, new Item(6107));// Ghostly Robe
        p.getEquipment().set(12, new Item(2550)); // Ring of Recoil
        p.getEquipment().set(7, new Item(2497)); // Black d hide Chaps
        p.getEquipment().set(9, new Item(7458)); // Mithril Gloves
        p.getEquipment().set(3, new Item(4587)); // Dragon Scimitar
        p.getEquipment().set(5, new Item(3842)); // Unholy Book

        p.getFrames().sendChatMessage(0, "<img=3><col=FF0000>Congratulations! You are now a Pure!");
        p.getFrames().sendChatMessage(0, "<img=3><col=FF0000>To set individual stats you can do ::setlevel! Attack=0 Prayer=5.");
    }
}
