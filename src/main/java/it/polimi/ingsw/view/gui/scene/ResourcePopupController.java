package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class implements the scene in which the player chooses one or more resources then added to its warehouse
 */

public class ResourcePopupController implements GenericSceneController {

    private GuiView gui;

    private int choiceNumber;

    private ArrayList<Resource> chosenResources;

    @FXML
    private ImageView coin = new ImageView();

    @FXML
    private ImageView shield = new ImageView();

    @FXML
    private ImageView servant = new ImageView();

    @FXML
    private ImageView stone = new ImageView();

    @FXML
    private Label label;

    public void initialize(){
        chosenResources = new ArrayList<>();
        setResourceImages();
        coin.setPickOnBounds(true);
        servant.setPickOnBounds(true);
        shield.setPickOnBounds(true);
        stone.setPickOnBounds(true);
        handleClick();
        label.setText("Choose " + choiceNumber + " resource(s) to take among these:");
    }

    public void setResourceImages() {
        coin.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/coin.png"))));
        shield.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/shield.png"))));
        servant.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/servant.png"))));
        stone.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/stone.png"))));
    }

    private void handleClick() {

        coin.setCursor(Cursor.HAND);
        coin.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chosenResources.add(new Coin(1));
                numberCheck();
                mouseEvent.consume();

            }
        });

        shield.setCursor(Cursor.HAND);
        shield.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chosenResources.add(new Shield(1));
                numberCheck();
                mouseEvent.consume();
            }
        });

        servant.setCursor(Cursor.HAND);
        servant.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chosenResources.add(new Servant(1));
                numberCheck();
                mouseEvent.consume();
            }
        });

        stone.setCursor(Cursor.HAND);
        stone.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chosenResources.add(new Stone(1));
                numberCheck();
                mouseEvent.consume();

            }
        });
    }

    private void disableAll() {
        coin.setPickOnBounds(false);
        servant.setPickOnBounds(false);
        shield.setPickOnBounds(false);
        stone.setPickOnBounds(false);
    }

    private void numberCheck() {
        if(choiceNumber == 1) {
            disableAll();
            gui.resourceChoice(chosenResources);
            Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
        } else choiceNumber--;
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
    }
}
