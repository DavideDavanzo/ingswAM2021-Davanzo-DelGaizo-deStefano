package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.TurnWakeMessage;
import it.polimi.ingsw.view.VirtualView;

import java.util.LinkedList;
import java.util.Map;

public class TurnController {

    private GameController gameController;
    private Map<String, VirtualView> virtualViewMap;

    private int turn;

    private LinkedList<Player> players;
    private Player currentPlayer;

    public TurnController(GameController gameController, Map<String, VirtualView> virtualViewMap) {
        this.gameController = gameController;
        this.virtualViewMap = virtualViewMap;
        this.turn = 0;
        this.players = gameController.getPlayers();
        this.currentPlayer = players.peek();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextTurn() {

        currentPlayer.giveBigActionToken();
        gameController.updateQueue();
        currentPlayer = players.peek();
        updateTurnCounter();

        if(gameController.isSinglePlayer()) {
            try {
                gameController.flipActionToken();
            } catch (LossException e) {
                return;
            }
        }

        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + "'s turn started . ." , currentPlayer.getNickname());
        virtualViewMap.get(currentPlayer.getNickname()).sendMessage(new TurnWakeMessage());
    }

    public void updateTurnCounter() {
        turn++;
    }

    public void updateQueue() {
        Player p = players.remove();
        players.add(p);
        currentPlayer = players.peek();
    }

    public Integer initResourceSupply() throws Exception {

        if(turn < 2 || turn > 4) throw new Exception();

        Integer resourceSupply;

        resourceSupply = turn/2;
        if(turn == 2) return resourceSupply;

        getCurrentPlayer().moveForward(1);
        //TODO: Move observed by views, update other players
        return resourceSupply;

    }

    public boolean isCurrentPlayer(String username) {
        return currentPlayer.getNickname().equals(username);
    }

    public int getTurn() {
        return turn;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
