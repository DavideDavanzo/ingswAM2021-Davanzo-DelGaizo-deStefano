package it.polimi.ingsw.model.resources;


import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

/**
 *  Represents an amount of resources of the same type (coins, shields, stones, servants)
 *  or a blank resource.
 */
public class Resource extends Item {

    public Resource(){
        super();
    }

    public Resource(int numResources) {
        super(numResources);
    }

    @Override
    public void update(Item newItems) throws NotEnoughResourcesException {
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
        return 0;
    }

    @Override
    public int pathSteps() {
        return 0;
    }


    /**
     * This equals to other resource if they are of the same subclass and have same volume
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return volume == resource.volume;
    }

    //TODO: Remove
    public boolean sameType(Object o) {
        return false;
    }

    @Override
    public Resource clone() {
        return new Resource(getVolume());
    }
}
