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
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.LinkedList;

public class InGameState extends GameState {

    public InGameState(GameController gameController) {
        super(gameController);
    }

    @Override
    public void process(BuyCardCmd buyCardCmd) throws InvalidStateException {

        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(buyCardCmd.getUsername());

        if(bigActionNotAvailable(currentPlayer.hasBigActionToken(), currentView)) return;

        ECardColor color = buyCardCmd.getColor();
        int level = buyCardCmd.getLevel();
        int slot = buyCardCmd.getSlot();

        DevelopmentCard developmentCard;

        try {
            developmentCard = gameController.getMatch().getSharedArea().getCardMarket().getCard(color.toString(), level);
        } catch (IllegalArgumentException e) {
            currentView.showError("No such card. .");
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
                currentView.showError("Not enough resources to buy this card. .");
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

        currentPlayer.revokeBigActionToken();
        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + " bought a level " + level + " " + color + " card", currentPlayer.getNickname());
        currentView.showMessage("You bought the card successfully: can find it in the " + slot + " slot");
    }

    @Override
    public void process(MarketResourcesCmd marketResourcesCmd) throws InvalidStateException {
        char line = marketResourcesCmd.getLine();
        int index = marketResourcesCmd.getIndex();
        ArrayList<Item> resourcesFromMarket;

        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(marketResourcesCmd.getUsername());

        if(bigActionNotAvailable(currentPlayer.hasBigActionToken(), currentView)) return;

        //////////////////////////////////// Input Error /////////////////////////////////////////////////////
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

        /////////////////////////////////// Successful Action  ////////////////////////////////////////////////
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
            currentView.askToStockMarketResources(temporaryItems, currentPlayer.extraShelvesCount());
        }

        ////////////////////////////////// Two Extra Marbles //////////////////////////////////////////////////
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
                currentView.showMessage("Items taken from the Market successfully!");
                currentView.askToStockMarketResources(temporaryItems, currentPlayer.extraShelvesCount());
            }
            else {
                ArrayList<Item> obtainableItems = new ArrayList<>();
                for(Marble m : currentPlayer.getExtraMarbles()) obtainableItems.add(m.returnItem());
                currentView.askToChangeWhiteMarbles(obtainableItems, blankResourcesToSet);
            }
        }

        currentPlayer.revokeBigActionToken();

    }

    @Override
    public void process(ChangeWhiteMarbleReply changeWhiteMarbleReply) throws InvalidStateException {
        Player currentPlayer = gameController.getCurrentPlayer();
        ArrayList<Item> itemsToArrange = currentPlayer.getItemsToArrangeInWarehouse();

        itemsToArrange.addAll(changeWhiteMarbleReply.getChangedItems());

        gameController.getVirtualViewMap().get(changeWhiteMarbleReply.getUsername()).askToStockMarketResources(itemsToArrange, currentPlayer.extraShelvesCount());
    }

    @Override
    public void process(ArrangeInWarehouseCmd arrangeInWarehouseCmd) throws InvalidStateException {
        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(arrangeInWarehouseCmd.getUsername());
        ArrayList<Integer> choices = arrangeInWarehouseCmd.getChoices();
        int counter = 0;

        ArrayList<Shelf> allShelves = currentPlayer.getWarehouse().getAllWarehouseShelves();

        for(Integer i : choices) {
            if(i.equals(0)) {
                currentPlayer.getItemsToArrangeInWarehouse().remove(counter);
                gameController.moveAllExcept(currentPlayer, 1);
                continue;
            }
            if(!(allShelves.get(i-1).isEmpty()) && !(allShelves.get(i-1).getShelfResource().sameType(currentPlayer.getItemsToArrangeInWarehouse().get(counter)))) {
                counter++;
                continue;
            }
            try {
                allShelves.get(i-1).updateShelf((Resource) currentPlayer.getItemsToArrangeInWarehouse().get(counter));
                currentPlayer.getItemsToArrangeInWarehouse().remove(counter);
            } catch (NotEnoughResourcesException | InvalidInputException e) {
                counter++;
            }
        }

        if(currentPlayer.getItemsToArrangeInWarehouse().size() > 0) { //If there are still resources to arrange...
            currentView.showMessage("Some resources couldn't be put in the selected shelves. Only same type in the same shelf!");
            currentView.askToStockMarketResources(currentPlayer.getItemsToArrangeInWarehouse(), currentPlayer.extraShelvesCount());
        }
        currentView.showMessage("Added to your warehouse!");
    }

    @Override
    public void process(ActivateLeaderCmd activateLeaderCmd) throws InvalidStateException {
        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(activateLeaderCmd.getUsername());
        LinkedList<LeaderCard> leaderCards = currentPlayer.getLeaderCards();

        for(Integer i : activateLeaderCmd.getChoices()) {
            if(leaderCards.size() != 0 && leaderCards.size() >= i) {
                if(!leaderCards.get(i).isActive()) {
                    try {
                        currentPlayer.getLeaderCards().get(i-1).activateOn(currentPlayer);
                        currentView.showMessage("Activated leader card number " + i);
                        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + " activated a leader card!", currentPlayer.getNickname());
                    } catch (NotEnoughResourcesException e) {
                        currentView.showError("Couldn't activate the leader card number " + i + " because of the resources missing. .");
                    }
                } else currentView.showError("Couldn't activate the leader card number " + i + " because it's already active");
            } else currentView.showError("Couldn't activate the leader card number " + i + " because it doesn't exist");
        }
    }

    @Override
    public void process(DiscardLeaderCmd discardLeaderCmd) throws InvalidStateException {
        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(discardLeaderCmd.getUsername());
        ArrayList<LeaderCard> leadersToDiscard = new ArrayList<>();

        for(Integer i : discardLeaderCmd.getChoices()) {
            if(currentPlayer.getLeaderCards().size() != 0 && currentPlayer.getLeaderCards().size() >= i) {
                leadersToDiscard.add(currentPlayer.getLeaderCards().get(i-1));
                currentView.showMessage("Discarded leader card number " + i);
                gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + " discarded a leader card!", currentPlayer.getNickname());
            }
            else {
                currentView.showError("Couldn't remove the leader card number " + i);
            }
        }
        currentPlayer.getLeaderCards().removeAll(leadersToDiscard);
        try {
            currentPlayer.moveForward(leadersToDiscard.size());
        } catch (InvalidInputException e) {
            //Should never reach this situation;
            e.printStackTrace();
        }
    }

    @Override
    public void process(ActivateProductionCmd activateProductionCmd) throws InvalidStateException {

    }

    public boolean bigActionNotAvailable(boolean token, VirtualView currentView) {
        if(!token) {
            currentView.showError("Can't perform a big action in this turn anymore. .");
            return true;
        }
        return false;
    }
}












