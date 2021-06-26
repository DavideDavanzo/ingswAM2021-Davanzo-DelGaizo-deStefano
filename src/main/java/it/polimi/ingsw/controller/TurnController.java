package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.ErrorMessage;
import it.polimi.ingsw.network.messages.LorenzoPositionUpdate;
import it.polimi.ingsw.network.messages.PassTurnMessage;
import it.polimi.ingsw.network.messages.TurnWakeMessage;
import it.polimi.ingsw.view.VirtualView;

import java.util.LinkedList;
import java.util.Map;

/**
 * This class implements the controller of a turn
 */
public class TurnController {

    private GameController gameController;
    private Map<String, VirtualView> virtualViewMap;

    private int turn;
    private Thread timerThread;

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

        do {
            nextPlayer();
        } while(!gameController.getVirtualViewMap().get(currentPlayer.getNickname()).isConnected());

        if(gameController.isEndGame() && currentPlayer.hasInkwell()) {
            gameController.handleEndGame();
            return;
        }

        updateTurnCounter();

        if(gameController.isSinglePlayer()) {
            try {
                gameController.flipActionToken();
                virtualViewMap.get(currentPlayer.getNickname()).updateLorenzoPosition(gameController.getMatch().getLorenzoIlMagnifico().getBlackCrossPosition());
                if(gameController.getCurrentPlayer().getPlayerBoard().getPath().getTrack()[gameController.getMatch().getLorenzoIlMagnifico().getBlackCrossPosition()].isPopeSquare())
                    gameController.getCurrentPlayer().getPlayerBoard().getPath().applyVaticanReport(gameController.getMatch().getLorenzoIlMagnifico().getBlackCrossPosition());
                else if (gameController.getMatch().getLorenzoIlMagnifico().getBlackCrossPosition() - 1 > 0 && gameController.getCurrentPlayer().getPlayerBoard().getPath().getTrack()[gameController.getMatch().getLorenzoIlMagnifico().getBlackCrossPosition() - 1].isPopeSquare())
                    gameController.getCurrentPlayer().getPlayerBoard().getPath().applyVaticanReport(gameController.getMatch().getLorenzoIlMagnifico().getBlackCrossPosition() - 1);
            } catch (LossException e) {
                return;
            }
        }

        gameController.sendBroadcastMessageExclude(currentPlayer.getNickname() + "'s turn started . ." , currentPlayer.getNickname());
        virtualViewMap.get(currentPlayer.getNickname()).sendMessage(new TurnWakeMessage());

        timerThread = new Thread(() -> startTimer(120000));
        timerThread.start();

    }

    public void nextPlayer() {
        gameController.updateQueue();
        currentPlayer = players.peek();
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

        return resourceSupply;

    }

    private void startTimer(int milliseconds){
        System.out.println("Turn timer started for " + gameController.getCurrentPlayer().getNickname());
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Turn's timer killed on time");
            return;
        }
        System.out.println("Timer exceeded");
        VirtualView currentVirtualView = gameController.getVirtualViewMap().get(currentPlayer.getNickname());
        currentVirtualView.sendMessage(new ErrorMessage("You waited too long!"));
        currentVirtualView.sendMessage(new PassTurnMessage());
        nextTurn();
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

    public Thread getTimerThread(){
        return timerThread;
    }
}
