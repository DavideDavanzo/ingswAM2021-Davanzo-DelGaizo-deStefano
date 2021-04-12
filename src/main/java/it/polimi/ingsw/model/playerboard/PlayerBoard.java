package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.playerboard.path.Path;

public class PlayerBoard {

    private final Warehouse warehouse = new Warehouse();
    private final Coffer coffer = new Coffer();
    private final DevelopmentCardsArea developmentCardsArea = new DevelopmentCardsArea();
    private final Path path = new Path();

    public DevelopmentCardsArea getDevelopmentCardsArea() {
        return developmentCardsArea;
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
