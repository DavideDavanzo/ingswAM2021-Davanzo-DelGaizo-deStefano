package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;
import it.polimi.ingsw.view.View;

import java.util.HashMap;

public class WinMessage extends Message {

    boolean lorenzoWins;

    private HashMap<String, Integer> ranking;

    public WinMessage(String msg) {
        super(msg);
    }

    public WinMessage(String msg, boolean lorenzoWins) {
        super(msg);
        this.lorenzoWins = lorenzoWins;
    }

    public WinMessage(String msg, HashMap ranking) {
        super(msg);
        this.ranking = ranking;
    }

    @Override
    public void apply(ClientView view) {

    }

}
