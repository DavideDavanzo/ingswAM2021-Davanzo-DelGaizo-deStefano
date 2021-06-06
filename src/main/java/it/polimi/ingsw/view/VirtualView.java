package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.server.ServerClientHandler;

import java.io.IOException;
import java.util.ArrayList;

public class VirtualView extends View {

    private final ServerClientHandler clientHandler;
    private String username;
    private boolean connected;

    public VirtualView(ServerClientHandler clientHandler){
        this.clientHandler = clientHandler;
        connected = false;
    }

    @Override
    public void update(Message message) {
        if(message.getUsername() == null)
            message.setUsername(username);
        notifyObservers(message);
    }

    @Override
    public void update(Warehouse warehouse){
        updateWarehouse(warehouse);
    }

    @Override
    public void update(Path path) {
        updateFaithTrack(path);
    }

    @Override
    public void update(Coffer coffer) {
        updateCoffer(coffer);
    }

    @Override
    public void update(DevelopmentCardsArea developmentCardsArea) {
        updateDevCards(developmentCardsArea);
    }

    @Override
    public void start() {
        clientHandler.addObserver(this);
        Thread thread = new Thread(clientHandler);
        thread.start();
    }

    @Override
    public void login() {

    }

    @Override
    public void askNumberOfPlayers() {
        clientHandler.sendMessage(new PlayersNumRequest());
    }

    @Override
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {
        LeaderCardParser parser = new LeaderCardParser();
        clientHandler.sendMessage(new LeaderRequest(parser.serialize(leaderCards)));
    }

    @Override
    public void askBlankResources(String msg) {
        clientHandler.sendMessage(new ResourceRequest(msg));
    }

    @Override
    public void showLogin(String msg, boolean successful) {
        clientHandler.sendMessage(new LoginReply(msg, successful));
    }

    @Override
    public void showError(String msg) {
        clientHandler.sendMessage(new ErrorMessage(msg));
    }

    @Override
    public void showMessage(String msg) {
        clientHandler.sendMessage(new InfoMessage(msg));
    }

    @Override
    public void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves){
        sendMessage(new StockMarketResourcesRequest(resources, numExtraShelves));
    }

    @Override
    public void askToChangeWhiteMarbles(ArrayList<Item> items, int count) {
        clientHandler.sendMessage(new ChangeWhiteMarbleRequest(items, count));
    }

    public void stopTimer(){
        clientHandler.stopTimer();
    }

    @Override
    public void disconnect() {
        try {
            clientHandler.getClientSocket().close();
            connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        sendMessage(new WarehouseUpdate(warehouse));
    }

    @Override
    public void updateCoffer(Coffer coffer) {
        sendMessage(new CofferUpdate(coffer));
    }

    @Override
    public void updateFaithTrack(Path path) {
        sendMessage(new FaithPathUpdate(path));
    }

    @Override
    public void updateDevCards(DevelopmentCardsArea developmentCardsArea) {
        sendMessage(new DevCardsUpdate(developmentCardsArea));
    }

    @Override
    public void showMarket(Market market) {
        sendMessage(new MarketInfoMessage(market));
    }

    @Override
    public void showCardsMarket(CardMarket cardMarket) {
        sendMessage(new CardsMarketInfoMessage(cardMarket));
    }

    @Override
    public void updateActiveLeader(int index) {
        sendMessage(new LeaderCardUpdate(index));
    }

    public ServerClientHandler getClientHandler(){
        return clientHandler;
    }

    public void sendMessage(Message message){
        clientHandler.sendMessage(message);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void connect() {
        connected = true;
    }

    public boolean isConnected() {
        return connected;
    }


}
