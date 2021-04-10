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

        if( this.volume == otherCoin.getVolume() )
            return true;

        return false;
    }


}
