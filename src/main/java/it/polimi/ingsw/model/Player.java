package it.polimi.ingsw.model;

import it.polimi.ingsw.model.playerboard.PlayerBoard;

/**
 * <h1>Player</h1>
 * Player represents a player in a {@link Match}.
 */
public class Player {
    private PlayerBoard playerBoard;

    public Player() {
        playerBoard = new PlayerBoard();
    }
}
