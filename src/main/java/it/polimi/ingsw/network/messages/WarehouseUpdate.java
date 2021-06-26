package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.view.ClientView;

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
    public void apply(ClientView view) {
        view.updateWarehouse(warehouse);
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
