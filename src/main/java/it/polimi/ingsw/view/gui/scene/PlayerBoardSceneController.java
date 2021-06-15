package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.network.messages.SwitchShelvesCmd;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class PlayerBoardSceneController implements GenericSceneController{

    private GuiView gui;

    private ArrayList<Integer> shelvesToSwitch;

    @FXML
    private Button backButton, firstShelfButton, secondShelfButton, thirdShelfButton, firstExtraButton, secondExtraButton;

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
    private Label coinCofferVolume, shieldCofferVolume, servantCofferVolume, stoneCofferVolume;

    @FXML
    private ImageView popeToken1, popeToken2, popeToken3;

    public void initialize() {
        Platform.runLater(this::initAll);
    }

    private void initAll() {
        initTrack();
        initWareHouse();
        initCoffer();
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);
        if(!gui.getClientModel().isMyTurn()){
            firstShelfButton.setDisable(true);
            secondShelfButton.setDisable(true);
            thirdShelfButton.setDisable(true);
            //firstExtraButton.setDisable(true);
            //secondExtraButton.setDisable(true);
        }
        firstShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchFirstShelf);
        secondShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchSecondShelf);
        thirdShelfButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchThirdShelf);
        //firstExtraButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchFirstExtra);
        //secondExtraButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::switchSecondExtra);
        shelvesToSwitch = new ArrayList<>();
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

    public void backButtonClick(Event event){
        backButton.setDisable(true);
        Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}

