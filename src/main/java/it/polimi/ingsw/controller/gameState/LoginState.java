package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.network.messages.*;

public class LoginState extends GameState {

    private Match match;
    private GameController gameController;

    public LoginState(Match match, GameController gameController) {
        this.match = match;
        this.gameController = gameController;
    }

    @Override
    public void process(ReplyMessage message) {

    }

    @Override
    public void process(ErrorMessage message) {

    }

    @Override
    public void process(PlayersNumber message) {
        if(!match.setChosenPlayerNumber(message.getPlayerNum()))
            gameController.getVirtualViewMap().get(message.getUsername()).askNumberOfPlayers();
        else
            if(match.isSinglePlayer()) gameController.startMatch(); //Directly starts a singlePlayer match.
    }

    @Override
    public void process(QueryMessage message) {

    }

    @Override
    public void process(Command command) {

    }

    @Override
    public void process(InfoMessage infoMessage) throws InvalidStateException {
        //throw new InvalidStateException("Cannot do this during login phase!");
    }


}
