package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayersNumberSceneController implements GenericSceneController{

    private GuiView gui;

    @FXML
    private Button singlePlayerButton;

    @FXML
    private Button multiPlayerButton;

    @FXML
    private ChoiceBox<Integer> numChoice;

    @FXML
    private Label connectingLabel;

    @FXML
    public void initialize() {
        singlePlayerButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::singlePlayerButtonClick);
        multiPlayerButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::multiPlayerButtonClick);
        numChoice.setItems(FXCollections.observableArrayList(2, 3, 4));
    }

    public void singlePlayerButtonClick(Event event) {
        singlePlayerButton.setDisable(true);
        multiPlayerButton.setDisable(true);
        numChoice.setDisable(true);

        gui.getClientModel().setSinglePlayer(true);
        gui.askNumberOfPlayers(1);
    }

    public void multiPlayerButtonClick(Event event) {
        if(numChoice.getValue() == null) return;

        singlePlayerButton.setDisable(true);
        multiPlayerButton.setDisable(true);
        numChoice.setDisable(true);
        connectingLabel.setVisible(true);

        gui.askNumberOfPlayers(numChoice.getValue());
        Platform.runLater(() -> SceneController.changeScene(gui, "lobby_scene.fxml"));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
