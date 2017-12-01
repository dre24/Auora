/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.ndrop;

import Auora.model.GlobalDropManager;
import Auora.model.Item;
import Auora.model.npc.Npc;
import Auora.model.player.Player;
import Auora.scripts.ndropScript;
import Auora.util.Misc;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class n1 extends ndropScript {

    //Man Drop Items/Chances

    public final int commonchance = 40;
    public final int uncommonchance = 45;
    public final int rarechance = 50;
    public final int ultrararechance = 55;
    public int[] dropitems = {526};
    public int[] commonitems = {199, 995};
    public int[] uncommonitems = {557, 554, 558, 555, 202, 203, 206, 208, 213,
            210, 882, 1203, 877, 1139, 1438, 1440, 1965,
            313, 436, 438};
    public int[] rareitems = {562, 212, 215, 2485, 218};
    public int[] ultrarareitems = {559};
    //MAX AMOUNT
    public int[] dropitemsamount = {1};
    public int[] commonitemsamount = {1, 40};
    public int[] uncommonitemsamount = {4, 6, 9, 7, 1, 1, 1, 1, 1, 1, 7, 1, 12,
            1, 1, 1, 1, 1, 1};
    public int[] rareitemsamount = {2, 1, 1, 1, 1};
    public int[] ultrarareitemsamount = {7};

    @Override
    public void dropItem(Player p, Npc n) {
        //100% Chance
        for (int dropitem : dropitems) {
            Item item = new Item(dropitem);
            GlobalDropManager.dropItem(p, n.getLocation(), item, false);
        }
        //Common
        int chance = Misc.random(100);
        if (chance > commonchance) {
            GlobalDropManager.dropItem(p, n.getLocation(), new Item(commonitems[Misc.random(commonitems.length - 1)], Misc.random(commonitemsamount[Misc.random(commonitems.length - 1)])), false);
        }
        //Uncommon
        chance = Misc.random(70);
        if (chance > uncommonchance) {
            int item = Misc.random(uncommonitems.length);
            if (item == -1) {
                item = 0;
            } else if (item > uncommonitems.length) {
                item = uncommonitems.length - 1;
            }
            int amount = Misc.random(uncommonitemsamount.length);
            if (amount == -1) {
                item = 0;
            } else if (amount > uncommonitemsamount.length) {
                amount = uncommonitemsamount.length - 1;
            }
            GlobalDropManager.dropItem(p, n.getLocation(), new Item(uncommonitems[Misc.random(uncommonitems.length - 1)], Misc.random(uncommonitemsamount[Misc.random(uncommonitems.length - 1)])), false);
        }
        //Rare
        chance = Misc.random(60);
        if (chance > rarechance) {
            int item = Misc.random(rareitems.length);
            if (item == -1) {
                item = 0;
            } else if (item > rareitems.length) {
                item = rareitems.length - 1;
            }
            int amount = Misc.random(rareitemsamount.length);
            if (amount == -1) {
                item = 0;
            } else if (amount > rareitemsamount.length) {
                amount = rareitemsamount.length - 1;
            }
            GlobalDropManager.dropItem(p, n.getLocation(), new Item(rareitems[item], Misc.random(rareitemsamount[amount])), false);
        }
        //Very Rare
        chance = Misc.random(60);
        if (chance > ultrararechance) {
            int item = Misc.random(ultrarareitems.length);
            if (item == -1) {
                item = 0;
            } else if (item > ultrarareitems.length) {
                item = ultrarareitems.length - 1;
            }
            int amount = Misc.random(ultrarareitemsamount.length);
            if (amount == -1) {
                item = 0;
            } else if (amount > ultrarareitemsamount.length) {
                amount = ultrarareitemsamount.length - 1;
            }
            GlobalDropManager.dropItem(p, n.getLocation(), new Item(ultrarareitems[item], Misc.random(ultrarareitemsamount[amount])), true);
        }
    }
}