package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.ClientView;

public class TurnWakeMessage extends Message {

    public TurnWakeMessage() {
        super("Your turn started!\n" + "Pick your moves. .");
    }

    public TurnWakeMessage(String msg) {
        super(msg);
    }

    @Override
    public void apply(ClientView view) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        view.showMessage(msg);
        view.getClientModel().setMyTurn(true);
        view.askCommand();
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
    }
}
