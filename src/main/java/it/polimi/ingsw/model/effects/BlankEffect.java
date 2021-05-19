package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * An effect without any power.
 */
public class BlankEffect extends Effect {

    public BlankEffect() { }

    @Override
    public void applyOn(Player p) {
        return;
    }

    @Override
    public Object returnAttribute() {
        return null;
    }

    @Override
    public String toString() {
        return "No Effect";
    }

    @Override
    public String print() {
        return "None ! ";
    }
}
