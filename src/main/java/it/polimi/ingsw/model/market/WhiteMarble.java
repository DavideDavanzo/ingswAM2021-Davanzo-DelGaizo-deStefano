package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

/**
 * This class represents a white Marble and returns either nothing or a Resource if hasWhiteMarblePower is true
 */

public class WhiteMarble extends Marble {

    @Override
    public Item returnItem() {
        return new Resource(1);
    }

    @Override
    public String toString() {
        return "White Marble";
    }
}
