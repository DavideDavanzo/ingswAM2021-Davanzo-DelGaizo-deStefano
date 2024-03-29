package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.enums.Color;

/**
 * Class which represents a general amount of coins
 */
public class Coin extends Resource {

    public Coin(){
        super();
    }

    public Coin(int numCoins){
        super(numCoins);
    }

    /**
     * Updates the volume of the resource.
     * @param newItems is a generic item, but only the same type of resource will be updated.
     * @throws NotEnoughResourcesException when a negative (<0) update is performed.
     */
    @Override
    public void update(Item newItems) throws NotEnoughResourcesException {
        if(volume + newItems.addCoins() < 0)
            throw new NotEnoughResourcesException("Not enough resources to complete operation");
        volume += newItems.addCoins();
    }

    @Override
    public int addCoins() {
        return volume;
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
    public boolean equals(Object o) {

        if(!(o instanceof Coin))
            return false;

        Coin otherCoin = (Coin) o;

        return this.volume == otherCoin.getVolume();
    }

    //TODO: Remove
    @Override
    public boolean sameType(Object o) {
        return (o instanceof Coin);
    }

    @Override
    public Resource clone() {
        return new Coin(getVolume());
    }

    @Override
    public String toString() {
        return "Coin: " + getVolume();
    }

    @Override
    public String print() {
        return Color.ANSI_YELLOW.escape() + "● " + Color.ANSI_WHITE.escape();
    }


}
