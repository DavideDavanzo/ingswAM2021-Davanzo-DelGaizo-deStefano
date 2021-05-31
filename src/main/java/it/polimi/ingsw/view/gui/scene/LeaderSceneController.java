package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class LeaderSceneController implements GenericSceneController {

    private GuiView gui;

    @FXML
    private Button firstCard;

    @FXML
    private Button secondCard;

    @FXML
    private Button thirdCard;

    @FXML
    private Button fourthCard;

    @FXML
    private ImageView firstLeader;
    @FXML
    private ImageView secondLeader;
    @FXML
    private ImageView thirdLeader;
    @FXML
    private ImageView fourthLeader;
    @FXML
    public HBox hbox;

    private ImageView[] images = {firstLeader, secondLeader, thirdLeader,fourthLeader};

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

    public void showLeaders(ArrayList<LeaderCard> leaderCards) {
        for(int i = 0; i < leaderCards.size(); i++) {
            images[i] = new ImageView(new Image(getClass().getResource("/images/cardsFront" + leaderCards.get(i).getId() + ".png").toString()));
            hbox.getChildren().add(images[i]);
        }
    }

    public void firstCardButton(Event event) {
        firstCard.setDisable(true);
        if(secondCard.isDisabled() || thirdCard.isDisabled() || fourthCard.isDisabled()){
            secondCard.setDisable(true);
            thirdCard.setDisable(true);
            fourthCard.setDisable(true);
        }
    }

    public void secondCardButton(Event event) {
        secondCard.setDisable(true);
        if(firstCard.isDisabled() || thirdCard.isDisabled() || fourthCard.isDisabled()){
            firstCard.setDisable(true);
            thirdCard.setDisable(true);
            fourthCard.setDisable(true);
        }
    }

    public void thirdCardButton(Event event) {
        thirdCard.setDisable(true);
        if(firstCard.isDisabled() || secondCard.isDisabled() || fourthCard.isDisabled()){
            firstCard.setDisable(true);
            secondCard.setDisable(true);
            fourthCard.setDisable(true);
        }
    }

    public void fourthCardButton(Event event) {
        fourthCard.setDisable(true);
        if(firstCard.isDisabled() || secondCard.isDisabled() || thirdCard.isDisabled()){
            firstCard.setDisable(true);
            secondCard.setDisable(true);
            thirdCard.setDisable(true);
        }
    }
}
