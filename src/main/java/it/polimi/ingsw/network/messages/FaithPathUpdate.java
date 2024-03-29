package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.view.ClientView;

public class FaithPathUpdate extends Message{

    @JsonSerialize(as = Path.class)
    private Path path;

    public FaithPathUpdate() {
    }

    public FaithPathUpdate(Path path) {
        this.path = path;
    }

    @Override
    public void apply(ClientView view) {
        view.updateFaithTrack(path);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
