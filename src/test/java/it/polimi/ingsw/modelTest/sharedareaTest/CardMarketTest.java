package it.polimi.ingsw.modelTest.sharedareaTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CardMarketTest {

    CardMarket underTest;

    @BeforeEach
    void setUp() {
        underTest = new CardMarket();
        assertFalse(underTest.isEmpty());
    }

    @Test
    public void creationTest() {
        assertTrue(underTest.getDecks()[2][0].getColor().equals(ECardColor.GREEN));
        underTest.getDecks()[2][0].getCards().push(new DevelopmentCard(ECardColor.GREEN, 1, new ArrayList<Resource>(), new Trade(), 4));
        assertTrue(underTest.contains(new DevelopmentCard(ECardColor.GREEN, 1, new ArrayList<Resource>(), new Trade(), 4)));
    }

    @Test
    public void takeTest() {

        //@TestedMethod
        DevelopmentCard card = underTest.takeCard("GREEN", 1);
        assertFalse(underTest.contains(card));
        assertFalse(underTest.isEmpty());

        //@TestedMethod
        DevelopmentCard secondCard = underTest.takeCard("GREEN", 1);
        assertFalse(underTest.contains(secondCard));
    }

    @Test
    public void destroyTest() throws LossException {

        //@TestedMethod
        underTest.destroyCard(ECardColor.GREEN);
        assertDoesNotThrow(() -> underTest.destroyCard(ECardColor.GREEN));
        if(underTest.isEmpty()) assertThrows(LossException.class, () -> underTest.destroyCard(ECardColor.GREEN));
    }
}
