package Auora.scripts.dialogues;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class Auoratele extends dialogueScript {

    private short npcclientid = 300;

    private short getEntityId() {
        for (Npc n : World.getNpcs()) {
            if (n.getId() == npcclientid) {
                return (short) n.getIndex();
            }
        }
        return -1;
    }

    @Override
    public void start() {
        String[] talkDefinitions = new String[]{"Where would you like to teleport?", "Safe Pking",
                "Clanwars (Unsafe/Multi)", "Unsafe Pking (No Protect Item)", "Nowhere"};
        this.sendDialogue(SEND_4_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
            	player.animate(8939);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        player.animate(8941);
                        player.getMask().getRegion().teleport(2815, 5511, 0, 0);
                    }
                }, 1801);
                player.getFrames().sendChatMessage(0, "<col=8A2BE2><img=3>Welcome to Safe Pking!</col>");
                this.finish();
                break;
            case 2:
            	player.animate(8939);
                GameServer.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        player.animate(8941);
                        player.getMask().getRegion().teleport(3271, 3685, 0, 0);
                    }
                }, 1801);
                player.getFrames().sendChatMessage(0, "<col=8A2BE2z><img=3>Welcome to Clanwars! [Multi/UnSafe Pk] </col>");
                this.finish();
                break;
            case 3:            	
            	player.getFrames().sendInterface(892);
                this.finish();
                break;
            case 4:
                this.finish();
                break;            
            default:
                this.finish();
                break;
        }
    }
}
