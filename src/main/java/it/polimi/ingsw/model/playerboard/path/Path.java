package it.polimi.ingsw.model.playerboard.path;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.EndGameException;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.FaithPoint;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.CliPrinter;

import java.util.ArrayList;

public class Path extends Observable implements CliPrinter {

    private final Square[] track = new Square[25];
    private final ArrayList<PopeToken> popeTokens = new ArrayList<>();
    private final FaithPoint currentFaithPoints = new FaithPoint();

    public Path() {

        //creation of the 25 squares that compose the track
        for(int i=0; i<track.length; i++)
            track[i] = new Square();

        //initialization of the three papal token
        popeTokens.add(new PopeToken(2));       //first token gives 2 victory points if flipped
        popeTokens.add(new PopeToken(3));       //second token gives 3 victory points if flipped
        popeTokens.add(new PopeToken(4));       //third token gives 4 victory points if flipped

        //set specific victory points to the position that have them
        for(int i=3; i<=5; i++)
            track[i].setVictoryPoints(1);
        for(int i=6; i<=8; i++)
            track[i].setVictoryPoints(2);
        for(int i=9; i<=11; i++)
            track[i].setVictoryPoints(4);
        for(int i=12; i<=14; i++)
            track[i].setVictoryPoints(6);
        for(int i=15; i<=17; i++)
            track[i].setVictoryPoints(9);
        for(int i=18; i<=20; i++)
            track[18].setVictoryPoints(12);
        for(int i=21; i<=23; i++)
            track[21].setVictoryPoints(16);
        track[24].setVictoryPoints(20);

        //setting the three Pope spaces
        track[8].setPopeSquare(true);
        track[16].setPopeSquare(true);
        track[24].setPopeSquare(true);

        //for all positions of a "report to the Vatican" section, set the pointers to the Pope space and to the papal token
        for(int i=5; i<=8; i++){
            track[i].setVaticanReport(track[8]);        //positions from 5 to 8 link to the Pope space in 8th position
            track[i].setPopeToken(popeTokens.get(0));   //and to the first papal token
        }
        for(int i=12; i<=16; i++){
            track[i].setVaticanReport(track[16]);       //positions from 12 to 16 link to the Pope space in 16th position
            track[i].setPopeToken(popeTokens.get(1));   //and to the second papal token
        }
        for(int i=19; i<=24; i++){
            track[i].setVaticanReport(track[24]);       //positions from 19 to 24 link to the Pope space in 24th position (the finish line)
            track[i].setPopeToken(popeTokens.get(2));   //and to the third papal token
        }

    }

    //add one by one incoming faith points
    public boolean moveForward(int totSteps) throws InvalidInputException {
        for(int i=0; i<totSteps; i++) {
            if(getCurrentPositionAsInt() == 24) return true;
            currentFaithPoints.update(new FaithPoint(1));
            if(getCurrentPositionAsInt() == 24) {
                notifyObservers(this);
                return true;
            }
        }
        if(totSteps != 0)
            notifyObservers(this);
        return false;
    }

    //returns if the player is currently in a Vatican report section
    private boolean isVaticanReport(int position) {
        return track[getCurrentPositionAsInt()].getVaticanReport() == track[position];
    }

    //if in the right position, flip the player's papal token
    public void applyVaticanReport(int position){
        if(isVaticanReport(position))
            track[getCurrentPositionAsInt()].getPopeToken().flip();
        track[position].setPopeSquare(false);       //at the end disable the Pope space
        //notifyObservers(this);
    }

    //returns the sum between the points given by the position and the points given by the papal tokens if face up
    public int getPathVictoryPoints() {
        return popeTokens.stream().filter(PopeToken::isFaceUp).mapToInt(PopeToken::getVictoryPoints).sum() + track[getCurrentPositionAsInt()].getVictoryPoints();
    }

    public Square[] getTrack() {
        return track;
    }

    public ArrayList<PopeToken> getPopeTokens() {
        return popeTokens;
    }

    public FaithPoint getCurrentFaithPoints() {
        return currentFaithPoints;
    }

    public int getCurrentPositionAsInt() {
        return getCurrentFaithPoints().getVolume();
    }

    public String crossValue(int value){
        int position = getCurrentPositionAsInt();
        String box;
        if(position == value && position < 10){
            box = Color.ANSI_RED.escape() + "†";
        }
        else
            if(position == value){
                box = Color.ANSI_RED.escape() + "† ";
        }
        else
            box = String.valueOf(( value));

        return box;
    }

    


    @Override
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("            ┌─────"+ Color.ANSI_RED.escape() +"───────────────"+ Color.ANSI_WHITE.escape() +"─────────┐    ┌─────────┐ "+ Color.ANSI_RED.escape() +"   ┌─────────────────────────────┐"+ Color.ANSI_WHITE.escape() +"\n")
                     .append("            │  " + crossValue(5) + Color.ANSI_RED.escape() + "  │ " + crossValue(6) +  " │ " + crossValue(7) + "  │ " + crossValue(8) + "  │ "+ Color.ANSI_WHITE.escape()+ crossValue(9) +"  │ " + crossValue(10) + " │    │  " + popeTokens.get(2).popeToken() +"  │ "+ Color.ANSI_RED.escape() +"   │ " + crossValue(19) + " │ "+ crossValue(20) + " │ " + crossValue(21) + " │ " + crossValue(22) +" │ " + crossValue(23) + " │ " + crossValue(24) + " │"+ Color.ANSI_WHITE.escape() + "\n")
                     .append("            │────"+ Color.ANSI_RED.escape() +"┌───────────────"+ Color.ANSI_WHITE.escape() +"────┐────│    │         │  "+ Color.ANSI_RED.escape() +"  │────┌────────────────────────┘"+ Color.ANSI_WHITE.escape() +"\n")
                     .append("            │ "+ crossValue(4) + "  │    ┌─────────┐    │ " + crossValue(11) + " │    └─────────┘    │ " + crossValue(18) + " │      ┌─────────┐ \n")
                     .append("    ┌───────┘────│    │  " + popeTokens.get(0).popeToken() + "  │"+ Color.ANSI_RED.escape() +"    │────└───────────────────"+ Color.ANSI_WHITE.escape() +"┘────│      │  " + popeTokens.get(1).popeToken() + "  │\n")
                     .append("    │ " + crossValue(1) + " │ " + crossValue(2) + " │ " + crossValue(3) + "  │    │         │ "+ Color.ANSI_RED.escape() +"   │ " + crossValue(12) + " │ "+ crossValue(13) + " │ " + crossValue(14) +" │ "+ crossValue(15) + " │ " + crossValue(16) + " │ "+ Color.ANSI_WHITE.escape() +crossValue(17) + " │      │         │\n ")
                     .append("   └────────────┘    └─────────┘"+ Color.ANSI_RED.escape() +"    └────────────────────────"+ Color.ANSI_WHITE.escape() +"─────┘      └─────────┘    ");


        return stringBuilder.toString();
    }
}
