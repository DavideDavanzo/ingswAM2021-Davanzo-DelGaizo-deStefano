package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.view.View;

public class BuyCardCmd extends Command{

    private ECardColor color;

    private int level;

    public BuyCardCmd(){
        super();
    }

    public BuyCardCmd(ECardColor color, int level){
        this.color = color;
        this.level = level;
    }

    @Override
    public void apply(View view) {

    }

    public ECardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }
}
