package it.polimi.ingsw.modelTest;

import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.effects.Discount;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.market.BlueMarble;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.Servant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerTest {

    Player underTest;

    DevelopmentCard developmentCard;
    ArrayList<Resource> cost;

    @BeforeEach
    void setUp() {
        underTest = new Player();

        underTest.getPlayerBoard().getWarehouse().getSecondShelf().setShelfResource(new Servant(2));
        underTest.getPlayerBoard().getWarehouse().getThirdShelf().setShelfResource(new Coin(1));

        developmentCard = new DevelopmentCard();
        cost = new ArrayList<>();
        cost.add(new Servant(1));
        cost.add(new Coin(1));
        developmentCard.setCost(cost);
    }

    @Test
    void testPay() throws NotEnoughResourcesException, InvalidInputException {

        //@TestedMethod
        underTest.pay(developmentCard);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getSecondShelf().getShelfResource().getVolume(), 1);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getThirdShelf().getShelfResource().getVolume(),0);

        //@TestedMethod
        assertThrows(NotEnoughResourcesException.class, () -> underTest.pay(developmentCard));

        ArrayList<Resource> cost2 = new ArrayList<>();
        cost2.add(new Servant(1));
        developmentCard.setCost(cost2);

        //@TestedMethod
        underTest.pay(developmentCard);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getSecondShelf().getShelfResource().getVolume(), 0);
        assertEquals(underTest.getPlayerBoard().getWarehouse().getThirdShelf().getShelfResource().getVolume(),0);

        assertThrows(NotEnoughResourcesException.class, () -> underTest.pay(developmentCard));

    }
}
