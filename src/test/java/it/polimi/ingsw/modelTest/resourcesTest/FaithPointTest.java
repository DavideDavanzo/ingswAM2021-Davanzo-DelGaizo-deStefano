package it.polimi.ingsw.modelTest.resourcesTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.GameOverException;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.FaithPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * FaithPoint test class
 */
class FaithPointTest {

    /**
     * Tests that assures faith points can be increased correctly
     */
    @Test
    @DisplayName("Attribute numPoints increased correctly")
    void testUpdate() throws GameOverException, InvalidInputException {

        FaithPoint underTest = new FaithPoint();               //numPoints initialized to 0

        underTest.update(new FaithPoint(3));        //add 3 to 0 numPoints
        assertTrue(underTest.getVolume() == 3);     //numPoints of tested should now be 3

        underTest.update(new FaithPoint());
        assertTrue(underTest.getVolume() == 3);     //numPoints of tested should still be 5

        underTest.update(new FaithPoint(30));
        assertTrue(underTest.getVolume() == 24);

    }

    /**
     * Testing if method equals() works correctly
     */
    @Test
    void testEquals() {

        FaithPoint underTestOne = new FaithPoint(2);
        FaithPoint underTestTwo = new FaithPoint(2);

        //a.equals(b) <==> b.equals(a)
        assertTrue(underTestOne.equals(underTestTwo));
        assertTrue(underTestTwo.equals(underTestOne));

        underTestOne = new FaithPoint(1);

        assertFalse(underTestOne.equals(underTestTwo));
        assertFalse(underTestTwo.equals(underTestOne));

    }

}
