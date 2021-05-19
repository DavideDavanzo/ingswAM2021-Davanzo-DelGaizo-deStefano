package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

public class TurnWakeMessage extends Message {

    public TurnWakeMessage() {
        super("Your turn started!\n" + "Pick your moves. .");
    }

    public TurnWakeMessage(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        System.out.println(msg);
        view.askCommand();
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
    }
}
