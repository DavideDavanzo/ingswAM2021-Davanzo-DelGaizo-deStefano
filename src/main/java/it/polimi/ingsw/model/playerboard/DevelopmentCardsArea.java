package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.view.CliPrinter;

import java.util.*;

/**
 * <h1>Development Card Area</h1>
 * This section of the {@link PlayerBoard} contains three stacks of
 * {@link DevelopmentCard} which are previously purchased by the Player
 */
public class DevelopmentCardsArea implements CliPrinter {

    private Stack<DevelopmentCard> firstStack = new Stack<>();
    private Stack<DevelopmentCard> secondStack = new Stack<>();
    private Stack<DevelopmentCard> thirdStack = new Stack<>();

    private Stack<DevelopmentCard>[] area = new Stack[3];

    public DevelopmentCardsArea() {
        area[0] = firstStack;
        area[1] = secondStack;
        area[2] = thirdStack;
    }

    public void addDevCard(DevelopmentCard developmentCard, Stack<DevelopmentCard> slot) throws InvalidInputException {

        if(notValid(developmentCard, slot)) {
            throw new InvalidInputException("Development card must be placed over other ones of a lesser level");
        } else {
            slot.push(developmentCard);
        }

    }

    public boolean hasColorLevel(HashMap<ECardColor, Integer> colorLevel) {

        for(Map.Entry<ECardColor, Integer> entry : colorLevel.entrySet()) {
            ECardColor color = entry.getKey();
            Integer level = entry.getValue();
            if(!colorAndLevelCheck(color, level)) return false;
        }

        return true;

    }

    public boolean hasColors(HashMap<ECardColor, Integer> colors) {

        for(Map.Entry<ECardColor, Integer> entry : colors.entrySet()) {
            ECardColor color = entry.getKey();
            Integer number = entry.getValue();
            if(!colorAndNumberCheck(color, number)) return false;
        }

        return true;

    }

    private boolean colorAndLevelCheck(ECardColor color, Integer level) {

        for(Stack<DevelopmentCard> s : area) {
            for(DevelopmentCard card : s) {
                if(card.getColor().equals(color) && card.getLevel() == level) return true;
            }
        }

        return false;
    }

    private boolean colorAndNumberCheck(ECardColor color, Integer i) {

        int count = i;

        for(Stack<DevelopmentCard> s : area) {
            for(DevelopmentCard card : s) {
                if(card.getColor().equals(color)) count--;
            }
        }

        return count <= 0;

    }

    public boolean notValid(DevelopmentCard developmentCard, Stack<DevelopmentCard> slot) {

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


    public String printFirst(){

        StringBuilder stringBuilder = new StringBuilder();
        for(DevelopmentCard card : firstStack){
            stringBuilder.append(card.print());
        }
        return stringBuilder.toString();
    }



    @Override
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(printFirst()).toString();


    }
}
