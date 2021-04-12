package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Servant;

/**
 *Class which represents a purple Marble and returns a Servant
 */

public class PurpleMarble extends Marble{
    @Override
    public Item returnItem(Player p) {
        return new Servant(1);
    }
}
