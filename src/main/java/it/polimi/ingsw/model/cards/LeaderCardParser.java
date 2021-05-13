package it.polimi.ingsw.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

public class LeaderCardParser {

    public ArrayList<LeaderCard> parse() {

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();

        try(Reader reader = new FileReader("src/main/resources/leaderCards")) {

            ObjectMapper objectMapper = new ObjectMapper();
            LeaderCard[] card = objectMapper.readValue(reader, LeaderCard[].class);

            leaderCards = new ArrayList<>(Arrays.asList(card));

        } catch (IOException e) {
            System.out.println("No json file for leader cards..");
            e.printStackTrace();
        }

        return leaderCards;

    }

    public String serialize(ArrayList<LeaderCard> leaderCards) {

        ObjectMapper objectMapper = new ObjectMapper();
        String s = "";

        try {
            s = objectMapper.writeValueAsString(leaderCards);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Error in leader cards serialization");
        }

        return s;

    }

    public ArrayList<LeaderCard> deserialize(String leadersAsString) {

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();

        try {
            LeaderCard[] card = objectMapper.readValue(leadersAsString, LeaderCard[].class);
            leaderCards = new ArrayList<>(Arrays.asList(card));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Error in leader cards deserialization");
        }

        return leaderCards;

    }

}
