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

    /**
     * Maximum number of players.
     */
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

    /**
     * Sets the game to SinglePlayer. Creates {@link LorenzoIlMagnifico}.
     */
    public void setToSinglePlayer() {
        this.lorenzoIlMagnifico = new LorenzoIlMagnifico(sharedArea.getCardMarket());
        this.singlePlayer = true;
    }

    /**
     * @return the complete leader card list.
     */
    private ArrayList<LeaderCard> createLeaders() {
        return new LeaderCardParser().parse();
    }

    public boolean isSinglePlayer(){
       return singlePlayer;
   }

    /**
     * Adds a player to the players' list.
     * @param p
     */
    public void addPlayer(Player p){
       players.add(p);
   }

    /**
     * Shuffles the players' list.
     */
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

    /**
     * @return a decreasing list of players, ordered by victory points.
     */
    public LinkedList<Player> getRanking() {
         LinkedList<Player> ranking = new LinkedList<>(players);
         ranking.sort(Comparator.comparingInt(Player::getCurrentVictoryPoints).reversed());
         ranking = tieBreakerCheck(ranking);
         return ranking;
    }

    /**
     * Checks for ties and resolves them with the number of resources logic.
     * If two players have the same score, the one with more total resources left win.
     * @param initRanking is the initial ranking, without tieBreaking logistics.
     * @return the new ranking.
     */
    public LinkedList<Player> tieBreakerCheck(LinkedList<Player> initRanking) {

        for(int i = 0 ; i < initRanking.size() - 1; i++) {
            for(int j = i + 1 ; j < initRanking.size(); j++) {
                if(initRanking.get(i).getCurrentVictoryPoints() == initRanking.get(j).getCurrentVictoryPoints()) {
                    if(initRanking.get(i).getPlayerBoard().getAllResourcePoints() < initRanking.get(j).getPlayerBoard().getAllResourcePoints()) {
                        Collections.swap(initRanking, i, j);
                    }
                }
            }

        }
        return initRanking;
    }

    public SharedArea getSharedArea() {
        return sharedArea;
    }

}
