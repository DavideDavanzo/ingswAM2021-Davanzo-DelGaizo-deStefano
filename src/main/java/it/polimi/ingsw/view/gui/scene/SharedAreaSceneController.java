package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.network.messages.MarketResourcesCmd;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;

import java.util.Objects;

/**
 * This scene implements the shared area, composed by the resources market anf the card market
 */
public class SharedAreaSceneController implements GenericSceneController {

    private GuiView gui;

    @FXML
    private GridPane gridPane;

    @FXML
    private GridPane cardGridPane;

    @FXML
    private Button backButton;

    @FXML
    private Button firstRowArrowButton, secondRowArrowButton, thirdRowArrowButton,firstColumnArrowButton, secondColumnArrowButton,thirdColumnArrowButton,fourthColumnArrowButton;

    @FXML
    private Button firstGreen, secondGreen, thirdGreen, firstBlue, secondBlue, thirdBlue, firstYellow, secondYellow, thirdYellow, firstPurple, secondPurple, thirdPurple;

    @FXML
    private ImageView spareImageView;

    @FXML
    public void initialize(){
        setMarketImages();
        setCardMarketImages();
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);
        firstRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstRowArrowButtonClick);
        secondRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondRowArrowButtonClick);
        thirdRowArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdRowArrowButtonClick);
        firstColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstColumnArrowButtonClick);
        secondColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondColumnArrowButtonClick);
        thirdColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdColumnArrowButtonCLick);
        fourthColumnArrowButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::fourthColumnArrowButtonClick);
        firstGreen.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstGreenCardButtonClick);
        firstBlue.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstBlueCardButtonClick);
        firstYellow.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstYellowCardButtonClick);
        firstPurple.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstPurpleCardButtonClick);
        secondGreen.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondGreenCardButtonClick);
        secondBlue.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondBlueCardButtonClick);
        secondYellow.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondYellowCardButtonClick);
        secondPurple.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondPurpleCardButtonClick);
        thirdGreen.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdGreenCardButtonClick);
        thirdBlue.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdBlueCardButtonClick);
        thirdYellow.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdYellowCardButtonClick);
        thirdPurple.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdPurpleCardButtonClick);
    }

    public void backButtonClick(Event event){
        backButton.setDisable(true);
        Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
    }

    public void setMarketImages() {

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {

                ImageView imageView = (ImageView) gridPane.getChildren().get(j * 4 + i);
                imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/marbles/" + gui.getClientModel().getMarket().getMarketMatrix()[j][i].toPath() + ".png"))));

            }
        }

        spareImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/marbles/" + gui.getClientModel().getMarket().getSpareMarble().toPath() + ".png"))));

    }

    public void setCardMarketImages() {

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                Image image;
                if(!gui.getClientModel().getCardMarket().getDecks()[j][i].isEmpty())
                    image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + gui.getClientModel().getCardMarket().getDecks()[j][i].getCards().peek().getId() + ".png")));
                else
                    image = null;
                ImageView imageView = (ImageView) cardGridPane.getChildren().get(j * 4 + i);
                imageView.setImage(image);

            }
        }
    }

    public void firstRowArrowButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            gui.sendMessage(new MarketResourcesCmd( 'r',0));
        else System.out.println("Not your turn!");
    }

    public void secondRowArrowButtonClick(Event event) {
        if(gui.getClientModel().isMyTurn())
            gui.sendMessage(new MarketResourcesCmd( 'r',1));
        else System.out.println("Not your turn!");
    }

    public void thirdRowArrowButtonClick(Event event) {
        if(gui.getClientModel().isMyTurn())
            gui.sendMessage(new MarketResourcesCmd( 'r',2));
        else System.out.println("Not your turn!");
    }

    public void firstColumnArrowButtonClick(Event event) {
        if(gui.getClientModel().isMyTurn())
            gui.sendMessage(new MarketResourcesCmd( 'c',0));
        else System.out.println("Not your turn!");
    }

    public void secondColumnArrowButtonClick(Event event) {
        if(gui.getClientModel().isMyTurn())
            gui.sendMessage(new MarketResourcesCmd( 'c',1));
        else System.out.println("Not your turn!");
    }

    public void thirdColumnArrowButtonCLick(Event event) {
        if(gui.getClientModel().isMyTurn())
            gui.sendMessage(new MarketResourcesCmd( 'c',2));
        else System.out.println("Not your turn!");
    }

    public void fourthColumnArrowButtonClick(Event event) {
        if(gui.getClientModel().isMyTurn())
            gui.sendMessage(new MarketResourcesCmd( 'c',3));
        else System.out.println("Not your turn!");
    }

    public void firstGreenCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.GREEN, 1), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void firstBlueCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.BLUE, 1), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void firstYellowCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.YELLOW, 1), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void firstPurpleCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.PURPLE, 1), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void secondGreenCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.GREEN, 2), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void secondBlueCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.BLUE, 2), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void secondYellowCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.YELLOW, 2), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void secondPurpleCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.PURPLE, 2), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void thirdGreenCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.GREEN, 3), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void thirdBlueCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.BLUE, 3), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void thirdYellowCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.YELLOW, 3), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    public void thirdPurpleCardButtonClick(Event event){
        if(gui.getClientModel().isMyTurn())
            Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.PURPLE, 3), "playerDevArea_scene.fxml"));
        else System.out.println("Not your turn!");
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
