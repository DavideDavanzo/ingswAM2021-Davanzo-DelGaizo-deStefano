package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class ResourcesToWhCmd extends Message{

    private ArrayList<Integer> choices;

    public ResourcesToWhCmd(){
        super();
    }

    public ResourcesToWhCmd(String msg){
        super(msg);
    }

    public ResourcesToWhCmd(ArrayList<Integer> choices){
        this.choices = choices;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        //TODO: gameState.process(this);
    }

}
