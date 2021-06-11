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

import java.util.ArrayList;
import java.util.Objects;

public class SharedAreaSceneController implements GenericSceneController {

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
        setCardMarketImages();
        firstRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstRowArrowButtonClick);
        secondRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondRowArrowButtonClick);
        thirdRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdRowArrowButtonClick);
        firstColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstColumnArrowButtonClick);
        secondColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondColumnArrowButtonClick);
        thirdRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdColumnArrowButtonCLick);
        fourthColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::fourthColumnArrowButtonClick);
    }

    public void setMarketImages() {

        for(int i = 0; i < 4; i++) {

            for(int j = 0; j < 3; j++) {

                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/marbles/redmarble.png")));
                ImageView imageView = (ImageView) gridPane.getChildren().get(j * 4 + i);
                imageView.setImage(image);

                //gridPane.add(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/marbles/" + gui.getClientModel().getMarket().getMarbleArrayList().get(i * 4 + j).toPath() + ".png")))), i, j);

            }

        }

    }

    public void setCardMarketImages() {

        for(int i = 0; i < 4; i++) {

            for(int j = 0; j < 3; j++) {

                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + gui.getClientModel().getCardMarket().getDecks()[j][i].getCards().peek().getId() + ".png")));
                ImageView imageView = (ImageView) cardGridPane.getChildren().get(j * 4 + i);
                imageView.setImage(image);

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
