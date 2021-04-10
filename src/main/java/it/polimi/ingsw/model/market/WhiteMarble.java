package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStub;
import it.polimi.ingsw.model.resources.Item;

/**
 * Class which represents a white Marble and returns either nothing or a Resource if hasWhiteMarblePower is true
 */

public class WhiteMarble extends Marble {

    @Override
    public Item returnItem(Player p) {
        if (p.hasWhiteMarblePower())
            return p.getWhiteResource();
    return null;
    }
}

