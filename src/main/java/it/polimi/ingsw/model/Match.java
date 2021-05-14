package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.lorenzo.LorenzoIlMagnifico;
import it.polimi.ingsw.model.sharedarea.SharedArea;
import it.polimi.ingsw.network.observingPattern.Observable;

import java.util.*;

public class Match extends Observable {

    public static final int MAX_PLAYERS = 4;

    private LinkedList<Player> players;
    private SharedArea sharedArea;
    private int turn;
    private Player currentPlayer;
    private ArrayList<LeaderCard> leaders;

    private boolean singlePlayer;
    private LorenzoIlMagnifico lorenzoIlMagnifico;

    private int chosenPlayerNumber;

    public Match() {
        this.players = new LinkedList<>();
        this.sharedArea = new SharedArea();
        this.leaders = createLeaders();
        this.turn = 1;
        this.chosenPlayerNumber = 0;
    }

    public void setToSinglePlayer() {
        this.lorenzoIlMagnifico = new LorenzoIlMagnifico();
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

    public boolean setChosenPlayerNumber(int number) {
        if(number > 0 && number <= MAX_PLAYERS) {
            this.chosenPlayerNumber = number;
            if(chosenPlayerNumber == 1) singlePlayer = true;
            return true;
        }

        return false;

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

    public void updateQueue() {
         Player p = players.remove();
         players.add(p);
    }



    public LinkedList<Player> getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
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

    public boolean isFull() {
        return chosenPlayerNumber != 0 && chosenPlayerNumber == players.size();
    }

    public void nextTurn() {
        turn++;
    }

}
