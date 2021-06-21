package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Ranking scene
 */

public class WinnerSceneController implements GenericSceneController{

    private GuiView gui;

    @FXML
    private ImageView firstPlace = new ImageView();

    @FXML
    private ImageView secondPlace = new ImageView();

    @FXML
    private ImageView thirdPlace = new ImageView();

    @FXML
    private ImageView fourthPlace = new ImageView();

    @FXML
    private Label firstPlaceLabel = new Label();

    @FXML
    private Label secondPlaceLabel = new Label();

    @FXML
    private Label thirdPlaceLabel = new Label();

    @FXML
    private Label fourthPlaceLabel = new Label();


    public void initialize(){
        setImages();
        setLabels();

    }

    public void setImages(){
        firstPlace.setImage(new Image(getClass().getResourceAsStream("/images/resources/first.png")));
        secondPlace.setImage(new Image(getClass().getResourceAsStream("/images/resources/second.png")));
        thirdPlace.setImage(new Image(getClass().getResourceAsStream("/images/resources/third.png")));
        fourthPlace.setImage(new Image(getClass().getResourceAsStream("/images/resources/fourth.png")));
    }


    public void setLabels(){

    }



    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
