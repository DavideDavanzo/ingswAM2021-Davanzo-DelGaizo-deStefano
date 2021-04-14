package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <h1>Development Card Area</h1>
 * This section of the {@link PlayerBoard} contains three stacks of
 * {@link DevelopmentCard} which are previously purchased by the Player
 */
public class DevelopmentCardsArea {

    private Stack<DevelopmentCard> firstStack = new Stack<>();
    private Stack<DevelopmentCard> secondStack = new Stack<>();
    private Stack<DevelopmentCard> thirdStack = new Stack<>();

    public void addDevCard(DevelopmentCard developmentCard, Stack<DevelopmentCard> slot) throws InvalidInputException {

        if(notValid(developmentCard, slot)) {
            throw new InvalidInputException("Development card must be placed over other ones of a lesser level");
        } else {
            slot.push(developmentCard);
        }

    }

    private boolean notValid(DevelopmentCard developmentCard, Stack<DevelopmentCard> slot) {

        if(slot.empty())
            return developmentCard.getLevel() != 1;

        return slot.peek().getLevel() != developmentCard.getLevel() - 1;

    }

    public Stack<DevelopmentCard> getFirstStack() {
        return firstStack;
    }

    public Stack<DevelopmentCard> getSecondStack() {
        return secondStack;
    }

    public Stack<DevelopmentCard> getThirdStack() {
        return thirdStack;
    }

}
