package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class YourLeaderSceneController implements GenericSceneController {

    private GuiView gui;

    private LeaderCard selected = null;

    @FXML
    private Button backButton;
    @FXML
    public ImageView card1;
    @FXML
    public ImageView card2;
    @FXML
    public Button leader1Button;
    @FXML
    public Button leader2Button;

    public void initialize() {
        initCards();
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);
        leader1Button.addEventHandler(MouseEvent.MOUSE_RELEASED, this::leader1ButtonClick);
        leader2Button.addEventHandler(MouseEvent.MOUSE_RELEASED, this::leader2ButtonClick);
    }

    private void initCards() {
        card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + gui.getClientModel().getLeaderCards().get(0) + ".png"))));
        card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + gui.getClientModel().getLeaderCards().get(1) + ".png"))));
    }

    public void leader1ButtonClick(Event event) {
        if(!leader1Button.isDisabled()) {
            leader1Button.setDisable(true);
            leader2Button.setDisable(true);
            leader1Button.setOpacity(0.6);
            selected = gui.getClientModel().getLeaderCards().get(0);
        }
        else {
            leader1Button.setDisable(false);
            leader2Button.setDisable(false);
            leader1Button.setOpacity(1);
            selected = null;
        }
    }

    public void leader2ButtonClick(Event event) {

    }

    public void backButtonClick(Event event) {
        Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
