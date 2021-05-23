package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.view.View;

public class DevCardsUpdate extends Message{

    @JsonSerialize(as = DevelopmentCardsArea.class)
    private DevelopmentCardsArea developmentCardsArea;

    public DevCardsUpdate() {
    }

    public DevCardsUpdate(DevelopmentCardsArea developmentCardsArea) {
        this.developmentCardsArea = developmentCardsArea;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public void setDevelopmentCardsArea(DevelopmentCardsArea developmentCardsArea) {
        this.developmentCardsArea = developmentCardsArea;
    }
}
