package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.FaithPoint;
/**
 * This class implements one of the leader card requirement
 * it requires that the player is in square 14 or on
 */
public class FaithPointsRequirement extends Requirement {

    FaithPoint points;

    public FaithPointsRequirement() { }

    public FaithPointsRequirement(FaithPoint points) {
        this.points = points;
    }


    @Override
    public boolean validateOn(Player p) {
        return p.getPlayerBoard().getPath().getCurrentPositionAsInt() >= points.getVolume();
    }

    @Override
    public Object returnAttribute() {
        return getPoints();
    }

    public FaithPoint getPoints(){
        return points;
    }



    public void setPoints(FaithPoint points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Position at least " + points.toString();
    }

    @Override
    public String print() {
        return "14 " + Color.ANSI_RED.escape() + "†                                " + Color.ANSI_WHITE.escape();


    }
}
