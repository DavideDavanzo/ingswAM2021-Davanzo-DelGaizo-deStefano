package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.controller.GameController;

public class GameState {

    protected GameController gameController;

    public GameState(GameController gameController){
        this.gameController = gameController;
    }

    public void process(ReplyMessage message) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(ErrorMessage message) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(PlayersNumber message) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(QueryMessage message) throws InvalidStateException {
        throw new InvalidStateException("This action cannot be performed during this phase of the game");
    }

    public void process(Command command) throws InvalidStateException {
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

    public void process(PingMessage pingMessage){
        gameController.notify();
    }

    public void nextState() {

    }

}
