package it.polimi.ingsw.model.resources;

/**
 * Class which represents a group of servants
 */
public class Servant extends Resource{

    /**
     * Default method
     */
    public Servant(){};

    /**
     * Parameterized constructor
     * @param numServants
     */
    public Servant(int numServants) {
        volume = numServants;
    }

    /**
     * Implementation of super class "Resource"'s method
     * @return
     */
    @Override
    public String getResourceName() {
        return "Servant";
    }

}
