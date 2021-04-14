package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.GameOverException;

/**
 * Abstract class which represents either a resource or faith points
 */
abstract public class Item {
    protected int volume;

    public Item(){
    }

    public Item(int volume){
        this.volume = volume;
    }

    /**
     * Method which increases or decreases the volume of resources
     * @param newItem is the amount added (positive or negative)
     */
     public abstract void update(Item newItem) throws NotEnoughResourcesException, GameOverException, InvalidInputException;

     public int getVolume(){
         return volume;
     }

     public void setVolume(int volume) {
        this.volume = volume;
     }

     public abstract boolean sameType(Object o);

}
