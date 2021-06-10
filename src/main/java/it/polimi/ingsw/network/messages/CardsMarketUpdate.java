package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.view.View;

public class CardsMarketUpdate extends Message{

    @JsonSerialize(as = CardMarket.class)
    private CardMarket cardMarket;

    public CardsMarketUpdate(){}

    public CardsMarketUpdate(CardMarket cardMarket){
        this.cardMarket = cardMarket;
    }

    @Override
    public void apply(View view) {
        view.updateCardMarket(cardMarket);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public void setCardMarket(CardMarket cardMarket) {
        this.cardMarket = cardMarket;
    }
}
