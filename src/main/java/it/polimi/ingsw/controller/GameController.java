package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.controller.gameState.LoginState;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.controllerExceptions.NicknameException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PlayersListMessage;
import it.polimi.ingsw.network.messages.WarehouseUpdate;
import it.polimi.ingsw.network.messages.WinMessage;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.observingPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

/**
 * This class implements a Controller bind to a {@link Match}.
 * Communicates with the Model and the players' {@link VirtualView}(s).
 * It contains the Match and the current {@link GameState}.
 */
public class GameController extends Observable implements Observer, Serializable {

    private Match match;
    private Map<String, VirtualView> virtualViewMap;

    private int chosenPlayerNum;
    private boolean endGame;

    private GameState gameState;
    private TurnController turnController;

    private Stack<ArrayList<LeaderCard>> leaderChoices;

    public GameController() {
        this.chosenPlayerNum = 0;
        this.endGame = false;
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.gameState = new LoginState(this);
        this.leaderChoices = new Stack<>();
    }

    /**
     * Receives and forwards a {@link Message}
     * to the {@link GameState} that processes it.
     * @param received is the Message.
     */
    public void onMessage(Message received) {

        try {
            received.getProcessedBy(gameState);
        } catch (InvalidStateException e) {
            // Handled by GameState
        }

    }

