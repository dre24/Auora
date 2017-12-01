/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.model.npc;

import Auora.model.player.Player;

import java.util.ArrayList;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class nDropManager {

    public Npc n;

    private ArrayList<PlayerDamage> damageToNpc = new ArrayList<PlayerDamage>(500);

    public nDropManager(Npc n) {
        this.setNpc(n);
    }

    private void setNpc(Npc n) {
        this.n = n;
    }

    public void hitNpc(Player p, int damage) {
        PlayerDamage pdd = new PlayerDamage(p, damage);
        boolean addeddamage = false;
        for (PlayerDamage pd : damageToNpc) {
            if (pdd.player == pd.player) {
                pd.addDamage(damage);
                addeddamage = true;
                break;
            } else {
                continue;
            }
        }
        if (!addeddamage)
            addPlayerDamage(p, damage);
    }

    public Player getPlayerMostDamage() {
        int damage = 0;
        Player p = null;
        for (PlayerDamage pd : damageToNpc) {
            if (damage < pd.damage) {
                damage = pd.damage;
                p = pd.player;
            }
        }
        return p;
    }

    public void resetPlayerDamage() {
        damageToNpc = new ArrayList<PlayerDamage>(500);
    }

    private void addPlayerDamage(Player p, int damage) {
        PlayerDamage pd = new PlayerDamage(p, damage);
        damageToNpc.add(pd);
    }

}