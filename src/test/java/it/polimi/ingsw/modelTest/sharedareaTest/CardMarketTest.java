package it.polimi.ingsw.modelTest.sharedareaTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardMarketTest {

    CardMarket underTest;

    @BeforeEach
    void setUp() {
        underTest = new CardMarket();
    }

    @Test
    public void creationTest() {
        assertTrue(!underTest.isEmpty());
        assertTrue(underTest.getDecks()[2][0].getColor().equals(ECardColor.GREEN));
        System.out.println(underTest.toString());
    }
}
