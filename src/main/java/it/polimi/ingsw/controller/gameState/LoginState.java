package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.exceptions.InvalidStateException;
import it.polimi.ingsw.network.messages.*;

public class LoginState extends GameState {

    @Override
    public void process(ReplyMessage message) {

    }

    @Override
    public void process(ErrorMessage message) {

    }

    @Override
    public void process(PlayersNumRequest message) {

    }

    @Override
    public void process(QueryMessage message) {

    }

    @Override
    public void process(Command command) {

    }

    @Override
    public void process(LoginReply loginReply) {

    }

    @Override
    public void process(LoginRequest loginRequest) {

    }

    @Override
    public void process(InfoMessage infoMessage) throws InvalidStateException {
        throw new InvalidStateException("Cannot do this during login phase!");
    }


}
