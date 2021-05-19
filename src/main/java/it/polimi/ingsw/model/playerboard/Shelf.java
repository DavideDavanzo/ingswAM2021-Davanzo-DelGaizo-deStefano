package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.resources.*;

/**
 * Class which represents a single shelf of the player's warehouse
 */
public class Shelf {

    private boolean empty;

    private final int dimension;

    private boolean extraShelf;

    /**
     * All the resources in the shelf, all of the same type (ex: coins)
     */
    private Resource shelfResource;

    /**
     * Dynamic attribute that keeps track of the space left in the shelf
     */
    private int availableVolume;

    public Shelf(int dimension) {
        extraShelf = false;
        this.dimension = dimension;
        availableVolume = dimension;
        empty = true;
    }

    public void emptyThisShelf() {
        empty = true;
        shelfResource.setVolume(0);
        availableVolume = dimension;
    }

    /**
     * To add or subtract a given amount of resources
     * @param newResource
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException if trying to introduce more resources than available spaces
     */
    public void updateShelf(Resource newResource) throws NotEnoughResourcesException, InvalidInputException {

        if(newResource.getVolume() > availableVolume)   //Trying to introduce more resources than available spaces
            throw new InvalidInputException("Not enough spaces available in this shelf");

        else {

            if (isEmpty() && newResource.getVolume() > 0 && !isExtraShelf()) {
                setShelfResource(newResource);   //Empty case, just sets.
                empty = false;
            }

            else {

                shelfResource.update(newResource);

                if(shelfResource.getVolume() == 0 && !isExtraShelf()) {
                    emptyThisShelf();   //Empties the shelf.
                    return;
                }

            }

            empty = false;
            availableVolume = dimension - shelfResource.getVolume();

        }

    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isExtraShelf() {
        return extraShelf;
    }

    public Resource getShelfResource() {
        return shelfResource;
    }

    public void setShelfResource(Resource resource) throws IllegalArgumentException {

        int volume = resource.getVolume();

        if(volume < 0 || volume > dimension)
            throw new IllegalArgumentException("Too many Resources for this Shelf! Can only have : " + dimension);

        shelfResource = resource;
        availableVolume = dimension - volume;
        empty = volume == 0;
    }

    public void setAsExtraShelf() {
        extraShelf = true;
    }

    public int getAvailableVolume() {
        return availableVolume;
    }

    public int getDimension() {
        return dimension;
    }




}