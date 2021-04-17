package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.ECardColor;

import java.util.HashMap;

public class ColorRequirement extends Requirement {

    HashMap<ECardColor, Integer> colors;

    public ColorRequirement() { }

    public ColorRequirement(HashMap<ECardColor, Integer> colors) {
        this.colors = colors;
    }

    @Override
    public boolean validateOn(Player p) {
        return p.getPlayerBoard().getDevelopmentCardsArea().hasColors(colors);
    }

    @Override
    public Object getAttribute() {
        return colors;
    }

}
