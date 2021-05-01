package it.polimi.ingsw.modelTest.stubs;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.Warehouse;

//TODO: Remove when Player class is ready
public class PlayerEffectStub extends Player {

    private PlayerBoardStub playerBoard;

    public PlayerEffectStub() { playerBoard = new PlayerBoardStub(); }

    /**
     * Temporary PlayerBoard class.
     */
    private class PlayerBoardStub extends PlayerBoard {

        // Contains a Warehouse, already implemented
        private Warehouse warehouse;

        public PlayerBoardStub() {
            warehouse = new Warehouse();
        }

    }

}