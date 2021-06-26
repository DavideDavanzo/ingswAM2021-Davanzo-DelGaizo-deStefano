package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.view.ClientView;

public class MarketUpdate extends Message{

    @JsonSerialize(as = Market.class)
    Market market;

    public MarketUpdate(){}

    public MarketUpdate(Market market){
        this.market = market;
    }

    @Override
    public void apply(ClientView view) {
        view.updateMarket(market);
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
