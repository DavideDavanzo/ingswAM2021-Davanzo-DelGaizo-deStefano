package it.polimi.ingsw.modelTest.marketTest;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStub;
import it.polimi.ingsw.model.market.*;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for all the subclasses of Marble
 */

public class MarbleTest {

    /**
     * Testing if a blue marble returns a stone
     */

    @Test
    @DisplayName("Method returns the right Item Shield")
    public void testGetBlueMarble(){
        Marble tested = new BlueMarble();
        PlayerStub currentPlayer = new PlayerStub();
        assertEquals(new Shield(1), tested.returnItem(currentPlayer));

    }

    /**
     * Testing if a purple marble returns a servants
     */

    @Test
    @DisplayName("Method returns the right Item Servant")
    public void testGetPurpleMarble(){
        Marble tested = new PurpleMarble();
        PlayerStub currentPlayer = new PlayerStub();
        assertEquals(new Servant(1), tested.returnItem(currentPlayer));
    }

    /**
     * Testing if a yellow marble returns a coin
     */

    @Test
    @DisplayName("Method returns the right Item Coin")
    public void testGetYellowMarble(){
        Marble tested = new YellowMarble();
        PlayerStub currentPlayer = new PlayerStub();
        assertEquals(new Coin(1), tested.returnItem(currentPlayer));
    }

    /**
     * Testing if a grey marble returns a stone
     */

    @Test
    @DisplayName("Method returns the right Item Stone")
    public void testGetGreyMarble(){
        Marble tested = new GreyMarble();
        PlayerStub currentPlayer = new PlayerStub();
        assertEquals(new Stone(1), tested.returnItem(currentPlayer));
    }

    /**
     * Testing if a red marble returns a faith point
     */

    @Test
    @DisplayName("Method returns the right Item FaithPoint")
    public void testGetRedMarble(){
        Marble tested = new RedMarble();
        PlayerStub currentPlayer = new PlayerStub();
        assertEquals(new FaithPoint(1), tested.returnItem(currentPlayer));
    }

    /**
     * Testing if the white marble returns a Shield, boolean is true
     */

    @Test
    @DisplayName("Method returns Shield if boolean is true")
    public void testGetWhiteResourceBlueMarble(){
        PlayerStub p = new PlayerStub(new Shield(1), true);
        Marble tested = new WhiteMarble();
        assertEquals(new Shield(1), tested.returnItem(p));
    }

    /**
     * Testing if the white marble returns a servant, boolean s true
     */

    @Test
    @DisplayName("Method returns either Servant, boolean is true")
    public void testGetWhiteResourcePurpleMarble(){
        PlayerStub p = new PlayerStub(new Servant(1), true);
        Marble tested = new WhiteMarble();
        assertEquals(new Servant(1), tested.returnItem(p));
    }

    /**
     * Testing if the white marble returns a coin, boolean s true
     */

    @Test
    @DisplayName("Method returns Coin, boolean is true")
    public void testGetWhiteResourceYellowMarble() {
        PlayerStub p = new PlayerStub(new Coin(1), true);
        Marble tested = new WhiteMarble();
        assertEquals(new Coin(1), tested.returnItem(p));
    }

    /**
     * Testing if the white marble returns a stone, boolean s true
     */

    @Test
    @DisplayName("Method returns Stone, boolean is true")
    public void testGetWhiteResourceGreyMarble(){
        PlayerStub p = new PlayerStub(new Stone(1), true);
        Marble tested = new WhiteMarble();
        assertEquals(new Stone(1), tested.returnItem(p));
    }

    @Test
    @DisplayName("Boolean is false so method returns null")
    public void testGetResourceNull(){
        PlayerStub p = new PlayerStub();
        Marble tested = new WhiteMarble();
        assertNull(tested.returnItem(p));
    }

}



