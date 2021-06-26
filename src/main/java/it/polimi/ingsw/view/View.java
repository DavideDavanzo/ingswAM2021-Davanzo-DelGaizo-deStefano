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
/**
 * Defines a generic View to be implemented by each view type
 */
public abstract class View extends Observable implements Observer {

    protected SocketHandler socketHandler;

    protected String myUsername;

    protected ClientModel clientModel;

    protected ClientModel otherPlayerClientModel;

    public abstract void start();

    public abstract void login();

    /**
     * Sends message
     * @param message
     */
    public void sendMessage(Message message){
        socketHandler.sendMessage(message);
    }

    /**
     * Verifies if the chosen username is valid
     * @param username
     * @return true or false
     */
    public boolean validateUsername(String username){
        if(username.contains(" ") || username.contains("!") || username.contains("?") || username.contains("(") || username.contains(")"))
            return false;
        else if(username.startsWith("0") || username.startsWith("1") || username.startsWith("2") || username.startsWith("3") || username.startsWith("4") || username.startsWith("5") || username.startsWith("6") || username.startsWith("7") || username.startsWith("8") || username.startsWith("9"))
            return false;
        else return true;
    }

    /**
     * Asks how many players the user wants to play with
     */
    public abstract void askNumberOfPlayers();

    /**
     * Asks the user to choose two leader cards among four
     * @param leaderCards
     */
    public abstract void askLeaders(ArrayList<LeaderCard> leaderCards);

    /**
     * Asks the second, third and fourth players to choose resources
     * @param msg
     */
    public abstract void askBlankResources(String msg);

    /**
     * Asks in which shelf the user wants to place resources
     * @param resources
     * @param numExtraShelves
     */
    public abstract void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves);

    /**
     * If White Marble Effect is active
     * Asks the user which resource the user wants in exchange of a white marble
     * @param items
     * @param count
     */
    public abstract void askToChangeWhiteMarbles(ArrayList<Item> items, int count);

    public abstract void showLogin(String msg, boolean successful);

    public abstract void showError(String msg);

    public abstract void showMessage(String msg);

    public void onLoginReply(LoginReply message){

    }

    public void onLoginRequest(LoginRequest message){

    }

    /**
     * The user chooses which info he/she wants to access
     */
    public void chooseInfo(){}

    /**
     * Asks the user which action he/she wants to take next
     */
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

    public abstract void updateLorenzoPosition(int lorenzoPosition);

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

    public String getMyUsername() {
        return myUsername;
    }
}
