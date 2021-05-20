package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class DiscardLeaderCmd extends Message{

    ArrayList<Integer> choices;

    public DiscardLeaderCmd() {
    }

    public DiscardLeaderCmd(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public ArrayList<Integer> getChoices() {
        return choices;
    }
}
