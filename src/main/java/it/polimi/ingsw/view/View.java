package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.network.client.ClientModel;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.Ack;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.LoginRequest;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.observingPattern.Observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public abstract class View extends Observable implements Observer {

    protected SocketHandler socketHandler;

    protected ClientModel clientModel;

    protected ClientModel otherPlayerClientModel;

    public abstract void start();

    public abstract void login();

    public void sendMessage(Message message){
        socketHandler.sendMessage(message);
    }

    public boolean validateUsername(String username){
        if(username.contains(" ") || username.contains("!") || username.contains("?") || username.contains("(") || username.contains(")"))
            return false;
        else if(username.startsWith("0") || username.startsWith("1") || username.startsWith("2") || username.startsWith("3") || username.startsWith("4") || username.startsWith("5") || username.startsWith("6") || username.startsWith("7") || username.startsWith("8") || username.startsWith("9"))
            return false;
        else return true;
    }

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

    public abstract void updateMarket(Market market);

    public abstract void updateCardMarket(CardMarket cardMarket);

    public void processAck(Ack ack){ }

    public void waitTurn(){ }

    public abstract void disconnect();

    public void showOtherPlayerClientModel(){

    }

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

    @Override
    public void update(Market market) {
        //do nothing
    }

    @Override
    public void update(CardMarket cardMarket) {
        //do nothing
    }

    public abstract void updateActiveLeader(int index);

    public abstract void updateLeaderCards(LinkedList<LeaderCard> leaderCards);

    public ClientModel getClientModel() {
        return clientModel;
    }

    public ClientModel getOtherPlayerClientModel(){
        return otherPlayerClientModel;
    }

    public void setOtherPlayerClientModel(ClientModel otherPlayerClientModel) {
        this.otherPlayerClientModel = otherPlayerClientModel;
    }
}
