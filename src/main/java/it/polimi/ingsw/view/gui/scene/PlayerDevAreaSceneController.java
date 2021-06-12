package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.network.messages.BuyCardCmd;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.Event;
import javafx.scene.image.ImageView;
import java.util.Objects;
import java.util.Stack;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class PlayerDevAreaSceneController implements GenericSceneController{

    private GuiView gui;

    private ECardColor color;
    private int cardLvl;

    @FXML
    private ImageView firstSlotFirstLvl, firstSlotSecondLvl, firstSlotThirdLvl, secondSlotFirstLvl, secondSlotSecondLvl, secondSlotThirdLvl, thirdSlotFirstLvl, thirdSlotSecondLvl, thirdSlotThirdLvl;

    @FXML
    private Button firstDevSlot, secondDevSlot, thirdDevSlot;

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
            firstSlotFirstLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + firstStack.get(0).toString() + ".png"))));
            if(firstStack.get(1) != null)
                firstSlotSecondLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + firstStack.get(1).toString() + ".png"))));
            if(firstStack.get(1) != null)
                firstSlotThirdLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + firstStack.get(1).toString() + ".png"))));
        }
        if(!secondStack.isEmpty()){
            secondSlotFirstLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + secondStack.get(0).toString() + ".png"))));
            if(secondStack.get(1) != null)
                secondSlotSecondLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + secondStack.get(1).toString() + ".png"))));
            if(secondStack.get(1) != null)
                secondSlotThirdLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + secondStack.get(1).toString() + ".png"))));
        }
        if(!thirdStack.isEmpty()){
            thirdSlotFirstLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdStack.get(0).toString() + ".png"))));
            if(thirdStack.get(1) != null)
                thirdSlotSecondLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdStack.get(1).toString() + ".png"))));
            if(thirdStack.get(1) != null)
                thirdSlotThirdLvl.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + thirdStack.get(1).toString() + ".png"))));
        }
        firstDevSlot.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstSlotClick);
        secondDevSlot.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondSlotClick);
        thirdDevSlot.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdSlotClick);
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

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
