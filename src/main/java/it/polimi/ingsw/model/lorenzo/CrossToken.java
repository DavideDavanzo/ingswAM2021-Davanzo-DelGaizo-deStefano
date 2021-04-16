package it.polimi.ingsw.model.lorenzo;

import java.util.Stack;

public class CrossToken extends LorenzoToken{
    private int steps;

    public CrossToken(){
        steps = 2;
    }




    @Override
    public void activate(LorenzoIlMagnifico lorenzo) {
       lorenzo.move(steps);
    }
}
