package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class LorenzoPositionUpdate extends Message{

    private int position;

    public LorenzoPositionUpdate(){}

    public LorenzoPositionUpdate(int position){
        this.position = position;
    }

    @Override
    public void apply(ClientView view) {
        view.updateLorenzoPosition(position);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
