package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.client.ClientModel;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.ClientView;
import it.polimi.ingsw.view.gui.scene.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * This class implements all methods of View and it is used for the Graphic User Interface.
 */
public class GuiView extends ClientView {

    private Alert alert;
    MediaPlayer mediaPlayer;
    Thread musicThread;

    public GuiView(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
        clientModel = new ClientModel();
        executor = Executors.newCachedThreadPool();
        alert = new Alert(AlertType.INFORMATION);
        alert.setResizable(true);
    }

    @Override
    public void start() {
        socketHandler.addObserver(this);
        new Thread(socketHandler).start();
        (musicThread = new Thread(this::startMusic)).start();
    }

    @Override
    public synchronized void update(Message message) {
        try {
            wait(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.submit(() -> message.apply(this));
    }

    @Override
    public void login(){
        Platform.runLater(() -> SceneController.changeScene(this, "login_scene.fxml"));
    }

    @Override
    public synchronized void askNumberOfPlayers() {
        Platform.runLater(() -> SceneController.changeScene(this, "playersNumber_scene.fxml"));
    }

    public void askNumberOfPlayers(int num) {
        sendMessage(new PlayersNumber(num));
    }

    public void leaderChoice(ArrayList<LeaderCard> choice) {
        sendMessage(new LeaderRequest(Parser.serialize(choice)));
        clientModel.setLeaderCards(new LinkedList<>(choice));
    }

    public void resourceChoice(ArrayList<Resource> choice) {
        sendMessage(new ResourceChoice(choice));
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
        rpc.setChoiceNumber(Integer.parseInt(msg));
        Platform.runLater(() -> SceneController.changeScene(this, rpc, "resource_popup.fxml"));
    }

    public void onLoginRequest(LoginRequest message) {
        socketHandler.setUsername(message.getMsg());
        sendMessage(message);
    }

    @Override
    public synchronized void onLoginReply(LoginReply message) {
        showMessage(message.getMsg());
        if(!message.isSuccessful()) {
            LoginSceneController loginSceneController = (LoginSceneController) SceneController.getActiveSceneController();
            loginSceneController.reAskLogin();
        }
        else {
            username = socketHandler.getUsername();
            Platform.runLater(() -> SceneController.changeScene(this, "lobby_scene.fxml"));
        }
    }

    @Override
    public void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves) {
        Platform.runLater(() -> SceneController.changeScene(this, new StockResourcesSceneController(resources, numExtraShelves), "askToStockResources_scene.fxml"));
    }

    @Override
    public void askToChangeWhiteMarbles(ArrayList<Item> items, int count) {
        Platform.runLater(() -> SceneController.changeScene(this, "whiteMarbleEffect_scene.fxml"));
    }

    @Override
    public void askCommand() {
        Platform.runLater(() -> SceneController.changeScene(this, "command_scene.fxml"));
    }

    public void activateLeaderCards(int i) {
        sendMessage(new ActivateLeaderCmd(new ArrayList<Integer>(){{add(i);}}));
    }

    public void tossLeaderCards(int i) {
        LeaderCard card = clientModel.getLeaderCards().get(i-1);
        if(!card.isActive()) card.setDiscarded(true);
        if(i == 2) i = getClientModel().getFirstLeader().isDiscarded() ? 1 : i;
        int index = i;
        sendMessage(new DiscardLeaderCmd(new ArrayList<Integer>(){{add(index);}}));
    }

    @Override
    public synchronized void processAck(Ack ack) {
        Platform.runLater(() -> SceneController.changeScene(this, new PlayerBoardSceneController(clientModel), "playerBoard_scene.fxml"));
        if(ack.isAck()) {
            makeSound("/sounds/positive.mp3");
        } else {
            makeSound("/sounds/error.mp3");
        }
    }

    @Override
    public void updateLorenzoPosition(int lorenzoPosition) {
        clientModel.setLorenzoPosition(lorenzoPosition);
    }

    @Override
    public void showError(String msg) {
        Platform.runLater(() -> {
            alert.setHeaderText("Error");
            if(alert.isShowing()) {
                alert.setContentText(alert.getContentText() + "\n" + msg);
                alert.close();
            }
            else    alert.setContentText(msg);
            alert.show();
        });
    }

    @Override
    public void showMessage(String msg) {
        Platform.runLater(() -> {
            alert.setHeaderText("Message");
            if(alert.isShowing()) {
                alert.setContentText(alert.getContentText() + "\n" + msg);
                alert.close();
            }
            else    alert.setContentText(msg);
            alert.show();
            makeSound("/sounds/message_pop_up.mp3");
        });
    }

    @Override
    public void showOtherPlayerClientModel() {
        Platform.runLater(() -> SceneController.changeScene(this, new PlayerBoardSceneController(otherPlayerClientModel), "playerBoard_scene.fxml"));
    }

    @Override
    public void waitTurn() {
        Platform.runLater(() -> SceneController.changeScene(this, new PlayerBoardSceneController(clientModel), "playerBoard_scene.fxml"));
    }

    @Override
    public void endGame(WinMessage winMessage) {
        mediaPlayer.stop();
        Platform.runLater(() -> SceneController.changeScene(this, new WinnerSceneController(winMessage), "winner_scene.fxml"));
        showMessage(winMessage.getMsg());
    }

    private void startMusic(){
        Media media = new Media(Objects.requireNonNull(getClass().getResource("/sounds/soundtrack.mp3")).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
        });
        mediaPlayer.play();
    }

    public synchronized void makeSound(String resourcePath){
        Media media = new Media(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm());
        MediaPlayer player = new MediaPlayer(media);
        player.setOnEndOfMedia(() -> {});
        player.play();
    }

    public synchronized void makeSound(String resourcePath, int seconds){
        Media media = new Media(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm());
        MediaPlayer player = new MediaPlayer(media);
        player.setStopTime(Duration.seconds(seconds));
        player.play();
    }

}
