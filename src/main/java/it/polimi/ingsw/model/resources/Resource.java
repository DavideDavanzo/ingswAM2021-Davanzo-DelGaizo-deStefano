package it.polimi.ingsw.model.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Coin.class, name = "coin"),
        @JsonSubTypes.Type(value = Servant.class, name = "servant"),
        @JsonSubTypes.Type(value = Shield.class, name = "shield"),
        @JsonSubTypes.Type(value = Stone.class, name = "stone")
})
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

    @Override
    public boolean sameType(Object o) {
        return o instanceof Resource;
    }

    @Override
    public Resource clone() {
        return new Resource(getVolume());
    }

    @Override
    public String toString() {
        return "Resource: " + getVolume();
    }

    @Override
    public String print() {
        return "? ";
    }
}
