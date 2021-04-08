package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.resources.Resource;

/**
 * Class which represents a single shelf of the player's warehouse
 */
public class Shelf {

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
        this.availableVolume = shelfDimension;
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
            if (shelfResource == null)                      //case: empty shelf
                this.setShelfResource(newResource);
            else
                shelfResource.update(newResource);          //trying to add the incoming resources
            availableVolume -= newResource.getVolume();     //update the available spaces in this shelf
        }

    }

    public Resource getShelfResource() {
        return shelfResource;
    }

    public void setShelfResource(Resource resource) {
        shelfResource = resource;
    }

    public int getAvailableVolume() {
        return availableVolume;
    }

}
