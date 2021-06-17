package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Servant;
import it.polimi.ingsw.model.resources.Shield;
import it.polimi.ingsw.model.resources.Stone;
import it.polimi.ingsw.network.messages.ActivateProductionCmd;
import it.polimi.ingsw.network.messages.SwitchShelvesCmd;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

public class PlayerBoardSceneController implements GenericSceneController{

    private GuiView gui;
    private ArrayList<Integer> shelvesToSwitch;
    private ArrayList<Integer> productionStacks;
    private Trade baseProduction;
    @FXML
    private Button backButton, firstShelfButton, secondShelfButton, thirdShelfButton, firstExtraButton, secondExtraButton, firstStackButton, secondStackButton, thirdStackButton, baseProductionButton, activateProductionButton, coinInputButton, shieldInputButton, stoneInputButton, servantInputButton, coinOutputButton, shieldOutputButton, stoneOutputButton, servantOutputButton;
    @FXML
    private ImageView pos0, pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13, pos14, pos15, pos16, pos17, pos18, pos19, pos20, pos21, pos22, pos23, pos24;
    private ImageView[] positions = new ImageView[25];
    @FXML
    private ImageView shelf1, shelf2pos1, shelf2pos2, shelf3pos1, shelf3pos2, shelf3pos3;
    @FXML
    private ImageView slot1lvl1, slot1lvl2, slot1lvl3, slot2lvl1, slot2lvl2, slot2lvl3, slot3lvl1,slot3lvl2,slot3lvl3;
    @FXML
    private ImageView coinCoffer, shieldCoffer, servantCoffer, stoneCoffer;
    @FXML
    private ImageView firstInputImageView, secondInputImageView, outputImageView;
    @FXML
    private Label coinCofferVolume, shieldCofferVolume, servantCofferVolume, stoneCofferVolume;
    @FXML
    private ImageView popeToken1, popeToken2, popeToken3;
    @FXML
    private AnchorPane mainAnchorPane, baseProductionAnchorPane;
    @FXML
    private GridPane baseInputGridPane, baseOutputGridPane;

    public void initialize() {
        Platform.runLater(this::initAll);
    }

