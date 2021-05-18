package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Shield;

/**
 * This class represents a blue Marble and returns a Shield
 */

public class BlueMarble extends Marble{

    public BlueMarble(){
        super();
    }
    @Override
    public Item returnItem() {
        return new Shield(1);
    }

    @Override
    public String toString() {
        return "Blue Marble";
    }

    @Override
    public String print() {
        return Color.ANSI_BLUE.escape() + "●" + Color.ANSI_WHITE.escape();
    }
}
