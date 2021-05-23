package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.view.View;

public class CofferUpdate extends Message{

    @JsonSerialize(as = Coffer.class)
    private Coffer coffer;

    public CofferUpdate() {
    }

    public CofferUpdate(String msg) {
        super(msg);
    }

    public CofferUpdate(Coffer coffer){
        this.coffer = coffer;
    }

    @Override
    public void apply(View view) {

    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public void setCoffer(Coffer coffer) {
        this.coffer = coffer;
    }
}
