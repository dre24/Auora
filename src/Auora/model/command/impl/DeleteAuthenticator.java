package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * @author Jonny
 */
public class DeleteAuthenticator extends Command {

    public DeleteAuthenticator(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if (p.getCombat().hasTarget() || p.getCombat().combatWithDelay > 0 || p.getSkills().isDead() || p.getCombat().delay > 0) {
            p.getFrames().sendChatMessage(0, "You can't do this here.");
            return;
        }
        if (!p.hasAuth) {
            p.sm("You dont have an authentication code.");
            return;
        }
        if (!p.sure) {
            p.getFrames().sendChatMessage(0, "Please type ::imsure to do this command.");
            p.getFrames().sendChatMessage(0, "<col=ff0000>Warning, this will remove your authentication code!");
            return;
        }
        p.authCode = "";
        p.hasAuth = false;
        p.enteredAuth = false;
        p.sure = false;
        p.sm("You have deleted your authentication code.");
    }
}
