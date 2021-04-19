package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;

/**
 *Class which represents a red Marble and returns a FaithPoint
 */

public class RedMarble extends Marble{
    @Override
    public Item returnItem() {
        return new FaithPoint(1);
    }
}
