package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.*;

/**
 * <h1>Development Card Area</h1>
 * This section of the {@link PlayerBoard} contains three stacks of
 * {@link DevelopmentCard} which are previously purchased by the Player
 */
public class DevelopmentCardsArea extends Observable implements CliPrinter {

    private Stack<DevelopmentCard> firstStack = new Stack<>();
    private Stack<DevelopmentCard> secondStack = new Stack<>();
    private Stack<DevelopmentCard> thirdStack = new Stack<>();
    int cardCount;

    private Stack<DevelopmentCard>[] area = new Stack[3];

    public DevelopmentCardsArea() {
        area[0] = firstStack;
        area[1] = secondStack;
        area[2] = thirdStack;
        cardCount = 0;
    }

    /**
     * Tries the placement of a Dev Card into a slot.
     * @param developmentCard
     * @param slot goes from 1 to 3.
     * @throws InvalidInputException if the card is not placed over another one of a lesser level.
     */
    public void addDevCard(DevelopmentCard developmentCard, Stack<DevelopmentCard> slot) throws InvalidInputException {

        if (notValid(developmentCard, slot)) {
            throw new InvalidInputException("Development card must be placed over other ones of a lesser level");
        } else {
            slot.push(developmentCard);
            cardCount++;
            notifyObservers(this);
        }

    }

    /**
     * @param colorLevel is a map containing <ECardColors, Integer> pairs.
     * @return true if {{@link #colorAndLevelCheck(ECardColor, Integer)} is true for
     * every entry of the map.
     */
    public boolean hasColorLevel(HashMap<ECardColor, Integer> colorLevel) {

        for (Map.Entry<ECardColor, Integer> entry : colorLevel.entrySet()) {
            ECardColor color = entry.getKey();
            Integer level = entry.getValue();
            if (!colorAndLevelCheck(color, level)) return false;
        }

        return true;

    }

    /**
     * @param colors is a map containing <ECardColors, Integer> pairs.
     * @return true if {{@link #colorAndNumberCheck(ECardColor, Integer)}} is true for
     * every entry of the map.
     */
    public boolean hasColors(HashMap<ECardColor, Integer> colors) {

        for (Map.Entry<ECardColor, Integer> entry : colors.entrySet()) {
            ECardColor color = entry.getKey();
            Integer number = entry.getValue();
            if (!colorAndNumberCheck(color, number)) return false;
        }

        return true;

    }

    /**
     * @param color
     * @param level
     * @return true if the Area contains a Card of the specified Color and Level, false otherwise.
     */
    private boolean colorAndLevelCheck(ECardColor color, Integer level) {

        for (Stack<DevelopmentCard> s : area) {
            for (DevelopmentCard card : s) {
                if (card.getColor().equals(color) && card.getLevel() == level) return true;
            }
        }

        return false;
    }

    /**
     * @param color
     * @param i
     * @return true if the Area contains as many cards of a specified Color, as the Integer parameter,
     * false otherwise.
     */
    private boolean colorAndNumberCheck(ECardColor color, Integer i) {

        int count = i;

        for (Stack<DevelopmentCard> s : area) {
            for (DevelopmentCard card : s) {
                if (card.getColor().equals(color)) count--;
            }
        }

        return count <= 0;

    }

