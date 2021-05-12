package it.polimi.ingsw.model.cards;



/**
 * <h1>Card</h1>
 * Abstract concept of Card, tracks a card's Victory Voints.
 * Inherited by {@link LeaderCard} {@link DevelopmentCard}
 */
public abstract class Card  {

    private int victoryPoints;

    /**
     * Default Constructor
     */
    public Card() { }

    /**
     * Main Constructor
     * @param victoryPoints sets the card's Victory Points
     */
    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /**
     *
     * @return Victory Points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

}

