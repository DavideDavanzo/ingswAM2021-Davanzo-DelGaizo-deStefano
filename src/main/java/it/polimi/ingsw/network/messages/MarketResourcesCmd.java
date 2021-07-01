package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class MarketResourcesCmd extends Message{

    private char line;

    private int index;

    public MarketResourcesCmd(){}

    public MarketResourcesCmd(char line, int index){
        this.line = line;
        this.index = index;
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public char getLine() {
        return line;
    }

    public int getIndex() {
        return index;
    }
}
