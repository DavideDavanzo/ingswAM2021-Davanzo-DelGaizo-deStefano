package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.view.ClientView;

public class CofferUpdate extends Message{

    @JsonSerialize(as = Coffer.class)
    private Coffer coffer;

    public CofferUpdate(Coffer coffer) {
        this.coffer = coffer;
    }

    public CofferUpdate() {
    }

    @Override
    public void apply(ClientView view) {
        view.updateCoffer(coffer);
    }

    public Coffer getCoffer() {
        return coffer;
    }

    public void setCoffer(Coffer coffer) {
        this.coffer = coffer;
    }
}
