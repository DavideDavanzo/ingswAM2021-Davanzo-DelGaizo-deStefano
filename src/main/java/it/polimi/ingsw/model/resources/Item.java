package it.polimi.ingsw.model.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.polimi.ingsw.view.CliPrinter;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Resource.class, name = "resource"),
        @JsonSubTypes.Type(value = Coin.class, name = "coin"),
        @JsonSubTypes.Type(value = Servant.class, name = "servant"),
        @JsonSubTypes.Type(value = Shield.class, name = "shield"),
        @JsonSubTypes.Type(value = Stone.class, name = "stone"),
        @JsonSubTypes.Type(value = FaithPoint.class, name = "faith")
})
/**
 * Abstract class which represents either a resource or faith points
 */
abstract public class Item implements CliPrinter {

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

    public abstract int pathSteps();

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
