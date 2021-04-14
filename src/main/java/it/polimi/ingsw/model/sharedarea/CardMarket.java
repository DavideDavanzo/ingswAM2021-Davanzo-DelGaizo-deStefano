package it.polimi.ingsw.model.sharedarea;


import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.cards.DevCardParser;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;


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

    //TODO: Simplify when number - color association is created
    /**
     * Returns true if the Card Market contains on top of a deck the specific card.
     * @param card if null, it returns false.
     */
    public boolean contains(DevelopmentCard card) {
        for(Deck d : decks[3 - card.getLevel()]) {
            if(d.isEmpty()) continue;
            if(d.getCards().peek().equals(card)) return true;
        }
        return false;
    }

    //TODO: Simplify when number - color association is created
    /**
     * The method destroys a card of a specific color starting from the lower level to the highest.
     * @param color is a card color.
     * @throws LossException when every card of a color is destroyed by {@link LorenzoIlMagnifico}
     * in single player mode, causing the instant loss.
     */
    public void destroyCard(ECardColor color) throws LossException {
        switch (color) {
            case GREEN: destroyLine(0);
                break;
            case BLUE: destroyLine(1);
                break;
            case YELLOW: destroyLine(2);
                break;
            case PURPLE: destroyLine(3);
                break;
        }
    }

    //TODO: Simplify when number - color association is created
    /**
     * Takes a specific Development Card from the Card Market given the color as a String and the level.
     * @param color is the color of the card as a String.
     * @param level is the level of the card.
     * @return a development card.
     * @throws IllegalArgumentException if the specific card doesn't exist in the market.
     */
    public DevelopmentCard takeCard(String color, int level) throws IllegalArgumentException {
        for(Deck[] line : decks) {
            for(Deck d : line) {
                if(d.isEmpty()) continue;
                if(d.getColor().equals(ECardColor.valueOf(color)) && d.getLevel() == level) return d.getCards().pop();
            }
        }
        throw new IllegalArgumentException("There's no such card... try again.");
    }

    //TODO: Simplify when number - color association is created
    /**
     * Takes a specific Card given a row and a column.
     * @param row goes from 1 up to 3.
     * @param column goes from 1 up to 4.
     * @return a development card.
     * @throws IllegalArgumentException if the specific card doesn't exist in the market.
     */
    public DevelopmentCard takeCard(int row, int column) throws IllegalArgumentException {
         if(decks[row - 1][column - 1].isEmpty()) throw new IllegalArgumentException("There's no such card... try again.");
         return decks[row - 1][column - 1].getCards().pop();
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

    private void destroyLine(int line) throws LossException {

        for(int i = 2; i >= 0 ; i--) {
            if(decks[i][line].isEmpty()) continue;
            decks[i][line].getCards().pop();
            return;
        }

        throw new LossException("You Lost!");
    }

    /**
     * Decks Getter
     * @return the decks in the market, even if empty.
     */
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
