package it.polimi.ingsw.modelTest.cardTest.developmentTest;

import  static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DevelopmentCardTest {

    DevelopmentCard underTest;
    ECardColor green;
    ECardColor purple;
    int levelOne;
    int levelTwo;
    ArrayList<Resource> inputOne = new ArrayList<>();
    ArrayList<Resource> inputTwo = new ArrayList<>();
    ArrayList<Item> outputOne = new ArrayList<>();
    ArrayList<Item> outputTwo = new ArrayList<>();
    Trade tradeOne;
    Trade tradeTwo;
    int pointsOne;
    int pointsTwo;

    ArrayList<Resource> newInput = new ArrayList<>();

    @BeforeEach
    void setUp() {
        green = ECardColor.GREEN;
        purple = ECardColor.PURPLE;

        levelOne = 1;
        levelTwo = 3;

        inputOne.add(new Coin(2));
        inputOne.add(new Stone(2));

        inputTwo.add(new Coin(2));
        inputTwo.add(new Shield(3));

        outputOne.add(new FaithPoint(3));
        outputOne.add(new Shield(2));

        outputTwo.add(new Coin(3));
        outputTwo.add(new FaithPoint(10));

        tradeOne = new Trade(inputOne, outputOne);
        tradeTwo = new Trade(inputTwo, outputTwo);

        pointsOne = 2;
        pointsTwo = 3;

        newInput.add(new Stone(2));
        newInput.add(new Coin(2));
    }

    @Test
    void testEquals() {
        //I'll use the input of the trade as the cost of the card to save some space.
        underTest = new DevelopmentCard(green, levelOne, inputOne, tradeOne, pointsOne);

        DevelopmentCard equalCard = new DevelopmentCard(green, levelOne, inputOne, tradeOne, pointsOne);
        DevelopmentCard equalCardTwo = new DevelopmentCard(ECardColor.GREEN, levelOne, newInput, tradeOne, 2);
        DevelopmentCard differentCard = new DevelopmentCard(purple, levelTwo, inputTwo, tradeTwo, pointsTwo);

        //Only points are different.
        DevelopmentCard almostEqualCard = new DevelopmentCard(green, levelOne, inputOne, tradeOne, pointsTwo);


        assertEquals(equalCard, underTest);
        assertEquals(equalCardTwo, underTest);
        assertNotEquals(differentCard, underTest);
        assertNotEquals(almostEqualCard, underTest);
        assertTrue(underTest.equalCost(equalCardTwo.getCost()));
    }
}
