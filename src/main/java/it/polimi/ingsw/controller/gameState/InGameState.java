package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.network.messages.BuyCardCmd;

public class InGameState extends GameState {

    public InGameState(GameController gameController) {
        super(gameController);
    }

    @Override
    public void process(BuyCardCmd buyCardCmd) throws InvalidStateException {
        ECardColor color = buyCardCmd.getColor();
        int level = buyCardCmd.getLevel();
        int slot;
        DevelopmentCard developmentCard;

        try {
            developmentCard = gameController.getMatch().getSharedArea().getCardMarket().getCard(color.toString(), level);
        } catch (IllegalArgumentException e) {
            //TODO: Send no card error;
            return;
        }







    }
}
