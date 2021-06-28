package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.WinMessage;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Ranking scene
 */

public class WinnerSceneController implements GenericSceneController{

    private GuiView gui;

    private WinMessage winMessage;

    @FXML
    private ImageView firstPlace;

    @FXML
    private ImageView secondPlace;

    @FXML
    private ImageView thirdPlace;

    @FXML
    private ImageView fourthPlace;

    @FXML
    private Label firstPlaceLabel;

    @FXML
    private Label secondPlaceLabel;

    @FXML
    private Label thirdPlaceLabel;

    @FXML
    private Label fourthPlaceLabel;

    public WinnerSceneController(WinMessage winMessage) {
        this.winMessage = winMessage;
    }

    @FXML
    public void initialize(){
        setLabels();
    }

    public void setLabels(){

        if(gui.getClientModel().isSinglePlayer()){
            if(winMessage.isLorenzoWins()){
                firstPlaceLabel.setText("The winner is Lorenzo!");
                secondPlaceLabel.setText("In second place: " + gui.getUsername());
            } else {
                firstPlaceLabel.setText("The winner is: " + gui.getUsername());
                secondPlaceLabel.setText("In second place: Lorenzo");
            }
        } else {
            firstPlaceLabel.setText("The winner is: " + winMessage.getRanking().keySet().toArray()[0] + " (" + winMessage.getRanking().values().toArray()[0] + " pts.)");
            if(gui.getClientModel().getUsernamesList().size() >= 2){
                secondPlaceLabel.setText("In second place: " + winMessage.getRanking().keySet().toArray()[1] + " (" + winMessage.getRanking().values().toArray()[1] + " pts.)");
                if(gui.getClientModel().getUsernamesList().size() >= 3){
                    thirdPlaceLabel.setText("In third place: " + winMessage.getRanking().keySet().toArray()[2] + " (" + winMessage.getRanking().values().toArray()[2] + " pts.)");
                    if(gui.getClientModel().getUsernamesList().size() == 4)
                        fourthPlaceLabel.setText("In fourth place: " + winMessage.getRanking().keySet().toArray()[3] + " (" + winMessage.getRanking().values().toArray()[3] + " pts.)");
                }
            }
        }
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
