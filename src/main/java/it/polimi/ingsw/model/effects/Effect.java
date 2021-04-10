package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;

/**
 * <\h1>Effect</\h1>
 * An Effect is a special special power that can be activated
 * through a {@link LeaderCard}. Once active, it lasts until the
 * end of the game
 */
public abstract class Effect {

    /**
     * Applies the effect on a Player
     * @param p is the {@link Player}
     */
    public abstract void applyOn(Player p);
}
