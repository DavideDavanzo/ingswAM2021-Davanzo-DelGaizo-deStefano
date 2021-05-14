package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.FaithPoint;


public class ExtraDevEffect extends Effect {
    private Trade extraTrade;

    /**
     * Default Constructor
     */
    public ExtraDevEffect() { }

    /**
     * Main Constructor
     * @param extraTrade is an extra Trade that gives a {@link FaithPoint} and a selectable {@link Resource}
     */
    public ExtraDevEffect(Trade extraTrade) {
        this.extraTrade = extraTrade;
    }

    /**
     * Applies the Effect on the Player
     * Calls {@link #addExtraTrade(Player) addExtraTrade} method
     * @param p is the {@link Player}
     */
    @Override
    public void applyOn(Player p) {
        addExtraTrade(p);
    }

    private void addExtraTrade(Player p) {
        p.giveExtraTrade(extraTrade);
    }

    /**
     * Special Getter
     * @return is Effect's Trade attribute
     */
    @Override
    public Object returnAttribute() {
        return getExtraTrade();
    }

    public Trade getExtraTrade() {
        return extraTrade;
    }

    public void setExtraTrade(Trade extraTrade) {
        this.extraTrade = extraTrade;
    }

    @Override
    public String toString() {
        return "Extra Trade " + extraTrade.toString();
    }

}