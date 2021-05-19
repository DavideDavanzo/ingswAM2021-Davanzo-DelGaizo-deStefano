package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.CliBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

public class ColorRequirement extends Requirement {

    HashMap<ECardColor, Integer> colors;

    public ColorRequirement() {
    }

    public ColorRequirement(HashMap<ECardColor, Integer> colors) {
        this.colors = colors;
    }

    @Override
    public boolean validateOn(Player p) {
        return p.getPlayerBoard().getDevelopmentCardsArea().hasColors(colors);
    }

    @Override
    public Object returnAttribute() {
        return getColors();
    }

    public HashMap<ECardColor, Integer> getColors(){
        return colors;
    }


    public void setColors(HashMap<ECardColor, Integer> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator iterator = colors.entrySet().iterator();
        for (Map.Entry<ECardColor, Integer> entry : colors.entrySet()) {
            s.append("Color ");
            s.append(entry.getKey().toString());
            s.append(" , Quantity ");
            s.append(entry.getValue());
            if (iterator.hasNext()) s.append("; ");
        }
        return s.toString();
    }

    @Override
    public String print() {
        StringBuilder p = new StringBuilder();
        for (ECardColor color : colors.keySet()){
            p.append(colors.get(color) + " " + color.print() + " ");
        }
        p.append("                ");
        if(colors.size() == 2){
            p.append("          ");
        }

        return p.toString();

    }
}
