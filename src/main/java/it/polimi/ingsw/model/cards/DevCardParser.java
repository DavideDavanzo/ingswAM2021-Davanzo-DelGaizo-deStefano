package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DevCardParser {

    public ArrayList<DevelopmentCard> parse() throws FileNotFoundException {

        ArrayList<DevelopmentCard> cards = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("src/main/java/it/polimi/ingsw/model/files/developmentCards.txt"));

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

    private DevelopmentCard create(String[] values) {
        ECardColor color = ECardColor.valueOf(values[0]);
        int level = Integer.parseInt(values[1].replaceAll("[^0-9]", ""));
        ArrayList<Resource> cost = parseCost(values[2]);
        Trade trade = parseTrade(values[3]);
        int point = Integer.parseInt(values[4].replaceAll("[^0-9]", ""));

        return new DevelopmentCard(color, level, cost, trade, point);
    }

    private ArrayList<Resource> parseCost(String cost) {

        ArrayList<Resource> compoundCost = new ArrayList<>();
        String[] resources = cost.split(",");

        for(String r : resources) {

            if(r.contains("Shield")) {
                compoundCost.add(new Shield(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
                continue;
            }
            if(r.contains("Coin")) {
                compoundCost.add(new Coin(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
                continue;
            }
            if(r.contains("Servant")) {
                compoundCost.add(new Servant(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
                continue;
            }
            if(r.contains("Stone")) {
                compoundCost.add(new Stone(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
            }

        }
        //Return Statement
        return compoundCost;
    }

    private Trade parseTrade(String trade) {

        ArrayList<Resource> input;
        ArrayList<Item> output = new ArrayList<>();

        String[] inOut = trade.split(">");

        input = parseCost(inOut[0]); //For the input, I can use the parseCost function

        String[] out = inOut[1].split(",");

        for(String r : out) {

            if(r.contains("Shield")) {
                output.add(new Shield(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
                continue;
            }
            if(r.contains("Coin")) {
                output.add(new Coin(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
                continue;
            }
            if(r.contains("Servant")) {
                output.add(new Servant(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
                continue;
            }
            if(r.contains("Stone")) {
                output.add(new Stone(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
                continue;
            }
            if(r.contains("Faith")) {
                output.add(new FaithPoint(Integer.parseInt(r.replaceAll("[^0-9]", ""))));
            }

        }

        return new Trade(input, output);
    }
}