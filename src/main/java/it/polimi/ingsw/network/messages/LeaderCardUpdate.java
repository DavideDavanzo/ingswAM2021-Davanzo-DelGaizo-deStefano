package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class LeaderCardUpdate extends Message{

    private int index;

    private LeaderCardUpdate(){

    }

    public LeaderCardUpdate(int index) {
        this.index = index;
    }

    public LeaderCardUpdate(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        view.updateActiveLeader(index);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public int getIndex() {
        return index;
    }
}
