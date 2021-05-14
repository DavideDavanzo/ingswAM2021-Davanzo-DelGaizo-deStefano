package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.LinkedList;
import java.util.Map;

public class TurnController {

    private GameController gameController;
    private Match match;
    private Map<String, VirtualView> virtualViewMap;

    private LinkedList<Player> players;
    private Player currentPlayer;

    public TurnController(GameController gameController, Map<String, VirtualView> virtualViewMap, Match match) {
        this.gameController = gameController;
        this.virtualViewMap = virtualViewMap;
        this.match = match;
        this.players = match.getPlayers();
        this.currentPlayer = players.peek();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextTurn() {
        match.updateQueue();
        currentPlayer = players.peek();
        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + "'s turn started . ." , currentPlayer.getNickname());
        virtualViewMap.get(currentPlayer.getNickname()).showMessage("Your turn started . .\n" + "Pick your moves!");
    }

    public boolean isValidPlayer(String username) {
        return currentPlayer.getNickname().equals(username);
    }
}
