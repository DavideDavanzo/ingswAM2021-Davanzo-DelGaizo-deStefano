package it.polimi.ingsw.model;

import it.polimi.ingsw.model.playerboard.PlayerBoard;

/**
 * <h1>Player</h1>
 * Player represents a player in a {@link Match}.
 */
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

public class Player {
    private PlayerBoard playerBoard;
    public boolean hasWhiteMarblePower() {
        return false;
    }

    public Player() { playerBoard = new PlayerBoard(); }

    public PlayerBoard getPlayerBoard() { return playerBoard; }
    public Resource getWhiteResource() {
        return null;
    }
}
