package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class PlayersNumRequest extends Message {

    public PlayersNumRequest(){
        super();
    }

    public PlayersNumRequest(String msg){
        super(msg);
    }

    @Override
    public void apply(View view) {
        view.askNumberOfPlayers();
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

}
