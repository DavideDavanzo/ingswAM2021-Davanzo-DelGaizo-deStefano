package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;

/**
 * A blank requirement doesn't require anything for it to be satisfied.
 * {@link LeaderCard Leaders cards} with this requirement can always be activated.
 */
public class BlankRequirement extends Requirement {

    /**
     * Validates the requirement on a player.
     * @param p is the Player on which the control is done.
     * @return true if the player matches the requirement.
     */
    @Override
    public boolean validateOn(Player p) {
        return true;
    }

    @Override
    public Object returnAttribute() {
        return null;
    }



    @Override
    public String toString() {
        return "No Requirement";
    }

    @Override
    public String print() {
        return null;
    }
}
