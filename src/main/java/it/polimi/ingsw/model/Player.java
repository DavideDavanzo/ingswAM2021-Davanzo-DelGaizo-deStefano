package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.effects.Discount;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;

import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.sharedarea.SharedArea;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <h1>Player</h1>
 * Represents a player in a {@link Match}.
 */
public class Player {

    private String nickname;
    private PlayerBoard playerBoard;
    private SharedArea sharedArea;
    private ArrayList<LeaderCard> leaderCards;
    private boolean whiteMarblePower;
    private Marble extraMarble;
    private Discount discount;



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

    public DevelopmentCard buyDevCard(String color, int level) throws NotEnoughResourcesException, InvalidInputException {

        ArrayList<Resource> devCost = sharedArea.getCardMarket().getCard(color, level).getCost();

        if(!playerBoard.possiblePayment(devCost))
            throw new NotEnoughResourcesException("Impossible transaction");
        else {

            //TODO: verify discount, if there is one -> modify devCost
            playerBoard.payRequiredResources(devCost);

            return sharedArea.getCardMarket().takeCard(color, level);
        }

    }

    public void putDevCardOnBoard(DevelopmentCard developmentCard, Stack<DevelopmentCard> developmentCardStack) throws InvalidInputException {
        playerBoard.getDevelopmentCardsArea().addDevCard(developmentCard, developmentCardStack);
    }

    public ArrayList<Item> takeResourcesFromMarket(char rowOrColumn, int index) throws IllegalChoiceException {
        return sharedArea.getMarket().getResources(rowOrColumn, index, this);
    }

    public void putMarketResourcesInWarehouse(Resource newResource, Shelf warehouseShelf) throws NotEnoughResourcesException, InvalidInputException {
        playerBoard.getWarehouse().addResourcesToShelf(newResource, warehouseShelf);
    }

    public PlayerBoard getPlayerBoard() { return playerBoard; }

    public Marble getExtraMarble() { return extraMarble; }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

}
