package it.polimi.ingsw.model.sharedarea;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.PurpleMarble;

/**
 * <h1>Shared Area</h1>
 * The Shared Area contains a {@link Market} and the {@link CardMarket}
 */
public class SharedArea {

    private Market market;
    private CardMarket cardMarket;
    
    public SharedArea() {
        market = new Market();
        cardMarket = new CardMarket();
    }
}
