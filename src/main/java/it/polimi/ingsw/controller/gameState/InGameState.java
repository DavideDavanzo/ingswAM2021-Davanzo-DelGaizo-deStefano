package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.ProductionFailException;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalArgumentException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.ExtraDevEffect;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.market.Marble;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.utils.Parser;
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
            currentView.sendMessage(new Ack(false));
            return;
        }

        DevelopmentCardsArea currentPlayerArea = currentPlayer.getPlayerBoard().getDevelopmentCardsArea();

        if(currentPlayerArea.notValid(developmentCard, currentPlayerArea.getArea()[slot-1])) {
            currentView.showError("Cannot place the selected card into the slot you've chosen.\n" +
                    "You can only place cards on empty spaces or on top of lower level cards\n" +
                    "Select a different card or slot . .");
            currentView.sendMessage(new Ack(false));
            return;
        }
        else {
            try {
                currentPlayer.pay(developmentCard);
            } catch (NotEnoughResourcesException e) {
                currentView.showError("Not enough resources to buy this card. .");
                currentView.sendMessage(new Ack(false));
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
        if(currentPlayer.devCardCount() == 7) gameController.setEndGame(true);

        currentPlayer.revokeBigActionToken();
        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + " bought a level " + level + " " + color + " card", currentPlayer.getNickname());
        currentView.showMessage("You bought the card successfully: can find it in the " + slot + " slot");
        currentView.sendMessage(new Ack(true));

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
            currentView.sendMessage(new Ack(false));
            return;
        } catch (IllegalArgumentException e) {
            currentView.showError("Chosen index out of Market. Choose between 1 and 4 . .");
            currentView.sendMessage(new Ack(false));
            return;
        }

        ArrayList<Item> temporaryItems = new ArrayList<>();

        /////////////////////////////////// Successful Action  ////////////////////////////////////////////////
        if(!currentPlayer.hasTwoWhiteMarblePowers()) { //If the player doesn't hold the White Marble Power
            for (Item item : resourcesFromMarket) {
                try {
                    if(currentPlayer.moveForward(item.pathSteps())) gameController.setEndGame(true);
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
                    if(currentPlayer.moveForward(item.pathSteps())) gameController.setEndGame(true);
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

        Warehouse wH = currentPlayer.getWarehouse();
        ArrayList<Shelf> allShelves = currentPlayer.getWarehouse().getAllWarehouseShelves();

        for(Integer i : choices) {
            if(i.equals(0)) {
                currentPlayer.getItemsToArrangeInWarehouse().remove(counter);
                if(gameController.moveAllExcept(currentPlayer, 1)) gameController.setEndGame(true);
                continue;
            }
            try {

                if((i > 3 && currentPlayer.extraShelvesCount() < i - 3)) {
                    counter++;
                    continue;
                }

                if(allShelves.get(i - 1).isEmpty() ||
                    !allShelves.get(i - 1).isEmpty() &&
                    currentPlayer.getItemsToArrangeInWarehouse().get(counter).sameType(allShelves.get(i-1).getShelfResource())) {

                    wH.addResourcesToShelf((Resource) currentPlayer.getItemsToArrangeInWarehouse().get(counter), allShelves.get(i - 1));
                    currentPlayer.getItemsToArrangeInWarehouse().remove(counter);

                } else counter++;

            } catch (NotEnoughResourcesException | InvalidInputException e) {
                counter++;
            }
        }

        if(currentPlayer.getItemsToArrangeInWarehouse().size() > 0) { //If there are still resources to arrange...
            currentView.showError("Some resources couldn't be put in the selected shelves. Only same type in the same shelf and prefixed size 1-2-3. 2 for extra shelves. .");
            currentView.askToStockMarketResources(currentPlayer.getItemsToArrangeInWarehouse(), currentPlayer.extraShelvesCount());
            return;
        }
        currentView.showMessage("Added to your warehouse!");
        currentView.sendMessage(new Ack(true));
    }

    @Override
    public void process(ActivateLeaderCmd activateLeaderCmd) throws InvalidStateException {
        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(activateLeaderCmd.getUsername());
        LinkedList<LeaderCard> leaderCards = currentPlayer.getLeaderCards();

        for(Integer i : activateLeaderCmd.getChoices()) {
            if(leaderCards.size() != 0 && leaderCards.size() >= i) {
                if(!leaderCards.get(i-1).isActive()) {
                    try {
                        currentPlayer.getLeaderCards().get(i-1).activateOn(currentPlayer);
                        currentView.showMessage("Activated leader card number " + i);
                        currentView.updateActiveLeader(i-1);
                        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + " activated a leader card!", currentPlayer.getNickname());
                    } catch (NotEnoughResourcesException e) {
                        currentView.showError("Couldn't activate the leader card number " + i + " because you don't match requirements. .");
                    }
                } else
                    currentView.showError("Couldn't activate the leader card number " + i + " because it's already active");
            } else
                currentView.showError("Couldn't activate the leader card number " + i + " because it doesn't exist");
        }
        currentView.sendMessage(new Ack(true));
    }

    @Override
    public void process(DiscardLeaderCmd discardLeaderCmd) throws InvalidStateException {
        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(discardLeaderCmd.getUsername());
        ArrayList<LeaderCard> leadersToDiscard = new ArrayList<>();

        for(Integer i : discardLeaderCmd.getChoices()) {

            if(currentPlayer.getLeaderCards().size() != 0 && currentPlayer.getLeaderCards().size() >= i) {

                LeaderCard toDiscard = currentPlayer.getLeaderCards().get(i-1);

                if(toDiscard.isActive()) {
                    currentView.showError("Couldn't remove the leader card number " + i + " because it's active.");
                }

                else {
                    leadersToDiscard.add(currentPlayer.getLeaderCards().get(i - 1));
                    currentView.showMessage("Discarded leader card number " + i);
                    gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + " discarded a leader card!", currentPlayer.getNickname());
                }

            }

            else
                currentView.showError("Couldn't remove the leader card number " + i);

        }
        currentPlayer.getLeaderCards().removeAll(leadersToDiscard);
        try {
            if(currentPlayer.moveForward(leadersToDiscard.size())) gameController.setEndGame(true);
        } catch (InvalidInputException e) {
            //Should never reach this situation;
            e.printStackTrace();
        }

        currentView.sendMessage(new Ack(true));
    }

    @Override
    public void process(ActivateProductionCmd activateProductionCmd) throws InvalidStateException {

        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(currentPlayer.getNickname());

        if(bigActionNotAvailable(currentPlayer.hasBigActionToken(), currentView)) return;

        boolean wantsBaseProduction = activateProductionCmd.hasBaseProduction();
        ArrayList<Integer> cardsIndex = activateProductionCmd.getProductionCardsIndex();
        boolean doesntWantCardProduction = cardsIndex.isEmpty();
        Trade baseProduction = new Trade();
        boolean askBlankResource = false;

        ArrayList<Resource> productionInput = new ArrayList<>();
        DevelopmentCardsArea area = gameController.getCurrentPlayer().getPlayerBoard().getDevelopmentCardsArea();

        if(wantsBaseProduction) {
            baseProduction = activateProductionCmd.getBaseProduction();
            for(Resource r : baseProduction.getInput()) productionInput.add(r.clone());
        }

        if(!doesntWantCardProduction) {
            for(Integer i : cardsIndex) {
                if(i > 0 && i < 4) {
                    if(!area.getArea()[i-1].isEmpty()) {
                        for (Resource r : area.getArea()[i-1].peek().getTrade().getInput()) productionInput.add(r.clone());
                    }
                }
                else {
                    LinkedList<LeaderCard> leaderCards = currentPlayer.getLeaderCards();
                    if(!leaderCards.isEmpty() && leaderCards.get(i-4).isActive() && leaderCards.get(i-4).getEffect() instanceof ExtraDevEffect) {
                        for(Resource r : ((ExtraDevEffect) leaderCards.get(i-4).getEffect()).getExtraTrade().getInput()) {
                            productionInput.add(r.clone());
                        }
                    }
                }
            }
        }

        try {
            if(currentPlayer.getPlayerBoard().possiblePayment(productionInput)) {

                if(wantsBaseProduction) {
                    try {
                        if(currentPlayer.getPlayerBoard().activateBaseProduction(baseProduction.getInput(), baseProduction.createOutput().get(0))) gameController.setEndGame(true);
                    } catch (InvalidInputException e) {
                        //Shouldn't reach this statement.
                        e.printStackTrace();
                    }
                }

                if(!doesntWantCardProduction) {
                    ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();
                    LinkedList<LeaderCard> leaderCards = currentPlayer.getLeaderCards();

                    for (Integer i : cardsIndex) {

                        if (i > 0 && i < 4) {
                            if (!area.getArea()[i - 1].isEmpty()) {
                                developmentCards.add(area.getArea()[i-1].peek());
                            }
                        }

                        else if(!leaderCards.isEmpty() && leaderCards.get(i-4).isActive() && leaderCards.get(i-4).getEffect() instanceof ExtraDevEffect) {

                            currentPlayer.getPlayerBoard().payRequiredResources(((ExtraDevEffect) leaderCards.get(i-4).getEffect()).getExtraTrade().getInput());

                            for(Item item : ((ExtraDevEffect) leaderCards.get(i-4).getEffect()).getExtraTrade().getOutput()) {
                                try {
                                    currentPlayer.getPlayerBoard().getCoffer().updateCoffer(item);
                                    currentPlayer.getPlayerBoard().getPath().moveForward(item.pathSteps());
                                } catch (InvalidInputException e) {
                                    //Shouldn't reach this statement.
                                    e.printStackTrace();
                                }
                            }

                            askBlankResource = true;

                        }

                    }

                    if(currentPlayer.getPlayerBoard().activateProduction(developmentCards)) gameController.setEndGame(true);

                    if(askBlankResource){
                        currentView.sendMessage(new ResourceRequest("1"));      //TODO: use int rather than String
                        return;
                    }
                }
            }
        } catch (NotEnoughResourcesException e) {
            currentView.showError("Not enough resources for this type of production. Try again. .");
            currentView.sendMessage(new Ack(false));
        } catch (InvalidInputException | ProductionFailException e) {
            //Shouldn't reach this statement.
            e.printStackTrace();
        }

        currentView.showMessage("Successful production!");
        currentPlayer.revokeBigActionToken();
        currentView.sendMessage(new Ack(true));
    }

    @Override
    public void process(ResourceChoice resourceChoice){
        Player currentPlayer =  gameController.getCurrentPlayer();
        Resource[] resources = (Resource[]) Parser.deserialize(resourceChoice.getMsg(), Resource[].class);
        for(Resource resource : resources) {
            try {
                currentPlayer.getPlayerBoard().getCoffer().updateCoffer(resource);
            } catch (NotEnoughResourcesException | InvalidInputException e) {
                //Shouldn't reach this statement.
                e.printStackTrace();
            }
        }
        currentPlayer.revokeBigActionToken();
        gameController.getVirtualViewMap().get(currentPlayer.getNickname()).showMessage("Successful production!");
        gameController.getVirtualViewMap().get(currentPlayer.getNickname()).sendMessage(new Ack(true));
    }

    @Override
    public void process(SwitchShelvesCmd switchShelvesCmd) throws InvalidStateException {

        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(currentPlayer.getNickname());
        int firstIndex = switchShelvesCmd.getFirst();
        int secondIndex = switchShelvesCmd.getSecond();
        ArrayList<Shelf> shelves = currentPlayer.getWarehouse().getAllWarehouseShelves();

        try {

            if(currentPlayer.extraShelvesCount() < firstIndex - 3 || currentPlayer.extraShelvesCount() < secondIndex - 3) {
                currentView.showError("You selected one or two extra shelves given by leader cards that are not active / you don't have. .");
                currentView.sendMessage(new Ack(false));
                return;
            }

            currentPlayer.getPlayerBoard().getWarehouse().switchShelves(shelves.get(firstIndex-1), shelves.get(secondIndex-1));

        } catch (InvalidInputException e) {
            currentView.showError("These shelves cannot be switched. . try again");
            currentView.sendMessage(new Ack(false));
            return;
        }
        currentView.sendMessage(new Ack(true));
    }

    @Override
    public void process(PassTurnMessage passTurnMessage) throws InvalidStateException {
        Player currentPlayer = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(currentPlayer.getNickname());

        if(!currentPlayer.hasBigActionToken()) {
            currentView.sendMessage(new PassTurnMessage());
            gameController.getTurnController().nextTurn();
        }
        else {
            currentView.showError("You have to perform a main action during your turn.");
            currentView.sendMessage(new Ack(false));
        }

    }

    @Override
    public void process(Disconnection disconnection) {

        gameController.disconnect(disconnection.getUsername());

        long playerNum = gameController.connectedPlayersAsInt();

        if(gameController.isSinglePlayer() || playerNum == 1) {
            gameController.notifyObservers(gameController.getVirtualViewMap().keySet());
            return;
        }

        gameController.sendBroadcastMessageExclude(disconnection.getUsername() + " lost connection...", disconnection.getUsername());
    }

    public boolean bigActionNotAvailable(boolean token, VirtualView currentView) {
        if(!token) {
            currentView.showError("Can't perform a big action in this turn anymore. .");
            currentView.sendMessage(new Ack(false));
            return true;
        }
        return false;
    }
}