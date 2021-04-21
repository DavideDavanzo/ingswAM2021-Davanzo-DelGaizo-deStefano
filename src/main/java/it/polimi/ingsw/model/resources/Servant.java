package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

/**
 * Class which represents a group of servants
 */
public class Servant extends Resource {

    public Servant(){
        super();
    }

    public Servant(int numServants) {
        super(numServants);
    }

    @Override
    public void update(Item newItems) throws NotEnoughResourcesException {
        if(volume + newItems.addServants() < 0)
            throw new NotEnoughResourcesException("Not enough resources to complete operation");
        volume += newItems.addServants();
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
        return volume;
    }

    @Override
    public int addFaithPoints() {
        return 0;
    }


    @Override
    public boolean equals(Object o) {

        if(!(o instanceof Servant))
            return false;

        Servant otherServant = (Servant) o;

        return this.volume == otherServant.getVolume();
    }

    //TODO: Remove
    @Override
    public boolean sameType(Object o) {
        return (o instanceof Servant);
    }

    @Override
    public Resource clone() {
        return new Servant(getVolume());
    }
}
