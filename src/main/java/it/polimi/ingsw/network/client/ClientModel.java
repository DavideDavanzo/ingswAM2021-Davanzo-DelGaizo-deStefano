package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class implements a local version of the model.
 * In any case the ClientModel is only queried and no significant actions are performed on it.
 */
public class ClientModel implements CliPrinter {

    private Warehouse warehouse;
    private Coffer coffer;
    private Path faithTrack;
    private DevelopmentCardsArea developmentCardsArea;
    private LinkedList<LeaderCard> leaderCards;
    private LeaderCard firstLeader;
    private LeaderCard secondLeader;
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

    @Override
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("       ╔═════╗\n")
                .append("       ║ " + (warehouse.getFirstShelf().getShelfResource() != null ? warehouse.getFirstShelf().getShelfResource().getVolume() : " -") + " " + (warehouse.getFirstShelf().getShelfResource() != null ? warehouse.getFirstShelf().getShelfResource().print() : " ") + "║ " + "                       ┌─────"+ Color.ANSI_RED.escape() +"───────────────"+ Color.ANSI_WHITE.escape() +"─────────┐    ┌─────────┐ "+ Color.ANSI_RED.escape() +"   ┌─────────────────────────────┐"+ Color.ANSI_WHITE.escape() + "                 ╔════════════════════╗\n")
                .append("       ╚═════╝" + "                        │  " + faithTrack.crossValue(5) + Color.ANSI_RED.escape() + " │ " + faithTrack.crossValue(6) +  "  │ " + faithTrack.crossValue(7) + "  │ " + faithTrack.crossValue(8) + "  │ "+ Color.ANSI_WHITE.escape()+ faithTrack.crossValue(9) +"  │ " + faithTrack.crossValue(10) + " │    │  " + faithTrack.getPopeTokens().get(1).popeToken() +"  │ "+ Color.ANSI_RED.escape() +"   │ " + faithTrack.crossValue(19) + " │ "+ faithTrack.crossValue(20) + " │ " + faithTrack.crossValue(21) + " │ " + faithTrack.crossValue(22) +" │ " + faithTrack.crossValue(23) + " │ " + faithTrack.crossValue(24) + " │"+ Color.ANSI_WHITE.escape() + "                 ║ "  + coffer.getCoins().getVolume() + " " + coffer.getCoins().print() + "           " + coffer.getShields().getVolume() + " " + coffer.getShields().print() + "║ \n")
                .append("    ╔═══════════╗" + "                     │────"+ Color.ANSI_RED.escape() +"┌───────────────"+ Color.ANSI_WHITE.escape() +"────┐────│    │         │  "+ Color.ANSI_RED.escape() +"  │────┌────────────────────────┘"+ Color.ANSI_WHITE.escape() +"                 ║                    ║ \n")
                .append("    ║    " + (warehouse.getSecondShelf().getShelfResource() != null ? warehouse.getSecondShelf().getShelfResource().getVolume() : " -") + " " + (warehouse.getSecondShelf().getShelfResource() != null ? warehouse.getSecondShelf().getShelfResource().print() : " ") + "   ║" + "                     │ "+ faithTrack.crossValue(4) + "  │    ┌─────────┐    │ " + faithTrack.crossValue(11) + " │    └─────────┘    │ " + faithTrack.crossValue(18) + " │      ┌─────────┐ " + "                        ║ " + coffer.getServants().getVolume() + " " + coffer.getServants().print() + "           "                      + coffer.getStones().getVolume() + " " + coffer.getStones().print() + "║\n")
                .append("    ╚═══════════╝" + "         ┌───────────┘────│    │  " + faithTrack.getPopeTokens().get(0).popeToken() + "  │"+ Color.ANSI_RED.escape() +"    │────└───────────────────"+ Color.ANSI_WHITE.escape() +"┘────│      │  " + faithTrack.getPopeTokens().get(2).popeToken() + "  │" + "                         ╚════════════════════╝\n")
                .append(" ╔══════════════════╗" + "     │ " + faithTrack.crossValue(0) +" │ "    + faithTrack.crossValue(1) + " │ " + faithTrack.crossValue(2) + " │ " + faithTrack.crossValue(3) + "  │    │         │ "+ Color.ANSI_RED.escape() +"   │ " + faithTrack.crossValue(12) + " │ "+ faithTrack.crossValue(13) + " │ " + faithTrack.crossValue(14) +" │ "+ faithTrack.crossValue(15) + " │ " + faithTrack.crossValue(16) + " │ "+ Color.ANSI_WHITE.escape() +faithTrack.crossValue(17) + " │      │         │\n ")
                .append("║       " + (warehouse.getThirdShelf().getShelfResource() != null ? warehouse.getThirdShelf().getShelfResource().getVolume() : " -") + " " + (warehouse.getThirdShelf().getShelfResource() != null ? warehouse.getThirdShelf().getShelfResource().print() : " ") + "       ║" + "     └────────────────┘    └─────────┘"+ Color.ANSI_RED.escape() +"    └────────────────────────"+ Color.ANSI_WHITE.escape() +"─────┘      └─────────┘    \n")
                .append(" ╚══════════════════╝\n");

        if (warehouse.getExtraShelves() != null) {
            stringBuilder.append("Extra shelves:\n");
            for (Shelf extraShelf : warehouse.getExtraShelves()) {
                stringBuilder.append("   ╔═══════════════╗\n")
                        .append("   ║      " + (extraShelf.getShelfResource().getVolume()) + " " + extraShelf.getShelfResource().print() + "     ║\n")
                        .append("   ╚═══════════════╝\n");
            }
        }

        stringBuilder.append(developmentCardsArea.print());

        return stringBuilder.toString();
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

    public LeaderCard getFirstLeader() {
        return firstLeader;
    }

    public LeaderCard getSecondLeader() {
        return secondLeader;
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

    public void setLeadersOrdered() {
        firstLeader = leaderCards.get(0);
        secondLeader = leaderCards.get(1);
    }

    public Set<String> getUsernamesList() {
        return usernamesList;
    }

    public void setUsernamesList(Set<String> usernamesList) {
        this.usernamesList = usernamesList;
    }
}
