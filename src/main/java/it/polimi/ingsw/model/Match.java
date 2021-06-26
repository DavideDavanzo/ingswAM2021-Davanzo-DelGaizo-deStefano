package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;
import it.polimi.ingsw.model.sharedarea.SharedArea;
import it.polimi.ingsw.observingPattern.Observable;

import java.util.*;

/**
 * This class implements a match
 */
public class Match extends Observable {

    public static final int MAX_PLAYERS = 4;

    private LinkedList<Player> players;
    private SharedArea sharedArea;
    private ArrayList<LeaderCard> leaders;

    private boolean singlePlayer;
    private LorenzoIlMagnifico lorenzoIlMagnifico;

    public Match() {
        this.players = new LinkedList<>();
        this.sharedArea = new SharedArea();
        this.leaders = createLeaders();
    }

    public void setToSinglePlayer() {
        this.lorenzoIlMagnifico = new LorenzoIlMagnifico(sharedArea.getCardMarket());
        this.singlePlayer = true;
    }

    private ArrayList<LeaderCard> createLeaders() {
        return new LeaderCardParser().parse();
    }

    public boolean isSinglePlayer(){
       return singlePlayer;
   }

    public void addPlayer(Player p){
       players.add(p);
   }

    public void shufflePlayers(){
         Random random = new Random();

         for(int i = 0; i < players.size(); i++){

             int index = i + random.nextInt(players.size() - i);

             Player temp = players.get(index);
             players.set(index, players.get(i));
             players.set(i, temp);
       }
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public ArrayList<LeaderCard> getLeaders() {
        return leaders;
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public Player getCurrentPlayer() {
        return players.peek();
    }

    public LinkedList<Player> getRanking() {
         LinkedList<Player> ranking = new LinkedList<>(players);
         ranking.sort(Comparator.comparingInt(Player::getCurrentVictoryPoints).reversed());
         return ranking;
    }

    public SharedArea getSharedArea() {
        return sharedArea;
    }

}
