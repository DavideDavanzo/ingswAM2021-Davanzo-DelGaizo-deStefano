package it.polimi.ingsw.modelTest.playerboardTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.GameOverException;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.FaithPoint;
import org.junit.jupiter.api.Test;

public class PathTest {

    @Test
    void testStandingOnPopeSpace() throws InvalidInputException {

        Path tested = new Path();
        //move to first Pope space
        tested.moveForward(new FaithPoint(8));

        tested.applyVaticanReport(8);

        assertTrue(tested.getPopeTokens().get(0).isFaceUp());
        assertFalse(tested.getTrack()[8].isPopeSquare());

        //move to second Pope space
        tested.moveForward(new FaithPoint(8));

        tested.applyVaticanReport(16);

        assertTrue(tested.getPopeTokens().get(1).isFaceUp());
        assertFalse(tested.getTrack()[16].isPopeSquare());

        //move to third Pope space
        tested.moveForward(new FaithPoint(8));

        tested.applyVaticanReport(24);

        assertTrue(tested.getPopeTokens().get(2).isFaceUp());
        assertFalse(tested.getTrack()[24].isPopeSquare());

    }

    @Test
    void testStandingInVaticanReportArea() throws InvalidInputException {

        Path tested = new Path();
        tested.moveForward(new FaithPoint(6));

        tested.applyVaticanReport(8);

        assertTrue(tested.getPopeTokens().get(0).isFaceUp());

        assertFalse(tested.getTrack()[8].isPopeSquare());

    }

    @Test
    void testStandingBeforeVaticanReportArea() throws InvalidInputException {

        Path tested = new Path();

        tested.moveForward(new FaithPoint(3));

        tested.applyVaticanReport(8);

        assertFalse(tested.getPopeTokens().get(0).isFaceUp());

        assertFalse(tested.getTrack()[8].isPopeSquare());

    }

    @Test
    void testGetPathVictoryPoints() throws InvalidInputException {

        Path tested = new Path();

        assertTrue(tested.getPathVictoryPoints() == 0);

        tested.moveForward(new FaithPoint(3));

        assertTrue(tested.getPathVictoryPoints() == 1);

        tested.moveForward(new FaithPoint(5));
        tested.applyVaticanReport(8);

        assertTrue(tested.getPathVictoryPoints() == 4);

        tested.moveForward(new FaithPoint(2));
        tested.applyVaticanReport(16);

        assertTrue(tested.getPathVictoryPoints() == 6);


    }


}
