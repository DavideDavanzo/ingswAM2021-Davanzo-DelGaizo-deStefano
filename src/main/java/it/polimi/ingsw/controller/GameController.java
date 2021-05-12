package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.controller.gameState.LoginState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.controllerExceptions.NicknameException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.observingPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameController implements Observer, Serializable {

    private Match match;
    private Map<String, VirtualView> virtualViewMap;

    private GameState gameState;

    public GameController() {
        this.match = new Match();
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.gameState = new LoginState(match, this);
    }

    public void onMessage(Message received) {

        //TODO: Create VV taken from map of received nickname

        try {
            received.getProcessedBy(gameState);
        } catch (InvalidStateException e) {
            //TODO: Notify server that the message is invalid in this state.
        }

    }

    public void logPlayer(String nickname, VirtualView virtualView) {

        if(virtualViewMap.isEmpty()) {

            try {
                addVirtualView(nickname, virtualView);
            } catch (NicknameException e) { //This catch will never happen, it's explicit just for code richness.
                virtualView.showLogin("error", false);
                return;
            }

            match.addPlayer(new Player(nickname));
            virtualView.showLogin("You've been logged in successfully", true);
            virtualView.askNumberOfPlayers();

        }
        else if(!match.isFull()) {

            try {
                addVirtualView(nickname, virtualView);
            } catch (NicknameException e) {
                virtualView.showLogin("Nickname already logged, try another one", false);
                return;
            }

            match.addPlayer(new Player(nickname));
            virtualView.showLogin("You've been logged in successfully", true);

            if(match.isFull()) {
                //TODO: Restore interrupted match ??
                startMatch();
            }

        }
        else {
            virtualView.showError("Sorry, something went wrong..");
        }

    }

    public void startMatch() {
        //TODO: Match start messages and calls. Remember to divide singlePlayer and multiPlayer.
    }

    public void addVirtualView(String nickname, VirtualView virtualView) throws NicknameException {

        if(virtualViewMap.containsKey(nickname)) throw new NicknameException();

        virtualViewMap.put(nickname, virtualView);
        match.addObserver(virtualView);
        //TODO: SharedArea Observable? -> addObserver(virtualView)?
    }

    @Override
    public void update(Message message) {

    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return virtualViewMap;
    }

    public boolean isFull() {
        return match.isFull();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
