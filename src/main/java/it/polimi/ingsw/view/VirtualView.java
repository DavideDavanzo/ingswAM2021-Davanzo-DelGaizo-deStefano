package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.server.ServerClientHandler;

import java.util.ArrayList;

public class VirtualView extends View {

    private final ServerClientHandler clientHandler;
    private String username;

    public VirtualView(ServerClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message) {
        notifyObservers(message);
    }

    @Override
    public void update(Warehouse warehouse){
        updateWarehouse(warehouse.print());
    }

    @Override
    public void update(Path path) {
        updateFaithTrack(path.print());
    }

    @Override
    public void update(Coffer coffer) {
        updateCoffer(coffer.print());
    }

    @Override
    public void update(DevelopmentCardsArea developmentCardsArea) {
        updateDevCards(developmentCardsArea.print());
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
    public void checkConnection() {

    }

    @Override
    public void updateWarehouse(String warehouse) {
        sendMessage(new WarehouseUpdate(warehouse));
    }

    @Override
    public void updateCoffer(String coffer) {
        sendMessage(new CofferUpdate(coffer));
    }

    @Override
    public void updateFaithTrack(String path) {
        sendMessage(new FaithPathUpdate(path));
    }

    @Override
    public void updateDevCards(String developmentCardsArea) {
        sendMessage(new DevCardsUpdate(developmentCardsArea));
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

}
