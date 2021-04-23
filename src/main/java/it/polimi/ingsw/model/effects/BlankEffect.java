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
    public Object getAttribute() {
        return null;
    }

    @Override
    public String toString() {
        return "No Effect";
    }
}
