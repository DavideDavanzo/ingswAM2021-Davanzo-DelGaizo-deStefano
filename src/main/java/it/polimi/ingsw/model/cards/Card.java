package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.view.cli.CliPrinter;

/**
 * <h1>Card</h1>
 * Abstract concept of Card, tracks a card's Victory Points.
 * Inherited by {@link LeaderCard} {@link DevelopmentCard}
 */
public abstract class Card implements CliPrinter {

    private final int id;
    private int victoryPoints;

    /**
     * Default Constructor
     */
    public Card() {
        id = 0;
    }

    /**
     * Points-Only Constructor
     * @param victoryPoints the card's Victory Points
     */
    public Card(int victoryPoints) {
        id = 0;
        this.victoryPoints = victoryPoints;

    }

    /**
     * Main Constructor
     * @param victoryPoints card's Victory Points
     * @param id is a unique id that links the card to an image
     */
    public Card(int victoryPoints, int id) {
        this.victoryPoints = victoryPoints;
        this.id = id;

    }

    /**
     *
     * @return Victory Points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getId() {
        return id;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}

