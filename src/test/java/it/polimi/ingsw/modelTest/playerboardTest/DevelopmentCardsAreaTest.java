package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DevelopmentCardsAreaTest {

    DevelopmentCardsArea underTest;

    @BeforeEach
    void setUp() {
        underTest = new DevelopmentCardsArea();
    }

    @Test
    void testAddDevCard() throws InvalidInputException {

        DevelopmentCard greenCard = new DevelopmentCard( ECardColor.GREEN, 1);
        DevelopmentCard blueCard = new DevelopmentCard(ECardColor.BLUE, 1);
        DevelopmentCard purpleCard = new DevelopmentCard(ECardColor.PURPLE, 2);
        DevelopmentCard yellowCard = new DevelopmentCard(ECardColor.YELLOW,3);

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

}
