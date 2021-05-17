package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.messages.*;

public class LoginState extends GameState {

    public LoginState(GameController gameController) {
        super(gameController);
    }

    @Override
    public void process(PlayersNumber message) {
        synchronized (gameController) {

            if (!gameController.setChosenPlayersNum(message.getPlayerNum()))
                gameController.getVirtualViewMap().get(message.getUsername()).askNumberOfPlayers();
            else {
                gameController.getVirtualViewMap().get(message.getUsername()).showMessage("Ok,waiting for players . .");
                gameController.notifyAll();
            }
            if(gameController.isSinglePlayer()){
                nextState();
                gameController.startMatch(); //Directly starts a singlePlayer match.
            }

        }
    }

    @Override
    public void nextState() {
        gameController.setGameState(new InitState(gameController));
    }
}
