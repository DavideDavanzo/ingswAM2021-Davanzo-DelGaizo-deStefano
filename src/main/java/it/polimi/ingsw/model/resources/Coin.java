package it.polimi.ingsw.model.resources;

/**
 * Class which represents an amount of coins
 */
public class Coin extends Resource{

    /**
     * Default method
     */
    public Coin(){};

    /**
     * Parameterized constructor
     * @param numCoins
     */
    public Coin(int numCoins){
        volume = numCoins;
    }

    /**
     * Implementation of super class "Resource"'s method
     * @return
     */
    @Override
    public String getResourceName() {
        return "Coin";
    }
}