    /**
     * Logs the player and notifies if the login is successful or
     * unsuccessful (the Nickname is already taken by another User).
     * @param nickname is given by the player.
     * @param virtualView is created by the {@link it.polimi.ingsw.network.server.Server}.
     * @return true only if the login is successful.
     */
    public synchronized boolean logPlayer(String nickname, VirtualView virtualView) {

        while(!virtualViewMap.isEmpty() && (chosenPlayerNum == 0)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(virtualViewMap.isEmpty()) {

            try {
                addVirtualView(nickname, virtualView);
                virtualView.connect();
            } catch (NicknameException e) { //This catch will never happen, it's explicit just for code richness.
                virtualView.showLogin("error", false);
                return false;
            }

            //TO RECEIVE INCOMING MESSAGES
            virtualView.addObserver(this);
            virtualView.start();

            virtualView.showLogin("You've been logged in successfully", true);
            virtualView.askNumberOfPlayers();

        }
        else if(!isFull()) {

            try {
                addVirtualView(nickname, virtualView);
                virtualView.connect();
            } catch (NicknameException e) {
                virtualView.showLogin("Nickname already logged, try another one", false);
                return false;
            }

            //TO RECEIVE INCOMING MESSAGES
            virtualView.addObserver(this);
            virtualView.start();
            virtualView.showLogin("You've been logged in successfully", true);

            if(isFull()) {
                gameState.nextState();
                startMatch();
            }

        }
        else if(isFull()){
            virtualView.showLogin("Sorry, something went wrong", false);
            return false;
        }
        else {
            virtualView.showError("Sorry, something went wrong..");
        }

        return true;

    }

    /**
     * Handles reconnection sending back all the reconnected
     * player's features and inserting him back into the game.
     * @param nickname
     * @param virtualView
     */
    public void reconnect(String nickname, VirtualView virtualView) {
        virtualView.connect();
        virtualViewMap.put(nickname, virtualView);
        virtualView.addObserver(this);
        virtualView.start();
        for(Player p : match.getPlayers()){
            if(p.getNickname().equals(nickname)){
                match.addObserver(virtualViewMap.get(nickname));
                p.getPlayerBoard().getWarehouse().addObserver(virtualViewMap.get(nickname));
                p.getPlayerBoard().getCoffer().addObserver(virtualViewMap.get(nickname));
                p.getPlayerBoard().getDevelopmentCardsArea().addObserver(virtualViewMap.get(nickname));
                p.getPlayerBoard().getPath().addObserver(virtualViewMap.get(nickname));
                virtualView.updateFaithTrack(p.getPlayerBoard().getPath());
                virtualView.updateDevCards(p.getPlayerBoard().getDevelopmentCardsArea());
                virtualView.updateWarehouse(p.getPlayerBoard().getWarehouse());
                virtualView.updateCoffer(p.getPlayerBoard().getCoffer());
                virtualView.updateLeaderCards(p.getLeaderCards());
                break;
            }
        }
        virtualView.showLogin("Reconnection to match completed. Wait for your turn...", true);
    }

    public boolean verifyConnected(String nickname) {
        return virtualViewMap.get(nickname).isConnected();
    }

    /**
     * Starts a match, adding {@link Observer}(s) and giving the first player
     * an inkwell.
     * Creates a {@link TurnController} to handle turn logistics.
     */
    public void startMatch() {
        System.out.println("Starting match..."); //Server side message.

        match = new Match();
        for(Map.Entry<String, VirtualView> entry : virtualViewMap.entrySet()) {
            Player p = new Player(entry.getKey());
            match.addPlayer(p);
            match.addObserver(entry.getValue());
            match.getSharedArea().getMarket().addObserver(entry.getValue());
            match.getSharedArea().getCardMarket().addObserver(entry.getValue());
            p.getPlayerBoard().getWarehouse().addObserver(entry.getValue());
            p.getPlayerBoard().getCoffer().addObserver(entry.getValue());
            p.getPlayerBoard().getDevelopmentCardsArea().addObserver(entry.getValue());
            p.getPlayerBoard().getPath().addObserver(entry.getValue());
            p.getPlayerBoard().getPath().addObserver(this);
        }
        match.getSharedArea().getMarket().notifyObservers(match.getSharedArea().getMarket());
        match.getSharedArea().getCardMarket().notifyObservers(match.getSharedArea().getCardMarket());

        if(isSinglePlayer()) match.setToSinglePlayer();
        else match.shufflePlayers();
        getPlayers().peek().giveInkwell(); //Gives the inkwell to the first player

        turnController = new TurnController(this, virtualViewMap);
        turnController.updateTurnCounter();
        prepareLeaderChoices();

        if(virtualViewMap.size() !=1 )
            sendBroadcastMessage("Match started!\nPlayers: " + virtualViewMap.keySet() + "\n" + turnController.getCurrentPlayer().getNickname() + " is the first player");
        sendBroadcastMessage(new PlayersListMessage(virtualViewMap.keySet()));
        askLeaders();

    }

    /**
     * Sends a leader card request to the current player.
     */
    public void askLeaders() {
        virtualViewMap.get(turnController.getCurrentPlayer().getNickname()).askLeaders(leaderChoices.pop());
    }

    /**
     * SinglePlayer only.
     * Flips a {@link it.polimi.ingsw.model.lorenzo.LorenzoToken} and sends the
     * message to the player.
     * @throws LossException if one of the Loss conditions are reached.
     */
    public void flipActionToken() throws LossException {
        try {
            String action = match.getLorenzoIlMagnifico().flipTokenReadAction();
            virtualViewMap.get(getCurrentPlayer().getNickname()).showMessage(action);
        } catch (LossException e) {
            lorenzoWins(e.getMessage());
            throw new LossException("");
        }
    }

    /**
     * Declares Lorenzo as the winner and sends score and position.
     * @param lossMessage for the user.
     */
    private void lorenzoWins(String lossMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(lossMessage).append("Score : ").append(getCurrentPlayer().getCurrentVictoryPoints()).append("\n").append("Position : ").append(getCurrentPlayer().getPlayerBoard().getPath().getCurrentPositionAsInt());
        VirtualView currentView = virtualViewMap.get(getCurrentPlayer().getNickname());
        currentView.sendMessage(new WinMessage(sb.toString(), true));
        notifyObservers(virtualViewMap.keySet());
    }

    /**
     * Prepares the right number (# match players) of leader cards stacks,
     * 4 cards for every stack, from which the initial leader card choice can be performed.
     */
    private void prepareLeaderChoices() {

        ArrayList<LeaderCard> leaderCards = match.getLeaders();
        Collections.shuffle(leaderCards);

        for(int players = 0; players < chosenPlayerNum; players++) {

            ArrayList<LeaderCard> choice = new ArrayList<>();

            for(int i = 0; i < 4; i++) {
                choice.add(leaderCards.get(i + players * 4));
            }

            leaderChoices.push(choice);

        }

    }

    /**
     * Creates the ranking, sending it to the players and declares the winner.
     * If SinglePlayer, the match is won by the player.
     */
    public void handleEndGame() {

        if(isSinglePlayer()) {
            VirtualView currentView = virtualViewMap.get(getCurrentPlayer().getNickname());
            currentView.sendMessage(new WinMessage(singlePlayerWinMessage(), false));
            notifyObservers(virtualViewMap.keySet());
            return;
        }

        LinkedList<Player> ranking = match.getRanking();

        HashMap<String, Integer> rankingTable = new HashMap<>();

        for(Player p : ranking){
            rankingTable.put(p.getNickname(), p.getVictoryPoints());
        }

        LinkedList<String> rankingUsernames = new LinkedList<>();
        for(Object nickname : ranking.stream().map(Player::getNickname).toArray()){
            rankingUsernames.add((String) nickname);
        }

        sendBroadcastMessage(new WinMessage("Match Ended!", rankingTable, rankingUsernames));

        notifyObservers(virtualViewMap.keySet());

    }

    /**
     * Creates the SinglePlayer winning message as a String, specifies
     * position and score.
     * @return
     */
    public String singlePlayerWinMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("You Won!");
        sb.append("Score : " + getCurrentPlayer().getCurrentVictoryPoints() + "\n" + "Position : " + getCurrentPlayer().getPlayerBoard().getPath().getCurrentPositionAsInt());
        return sb.toString();
    }

    /**
     * Moves all the crosses of the players except the one of the specified player.
     * @param player is the player that will not move.
     * @param steps number of steps moved.
     * @return true if the EndGame phase is reached (see game's rules).
     */
    public boolean moveAllExcept(Player player, int steps) {

        String nickname = player.getNickname();
        boolean maxMovement = false;

        for(Player other : getPlayers()) {
            if(!other.getNickname().equals(nickname)) {
                try {
                    if(!virtualViewMap.get(other.getNickname()).isConnected()) continue;
                    if(other.moveForward(steps)) maxMovement = true;
                } catch (InvalidInputException e) {
                    //Should never reach this statement
                    e.printStackTrace();
                }
            }
        }

        return maxMovement;
    }

