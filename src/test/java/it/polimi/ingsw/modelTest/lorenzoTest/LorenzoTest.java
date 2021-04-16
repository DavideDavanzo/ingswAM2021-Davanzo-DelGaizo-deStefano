package it.polimi.ingsw.modelTest.lorenzoTest;

import it.polimi.ingsw.model.lorenzo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class LorenzoTest {

    @Test
    @DisplayName("test that method adds the right amount to Lorenzo's position")
    public void testCrossToken(){
        CrossToken tested = new CrossToken();
        LorenzoIlMagnifico lorenzoIlMagnifico = new LorenzoIlMagnifico();
        lorenzoIlMagnifico.move(2);
        tested.activate(lorenzoIlMagnifico);
        assertEquals(4, lorenzoIlMagnifico.getBlackCrossPosition());
    }


    @Test
    @DisplayName("test that method adds the right amount to Lorenzo's position and actually shuffles tokens")
    public void testCrossAndShuffle(){
        CrossAndShuffleToken tested = new CrossAndShuffleToken();
        LorenzoIlMagnifico lorenzoIlMagnifico = new LorenzoIlMagnifico();
        lorenzoIlMagnifico.move(1);
        Stack<LorenzoToken> lorenzoTokens = new Stack<>();

        lorenzoTokens.add(0, new TossDevCardsToken());
        lorenzoTokens.add(1, new CrossToken());
        lorenzoTokens.add(2, new TossDevCardsToken());
        lorenzoTokens.add(3, new TossDevCardsToken());
        lorenzoTokens.add(4, new CrossToken());
        lorenzoTokens.add(5, new TossDevCardsToken());
        lorenzoTokens.add(6, new CrossAndShuffleToken());
        Stack<LorenzoToken> otherStack = new Stack<>();

        otherStack.add(0, new TossDevCardsToken());
        otherStack.add(1, new CrossToken());
        otherStack.add(2, new TossDevCardsToken());
        otherStack.add(3, new TossDevCardsToken());
        otherStack.add(4, new CrossToken());
        otherStack.add(5, new TossDevCardsToken());
        otherStack.add(6, new CrossAndShuffleToken());
        tested.activate(lorenzoIlMagnifico);
        assertEquals(2, lorenzoIlMagnifico.getBlackCrossPosition());
        assertNotEquals(otherStack,lorenzoTokens);
    }


}
