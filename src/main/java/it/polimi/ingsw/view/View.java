package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.network.messages.Ack;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.LoginRequest;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.observingPattern.Observer;

import java.util.ArrayList;
import java.util.Set;

public abstract class View extends Observable implements Observer {

    public abstract void start();

    public abstract void login();

    public abstract void askNumberOfPlayers();

    public abstract void askLeaders(ArrayList<LeaderCard> leaderCards);

    public abstract void askBlankResources(String msg);

    public abstract void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves);

    public abstract void askToChangeWhiteMarbles(ArrayList<Item> items, int count);

    public abstract void showLogin(String msg, boolean successful);

    public abstract void showError(String msg);

    public abstract void showMessage(String msg);

    public void onLoginReply(LoginReply message){

    }

    public void onLoginRequest(LoginRequest message){

    }

    public void chooseInfo(){}

    public void askCommand(){}

    public void activateLeaderCards(){}

    public void tossLeaderCards(){}

    public abstract void updateWarehouse(Warehouse warehouse);

    public abstract void updateCoffer(Coffer coffer);

    public abstract void updateFaithTrack(Path path);

    public abstract void updateDevCards(DevelopmentCardsArea developmentCardsArea);

    public void processAck(Ack ack){ }

    public void waitTurn(){ }

    public abstract void showMarket(Market market);

    public abstract void showCardsMarket(CardMarket cardMarket);

    public abstract void disconnect();

    @Override
    public void update(Warehouse warehouse){
        //do nothing
    }

    @Override
    public void update(Path path) {
        //do nothing
    }

    @Override
    public void update(Coffer coffer) {
        //do nothing
    }

    @Override
    public void update(DevelopmentCardsArea developmentCardsArea) {
        //do nothing
    }

    @Override
    public void update(int pathPosition) {
        //do nothing
    }

    @Override
    public void update(Set<String> usernames){
        //do nothing
    }

    public abstract void updateActiveLeader(int index);

}
