package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

/**
 * <\h1>Development Card</\h1>
 * A Development Card contains a unique {@link Trade}, a cost and
 * some Victory Points.
 */
public class DevelopmentCard extends Card {

    private int level;
    private ECardColor color;
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

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public void setCost(ArrayList<Resource> cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public ECardColor getColor() {
        return color;
    }

    public ArrayList<Resource> getCost() {
        return cost;
    }

    public Trade getTrade() {
        return trade;
    }

}
