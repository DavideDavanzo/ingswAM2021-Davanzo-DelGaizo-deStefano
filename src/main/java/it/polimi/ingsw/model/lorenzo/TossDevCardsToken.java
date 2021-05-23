package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.SharedArea;

/**
 * The class represents a specific kind of token
 */

public class TossDevCardsToken extends LorenzoToken {

    public ECardColor color;

    public TossDevCardsToken() {
    }

    public TossDevCardsToken(ECardColor color) {
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

}


