package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.ExtraDevEffect;
import it.polimi.ingsw.model.effects.WhiteMarbleEffect;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.market.BlueMarble;
import it.polimi.ingsw.model.market.GreyMarble;
import it.polimi.ingsw.model.requirements.ColorRequirement;
import it.polimi.ingsw.model.resources.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AppTest {

    @Test
    void leaderPrint() {

        LeaderCard leaderCard = new LeaderCard(new ColorRequirement(new HashMap<ECardColor, Integer>(){{put(ECardColor.GREEN, 2);}}),
                                                new WhiteMarbleEffect(new BlueMarble()),
                                                4);
        System.out.println(leaderCard.print());


    }




}