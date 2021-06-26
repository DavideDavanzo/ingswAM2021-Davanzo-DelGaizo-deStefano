package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.network.client.ClientModel;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.Ack;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.Message;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

public abstract class ClientView extends View{

    protected SocketHandler socketHandler;
    protected ClientModel clientModel;
    protected ClientModel otherPlayerClientModel;
    protected ExecutorService executor;

    public abstract void login();

    /**
     * Sends message
     * @param message
     */
    @Override
    public void sendMessage(Message message){
        socketHandler.sendMessage(message);
    }

    /**
     * Asks the user which action he/she wants to take next
     */
    public abstract void askCommand();

    public abstract void processAck(Ack ack);

    public abstract void onLoginReply(LoginReply message);

    public abstract void showOtherPlayerClientModel();

    public abstract void waitTurn();

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        clientModel.updateWarehouse(warehouse);
    }

    @Override
    public void updateCoffer(Coffer coffer) {
        clientModel.updateCoffer(coffer);
    }

    @Override
    public void updateFaithTrack(Path path) {
        clientModel.updateFaithTrack(path);
    }

    @Override
    public void updateDevCards(DevelopmentCardsArea developmentCardsArea) {
        clientModel.updateDevCardsArea(developmentCardsArea);
    }

    @Override
    public synchronized void updateActiveLeader(int index) {
        clientModel.getLeaderCards().get(index).setActive(true);
    }

    @Override
    public void updateLeaderCards(LinkedList<LeaderCard> leaderCards) {
        clientModel.setLeaderCards(leaderCards);
    }

    @Override
    public void updateMarket(Market market) {
        clientModel.updateMarket(market);
    }

    @Override
    public void updateCardMarket(CardMarket cardMarket) {
        clientModel.updateCardMarket(cardMarket);
    }

    @Override
    public void disconnect() {
        socketHandler.disconnect();
    }

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
