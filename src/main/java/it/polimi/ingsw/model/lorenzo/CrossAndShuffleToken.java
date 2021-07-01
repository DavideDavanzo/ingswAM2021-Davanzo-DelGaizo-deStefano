package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;

import java.util.Random;

/**
 * The class represents a specific kind of token:
 * it moves Lorenzo forward by 1 and shuffles the token stack.
 */
public class CrossAndShuffleToken extends CrossToken {

    private int steps;

    public CrossAndShuffleToken(){
        super();
        steps = 1;
    }

    /**
     * This method calls method move(), that increases Lorenzo's position and shuffles all tokens
     * @param lorenzo represents Lorenzo il Magnifico
     */
    @Override
    public void activate(LorenzoIlMagnifico lorenzo) throws LossException {
        lorenzo.move(steps);
        lorenzo.shuffleTokens();
    }

    @Override
    public String toString() {
        return "Cross & Shuffle Token: moves Lorenzo 1 position forward and shuffles the token stack.";
    }
}
