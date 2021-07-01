package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;

public class ChangeWhiteMarbleReply extends Message {

    ArrayList<Item> changedItems;

    public ChangeWhiteMarbleReply() {
    }

    public ChangeWhiteMarbleReply(String msg) {
        super(msg);
    }

    public ChangeWhiteMarbleReply(ArrayList<Item> changedItems) {
        this.changedItems = changedItems;
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public ArrayList<Item> getChangedItems() {
        return changedItems;
    }
}
