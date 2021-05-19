package it.polimi.ingsw.modelTest.marketTest;

import it.polimi.ingsw.exceptions.marketExceptions.IllegalArgumentException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.*;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MarketPlaceTest {

    @Test
    @DisplayName("Testing all the methods of Market")
    public void testMarketPlace() throws IllegalArgumentException, IllegalChoiceException {

        Market market = new Market();
        Market otherMarket = new Market();

        //Market Creation
        Marble[][] tested = { {new BlueMarble(), new WhiteMarble(), new YellowMarble(), new GreyMarble()},
                              {new PurpleMarble(), new RedMarble(), new WhiteMarble(), new BlueMarble()},
                              {new GreyMarble(), new YellowMarble(), new PurpleMarble(), new WhiteMarble()}
        };
        market.setMarketMatrix(tested);

        //Other Market Creation
        Marble[][] anotherOne = { {new BlueMarble(), new WhiteMarble(), new YellowMarble(), new GreyMarble()},
                                  {new WhiteMarble(), new PurpleMarble(), new RedMarble(), new WhiteMarble()},
                                  {new GreyMarble(), new YellowMarble(), new PurpleMarble(), new WhiteMarble()}
        };
        otherMarket.setMarketMatrix(anotherOne);

        //Spare Marbles
        Marble spareMarble = new WhiteMarble();
        market.setSpareMarble(spareMarble);

        Marble secondSpareMarble = new BlueMarble();
        otherMarket.setSpareMarble(secondSpareMarble);

        //Returned Items
        ArrayList<Item> test = market.getResources('r',1);
        ArrayList<Item> proof = new ArrayList<Item>(Arrays.asList(new Servant(), new FaithPoint(), new Resource(), new Shield()));

        //Asserts
        for(int i = 0; i < test.size(); i++) {
            assertSame(test.get(i).getClass(), proof.get(i).getClass());
        }
        assertEquals(otherMarket, market);
    }

}

