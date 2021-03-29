package it.polimi.ingsw.modelTest.resourcesTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.resourcesExceptions.GameOverException;
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
    public void testUpdate() throws GameOverException {

        FaithPoint underTest = new FaithPoint();               //numPoints initialized to 0

        underTest.update(new FaithPoint(3));        //add 3 to 0 numPoints
        assertTrue(underTest.getVolume() == 3);     //numPoints of tested should now be 3

        underTest.update(new FaithPoint());
        assertTrue(underTest.getVolume() == 3);     //numPoints of tested should still be 5

        assertThrows(GameOverException.class, () -> underTest.update(new FaithPoint(20)));      //this exception must be thrown when faith points exceeds 20

        assertTrue(underTest.getVolume() == 20);    //20 is the upper limit

    }

}
