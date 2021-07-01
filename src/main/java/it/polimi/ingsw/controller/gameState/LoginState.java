package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.messages.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
                if(message.getPlayerNum() != 1)
                    gameController.getVirtualViewMap().get(message.getUsername()).showMessage("Ok,waiting for players . .");
                else    gameController.getVirtualViewMap().get(message.getUsername()).showMessage("Ok!");
                gameController.notifyAll();
            }
            if(gameController.isSinglePlayer()){
                nextState();
                gameController.startMatch(); //Directly starts a singlePlayer match.
            }

        }
    }

    @Override
    public void process(Disconnection disconnection) {
        gameController.getVirtualViewMap().remove(disconnection.getUsername());
        gameController.notifyObservers(new HashSet<>(Arrays.asList(disconnection.getUsername())));
    }

    @Override
    public void nextState() {
        gameController.setGameState(new InitState(gameController));
    }

}
