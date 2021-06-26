package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;

public class ActivateLeaderCmd extends Message {

    ArrayList<Integer> choices;

    public ActivateLeaderCmd() {
        super();
    }

    public ActivateLeaderCmd(String msg){
        super(msg);
    }

    public ActivateLeaderCmd(ArrayList<Integer> choices) {
        this.choices = choices;
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public ArrayList<Integer> getChoices() {
        return choices;
    }
}
