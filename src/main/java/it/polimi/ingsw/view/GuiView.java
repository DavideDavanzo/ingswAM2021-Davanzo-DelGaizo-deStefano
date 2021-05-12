package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

public class GuiView extends View{

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
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void onLoginReply(LoginReply message) {

    }

    @Override
    public void askQuery(String msg) {

    }

    @Override
    public void showLogin(String msg, boolean successful) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showMessage(String msg) {

    }

}
