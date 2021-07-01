package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.gameState.LoginState;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.PlayersNumber;
import it.polimi.ingsw.network.server.ServerClientHandler;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.Socket;

public class GameControllerTest {

    //log a player/vv ina single player match, then try (and fail) to log another player
    @Test
    void testLogSinglePlayer(){
        GameController gameController = new GameController();
        assertEquals(LoginState.class, gameController.getGameState().getClass());
        VirtualView vv1 = new VirtualView(new ServerClientHandler(new Socket()));
        assertThrows(NullPointerException.class, () -> gameController.logPlayer("<nickname1>", vv1));
        assertTrue(gameController.getVirtualViewMap().containsKey("<nickname1>"));
        assertTrue(gameController.getVirtualViewMap().containsValue(vv1));
        assertThrows(NullPointerException.class, () -> gameController.onMessage(new PlayersNumber(1)));
        assertEquals(1, gameController.getChosenPlayerNum());
        assertTrue(gameController.isFull());
        assertTrue(gameController.isSinglePlayer());

        VirtualView vv2 = new VirtualView(new ServerClientHandler(new Socket()));
        assertThrows(NullPointerException.class, () -> gameController.logPlayer("<nickname2>", vv2));
        assertFalse(gameController.getVirtualViewMap().containsKey("<nickname2>"));
    }

    //log two players and check isFull()
    @Test
    void testLogMultiplayer(){
        GameController gameController = new GameController();
        assertEquals(LoginState.class, gameController.getGameState().getClass());
        VirtualView vv1 = new VirtualView(new ServerClientHandler(new Socket()));
        assertThrows(NullPointerException.class, () -> gameController.logPlayer("<nickname1>", vv1));
        assertTrue(gameController.getVirtualViewMap().containsKey("<nickname1>"));
        assertTrue(gameController.getVirtualViewMap().containsValue(vv1));
        assertThrows(NullPointerException.class, () -> gameController.onMessage(new PlayersNumber(2)));
        assertEquals(2, gameController.getChosenPlayerNum());
        VirtualView vv2 = new VirtualView(new ServerClientHandler(new Socket()));
        assertThrows(NullPointerException.class, () -> gameController.logPlayer("<nickname2>", vv2));
        assertTrue(gameController.isFull());
        assertFalse(gameController.isSinglePlayer());
    }

    @Test
    void testMoveAllExcept(){
        GameController gameController = new GameController();
        Player p1, p2, p3;
        VirtualView v1, v2, v3;
        gameController.getVirtualViewMap().put("<nickname1>", v1 = new VirtualView(new ServerClientHandler(new Socket())));
        gameController.getVirtualViewMap().put("<nickname2>", v2 = new VirtualView(new ServerClientHandler(new Socket())));
        gameController.getVirtualViewMap().put("<nickname3>", v3 = new VirtualView(new ServerClientHandler(new Socket())));
        v1.connect();
        v2.connect();
        v3.connect();
        gameController.setMatch(new Match());
        gameController.getMatch().addPlayer(p1 = new Player("<nickname1>"));
        gameController.getMatch().addPlayer(p2 = new Player("<nickname2>"));
        gameController.getMatch().addPlayer(p3 = new Player("<nickname3>"));
        gameController.moveAllExcept(p3, 2);
        assertEquals(2, p1.getPlayerBoard().getPath().getCurrentPositionAsInt());
        assertEquals(2, p2.getPlayerBoard().getPath().getCurrentPositionAsInt());
        assertEquals(0, p3.getPlayerBoard().getPath().getCurrentPositionAsInt());
    }

}
