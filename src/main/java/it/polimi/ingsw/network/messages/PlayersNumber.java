package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class PlayersNumber extends Message {

    private int playerNum;

    public PlayersNumber(){
        super();
    }

    public PlayersNumber(int playerNum){
        this.playerNum = playerNum;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public int getPlayerNum() {
        return playerNum;
    }

}
