package it.polimi.ingsw.modelTest.resourcesTest;

import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for all the subclasses of "Resource"
 */
class ResourceTest {

    /**
     * Tests the method "update()" with positive parameter
     */
    @Test
    @DisplayName("Attribute volume of Resource (Coin) increased correctly")
    void testPositiveUpdate(){

        Resource tested = new Coin();

        tested.update(3);
        assertTrue(tested.getVolume() == 3);

    }

    /**
     * Tests the method "update()" with negative parameter
     */
    @Test
    @DisplayName("Attribute volume of Resource (Coin) decreased correctly")
    void testNegativeUpdate(){      //TODO: create an exception to throw when there are not enough resources
        Resource tested = new Coin(3);

        tested.update(-2);
        assertTrue(tested.getVolume() == 1);

    }

}
