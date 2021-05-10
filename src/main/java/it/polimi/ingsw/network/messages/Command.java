package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.InvalidStateException;

public abstract class Command extends Message {

    public Command(){
        super();
    }

    public Command(String msg){
        super(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

}
