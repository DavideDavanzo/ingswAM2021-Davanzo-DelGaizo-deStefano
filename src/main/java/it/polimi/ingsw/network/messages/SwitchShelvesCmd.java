package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class SwitchShelvesCmd extends Message {

    private int first;
    private int second;

    public SwitchShelvesCmd() {

    }

    public SwitchShelvesCmd(String msg) {
        super(msg);
    }

    public SwitchShelvesCmd(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
