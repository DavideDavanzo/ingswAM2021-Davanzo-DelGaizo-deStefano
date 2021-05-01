package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return this.equalInput(trade.getInput()) &&
                this.equalOutput(trade.getOutput());
    }

    public boolean equalInput(ArrayList<Resource> resources) {
        if(input == resources) return true;
        if(resources.size() != this.getInput().size()) return false;
        for(Resource r : resources) {
            if(this.getInput().contains(r)) continue;
            return false;
        }
        return true;
    }

    public boolean equalOutput(ArrayList<Item> items) {
        if(output == items) return true;
        if(items.size() != this.getOutput().size()) return false;
        for(Item i : items) {
            if(this.getInput().contains(i)) continue;
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getInput().toString() + " -> " + output.toString();
    }
}
