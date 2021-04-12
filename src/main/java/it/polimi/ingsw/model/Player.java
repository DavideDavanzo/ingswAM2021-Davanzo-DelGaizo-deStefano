package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Discount;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;

/**
 * <h1>Player</h1>
 * Player represents a player in a {@link Match}.
 */
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

/**
 * <h1>Player</h1>
 * Represents a player in a {@link Match}.
 */
public class Player {

    private PlayerBoard playerBoard;
    private boolean whiteMarblePower;
    private Marble extraMarble;
    private Discount discount;

    /**
     * Default Constructor
     */
    public Player() {
        playerBoard = new PlayerBoard();
        whiteMarblePower = false;
    }

    /**
     * Enables the {@link WhiteMarbleEffect}
     * @param extraMarble is a colored Marble
     */
    public void giveWhiteMarblePower(Marble extraMarble) {
        this.whiteMarblePower = true;
        this.extraMarble = extraMarble;
    }

    public boolean hasWhiteMarblePower() { return whiteMarblePower; }

    public PlayerBoard getPlayerBoard() { return playerBoard; }

    public Marble getExtraMarble() { return extraMarble; }

}
