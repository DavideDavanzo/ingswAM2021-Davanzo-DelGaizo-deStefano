package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.MarketResourcesCmd;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class SharedAreaSceneController implements GenericSceneController{

    private GuiView gui;

    @FXML
    private GridPane gridPane;

    @FXML
    private GridPane cardGridPane;

    @FXML
    private Button firstRowArrowButton, secondRowArrowButton, thirdRowArrowButton,firstColumnArrowButton, secondColumnArrowButton,thirdColumnArrowButton,fourthColumnArrowButton;

    @FXML
    public void initialize(){
        setMarketImages();
        setMarketImages();
        firstRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstRowArrowButtonClick);
        secondRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondRowArrowButtonClick);
        thirdRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdRowArrowButtonClick);
        firstColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstColumnArrowButtonClick);
        secondColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondColumnArrowButtonClick);
        thirdRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdColumnArrowButtonCLick);
        fourthColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::fourthColumnArrowButtonClick);

    }

    public void setMarketImages(){
        for(int i= 0; i< gridPane.getMaxWidth() -1; i++){
            for(int j = 0; j < gridPane.getMaxHeight() -1 ; j++) {
                gridPane.add(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/marbles/" + gui.getClientModel().getMarket().getMarbleArrayList().get(i * 4 + j).toPath() + ".png")))), i, j);
            }
        }
    }

    public void setCardMarketImages(){
        for(int i= 0; i< cardGridPane.getMaxWidth() -1; i++){
            for(int j = 0; j< cardGridPane.getMaxHeight() -1; i++){

                cardGridPane.add(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardFront/" + gui.getClientModel().getCardMarket().getDecks()[i][j].getCards().peek().getId() + "png")))), i, j);
            }
        }
    }

    public void firstRowArrowButtonClick(Event event){
        gui.sendMessage(new MarketResourcesCmd( 'r',0));
    }

    public void secondRowArrowButtonClick(Event event) {
        gui.sendMessage(new MarketResourcesCmd( 'r',1));
    }

    public void thirdRowArrowButtonClick(Event event) {
        gui.sendMessage(new MarketResourcesCmd( 'r',2));
    }

    public void firstColumnArrowButtonClick(Event event) {
        gui.sendMessage(new MarketResourcesCmd( 'c',0));
    }

    public void secondColumnArrowButtonClick(Event event) {
        gui.sendMessage(new MarketResourcesCmd( 'c',1));
    }

    public void thirdColumnArrowButtonCLick(Event event) {
        gui.sendMessage(new MarketResourcesCmd( 'c',2));
    }

    public void fourthColumnArrowButtonClick(Event event) {
        gui.sendMessage(new MarketResourcesCmd( 'c',3));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
