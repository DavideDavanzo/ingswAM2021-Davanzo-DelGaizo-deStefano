package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.view.View;

public class MarketInfoMessage extends Message{

    @JsonSerialize(as = Market.class)
    Market market;

    public MarketInfoMessage(){}

    public MarketInfoMessage(Market market){
        this.market = market;
    }

    @Override
    public void apply(View view) {
        view.showMarket(market);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }
}
