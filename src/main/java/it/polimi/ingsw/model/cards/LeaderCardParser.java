package it.polimi.ingsw.model.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeaderCardParser {

    public ArrayList<LeaderCard> parse() throws FileNotFoundException {

        ArrayList<LeaderCard> cards = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/main/java/it/polimi/ingsw/model/files/leaderCards.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split("//");
                cards.add(create(values));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cards;
    }

    private LeaderCard create(String[] values) {
        return null;
    }
}
