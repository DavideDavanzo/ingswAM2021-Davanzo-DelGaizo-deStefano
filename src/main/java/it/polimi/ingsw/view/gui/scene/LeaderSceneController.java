package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class LeaderSceneController implements GenericSceneController {

    private GuiView gui;

    private ArrayList<LeaderCard> leaderCards;

    @FXML
    private Button firstCard;

    @FXML
    private Button secondCard;

    @FXML
    private Button thirdCard;

    @FXML
    private Button fourthCard;

    @FXML
    private ImageView firstLeader = new ImageView();
    @FXML
    private ImageView secondLeader = new ImageView();
    @FXML
    private ImageView thirdLeader = new ImageView();
    @FXML
    private ImageView fourthLeader = new ImageView();
    @FXML
    public HBox hbox;

    private ImageView[] images = {firstLeader, secondLeader, thirdLeader,fourthLeader};

    public void initialize() {
        setLeaderImages();
    }

    private void setLeaderImages() {
        int last = 0;
        for (ImageView imageView : images) {
            Image img = new Image(getClass().getResourceAsStream("/images/cardsFront/" + leaderCards.get(last).getId() + ".png"));
            imageView.setImage(img);
            last++;
        }
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
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
