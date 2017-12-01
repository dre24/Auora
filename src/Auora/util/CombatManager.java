package Auora.util;

import Auora.model.Entity;
import Auora.model.Item;
import Auora.model.player.Equipment;
import Auora.model.player.Player;

public class CombatManager {


    public static byte getSpeedForWeapon(int weaponId) {
        if (weaponId == -1)
            return 3;

        if (new Item(weaponId).getDefinition().name.toLowerCase().contains("godsword") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("battleaxe") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("anchor") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("ahrim's staff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("toktz-Mej-tal") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("longbow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("hand cannon") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("crossbow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("chaotic maul") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("elder maul") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("primal maul") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("javelin"))

            return 6;

        if (new Item(weaponId).getDefinition().name.toLowerCase().contains("vesta's longsword") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("chaotic longsword"))

            return 5;

        if (new Item(weaponId).getDefinition().name.toLowerCase().contains("dagger") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("claws") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("whip") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("abyssal tentacle") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("zamorakian spear") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("slayers staff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("ancient staff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("saradomin sword") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("warhammer") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("scimitar") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("toktz-xil-ak") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("toktz-xil-ek") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("shortbow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("twisted bow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("karil's crossbow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("hunter's crossbow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("toktz-xil-ul") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("salamander") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("saradomin staff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("zamorak staff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("korasi's sword") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("guthix staff"))
            return 4;

        if (new Item(weaponId).getDefinition().name.toLowerCase().contains("longsword") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("mace") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("spear") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("pickaxe") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("tzHaar-ket-em") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("torag's hammers") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("guthan's warspear") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("verac's flail") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("(sighted)") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("throwing axe") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("battlestaff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("iban's staff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("composite bow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("seercull") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("chaotic rapier") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("chaotic staff") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("rapier") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("crystal bow") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("staff of light") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("staff"))
            return 5;

        if (new Item(weaponId).getDefinition().name.toLowerCase().contains("2h") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("halberd") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("granite maul") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("balmung") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("tzHaar-ket-om") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("ivandis flail") ||
                new Item(weaponId).getDefinition().name.toLowerCase().contains("dharok's greataxe"))
            return 7;

        if (new Item(weaponId).getDefinition().name.toLowerCase().contains("ogre bow"))
            return 8;
        if (new Item(weaponId).getDefinition().name.toLowerCase().contains("dark bow"))
            return 8;


        return 3;
    }

    public static boolean wearingDharok(Player p) {
        return !(p.getEquipment().get(Equipment.SLOT_WEAPON) == null || !p.getEquipment().get(Equipment.SLOT_WEAPON).getDefinition().name.equals("Dharok's greataxe")) && !(p.getEquipment().get(Equipment.SLOT_HAT) == null || !p.getEquipment().get(Equipment.SLOT_HAT).getDefinition().name.equals("Dharok's helm")) && !(p.getEquipment().get(Equipment.SLOT_CHEST) == null || !p.getEquipment().get(Equipment.SLOT_CHEST).getDefinition().name.equals("Dharok's platebody")) && !(p.getEquipment().get(Equipment.SLOT_LEGS) == null || !p.getEquipment().get(Equipment.SLOT_LEGS).getDefinition().name.equals("Dharok's platelegs"));
    }

    public static boolean wearingVoid(Player p, int helm) {
        return !(p.getEquipment().get(Equipment.SLOT_WEAPON) == null || !p.getEquipment().get(Equipment.SLOT_WEAPON).getDefinition().name.toLowerCase().contains("void")) && !(p.getEquipment().get(Equipment.SLOT_HAT) == null || !(p.getEquipment().get(Equipment.SLOT_HAT).getDefinition().getId() == helm)) && !(p.getEquipment().get(Equipment.SLOT_CHEST) == null || !p.getEquipment().get(Equipment.SLOT_CHEST).getDefinition().name.toLowerCase().contains("void")) && !(p.getEquipment().get(Equipment.SLOT_LEGS) == null || !p.getEquipment().get(Equipment.SLOT_LEGS).getDefinition().name.toLowerCase().contains("void"));
    }

    public static byte distForWeap(int weaponId) {
        switch (weaponId) {
            default:
                return 0;
        }
    }

    public static double getSpecDamageDoublePercentage(int weaponId) {
        switch (weaponId) {
            case 11694:
                return 1.34375;
            case 11696:
                return 1.1825;
            case 3204:
            case 3101:
                return 1.1;
            case 3105:
                return 1.15;
            case 1434:
                return 1.45;
            case 11698:
            case 11700:
                return 1.075;
            case 13902:
            case 13904:
                return 1.25;
            case 18786:
                return 1.45;
            case 13899:
            case 13901:
                return 1.20;
        }
        String weaponName = weaponId == -1 ? "" : new Item(weaponId).getDefinition().name;
        if (weaponName.contains("Dragon dagger"))
            return 1.1;

        return 1;
    }

    public static short getSpecAmt(int weaponId) {
        switch (weaponId) {
            case 4151:
            case 4153:
            case 5670:
            case 11694:
            case 14484:
                return 50;
            default:
                return -1;
        }
    }

    public static short getDefenceEmote(Entity entity) {
        if (entity instanceof Player) {
            Player target = (Player) entity;
            final short weaponId = target.getEquipment().get(3) == null ? -1 : target.getEquipment().get(3).getId();
            final short shieldId = target.getEquipment().get(5) == null ? -1 : target.getEquipment().get(5).getId();
            if (shieldId == -1 && weaponId == -1)
                return 424;
            String weaponName = weaponId == -1 ? "" : new Item(weaponId).getDefinition().name;
            String shieldName = shieldId == -1 ? "" : new Item(shieldId).getDefinition().name;
            if (shieldId != -1 && shieldName.contains("defender"))
                return 4177;
            if (shieldId != -1 && shieldName.contains("shield") || shieldId == 6524)
                return 1156;
            if (weaponId != -1 && (weaponName.contains("godsword") || weaponName.contains("2h sword")))
                return 7050;
            if (weaponId != -1 && (weaponName.contains("Keris") || weaponName.contains("dagger")))
                return 403;
            if (weaponId != -1 && (weaponName.contains("Staff of light")))
                return 12806;
            if (weaponId != -1 && (weaponName.contains("Dharok") && (weaponName.contains("axe"))))
                return 12004;
            if (weaponId != -1 && weaponName.contains("maul") && !weaponName.contains("Granite"))
                return 1666;
            if (weaponId != -1 && weaponName.contains("staff"))
                return 12030;
            if (weaponId != -1 && weaponName.contains("Bow-sword"))
                return 12030;
            if (weaponId == 15486) {
                return 13038;
            }

        }
        return 424;
    }

    public static boolean isRangingWeapon(int weaponId) {
        if (weaponId == -1)
            return false;
        String weaponName = new Item(weaponId).getDefinition().name;
        return weaponName.contains("bow") || weaponName.contains("dart") || weaponName.contains("knife");
    }

}
