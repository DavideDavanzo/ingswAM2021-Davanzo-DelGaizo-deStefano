package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Objects;

public class LeaderSceneController implements GenericSceneController {

    private GuiView gui;

    private ArrayList<LeaderCard> leaderCards;

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
}
