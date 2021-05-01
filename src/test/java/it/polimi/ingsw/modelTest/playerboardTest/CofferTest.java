package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Shield;
import org.junit.jupiter.api.Test;

class CofferTest {

    @Test
    public void testUpdateCoffer() throws NotEnoughResourcesException, InvalidInputException {

        Coffer cofferTested = new Coffer();

        assertEquals( new Coin(), cofferTested.getCoins() );
        assertEquals( new Shield(), cofferTested.getShields() );

        cofferTested.updateCoffer(new Coin(2));
        cofferTested.updateCoffer(new Shield(1));
        assertEquals( new Coin(2), cofferTested.getCoins());
        assertEquals( new Shield(1), cofferTested.getShields() );

        cofferTested.updateCoffer(new Coin(-1));
        assertEquals( new Coin(1), cofferTested.getCoins());

        assertThrows( NotEnoughResourcesException.class, () -> cofferTested.updateCoffer(new Coin(-5)) );

    }

}
