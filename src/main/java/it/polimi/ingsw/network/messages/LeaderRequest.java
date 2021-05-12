package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class LeaderRequest extends Message {

    public LeaderRequest() {
    }

    public LeaderRequest(String msg) {
        super(msg);
    }

    @Override
    public void apply(View view) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LeaderCard[] cards = objectMapper.readValue(msg, LeaderCard[].class);
            view.askLeaders((ArrayList<LeaderCard>) Arrays.asList(cards));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

}
