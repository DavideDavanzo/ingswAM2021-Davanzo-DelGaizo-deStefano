package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;

public class InitState extends GameState {

    public InitState(GameController gameController) {
        super(gameController);
    }

    @Override
    public void process(LeaderRequest leaderRequest) throws InvalidStateException {

        Player current = gameController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(leaderRequest.getUsername());

        String username = leaderRequest.getUsername();
        if(!gameController.isCurrentPlayer(username)) {
            currentView.showError("Not your turn! Invalid command.");
            return;
        }

        ArrayList<LeaderCard> leaderCards = new LeaderCardParser().deserialize(leaderRequest.getMsg());

        for(LeaderCard l : leaderCards) current.giveLeaderCard(l);

        Integer resourceSupply;
        try {
            resourceSupply = gameController.getTurnController().initResourceSupply();
        } catch (Exception e) {
            if(!gameController.isSinglePlayer()) nextTurnInit();
            else {
                gameController.setGameState(new InGameState(gameController));
                gameController.getTurnController().nextTurn();
            }
            return;
        }
        currentView.askBlankResources(resourceSupply.toString());
    }

    @Override
    public void process(ResourceChoice resourceChoice) throws InvalidStateException {
        Resource[] resources = (Resource[]) Parser.deserialize(resourceChoice.getMsg(), Resource[].class);
        try {
            addToShelfInit(new ArrayList<>(Arrays.asList(resources)));
        } catch (InvalidInputException | NotEnoughResourcesException e) {
            e.printStackTrace();
        }
        gameController.getVirtualViewMap().get(resourceChoice.getUsername()).showMessage("Resources added to your warehouse.");

        if(gameController.getTurnController().getTurn() == gameController.getChosenPlayerNum()) {
            nextState();
            gameController.getTurnController().nextTurn();
        }
        else nextTurnInit();
    }

    private void nextTurnInit() {
        gameController.updateQueue();
        gameController.getTurnController().updateTurnCounter();
        String currentPlayer = gameController.getCurrentPlayer().getNickname();
        gameController.sendBroadcastMessageExclude(currentPlayer + "'s turn started . ." , currentPlayer);
        gameController.askLeaders();
    }

    private void addToShelfInit(ArrayList<Resource> resources) throws InvalidInputException, NotEnoughResourcesException {
        Warehouse warehouse = gameController.getCurrentPlayer().getWarehouse();

        if(resources.size() == 1) warehouse.addResourcesToShelf(resources.get(0), warehouse.getFirstShelf());

        else if(resources.get(0).sameType(resources.get(1)))
            for (Resource r : resources) warehouse.addResourcesToShelf(r, warehouse.getSecondShelf());

        else {
            warehouse.addResourcesToShelf(resources.get(0), warehouse.getFirstShelf());
            warehouse.addResourcesToShelf(resources.get(1), warehouse.getSecondShelf());
        }
    }

    @Override
    public void nextState() {
        gameController.setGameState(new InGameState(gameController));
    }
}
