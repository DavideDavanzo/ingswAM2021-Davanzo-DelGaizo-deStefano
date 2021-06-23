package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.market.Market;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class ClientModel {

    private Warehouse warehouse;
    private Coffer coffer;
    private Path faithTrack;
    private DevelopmentCardsArea developmentCardsArea;
    private LinkedList<LeaderCard> leaderCards;
    private Market market;
    private CardMarket cardMarket;
    private boolean myTurn;
    private int lorenzoPosition;
    private boolean singlePlayer;
    private Set<String> usernamesList;

    public ClientModel(){
        leaderCards = new LinkedList<>();
        warehouse = new Warehouse();
        coffer = new Coffer();
        faithTrack = new Path();
        developmentCardsArea = new DevelopmentCardsArea();
        myTurn = false;
        lorenzoPosition = 0;
    }

    public ClientModel(LinkedList<LeaderCard> leaderCards, Warehouse warehouse, Coffer coffer, Path path, DevelopmentCardsArea developmentCardsArea){
        this.leaderCards = leaderCards;
        this.warehouse = warehouse;
        this.coffer = coffer;
        this.faithTrack = path;
        this.developmentCardsArea = developmentCardsArea;
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

    public int getLorenzoPosition() {
        return lorenzoPosition;
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

    public void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public synchronized void updateFaithTrack(Path path){
        this.faithTrack = path;
    }

    public void updateMarket(Market market) {this.market = market;}

    public void updateCardMarket(CardMarket cardMarket) {this.cardMarket = cardMarket;}

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void setLorenzoPosition(int lorenzoPosition){
        this.lorenzoPosition = lorenzoPosition;
    }

    public Set<String> getUsernamesList() {
        return usernamesList;
    }

    public void setUsernamesList(Set<String> usernamesList) {
        this.usernamesList = usernamesList;
    }
}
