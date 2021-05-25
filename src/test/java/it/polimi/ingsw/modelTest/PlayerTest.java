package it.polimi.ingsw.modelTest;

import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.effects.BlankEffect;
import it.polimi.ingsw.model.effects.Discount;
import it.polimi.ingsw.model.effects.DiscountEffect;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.requirements.BlankRequirement;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerTest {

    Player underTest;

    DevelopmentCard developmentCard;
    ArrayList<Resource> cost;
    Effect discount;

    @BeforeEach
    void setUp() {
        underTest = new Player();

        discount = new DiscountEffect(new Discount(new Coin(-1)));

        underTest.getPlayerBoard().getWarehouse().getSecondShelf().setShelfResource(new Servant(2));
        underTest.getPlayerBoard().getWarehouse().getThirdShelf().setShelfResource(new Coin(1));

        developmentCard = new DevelopmentCard(ECardColor.BLUE, 1);
        cost = new ArrayList<>();
        cost.add(new Servant(1));
        cost.add(new Coin(1));
        developmentCard.setCost(cost);
    }

    @Test
    void testPayNoDiscount() throws NotEnoughResourcesException, InvalidInputException {

        //@TestedMethod
        underTest.pay(developmentCard);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getSecondShelf().getResourceVolume(), 1);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getThirdShelf().getResourceVolume(),0);

        //@TestedMethod
        assertThrows(NotEnoughResourcesException.class, () -> underTest.pay(developmentCard));

        ArrayList<Resource> cost2 = new ArrayList<>();
        cost2.add(new Servant(1));
        developmentCard.setCost(cost2);

        //@TestedMethod
        underTest.pay(developmentCard);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getSecondShelf().getResourceVolume(), 0);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getThirdShelf().getResourceVolume(),0);

        assertThrows(NotEnoughResourcesException.class, () -> underTest.pay(developmentCard));

    }

    @Test
    void testPayDiscounted() throws NotEnoughResourcesException, InvalidInputException {

        discount.applyOn(underTest);

        //@TestedMethod
        underTest.pay(developmentCard);

        assertEquals(underTest.getPlayerBoard().getWarehouse().getSecondShelf().getResourceVolume(), 1);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getThirdShelf().getResourceVolume(),1);

        Effect discount2 = new DiscountEffect(new Discount(new Servant(-2)));
        discount2.applyOn(underTest);

        //@TestedMethod
        underTest.pay(developmentCard);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getSecondShelf().getResourceVolume(), 1);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getThirdShelf().getResourceVolume(),1);

    }

    @Test
    void testHandleNewDevCard() throws InvalidInputException {

        underTest.handleNewDevCard(developmentCard, underTest.getPlayerBoard().getDevelopmentCardsArea().getFirstStack());
        assertThrows(InvalidInputException.class, () -> underTest.handleNewDevCard(developmentCard, underTest.getPlayerBoard().getDevelopmentCardsArea().getFirstStack()));
        underTest.handleNewDevCard(new DevelopmentCard(ECardColor.PURPLE, 2), underTest.getPlayerBoard().getDevelopmentCardsArea().getFirstStack());

    }

    @Test
    void testHandleMarketResource() throws NotEnoughResourcesException, InvalidInputException {
        Resource rsh = new Shield(1);
        Resource rst = new Stone(1);

        //@TestedMethod
        underTest.handleMarketResources(rsh, underTest.getPlayerBoard().getWarehouse().getFirstShelf());
        assertThrows(InvalidInputException.class, () -> underTest.handleMarketResources(rst, underTest.getPlayerBoard().getWarehouse().getFirstShelf()));
        assertThrows(InvalidInputException.class, () -> underTest.handleMarketResources(rst, underTest.getPlayerBoard().getWarehouse().getSecondShelf()));

        underTest.getPlayerBoard().getWarehouse().getSecondShelf().emptyThisShelf();
        assertDoesNotThrow(() -> underTest.handleMarketResources(rst, underTest.getPlayerBoard().getWarehouse().getSecondShelf()));

    }

    @Test
    void testGetCurrentVictoryPoints() throws NotEnoughResourcesException {

        underTest.getPlayerBoard().getDevelopmentCardsArea().getFirstStack().push(new DevelopmentCard(10));
        underTest.getPlayerBoard().getDevelopmentCardsArea().getSecondStack().push(new DevelopmentCard(4));

        LeaderCard card = new LeaderCard(new BlankRequirement(), new BlankEffect(), 3);
        underTest.giveLeaderCard(card);
        card.activateOn(underTest);
        assertTrue(card.isActive());

        //@TestedMethod
        assertEquals(underTest.getCurrentVictoryPoints(), 17);

    }

}
