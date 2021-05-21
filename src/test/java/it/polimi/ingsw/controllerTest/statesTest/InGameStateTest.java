package it.polimi.ingsw.controllerTest.statesTest;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.controller.gameState.InGameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.exceptions.controllerExceptions.NicknameException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.messages.MarketResourcesCmd;
import it.polimi.ingsw.network.server.ServerClientHandler;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class InGameStateTest {

    GameController gameController = new GameController();
    GameState inGameState = new InGameState(gameController);
    Match match = new Match();

    @Test
    public void marketResourceCmdTest() throws NicknameException, InvalidStateException {
        /*Market market = new Market();
        gameController.setGameState(inGameState);
        Player currentPlayer = new Player();
        match.addPlayer(currentPlayer);
        gameController.setMatch(match);
        gameController.addVirtualView("Pippo", new VirtualView(new ServerClientHandler(new Socket())));
        TurnController turnController = new TurnController(gameController, gameController.getVirtualViewMap());
        gameController.setTurnController(turnController);
        turnController.setCurrentPlayer(currentPlayer);

        MarketResourcesCmd marketResourcesCmd = new MarketResourcesCmd('r', 1);

        inGameState.process(marketResourcesCmd);

        for(Item i : currentPlayer.getItemsToArrangeInWarehouse()) {
            System.out.println(i.toString());
        }*/




    }

}
