package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.GenericSceneController;
import it.polimi.ingsw.view.gui.scene.LeaderSceneController;
import it.polimi.ingsw.view.gui.scene.LoginSceneController;
import it.polimi.ingsw.view.gui.scene.ResourcePopupController;
import javafx.application.Platform;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuiView extends View {

    private SocketHandler socketHandler;
    ExecutorService executor;

    public GuiView(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void start() {
        socketHandler.addObserver(this);
        new Thread(socketHandler).start();
    }

    @Override
    public void login(){
        Platform.runLater(() -> SceneController.changeScene(this, "login_scene.fxml"));
    }

    @Override
    public void askNumberOfPlayers() {
        Platform.runLater(() -> SceneController.changeScene(this, "playersNumber_scene.fxml"));
    }

    public void askNumberOfPlayers(int num) {
        sendMessage(new PlayersNumber(num));
    }

    @Override
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {
        LeaderSceneController lsc = new LeaderSceneController();
        lsc.setLeaderCards(leaderCards);
        Platform.runLater(() -> SceneController.changeScene(this, lsc, "leader_scene.fxml"));
    }

    @Override
    public void askBlankResources(String msg) {
        ResourcePopupController rpc = new ResourcePopupController();
        rpc.setResourceImages();
        Platform.runLater(() -> SceneController.changeScene(this, rpc, "resource_popup.fxml"));
    }

    @Override
    public void onLoginRequest(LoginRequest message) {
        sendMessage(message);
    }

    @Override
    public void onLoginReply(LoginReply message) {
        if(message.isSuccessful()) {
            askNumberOfPlayers();
        }
        else {
            LoginSceneController loginSceneController = (LoginSceneController) SceneController.getActiveSceneController();
            loginSceneController.reAskLogin();
        }
    }

    @Override
    public void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves) {

    }

    @Override
    public void askToChangeWhiteMarbles(ArrayList<Item> items, int count) {

    }

    @Override
    public void chooseInfo() {

    }

    @Override
    public void askCommand() {

    }

    @Override
    public void activateLeaderCards() {

    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {

    }

    @Override
    public void updateCoffer(Coffer coffer) {

    }

    @Override
    public void updateFaithTrack(Path path) {

    }

    @Override
    public void updateDevCards(DevelopmentCardsArea developmentCardsArea) {

    }

    @Override
    public void processAck(Ack ack) {

    }

    @Override
    public void showMarket(Market market) {

    }

    @Override
    public void showCardsMarket(CardMarket cardMarket) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void updateActiveLeader(int index) {

    }

    @Override
    public void showLogin(String msg, boolean successful) {

    }

    private void sendMessage(Message message){
        socketHandler.sendMessage(message);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public synchronized void update(Message message) {
        executor.submit(() -> message.apply(this));
    }


}
