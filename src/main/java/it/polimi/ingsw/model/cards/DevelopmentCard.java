package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * <\h1>Development Card</\h1>
 * A Development Card contains a unique {@link Trade}, a cost and
 * some Victory Points.
 */
public class DevelopmentCard extends Card {

    private ECardColor color;
    private int level;
    private ArrayList<Resource> cost;
    private Trade trade;

    /**
     * Default Constructor
     */
    public DevelopmentCard() {

    }

    /**
     * Points Constructor
     * @param victoryPoints refers to the card's Victory Points
     */
    public DevelopmentCard(int victoryPoints) {
        super(victoryPoints);
    }

    /**
     * Level&Color-Only Constructor
     * @param level goes from 1 up to 3
     * @param color is a {@link ECardColor}
     */
    public DevelopmentCard(ECardColor color, int level) {
        this.level = level;
        this.color = color;
    }

    /**
     * Complete Constructor
     * @param victoryPoints refers to the card's Victory Points
     * @param level goes from 1 up to 3
     * @param color is a {@link ECardColor}
     * @param cost indicates the {@link Resource} the Player spends to buy the Card
     * @param trade indicates the {@link Trade} of the Card
     */
    public DevelopmentCard(ECardColor color,  int level, ArrayList<Resource> cost, Trade trade, int victoryPoints) {
        super(victoryPoints);
        this.level = level;
        this.color = color;
        this.cost = cost;
        this.trade = trade;
    }

    public void setColor(ECardColor color) {
        this.color = color;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public void setCost(ArrayList<Resource> cost) {
        this.cost = cost;
    }

    public ECardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Resource> getCost() {
        return cost;
    }

    public Trade getTrade() {
        return trade;
    }

    @Override
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }

    @Override
    public String toString() {
        return "DevelopmentCard [" +
                " Color: " + color +
                " | Level: " + level +
                " | Cost: " + cost +
                " | Trade: " + trade +
                " | Victory Points: " + getVictoryPoints() +
                " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevelopmentCard that = (DevelopmentCard) o;
        return level == that.level &&
                getVictoryPoints() == that.getVictoryPoints() &&
                color == that.color &&
                this.equalCost(that.cost) &&
                this.trade.equals(that.trade);
    }

    public boolean equalCost(ArrayList<Resource> resources) {
        if(cost == resources) return true;
        if(resources.size() != this.getCost().size()) return false;
        for(Resource r : resources) {
            if(this.getCost().contains(r)) continue;
            return false;
        }
        return true;
    }

    public String printCost() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Resource r : cost) {
            stringBuilder.append(r.print());
        }
        return stringBuilder.toString();
    }

    public String printTrade(){

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder s = new StringBuilder();
        for(Resource resource : trade.getInput()) {
            stringBuilder.append(resource.print());
        }
            for(Item o : trade.getOutput()){
                 s.append(o.print());
            }

        return stringBuilder.toString() + " " +" \u2192 " + " " + s.toString();
    }


    @Override
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ECardColor.getColorMap().get(color).escape() + "╔═════════════════╗\n")
                     .append("║ DEV CARD " + "lvl " + getLevel() + "  ║\n")
                     .append("║ cost  " + Color.ANSI_WHITE.escape() + printCost() + ECardColor.getColorMap().get(color).escape() +  "        ║\n")
                     .append("║ trade " + Color.ANSI_WHITE.escape() + printTrade() + ECardColor.getColorMap().get(color).escape() + "  ║\n")
                     .append("║ vp : " + getVictoryPoints() +  "          ║\n")
                     .append("╚═════════════════╝" + Color.ANSI_WHITE.escape());

            return stringBuilder.toString() + Color.ANSI_WHITE.escape();
        }
}
