package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.observingPattern.Observer;

import java.util.ArrayList;

public abstract class View extends Observable implements Observer {

    public abstract void start();

    public abstract void askNumberOfPlayers();

    public abstract void askLeaders(ArrayList<LeaderCard> leaderCards);

    public abstract void askBlankResources(String msg);

    public abstract void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves);

    public abstract void askToChangeWhiteMarbles(ArrayList<Item> items, int count);

    public abstract void showLogin(String msg, boolean successful);

    public abstract void showError(String msg);

    public abstract void showMessage(String msg);

    public abstract void onLoginReply(LoginReply message);

    public abstract void checkConnection();

    public abstract void chooseInfo();

    public abstract void askCommand();

    public abstract void activateLeaderCards();

    public abstract void updateWarehouse(Warehouse warehouse);

}
