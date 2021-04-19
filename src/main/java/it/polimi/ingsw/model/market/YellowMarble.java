package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Item;

/**
 *vClass which represents a yellow Marble and returns a Coin
 */

public class YellowMarble extends Marble {

    @Override
    public Item returnItem() {
        return new Coin(1);
    }

}