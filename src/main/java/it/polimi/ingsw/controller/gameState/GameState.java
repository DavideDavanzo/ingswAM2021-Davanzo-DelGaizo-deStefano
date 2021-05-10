package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.exceptions.InvalidStateException;
import it.polimi.ingsw.network.messages.*;

public abstract class GameState {

    public abstract void process(ReplyMessage message) throws InvalidStateException;

    public abstract void process(ErrorMessage message) throws InvalidStateException;

    public abstract void process(PlayersNumRequest message) throws InvalidStateException;

    public abstract void process(QueryMessage message) throws InvalidStateException;

    public abstract void process(Command command) throws InvalidStateException;

    public abstract void process(LoginReply loginReply) throws InvalidStateException;

    public abstract void process(LoginRequest loginRequest) throws InvalidStateException;

    public abstract void process(InfoMessage infoMessage) throws InvalidStateException;

}
