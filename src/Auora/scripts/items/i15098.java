package Auora.scripts.items;

import Auora.model.player.DiceGame;
import Auora.model.player.Player;
import Auora.scripts.itemScript;

public class i15098 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.dicerRank == 1) {
            if (p.getInventory().getContainer().get(slot) == null)
                return;
            if (p.getInventory().getContainer().get(slot).getId() != itemId)
                return;
            if (interfaceId != 149)
                return;
            DiceGame.rollDice(p);
        } else {
        	p.getFrames().sendInterface(793);
        	p.getFrames().sendString("Dice Rules", 793, 5);
        	p.getFrames().sendString("Agreeing to the following rules brings you one step closer to being a dicehost.<br>"
        			+ "<br>"
        			+ "- Scamming will in no circumstances be tolerated. Scamming is a strongly forbidden act which leads to your account being permanently banned."
        			+ "<br>"
        			+ "- The holder of the dicebag is ONLY allowed to host the following games: 60x2 (or any variant being agreed upon), blackjack, dice duel."
        			+ "<br>"
        			+ "- Selling the dicebag to another player is allowed. Trading the dicebag over to an alternative account of yours without the agreement of an admin is STRONGLY FORBIDDEN and will lead to your dicebag being taken away."
        			, 793, 13);
        	p.getFrames().sendString("DECLINE", 793, 14);
        	p.getFrames().sendString("", 793, 12);
        	p.getFrames().sendString("ACCEPT", 793, 15);
        	
        }
    }
}