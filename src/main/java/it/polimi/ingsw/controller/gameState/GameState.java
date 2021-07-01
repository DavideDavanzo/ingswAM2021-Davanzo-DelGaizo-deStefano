package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.controller.GameController;

import java.util.NoSuchElementException;

public class GameState {

    protected GameController gameController;

    public GameState(GameController gameController){
        this.gameController = gameController;
    }

    public void process(ErrorMessage message) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(PlayersNumber message) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(InfoMessage infoMessage) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(LeaderRequest leaderRequest) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(ResourceRequest resourceRequest) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(ResourceChoice resourceChoice) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(BuyCardCmd buyCardCmd) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(MarketResourcesCmd marketResourcesCmd) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(ArrangeInWarehouseCmd arrangeInWarehouseCmd) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(ChangeWhiteMarbleReply changeWhiteMarbleReply) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(ActivateLeaderCmd activateLeaderCmd) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(DiscardLeaderCmd discardLeaderCmd) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(ActivateProductionCmd activateProductionCmd) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(PassTurnMessage passTurnMessage) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(SwitchShelvesCmd switchShelvesCmd) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(Disconnection disconnection) {
        gameController.sendBroadcastMessageExclude(disconnection.getUsername() + " lost connection...", disconnection.getUsername());
        gameController.getVirtualViewMap().get(disconnection.getUsername()).disconnect();
    }

    public void process(OtherPlayerInfosRequest otherPlayerInfosRequest){
        try {
            Player otherPlayer = gameController.getPlayers().stream().filter(p -> p.getNickname().equals(otherPlayerInfosRequest.getOtherUsername())).findAny().get();
            gameController.getVirtualViewMap().get(otherPlayerInfosRequest.getUsername()).sendMessage(new OtherPlayerInfosReply(otherPlayer.getWarehouse(), otherPlayer.getPlayerBoard().getCoffer(), otherPlayer.getPlayerBoard().getPath(), otherPlayer.getPlayerBoard().getDevelopmentCardsArea(), otherPlayer.getLeaderCards()));
        } catch (NoSuchElementException e) {
            gameController.getVirtualViewMap().get(otherPlayerInfosRequest.getUsername()).sendMessage(new ErrorMessage("Username not found"));
        }
    }

    public void nextState() {

    }

}
