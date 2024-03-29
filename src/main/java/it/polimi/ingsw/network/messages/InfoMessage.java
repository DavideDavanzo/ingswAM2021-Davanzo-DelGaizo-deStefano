package it.polimi.ingsw.network.messages;


import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class InfoMessage extends Message {

    public InfoMessage(){
        super();
    }

    public InfoMessage(String msg){
        super(msg);
    }

    @Override
    public void apply(ClientView view) {
        view.showMessage(msg);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

}
