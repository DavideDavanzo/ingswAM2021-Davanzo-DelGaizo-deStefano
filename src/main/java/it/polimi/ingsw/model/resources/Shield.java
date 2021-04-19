package it.polimi.ingsw.model.resources;

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
