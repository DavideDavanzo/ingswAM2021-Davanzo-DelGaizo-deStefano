package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.view.cli.CliPrinter;

/**
 * <h1>Card</h1>
 * Abstract concept of Card, tracks a card's Victory Voints.
 * Inherited by {@link LeaderCard} {@link DevelopmentCard}
 */
public abstract class Card implements CliPrinter {

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

