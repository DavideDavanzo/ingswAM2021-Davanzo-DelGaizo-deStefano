package it.polimi.ingsw.model.resources;

/**
 * Class which represents an amount of coins
 */
public class Coin extends Resource{

    /**
     * Default method
     */
    public Coin(){
        super();
    }

    /**
     * Parameterized constructor
     * @param numCoins
     */
    public Coin(int numCoins){
        super(numCoins);
    }

}
