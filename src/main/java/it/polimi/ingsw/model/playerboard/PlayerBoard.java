package it.polimi.ingsw.model.playerboard;


/**
 * <h1>PlayerBoard</h1>
 */
public class PlayerBoard {
    private Warehouse warehouse;

    public PlayerBoard() { warehouse = new Warehouse(); }

    public Warehouse getWarehouse() {
        return warehouse;
    }

}
