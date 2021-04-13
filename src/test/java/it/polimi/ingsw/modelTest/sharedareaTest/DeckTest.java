package it.polimi.ingsw.modelTest.sharedareaTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.sharedarea.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

public class DeckTest {

    Deck underTest;

    @BeforeEach
    void setUp() {
        underTest = new Deck();
    }

    @Test
    public void testAddAndTake() {

        assertTrue(underTest.isEmpty());

        //@TestedMethod
        underTest.add(new DevelopmentCard(1));

        assertFalse(underTest.isEmpty());
        assertEquals(new DevelopmentCard(1).getVictoryPoints(), underTest.getCards().peek().getVictoryPoints());

        //@TestedMethod
        DevelopmentCard card = underTest.takeCard();
        assertEquals(1, card.getVictoryPoints());
        assertTrue(underTest.isEmpty());

    }

    @Test
    public void shuffleTest() {

        Stack<DevelopmentCard> cards = new Stack<>();
        cards.push(new DevelopmentCard(3));
        cards.push(new DevelopmentCard(4));
        cards.push(new DevelopmentCard(6));
        //Stack: 6 / 4 / 3 (bottom)
        underTest.add(cards);

        Deck different = new Deck();
        different.add(underTest.getCards());
        assertEquals(underTest.toString(), different.toString());

        //@TestedMethod
        underTest.shuffle();
        //assertNotEquals(underTest.toString(), different.toString()); // not always true (random)
        for (DevelopmentCard d : underTest.getCards()) {
            assertTrue(different.toString().contains(d.toString()));
        }

    }

}
