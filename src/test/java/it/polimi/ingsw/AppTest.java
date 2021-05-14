package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.parser.Parser;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.market.BlueMarble;
import it.polimi.ingsw.model.requirements.ColorRequirement;
import it.polimi.ingsw.model.resources.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() { assertTrue(true); }

    @Test
    public void Parser() {
        Parser parser = new Parser();

        Resource coin = new Coin(3);

        System.out.println(parser.serialize(coin));

        Resource resource = (Resource) parser.deserialize(parser.serialize(coin), Resource.class);

        System.out.println(resource.toString());

    }


}