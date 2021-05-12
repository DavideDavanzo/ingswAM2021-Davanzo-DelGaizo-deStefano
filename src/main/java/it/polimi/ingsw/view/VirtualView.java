package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.observingPattern.Observer;

public class VirtualView extends View {

    @Override
    public void update(Message message) {

    }

    @Override
    public void start() {

    }

    @Override
    public void askNumberOfPlayers() {

    }

    @Override
    public void askQuery(String msg) {

    }

    @Override
    public void showLogin(String msg, boolean successful) {
        //TODO: clientHandler.sendMessage(new LoginReply(msg, successful));
    }

    @Override
    public void showError(String msg) {
        //TODO: clientHandler.sendMessage(new ErrorMessage(msg));
    }


}
