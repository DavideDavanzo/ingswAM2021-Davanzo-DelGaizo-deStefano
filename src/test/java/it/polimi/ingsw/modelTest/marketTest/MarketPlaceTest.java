package it.polimi.ingsw.modelTest.marketTest;

import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.*;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Servant;
import it.polimi.ingsw.model.resources.Shield;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MarketPlaceTest {

    @Test
    @DisplayName("Testing all the methods of Market")
    public void testMarketPlace() throws IllegalArgumentException, IllegalChoiceException {
        Market market = new Market();
        Marble[][] tested = { {new BlueMarble(), new WhiteMarble(), new YellowMarble(), new GreyMarble()}, {new PurpleMarble(), new RedMarble(),
                    new WhiteMarble(), new BlueMarble()}, {new GreyMarble(), new YellowMarble(), new PurpleMarble(), new WhiteMarble()} };
        Marble spareMarble = new WhiteMarble();
        Player currentPlayer = new Player();
        ArrayList<Item> test = market.getResources('r',1 , currentPlayer);
        ArrayList<Item> proof = new ArrayList<Item>(Arrays.asList(new Servant(), new FaithPoint(), null, new Shield()));
        Marble[][] anotherOne = { {new BlueMarble(), new WhiteMarble(), new YellowMarble(), new GreyMarble()}, {new WhiteMarble(), new PurpleMarble(), new RedMarble(),
                new WhiteMarble()}, {new GreyMarble(), new YellowMarble(), new PurpleMarble(), new WhiteMarble()} };
        assertEquals(proof, test);
        assertEquals(anotherOne, market.getMatrix());
        assertTrue(spareMarble instanceof BlueMarble);

    }

}

