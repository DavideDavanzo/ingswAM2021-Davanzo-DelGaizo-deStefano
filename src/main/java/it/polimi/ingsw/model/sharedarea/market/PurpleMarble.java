package it.polimi.ingsw.model.sharedarea.market;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Servant;

/**
 * This class represents a purple Marble and returns a Servant
 */

public class PurpleMarble extends Marble{
    public PurpleMarble(){
        super();
    }

    /**
     * @return 1 Servant.
     */
    @Override
    public Item returnItem() {
        return new Servant(1);
    }

    @Override
    public String toString() {
        return "Purple Marble";
    }

    @Override
    public String print() {
        return Color.ANSI_PURPLE.escape() + "●" + Color.ANSI_WHITE.escape();
    }

    @Override
    public String toPath() {
        return "purplemarble";
    }
}
