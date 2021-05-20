package it.polimi.ingsw.model.playerboard.path;

import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.view.CliPrinter;

public class PopeToken  {

    private boolean faceUp;
    private final int victoryPoints;

    public PopeToken(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void flip() {
        if(!isFaceUp())
            faceUp = true;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }


    public String popeToken() {

        StringBuilder stringBuilder = new StringBuilder();

            if (isFaceUp())
                stringBuilder.append("VP: " + getVictoryPoints());
            else
                stringBuilder.append("  X  ");

        return stringBuilder.toString();
    }
}
