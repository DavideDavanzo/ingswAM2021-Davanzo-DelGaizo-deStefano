package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.observingPattern.Observable;
import it.polimi.ingsw.network.observingPattern.Observer;

import java.util.ArrayList;

public abstract class View extends Observable implements Observer {

    public abstract void start();

    public abstract void askQuery(String msg);

    public abstract void askNumberOfPlayers();

    public abstract void askLeaders(ArrayList<LeaderCard> leaderCards);

    public abstract void showLogin(String msg, boolean successful);

    public abstract void showError(String msg);

    public abstract void showMessage(String msg);

    public abstract void onLoginReply(LoginReply message);

}
