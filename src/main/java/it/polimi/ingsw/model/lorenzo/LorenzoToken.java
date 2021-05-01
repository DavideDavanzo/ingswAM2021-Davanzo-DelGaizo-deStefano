package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;

/**
 * Class that represents the tokens used to play a solo match
 */
public abstract class LorenzoToken {

    private boolean flipped;

    public void flip(){
        flipped = !flipped;
    }

    public abstract void activate(LorenzoIlMagnifico lorenzo) throws LossException;


}
