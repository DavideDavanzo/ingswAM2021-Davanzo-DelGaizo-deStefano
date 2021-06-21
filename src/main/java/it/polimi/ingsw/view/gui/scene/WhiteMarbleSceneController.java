package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.messages.ChangeWhiteMarbleReply;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class Implements the scene used to make the player choose which resource he/she/they desire/s thanks to the
 * white marble leader card effect
 */
public class WhiteMarbleSceneController implements GenericSceneController {

    private GuiView gui;

    private ArrayList<Item> items;
    ArrayList<Item> choices;
    int cont;

    @FXML
    private ImageView firstShelfFirstResource, secondShelfFirstResource, secondShelfSecondResource, thirdShelfFirstResource, thirdShelfSecondResource, thirdShelfThirdResource, firstLeaderFirstResource, firstLeaderSecondResource, secondLeaderFirstResource, secondLeaderSecondResource;
    @FXML
    private GridPane leadersGridPane, whiteMarblesGridPane;
    @FXML
    private Button firstLeaderButton, secondLeaderButton;

    public WhiteMarbleSceneController(ArrayList<Item> items, int cont){
        this.items = items;
        this.cont = cont;
        choices = new ArrayList<>();
    }

    @FXML
    public void initialize(){

        Shelf firstShelf = gui.getClientModel().getWarehouse().getFirstShelf();
        Shelf secondShelf = gui.getClientModel().getWarehouse().getSecondShelf();
        Shelf thirdShelf = gui.getClientModel().getWarehouse().getThirdShelf();
        if(!firstShelf.isEmpty())
            firstShelfFirstResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + firstShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
        if(!secondShelf.isEmpty()){
            secondShelfFirstResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + secondShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            if(secondShelf.getShelfResource().getVolume() == 2)
                secondShelfSecondResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + secondShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
        }
        if(!thirdShelf.isEmpty()){
            thirdShelfFirstResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            if(thirdShelf.getShelfResource().getVolume() >= 2)
                thirdShelfSecondResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            if(thirdShelf.getShelfResource().getVolume() == 3)
                thirdShelfThirdResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
        }

        for(LeaderCard leaderCard : gui.getClientModel().getLeaderCards()) {
            ((ImageView) leadersGridPane.getChildren().get(0)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + leaderCard.getId() + ".png"))));
        }

        for(Node node : whiteMarblesGridPane.getChildren()){
            ((ImageView) node).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/marbles/whitemarble.png"))));
        }

        firstLeaderButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstLeaderClick);
        secondLeaderButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondLeaderClick);

    }

    private void firstLeaderClick(Event event){
        choices.add(items.get(0));
        if(choices.size() == cont)
            gui.sendMessage(new ChangeWhiteMarbleReply(choices));
    }

    private void secondLeaderClick(Event event){
        choices.add(items.get(1));
        if(choices.size() == cont)
            gui.sendMessage(new ChangeWhiteMarbleReply(choices));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