    private void initAll() {
        initTrack();
        initWareHouse();
        initCoffer();
        initDevCards();
        if(!gui.getClientModel().isMyTurn()){
            firstShelfButton.setDisable(true);
            secondShelfButton.setDisable(true);
            thirdShelfButton.setDisable(true);
            //firstExtraButton.setDisable(true);
            //secondExtraButton.setDisable(true);
            firstStackButton.setDisable(true);
            secondStackButton.setDisable(true);
            thirdStackButton.setDisable(true);
            baseProductionButton.setDisable(true);
        }
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);
        firstShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchFirstShelf);
        secondShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchSecondShelf);
        thirdShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchThirdShelf);
        //firstExtraButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchFirstExtra);
        //secondExtraButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchSecondExtra);
        firstStackButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstStackClick);
        secondStackButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondStackClick);
        thirdStackButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdStackClick);
        baseProductionButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::baseProductionClick);
        activateProductionButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::activateProduction);
        coinInputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::coinInputClick);
        shieldInputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::shieldInputClick);
        stoneInputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::stoneInputClick);
        servantInputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::servantInputClick);
        coinOutputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::coinOutputClick);
        shieldOutputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::shieldOutputClick);
        stoneOutputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::stoneOutputClick);
        servantOutputButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::servantOutputClick);
        shelvesToSwitch = new ArrayList<>();
        productionStacks = new ArrayList<>();
    }

    private void initWareHouse(){
        Shelf firstShelf = gui.getClientModel().getWarehouse().getFirstShelf();
        Shelf secondShelf = gui.getClientModel().getWarehouse().getSecondShelf();
        Shelf thirdShelf = gui.getClientModel().getWarehouse().getThirdShelf();
        if(!firstShelf.isEmpty())
            shelf1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + firstShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
        if(!secondShelf.isEmpty()){
            shelf2pos1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + secondShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            if(secondShelf.getShelfResource().getVolume() == 2)
                shelf2pos2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + secondShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
        }
        if(!thirdShelf.isEmpty()){
            shelf3pos1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            if(thirdShelf.getShelfResource().getVolume() >= 2)
                shelf3pos2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
            if(thirdShelf.getShelfResource().getVolume() == 3)
                shelf3pos3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdShelf.getShelfResource().getClass().getSimpleName().toLowerCase() + ".png"))));
        }
    }

    private void initCoffer(){
        coinCoffer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/coin.png"))));
        shieldCoffer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/shield.png"))));
        stoneCoffer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/stone.png"))));
        servantCoffer.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/servant.png"))));
        Coffer coffer = gui.getClientModel().getCoffer();
        coinCofferVolume.setText(String.valueOf(coffer.getCoins().getVolume()));
        shieldCofferVolume.setText(String.valueOf(coffer.getShields().getVolume()));
        stoneCofferVolume.setText(String.valueOf(coffer.getStones().getVolume()));
        servantCofferVolume.setText(Integer.toString(coffer.getServants().getVolume()));
    }

    private void initTrack() {
        positions[0] = pos0;
        positions[1] = pos1;
        positions[2] = pos2;
        positions[3] = pos3;
        positions[4] = pos4;
        positions[5] = pos5;
        positions[6] = pos6;
        positions[7] = pos7;
        positions[8] = pos8;
        positions[9] = pos9;
        positions[10] = pos10;
        positions[11] = pos11;
        positions[12] = pos12;
        positions[13] = pos13;
        positions[14] = pos14;
        positions[15] = pos15;
        positions[16] = pos16;
        positions[17] = pos17;
        positions[18] = pos18;
        positions[19] = pos19;
        positions[20] = pos20;
        positions[21] = pos21;
        positions[22] = pos22;
        positions[23] = pos23;
        positions[24] = pos24;

        positions[gui.getClientModel().getFaithTrack().getCurrentPositionAsInt()].setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/thumbnail_faith.png"))));

        if(gui.getClientModel().getFaithTrack().getPopeTokens().get(0).isFaceUp())
            popeToken1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/.png"))));
        else popeToken1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/quadrato giallo.png"))));
        if(gui.getClientModel().getFaithTrack().getPopeTokens().get(1).isFaceUp())
            popeToken2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/.png"))));
        else popeToken2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/quadrato arancione.png"))));
        if(gui.getClientModel().getFaithTrack().getPopeTokens().get(2).isFaceUp())
            popeToken3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/.png"))));
        else popeToken3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/quadrato rosso.png"))));

    }

    public void initDevCards(){
        Stack<DevelopmentCard> firstStack = gui.getClientModel().getDevelopmentCardsArea().getFirstStack();
        Stack<DevelopmentCard> secondStack = gui.getClientModel().getDevelopmentCardsArea().getSecondStack();
        Stack<DevelopmentCard> thirdStack = gui.getClientModel().getDevelopmentCardsArea().getThirdStack();
        if(!firstStack.isEmpty()){
            slot1lvl1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + firstStack.get(0).getId() + ".png"))));
            if(firstStack.size() >= 2)
                slot1lvl2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + firstStack.get(1).getId() + ".png"))));
            if(firstStack.size() == 3)
                slot1lvl3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + firstStack.get(2).getId() + ".png"))));
            firstStackButton.setDisable(false);
        }
        if(!secondStack.isEmpty()){
            slot2lvl1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + secondStack.get(0).getId() + ".png"))));
            if(secondStack.size() >= 2)
                slot2lvl2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + secondStack.get(1).getId() + ".png"))));
            if(secondStack.size() == 3)
                slot2lvl3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + secondStack.get(2).getId() + ".png"))));
            secondStackButton.setDisable(false);
        }
        if(!thirdStack.isEmpty()){
            slot3lvl1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + thirdStack.get(0).getId() + ".png"))));
            if(thirdStack.size() >= 2)
                slot3lvl2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + thirdStack.get(1).getId() + ".png"))));
            if(thirdStack.size() == 3)
                slot3lvl3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + thirdStack.get(2).getId() + ".png"))));
            thirdStackButton.setDisable(false);
        }
    }

    public void switchFirstShelf(Event event){
        shelvesToSwitch.add(1);
        if(shelvesToSwitch.size() == 2){
            gui.sendMessage(new SwitchShelvesCmd(shelvesToSwitch.get(0), shelvesToSwitch.get(1)));
            shelvesToSwitch = new ArrayList<>();
        }
    }

    public void switchSecondShelf(Event event){
        shelvesToSwitch.add(2);
        if(shelvesToSwitch.size() == 2){
            gui.sendMessage(new SwitchShelvesCmd(shelvesToSwitch.get(0), shelvesToSwitch.get(1)));
            shelvesToSwitch = new ArrayList<>();
        }
    }

    public void switchThirdShelf(Event event){
        shelvesToSwitch.add(3);
        if(shelvesToSwitch.size() == 2){
            gui.sendMessage(new SwitchShelvesCmd(shelvesToSwitch.get(0), shelvesToSwitch.get(1)));
            shelvesToSwitch = new ArrayList<>();
        }
    }

    public void switchFirstExtra(Event event){
        shelvesToSwitch.add(4);
        if(shelvesToSwitch.size() == 2){
            gui.sendMessage(new SwitchShelvesCmd(shelvesToSwitch.get(0), shelvesToSwitch.get(1)));
            shelvesToSwitch = new ArrayList<>();
        }
    }

    public void switchSecondExtra(Event event){
        shelvesToSwitch.add(5);
        if(shelvesToSwitch.size() == 2){
            gui.sendMessage(new SwitchShelvesCmd(shelvesToSwitch.get(0), shelvesToSwitch.get(1)));
            shelvesToSwitch = new ArrayList<>();
        }
    }

    public void firstStackClick(Event event){
        activateProductionButton.setDisable(false);
        productionStacks.add(1);
        firstStackButton.setDisable(true);
        firstStackButton.setOpacity(0.6);
    }

    public void secondStackClick(Event event){
        activateProductionButton.setDisable(false);
        productionStacks.add(2);
        secondStackButton.setDisable(true);
        secondStackButton.setOpacity(0.6);
    }

    public void thirdStackClick(Event event){
        activateProductionButton.setDisable(false);
        productionStacks.add(3);
        thirdStackButton.setDisable(true);
        thirdStackButton.setOpacity(0.6);
    }

    public void baseProductionClick(Event event){
        baseProduction = new Trade();
        baseProduction.setInput(new ArrayList<>());
        baseProduction.setOutput(new ArrayList<>());
        //mainAnchorPane.setDisable(true);
        baseProductionAnchorPane.setDisable(false);
        baseProductionAnchorPane.setVisible(true);
    }

    public void coinInputClick(Event event){
        baseProduction.getInput().add(new Coin(1));
        if(baseProduction.getInput().size() == 1)
            firstInputImageView.setImage(new Image("/images/resources/coin.png"));
        else
            secondInputImageView.setImage(new Image("/images/resources/coin.png"));
        handleBaseInputClick();
    }

    public void shieldInputClick(Event event){
        baseProduction.getInput().add(new Shield(1));
        if(baseProduction.getInput().size() == 1)
            firstInputImageView.setImage(new Image("/images/resources/shield.png"));
        else
            secondInputImageView.setImage(new Image("/images/resources/shield.png"));
        handleBaseInputClick();
    }

    public void stoneInputClick(Event event){
        baseProduction.getInput().add(new Stone(1));
        if(baseProduction.getInput().size() == 1)
            firstInputImageView.setImage(new Image("/images/resources/stone.png"));
        else
            secondInputImageView.setImage(new Image("/images/resources/stone.png"));
        handleBaseInputClick();
    }

    public void servantInputClick(Event event){
        baseProduction.getInput().add(new Servant(1));
        if(baseProduction.getInput().size() == 1)
            firstInputImageView.setImage(new Image("/images/resources/servant.png"));
        else
            secondInputImageView.setImage(new Image("/images/resources/servant.png"));
        handleBaseInputClick();
    }

    public void coinOutputClick(Event event){
        baseProduction.getOutput().add(new Coin(1));
        outputImageView.setImage(new Image("/images/resources/coin.png"));
        handleBaseOutputClick();
    }

    public void shieldOutputClick(Event event){
        baseProduction.getOutput().add(new Shield(1));
        outputImageView.setImage(new Image("/images/resources/shield.png"));
        handleBaseOutputClick();
    }

    public void stoneOutputClick(Event event){
        baseProduction.getOutput().add(new Stone(1));
        outputImageView.setImage(new Image("/images/resources/stone.png"));
        handleBaseOutputClick();
    }

    public void servantOutputClick(Event event){
        baseProduction.getOutput().add(new Servant(1));
        outputImageView.setImage(new Image("/images/resources/servant.png"));
        handleBaseOutputClick();
    }

    public void activateProduction(Event event){
        gui.sendMessage(new ActivateProductionCmd(baseProduction, baseProduction!=null, productionStacks));
    }

    public void backButtonClick(Event event){
        backButton.setDisable(true);
        Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
    }

    private void handleBaseInputClick(){
        if(baseProduction.getInput().size() == 2) {
            coinInputButton.setDisable(true);
            shieldInputButton.setDisable(true);
            stoneInputButton.setDisable(true);
            servantInputButton.setDisable(true);
            for(Node node : baseInputGridPane.getChildren()){
                node.setOpacity(0.5);
            }
            if(baseProduction.getOutput().size() == 1) {
                baseProductionAnchorPane.setDisable(true);
                baseProductionAnchorPane.setVisible(false);
                mainAnchorPane.setDisable(false);
                activateProductionButton.setDisable(false);
            }
        }
    }

    private void handleBaseOutputClick(){
        if(baseProduction.getOutput().size() == 1) {
            coinOutputButton.setDisable(true);
            shieldOutputButton.setDisable(true);
            stoneOutputButton.setDisable(true);
            servantOutputButton.setDisable(true);
            for(Node node : baseOutputGridPane.getChildren()){
                node.setOpacity(0.5);
            }
            if(baseProduction.getInput().size() == 2) {
                baseProductionAnchorPane.setDisable(true);
                baseProductionAnchorPane.setVisible(false);
                mainAnchorPane.setDisable(false);
                activateProductionButton.setDisable(false);
            }
        }
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}

