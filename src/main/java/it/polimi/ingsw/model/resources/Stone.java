package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

/**
 * Class which represents an amount of stones
 */
public class Stone extends Resource{

    public Stone(){
        super();
    }

    public Stone(int numStones){
        super(numStones);
    }

    @Override
    public void update(Item newItems) throws NotEnoughResourcesException {
        if(volume + newItems.addStones() < 0)
            throw new NotEnoughResourcesException("Not enough resources to complee operation");
        volume += newItems.addStones();
    }

    @Override
    public int addCoins() {
        return 0;
    }

    @Override
    public int addStones() {
        return volume;
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
    public boolean equals(Object o) {

        if(!(o instanceof Stone))
            return false;

        Stone otherStone = (Stone) o;

        if( this.volume == otherStone.getVolume() )
            return true;

        return false;
    }

    @Override
    public boolean sameType(Object o) {
        return (o instanceof Stone);
    }

}
