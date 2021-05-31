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
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observingPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class GameController implements Observer, Serializable {

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

    public void onMessage(Message received) {

        try {
            received.getProcessedBy(gameState);
        } catch (InvalidStateException e) {
            // Handled by GameState
        }

    }

    public synchronized void logPlayer(String nickname, VirtualView virtualView) throws Exception {

        while(!virtualViewMap.isEmpty() && (chosenPlayerNum == 0))
            wait();

        if(virtualViewMap.isEmpty()) {

            try {
                addVirtualView(nickname, virtualView);
            } catch (NicknameException e) { //This catch will never happen, it's explicit just for code richness.
                virtualView.showLogin("error", false);
                return;
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
            } catch (NicknameException e) {
                virtualView.showLogin("Nickname already logged, try another one", false);
                return;
            }

            //TO RECEIVE INCOMING MESSAGES
            virtualView.addObserver(this);
            virtualView.start();
            virtualView.showLogin("You've been logged in successfully", true);

            if(isFull()) {
                //TODO: Restore interrupted match ??
                gameState.nextState();
                startMatch();
            }

        }
        else if(isFull()){
            virtualView.showLogin("Sorry, something went wrong", false);
            return;
        }
        else {
            virtualView.showError("Sorry, something went wrong..");
        }

        throw new Exception();

    }

    public void startMatch() {
        System.out.println("Starting match..."); //Server side message.

        match = new Match();
        for(Map.Entry<String, VirtualView> entry : virtualViewMap.entrySet()) {
            Player p = new Player(entry.getKey());
            match.addPlayer(p);
            match.addObserver(entry.getValue());
            p.getPlayerBoard().getWarehouse().addObserver(entry.getValue());
            p.getPlayerBoard().getCoffer().addObserver(entry.getValue());
            p.getPlayerBoard().getDevelopmentCardsArea().addObserver(entry.getValue());
            p.getPlayerBoard().getPath().addObserver(entry.getValue());
            p.getPlayerBoard().getPath().addObserver(this);
        }

        if(isSinglePlayer()) match.setToSinglePlayer();
        else match.shufflePlayers();
        getPlayers().peek().giveInkwell(); //Gives the inkwell to the first player

        turnController = new TurnController(this, virtualViewMap);
        turnController.updateTurnCounter();
        prepareLeaderChoices();

        sendBroadcastMessage("Match started! " + turnController.getCurrentPlayer().getNickname() + " is the first player");
        askLeaders();

    }

    public void askLeaders() {
        virtualViewMap.get(turnController.getCurrentPlayer().getNickname()).askLeaders(leaderChoices.pop());
    }

    public void flipActionToken() throws LossException {
        try {
            String action = match.getLorenzoIlMagnifico().flipTokenReadAction();
            virtualViewMap.get(getCurrentPlayer().getNickname()).showMessage(action);
        } catch (LossException e) {
            lorenzoWins(e.getMessage());
            throw new LossException("");
        }
    }

    private void lorenzoWins(String lossMessage) {
        VirtualView currentView = virtualViewMap.get(getCurrentPlayer().getNickname());
        currentView.showMessage(lossMessage); //TODO: Use a message for loss ??
        currentView.showMessage("Score : " + getCurrentPlayer().getVictoryPoints() + "\n" + "Position : " + getCurrentPlayer().getPlayerBoard().getPath().getCurrentPositionAsInt());
    }

    private void prepareLeaderChoices() {

        ArrayList<LeaderCard> leaderCards = match.getLeaders();
        Collections.shuffle(leaderCards);

        for(int players = 0; players < chosenPlayerNum; players++) {

            ArrayList<LeaderCard> choice = new ArrayList<>();

            for(int i = 0; i < 4; i++) {
                choice.add(leaderCards.get(i + players * chosenPlayerNum));
            }

            leaderChoices.push(choice);

        }

    }

    public void handleEndGame() {

        if(isSinglePlayer()) {
            VirtualView currentView = virtualViewMap.get(getCurrentPlayer().getNickname());
            currentView.showMessage("You Won!");
            currentView.showMessage("Score : " + getCurrentPlayer().getVictoryPoints() + "\n" + "Position : " + getCurrentPlayer().getPlayerBoard().getPath().getCurrentPositionAsInt());
            return;
        }

        LinkedList<Player> ranking = match.getRanking();
        Player winner = ranking.peek();
        VirtualView winnerView = virtualViewMap.get(winner.getNickname());
        HashMap<String, Integer> completeRanking = new HashMap<>();

        for(Player p : ranking) completeRanking.put(p.getNickname(), p.getVictoryPoints());
        for(int i = 0; i < 20; i++) winnerView.showMessage("YOU WON !!!");

        sendBroadcastMessageExclude(winner.getNickname() + " Won!", winner.getNickname());
        sendBroadcastMessage(completeRanking.toString());

    }

    public boolean moveAllExcept(Player player, int steps) {

        String nickname = player.getNickname();
        boolean maxMovement = false;

        for(Player other : getPlayers()) {
            if(!other.getNickname().equals(nickname)) {
                try {
                    if(other.moveForward(steps)) maxMovement = true;
                } catch (InvalidInputException e) {
                    //Should never reach this statement
                    e.printStackTrace();
                }
            }
        }

        return maxMovement;
    }

    public void addVirtualView(String nickname, VirtualView virtualView) throws NicknameException {
        if(virtualViewMap.containsKey(nickname)) throw new NicknameException();
        virtualViewMap.put(nickname, virtualView);
    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return virtualViewMap;
    }

    public void sendBroadcastMessage(String message) {
        for(VirtualView v : virtualViewMap.values()) v.showMessage(message);
    }

    public void sendBroadcastMessageExclude(String message, String nickname) {
        for(Map.Entry<String, VirtualView> entry : virtualViewMap.entrySet()) {
            if(!entry.getKey().equals(nickname)) entry.getValue().showMessage(message);
        }
    }

    public boolean isCurrentPlayer(String username) {
        return turnController.isCurrentPlayer(username);
    }

    public Player getCurrentPlayer() {
        return turnController.getCurrentPlayer();
    }

    public boolean isFull() {
        return chosenPlayerNum != 0 && chosenPlayerNum == virtualViewMap.size();
    }

    public boolean isSinglePlayer() {
        return chosenPlayerNum == 1 && virtualViewMap.size() == 1;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean setChosenPlayersNum(int number) {

        if(number > 0 && number <= Match.MAX_PLAYERS) {
            this.chosenPlayerNum = number;
            return true;
        }

        return false;

    }

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
    public void update(int pathPosition) {
        match.getPlayers().forEach(p -> p.getPlayerBoard().getPath().applyVaticanReport(pathPosition));
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
}
