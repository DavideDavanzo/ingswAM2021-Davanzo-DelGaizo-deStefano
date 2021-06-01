package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.network.client.ClientModel;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.util.ArrayList;
import java.util.Objects;

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
    private ImageView firstLeader;
    @FXML
    private ImageView secondLeader;
    @FXML
    private ImageView thirdLeader;
    @FXML
    private ImageView fourthLeader;

    public void initialize() {
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
