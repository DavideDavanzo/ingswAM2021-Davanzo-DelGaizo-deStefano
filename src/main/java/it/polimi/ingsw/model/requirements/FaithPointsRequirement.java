package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.FaithPoint;

public class FaithPointsRequirement extends Requirement{

    FaithPoint points;

    public FaithPointsRequirement() { }

    public FaithPointsRequirement(FaithPoint points) {
        this.points = points;
    }


    @Override
    public boolean validateOn(Player p) {
        return p.getPlayerBoard().getPath().getCurrentPositionAsInt() >= points.getVolume() ? true : false;
    }

    @Override
    public Object getAttribute() {
        return points;
    }
}
