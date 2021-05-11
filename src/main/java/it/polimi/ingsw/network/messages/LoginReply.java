package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class LoginReply extends Message {

    public LoginReply(){
        super();
    }

    public LoginReply(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        view.showMessage(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }


}
