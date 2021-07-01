package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;

public class ResourceChoice extends Message {

    ArrayList<Resource> resources;

    public ResourceChoice() {
    }

    public ResourceChoice(String msg) {
        super(msg);
    }

    public ResourceChoice(ArrayList<Resource> resources) {
        super(null);
        this.resources = resources;
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }
}
