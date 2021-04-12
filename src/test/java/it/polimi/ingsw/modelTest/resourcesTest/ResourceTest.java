package it.polimi.ingsw.modelTest.resourcesTest;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
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
    void testPositiveUpdate() throws NotEnoughResourcesException, InvalidInputException {

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
    void testNegativeUpdate() throws NotEnoughResourcesException, InvalidInputException {

        Resource underTest = new Coin(3);       //initialized at 3

        underTest.update(new Coin(-2));
        assertTrue(underTest.getVolume() == 1);     //decreased by 2

        assertThrows(NotEnoughResourcesException.class, () -> underTest.update( new Coin(-2)));     //volume cannot be decreased below zero

        assertTrue(underTest.getVolume() == 1);     //when NotEnoughResourcesException thrown volume must not be modified

    }

    @Test
    void testResourceTypeException() {

        Resource coins = new Coin(3);
        Resource stones = new Stone(1);
        assertThrows(InvalidInputException.class, () -> coins.update(stones));

    }


    /**
     * Testing if method equals() works correctly
     */
    @Test
    void testEquals(){

        Resource underTestOne = new Coin(3);
        Resource underTestTwo = new Coin(3);

        //a.equals(b) <==> b.equals(a)
        assertTrue(underTestOne.equals(underTestTwo));
        assertTrue(underTestTwo.equals(underTestOne));

        underTestOne = new Coin(1);

        assertFalse(underTestOne.equals(underTestTwo));
        assertFalse(underTestTwo.equals(underTestOne));

    }
}
