package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.requirements.Requirement;

/**
 * <h1>Leader Cards</h1>
 * Leader Cards
 */
public class LeaderCard extends Card {

    private Requirement requirement;
    private Effect effect;
    private boolean active;
    private int victoryPoints;

    public LeaderCard() {
        active = false;
    }

    public LeaderCard(Requirement requirement, Effect effect, int victoryPoints) {
        this.requirement = requirement;
        this.effect = effect;
        this.active = false;
        this.victoryPoints = victoryPoints;
    }

    //TODO: Create a new exception?
    public void activate(Player p) throws NotEnoughResourcesException {

        if(this.isActive()) return;

        if(requirement.validateOn(p)) {
            effect.applyOn(p);
            return;
        }

        throw new NotEnoughResourcesException(p.getNickname() + " doesn't match the requirements..");

    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
