package it.polimi.ingsw.model.sharedarea;


import it.polimi.ingsw.model.cards.DevCardParser;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * <h1>Card Market</h1>
 * A Card Market contains various {@link Deck Decks} arranged in this order:
 * Green, Blue, Yellow, Purple. The card's level decreases from the top to the bottom.
 */
public class CardMarket {

    private Deck[][] decks = new Deck[3][4];

    /**
     * Default Constructor
     * Tries to initialize a Card Market, if unsuccessful prints the Stack Trace
     */
    public CardMarket() {
        try {
            init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return True if and only if <b>every</b> deck is empty.
     */
    public boolean isEmpty() {

        for(Deck[] line : decks) {
            for(Deck d : line) {
                if(!d.isEmpty()) return false;
            }
        }

        return true;
    }

    private void init() throws FileNotFoundException {
        DevCardParser parser = new DevCardParser();
        ArrayList<DevelopmentCard> cards = parser.parse();
        arrangeInDecks(cards);
    }

    private void initializeDecks() {
        for(int i = 0; i < 3 ; i++) {
            for(int j = 0; j < 4 ; j++) {
                decks[i][j] = new Deck();
            }
        }
    }

    private void arrangeInDecks(ArrayList<DevelopmentCard> cards) {

        this.initializeDecks();

        for(DevelopmentCard c : cards) {

            ECardColor color = c.getColor();

            switch (color) {
                case GREEN: fillDeck(0, c);
                    break;
                case BLUE: fillDeck(1, c);
                    break;
                case YELLOW: fillDeck(2, c);
                    break;
                case PURPLE: fillDeck(3, c);
                    break;
            }

        }

        for(Deck[] line : decks) {
            for(Deck deck : line) {
                deck.shuffle();
            }
        }

    }

    //TODO: Move in this ^ for() the deck color level setting when .txt filled with cards
    private void fillDeck(int color, DevelopmentCard c) {
        decks[3 - c.getLevel()][color].add(c);
        decks[3 - c.getLevel()][color].setColor(c.getColor());
        decks[3 - c.getLevel()][color].setLevel(c.getLevel());
    }

    public Deck[][] getDecks() {
        return decks;
    }

    /**
     * Prints the observable cards in the Card Market. <b>Observable</b> has to be intended
     * as the card on top of a {@link Deck}. It can also return "Empty Market !"
     * if there's no card left to buy.
     * @return a printed state of the Market
     */
    @Override
    public String toString() {

        if(this.isEmpty()) return "Empty Market !";

        StringBuilder s = new StringBuilder();
        for(Deck[] line : decks) {
            for (Deck deck : line) {
                if (!deck.isEmpty()) s.append(deck.getCards().peek()).append('\n');
            }
        }

        return "Welcome to the Card Market !" + "\n\n" +
                "Available Cards :" + '\n' +
                s.toString();
    }
}
