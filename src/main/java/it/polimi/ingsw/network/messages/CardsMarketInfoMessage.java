package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.view.View;

public class CardsMarketInfoMessage extends Message{

    @JsonSerialize(as = CardMarket.class)
    private CardMarket cardMarket;

    public CardsMarketInfoMessage(){}

    public CardsMarketInfoMessage(CardMarket cardMarket){
        this.cardMarket = cardMarket;
    }

    @Override
    public void apply(View view) {
        view.showCardsMarket(cardMarket);
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
