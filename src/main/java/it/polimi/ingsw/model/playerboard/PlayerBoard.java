package it.polimi.ingsw.model.playerboard;


/**
 * <h1>PlayerBoard</h1>
 */
public class PlayerBoard {
    private Warehouse warehouse;
    private Coffer coffer;
    private DevelopmentCardsArea developmentCardsArea;

    public DevelopmentCardsArea getDevelopmentCardsArea() {
        return developmentCardsArea;
    }

    public PlayerBoard() { warehouse = new Warehouse(); }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Coffer getCoffer() {
        return coffer;
    }

}
