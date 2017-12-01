/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.model.npc.list;

/**
 * NPCDefinition.java
 *
 * @author SiniSoul
 * @version 1.0
 */
public class NPCDefinition {

    private int currenthp;
    private boolean dead = false;
    private int currentRespawnTime = -1;
    //Basic Information
    private int id;
    private boolean attackable;
    //Basic Combat Information
    private int combatlevel;
    private int maxhp;
    private boolean agressive;
    private int maxmeleehit;
    private int maxrangehit;
    private int maxmagichit;
    //Emote Information
    private int walkemote;
    private int attackemote;
    private int defendemote;
    private int deathemote;
    //GFX Information
    private int magicprojectilegfx;
    private int magicprojectilesplashgfx;
    private int rangeprojectilegfx;
    private int rangeprojectilesplashgfx;
    //Extensive Emote Information
    private int meleeemote;
    private int rangeemote;
    private int magicemote;
    //Extensive Combat Information
    private int meleedelay;
    private int magicdelay;
    private int rangedelay;
    private boolean defendmelee;
    private boolean defendmagic;
    private boolean defendrange;
    private boolean usesmelee;
    private boolean usesmagic;
    private boolean usesrange;
    private int respawntime;

    /**
     * For basic non-attackable NPCs
     */
    public NPCDefinition(int id, boolean attackable) {
        this.setDefinitions(id, attackable);
    }

    public NPCDefinition(int id, boolean attackable, int combatlevel,
                         int maxhp, boolean agressive, int maxmeleehit,
                         int maxrangehit, int maxmagichit, int walkemote,
                         int attackemote, int defendemote, int deathemote,
                         int magicprojectilegfx, int magicsplashgfx,
                         int rangeprojectilegfx, int rangesplashgfx,
                         int meleeemote, int magicemote, int rangeemote,
                         int meleedelay, int magicdelay, int rangedelay,
                         boolean defendmelee, boolean defendrange, boolean defendmagic,
                         boolean usesmelee, boolean usesmagic, boolean usesrange, int respawntime) {

        setDefinitions(id, attackable, combatlevel, maxhp, agressive, maxmeleehit,
                maxrangehit, maxmagichit, walkemote, attackemote, defendemote,
                deathemote, magicprojectilegfx, magicsplashgfx, rangeprojectilegfx,
                rangesplashgfx, meleeemote, magicemote, rangeemote,
                meleedelay, magicdelay, rangedelay, defendmelee, defendrange,
                defendmagic, usesmelee, usesrange, usesmagic, respawntime);

    }

    public boolean isDead() {
        return currenthp < 0 || currenthp == 0;
    }

    public void setDead(boolean bool) {
        this.dead = bool;
    }

    public int currenthp() {
        return currenthp;
    }

    public void setCurrentHp(int hp) {
        this.currenthp = hp;
    }

    public boolean dead() {
        return this.dead;
    }

    private void setDefinitions(int id, boolean attackable, int combatlevel,
                                int maxhp, boolean agressive, int maxmeleehit,
                                int maxrangehit, int maxmagichit, int walkemote,
                                int attackemote, int defendemote, int deathemote,
                                int magicprojectilegfx, int magicsplashgfx,
                                int rangeprojectilegfx, int rangesplashgfx,
                                int meleeemote, int magicemote, int rangeemote,
                                int meleedelay, int magicdelay, int rangedelay,
                                boolean defendmelee, boolean defendrange, boolean defendmagic,
                                boolean usesmelee, boolean usesrange, boolean usesmagic, int respawntime) {
        this.setID(id);
        this.setAttackable(attackable);
        this.setCombatlevel(combatlevel);
        this.setMaxHP(maxhp);
        this.setAgressive(agressive);
        this.setMaxMeleeHit(maxmeleehit);
        this.setMaxRangeHit(maxrangehit);
        this.setMaxMagicHit(maxmagichit);
        this.setWalkEmote(walkemote);
        this.setAttackEmote(attackemote);
        this.setDefendEmote(defendemote);
        this.setDeathEmote(deathemote);
        this.setMagicProjectileGFX(magicprojectilegfx);
        this.setMagicProjectileSplashGFX(magicsplashgfx);
        this.setRangeProjectileGFX(rangeprojectilegfx);
        this.setRangeProjectileSplashGFX(rangesplashgfx);
        this.setMeleeEmote(meleeemote);
        this.setMagicEmote(magicemote);
        this.setRangeEmote(rangeemote);
        this.setMeleeDelay(meleedelay);
        this.setMagicDelay(magicdelay);
        this.setRangeDelay(rangedelay);
        this.setDefendMelee(defendmelee);
        this.setDefendMagic(defendmagic);
        this.setDefendRange(defendrange);
        this.setusesMelee(usesmelee);
        this.setusesMagic(usesmagic);
        this.setusesRange(usesrange);
        this.setRespawnTime(respawntime);
    }

    private void setDefinitions(int id, boolean bool) {
        this.setID(id);
        this.setAttackable(bool);
    }

    /**
     * Basic Information
     */
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public boolean getAttackable() {
        return attackable;
    }

