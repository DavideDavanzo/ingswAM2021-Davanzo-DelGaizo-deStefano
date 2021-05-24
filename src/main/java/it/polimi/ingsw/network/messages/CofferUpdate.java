package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class CofferUpdate extends Message{

    public CofferUpdate() {
    }

    public CofferUpdate(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        view.updateCoffer(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

}
