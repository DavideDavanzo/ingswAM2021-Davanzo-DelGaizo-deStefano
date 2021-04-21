package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.ProductionFailException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerBoardTest {

    @Test
    void testPossiblePayment() throws NotEnoughResourcesException, InvalidInputException {
        ArrayList<Resource> inputRequired = new ArrayList<>();
        Player player = new Player();

        // Player board status initialisation
        // Warehouse: first shelf -> 1 coin, second shelf -> 1 shield, first extra -> 1 servant, second extra -> 2 shields
        // Coffer: 5 coins, 3 stones, 1 servant
        Effect effect = new ExtraShelfEffect(new Servant());
        player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Coin(1), player.getPlayerBoard().getWarehouse().getFirstShelf());
        player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Shield(1), player.getPlayerBoard().getWarehouse().getSecondShelf());
        effect.applyOn(player);
        effect = new ExtraShelfEffect(new Shield());
        effect.applyOn(player);
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(0).updateShelf(new Servant(1));
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(1).updateShelf(new Shield(2));
        player.getPlayerBoard().getCoffer().updateCoffer(new Coin(5));
        player.getPlayerBoard().getCoffer().updateCoffer(new Stone(3));
        player.getPlayerBoard().getCoffer().updateCoffer(new Servant(1));

        //trade input example:
        //1 shield, 3 servants, 3 coins, 2 stones, 1 coin
        inputRequired.add(new Shield(1));
        inputRequired.add(new Servant(3));
        inputRequired.add(new Coin(3));
        inputRequired.add(new Stone(2));
        inputRequired.add(new Coin(1));

        assertFalse(player.getPlayerBoard().possiblePayment(inputRequired));

        player.getPlayerBoard().getCoffer().updateCoffer(new Servant(3));

        assertTrue(player.getPlayerBoard().possiblePayment(inputRequired));

    }

    @Test
    void testPayRequiredProductionResources() throws NotEnoughResourcesException, InvalidInputException {

        // Player board status initialisation
        // Warehouse: first shelf -> 1 coin, second shelf -> 1 shield, first extra -> 1 servant, second extra -> 2 shields
        // Coffer: 5 coins, 3 stones, 3 servants
        Player player = new Player();
        Effect effect = new ExtraShelfEffect(new Servant());

        player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Coin(1), player.getPlayerBoard().getWarehouse().getFirstShelf());
        player.getPlayerBoard().getWarehouse().addResourcesToShelf(new Shield(1), player.getPlayerBoard().getWarehouse().getSecondShelf());
        effect.applyOn(player);
        effect = new ExtraShelfEffect(new Shield());
        effect.applyOn(player);
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(0).updateShelf(new Servant(1));
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(1).updateShelf(new Shield(2));
        player.getPlayerBoard().getCoffer().updateCoffer(new Coin(5));
        player.getPlayerBoard().getCoffer().updateCoffer(new Stone(3));
        player.getPlayerBoard().getCoffer().updateCoffer(new Servant(3));

        //trade input example:
        //1 shield, 2 servants, 3 coins, 2 stones, 1 coin
        ArrayList<Resource> input = new ArrayList<>();
        input.add(new Shield(1));
        input.add(new Servant(2));
        input.add(new Coin(3));
        input.add(new Stone(2));
        input.add(new Coin(1));

        player.getPlayerBoard().payRequiredResources(input);

        assertTrue(player.getPlayerBoard().getWarehouse().getFirstShelf().isEmpty());
        assertTrue(player.getPlayerBoard().getWarehouse().getSecondShelf().isEmpty());
        assertTrue(player.getPlayerBoard().getWarehouse().getThirdShelf().isEmpty());
        assertEquals(0, player.getPlayerBoard().getWarehouse().getExtraShelves().get(0).getShelfResource().getVolume());
        assertEquals(2, player.getPlayerBoard().getCoffer().getServants().getVolume());
        assertEquals(2, player.getPlayerBoard().getCoffer().getCoins().getVolume());
        assertEquals(1, player.getPlayerBoard().getCoffer().getStones().getVolume());

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

        Player player = new Player();
        Effect effect = new ExtraShelfEffect(new Servant());
        effect.applyOn(player);
        effect = new ExtraShelfEffect(new Shield());
        effect.applyOn(player);

        // Player board status initialisation
        // Warehouse: first shelf -> 1 coin, second shelf -> 1 shield, first extra -> 1 servant, second extra -> 2 shields
        // Coffer: 5 coins, 3 stones, 3 servants
        player.getPlayerBoard().getWarehouse().getFirstShelf().updateShelf(new Coin(1));
        player.getPlayerBoard().getWarehouse().getSecondShelf().updateShelf(new Shield(1));
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(0).updateShelf(new Servant(1));
        player.getPlayerBoard().getWarehouse().getExtraShelves().get(1).updateShelf(new Shield(2));
        player.getPlayerBoard().getCoffer().updateCoffer(new Coin(5));
        player.getPlayerBoard().getCoffer().updateCoffer(new Stone(3));
        player.getPlayerBoard().getCoffer().updateCoffer(new Servant(3));

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

        player.getPlayerBoard().activateProduction(chosenCards);

        // Player board final status
        // Warehouse: totally empty except fot second extra shelf with 2 shields
        // Coffer: 4 servants, 4 coins, 1 stone, 2 shields
        // Path: +2
        assertTrue(player.getPlayerBoard().getWarehouse().getFirstShelf().isEmpty());
        assertTrue(player.getPlayerBoard().getWarehouse().getSecondShelf().isEmpty());
        assertTrue(player.getPlayerBoard().getWarehouse().getThirdShelf().isEmpty());
        assertEquals(new Servant(), player.getPlayerBoard().getWarehouse().getExtraShelves().get(0).getShelfResource());
        assertEquals(new Shield(2), player.getPlayerBoard().getWarehouse().getExtraShelves().get(1).getShelfResource());
        assertEquals(4, player.getPlayerBoard().getCoffer().getCoins().getVolume());
        assertEquals(4, player.getPlayerBoard().getCoffer().getServants().getVolume());
        assertEquals(1, player.getPlayerBoard().getCoffer().getStones().getVolume());
        assertEquals(2, player.getPlayerBoard().getCoffer().getShields().getVolume());
        assertEquals(2, player.getPlayerBoard().getPath().getCurrentPositionAsInt());

    }

    @Test
    void testCalculateVictoryPoints() throws InvalidInputException {

        PlayerBoard tested = new PlayerBoard();

        tested.getPath().moveForward(7);    //move to position 7

        tested.getPath().applyVaticanReport(8);     //flip first papal token

        DevelopmentCard dev1 = new DevelopmentCard(ECardColor.GREEN, 1, null, null, 3);
        DevelopmentCard dev2 = new DevelopmentCard(ECardColor.BLUE, 2, null, null, 4);
        DevelopmentCard dev3 = new DevelopmentCard(ECardColor.BLUE, 1, null, null, 3);

        //de2 on dev1 in first slot, dev 3 in second slot
        tested.getDevelopmentCardsArea().addDevCard(dev1, tested.getDevelopmentCardsArea().getFirstStack());
        tested.getDevelopmentCardsArea().addDevCard(dev2, tested.getDevelopmentCardsArea().getFirstStack());
        tested.getDevelopmentCardsArea().addDevCard(dev3, tested.getDevelopmentCardsArea().getSecondStack());

        assertEquals(14, tested.calculateVictoryPoints() );

    }

    @Test
    void testBaseProduction() throws NotEnoughResourcesException, InvalidInputException {

        PlayerBoard underTest = new PlayerBoard();

        underTest.getWarehouse().addResourcesToShelf(new Shield(1), underTest.getWarehouse().getFirstShelf());
        underTest.getWarehouse().addResourcesToShelf(new Coin(2), underTest.getWarehouse().getSecondShelf());

        ArrayList<Resource> input = new ArrayList<>();
        input.add(new Coin(1));
        input.add(new Shield(1));

        underTest.activateBaseProduction(input, new Servant(1));
        assertEquals(1, underTest.getCoffer().getServants().getVolume());

        underTest.getCoffer().updateCoffer(new Servant(1));

        input = new ArrayList<>();
        input.add(new Servant(2));

        underTest.activateBaseProduction(input, new FaithPoint(1));
        assertEquals(1, underTest.getPath().getCurrentPositionAsInt());

    }

}
