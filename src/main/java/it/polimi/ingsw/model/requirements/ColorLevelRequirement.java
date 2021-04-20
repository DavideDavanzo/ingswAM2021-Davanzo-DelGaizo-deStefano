package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.ECardColor;

import java.util.HashMap;

public class ColorLevelRequirement extends Requirement {

    private HashMap<ECardColor, Integer> colorLevel;

    public ColorLevelRequirement() { }

    public ColorLevelRequirement(HashMap<ECardColor, Integer> colorLevel) {
        this.colorLevel = colorLevel;
    }

    @Override
    public boolean validateOn(Player p) {
        return p.getPlayerBoard().getDevelopmentCardsArea().hasColorLevel(colorLevel);
    }

    @Override
    public Object getAttribute() {
        return colorLevel;
    }
}
