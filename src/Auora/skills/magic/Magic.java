package Auora.skills.magic;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;
import Auora.model.player.Skills;

public class Magic {


    public static void spellBookTeleport(final Player p, int spellBook, int buttonId) {
        if (System.currentTimeMillis() < p.getCombatDefinitions().getLastEmote())
            return;
        if (p.getSkills().isDead())
            return;
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
            return;
        }
        //	if(player.jailTimer > 0) {
        //	player.getFrames().sendChatMessage(0, "You are jailed.");
        //return;
        //}
        if (!p.getCombat().isSafe(p) && p.getCombat().inDangerousPVP(p) && !p.getCombat().inWild(p)) {
            p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
            return;
        }
        final int coordX;
        final int coordY;
        final int magicLvl = p.getSkills().getLevel(Skills.MAGIC);
        int neededLvl = 0;
        switch (spellBook) {
            case 192:
                switch (buttonId) {
                    case 43:
                        if (magicLvl < 31)
                            neededLvl = 31;
                        coordX = 3221;
                        coordY = 3219;
                        break;
                    case 24:
                        coordX = 3186;
                        coordY = 3440;
                        break;
                    case 40:
                        if (magicLvl < 25)
                            neededLvl = 25;
                        coordX = 3212;
                        coordY = 3428;
                        break;
                    case 51:
                        if (magicLvl < 45)
                            neededLvl = 45;
                        coordX = 2757;
                        coordY = 3478;
                        break;
                    case 57:
                        if (magicLvl < 58)
                            neededLvl = 58;
                        coordX = 2662;
                        coordY = 3308;
                        break;
                    case 72:
                        if (magicLvl < 64)
                            neededLvl = 64;
                        coordX = 2755;
                        coordY = 2784;
                        break;
                    default:
                        System.out.println("buttonId: " + buttonId);
                        return;
                }
                break;
            default:
                System.out.println("bookId: " + spellBook);
                return;
        }
        if (neededLvl != 0) {
            p.getFrames().sendChatMessage(0, "You need level " + neededLvl + " magic to use this spell.");
            return;
        }
        p.getCombatDefinitions().doEmote(8939, 1576, 4200);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            int count = 0;

            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.stop();
                    return;
                }
                if (count++ == 0)
                    p.getMask().getRegion().teleport(coordX, coordY, 0, 0);
                else {
                    p.animate(8941);
                    p.graphics(1577);
                    this.stop();
                }
            }

        }, 3, 0, 0);
    }
}
