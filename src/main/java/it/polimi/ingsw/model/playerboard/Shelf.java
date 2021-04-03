package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.resources.Resource;

public class Shelf {
    private Resource shelfResource;
    private int dimension;

    public Shelf() {}

    public Shelf(int dimension) {
        this.dimension = dimension;
    }
}
