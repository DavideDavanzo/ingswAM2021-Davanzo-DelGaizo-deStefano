package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In this scene the player decides how many persons he/she/they want/s to play with
 */

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

    /**
     * Solo Match
     * @param event
     */
    public void singlePlayerButtonClick(Event event) {
        singlePlayerButton.setDisable(true);
        multiPlayerButton.setDisable(true);
        numChoice.setDisable(true);

        gui.getClientModel().setSinglePlayer(true);
        gui.askNumberOfPlayers(1);
    }

    /**
     * Multiplayer match
     * @param event
     */
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
