package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.network.messages.MarketResourcesCmd;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;

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
    private Button firstGreen, secondGreen, thirdGreen, firstBlue, secondBlue, thirdBlue, firstYellow, secondYellow, thirdYellow, firstPurple, secondPurple, thirdPurple;

    @FXML
    public void initialize(){
        setMarketImages();
        setCardMarketImages();
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

    public void firstGreenCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.GREEN, 1), "playerDevArea_scene.fxml"));
    }

    public void firstBlueCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.BLUE, 1), "playerDevArea_scene.fxml"));
    }

    public void firstYellowCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.YELLOW, 1), "playerDevArea_scene.fxml"));
    }

    public void firstPurpleCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.PURPLE, 1), "playerDevArea_scene.fxml"));
    }

    public void secondGreenCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.GREEN, 2), "playerDevArea_scene.fxml"));
    }

    public void secondBlueCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.BLUE, 2), "playerDevArea_scene.fxml"));
    }

    public void secondYellowCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.YELLOW, 2), "playerDevArea_scene.fxml"));
    }

    public void secondPurpleCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.PURPLE, 2), "playerDevArea_scene.fxml"));
    }

    public void thirdGreenCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.GREEN, 3), "playerDevArea_scene.fxml"));
    }

    public void thirdBlueCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.BLUE, 3), "playerDevArea_scene.fxml"));
    }

    public void thirdYellowCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.YELLOW, 3), "playerDevArea_scene.fxml"));
    }

    public void thirdPurpleCardButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, new PlayerDevAreaSceneController(ECardColor.PURPLE, 3), "playerDevArea_scene.fxml"));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
