package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.ECardColor;

import java.util.HashMap;
import java.util.Map;

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
    public Object returnAttribute() {
        return getColorLevel();
    }

    public HashMap<ECardColor, Integer> getColorLevel(){
        return colorLevel;
    }


    public void setColorLevel(HashMap<ECardColor, Integer> colorLevel) {
        this.colorLevel = colorLevel;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Map.Entry<ECardColor, Integer> entry : colorLevel.entrySet()) {
            s.append("Color ");
            s.append(entry.getKey().toString());
            s.append(" , Level ");
            s.append(entry.getValue());
        }
        return s.toString();
    }

    @Override
    public String print() {
        StringBuilder p = new StringBuilder();
        for (ECardColor color : colorLevel.keySet()) {
            p.append(color.print() + "lvl: " + colorLevel.get(color));
        }
        return p.toString();

    }

}
