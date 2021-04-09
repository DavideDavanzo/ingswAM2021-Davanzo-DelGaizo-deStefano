package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;

public class DevelopmentCardsArea {

    private ArrayList<DevelopmentCard> firstDevSlot = new ArrayList<>();
    private ArrayList<DevelopmentCard> secondDevSlot = new ArrayList<>();
    private ArrayList<DevelopmentCard> thirdDevSlot = new ArrayList<>();

    public void addDevCard(DevelopmentCard developmentCard, ArrayList<DevelopmentCard> slot) throws InvalidInputException {

        if(notValid(developmentCard, slot)) {
            throw new InvalidInputException("Development card must be placed over other ones of a lesser level");
        } else {
            slot.add(developmentCard);
        }

    }

    private boolean notValid(DevelopmentCard developmentCard, ArrayList<DevelopmentCard> slot) {

        if(slot.size() == 0)
            return false;

        return slot.get(slot.size()-1).getLevel() >= developmentCard.getLevel();

    }

    public ArrayList<DevelopmentCard> getFirstDevSlot() {
        return firstDevSlot;
    }

    public ArrayList<DevelopmentCard> getSecondDevSlot() {
        return secondDevSlot;
    }

    public ArrayList<DevelopmentCard> getThirdDevSlot() {
        return thirdDevSlot;
    }

}
