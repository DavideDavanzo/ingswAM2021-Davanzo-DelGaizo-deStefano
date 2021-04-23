package it.polimi.ingsw.model.cards;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DevCardParser {

    public ArrayList<DevelopmentCard> parse() {

        ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();

        try(Reader reader = new FileReader("src/main/resources/developmentCards")) {

            ObjectMapper objectMapper = new ObjectMapper();
            DevelopmentCard[] cards = objectMapper.readValue(reader, DevelopmentCard[].class);

            developmentCards = new ArrayList<>(Arrays.asList(cards));

        } catch (IOException e) {
            System.out.println("No json file for development cards..");
            e.printStackTrace();
        }

        return developmentCards;

    }

}