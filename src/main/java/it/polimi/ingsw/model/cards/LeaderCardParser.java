package it.polimi.ingsw.model.cards;

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

}
