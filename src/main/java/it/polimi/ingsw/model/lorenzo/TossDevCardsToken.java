package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.SharedArea;

/**
 * The class represents a kind of token: if activated
 * it removes two cards of a specific color from the {@link it.polimi.ingsw.model.sharedarea.CardMarket}.
 */

public class TossDevCardsToken extends LorenzoToken {

    public ECardColor color;

    public TossDevCardsToken() {
        super();
    }

    public TossDevCardsToken(ECardColor color) {
        super();
        this.color = color;
    }

    /**
     * This method removes two cards of color ECardColor color from the development card area
     * @param lorenzo represents Lorenzo il Magnifico
     * @throws LossException if an entire column of cards is removed
     */
    @Override
    public void activate(LorenzoIlMagnifico lorenzo) throws LossException {
        for (int i = 0; i < 2; i++) {
            lorenzo.getCardMarket().destroyCard(color);
        }
    }

    @Override
    public String toString() {
        return "Toss Dev Card Token: tosses 2 of the lowest level " + color.toString() + " cards.";
    }
}


