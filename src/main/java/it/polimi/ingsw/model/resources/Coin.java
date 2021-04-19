package it.polimi.ingsw.model.resources;

/**
 * Class which represents a general amount of coins
 */
public class Coin extends Resource {

    public Coin(){
        super();
    }

    public Coin(int numCoins){
        super(numCoins);
    }

    @Override
    public boolean equals(Object o) {

        if(!(o instanceof Coin))
            return false;

        Coin otherCoin = (Coin) o;

        return this.volume == otherCoin.getVolume();
    }

    //TODO: Remove
    @Override
    public boolean sameType(Object o) {
        return (o instanceof Coin);
    }


}
