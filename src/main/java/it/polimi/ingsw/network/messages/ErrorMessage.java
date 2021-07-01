package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class ErrorMessage extends Message {

    public ErrorMessage(){
        super();
    }

    public ErrorMessage(String msg){
        super(msg);
    }

    @Override
    public void apply(ClientView view) {
        view.showError(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

}
