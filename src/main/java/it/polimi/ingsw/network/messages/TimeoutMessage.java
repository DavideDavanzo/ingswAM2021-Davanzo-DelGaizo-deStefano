package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class TimeoutMessage extends Message{

    public TimeoutMessage(){
        super();
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

}
