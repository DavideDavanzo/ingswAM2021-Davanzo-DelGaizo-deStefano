package it.polimi.ingsw.modelTest.resourcesTest;

import it.polimi.ingsw.exceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for all the subclasses of "Resource"
 */
class ResourceTest {

    /**
     * Testing if the amount of resource increases correctly, using subclass "Coin" as an example
     */
    @Test
    @DisplayName("Attribute volume of Resource (Coin) increased correctly")
    void testPositiveUpdate() throws NotEnoughResourcesException {

        Resource underTest = new Coin();

        assertTrue(underTest.getVolume() == 0);     //initialized at 0

        underTest.update(new Coin(3));
        assertTrue(underTest.getVolume() == 3);     //increased by 3

        underTest.update(new Coin());
        assertTrue(underTest.getVolume() == 3);     //not modified at all

    }

    /**
     * Testing if the amount of resource decreases correctly, using subclass "Coin" as an example
     */
    @Test
    @DisplayName("Attribute volume of Resource (Coin) decreased correctly")
    void testNegativeUpdate() throws NotEnoughResourcesException {

        Resource underTest = new Coin(3);       //initialized at 3

        underTest.update(new Coin(-2));
        assertTrue(underTest.getVolume() == 1);     //decreased by 2

        assertThrows(NotEnoughResourcesException.class, () -> underTest.update( new Coin(-2)));     //volume cannot be decreased below zero

        assertTrue(underTest.getVolume() == 1);     //when NotEnoughResourcesException thrown volume must not be modified

    }

}
