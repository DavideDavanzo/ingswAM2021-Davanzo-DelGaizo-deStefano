package it.polimi.ingsw.model.lorenzo;

import java.util.Random;

/**
 * The class represents a specific kind of token
 */
public class CrossAndShuffleToken extends CrossToken {
    private int steps;

    public CrossAndShuffleToken(){
        steps = 1;
    }

    /**
     * This method calls method move(), that increases Lorenzo's position and shuffles all tokens
     * @param lorenzo represents Lorenzo il Magnifico
     */

    @Override
    public void activate(LorenzoIlMagnifico lorenzo){
        lorenzo.move(steps);
        lorenzo.shuffleToken();
    }









}
