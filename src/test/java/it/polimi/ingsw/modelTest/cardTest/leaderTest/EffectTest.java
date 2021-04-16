package it.polimi.ingsw.modelTest.cardTest.leaderTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.*;
import it.polimi.ingsw.model.market.BlueMarble;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class EffectTest {

    Effect underTest;
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
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
        underTest = new WhiteMarbleEffect(new BlueMarble());

        assertNull(player.getExtraMarble());
        assertFalse(player.hasWhiteMarblePower());

        //@TestedMethod
        underTest.applyOn(player);

        assertNotNull(player.getExtraMarble());
        assertTrue(player.hasWhiteMarblePower());
        assertEquals(Shield.class, player.getExtraMarble().returnItem(player).getClass());
    }

    @Test
    public void testDiscount() {
        underTest = new DiscountEffect(new Discount(new Coin(1)));

        assertNull(player.getDiscount());
        assertFalse(player.hasDiscount());

        //@TestedMethod
        underTest.applyOn(player);

        assertNotNull(player.getDiscount());
        assertTrue(player.hasDiscount());
        assertEquals(Coin.class, player.getDiscount().getDiscountResource().getClass());
        assertTrue(player.getDiscount().isActive());
    }

    @Test
    public void testExtraTrade() {
        Trade t = new Trade();
        t.setInput(new ArrayList<Resource>(){{add(new Coin(1));}});
        t.setOutput(new ArrayList<Item>(){{add(new FaithPoint(1));}});

        underTest = new ExtraDevEffect(t);

        assertNull(player.getExtraTrade());
        assertFalse(player.hasExtraTrade());

        //@TestedMethod
        underTest.applyOn(player);

        assertNotNull(player.getExtraTrade());
        assertTrue(player.hasExtraTrade());
    }

}