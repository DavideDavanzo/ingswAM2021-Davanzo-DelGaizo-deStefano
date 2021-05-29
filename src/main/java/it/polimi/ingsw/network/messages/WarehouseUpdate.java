package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.view.View;

public class WarehouseUpdate extends Message{

    @JsonSerialize(as = Warehouse.class)
    private Warehouse warehouse;

    public WarehouseUpdate() {
        super();
    }

    public WarehouseUpdate(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void apply(View view) {
        view.updateWarehouse(warehouse);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
