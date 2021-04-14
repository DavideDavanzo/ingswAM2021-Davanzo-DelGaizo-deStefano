package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.ProductionFailException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerboardTest {

    @Test
    void testPossibleProduction() throws NotEnoughResourcesException, InvalidInputException {

        PlayerBoard tested = new PlayerBoard();

        ArrayList<Resource> resourcesRequired = new ArrayList<>();
        resourcesRequired.add(new Coin(1));
        resourcesRequired.add(new Shield(1));
        resourcesRequired.add(new Servant(2));
        resourcesRequired.add(new Coin(1));

        ArrayList<Resource> playerTotalResources = new ArrayList<>();
        playerTotalResources.add(new Coin(1));
        playerTotalResources.add(new Stone(1));
        playerTotalResources.add(new Shield(2));
        playerTotalResources.add(new Stone(4));
        playerTotalResources.add(new Servant(5));

        assertFalse(tested.possibleProduction(resourcesRequired, playerTotalResources));

        playerTotalResources.add(new Coin(2));

        assertTrue(tested.possibleProduction(resourcesRequired, playerTotalResources));

    }

    @Test
    void testPayRequiredProductionResources() throws NotEnoughResourcesException, InvalidInputException {

        // Player board status initialisation
        // Warehouse: first shelf -> 1 coin, second shelf -> 1 shield, first extra -> 1 servant, second extra -> 2 shields
        // Coffer: 5 coins, 3 stones, 3 servants
        PlayerBoard tested = new PlayerBoard();
        tested.getWarehouse().getFirstShelf().updateShelf(new Coin(1));
        tested.getWarehouse().getSecondShelf().updateShelf(new Shield(1));
        tested.getWarehouse().addExtraShelf(new Shelf(2));
        tested.getWarehouse().getExtraShelves().get(0).updateShelf(new Servant(1));
        tested.getWarehouse().addExtraShelf(new Shelf(2));
        tested.getWarehouse().getExtraShelves().get(1).updateShelf(new Shield(2));
        tested.getCoffer().updateCoffer(new Coin(5));
        tested.getCoffer().updateCoffer(new Stone(3));
        tested.getCoffer().updateCoffer(new Servant(3));

        //trade input example:
        //1 shield, 2 servants, 3 coins, 2 stones, 1 coin
        ArrayList<Resource> input = new ArrayList<>();
        input.add(new Shield(1));
        input.add(new Servant(2));
        input.add(new Coin(3));
        input.add(new Stone(2));
        input.add(new Coin(1));

        tested.payRequiredProductionResources(input);

        assertNull(tested.getWarehouse().getFirstShelf().getShelfResource());
        assertNull(tested.getWarehouse().getSecondShelf().getShelfResource());
        assertEquals(0, tested.getWarehouse().getExtraShelves().get(0).getShelfResource().getVolume());
        assertEquals(2, tested.getCoffer().getServants().getVolume());
        assertEquals(2, tested.getCoffer().getCoins().getVolume());
        assertEquals(1, tested.getCoffer().getStones().getVolume());

    }

    @Test
    void testProduce() throws InvalidInputException, NotEnoughResourcesException {
        PlayerBoard test = new PlayerBoard();
        ArrayList<Item> output = new ArrayList<>();
        output.add(new Coin(2));
        output.add(new FaithPoint(1));
        output.add(new Shield(1));
        output.add(new Servant(2));
        output.add(new Shield(1));
        output.add(new FaithPoint(1));

        test.produce(output);

        assertEquals(2, test.getCoffer().getCoins().getVolume());
        assertEquals(0, test.getCoffer().getStones().getVolume());
        assertEquals(2, test.getCoffer().getServants().getVolume());
        assertEquals(2, test.getCoffer().getShields().getVolume());
        assertEquals(2, test.getPath().getCurrentPositionAsInt());

    }

    @Test
    void testActivateProduction() throws NotEnoughResourcesException, InvalidInputException, ProductionFailException {

        // Player board status initialisation
        // Warehouse: first shelf -> 1 coin, second shelf -> 1 shield, first extra -> 1 servant, second extra -> 2 shields
        // Coffer: 5 coins, 3 stones, 3 servants
        PlayerBoard tested = new PlayerBoard();
        tested.getWarehouse().getFirstShelf().updateShelf(new Coin(1));
        tested.getWarehouse().getSecondShelf().updateShelf(new Shield(1));
        tested.getWarehouse().addExtraShelf(new Shelf(2));
        tested.getWarehouse().getExtraShelves().get(0).updateShelf(new Servant(1));
        tested.getWarehouse().addExtraShelf(new Shelf(2));
        tested.getWarehouse().getExtraShelves().get(1).updateShelf(new Shield(2));
        tested.getCoffer().updateCoffer(new Coin(5));
        tested.getCoffer().updateCoffer(new Stone(3));
        tested.getCoffer().updateCoffer(new Servant(3));

        ArrayList<Resource> input = new ArrayList<>();
        input.add(new Shield(1));
        input.add(new Servant(2));
        input.add(new Coin(3));
        ArrayList<Item> output = new ArrayList<>();
        output.add(new Coin(2));
        output.add(new FaithPoint(1));
        output.add(new Shield(1));
        DevelopmentCard developmentCardOne = new DevelopmentCard(ECardColor.GREEN, 2, null, new Trade(input, output), 3);

        input = new ArrayList<>();
        input.add(new Stone(2));
        input.add(new Coin(1));
        output = new ArrayList<>();
        output.add(new Servant(2));
        output.add(new Shield(1));
        output.add(new FaithPoint(1));
        DevelopmentCard developmentCardTwo = new DevelopmentCard(ECardColor.BLUE, 3, null, new Trade(input, output), 4);

        ArrayList<DevelopmentCard> chosenCards = new ArrayList<>();
        chosenCards.add(developmentCardOne);
        chosenCards.add(developmentCardTwo);

        //tot input: 1 shield, 2 servants, 3 coins, 2 stones, 1 coin
        //tot output: 2 coins, 1 faith point, 1 shield, 2 servants, 1 shield, 1 faith point

        tested.activateProduction(chosenCards);

        // Player board final status
        // Warehouse: totally empty except fot second extra shelf with 2 shields
        // Coffer: 4 servants, 4 coins, 1 stone, 2 shields
        // Path: +2
        assertNull(tested.getWarehouse().getFirstShelf().getShelfResource());
        assertNull(tested.getWarehouse().getSecondShelf().getShelfResource());
        assertNull(tested.getWarehouse().getThirdShelf().getShelfResource());
        assertEquals(new Servant(), tested.getWarehouse().getExtraShelves().get(0).getShelfResource());
        assertEquals(new Shield(2), tested.getWarehouse().getExtraShelves().get(1).getShelfResource());
        assertEquals(4, tested.getCoffer().getCoins().getVolume());
        assertEquals(4, tested.getCoffer().getServants().getVolume());
        assertEquals(1, tested.getCoffer().getStones().getVolume());
        assertEquals(2, tested.getCoffer().getShields().getVolume());
        assertEquals(2, tested.getPath().getCurrentPositionAsInt());

    }

}
