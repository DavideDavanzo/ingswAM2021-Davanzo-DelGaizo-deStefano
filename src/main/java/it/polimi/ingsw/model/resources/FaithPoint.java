package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.GameOverException;

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
    public void update(Item newPoints) throws GameOverException {

        //The path has 20 positions, player can't go further
        volume = volume + newPoints.getVolume() < 20 ? volume + newPoints.getVolume() : 20;

        //Finish line of the path  (last vatican report)
        if(getVolume()==20)
            throw new GameOverException("Last vatican report. Game Over");

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

}