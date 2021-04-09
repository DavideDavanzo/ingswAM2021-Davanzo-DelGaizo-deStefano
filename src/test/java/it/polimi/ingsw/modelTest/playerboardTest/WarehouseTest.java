package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Shield;
import it.polimi.ingsw.model.resources.Stone;
import org.junit.jupiter.api.Test;

public class WarehouseTest {

    private Warehouse underTest = new Warehouse();

    /**
     * Testing the behaviour of Warehouse when trying to switch valid and invalid shelves
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException
     */
    @Test
    void testSwitchShelves() throws NotEnoughResourcesException, InvalidInputException {

        //initialization of an example of shelves
        underTest.getFirstShelf().updateShelf(new Coin(1));
        underTest.getSecondShelf().updateShelf(new Shield(1));
        underTest.getThirdShelf().updateShelf(new Stone(2));

        //try to switch a 1 resource max shelf with a bigger one -> must throw exception
        assertThrows(InvalidInputException.class, () -> underTest.switchShelves(underTest.getFirstShelf(), underTest.getThirdShelf()));

        //check of warehouse's state
        assertEquals(new Coin(1), underTest.getFirstShelf().getShelfResource());
        assertEquals(new Shield(1), underTest.getSecondShelf().getShelfResource());
        assertEquals(new Stone(2), underTest.getThirdShelf().getShelfResource());

        //try to switch two valid shelves
        underTest.switchShelves(underTest.getFirstShelf(), underTest.getSecondShelf());

        //check of warehouse's state
        assertEquals(new Shield(1), underTest.getFirstShelf().getShelfResource());
        assertEquals(new Coin(1), underTest.getSecondShelf().getShelfResource());
        assertEquals(new Stone(2), underTest.getThirdShelf().getShelfResource());

    }

    @Test
    void testSwitchExtraShelves() throws NotEnoughResourcesException, InvalidInputException {

        underTest.getFirstShelf().updateShelf(new Shield(1));
        Shelf extraShelf = new Shelf(2);
        extraShelf.updateShelf(new Shield());
        underTest.addExtraShelf(extraShelf);

        assertThrows(InvalidInputException.class, () -> underTest.switchShelves(underTest.getFirstShelf(), underTest.getExtraShelves().get(0)));

    }

}
