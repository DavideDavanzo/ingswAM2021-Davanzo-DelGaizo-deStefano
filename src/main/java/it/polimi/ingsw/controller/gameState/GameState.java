package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.view.VirtualView;

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

    public void process(TimeoutMessage timeoutMessage){
        System.out.println("Got timeout from " + timeoutMessage.getMsg() + " handler");
    }

    public void process(MarketInfoRequest marketInfoRequest) {
        VirtualView virtualView = gameController.getVirtualViewMap().get(marketInfoRequest.getUsername());
        virtualView.sendMessage(new InfoMessage(gameController.getMatch().getSharedArea().getMarket().print()));
    }

    public void process(CardsMarketInfoRequest cardsMarketInfoRequest) {
        VirtualView virtualView = gameController.getVirtualViewMap().get(cardsMarketInfoRequest.getUsername());
        virtualView.sendMessage(new InfoMessage(gameController.getMatch().getSharedArea().getCardMarket().print()));
    }

    public void process(Disconnection disconnection){
        gameController.sendBroadcastMessageExclude(disconnection.getUsername() + " lost connection...", disconnection.getUsername());
        gameController.getVirtualViewMap().get(disconnection.getUsername()).disconnect();
    }

    public void nextState() {

    }

}
