package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class LorenzoPositionUpdate extends Message{

    private int position;

    public LorenzoPositionUpdate(){}

    public LorenzoPositionUpdate(int position){
        this.position = position;
    }

    @Override
    public void apply(View view) {
        view.updateLorenzoPosition(position);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
