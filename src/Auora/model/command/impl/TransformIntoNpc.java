package Auora.model.command.impl;

import Auora.model.command.Command;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.rscache.NpcDefinitions;

/**
 * @author Jonny
 */
public class TransformIntoNpc extends Command {

    public TransformIntoNpc(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::pnpc id");
            return;
        }
        int npcId = Integer.parseInt(args[0]);
        p.getAppearence().setNpcType((short) npcId);
        p.getMask().setApperanceUpdate(true);
        p.sendMessage("You have turned into a " + NpcDefinitions.forID(npcId) + " with id " + npcId + ".");
    }
}
