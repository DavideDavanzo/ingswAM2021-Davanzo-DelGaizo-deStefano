package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.network.messages.BuyCardCmd;
import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.Event;
import javafx.scene.image.ImageView;
import java.util.Objects;
import java.util.Stack;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Scene created to allow the player to place his Development Cards
 */
public class PlayerDevAreaSceneController implements GenericSceneController{

    private GuiView gui;

    private ECardColor color;
    private int cardLvl;

    @FXML
    private ImageView firstSlotFirstLvl, firstSlotSecondLvl, firstSlotThirdLvl, secondSlotFirstLvl, secondSlotSecondLvl, secondSlotThirdLvl, thirdSlotFirstLvl, thirdSlotSecondLvl, thirdSlotThirdLvl;

    @FXML
    private Button firstDevSlot, secondDevSlot, thirdDevSlot, backButton;

    public PlayerDevAreaSceneController(ECardColor color, int cardLvl){
        this.color = color;
        this.cardLvl = cardLvl;
    }

    @FXML
    public void initialize(){
        Stack<DevelopmentCard> firstStack = gui.getClientModel().getDevelopmentCardsArea().getFirstStack();
        Stack<DevelopmentCard> secondStack = gui.getClientModel().getDevelopmentCardsArea().getSecondStack();
        Stack<DevelopmentCard> thirdStack = gui.getClientModel().getDevelopmentCardsArea().getThirdStack();
        if(!firstStack.isEmpty()){
            firstSlotFirstLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + firstStack.get(0).getId() + ".png"))));
            if(firstStack.size() >= 2)
                firstSlotSecondLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + firstStack.get(1).getId() + ".png"))));
            if(firstStack.size() == 3)
                firstSlotThirdLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + firstStack.get(2).getId() + ".png"))));
        }
        if(!secondStack.isEmpty()){
            secondSlotFirstLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + secondStack.get(0).getId() + ".png"))));
            if(secondStack.size() >= 2)
                secondSlotSecondLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + secondStack.get(1).getId() + ".png"))));
            if(secondStack.size() == 3)
                secondSlotThirdLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + secondStack.get(2).getId() + ".png"))));
        }
        if(!thirdStack.isEmpty()){
            thirdSlotFirstLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + thirdStack.get(0).getId() + ".png"))));
            if(thirdStack.size() >= 2)
                thirdSlotSecondLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + thirdStack.get(1).getId() + ".png"))));
            if(thirdStack.size() == 3)
                thirdSlotThirdLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsFront/" + thirdStack.get(2).getId() + ".png"))));
        }
        firstDevSlot.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstSlotClick);
        secondDevSlot.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondSlotClick);
        thirdDevSlot.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdSlotClick);
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);
    }

    public void firstSlotClick(Event event){
        gui.sendMessage(new BuyCardCmd(color, cardLvl, 1));
    }

    public void secondSlotClick(Event event){
        gui.sendMessage(new BuyCardCmd(color, cardLvl, 2));
    }

    public void thirdSlotClick(Event event){
        gui.sendMessage(new BuyCardCmd(color, cardLvl, 3));
    }

    public void backButtonClick(Event event){
        Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
