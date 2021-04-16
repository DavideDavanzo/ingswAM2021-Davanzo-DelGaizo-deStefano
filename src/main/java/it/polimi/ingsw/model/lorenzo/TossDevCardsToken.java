package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.SharedArea;

public class TossDevCardsToken extends LorenzoToken {
    public ECardColor color;


    @Override
    public void activate(LorenzoIlMagnifico lorenzo) throws LossException {
        for (int i = 0; i < 2; i++) {
            lorenzo.getCardMarket().destroyCard(color);
        }
    }

}


