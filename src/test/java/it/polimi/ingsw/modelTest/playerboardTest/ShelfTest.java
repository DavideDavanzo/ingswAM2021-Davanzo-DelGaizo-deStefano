
package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.Coin;
import org.junit.jupiter.api.Test;

public class ShelfTest {

    @Test
    void testUpdate() throws NotEnoughResourcesException, InvalidInputException {

        Shelf underTest = new Shelf(3);

        assertNull(underTest.getShelfResource());

        underTest.updateShelf(new Coin(2));

        assertEquals(new Coin(2), underTest.getShelfResource());

        underTest.updateShelf(new Coin(-1));

        assertEquals(new Coin(1), underTest.getShelfResource());

        assertThrows(NotEnoughResourcesException.class, () -> underTest.updateShelf(new Coin(-2)));

        assertTrue(underTest.getShelfResource().getVolume() == 1);

        assertThrows(InvalidInputException.class, () -> underTest.updateShelf(new Coin(3)));

    }

}