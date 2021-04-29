package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Servant;

/**
 * This class represents a purple Marble and returns a Servant
 */

public class PurpleMarble extends Marble{
    @Override
    public Item returnItem() {
        return new Servant(1);
    }

    @Override
    public String toString() {
        return "Purple Marble";
    }
}
