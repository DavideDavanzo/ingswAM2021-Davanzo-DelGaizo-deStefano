package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.view.View;

public class BuyCardCmd extends Message{

    private ECardColor color;

    private int level;

    public BuyCardCmd(){
        super();
    }

    public BuyCardCmd(ECardColor color, int level){
        this.color = color;
        this.level = level;
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
}
