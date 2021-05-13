package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.controller.gameState.InitState;
import it.polimi.ingsw.controller.gameState.LoginState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.controllerExceptions.NicknameException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.observingPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.*;

public class GameController implements Observer, Serializable {

    private Match match;
    private Map<String, VirtualView> virtualViewMap;

    private GameState gameState;
    private TurnController turnController;

    private Stack<ArrayList<LeaderCard>> leaderChoices;

    public GameController() {
        this.match = new Match();
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.gameState = new LoginState(match, this);
    }

    public void onMessage(Message received) {

        //TODO: Create VV taken from map of received nickname

        try {
            received.getProcessedBy(gameState);
        } catch (InvalidStateException e) {
            //TODO: Notify server that the message is invalid in this state.
        }

    }

    public void logPlayer(String nickname, VirtualView virtualView) {

        if(virtualViewMap.isEmpty()) {

            try {
                addVirtualView(nickname, virtualView);
            } catch (NicknameException e) { //This catch will never happen, it's explicit just for code richness.
                virtualView.showLogin("error", false);
                return;
            }

            match.addPlayer(new Player(nickname));

            //TO RECEIVE INCOMING MESSAGES
            virtualView.addObserver(this);
            virtualView.start();

            virtualView.showLogin("You've been logged in successfully", true);
            virtualView.askNumberOfPlayers();

        }
        else if(!match.isFull()) {

            try {
                addVirtualView(nickname, virtualView);
            } catch (NicknameException e) {
                virtualView.showLogin("Nickname already logged, try another one", false);
                return;
            }

            //TO RECEIVE INCOMING MESSAGES
            virtualView.addObserver(this);
            virtualView.start();

            match.addPlayer(new Player(nickname));
            virtualView.showLogin("You've been logged in successfully", true);

            if(match.isFull()) {
                //TODO: Restore interrupted match ??
                startMatch();
            }

        }
        else {
            virtualView.showError("Sorry, something went wrong..");
        }

    }

    public void startMatch() {

        if(match.isSinglePlayer()) match.setToSinglePlayer();
        match.shufflePlayers();

        setGameState(new InitState(match, this));
        turnController = new TurnController(this, virtualViewMap, match);
        prepareLeaderChoices();

        sendBroadcastMessage("Match started!" + turnController.getCurrentPlayer().getNickname() + "is the first player");

        virtualViewMap.get(turnController.getCurrentPlayer().getNickname()).askLeaders(leaderChoices.pop());

    }

    private void prepareLeaderChoices() {

        ArrayList<LeaderCard> leaderCards = match.getLeaders();
        Collections.shuffle(leaderCards);
        int playerNum = match.getPlayers().size();

        for(int players = 0; players < playerNum; players++) {

            ArrayList<LeaderCard> choice = new ArrayList<>();

            for(int i = 0; i < 4; i++) {
                choice.add(leaderCards.get(i + players * playerNum));
            }

            leaderChoices.push(choice);

        }

    }

    public void addVirtualView(String nickname, VirtualView virtualView) throws NicknameException {

        if(virtualViewMap.containsKey(nickname)) throw new NicknameException();

        virtualViewMap.put(nickname, virtualView);
        match.addObserver(virtualView);
        //TODO: SharedArea Observable? -> addObserver(virtualView)?
    }

    @Override
    public void update(Message message) {
        onMessage(message);
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

    public boolean isFull() {
        return match.isFull();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
