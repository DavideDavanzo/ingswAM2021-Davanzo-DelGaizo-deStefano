package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.effects.Discount;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;

import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

/**
 * <h1>Player</h1>
 * Represents a player in a {@link Match}.
 */
public class Player {

    private PlayerBoard playerBoard;
    private boolean whiteMarblePower;
    private Marble extraMarble;
    private Discount discount;
    private ArrayList<LeaderCard> leaderCards;
    private String nickname;

    /**
     * Default Constructor
     */
    public Player() {
        playerBoard = new PlayerBoard();
        whiteMarblePower = false;
        leaderCards = new ArrayList<>();
    }

    public Player(String nickname) {
        playerBoard = new PlayerBoard();
        whiteMarblePower = false;
        leaderCards = new ArrayList<>();
        this.nickname = nickname;
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

    public int getCurrentVictoryPoints() {

        int victoryPoints = playerBoard.calculateVictoryPoints();      //add points given by development cards and path

        for(LeaderCard card : leaderCards)
            victoryPoints += card.getVictoryPoints();   //add points given by leader cards

        return victoryPoints;

    }

    public PlayerBoard getPlayerBoard() { return playerBoard; }

    public Marble getExtraMarble() { return extraMarble; }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

}
