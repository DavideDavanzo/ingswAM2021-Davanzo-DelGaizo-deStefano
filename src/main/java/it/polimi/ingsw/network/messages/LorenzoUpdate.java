package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class LorenzoUpdate extends Message{

    private int position;

    public LorenzoUpdate(){}

    public LorenzoUpdate(int position){
        this.position = position;
    }

    @Override
    public void apply(ClientView view) {
        view.updateLorenzo(position);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
