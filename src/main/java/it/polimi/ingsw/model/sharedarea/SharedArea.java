package it.polimi.ingsw.model.sharedarea;

import it.polimi.ingsw.model.sharedarea.market.Market;

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

    public Market getMarket() {
        return market;
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }



}
