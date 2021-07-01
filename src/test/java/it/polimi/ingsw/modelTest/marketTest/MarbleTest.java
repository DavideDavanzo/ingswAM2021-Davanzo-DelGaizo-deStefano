package it.polimi.ingsw.modelTest.marketTest;

import it.polimi.ingsw.model.sharedarea.market.*;
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
        assertEquals(new Shield(1), tested.returnItem());

    }

    /**
     * Testing if a purple marble returns a servants
     */

    @Test
    @DisplayName("Method returns the right Item Servant")
    public void testGetPurpleMarble(){
        Marble tested = new PurpleMarble();
        assertEquals(new Servant(1), tested.returnItem());
    }

    /**
     * Testing if a yellow marble returns a coin
     */

    @Test
    @DisplayName("Method returns the right Item Coin")
    public void testGetYellowMarble(){
        Marble tested = new YellowMarble();
        assertEquals(new Coin(1), tested.returnItem());
    }

    /**
     * Testing if a grey marble returns a stone
     */

    @Test
    @DisplayName("Method returns the right Item Stone")
    public void testGetGreyMarble(){
        Marble tested = new GreyMarble();
        assertEquals(new Stone(1), tested.returnItem());
    }

    /**
     * Testing if a red marble returns a faith point
     */

    @Test
    @DisplayName("Method returns the right Item FaithPoint")
    public void testGetRedMarble(){
        Marble tested = new RedMarble();
        assertEquals(new FaithPoint(1), tested.returnItem());
    }

    @Test
    @DisplayName("Method return generic Resource")
    public void testGetWhiteMarble(){
        Marble tested = new WhiteMarble();
        assertEquals(new Resource(1), tested.returnItem());
    }

}
