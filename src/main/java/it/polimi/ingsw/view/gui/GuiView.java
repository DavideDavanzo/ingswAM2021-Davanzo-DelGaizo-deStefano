package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.LoginSceneController;
import javafx.application.Platform;

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

    }

    @Override
    public void askBlankResources(String msg) {

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
    public void updateWarehouse(String warehouse) {

    }

    @Override
    public void updateCoffer(String coffer) {

    }

    @Override
    public void updateFaithTrack(String path) {

    }

    @Override
    public void updateDevCards(String developmentCardsArea) {

    }


    @Override
    public void processAck(Ack ack) {

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
