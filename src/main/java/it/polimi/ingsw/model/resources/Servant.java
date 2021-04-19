package it.polimi.ingsw.model.resources;

/**
 * Class which represents a group of servants
 */
public class Servant extends Resource{

    public Servant(){
        super();
    }

    public Servant(int numServants) {
        super(numServants);
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

}
