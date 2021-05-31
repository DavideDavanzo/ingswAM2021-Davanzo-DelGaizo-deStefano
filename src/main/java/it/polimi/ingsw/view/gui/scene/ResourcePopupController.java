package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class ResourcePopupController implements GenericSceneController{

    private GuiView gui;

    @FXML
    private MenuButton choiceButton;

    @FXML
    private MenuItem Coin = new MenuItem();

    @FXML
    private MenuItem Shield= new MenuItem();

    @FXML
    private MenuItem Servant = new MenuItem();

    @FXML
    private MenuItem Stone = new MenuItem();

    @FXML
    private ImageView coin = new ImageView();

    @FXML
    private ImageView shield = new ImageView();

    @FXML
    private ImageView servant = new ImageView();

    @FXML
    private ImageView stone = new ImageView();

    public void initialize(){
        setResourceImages();
        choiceButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::choiceButtonClick);
    }

    public void setResourceImages() {
        coin.setImage(new Image(getClass().getResourceAsStream("/images/resources/coin.png")));
        shield.setImage(new Image(getClass().getResourceAsStream("/images/resources/shield.png")));
        servant.setImage(new Image(getClass().getResourceAsStream("/images/resources/servant.png")));
        stone.setImage(new Image(getClass().getResourceAsStream("/images/resources/stone.png")));
    }


    private void choiceButtonClick(Event event) {
        gui.askBlankResources(choiceButton.getText());
    }


    @Override
    public void setGui(GuiView gui) {

    }
}
