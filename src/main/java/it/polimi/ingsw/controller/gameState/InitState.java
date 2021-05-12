package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.network.messages.*;

public class InitState extends GameState{
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
}
