package it.polimi.ingsw.modelTest.cardTest.leaderTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.ExtraShelfEffect;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.modelTest.stubs.PlayerEffectStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EffectTest {

    Effect underTest;
    PlayerEffectStub player;

    @BeforeEach
    void setUp() {
        player = new PlayerEffectStub();
    }

    @Test
    public void testExtraShelf() {
        underTest = new ExtraShelfEffect(new Coin());

        //@TestedMethod
        underTest.applyOn(player);

        Shelf extraShelf = player.getPlayerBoard().getWarehouse().getExtraShelves().get(0);
        assertNotNull(extraShelf);
        assertTrue(extraShelf.getShelfResource() instanceof Coin);
        assertEquals(extraShelf.getAvailableVolume(), 2);
        assertEquals(extraShelf.getShelfResource().getVolume(), 0);
    }

    @Test
    public void testWhiteMarble() {
        underTest = new WhiteMarbleEffect();

        //@TestedMethod
        underTest.applyOn(player);
    }

}