    public void setAttackable(boolean bool) {
        this.attackable = bool;
    }

    /**
     * Combat Information
     */
    public int getCombatlevel() {
        return combatlevel;
    }

    public void setCombatlevel(int combatlevel) {
        this.combatlevel = combatlevel;
    }

    public int getMaxHP() {
        return maxhp;
    }

    public void setMaxHP(int maxhp) {
        this.maxhp = maxhp;
        this.currenthp = maxhp;
    }

    public boolean getAgressive() {
        return agressive;
    }

    public void setAgressive(boolean bool) {
        this.attackable = bool;
    }

    public int getMaxMeleeHit() {
        return maxmeleehit;
    }

    public void setMaxMeleeHit(int maxmeleehit) {
        this.maxmeleehit = maxmeleehit;
    }

    public int getMaxMagicHit() {
        return maxmagichit;
    }

    public void setMaxMagicHit(int maxmagichit) {
        this.maxmagichit = maxmagichit;
    }

    public int getMaxRangeHit() {
        return maxrangehit;
    }

    public void setMaxRangeHit(int maxrangehit) {
        this.maxrangehit = maxrangehit;
    }

    /**
     * Emote Information
     */

    public int getWalkEmote() {
        return walkemote;
    }

    public void setWalkEmote(int emote) {
        this.walkemote = emote;
    }

    public int getAttackEmote() {
        return attackemote;
    }

    public void setAttackEmote(int attackemote) {
        this.attackemote = attackemote;
    }

    public int getDefendEmote() {
        return defendemote;
    }

    public void setDefendEmote(int defendemote) {
        this.defendemote = defendemote;
    }

    public int getDeathEmote() {
        return deathemote;
    }

    public void setDeathEmote(int deathemote) {
        this.deathemote = deathemote;
    }

    /**
     * GFX Information
     */

    public int getMagicProjectileGFX() {
        return magicprojectilegfx;
    }

    public void setMagicProjectileGFX(int magicprojectilegfx) {
        this.magicprojectilegfx = magicprojectilegfx;
    }

    public int getMagicProjectileSplashGFX() {
        return magicprojectilesplashgfx;
    }

    public void setMagicProjectileSplashGFX(int magicprojectilesplashgfx) {
        this.magicprojectilesplashgfx = magicprojectilesplashgfx;
    }

    public int getRangeProjectileGFX() {
        return rangeprojectilegfx;
    }

    public void setRangeProjectileGFX(int rangeprojectilegfx) {
        this.rangeprojectilegfx = rangeprojectilegfx;
    }

    public int getRangeProjectileSplashGFX() {
        return rangeprojectilesplashgfx;
    }

    public void setRangeProjectileSplashGFX(int rangeprojectilesplashgfx) {
        this.rangeprojectilesplashgfx = rangeprojectilesplashgfx;
    }

    /**
     * Extensive Emote Information
     */

    public int getMeleeEmote() {
        return meleeemote;
    }

    public void setMeleeEmote(int meleemote) {
        this.meleeemote = meleemote;
    }

    public int getMagicEmote() {
        return magicemote;
    }

    public void setMagicEmote(int magicemote) {
        this.magicemote = magicemote;
    }

    public int getRangeEmote() {
        return rangeemote;
    }

    public void setRangeEmote(int rangeemote) {
        this.rangeemote = rangeemote;
    }

    /**
     * Extensive Combat Information
     */

    public int getMeleeDelay() {
        return meleedelay;
    }

    public void setMeleeDelay(int meleedelay) {
        this.meleedelay = meleedelay;
    }

    public int getMagicDelay() {
        return magicdelay;
    }

    public void setMagicDelay(int magicdelay) {
        this.magicdelay = magicdelay;
    }

    public int getRangeDelay() {
        return rangedelay;
    }

    public void setRangeDelay(int rangedelay) {
        this.rangedelay = rangedelay;
    }

    public boolean defendMelee() {
        return defendmelee;
    }

    public void setDefendMelee(boolean bool) {
        this.defendmelee = bool;
    }

    public boolean defendMagic() {
        return defendmagic;
    }

    public void setDefendMagic(boolean bool) {
        this.defendmagic = bool;
    }

    public boolean defendRange() {
        return defendrange;
    }

    public void setDefendRange(boolean bool) {
        this.defendrange = bool;
    }

    public boolean usesMelee() {
        return usesmelee;
    }

    public void setusesMelee(boolean bool) {
        this.usesmelee = bool;
    }

    public boolean usesMagic() {
        return usesmagic;
    }

    public void setusesMagic(boolean bool) {
        this.usesmagic = bool;
    }

    public boolean usesRange() {
        return usesrange;
    }

    public void setusesRange(boolean bool) {
        this.usesrange = bool;
    }

    public int getRespawnTime() {
        return respawntime;
    }

    public void setRespawnTime(int time) {
        this.respawntime = time;
    }

    public int getCurrentRespawnTime() {
        return currentRespawnTime;
    }

    public void setCurrentRespawnTime(int time) {
        this.currentRespawnTime = time;
    }
}