package it.polimi.ingsw.model.cards;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Special parser, reads and constructs {@link DevelopmentCard}(s) from a given file.
 * Current implementation: Json.
 */
public class DevCardParser {

    /**
     * Parser the file.
     * @return a list of the cards.
     */
    public ArrayList<DevelopmentCard> parse() {

        ArrayList<DevelopmentCard> developmentCards;

        InputStream is = null;
        is = this.getClass().getClassLoader().getResourceAsStream("developmentCards");
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
        DevelopmentCard[] cards = new DevelopmentCard[0];
        try {
            cards = objectMapper.readValue(fileAsString, DevelopmentCard[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        developmentCards = new ArrayList<>(Arrays.asList(cards));


        return developmentCards;

    }

}