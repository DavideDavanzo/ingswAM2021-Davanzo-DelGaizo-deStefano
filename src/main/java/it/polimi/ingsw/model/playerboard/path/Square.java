package it.polimi.ingsw.model.playerboard.path;

/**
 * A Square is the smallest unit of the {@link Path}, it may contain
 * victory points, Vatican Report or {@link PopeToken}.
 */
public class Square {

    private boolean popeSquare;
    private int victoryPoints;
    private Square vaticanReport;       //pointer to the Pope space at the end of its report section
    private PopeToken popeToken;        //pointer to the papal token of its report section

    public void setVaticanReport(Square vaticanReport) {
        this.vaticanReport = vaticanReport;
    }

    public void setPopeToken(PopeToken popeToken) {
        this.popeToken = popeToken;
    }

    public Square getVaticanReport() {
        return vaticanReport;
    }

    public PopeToken getPopeToken() {
        return popeToken;
    }

    public void setPopeSquare(boolean popeSquare) {
        this.popeSquare = popeSquare;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public boolean isPopeSquare() {
        return popeSquare;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

}
