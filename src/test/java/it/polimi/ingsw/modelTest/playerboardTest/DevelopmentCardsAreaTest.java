package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DevelopmentCardsAreaTest {

    DevelopmentCardsArea underTest;
    HashMap<ECardColor, Integer> request;

    DevelopmentCard greenCard;
    DevelopmentCard blueCard;
    DevelopmentCard purpleCard;
    DevelopmentCard yellowCard;

    @BeforeEach
    void setUp() {
        underTest = new DevelopmentCardsArea();
        request = new HashMap<>();

        greenCard = new DevelopmentCard( ECardColor.GREEN, 1);
        blueCard = new DevelopmentCard(ECardColor.BLUE, 1);
        purpleCard = new DevelopmentCard(ECardColor.PURPLE, 2);
        yellowCard = new DevelopmentCard(ECardColor.YELLOW,3);
    }

    @Test
    void testAddDevCard() throws InvalidInputException {

        //@TestedMethod
        underTest.addDevCard(greenCard, underTest.getFirstStack());
        underTest.addDevCard(greenCard, underTest.getThirdStack());

        assertSame(underTest.getFirstStack().peek(), greenCard);
        //Level 1 on Level 1
        assertThrows(InvalidInputException.class, () -> underTest.addDevCard(blueCard, underTest.getFirstStack()));
        //Level 2 on Level 1
        assertDoesNotThrow(() -> underTest.addDevCard(purpleCard, underTest.getFirstStack()));
        //Level 2 on empty
        assertThrows(InvalidInputException.class, () -> underTest.addDevCard(purpleCard, underTest.getSecondStack()));
        //Level 3 on Level 1
        assertThrows(InvalidInputException.class, () -> underTest.addDevCard(yellowCard, underTest.getThirdStack()));

    }

    @Test
    void testHasColors() throws InvalidInputException {

        request.put(ECardColor.GREEN, 1);
        //@TestedMethod
        assertFalse(underTest.hasColors(request));

        underTest.addDevCard(greenCard, underTest.getThirdStack());
        //@TestedMethod
        assertTrue(underTest.hasColors(request));

        request.put(ECardColor.BLUE, 2);
        //@TestedMethod
        assertFalse(underTest.hasColors(request));

        underTest.addDevCard(blueCard, underTest.getFirstStack());
        //@TestedMethod
        assertFalse(underTest.hasColors(request));

        underTest.addDevCard(new DevelopmentCard(ECardColor.BLUE, 2), underTest.getThirdStack());
        //@TestedMethod
        assertTrue(underTest.hasColors(request));

        request.put(ECardColor.YELLOW, 1);
        //@TestedMethod
        assertFalse(underTest.hasColors(request));

    }

    @Test
    void testHasColorLevel() throws InvalidInputException {

        request.put(ECardColor.GREEN, 2);
        underTest.addDevCard(greenCard, underTest.getFirstStack());
        //@TestedMethod
        assertFalse(underTest.hasColorLevel(request));

        underTest.addDevCard(new DevelopmentCard(ECardColor.GREEN, 2), underTest.getFirstStack());
        //@TestedMethod
        assertTrue(underTest.hasColorLevel(request));

        request.put(ECardColor.BLUE, 3);
        //@TestedMethod
        assertFalse(underTest.hasColorLevel(request));

        underTest.addDevCard(new DevelopmentCard(ECardColor.BLUE, 3), underTest.getFirstStack());
        //@TestedMethod
        assertTrue(underTest.hasColorLevel(request));

    }
}
