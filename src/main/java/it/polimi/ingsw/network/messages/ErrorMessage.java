package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class ErrorMessage extends Message {

    public ErrorMessage(){
        super();
    }

    public ErrorMessage(String msg){
        super(msg);
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

}
