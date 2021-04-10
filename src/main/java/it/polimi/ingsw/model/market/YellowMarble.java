package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStub;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Item;

/**
 *Class which represents a yellow Marble and returns a Coin
 */

public class YellowMarble extends Marble{

    @Override
    public Item returnItem(Player p) {
        return new Coin(1);
    }
}