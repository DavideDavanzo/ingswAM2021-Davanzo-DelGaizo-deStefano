package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.util.ArrayList;
import java.util.Objects;

/**
 * In this scene the player chooses two leader card
 */
public class LeaderSceneController implements GenericSceneController {

    private GuiView gui;

    private ArrayList<LeaderCard> leaderCards;

    private ArrayList<LeaderCard> leaderChoices;

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

    public void initialize() {
        leaderChoices = new ArrayList<>();
        setLeaderImages();
        firstCard.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstCardButton);
        secondCard.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondCardButton);
        thirdCard.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdCardButton);
        fourthCard.addEventHandler(MouseEvent.MOUSE_RELEASED, this::fourthCardButton);
    }

    private void setLeaderImages() {
        firstLeader.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + leaderCards.get(0).getId() + ".png"))));
        secondLeader.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + leaderCards.get(1).getId() + ".png"))));
        thirdLeader.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + leaderCards.get(2).getId() + ".png"))));
        fourthLeader.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + leaderCards.get(3).getId() + ".png"))));
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
        firstCard.setOpacity(0.6);
        if(secondCard.isDisabled() || thirdCard.isDisabled() || fourthCard.isDisabled()){
            secondCard.setDisable(true);
            thirdCard.setDisable(true);
            fourthCard.setDisable(true);
            leaderChoices.add(leaderCards.get(0));
            sendCards();
            return;
        }
        leaderChoices.add(leaderCards.get(0));
    }

    public void secondCardButton(Event event) {
        secondCard.setDisable(true);
        secondCard.setOpacity(0.6);
        if(firstCard.isDisabled() || thirdCard.isDisabled() || fourthCard.isDisabled()){
            firstCard.setDisable(true);
            thirdCard.setDisable(true);
            fourthCard.setDisable(true);
            leaderChoices.add(leaderCards.get(1));
            sendCards();
            return;
        }
        leaderChoices.add(leaderCards.get(1));
    }

    public void thirdCardButton(Event event) {
        thirdCard.setDisable(true);
        thirdCard.setOpacity(0.6);
        if(firstCard.isDisabled() || secondCard.isDisabled() || fourthCard.isDisabled()){
            firstCard.setDisable(true);
            secondCard.setDisable(true);
            fourthCard.setDisable(true);
            leaderChoices.add(leaderCards.get(2));
            sendCards();
            return;
        }
        leaderChoices.add(leaderCards.get(2));
    }

    public void fourthCardButton(Event event) {
        fourthCard.setDisable(true);
        fourthCard.setOpacity(0.6);
        if(firstCard.isDisabled() || secondCard.isDisabled() || thirdCard.isDisabled()){
            firstCard.setDisable(true);
            secondCard.setDisable(true);
            thirdCard.setDisable(true);
            leaderChoices.add(leaderCards.get(3));
            sendCards();
            return;
        }
        leaderChoices.add(leaderCards.get(3));
    }

    private void sendCards() {
        gui.leaderChoice(leaderChoices);
    }

}
