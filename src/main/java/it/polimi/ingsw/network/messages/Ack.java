package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class Ack extends Message {

    private boolean ack;

    public Ack() {
    }

    public Ack(String msg, boolean ack) {
        super(msg);
        this.ack = ack;
    }

    public Ack(boolean ack) {
        this.ack = ack;
    }

    @Override
    public void apply(ClientView view) {
        view.getClientModel().setMyTurn(true);  //no ack after PassTurnMessage which sets myTurn to false directly
        view.processAck(this);
    }

    public boolean isAck() {
        return ack;
    }

    public boolean isNack() {
        return !ack;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }
}
