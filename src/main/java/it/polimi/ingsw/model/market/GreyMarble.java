package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Stone;

/**
 *Class which represents a grey Marble and returns a Stone
 */

public class GreyMarble extends Marble{
    @Override
    public Item returnItem() {
        return new Stone(1);
    }
}
