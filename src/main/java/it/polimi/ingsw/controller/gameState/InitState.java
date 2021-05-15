package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class InitState extends GameState {

    private Match match;
    private GameController gameController;

    public InitState(Match match, GameController gameController) {
        this.match = match;
        this.gameController = gameController;
    }

    @Override
    public void process(ReplyMessage message) throws InvalidStateException {

    }

    @Override
    public void process(ErrorMessage message) throws InvalidStateException {

    }

    @Override
    public void process(PlayersNumber message) throws InvalidStateException {

    }

    @Override
    public void process(QueryMessage message) throws InvalidStateException {

    }

    @Override
    public void process(Command command) throws InvalidStateException {

    }

    @Override
    public void process(InfoMessage infoMessage) throws InvalidStateException {

    }

    @Override
    public void process(LeaderRequest leaderRequest) throws InvalidStateException {

        TurnController turnController = gameController.getTurnController();
        Player current = turnController.getCurrentPlayer();
        VirtualView currentView = gameController.getVirtualViewMap().get(leaderRequest.getUsername());

        String username = leaderRequest.getUsername();
        if(!turnController.isValidPlayer(username)) {
            currentView.showError("Not your turn! Invalid command.");
            return;
        }

        ArrayList<LeaderCard> leaderCards = new LeaderCardParser().deserialize(leaderRequest.getMsg());

        for(LeaderCard l : leaderCards) current.giveLeaderCard(l);

        Integer resourceSupply;
        try {
            resourceSupply = turnController.initResourceSupply();
        } catch (Exception e) {
            turnController.nextTurn();
            return;
        }

        currentView.askBlankResources(resourceSupply.toString());
    }

    @Override
    public void process(ResourceRequest resourceRequest) throws InvalidStateException {

    }

    @Override
    public void process(ResourceChoice resourceChoice) throws InvalidStateException {

    }

}
