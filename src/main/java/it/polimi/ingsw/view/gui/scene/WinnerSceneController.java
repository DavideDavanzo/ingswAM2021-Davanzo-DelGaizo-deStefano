package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.WinMessage;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Ranking scene
 */

public class WinnerSceneController implements GenericSceneController{

    private GuiView gui;

    private boolean winner;

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
        if(winner)
            gui.makeSound("/sounds/winner.mp3", 5);
        else gui.makeSound("/sounds/fail.mp3", 5);
    }

    private void setLabels(){
        if(gui.getClientModel().isSinglePlayer()){
            if(winMessage.isLorenzoWins()){
                firstPlaceLabel.setText("The winner is Lorenzo!");
                secondPlaceLabel.setText("In second place: " + gui.getUsername());
            } else {
                winner = true;
                firstPlaceLabel.setText("The winner is: " + gui.getUsername());
                secondPlaceLabel.setText("In second place: Lorenzo");
            }
        } else {
            firstPlaceLabel.setText("The winner is: " + winMessage.getRanking().get(0) + " (" + winMessage.getRankingTable().get(winMessage.getRanking().get(0)) + " pts.)");
            if(gui.getClientModel().getUsernamesList().size() >= 2){
                secondPlaceLabel.setText("In second place: " + winMessage.getRanking().get(1) + " (" +  winMessage.getRankingTable().get(winMessage.getRanking().get(1)) + " pts.)");
                if(gui.getClientModel().getUsernamesList().size() >= 3){
                    thirdPlaceLabel.setText("In third place: " + winMessage.getRanking().get(2) + " (" +  winMessage.getRankingTable().get(winMessage.getRanking().get(2)) + " pts.)");
                    if(gui.getClientModel().getUsernamesList().size() == 4)
                        fourthPlaceLabel.setText("In fourth place: " + winMessage.getRanking().get(3) + " (" +  winMessage.getRankingTable().get(winMessage.getRanking().get(3)) + " pts.)");
                }
            }
            if(!winMessage.getRanking().get(winMessage.getRanking().size()-1).equals(gui.getUsername()))
                winner = true;
        }
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
