package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.Warehouse;

import java.util.ArrayList;

public class ClientModel {

    private PlayerBoard playerBoard;
    private ArrayList<LeaderCard> leaderCards;

    public ClientModel(){
        playerBoard = new PlayerBoard();
        leaderCards = new ArrayList<>();
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
    }

    public void updateWarehouse(Warehouse warehouse){
        playerBoard.setWarehouse(warehouse);
    }

    public void updateCoffer(Coffer coffer){
        playerBoard.setCoffer(coffer);
    }

    public void updateDevCardsArea(DevelopmentCardsArea developmentCardsArea){
        playerBoard.setDevelopmentCardsArea(developmentCardsArea);
    }

}
