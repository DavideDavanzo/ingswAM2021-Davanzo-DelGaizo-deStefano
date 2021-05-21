package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class LoginReply extends Message {

    private boolean successful;

    public LoginReply(){
        super();
    }

    public LoginReply(String msg) {
        super(msg);
    }

    public LoginReply(String msg, boolean successful){
        this.msg = msg;
        this.successful = successful;
    }

    @Override
    public void apply(View view) {
        view.onLoginReply(this);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public boolean isSuccessful() {
        return successful;
    }
}
