package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.WinMessage;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Ranking scene
 */

public class WinnerSceneController implements GenericSceneController{

    private GuiView gui;

    private WinMessage winMessage;

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

    public WinnerSceneController(WinMessage winMessage) {
        winMessage = this.winMessage;
    }

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

        if(gui.getClientModel().isSinglePlayer()){
            if(winMessage.isLorenzoWins()){
                firstPlaceLabel.setText("The winner is Lorenzo!");
                secondPlaceLabel.setText("In second place: " + gui.getUsername());
            }
            else
            {
                firstPlaceLabel.setText("The winner is: " + gui.getUsername());
                secondPlaceLabel.setText("In second place: Lorenzo");
            }
        }
        else
        {
            firstPlaceLabel.setText("The winner is: " + winMessage.getRanking().keySet().toArray()[0]);
                if(gui.getClientModel().getUsernamesList().size() >= 2){
                    secondPlaceLabel.setText("In second place: " + winMessage.getRanking().keySet().toArray()[1]);
                if(gui.getClientModel().getUsernamesList().size() >= 3){
                    thirdPlaceLabel.setText("In third place: " + winMessage.getRanking().keySet().toArray()[2]);
                if(gui.getClientModel().getUsernamesList().size() == 4){
                    fourthPlaceLabel.setText("In fourth place: " + winMessage.getRanking().keySet().toArray()[3]);
            }
            }
            }
        }
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
