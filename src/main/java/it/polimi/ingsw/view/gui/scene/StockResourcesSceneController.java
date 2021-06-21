package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.messages.ArrangeInWarehouseCmd;
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
 * In this scene the player stocks his/hers/their resources
 */
public class StockResourcesSceneController implements GenericSceneController{

    private GuiView gui;

    private ArrayList<Item> incomingResources;
    private int numExtraShelves;
    private ArrayList<Integer> choices;
    private int currResourceIndex;

    @FXML
    private Button firstShelfButton, secondShelfButton, thirdShelfButton, discardButton, firstExtraButton, secondExtraButton;

    @FXML
    private GridPane resourcesGridPane, leadersGridPane, toFirstShelfGridPane, toSecondShelfGridPane, toThirdShelfGridPane, toFirstExtraGridPane, toSecondExtraGridPane;

    @FXML
    private ImageView firstShelfFirstResource, secondShelfFirstResource, secondShelfSecondResource, thirdShelfFirstResource, thirdShelfSecondResource, thirdShelfThirdResource, firstLeaderFirstResource, firstLeaderSecondResource, secondLeaderFirstResource, secondLeaderSecondResource;

    public StockResourcesSceneController(ArrayList<Item> incomingResources, int numExtraShelves) {
        this.incomingResources = incomingResources;
        this.numExtraShelves = numExtraShelves;
        choices = new ArrayList<>();
        currResourceIndex = 0;
    }

    @FXML
    public void initialize(){

        //set images of the incoming market resources
        for(int i=0; i<incomingResources.size(); i++){
            ImageView imageView = (ImageView) resourcesGridPane.getChildren().get(i);
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + incomingResources.get(i).getClass().getSimpleName().toLowerCase() + ".png"))));
            if(i != currResourceIndex)
                imageView.setOpacity(0.5);
        }

        //set images and buttons of player's warehouse
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
        firstShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::addToFirstShelf);
        secondShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::addToSecondShelf);
        thirdShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::addToThirdShelf);
        discardButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::discard);

        //set images and buttons of optional extra shelves
        if(numExtraShelves>0) {
            LeaderCard firstExtraLeader = (LeaderCard) gui.getClientModel().getLeaderCards().stream().filter(l -> l.isActive() && l.getEffect() instanceof ExtraShelfEffect).toArray()[0];
            ((ImageView) leadersGridPane.getChildren().get(0)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + firstExtraLeader.getId() + ".png"))));
            firstExtraButton.setDisable(false);
            firstExtraButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::addToFirstExtra);
            if(!gui.getClientModel().getWarehouse().getExtraShelves().get(0).isEmpty()){
                firstLeaderFirstResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + gui.getClientModel().getWarehouse().getExtraShelves().get(0).getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
                if(gui.getClientModel().getWarehouse().getExtraShelves().get(0).getShelfResource().getVolume() == 2)
                    firstLeaderSecondResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + gui.getClientModel().getWarehouse().getExtraShelves().get(0).getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            }
        }
        if(numExtraShelves>1) {
            LeaderCard secondExtraLeader = (LeaderCard) gui.getClientModel().getLeaderCards().stream().filter(l -> l.isActive() && l.getEffect() instanceof ExtraShelfEffect).toArray()[1];
            ((ImageView) leadersGridPane.getChildren().get(1)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + secondExtraLeader.getId() + ".png"))));
            secondExtraButton.setDisable(false);
            secondExtraButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::addToSecondExtra);
            if(!gui.getClientModel().getWarehouse().getExtraShelves().get(1).isEmpty()){
                secondLeaderFirstResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + gui.getClientModel().getWarehouse().getExtraShelves().get(1).getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
                if(gui.getClientModel().getWarehouse().getExtraShelves().get(1).getShelfResource().getVolume() == 2)
                    secondLeaderSecondResource.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + gui.getClientModel().getWarehouse().getExtraShelves().get(1).getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            }
        }
    }

    public void addToFirstShelf(Event event){
        choices.add(1);
        for(Node node : toFirstShelfGridPane.getChildren()){
            ImageView imageView = (ImageView) node;
            if(imageView.getImage() == null){
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + incomingResources.get(currResourceIndex).getClass().getSimpleName().toLowerCase() + ".png"))));
                break;
            }
        }
        resourcesGridPane.getChildren().get(currResourceIndex++).setOpacity(0.5);
        if(currResourceIndex < incomingResources.size())
            resourcesGridPane.getChildren().get(currResourceIndex).setOpacity(1);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void addToSecondShelf(Event event){
        choices.add(2);
        for(Node node : toSecondShelfGridPane.getChildren()){
            ImageView imageView = (ImageView) node;
            if(imageView.getImage() == null){
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + incomingResources.get(currResourceIndex).getClass().getSimpleName().toLowerCase() + ".png"))));
                break;
            }
        }
        resourcesGridPane.getChildren().get(currResourceIndex++).setOpacity(0.5);
        if(currResourceIndex < incomingResources.size())
            resourcesGridPane.getChildren().get(currResourceIndex).setOpacity(1);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void addToThirdShelf(Event event){
        choices.add(3);
        for(Node node : toThirdShelfGridPane.getChildren()){
            ImageView imageView = (ImageView) node;
            if(imageView.getImage() == null){
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + incomingResources.get(currResourceIndex).getClass().getSimpleName().toLowerCase() + ".png"))));
                break;
            }
        }
        resourcesGridPane.getChildren().get(currResourceIndex++).setOpacity(0.5);
        if(currResourceIndex < incomingResources.size())
            resourcesGridPane.getChildren().get(currResourceIndex).setOpacity(1);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void discard(Event event){
        choices.add(0);
        resourcesGridPane.getChildren().get(currResourceIndex++).setOpacity(0.5);
        if(currResourceIndex < incomingResources.size())
            resourcesGridPane.getChildren().get(currResourceIndex).setOpacity(1);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void addToFirstExtra(Event event){
        choices.add(4);
        for(Node node : toFirstExtraGridPane.getChildren()){
            ImageView imageView = (ImageView) node;
            if(imageView.getImage() == null){
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + incomingResources.get(currResourceIndex).getClass().getSimpleName().toLowerCase() + ".png"))));
                break;
            }
        }
        resourcesGridPane.getChildren().get(currResourceIndex++).setOpacity(0.5);
        if(currResourceIndex < incomingResources.size())
            resourcesGridPane.getChildren().get(currResourceIndex).setOpacity(1);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void addToSecondExtra(Event event){
        choices.add(5);
        for(Node node : toSecondExtraGridPane.getChildren()){
            ImageView imageView = (ImageView) node;
            if(imageView.getImage() == null) {
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + incomingResources.get(currResourceIndex).getClass().getSimpleName().toLowerCase() + ".png"))));
                break;
            }
        }
        resourcesGridPane.getChildren().get(currResourceIndex++).setOpacity(0.5);
        if(currResourceIndex < incomingResources.size())
            resourcesGridPane.getChildren().get(currResourceIndex).setOpacity(1);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
