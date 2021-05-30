package it.polimi.ingsw.model.playerboard.path;

public class PopeToken  {

    private boolean faceUp;
    private int victoryPoints;

    public PopeToken(){}

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

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
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
