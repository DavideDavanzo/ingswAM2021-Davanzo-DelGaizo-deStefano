package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.ClientView;

import java.util.HashMap;
import java.util.LinkedList;

public class WinMessage extends Message {

    boolean lorenzoWins;

    private HashMap<String, Integer> rankingTable;

    private LinkedList<String> ranking;

    public WinMessage() {

    }

    public WinMessage(String msg, boolean lorenzoWins) {
        super(msg);
        this.lorenzoWins = lorenzoWins;
    }

    public WinMessage(String msg, HashMap rankingTable, LinkedList<String> ranking) {
        super(msg);
        this.rankingTable = rankingTable;
        this.ranking = ranking;
    }

    @Override
    public void apply(ClientView view) {
        view.endGame(this);
    }

    public boolean isLorenzoWins() {
        return lorenzoWins;
    }

    public void setLorenzoWins(boolean lorenzoWins) {
        this.lorenzoWins = lorenzoWins;
    }

    public HashMap<String, Integer> getRankingTable() {
        return rankingTable;
    }

    public void setRankingTable(HashMap<String, Integer> rankingTable) {
        this.rankingTable = rankingTable;
    }

    public LinkedList<String> getRanking() {
        return ranking;
    }

    public void setRanking(LinkedList<String> ranking) {
        this.ranking = ranking;
    }
}
