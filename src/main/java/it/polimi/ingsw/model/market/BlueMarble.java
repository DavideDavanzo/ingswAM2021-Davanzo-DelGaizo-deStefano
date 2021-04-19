package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Shield;

/**
 *Class which represents a blue Marble and returns a Shield
 */

public class BlueMarble extends Marble{
    @Override
    public Item returnItem() {
        return new Shield(1);
    }
}
