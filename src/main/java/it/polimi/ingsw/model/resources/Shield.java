package it.polimi.ingsw.model.resources;

/**
 * Class which represents an amount of shields
 */
public class Shield extends Resource{

    /**
     * Default method
     */
    public Shield(){};

    /**
     * Parameterized constructor
     * @param numShields
     */
    public Shield(int numShields){
        volume = numShields;
    }

    /**
     * Implementation of super class "Resource"'s method
     * @return
     */
    @Override
    public String getResourceName() {
        return "Shield";
    }

}
