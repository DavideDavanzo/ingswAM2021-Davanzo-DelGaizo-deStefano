package it.polimi.ingsw.model.sharedarea.market;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

/**
 * This class represents a white Marble and returns either nothing or a Resource if hasWhiteMarblePower is true
 */

public class WhiteMarble extends Marble {

    public WhiteMarble(){
        super();
    }

    @Override
    public Item returnItem() {
        return new Resource(1);
    }

    @Override
    public String toString() {
        return "White Marble";
    }

    @Override
    public String print() {
        return Color.ANSI_WHITE.escape() + "●";
    }
}
