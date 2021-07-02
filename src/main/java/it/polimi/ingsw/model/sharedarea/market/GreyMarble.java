package it.polimi.ingsw.model.sharedarea.market;

import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Stone;
import it.polimi.ingsw.model.enums.Color;

/**
 * This class represents a grey Marble and returns a Stone
 */

public class GreyMarble extends Marble{

    public GreyMarble(){
        super();
    }

    /**
     * @return 1 Stone.
     */
    @Override
    public Item returnItem() {
        return new Stone(1);
    }

    @Override
    public String toString() {
        return "Grey Marble";
    }

    @Override
    public String print() {
        return Color.ANSI_DARK_GREY.escape() + "●" + Color.ANSI_WHITE.escape();
    }

    @Override
    public String toPath() {
        return "greymarble";
    }
}
