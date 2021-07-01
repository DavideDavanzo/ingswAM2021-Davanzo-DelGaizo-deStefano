package it.polimi.ingsw.model.cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Special parser, reads and constructs {@link LeaderCard}(s) from a given file.
 * Current implementation: Json.
 */
public class LeaderCardParser {

    /**
     * Parser the file.
     * @return a list of the cards.
     */
    public ArrayList<LeaderCard> parse() {

        ArrayList<LeaderCard> leaderCards;

        InputStream is = null;
        is = this.getClass().getClassLoader().getResourceAsStream("leaderCards");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = null;
        try {
            line = buf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        while(line != null) {
            sb.append(line).append("\n");
            try {
                line = buf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String fileAsString = sb.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        LeaderCard[] card = new LeaderCard[0];
        try {
            card = objectMapper.readValue(fileAsString, LeaderCard[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        leaderCards = new ArrayList<>(Arrays.asList(card));


        return leaderCards;

    }

    /**
     * @param leaderCards is a list of Leader Cards.
     * @return Json String version of the parameter.
     */
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

    /**
     * Returns a list of cards from a Json-type-built String.
     * @param leadersAsString
     * @return a list of cards.
     */
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
