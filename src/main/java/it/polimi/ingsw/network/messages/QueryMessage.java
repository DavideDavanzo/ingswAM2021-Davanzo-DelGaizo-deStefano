package it.polimi.ingsw.network.messages;


import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class QueryMessage extends Message {

    public QueryMessage(){
        super();
    }

    public QueryMessage(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        view.askQuery(this.getMsg());
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

}
