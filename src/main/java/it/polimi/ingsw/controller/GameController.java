package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.controller.gameState.LoginState;
import it.polimi.ingsw.exceptions.InvalidStateException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.observingPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameController implements Observer, Serializable {

    private Match match;
    private Map<String, VirtualView> virtualViews;

    private GameState gameState;

    public GameController() {
        this.virtualViews = Collections.synchronizedMap(new HashMap<>());
        this.gameState = new LoginState();
    }

    public void onMessage(Message received) {
        try {
            received.getProcessedBy(gameState);
        } catch (InvalidStateException e) {
            //TODO: Notify server that the message is invalid in this state.
        }
    }

    public void addVirtualView(String nickname, VirtualView vv) {
        virtualViews.put(nickname, vv);
    }

    @Override
    public void update(Message message) {

    }
}
