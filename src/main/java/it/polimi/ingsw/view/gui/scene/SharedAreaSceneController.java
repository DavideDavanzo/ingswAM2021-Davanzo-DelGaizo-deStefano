package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class SharedAreaSceneController implements GenericSceneController{

    private GuiView gui;

    @FXML
    private GridPane gridPane;

    @FXML
    private GridPane cardGridPane;

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


    public void firstRowArrowButtonClick(Event event) {
    }

    public void secondRowArrowButtonClick(ActionEvent event) {
    }

    public void thirdRowArrowButtonClick(ActionEvent event) {
    }

    public void firstColumnArrowButtonClick(ActionEvent event) {
    }

    public void secondColumnArrowButtonClick(ActionEvent event) {
    }

    public void thirdColumnArrowButtonCLick(ActionEvent event) {
    }

    public void fourthColumnArrowButtonClick(ActionEvent event) {
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
