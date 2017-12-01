/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.model.npc;

import Auora.model.player.Player;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class PlayerDamage {

    public Player player;
    public int damage;

    public PlayerDamage(Player player, int damage) {
        this.player = player;
        this.damage = damage;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

}