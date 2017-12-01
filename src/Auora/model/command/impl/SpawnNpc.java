package Auora.model.command.impl;

import Auora.model.World;
import Auora.model.command.Command;
import Auora.model.npc.Npc;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.util.RSTile;

/**
 * @author Jonny
 */
public class SpawnNpc extends Command {

    public SpawnNpc(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        super(staffRights, donatorRights, safeOnly);
    }

    @Override
    public void execute(Player p, String[] args, StaffRights privilege) {
        if(args == null) {
            p.sendMessage("You must do this command as ::snpc id");
            return;
        }
        int npcId = Integer.parseInt(args[0]);
        int X = p.getLocation().getX();
        int Y = p.getLocation().getY();
        World.getNpcs().add(new Npc((short) npcId, RSTile.createRSTile(X, Y), 0, 0, 0, 0, 6));
    }
}
