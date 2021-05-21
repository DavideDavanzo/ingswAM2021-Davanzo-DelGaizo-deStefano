package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.controller.gameState.LoginState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.controllerExceptions.NicknameException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observingPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.*;

public class GameController implements Observer, Serializable {

    private Match match;
    private Map<String, VirtualView> virtualViewMap;

    private int chosenPlayerNum;

    private GameState gameState;
    private TurnController turnController;
    private Timer timer;

    private Stack<ArrayList<LeaderCard>> leaderChoices;


    public GameController() {
        this.chosenPlayerNum = 0;
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
            match.addPlayer(new Player(entry.getKey()));
            match.addObserver(entry.getValue());
        }

        if(isSinglePlayer()) match.setToSinglePlayer();
        else match.shufflePlayers();

        turnController = new TurnController(this, virtualViewMap);
        turnController.updateTurnCounter();
        prepareLeaderChoices();

        sendBroadcastMessage("Match started! " + turnController.getCurrentPlayer().getNickname() + " is the first player");
        askLeaders();

    }

    public void askLeaders() {
        virtualViewMap.get(turnController.getCurrentPlayer().getNickname()).askLeaders(leaderChoices.pop());
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

    public void moveAllExcept(Player player, int steps) {
        //TODO
    }

    public void addVirtualView(String nickname, VirtualView virtualView) throws NicknameException {
        if(virtualViewMap.containsKey(nickname)) throw new NicknameException();
        virtualViewMap.put(nickname, virtualView);
        //TODO: SharedArea Observable? -> addObserver(virtualView)?
    }

    @Override
    public void update(Object object) {
        onMessage((Message) object);
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

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    public void updateQueue() {
        turnController.updateQueue();
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
}
