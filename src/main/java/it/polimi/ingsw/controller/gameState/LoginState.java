package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.messages.*;

public class LoginState extends GameState {

    private final GameController gameController;

    public LoginState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void process(PlayersNumber message) {
        if(!gameController.setChosenPlayersNum(message.getPlayerNum()))
            gameController.getVirtualViewMap().get(message.getUsername()).askNumberOfPlayers();
        else
            gameController.getVirtualViewMap().get(message.getUsername()).showMessage("Ok,waiting for players . .");
            if(gameController.isSinglePlayer()) gameController.startMatch(); //Directly starts a singlePlayer match.
    }

}
