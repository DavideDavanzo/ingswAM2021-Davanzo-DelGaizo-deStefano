package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;

import java.util.ArrayList;

public class ClientModel {

    private Warehouse warehouse;
    private Coffer coffer;
    private Path faithTrack;
    private DevelopmentCardsArea developmentCardsArea;
    private ArrayList<LeaderCard> leaderCards;

    public ClientModel(){
        leaderCards = new ArrayList<>();
        warehouse = new Warehouse();
        coffer = new Coffer();
        faithTrack = new Path();
        developmentCardsArea = new DevelopmentCardsArea();
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Coffer getCoffer() {
        return coffer;
    }

    public Path getFaithTrack() {
        return faithTrack;
    }

    public DevelopmentCardsArea getDevelopmentCardsArea() {
        return developmentCardsArea;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
    }

    public synchronized void updateWarehouse(Warehouse warehouse){
        this.warehouse = warehouse;
    }

    public synchronized void updateCoffer(Coffer coffer){
        this.coffer = coffer;
    }

    public void updateDevCardsArea(DevelopmentCardsArea developmentCardsArea){
        this.developmentCardsArea = developmentCardsArea;
    }

    public synchronized void updateFaithTrack(Path path){
        this.faithTrack = path;
    }

}
