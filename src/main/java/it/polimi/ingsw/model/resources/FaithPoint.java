package it.polimi.ingsw.model.resources;


import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;

/**
 * Class which represents an amount of faith points
 */
public class FaithPoint extends Item {

    public FaithPoint(){
        super();
    }

    public FaithPoint(int numPoints) {
        super(numPoints);
    }

    @Override
    public void update(Item newItems) {
        volume = Math.min( (volume + newItems.addFaithPoints()), 24 );
    }

    @Override
    public int addCoins() {
        return 0;
    }

    @Override
    public int addStones() {
        return 0;
    }

    @Override
    public int addShields() {
        return 0;
    }

    @Override
    public int addServants() {
        return 0;
    }

    @Override
    public int addFaithPoints() {
        return volume;
    }

    @Override
    public int pathSteps() {
        return volume;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FaithPoint))
            return false;
        FaithPoint otherFaithPoint = (FaithPoint) o;
        if( this.volume == otherFaithPoint.getVolume() )
            return true;
        return false;
    }

    @Override
    public boolean sameType(Object o) {
        return (o instanceof FaithPoint);
    }

    @Override
    public Item clone() {
        return new FaithPoint(getVolume());
    }

    @Override
    public String toString() {
        return "FaithPoint: " + getVolume();
    }

    @Override
    public String print() {
        return Color.ANSI_RED.escape() + "U+271F" + Color.ANSI_WHITE.escape();
    }
}