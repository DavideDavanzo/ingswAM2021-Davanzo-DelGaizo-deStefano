package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;

public class ArrangeInWarehouseCmd extends Message{

    private ArrayList<Integer> choices;

    public ArrangeInWarehouseCmd(){
        super();
    }

    public ArrangeInWarehouseCmd(String msg){
        super(msg);
    }

    public ArrangeInWarehouseCmd(ArrayList<Integer> choices){
        this.choices = choices;
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public ArrayList<Integer> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Integer> choices) {
        this.choices = choices;
    }
}
