package it.polimi.ingsw.model.lorenzo;

import java.util.Random;

public class CrossAndShuffleToken extends CrossToken {
    private int steps;

    public CrossAndShuffleToken(){
        steps = 1;
    }

    @Override
    public void activate(LorenzoIlMagnifico lorenzo){
        lorenzo.move(steps);
        lorenzo.shuffleToken();
    }









}
