package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.PassTurnMessage;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

    public void sharedAreaButtonClick(Event event) {
        SharedAreaSceneController sAsC = new SharedAreaSceneController();
        sAsC.setGui(gui);
        Platform.runLater(() -> SceneController.changeScene(gui, sAsC, "sharedArea_scene.fxml"));
    }

    public void yourBoardButtonClick(Event event) {
        Platform.runLater(() -> SceneController.changeScene(gui, "playerBoard_scene.fxml"));
    }

    public void passTurnButtonClick(Event event) {
        gui.sendMessage(new PassTurnMessage());
        Platform.runLater(() -> SceneController.changeScene(gui, "playerBoard_scene.fxml"));
    }

    public void quitButtonClick(Event event) {
        gui.disconnect();
        Platform.runLater(() -> SceneController.changeScene(gui, "menu_scene.fxml", () -> System.exit(0)));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
