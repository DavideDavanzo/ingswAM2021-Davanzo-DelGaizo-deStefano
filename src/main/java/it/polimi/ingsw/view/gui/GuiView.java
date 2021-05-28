package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.Ack;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.LoginRequest;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.View;
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

    }

    @Override
    public void login(){

    }

    @Override
    public void askNumberOfPlayers() {

    }

    @Override
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void askBlankResources(String msg) {

    }

    @Override
    public void onLoginRequest(LoginRequest message) {
        socketHandler.sendMessage(message);
    }

    @Override
    public void onLoginReply(LoginReply message) {
        if(message.isSuccessful()) {
            Platform.runLater(() -> SceneController.changeScene(this, "playersNumber_scene.fxml"));
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
