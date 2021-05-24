package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class WarehouseUpdate extends Message{

    public WarehouseUpdate() {
        super();
    }

    public WarehouseUpdate(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        view.updateWarehouse(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

}
