package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PlayersNumberSceneController implements GenericSceneController{

    private GuiView gui;

    @FXML
    private Button singlePlayerButton;

    @FXML
    private Button multiPlayerButton;

    @FXML
    public void initialize() {

    }

    public void singlePlayerButtonClick(Event event) {
        singlePlayerButton.setDisable(true);

    }

    public void multiPlayerButtonClick(Event event) {
        singlePlayerButton.setDisable(true);

    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
