package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;

import java.util.ArrayList;

public class ClientModel {

    private String warehouse;
    private String coffer;
    private String faithTrack;
    private String developmentCardsArea;
    private ArrayList<LeaderCard> leaderCards;

    public ClientModel(){
        leaderCards = new ArrayList<>();
        warehouse = (new Warehouse().print());
        coffer = (new Coffer().print());
        faithTrack = (new Path()).print();
        developmentCardsArea = (new DevelopmentCardsArea()).print();
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public String getCoffer() {
        return coffer;
    }

    public String getFaithTrack() {
        return faithTrack;
    }

    public String getDevelopmentCardsArea() {
        return developmentCardsArea;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
    }

    public void updateWarehouse(String warehouse){
        this.warehouse = warehouse;
    }

    public void updateCoffer(String coffer){
        this.coffer = coffer;
    }

    public void updateDevCardsArea(String developmentCardsArea){
        this.developmentCardsArea = developmentCardsArea;
    }

    public void updateFaithTrack(String path){
        this.faithTrack = path;
    }

}
