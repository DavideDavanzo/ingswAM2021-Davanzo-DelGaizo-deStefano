package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class PlayersNumRequest extends Message{

    public PlayersNumRequest(){
        super();
    }

    @Override
    public void apply(ClientView view) {
        view.askNumberOfPlayers();
    }

}
