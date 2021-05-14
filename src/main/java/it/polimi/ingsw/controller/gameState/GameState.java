package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.network.messages.*;

public abstract class GameState {

    public abstract void process(ReplyMessage message) throws InvalidStateException;

    public abstract void process(ErrorMessage message) throws InvalidStateException;

    public abstract void process(PlayersNumber message) throws InvalidStateException;

    public abstract void process(QueryMessage message) throws InvalidStateException;

    public abstract void process(Command command) throws InvalidStateException;

    public abstract void process(InfoMessage infoMessage) throws InvalidStateException;

    public abstract void process(LeaderRequest leaderRequest) throws InvalidStateException;

    public abstract void process(ResourceRequest resourceRequest) throws InvalidStateException;

    public abstract void process(ResourceChoice resourceChoice) throws InvalidStateException;

}
