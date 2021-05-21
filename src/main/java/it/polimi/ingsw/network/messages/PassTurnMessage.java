package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class PassTurnMessage extends Message{

    public PassTurnMessage() {
        super();
    }

    public PassTurnMessage(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        //TODO: process this message
    }
}
