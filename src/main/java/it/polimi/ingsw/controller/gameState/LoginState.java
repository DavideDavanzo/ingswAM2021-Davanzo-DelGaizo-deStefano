package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.messages.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * LoginState is the first state of the game, where the
 * server handles the {@link PlayersNumber} requests
 * from the first player of the match.
 */
public class LoginState extends GameState {

    public LoginState(GameController gameController) {
        super(gameController);
    }

    /**
     * Processes player number requests from the first player
     * connecting to a new match. If the mode is single player,
     * then it starts the match.
     * @param message contains the number requested, it has to be
     *                exact, from 1 to 4.
     */
    @Override
    public void process(PlayersNumber message) {
        synchronized (gameController) {

            if (!gameController.setChosenPlayersNum(message.getPlayerNum()))
                gameController.getVirtualViewMap().get(message.getUsername()).askNumberOfPlayers();
            else {
                if(message.getPlayerNum() != 1)
                    gameController.getVirtualViewMap().get(message.getUsername()).showMessage("Ok,waiting for players . .");
                gameController.notifyAll();
            }
            if(gameController.isSinglePlayer()){
                nextState();
                gameController.startMatch(); //Directly starts a singlePlayer match.
            }

        }
    }

    /**
     * Processes a disconnection, notifies other players. If the number of players
     * is under the value of 2, it interrupts a multiplayer game.
     * @param disconnection is the disconnection message.
     */
    @Override
    public void process(Disconnection disconnection) {
        gameController.getVirtualViewMap().remove(disconnection.getUsername());
        gameController.notifyObservers(new HashSet<>(Arrays.asList(disconnection.getUsername())));
    }

    /**
     * Changes the game state, to {@link InitState}.
     */
    @Override
    public void nextState() {
        gameController.setGameState(new InitState(gameController));
    }

}
