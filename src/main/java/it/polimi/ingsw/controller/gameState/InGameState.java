package it.polimi.ingsw.controller.gameState;

import it.polimi.ingsw.controller.GameController;

public class InGameState extends GameState{

    GameController gameController;

    InGameState(GameController gameController) {
        this.gameController = gameController;
    }
}
