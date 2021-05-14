package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;
import it.polimi.ingsw.model.market.GreyMarble;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.market.YellowMarble;
import it.polimi.ingsw.model.requirements.BlankRequirement;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() { assertTrue(true); }
}