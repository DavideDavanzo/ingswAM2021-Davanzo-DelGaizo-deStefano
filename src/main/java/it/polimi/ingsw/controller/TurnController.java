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
}
