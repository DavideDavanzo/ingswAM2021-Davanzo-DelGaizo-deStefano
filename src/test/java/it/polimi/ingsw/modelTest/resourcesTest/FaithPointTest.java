package it.polimi.ingsw.modelTest.resourcesTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.resources.FaithPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * FaithPoint test class
 */
class FaithPointTest {

    /**
     * Tests the method "update()" with positive parameter
     */
    @Test
    @DisplayName("Attribute numPoints increased correctly")
    public void testPositiveUpdate() {

        FaithPoint tested = new FaithPoint();               //numPoints initialized to 0
        tested.update(3);                               //add 3 to 0 numPoints

        assertTrue(tested.getNumPoints() == 3);     //numPoints of tested should now be 3

        tested.update(2);                               //add 2 to 3 numPoints

        assertFalse(tested.getNumPoints() == 3);    //numPoints of tested should not be 3 anymore
        assertTrue(tested.getNumPoints() == 5);     //numPoints of tested should now be 5

    }

    /**
     * Tests the method "update()" with negative parameter
     */
    @Test
    @DisplayName("Attribute numPoints decreased correctly")
    public void testNegativeUpdate() {      //TODO: create an exception to throw when there are not enough resources

        FaithPoint tested = new FaithPoint(5);      //numPoints initialized to 5
        tested.update(-2);                              //sub 2 from 5

        assertTrue(tested.getNumPoints() == 3);     //numPoints of tested should now be 3

        tested.update(-5);                              //sub 5 from 3

        assertFalse(tested.getNumPoints() == -2);   //numPoints of tested should not be -2
        assertTrue(tested.getNumPoints() == 0);     //3-5<0 then numPoints of tested should now be 0

    }

}