    /**
     * Adds a {@link VirtualView} to the {@link #virtualViewMap}.
     * @param nickname
     * @param virtualView
     * @throws NicknameException if the nickname is already taken.
     */
    public void addVirtualView(String nickname, VirtualView virtualView) throws NicknameException {
        if(virtualViewMap.containsKey(nickname)) throw new NicknameException();
        virtualViewMap.put(nickname, virtualView);
    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return virtualViewMap;
    }

    /**
     * Sends a String message to all the {@link VirtualView}(s).
     * @param message
     */
    public void sendBroadcastMessage(String message) {
        for(VirtualView v : virtualViewMap.values()) {
            if(v.isConnected()) v.showMessage(message);
        }
    }

    /**
     * Sends a String message to all the {@link VirtualView}(s).
     * excluding the specified nickname.
     * @param message
     */
    public void sendBroadcastMessageExclude(String message, String nickname) {
        for(Map.Entry<String, VirtualView> entry : virtualViewMap.entrySet()) {
            if(!entry.getKey().equals(nickname) && entry.getValue().isConnected()) {
                entry.getValue().showMessage(message);
            }
        }
    }

    /**
     * Sends a {@link Message} to all the {@link VirtualView}(s).
     * @param message
     */
    public void sendBroadcastMessage(Message message){
        for(VirtualView v : virtualViewMap.values()) {
            if(v.isConnected()) v.sendMessage(message);
        }
    }

    /**
     * @return the number of connected players to the match.
     */
    public synchronized long connectedPlayersAsInt() {
        return virtualViewMap.values().stream().filter(VirtualView::isConnected).count();
    }

    /**
     * Handles disconnection of the user who matches the
     * nickname parameter. Gives the inkwell to the next connected player
     * of the list.
     * @param nickname name of the disconnected player.
     */
    public synchronized void disconnect(String nickname) {
        int count = 1;
        for(Player p : getPlayers()) {
            if(p.getNickname().equals(nickname) && p.hasInkwell()) {
                try {
                    while(!virtualViewMap.get(getPlayers().get(getPlayers().indexOf(p) + count).getNickname()).isConnected()) {
                        count++;
                    }
                    p.setInkwell(false);
                    getPlayers().get(getPlayers().indexOf(p) + count).giveInkwell();
                } catch(IndexOutOfBoundsException ie) {
                    count = 0;
                    while(!virtualViewMap.get(getPlayers().get(count).getNickname()).isConnected()) {
                        count++;
                    }
                    p.setInkwell(false);
                    getPlayers().get(count).giveInkwell();
                } finally {
                    virtualViewMap.get(nickname).disconnect();
                }
            }
        }
        virtualViewMap.get(nickname).disconnect();
    }

    public boolean isCurrentPlayer(String username) {
        return turnController.isCurrentPlayer(username);
    }

    public Player getCurrentPlayer() {
        return turnController.getCurrentPlayer();
    }

    /**
     * @return true if the game reached it's capacity.
     */
    public boolean isFull() {
        return chosenPlayerNum != 0 && chosenPlayerNum == virtualViewMap.size();
    }

    public boolean isSinglePlayer() {
        return chosenPlayerNum == 1 && virtualViewMap.size() == 1;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Sets the chosen number of players.
     * @param number
     * @return false if the number doesn't respect the 1-4 range.
     */
    public boolean setChosenPlayersNum(int number) {

        if(number > 0 && number <= Match.MAX_PLAYERS) {
            this.chosenPlayerNum = number;
            return true;
        }

        return false;

    }

    /**
     * Updates the match queue of players after a turn is completed.
     */
    public void updateQueue() {
        turnController.updateQueue();
    }

    @Override
    public void update(Message message) {
        onMessage(message);
    }

    @Override
    public void update(Warehouse warehouse) {
        //do nothing
    }

    @Override
    public void update(Path path) {
        //do nothing
    }

    @Override
    public void update(Coffer coffer) {
        //do nothing
    }

    @Override
    public void update(DevelopmentCardsArea developmentCardsArea) {
        //do nothing
    }

    @Override
    public void update(Market market) {
        //do nothing
    }

    @Override
    public void update(CardMarket cardMarket) {
        //do nothing
    }

    /**
     * When a player moves, he could reach a Vatican Square.
     * This method applies Vatican Report check and validation to all the players.
     * @param pathPosition
     */
    @Override
    public void update(int pathPosition) {
        match.getPlayers().forEach(p -> p.getPlayerBoard().getPath().applyVaticanReport(pathPosition));
    }

    @Override
    public void update(Set<String> usernames) {
        //do nothing
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public LinkedList<Player> getPlayers() {
        return match.getPlayers();
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public int getChosenPlayerNum() {
        return chosenPlayerNum;
    }

    public Match getMatch() {
        return match;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public GameState getGameState() {
        return gameState;
    }
}
