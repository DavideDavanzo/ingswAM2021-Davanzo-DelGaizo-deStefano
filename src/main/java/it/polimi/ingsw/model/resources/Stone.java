package it.polimi.ingsw.model.resources;

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

}
