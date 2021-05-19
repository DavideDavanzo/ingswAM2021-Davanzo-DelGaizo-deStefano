package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalArgumentException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.messages.BuyCardCmd;
import it.polimi.ingsw.network.messages.MarketResourcesCmd;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class InGameState extends GameState {

    public InGameState(GameController gameController) {
        super(gameController);
    }

    @Override
    public void process(BuyCardCmd buyCardCmd) throws InvalidStateException {

        ECardColor color = buyCardCmd.getColor();
        int level = buyCardCmd.getLevel();
        int slot = 10000; //Change

        DevelopmentCard developmentCard;

        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(buyCardCmd.getUsername());

        try {
            developmentCard = gameController.getMatch().getSharedArea().getCardMarket().getCard(color.toString(), level);
        } catch (IllegalArgumentException e) {
            //TODO: Send no card error;
            return;
        }

        DevelopmentCardsArea currentPlayerArea = currentPlayer.getPlayerBoard().getDevelopmentCardsArea();

        if(currentPlayerArea.notValid(developmentCard, currentPlayerArea.getArea()[slot-1])) {
            currentView.showError("Cannot place the selected card into the slot you've chosen.\n" +
                    "You can only place cards on empty spaces or on top of lower level cards\n" +
                    "Select a different card or slot . .");
            return;
        }
        else {
            try {
                currentPlayer.pay(developmentCard);
            } catch (NotEnoughResourcesException e) {
                //TODO: Send not enough resources error;
                return;
            } catch (InvalidInputException e) {
                //Shouldn't reach this catch.
                e.printStackTrace();
            }
        }

        try {
            currentPlayer.handleNewDevCard(gameController.getMatch().getSharedArea().getCardMarket().takeCard(color.toString(), level),
                                                                                                 currentPlayerArea.getArea()[slot-1]);
        } catch (IllegalArgumentException | InvalidInputException e) {
            //Shouldn't reach this catch.
            e.printStackTrace();
        }

        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + " bought a level " + level + " " + color + " card", currentPlayer.getNickname());
        currentView.showMessage("You bought the card successfully: can find it in the " + slot + " slot");
    }

    @Override
    public void process(MarketResourcesCmd marketResourcesCmd) throws InvalidStateException {
        char line = marketResourcesCmd.getLine();
        int index = marketResourcesCmd.getIndex();
        ArrayList<Item> resourcesFromMarket = new ArrayList<>();

        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(marketResourcesCmd.getUsername());

        try {
            resourcesFromMarket = gameController.getMatch().getSharedArea().getMarket().getResources(line, index);
        } catch (IllegalChoiceException e) {
            currentView.showError("You must choose r: row or c: column . .");
            return;
        } catch (IllegalArgumentException e) {
            currentView.showError("Chosen index out of Market. Choose between 1 and 4 . .");
            return;
        }

        ArrayList<Item> temporaryItems = new ArrayList<>();

        if(!currentPlayer.hasTwoWhiteMarblePowers()) { //If the player doesn't hold the White Marble Power
            for (Item item : resourcesFromMarket) {
                try {
                    currentPlayer.moveForward(item.pathSteps());
                } catch (InvalidInputException e) {
                    //Shouldn't reach this catch.
                    e.printStackTrace();
                }

                if(!(item.sameType(new Resource()) || item.sameType(new FaithPoint())))
                    temporaryItems.add(item.clone());

                if(item.sameType(new Resource()) && currentPlayer.hasWhiteMarblePower())
                    temporaryItems.add(currentPlayer.getExtraMarbles().get(0).returnItem());

            }
            currentPlayer.setItemsToArrangeInWarehouse(temporaryItems);
            currentView.showMessage("Items taken from the Market successfully!");
            //TODO: currentView.askToArrangeItemsInWarehouse();
        }

        else {
            int blankResourcesToSet = 0;
            for (Item item : resourcesFromMarket) {
                try {
                    currentPlayer.moveForward(item.pathSteps());
                } catch (InvalidInputException e) {
                    //Shouldn't reach this catch.
                    e.printStackTrace();
                }

                if(!(item.sameType(new Resource()) || item.sameType(new FaithPoint())))
                    temporaryItems.add(item.clone());

                if(item.sameType(new Resource()))
                    blankResourcesToSet++;

            }
            currentPlayer.setItemsToArrangeInWarehouse(temporaryItems);

            if(blankResourcesToSet == 0) {
                //TODO: currentView.askToArrangeItemsInWarehouse();
            }
            else {
                //TODO: currentView.askToSetBlankResources();
            }
        }

    }

    


}
