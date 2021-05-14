package it.polimi.ingsw.model.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Item;

import java.io.Serializable;

/**
 *vClass which represents a yellow Marble and returns a Coin
 */

public class YellowMarble extends Marble implements Serializable {
    public YellowMarble(){
        super();
    }

    @Override
    public Item returnItem() {
        return new Coin(1);
    }

    @Override
    public String toString() {
        return "Yellow Marble";
    }
}