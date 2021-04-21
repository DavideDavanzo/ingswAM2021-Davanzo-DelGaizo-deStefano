package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.GameOverException;

import java.util.Objects;

/**
 * Abstract class which represents either a resource or faith points
 */
abstract public class Item {

    protected int volume;

    public Item(){ }

    public Item(int volume){
        this.volume = volume;
    }

    public abstract void update(Item newItems) throws NotEnoughResourcesException;

    public abstract int addCoins();

    public abstract int addStones();

    public abstract int addShields();

    public abstract int addServants();

    public abstract int addFaithPoints();

    public int getVolume() {
         return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    //TODO: Remove
    public abstract boolean sameType(Object o);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return volume == item.volume;
    }

    @Override
    public abstract Item clone();
}
