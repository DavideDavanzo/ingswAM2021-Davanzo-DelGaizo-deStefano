package it.polimi.ingsw.model.playerboard.path;

public class PopeToken {

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

}
