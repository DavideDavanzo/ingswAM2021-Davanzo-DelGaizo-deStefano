package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class WarehouseTest {

    /**
     * Testing the behaviour of Warehouse when trying to switch valid and invalid shelves
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException
     */
    @Test
    void testSwitchShelves() throws NotEnoughResourcesException, InvalidInputException {

        Warehouse underTest = new Warehouse();

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
    void testAddExtraShelf() {

        Warehouse underTest = new Warehouse();

        Shelf extraShelf = new Shelf(2);
        extraShelf.setShelfResource(new Stone());

        underTest.addExtraShelf(extraShelf);

        assertTrue(extraShelf == underTest.getExtraShelves().get(0));

        extraShelf = new Shelf(2);
        extraShelf.setShelfResource(new Coin());

        underTest.addExtraShelf(extraShelf);

        assertFalse(extraShelf == underTest.getExtraShelves().get(0));
        assertTrue(extraShelf == underTest.getExtraShelves().get(1));

    }

    @Test
    void testSwitchExtraShelves() throws NotEnoughResourcesException, InvalidInputException {

        Player player = new Player();
        Effect effect = new ExtraShelfEffect(new Shield());

        player.getPlayerBoard().getWarehouse().getFirstShelf().updateShelf(new Shield(1));
        effect.applyOn(player);
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(0).updateShelf(new Shield(1));

        assertThrows(InvalidInputException.class, () -> player.getPlayerBoard().getWarehouse().switchShelves(player.getPlayerBoard().getWarehouse().getFirstShelf(), player.getPlayerBoard().getWarehouse().getExtraShelves().get(0)));

    }

    @Test
    void testGetAllResources() throws NotEnoughResourcesException, InvalidInputException {

        Player player = new Player();
        Effect effect = new ExtraShelfEffect(new Servant());

        player.getPlayerBoard().getWarehouse().getFirstShelf().updateShelf(new Coin(1));
        player.getPlayerBoard().getWarehouse().getSecondShelf().updateShelf(new Stone(1));
        player.getPlayerBoard().getWarehouse().getThirdShelf().updateShelf(new Shield(3));
        ArrayList<Resource> expected = new ArrayList<>();
        expected.add(new Coin(1));
        expected.add(new Stone(1));
        expected.add(new Shield(3));
        for(int i=0; i<3; i++)
            assertTrue(expected.get(i).equals(player.getPlayerBoard().getWarehouse().getAllWarehouseResources().get(i)));
        effect.applyOn(player);
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(0).updateShelf(new Servant(1));
        expected.add(new Servant(1));
        for(int i=0; i<3; i++)
            assertTrue(expected.get(i).equals(player.getPlayerBoard().getWarehouse().getAllWarehouseResources().get(i)));

    }

    @Test
    void testAddResourceToShelf() throws NotEnoughResourcesException, InvalidInputException {

        Player player = new Player();
        Effect effect = new ExtraShelfEffect(new Servant());

        player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Coin(1), player.getPlayerBoard().getWarehouse().getFirstShelf());
        effect.applyOn(player);
        player.getPlayerBoard().getWarehouse().addExtraShelf(new Shelf(2));
        player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Servant(), player.getPlayerBoard().getWarehouse().getExtraShelves().get(0));
        assertThrows(InvalidInputException.class, () -> player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Coin(2), player.getPlayerBoard().getWarehouse().getSecondShelf()));
        assertThrows(InvalidInputException.class, () -> player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Coin(2), player.getPlayerBoard().getWarehouse().getThirdShelf()));

    }

}
