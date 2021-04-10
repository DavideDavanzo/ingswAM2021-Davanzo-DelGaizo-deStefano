package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import java.util.ArrayList;

/**
 * <\h1>Trade</\h1>
 * Every {@link DevelopmentCard} has a Trade, defined as an Input and a matching Output
 */
public class Trade {

    private ArrayList<Resource> input;
    private ArrayList<Item> output;

    /**
     * Default Constructor
     */
    public Trade() {}

    /**
     * Main Constructor
     * @param input is the Input of the Trade
     * @param output is the Output of the Trade
     */
    public Trade(ArrayList<Resource> input, ArrayList<Item> output) {
        this.input = input;
        this.output = output;
    }

    /**
     * Output Creator
     * @return is an ArrayList of Items as a Trade Output
     */
    public ArrayList<Item> createOutput() {
        return this.getOutput();
    }

    public void setInput(ArrayList<Resource> input) {
        this.input = input;
    }

    public void setOutput(ArrayList<Item> output) {
        this.output = output;
    }

    public ArrayList<Resource> getInput() {
        return input;
    }

    public ArrayList<Item> getOutput() {
        return output;
    }
}
