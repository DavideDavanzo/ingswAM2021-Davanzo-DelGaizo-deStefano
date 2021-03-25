package it.polimi.ingsw.model.resources;

/**
 * Class which represents an amount of stones
 */
public class Stone extends Resource{

    /**
     * Default method
     */
    public Stone(){};

    /**
     * Parameterized constructor
     * @param numStones
     */
    public Stone(int numStones){
        volume = numStones;
    }

    /**
     * Implementation of super class "Resource"'s method
     * @return
     */
    @Override
    public String getResourceName() {
        return "Stone";
    }

}
