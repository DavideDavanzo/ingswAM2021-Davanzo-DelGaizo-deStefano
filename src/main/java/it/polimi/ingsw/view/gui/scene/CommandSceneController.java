package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.PassTurnMessage;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * This class implements the scene that allows the player to choose its next move
 */

public class CommandSceneController implements GenericSceneController {

    private GuiView gui;

    @FXML
    private Button sharedAreaButton;

    @FXML
    private Button yourBoardButton;

    @FXML
    private Button passTurnButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button leaderButton;

    public void initialize() {
        sharedAreaButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::sharedAreaButtonClick);
        yourBoardButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::yourBoardButtonClick);
        passTurnButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::passTurnButtonClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::quitButtonClick);
        leaderButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::leaderButtonClick);
    }

    /**
     * This button leads to the SharedArea scene
     */
    public void sharedAreaButtonClick(Event event) {
        SharedAreaSceneController sAsC = new SharedAreaSceneController();
        sAsC.setGui(gui);
        Platform.runLater(() -> SceneController.changeScene(gui, sAsC, "sharedArea_scene.fxml"));
    }

    /**
     * This button leads to the player's playerboard
     */
    public void yourBoardButtonClick(Event event) {
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerBoardSceneController(gui.getClientModel()), "playerBoard_scene.fxml"));
    }

    public void passTurnButtonClick(Event event) {
        if(gui.getClientModel().isMyTurn()) {
            gui.getClientModel().setMyTurn(false);
            gui.sendMessage(new PassTurnMessage());
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerBoardSceneController(gui.getClientModel()), "playerBoard_scene.fxml"));
        } else gui.showMessage("Not your turn!");
    }

    public void quitButtonClick(Event event) {
        gui.disconnect();
        Platform.runLater(() -> SceneController.changeScene(gui, "menu_scene.fxml", () -> System.exit(0)));
    }

    public void leaderButtonClick(Event event) {
        YourLeaderSceneController yLsC = new YourLeaderSceneController();
        yLsC.setGui(gui);
        Platform.runLater(() -> SceneController.changeScene(gui, yLsC, "yourLeader_scene.fxml"));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
