package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.parser.Parser;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class InitState extends GameState {

    public InitState(GameController gameController) {
        super(gameController);
    }

    @Override
    public void process(LeaderRequest leaderRequest) throws InvalidStateException {

        TurnController turnController = gameController.getTurnController();
        Player current = turnController.getCurrentPlayer();
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
            resourceSupply = turnController.initResourceSupply();
        } catch (Exception e) {
            System.out.println(gameController.getTurnController().getTurn());
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
        //TODO:Add resource to shelf.
    }

    private void nextTurnInit() {
        gameController.updateQueue();
        gameController.getTurnController().updateTurnCounter();
        String currentPlayer = gameController.getCurrentPlayer().getNickname();
        gameController.sendBroadcastMessageExclude(currentPlayer + "'s turn started . ." , currentPlayer);
        gameController.askLeaders();
    }

}
