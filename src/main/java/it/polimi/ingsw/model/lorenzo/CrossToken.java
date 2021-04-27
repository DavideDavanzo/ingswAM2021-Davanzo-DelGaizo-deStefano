package it.polimi.ingsw.model.lorenzo;

import java.util.Stack;

/**
 * The class represents a specific kind of token
 */
public class CrossToken extends LorenzoToken{
    private int steps;

    public CrossToken(){
        steps = 2;
    }

    /**
     * This method calls method move(), that increases Lorenzo's position
     * @param lorenzo represents Lorenzo il Magnifico
     */

    @Override
    public void activate(LorenzoIlMagnifico lorenzo) {
       lorenzo.move(steps);
    }
}
