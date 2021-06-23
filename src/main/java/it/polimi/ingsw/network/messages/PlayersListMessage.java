package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

import java.util.Set;

public class PlayersListMessage extends Message{

    private Set<String> usernamesList;

    public PlayersListMessage(){}

    public PlayersListMessage(Set<String> usernamesList){
        this.usernamesList = usernamesList;
    }

    @Override
    public void apply(View view) {
        view.getClientModel().setUsernamesList(usernamesList);
        if(usernamesList.size() == 1)
            view.getClientModel().setSinglePlayer(true);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public Set<String> getUsernamesList() {
        return usernamesList;
    }

    public void setUsernamesList(Set<String> usernamesList) {
        this.usernamesList = usernamesList;
    }
}
