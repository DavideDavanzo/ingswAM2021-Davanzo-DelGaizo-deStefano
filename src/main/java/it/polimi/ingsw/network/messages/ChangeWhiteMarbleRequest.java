package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class ChangeWhiteMarbleRequest extends Message {

    private ArrayList<Item> itemsGivenByMarble;
    private int objectsToChange;


    public ChangeWhiteMarbleRequest() {

    }

    public ChangeWhiteMarbleRequest(String msg) {
        super(msg);
    }

    public ChangeWhiteMarbleRequest(ArrayList<Item> itemsGivenByMarble, int count) {
        super("You have to change" + count + " white marbles using the power given by your leader cards:");
        this.itemsGivenByMarble = itemsGivenByMarble;
        this.objectsToChange = count;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
    }

}

