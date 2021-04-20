package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

/**
 * Class which represents an amount of shields
 */
public class Shield extends Resource{

    public Shield(){
        super();
    }

    public Shield(int numShields){
        super(numShields);
    }

    @Override
    public void update(Item newItems) throws NotEnoughResourcesException {
        if(volume + newItems.addShields() < 0)
            throw new NotEnoughResourcesException("Not enough resources to complee operation");
        volume += newItems.addShields();
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
        return volume;
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
    public boolean equals(Object o) {

        if(!(o instanceof Shield))
            return false;

        Shield otherShield = (Shield) o;

        return this.volume == otherShield.getVolume();
    }

    //TODO: Remove
    @Override
    public boolean sameType(Object o) {
        return (o instanceof Shield);
    }

}
