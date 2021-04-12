package it.polimi.ingsw.model.resources;


import it.polimi.ingsw.exceptions.InvalidInputException;

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
    public void update(Item newPoints) throws InvalidInputException {
        if(this.sameType(newPoints))
            //The path has 20 positions, player can't go further
            volume = Math.min(volume + newPoints.getVolume(), 24);
        else
            throw new InvalidInputException("Trying to add items of different type");
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

}