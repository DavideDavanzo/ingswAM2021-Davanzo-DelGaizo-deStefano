package it.polimi.ingsw.modelTest.cardTest.leaderTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.requirements.*;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class RequirementTest {

    Requirement underTest;
    Player player;
    HashMap<ECardColor, Integer> map;

    @BeforeEach
    void setUp() {
        player = new Player();
        map = new HashMap();
        map.put(ECardColor.GREEN, 1);
        map.put(ECardColor.PURPLE, 2);
    }

    @Test
    void testFaithPoints() throws InvalidInputException {

        FaithPoint points = new FaithPoint(14);
        underTest = new FaithPointsRequirement(points);

        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getPath().moveForward(12);
        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getPath().moveForward(2);
        assertTrue(underTest.validateOn(player));

        FaithPoint returned = (FaithPoint) underTest.getAttribute();

        //@TestedMethod
        assertEquals(returned, new FaithPoint(14));

    }

    @Test
    void testColorRequirement() {

        underTest = new ColorRequirement(map);

        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getDevelopmentCardsArea().getFirstStack().push(new DevelopmentCard(ECardColor.GREEN, 1));
        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getDevelopmentCardsArea().getFirstStack().push(new DevelopmentCard(ECardColor.PURPLE, 2));
        player.getPlayerBoard().getDevelopmentCardsArea().getSecondStack().push(new DevelopmentCard(ECardColor.PURPLE, 1));
        //@TestedMethod
        assertTrue(underTest.validateOn(player));

    }

    @Test
    void testColorLevelRequirement() {

        underTest = new ColorLevelRequirement(map);

        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getDevelopmentCardsArea().getFirstStack().push(new DevelopmentCard(ECardColor.GREEN, 1));
        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getDevelopmentCardsArea().getSecondStack().push(new DevelopmentCard(ECardColor.PURPLE, 1));
        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getDevelopmentCardsArea().getFirstStack().push(new DevelopmentCard(ECardColor.PURPLE, 2));
        //@TestedMethod
        assertTrue(underTest.validateOn(player));

    }

    @Test
    void testResourceRequirement() throws NotEnoughResourcesException, InvalidInputException {

        underTest = new ResourceRequirement(new Coin(5));
        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getCoffer().updateCoffer(new Coin(4));

        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getWarehouse().getSecondShelf().setShelfResource(new Coin(1));

        //@TestedMethod
        assertTrue(underTest.validateOn(player));

        DevelopmentCard developmentCard = new DevelopmentCard();
        ArrayList<Resource> cost = new ArrayList<Resource>() {
            {
                add(new Coin(2));
            }
        };
        developmentCard.setCost(cost);

        player.pay(developmentCard);
        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getWarehouse().getThirdShelf().setShelfResource(new Coin(1));
        //@TestedMethod
        assertFalse(underTest.validateOn(player));

        player.getPlayerBoard().getWarehouse().getFirstShelf().setShelfResource(new Coin(1));
        //@TestedMethod
        assertTrue(underTest.validateOn(player));

    }

}
