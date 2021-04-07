package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.resources.Coin;
import org.junit.jupiter.api.Test;

class CofferTest {

    @Test
    public void testUpdateCoffer() throws NotEnoughResourcesException {

        Coffer cofferTested = new Coffer();

        assertEquals( new Coin(), cofferTested.getCoins() );

        cofferTested.updateCoffer(new Coin(2));
        assertEquals(new Coin(2), cofferTested.getCoins());

        cofferTested.updateCoffer(new Coin(-1));
        assertEquals(new Coin(1), cofferTested.getCoins());

        assertThrows( NotEnoughResourcesException.class, () -> cofferTested.updateCoffer(new Coin(-5)) );

    }

}
