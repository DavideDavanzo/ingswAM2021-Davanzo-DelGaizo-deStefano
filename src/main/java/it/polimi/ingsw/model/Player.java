package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.Discount;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.sharedarea.market.Marble;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;

import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.observingPattern.Observable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * <h1>Player</h1>
 * Represents a player in a {@link Match}.
 */
public class Player extends Observable {

    private String nickname;

    private PlayerBoard playerBoard;
    private boolean inkwell;

    private LinkedList<LeaderCard> leaderCards;

    private ArrayList<Marble> extraMarbles;
    private boolean whiteMarblePower;

    private ArrayList<Discount> discounts;
    private boolean activeDiscount;

    private ArrayList<Trade> extraTrades;
    private boolean activeTrade;

    private ArrayList<Item> itemsToArrangeInWarehouse;
    private boolean bigActionToken;

    private int victoryPoints;

    /**
     * Default Constructor
     */
    public Player() {
        playerBoard = new PlayerBoard();
        leaderCards = new LinkedList<>();
        inkwell = false;
        whiteMarblePower = false;
        activeDiscount = false;
        activeTrade = false;
        bigActionToken = true;
        victoryPoints = 0;
    }

    /**
     * Nickname Constructor
     * @param nickname
     */
    public Player(String nickname) {
        playerBoard = new PlayerBoard();
        leaderCards = new LinkedList<>();
        this.nickname = nickname;
        inkwell = false;
        whiteMarblePower = false;
        activeDiscount = false;
        activeTrade = false;
        bigActionToken = true;
        victoryPoints = 0;
    }

    /**
     * Gives a specified leader card to the player.
     * @param leader is the specified leader card.
     */
    public void giveLeaderCard(LeaderCard leader) {
        leaderCards.add(leader);
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

    /**
     * Re-calculates the number of victory points and updates the victory point count.
     * @return the updated number of victory points.
     */
    public int getCurrentVictoryPoints() {

        int victoryPoints = playerBoard.calculateVictoryPoints();      //add points given by development cards, path and resources

        for(LeaderCard card : leaderCards)
            victoryPoints += card.calculateVictoryPoints();   //add points given by leader cards

        this.victoryPoints = victoryPoints;

        return victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints =victoryPoints;
    }

    /**
     * @param itemsToArrangeInWarehouse is a temporary list of items that the player has to arrange in his {@link Warehouse}.
     */
    public void setItemsToArrangeInWarehouse(ArrayList<Item> itemsToArrangeInWarehouse) {
        this.itemsToArrangeInWarehouse = itemsToArrangeInWarehouse;
    }

    /**
     * The player pays for a specific Development Card.
     * @param developmentCard
     * @throws NotEnoughResourcesException if the player could not pay the card.
     * @throws InvalidInputException
     */
    public void pay(DevelopmentCard developmentCard) throws NotEnoughResourcesException, InvalidInputException {

        ArrayList<Resource> devCost = new ArrayList<>(developmentCard.getCost().size());
        for(Resource r : developmentCard.getCost()) devCost.add(r.clone());

        if(activeDiscount) {
            for(Resource r : devCost) {
                for(Discount d : discounts) {
                    try {
                        r.update(d.getDiscountResource());
                    } catch (NotEnoughResourcesException e) {
                        r.setVolume(0);
                    }
                }
            }
        }

        if(!playerBoard.possiblePayment(devCost))
            throw new NotEnoughResourcesException("Impossible transaction, not enough resources..");
        else {
            playerBoard.payRequiredResources(devCost);
        }

    }

    /**
     * Adds a new development card to to the player's {@link it.polimi.ingsw.model.sharedarea.SharedArea}.
     * @param developmentCard
     * @param developmentCardStack is the stack where the card will be placed.
     * @throws InvalidInputException
     */
    public void handleNewDevCard(DevelopmentCard developmentCard, Stack<DevelopmentCard> developmentCardStack) throws InvalidInputException {
        playerBoard.getDevelopmentCardsArea().addDevCard(developmentCard, developmentCardStack);
    }

    /**
     * Adds a new resource to to the player's {@link Warehouse}.
     * @param newResource
     * @param warehouseShelf is the specific warehouse shelf.
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException
     */
    public void handleMarketResources(Resource newResource, Shelf warehouseShelf) throws NotEnoughResourcesException, InvalidInputException {
        playerBoard.getWarehouse().addResourcesToShelf(newResource, warehouseShelf);
    }

    /**
     * Moves a player forward by a specific number of steps (max. position 24).
     * @param steps
     * @return true if the player reaches the last square, enabling EndGame.
     * @throws InvalidInputException
     */
    public boolean moveForward(int steps) throws InvalidInputException {
        return this.getPlayerBoard().getPath().moveForward(steps);
    }

    public int devCardCount() {
        return playerBoard.getDevelopmentCardsArea().getCardCount();
    }

    /**
     * @return true if the player has two white marble powers. See: {@link WhiteMarbleEffect}.
     */
    public boolean hasTwoWhiteMarblePowers() {
        int count = 0;
        for(LeaderCard l : leaderCards) {
            if(l.isActive() && (l.getEffect() instanceof WhiteMarbleEffect)) count++;
        }
        return count == 2;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public ArrayList<Marble> getExtraMarbles() {
        return extraMarbles;
    }

    public LinkedList<LeaderCard> getLeaderCards() {
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

    public Warehouse getWarehouse() {
        return this.getPlayerBoard().getWarehouse();
    }

    public ArrayList<Item> getItemsToArrangeInWarehouse() {
        return itemsToArrangeInWarehouse;
    }

    /**
     * @return the number of extra shelves. See: {@link ExtraShelfEffect}
     */
    public int extraShelvesCount() {
        int count = 0;
        for(LeaderCard l : getLeaderCards()) {
            if(l.getEffect() instanceof ExtraShelfEffect && l.isActive()) count++;
        }
        return count;
    }

    public void giveBigActionToken() {
        bigActionToken = true;
    }

    public boolean hasBigActionToken() {
        return bigActionToken;
    }

    public void revokeBigActionToken() {
        bigActionToken = false;
    }

    public boolean hasInkwell() {
        return inkwell;
    }

    public void giveInkwell() {
        inkwell = true;
    }

    public void setInkwell(boolean inkwell) {
        this.inkwell = inkwell;
    }
}
