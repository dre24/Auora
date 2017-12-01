package Auora.skills.combat;

import Auora.model.player.Player;
import Auora.skills.combat.spells.*;
import Auora.util.Logger;

import java.util.HashMap;
import java.util.Map;

//import spawnscape.skills.combat.spells.MiasmicBarrage;

public class MagicManager {

    private static final Map<String, MagicInterface> SPELLS = new HashMap<String, MagicInterface>();

    public MagicManager() {
        SPELLS.put("IceBlitz", new IceBlitz());
        SPELLS.put("FireSurge", new FireSurge());
        SPELLS.put("Entangle", new Entangle());
        SPELLS.put("TeleBlock", new TeleBlock());
        SPELLS.put("IceBarrage", new IceBarrage());
        SPELLS.put("BloodBlitz", new BloodBlitz());
        SPELLS.put("MiasmicBarrage", new MiasmicBarrage());// that spell is not wokring i told you
        SPELLS.put("BloodBarrage", new BloodBarrage());
        Logger.log("MagicManager", "Successfully loaded: " + SPELLS.size() + ", magic spells.");
    }

    public static void executeSpell(Player p, Player opp, String Spell) {
        if (p == null)
            return;
        if (opp == null)
            return;
        MagicInterface spell = SPELLS.get(Spell);
        spell.execute(p, opp);
    }

}

