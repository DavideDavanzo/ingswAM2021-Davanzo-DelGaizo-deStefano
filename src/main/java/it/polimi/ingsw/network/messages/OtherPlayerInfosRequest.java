package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class OtherPlayerInfosRequest extends Message{

    private String otherUsername;

    public OtherPlayerInfosRequest(){

    }

    public OtherPlayerInfosRequest(String otherUsername){
        this.otherUsername = otherUsername;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
        gameState.process(this);
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(String otherUsername) {
        this.otherUsername = otherUsername;
    }
}
