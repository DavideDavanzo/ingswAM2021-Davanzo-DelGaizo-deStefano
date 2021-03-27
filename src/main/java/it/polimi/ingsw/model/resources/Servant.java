package it.polimi.ingsw.model.resources;

/**
 * Class which represents a group of servants
 */
public class Servant extends Resource{

    /**
     * Default method
     */
    public Servant(){
        super();
    }

    /**
     * Parameterized constructor
     * @param numServants
     */
    public Servant(int numServants) {
        super(numServants);
    }

}
