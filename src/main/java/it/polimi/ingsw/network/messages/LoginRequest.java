package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class LoginRequest extends Message {

    public LoginRequest(){
        super();
    }

    public LoginRequest(String msg){
        super(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException { }

}
