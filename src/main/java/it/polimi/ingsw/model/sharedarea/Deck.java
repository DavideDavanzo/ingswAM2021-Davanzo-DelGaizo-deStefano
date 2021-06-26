package it.polimi.ingsw.model.sharedarea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;

import java.util.Random;
import java.util.Stack;

/**
 * This class implements a deck of cards
 */
public class Deck {

    private Stack<DevelopmentCard> cards;
    private ECardColor color;
    private int level;

    public Deck() { this.cards = new Stack<>(); }

    public Deck(ECardColor color, int level) {
        this.cards = new Stack<>();
        this.color = color;
        this.level = level;
    }

    public void shuffle() {

        Random random = new Random();

        for(int i=0; i < cards.size(); i++) {
            int index = i + random.nextInt(cards.size() - i);

            DevelopmentCard temp = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, temp);
        }
    }

    public void add(DevelopmentCard card) {
        cards.push(card);
    }

    public void add(Stack<DevelopmentCard> stack) {
        for(DevelopmentCard d : stack) cards.push(d);
    }

    public DevelopmentCard takeCard() {
        return this.cards.pop();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Stack<DevelopmentCard> getCards() {
        return cards;
    }

    public void setCards(Stack<DevelopmentCard> cards) {
        this.cards = cards;
    }

    public ECardColor getColor() {
        return color;
    }

    public void setColor(ECardColor color) {
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {

        if(cards.isEmpty()) return "Empty" + '\n';

        StringBuilder s = new StringBuilder();
        for(int i = cards.size()-1; i >= 0; i--) {
            s.append(cards.get(i).toString()).append('\n');
        }

        return s.toString();
    }
}
