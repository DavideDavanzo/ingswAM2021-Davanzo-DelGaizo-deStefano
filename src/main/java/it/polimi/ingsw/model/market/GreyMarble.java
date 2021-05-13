package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Stone;

/**
 * This class represents a grey Marble and returns a Stone
 */

public class GreyMarble extends Marble{

    public GreyMarble(){
        super();
    }

    @Override
    public Item returnItem() {
        return new Stone(1);
    }

    @Override
    public String toString() {
        return "Grey Marble";
    }
}
