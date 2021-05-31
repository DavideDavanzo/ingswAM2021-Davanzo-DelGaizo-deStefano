package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class LeaderSceneController implements GenericSceneController {

    private GuiView gui;

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
}
