package it.polimi.ingsw.model;

import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

public class PlayerStub extends Player {

    private Resource whiteResource;
    private boolean whiteMarblePower;

    @Override
    public boolean hasWhiteMarblePower() {
        return whiteMarblePower;
    }

    public void setWhiteMarblePower(boolean whiteMarblePower){
        this.whiteMarblePower = whiteMarblePower;
    }

    @Override
    public Resource getWhiteResource() {
        return whiteResource;
    }

    public void setWhiteResource(Resource whiteResource) {
        this.whiteResource = whiteResource;
    }

    public PlayerStub(){};

    public PlayerStub(Resource whiteResource, boolean whiteMarblePower) {
        this.whiteResource = whiteResource;
        this.whiteMarblePower = whiteMarblePower;
        }

}

