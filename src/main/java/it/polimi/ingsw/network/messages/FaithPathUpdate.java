package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.view.View;

public class FaithPathUpdate extends Message{

    @JsonSerialize(as = Path.class)
    private Path path;

    public FaithPathUpdate() {
    }

    public FaithPathUpdate(String msg) {
        super(msg);
    }

    public FaithPathUpdate(Path path){
        this.path = path;
    }

    @Override
    public void apply(View view) {
        view.updateFaithTrack(path);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public void setPath(Path path) {
        this.path = path;
    }
}
