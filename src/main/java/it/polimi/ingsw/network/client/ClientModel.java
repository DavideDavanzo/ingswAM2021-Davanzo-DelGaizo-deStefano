package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.market.Market;

import java.util.ArrayList;
import java.util.LinkedList;

public class ClientModel {

    private Warehouse warehouse;
    private Coffer coffer;
    private Path faithTrack;
    private DevelopmentCardsArea developmentCardsArea;
    private LinkedList<LeaderCard> leaderCards;
    private Market market;
    private CardMarket cardMarket;

    public ClientModel(){
        leaderCards = new LinkedList<>();
        warehouse = new Warehouse();
        coffer = new Coffer();
        faithTrack = new Path();
        developmentCardsArea = new DevelopmentCardsArea();
        market = new Market();
        cardMarket = new CardMarket();
    }

    public LinkedList<LeaderCard> getLeaderCards() {
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

    public Market getMarket() {
        return market;
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public void setLeaderCards(LinkedList<LeaderCard> leaderCards){
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

    public synchronized void update(Market market) {this.market = market;}

    public synchronized void update(CardMarket cardMarket) {this.cardMarket = cardMarket;}
}
