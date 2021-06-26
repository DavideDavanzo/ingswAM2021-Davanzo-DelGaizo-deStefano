package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;

public class YourLeaderSceneController implements GenericSceneController {

    private GuiView gui;

    private Integer selected;

    @FXML
    private Button backButton;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private Button leader1Button;
    @FXML
    private Button leader2Button;
    @FXML
    private Button activateButton;
    @FXML
    private Button discardButton;
    @FXML
    private Label noCardsLabel;

    public void initialize() {

        initCards();
        activateButton.setVisible(false);
        discardButton.setVisible(false);
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);

        if(gui.getClientModel().isMyTurn()) {
            leader1Button.addEventHandler(MouseEvent.MOUSE_RELEASED, this::leader1ButtonClick);
            leader2Button.addEventHandler(MouseEvent.MOUSE_RELEASED, this::leader2ButtonClick);
            activateButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::activateButtonClick);
            discardButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::discardButtonClick);
        }

        activationCheck();

        if(gui.getClientModel().getFirstLeader().isDiscarded() && gui.getClientModel().getSecondLeader().isDiscarded())
            noCardsLabel.setVisible(true);

    }

    private void initCards() {

        if(gui.getClientModel().getFirstLeader().isDiscarded()) {
            leader1Button.setVisible(false);
            card1.setImage(null);
        }
        else card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + gui.getClientModel().getFirstLeader().getId() + ".png"))));

        if(gui.getClientModel().getSecondLeader().isDiscarded()) {
            leader2Button.setVisible(false);
            card2.setImage(null);
        }
        else card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + gui.getClientModel().getSecondLeader().getId() + ".png"))));

    }

    public void leader1ButtonClick(Event event) {
        if(!gui.getClientModel().getSecondLeader().isActive()) {
            if(leader2Button.isDisabled()) {
                leader1Button.setOpacity(0);
                leader2Button.setDisable(false);
                selected = null;
                activateButton.setVisible(false);
                discardButton.setVisible(false);
                return;
            }
        }
        leader1Button.setText("selected");
        leader2Button.setDisable(true);
        leader1Button.setOpacity(0.6);
        selected = 1;
        activateButton.setVisible(true);
        discardButton.setVisible(true);
    }

    public void leader2ButtonClick(Event event) {
        if(!gui.getClientModel().getFirstLeader().isActive()) {
            if(leader1Button.isDisabled()) {
                leader2Button.setOpacity(0);
                leader1Button.setDisable(false);
                selected = null;
                activateButton.setVisible(false);
                discardButton.setVisible(false);
                return;
            }
        }
        leader2Button.setText("selected");
        leader1Button.setDisable(true);
        leader2Button.setOpacity(0.6);
        selected = 2;
        activateButton.setVisible(true);
        discardButton.setVisible(true);
    }

    private void activationCheck() {
        DropShadow ds = new DropShadow(20, Color.AQUA);
        if(gui.getClientModel().getFirstLeader().isActive()) {
            card1.setEffect(ds);
            leader1Button.setDisable(true);
        }
        if(gui.getClientModel().getSecondLeader().isActive()) {
            card2.setEffect(ds);
            leader2Button.setDisable(true);
        }
    }

    public void backButtonClick(Event event) {
        Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
    }

    public void activateButtonClick(Event event) {
        gui.activateLeaderCards(selected);
    }

    public void discardButtonClick(Event event) {
        gui.tossLeaderCards(selected);
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
