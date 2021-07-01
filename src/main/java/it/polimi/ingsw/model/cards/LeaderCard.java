package it.polimi.ingsw.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.requirements.Requirement;

/**
 * <h1>Leader Cards</h1>
 * Leader Cards contains a unique {@link Effect} and an activation {@link Requirement}.
 * It can be activated or tossed.
 */
public class LeaderCard extends Card {

    private Requirement requirement;
    private Effect effect;
    private boolean active;
    private boolean discarded;

    /**
     * Default Constructor
     */
    public LeaderCard() { }

    /**
     * Points Constructor
     * @param victoryPoints refers to the card's Victory Points.
     */
    public LeaderCard(int victoryPoints) {
        super(victoryPoints);
        active = false;
        discarded = false;
    }

    /**
     * Complete Constructor - no id.
     * @param victoryPoints refers to the card's Victory Points.
     * @param requirement is the activation requirement of the card.
     * @param effect is the effect of the activated card.
     */
    public LeaderCard(Requirement requirement, Effect effect, int victoryPoints) {
        super(victoryPoints);
        this.requirement = requirement;
        this.effect = effect;
        this.active = false;
        this.discarded = false;
    }

    /**
     * Complete Constructor
     * @param victoryPoints refers to the card's Victory Points.
     * @param requirement is the activation requirement of the card.
     * @param effect is the effect of the activated card.
     * @param id is the unique id of the card.
     */
    public LeaderCard(Requirement requirement, Effect effect, int victoryPoints, int id) {
        super(victoryPoints, id);
        this.requirement = requirement;
        this.effect = effect;
        this.active = false;
        this.discarded = false;
    }

    /**
     * Activates the card on the specified player.
     * @param p is the player.
     * @throws NotEnoughResourcesException
     */
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

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public boolean isDiscarded() {
        return discarded;
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

    public String spaceForPoints(){
        StringBuilder stringBuilder = new StringBuilder();
        if(getVictoryPoints() < 10){
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public String printActive(){

        StringBuilder stringBuilder = new StringBuilder();
        if(isActive()){
            stringBuilder.append("active    ");

        }
        else
            stringBuilder.append("not active");
        return stringBuilder.toString();
    }

    @Override
    public String print() {
       StringBuilder stringBuilder = new StringBuilder();

       stringBuilder.append("╔══════════════════════════════════════════════╗\n")
                    .append("║                  LEADER CARD                 ║\n")
                    .append("║ req: "   + requirement.print() + "    ║\n")
                    .append("║ vp: " + getVictoryPoints() +  spaceForPoints() + "                                       ║\n")
                    .append("║ effect: " + effect.print() + "                             ║\n")
                    .append("║ status: " + printActive() + "                           ║\n")
                    .append("╚══════════════════════════════════════════════╝" );

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
