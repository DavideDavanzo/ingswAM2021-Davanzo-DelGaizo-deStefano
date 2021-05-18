package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.View;

public class MarketResourcesCmd extends Command{

    private char line;

    private int index;

    public MarketResourcesCmd(char line, int index){
        this.line = line;
        this.index = index;
    }

    @Override
    public void apply(View view) {

    }
}
