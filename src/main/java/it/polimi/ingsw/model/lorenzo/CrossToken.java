package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;

import java.util.Stack;

/**
 * The class represents a specific kind of token: it increases
 * Lorenzo's position by 2.
 */
public class CrossToken extends LorenzoToken{

    private int steps;

    public CrossToken(){
        super();
        steps = 2;
    }

    /**
     * This method calls method {@link LorenzoIlMagnifico#move(int)}, that increases Lorenzo's position
     * @param lorenzo represents Lorenzo il Magnifico
     */
    @Override
    public void activate(LorenzoIlMagnifico lorenzo) throws LossException {
       lorenzo.move(steps);
    }

    @Override
    public String toString() {
        return "Cross Token: moves Lorenzo 2 positions forward.";
    }
}
