package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
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

        updateTurnCounter();

        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + "'s turn started . ." , currentPlayer.getNickname());
        virtualViewMap.get(currentPlayer.getNickname()).showMessage("Your turn started . .\n" + "Pick your moves!");
    }

    private void updateTurnCounter() {
        match.nextTurn();
    }

    public Integer initResourceSupply() throws Exception {

        int turn = match.getTurn();
        if(turn < 2 || turn > 4) throw new Exception();

        Integer resourceSupply;

        resourceSupply = turn/2;
        if(turn == 2) return resourceSupply;

        getCurrentPlayer().moveForward(1);
        //TODO: Move observed by views, update other players
        return resourceSupply;

    }

    public boolean isValidPlayer(String username) {
        return currentPlayer.getNickname().equals(username);
    }
}
