package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuSceneController implements GenericSceneController {

    private GuiView gui;

    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;

    @FXML
    public void initialize() {
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::playButtonClick);
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::quitButtonClick);
    }

    public void playButtonClick(Event event) {
        playButton.setDisable(true);
        SceneController.changeScene(gui, "login_scene.fxml");
    }

    public void quitButtonClick(Event event) {
        System.exit(0);
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
