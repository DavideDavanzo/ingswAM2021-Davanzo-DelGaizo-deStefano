package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.Discount;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;

import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <h1>Player</h1>
 * Represents a player in a {@link Match}.
 */
public class Player {

    private String nickname;

    private PlayerBoard playerBoard;

    private ArrayList<LeaderCard> leaderCards;

    private ArrayList<Marble> extraMarbles;
    private boolean whiteMarblePower;

    private ArrayList<Discount> discounts;
    private boolean activeDiscount;

    private ArrayList<Trade> extraTrades;
    private boolean activeTrade;

    private int victoryPoints;

    /**
     * Default Constructor
     */
    public Player() {
        playerBoard = new PlayerBoard();
        leaderCards = new ArrayList<>();
        whiteMarblePower = false;
        activeDiscount = false;
        activeTrade = false;
        victoryPoints = 0;
    }

    public Player(String nickname) {
        playerBoard = new PlayerBoard();
        leaderCards = new ArrayList<>();
        this.nickname = nickname;
        whiteMarblePower = false;
        activeDiscount = false;
        activeTrade = false;
        victoryPoints = 0;
    }

    /**
     * Enables the {@link WhiteMarbleEffect}
     * @param extraMarble is a colored Marble
     */
    public void giveWhiteMarblePower(Marble extraMarble) {
        if(!whiteMarblePower) {
            extraMarbles = new ArrayList<>();
            whiteMarblePower = true;
        }
        extraMarbles.add(extraMarble);
    }

    /**
     * Return <b>true</b> if the {@link WhiteMarbleEffect} is active on the player.
     * @return
     */
    public boolean hasWhiteMarblePower() {
        return whiteMarblePower;
    }

    /**
     * Gives a discount to the player.
     * @param discount is a {@link Resource} discount, also see {@link Discount}.
     */
    public void giveDiscount(Discount discount) {
        if(!activeDiscount) {
            discounts = new ArrayList<>();
            activeDiscount = true;
        }
        discounts.add(discount);
    }

    /**
     * Returns <b>true</b> if the player has an active discount from a {@link LeaderCard}.
     * @return
     */
    public boolean hasDiscount() {
        return activeDiscount;
    }


    /**
     * Gives the player an extra possible Trade.
     * @param trade is made of a {@link FaithPoint} and a generic {@link Resource} as output.
     */
    public void giveExtraTrade(Trade trade) {
        if(!activeTrade) {
            extraTrades = new ArrayList<>();
            activeTrade = true;
        }
        extraTrades.add(trade);
    }

    /**
     * Returns <b>true</b> if the player has an active Extra Trade from a {@link LeaderCard}.
     * @return
     */
    public boolean hasExtraTrade() {
        return activeTrade;
    }

    public int getCurrentVictoryPoints() {

        int victoryPoints = playerBoard.calculateVictoryPoints();      //add points given by development cards and path

        for(LeaderCard card : leaderCards)
            victoryPoints += card.getVictoryPoints();   //add points given by leader cards

        this.victoryPoints = victoryPoints;

        return victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints =victoryPoints;
    }

    public void pay(DevelopmentCard developmentCard) throws NotEnoughResourcesException, InvalidInputException {

        ArrayList<Resource> devCost = new ArrayList<>(developmentCard.getCost().size());
        for(Resource r : developmentCard.getCost()) devCost.add(r.clone());

        if(activeDiscount) {
            for(Resource r : devCost) {
                for(Discount d : discounts) {
                    r.update(d.getDiscountResource());
                }
            }
        }

        if(!playerBoard.possiblePayment(devCost))
            throw new NotEnoughResourcesException("Impossible transaction, not enough resources..");
        else {

            //TODO: verify discount, if there is one -> modify devCost
            playerBoard.payRequiredResources(devCost);
        }

    }

    public void handleNewDevCard(DevelopmentCard developmentCard, Stack<DevelopmentCard> developmentCardStack) throws InvalidInputException {
        playerBoard.getDevelopmentCardsArea().addDevCard(developmentCard, developmentCardStack);
    }

    public void handleMarketResources(Resource newResource, Shelf warehouseShelf) throws NotEnoughResourcesException, InvalidInputException {
        playerBoard.getWarehouse().addResourcesToShelf(newResource, warehouseShelf);
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public ArrayList<Marble> getExtraMarbles() {
        return extraMarbles;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public ArrayList<Trade> getExtraTrades() {
        return extraTrades;
    }

    public String getNickname() {
        return nickname;
    }

}
