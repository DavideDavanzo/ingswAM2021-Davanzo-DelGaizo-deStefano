package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class ActivateProductionCmd extends Message {

    private Trade baseProduction;

    @JsonSerialize(as = boolean.class)
    private boolean hasBaseProduction;

    private ArrayList<Integer> productionCardsIndex; //1 up to 5.

    public ActivateProductionCmd() {
    }

    public ActivateProductionCmd(Trade baseProduction, boolean hasBaseProduction, ArrayList<Integer> productionCardsIndex) {
        this.baseProduction = baseProduction;
        this.hasBaseProduction = hasBaseProduction;
        this.productionCardsIndex = productionCardsIndex;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public Trade getBaseProduction() {
        return baseProduction;
    }

    public void setBaseProduction(Trade baseProduction) {
        this.baseProduction = baseProduction;
    }

    public boolean hasBaseProduction() {
        return hasBaseProduction;
    }

    public void setHasBaseProduction(boolean hasBaseProduction) {
        this.hasBaseProduction = hasBaseProduction;
    }

    public ArrayList<Integer> getProductionCardsIndex() {
        return productionCardsIndex;
    }

    public void setProductionCardsIndex(ArrayList<Integer> productionCardsIndex) {
        this.productionCardsIndex = productionCardsIndex;
    }
}
