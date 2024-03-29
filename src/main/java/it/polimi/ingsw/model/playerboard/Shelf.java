package it.polimi.ingsw.model.playerboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.resources.*;

/**
 * Class that represents a single shelf of the player's {@link Warehouse}.
 */
public class Shelf {

    private boolean empty;

    private int dimension;

    private boolean extraShelf;

    /**
     * The resources in the shelf are all of the same type (ex: coins).
     */
    private Resource shelfResource;

    /**
     * Dynamic attribute that keeps track of the space left in the shelf.
     */
    private int availableVolume;

    public Shelf(){
    }

    public Shelf(int dimension) {
        extraShelf = false;
        shelfResource = new Resource(0);
        this.dimension = dimension;
        availableVolume = dimension;
        empty = true;
    }

    /**
     * Empties a shelf, setting the Resource to a Blank one, except for {@link ExtraShelfEffect}.
     */
    public void emptyThisShelf() {
        empty = true;
        if(!isExtraShelf()) shelfResource = new Resource(0);
        if(isExtraShelf()) shelfResource.setVolume(0);
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

    /**
     * Sets the shelf resource type and initial quantity.
     * @param resource
     * @throws IllegalArgumentException if the volume of the resource is negative or
     * exceeds the maximum capacity of the shelf.
     */
    public void setShelfResource(Resource resource) throws IllegalArgumentException {

        if(resource == null) {
            emptyThisShelf();
            return;
        }

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

    /**
     * Special volume getter.
     * @return 0 if the shelf is empty, resource's volume otherwise.
     */
    @JsonIgnore
    public int getResourceVolume() {
        return shelfResource == null ? 0 : shelfResource.getVolume();
    }

    public int getDimension() {
        return dimension;
    }




}