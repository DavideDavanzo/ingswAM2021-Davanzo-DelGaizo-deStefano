package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.messages.ArrangeInWarehouseCmd;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Objects;

public class StockResourcesSceneController implements GenericSceneController{

    private GuiView gui;

    private ArrayList<Item> incomingResources;
    private int numExtraShelves;
    private ArrayList<Integer> choices;

    @FXML
    private Button firstShelfButton, secondShelfButton, thirdShelfButton, discardButton;

    @FXML
    private GridPane resourcesGridPane;

    @FXML
    private ImageView firstShelfFirstResource, secondShelfFirstResource, secondShelfSecondResource, thirdShelfFirstResource, thirdShelfSecondResource, thirdShelfThirdResource;

    public StockResourcesSceneController(ArrayList<Item> incomingResources, int numExtraShelves) {
        this.incomingResources = incomingResources;
        this.numExtraShelves = numExtraShelves;
        choices = new ArrayList<>();
    }

    @FXML
    public void initialize(){

        for(int i=0; i<incomingResources.size(); i++){
            ((ImageView) resourcesGridPane.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + incomingResources.get(i).getClass().getSimpleName().toLowerCase() + ".png"))));
        }

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

    }

    public void addToFirstShelf(Event event){
        choices.add(1);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void addToSecondShelf(Event event){
        choices.add(2);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void addToThirdShelf(Event event){
        choices.add(3);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    public void discard(Event event){
        choices.add(0);
        if(choices.size() == incomingResources.size())
            gui.sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
