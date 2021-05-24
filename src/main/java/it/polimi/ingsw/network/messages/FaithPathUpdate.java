package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class FaithPathUpdate extends Message{

    public FaithPathUpdate() {
    }

    public FaithPathUpdate(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        view.updateFaithTrack(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

}
