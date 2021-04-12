package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.playerboard.path.Path;

/**
 * <h1>PlayerBoard</h1>
 */
public class PlayerBoard {

    private final Warehouse warehouse;
    private final Coffer coffer;
    private final DevelopmentCardsArea developmentCardsArea;
    private final Path path;

    public DevelopmentCardsArea getDevelopmentCardsArea() {
        return developmentCardsArea;
    }

    public PlayerBoard() {
        warehouse = new Warehouse();
        coffer = new Coffer();
        path = new Path();
        developmentCardsArea = new DevelopmentCardsArea();
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Coffer getCoffer() {
        return coffer;
    }

    public Path getPath() {
        return path;
    }

}
