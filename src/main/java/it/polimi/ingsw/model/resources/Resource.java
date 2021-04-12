package it.polimi.ingsw.model.resources;


import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

/**
 * Abstract class which represents an amount of resources of the same type (coins, shields, stones, servants)
 */
public abstract class Resource extends Item{

    public Resource(){
        super();
    }

    public Resource(int numResources) {
        super(numResources);
    }

    /**
     * Method which modifies the amount of resources in the instance
     * @param newResource
     * @throws NotEnoughResourcesException when asked to remove more resources than current volume
     */
    @Override
    public void update(Item newResource) throws NotEnoughResourcesException, InvalidInputException {

        if (volume + newResource.getVolume() < 0) {         //volume cannot decreased below zero
            // if so, volume must not be updated
            throw new NotEnoughResourcesException("There are not enough resources to complete this operation");
        } else if(!this.sameType(newResource)) {
            throw new InvalidInputException("Trying to add resources of different type");
        } else {
            volume = volume + newResource.getVolume();      //add volume of newResource to this instance's one
        }

    }

    /**
     * This equals to other resource if they are of the same subclass and have same volume
     */
    @Override
    public abstract boolean equals(Object o);

    public abstract boolean sameType(Object o);

}
