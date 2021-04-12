package it.polimi.ingsw.modelTest.stubs;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

//TODO: Remove when Player class is ready
public class PlayerStub extends Player {

    private boolean whiteMarblePower;
    private Marble extraMarble;

    public PlayerStub() { };

    public PlayerStub(Marble extraMarble, boolean whiteMarblePower) {
        this.extraMarble = extraMarble;
        this.whiteMarblePower = whiteMarblePower;
    }

    @Override
    public boolean hasWhiteMarblePower() {
        return whiteMarblePower;
    }

    @Override
    public Marble getExtraMarble() {
        return extraMarble;
    }

    public void setWhiteMarblePower(boolean whiteMarblePower){
        this.whiteMarblePower = whiteMarblePower;
    }

    public void setExtraMarble(Marble extraMarble) { this.extraMarble = extraMarble; }
}

