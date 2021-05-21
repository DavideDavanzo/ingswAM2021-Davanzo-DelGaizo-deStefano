package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.view.View;

public class BuyCardCmd extends Message{

    private ECardColor color;

    private int level;

    private int slot;

    public BuyCardCmd(){
        super();
    }

    public BuyCardCmd(ECardColor color, int level, int slot){
        this.color = color;
        this.level = level;
        this.slot = slot;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public ECardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public int getSlot() {
        return slot;
    }
}
