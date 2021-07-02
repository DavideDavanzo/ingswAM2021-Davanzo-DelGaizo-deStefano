package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.ECardColor;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements one of the leader card requirement
 * it requires a card of a certain color and a certain level
 */
public class ColorLevelRequirement extends Requirement {

    private HashMap<ECardColor, Integer> colorLevel;
    public ColorLevelRequirement() { }

    public ColorLevelRequirement(HashMap<ECardColor, Integer> colorLevel) {
        this.colorLevel = colorLevel;
    }

    /**
     * Validates the requirement on a player.
     * @param p is the Player on which the control is done.
     * @return true if the player matches the requirement.
     */
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
            p.append(color.print() + " lvl: " + colorLevel.get(color));
        }
            p.append("                           ");


        return p.toString();

    }

}
