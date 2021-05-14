package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;

/**
 * Class which represents an amount of stones
 */
public class Stone extends Resource {

    public Stone(){
        super();
    }

    public Stone(int numStones){
        super(numStones);
    }

    @Override
    public void update(Item newItems) throws NotEnoughResourcesException {
        if(volume + newItems.addStones() < 0)
            throw new NotEnoughResourcesException("Not enough resources to complete operation");
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
    public String print() {
        return Color.ANSI_GREY.escape() + "●" + Color.ANSI_WHITE.escape();
    }

    @Override
    public boolean equals(Object o) {

        if(!(o instanceof Stone))
            return false;

        Stone otherStone = (Stone) o;

        return this.volume == otherStone.getVolume();
    }

    //TODO: Remove
    @Override
    public boolean sameType(Object o) {
        return (o instanceof Stone);
    }

    @Override
    public Resource clone() {
        return new Stone(getVolume());
    }

    @Override
    public String toString() {
        return "Stone: " + getVolume();
    }
}
