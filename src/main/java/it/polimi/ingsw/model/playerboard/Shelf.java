package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.resources.*;

/**
 * Class which represents a single shelf of the player's warehouse
 */
public class Shelf {

    private boolean extraShelf;

    /**
     * All the resources in the shelf, all of the same type (ex: coins)
     */
    private Resource shelfResource;

    /**
     * Dynamic attribute that keeps track of the space left in the shelf
     */
    private int availableVolume;

    public Shelf() {}

    public Shelf(int shelfDimension) {
        extraShelf = false;
        this.availableVolume = shelfDimension;
    }

    public void emptyThisShelf() {
        shelfResource = null;
    }

    /**
     * To add or subtract a given amount of resources
     * @param newResource
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException if trying to introduce more resources than available spaces
     */
    public void updateShelf(Resource newResource) throws NotEnoughResourcesException, InvalidInputException {

        if(newResource.getVolume() > availableVolume)       //trying to introduce more resources than available spaces
            throw new InvalidInputException("Not enough spaces available in this shelf");
        else {
            if (shelfResource == null)                  //case: empty shelf
                setShelfResource(newResource);
            else{
                shelfResource.update(newResource);
                if(shelfResource.getVolume() == 0 && !isExtraShelf())              //shelf actually empty
                    emptyThisShelf();
            }
            availableVolume -= newResource.getVolume();     //update the available spaces in this shelf
        }

    }

    public Resource getShelfResource() {
        return shelfResource;
    }

    public void setShelfResource(Resource resource) {
        shelfResource = resource;
    }

    public boolean isExtraShelf() {
        return extraShelf;
    }

    public void setAsExtraShelf() {
        extraShelf = true;
    }

    public int getAvailableVolume() {
        return availableVolume;
    }

}
