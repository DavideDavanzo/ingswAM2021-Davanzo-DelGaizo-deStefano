package it.polimi.ingsw.model;

import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;
import it.polimi.ingsw.model.lorenzo.LorenzoToken;
import it.polimi.ingsw.model.sharedarea.SharedArea;

import java.security.KeyStore;
import java.util.*;
import java.util.stream.Collectors;

public class Match {

    private LinkedList<Player> players = new LinkedList<>();
    private SharedArea sharedArea;
    private int turn;
    private Player currentPlayer;
    private ArrayList<Player> ranking;

    private boolean singlePlayer;
    private LorenzoIlMagnifico lorenzoIlMagnifico;


    public void initPlayers(){
        for(Player p: players){
            //p.giveLeaderCard(Stack<LeaderCard>)
        }
    }

   public boolean isSinglePlayer(){
       return singlePlayer;
   }


   public void addPlayer(Player p){
       players.add(p);
   }


   private void chooseOrder(){
       Random random = new Random();

       for(int i = 0; i < players.size(); i++){
           int index = i + random.nextInt(players.size() - i);

           Player temp = players.get(index);
           players.set(index, players.get(i));
           players.set(i, temp);
       }
   }

    public void createRanking() {
       ranking = new ArrayList<>();
       ranking.addAll(players);
       ranking.sort(Comparator.comparingInt(Player :: getCurrentVictoryPoints).reversed());
    }

    public void updateQueue() {
       Player p = players.remove();
       players.add(p);
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public SharedArea getSharedArea() {
        return sharedArea;
    }

    public int getTurn() {
        return turn;
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public Player getCurrentPlayer() {
        return players.peek();
    }

    public ArrayList<Player> getRanking() {
        return ranking;
    }
}
