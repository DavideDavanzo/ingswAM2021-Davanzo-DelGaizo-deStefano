package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import org.junit.jupiter.api.Test;

public class DevelopmentCardsAreaTest {

    DevelopmentCardsArea underTest = new DevelopmentCardsArea();

    @Test
    void testAddDevCard() throws InvalidInputException {

        DevelopmentCard devCard = new DevelopmentCard(1, ECardColor.GREEN);
        
        underTest.addDevCard(devCard, underTest.getFirstDevSlot());

        assertTrue(underTest.getFirstDevSlot().get(0) == devCard);

        assertThrows(InvalidInputException.class, () -> underTest.addDevCard(new DevelopmentCard(1, ECardColor.BLUE), underTest.getFirstDevSlot()) );

    }

}
