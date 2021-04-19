package it.polimi.ingsw.model.resources;


import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

import java.util.Iterator;

/**
 *  Represents an amount of resources of the same type (coins, shields, stones, servants)
 *  or a blank resource.
 */
public class Resource extends Item{

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return volume == resource.volume;
    }

    public boolean sameType(Object o) {
        //TODO: Remove
        return false;
    }

}