    /**
     * @param developmentCard
     * @param slot
     * @return true if the development card cannot be placed on the specified slot.
     * level N cards can only be placed over level N-1 cards.
     */
    public boolean notValid(DevelopmentCard developmentCard, Stack<DevelopmentCard> slot) {

        if (slot.empty())
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

    public Stack<DevelopmentCard>[] getArea() {
        return area;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    @Override
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();

        if (firstStack.isEmpty()) {
            stringBuilder.append("first deck: there are no cards in this deck\n");
        }
        else {
            stringBuilder.append("first deck: \n");
            stringBuilder.append("\n");
            for (int i = 0; i < firstStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(firstStack.get(i).getColor()).escape() + "╔═════════" + Color.ANSI_WHITE.escape());
            stringBuilder.append(ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + "╔═════════════════════════════════════╗" + Color.ANSI_WHITE.escape());
            stringBuilder.append("\n");
            for (int i = 0; i < firstStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(firstStack.get(i).getColor()).escape() + "║ lvl " + firstStack.get(i).getLevel() + "   ");
            stringBuilder.append(ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + "║ DEV CARD " + "lvl " + firstStack.peek().getLevel() +  "                      ║" + Color.ANSI_WHITE.escape());
            stringBuilder.append("\n");
            for (int i = 0; i < firstStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(firstStack.get(i).getColor()).escape() + "║         ");
            stringBuilder.append(ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + "║ cost  " + Color.ANSI_WHITE.escape() + firstStack.peek().printCost() + ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + "                  ║");
            stringBuilder.append("\n");
            for (int i = 0; i < firstStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(firstStack.get(i).getColor()).escape() + "║         ");
            stringBuilder.append(ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + "║ trade " + Color.ANSI_WHITE.escape() + firstStack.peek().printTrade() + ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + " ║");

            stringBuilder.append("\n");
            for (int i = 0; i < firstStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(firstStack.get(i).getColor()).escape() + "║ vp: " + firstStack.get(i).getVictoryPoints() + firstStack.get(i).spaceForPoints() + "  ");
            stringBuilder.append(ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + "║ vp : " + firstStack.peek().getVictoryPoints() + firstStack.peek().spaceForPoints() + "                             ║");

            stringBuilder.append("\n");
            for (int i = 0; i < firstStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(firstStack.get(i).getColor()).escape() + "╚═════════" + Color.ANSI_WHITE.escape());
            stringBuilder.append(ECardColor.getColorMap().get(firstStack.peek().getColor()).escape() + "╚═════════════════════════════════════╝\n" + Color.ANSI_WHITE.escape());
        }
        if (secondStack.isEmpty()) {
            stringBuilder.append("second deck: there are no cards in this deck\n");
        }
        else {
            stringBuilder.append("second deck:\n");
            for (int i = 0; i < secondStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(secondStack.get(i).getColor()).escape() + "╔═════════" + Color.ANSI_WHITE.escape());
            stringBuilder.append(ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + "╔═════════════════════════════════════╗" + Color.ANSI_WHITE.escape());
            stringBuilder.append("\n");
            for (int i = 0; i < secondStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(secondStack.get(i).getColor()).escape() + "║ lvl " + secondStack.get(i).getLevel() + "   ");
            stringBuilder.append(ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + "║ DEV CARD " + "lvl " + secondStack.peek().getLevel() + "                      ║");
            stringBuilder.append("\n");
            for (int i = 0; i < secondStack.size() - 1; i++)
            stringBuilder.append(ECardColor.getColorMap().get(secondStack.get(i).getColor()).escape() + "║         ");
            stringBuilder.append(ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + "║ cost  " + Color.ANSI_WHITE.escape() + secondStack.peek().printCost() + ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + "                  ║");
            stringBuilder.append("\n");
            for (int i = 0; i < secondStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(secondStack.get(i).getColor()).escape() + "║         ");
                stringBuilder.append(ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + "║ trade " + Color.ANSI_WHITE.escape() + secondStack.peek().printTrade() + ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + " ║");
            stringBuilder.append("\n");
            for (int i = 0; i < secondStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(secondStack.get(i).getColor()).escape() + "║ vp: " + secondStack.get(i).getVictoryPoints() + secondStack.get(i).spaceForPoints() + "  ");
            stringBuilder.append(ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + "║ vp : " + secondStack.peek().getVictoryPoints() + secondStack.peek().spaceForPoints() + "                             ║");
            stringBuilder.append("\n");
            for (int i = 0; i < secondStack.size() - 1; i++)
                stringBuilder.append(ECardColor.getColorMap().get(secondStack.get(i).getColor()).escape() + "╚═════════" + Color.ANSI_WHITE.escape());
            stringBuilder.append(ECardColor.getColorMap().get(secondStack.peek().getColor()).escape() + "╚═════════════════════════════════════╝\n" + Color.ANSI_WHITE.escape());
        }

            if (thirdStack.isEmpty())
                stringBuilder.append("third deck: there are no cards in this deck\n");
            else {
                stringBuilder.append("third deck:\n");
                    for (int i = 0; i < thirdStack.size() - 1; i++)
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.get(i).getColor()).escape() + "╔═════════" + Color.ANSI_WHITE.escape());
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + "╔═════════════════════════════════════╗" + Color.ANSI_WHITE.escape());
                    stringBuilder.append("\n");
                for (int i = 0; i < thirdStack.size() - 1; i++)
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.get(i).getColor()).escape() + "║ lvl " + thirdStack.get(i).getLevel()+ "   ");
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + "║ DEV CARD " + "lvl " + thirdStack.peek().getLevel() + "                      ║");
                    stringBuilder.append("\n");
                for (int i = 0; i < thirdStack.size() - 1; i++)
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.get(i).getColor()).escape() +"║         ");
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + "║ cost  " + Color.ANSI_WHITE.escape() + thirdStack.peek().printCost() + ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + "                  ║");
                    stringBuilder.append("\n");
                for (int i = 0; i < thirdStack.size() - 1; i++)
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.get(i).getColor()).escape() +"║         ");
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + "║ trade " + Color.ANSI_WHITE.escape() + thirdStack.peek().printTrade() + ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + " ║");
                    stringBuilder.append("\n");
                for (int i = 0; i < thirdStack.size() - 1; i++)
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.get(i).getColor()).escape() + "║ vp: " + thirdStack.get(i).getVictoryPoints() + thirdStack.get(i).spaceForPoints() + "  ");
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + "║ vp: " + thirdStack.peek().getVictoryPoints() + thirdStack.peek().spaceForPoints() + "                              ║");

                    stringBuilder.append("\n");
                for (int i = 0; i < thirdStack.size() - 1; i++)
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.get(i).getColor()).escape() + "╚═════════" + Color.ANSI_WHITE.escape());
                    stringBuilder.append(ECardColor.getColorMap().get(thirdStack.peek().getColor()).escape() + "╚═════════════════════════════════════╝" + Color.ANSI_WHITE.escape());

                }

            return stringBuilder.toString();
        }
    }
