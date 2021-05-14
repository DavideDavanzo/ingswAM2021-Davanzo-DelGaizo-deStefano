package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.requirements.Requirement;
import it.polimi.ingsw.view.CliBuilder;

/**
 * <h1>Leader Cards</h1>
 * Leader Cards
 */
public class LeaderCard extends Card {

    private Requirement requirement;
    private Effect effect;
    private boolean active;

    public LeaderCard() { }

    public LeaderCard(int victoryPoints) {
        super(victoryPoints);
        active = false;
    }

    public LeaderCard(Requirement requirement, Effect effect, int victoryPoints) {
        super(victoryPoints);
        this.requirement = requirement;
        this.effect = effect;
        this.active = false;
    }

    //TODO: Create a new exception?
    public void activateOn(Player p) throws NotEnoughResourcesException {

        if(this.isActive()) return;

        if(requirement.validateOn(p)) {
            active = true;
            effect.applyOn(p);
            return;
        }

        throw new NotEnoughResourcesException(p.getNickname() + " doesn't match the requirements..");

    }

    public Requirement getRequirement() {
        return requirement;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    /**
     *
     * @return 0 Victory Points if the card is inactive. If activated it returns the value written on the card.
     */
    public int calculateVictoryPoints() {
        return isActive() ? getVictoryPoints() : 0;
    }

    /**
     * Reads the Victory Points omn top of the card.
     */
    @Override
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }

    @Override
    public String print() {
       StringBuilder stringBuilder = new StringBuilder();

       stringBuilder.append("╔═════════════════╗\n")
                    .append("║   LEADER CARD   ║\n")
                    .append("║ req: "   + requirement.print() + "          ║\n")
                    .append("║ vp: " + getVictoryPoints() +    "           ║\n")
                    .append("║ effect: " + effect.print() +  "   ║\n")
                    .append("╚═════════════════╝" );

       return stringBuilder.toString();
    }



    @Override
    public String toString() {
        String s = active ? "Active" : "Inactive";
        return "LeaderCard [" +
                " Requirement: " + requirement +
                " | Effect: " + effect +
                " | Status: " + s +
                " | Victory Points: " + getVictoryPoints() +
                " ]";
    }
}
