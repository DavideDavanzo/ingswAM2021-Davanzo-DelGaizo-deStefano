package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class PlayersNumber extends Message {

    private int playerNum;

    public PlayersNumber(){
        super();
    }

    public PlayersNumber(String msg){
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

    public int getPlayerNum() {
        return playerNum;
    }
}
