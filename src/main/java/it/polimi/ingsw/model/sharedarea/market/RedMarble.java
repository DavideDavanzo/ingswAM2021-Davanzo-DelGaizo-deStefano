package it.polimi.ingsw.model.sharedarea.market;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;

/**
 * This class represents a red Marble and returns a FaithPoint
 */

public class RedMarble extends Marble{
    public RedMarble(){
        super();
    }
    @Override
    public Item returnItem() {
        return new FaithPoint(1);
    }

    @Override
    public String toString() {
        return "Red Marble";
    }

    @Override
    public String print() {
        return Color.ANSI_RED.escape() + "●" + Color.ANSI_WHITE.escape();
    }
}
