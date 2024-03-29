package it.polimi.ingsw.model.sharedarea.market;

import it.polimi.ingsw.exceptions.marketExceptions.IllegalArgumentException;
import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements the market of the game
 */
public class Market extends Observable implements CliPrinter {

    private Marble[][] marketMatrix;
    private Marble spareMarble;
    private ArrayList<Marble> marbleArrayList = new ArrayList<>();

    public Market() {
        marketMatrix = new Marble[3][4];
        init();
    }

    /**
     * Initializes market
     */
    private void init() {

        ArrayList<Marble> marbleArrayList = new ArrayList<Marble>();

        marbleArrayList.add(new WhiteMarble());
        marbleArrayList.add(new WhiteMarble());
        marbleArrayList.add(new WhiteMarble());
        marbleArrayList.add(new WhiteMarble());
        marbleArrayList.add(new BlueMarble());
        marbleArrayList.add(new BlueMarble());
        marbleArrayList.add(new YellowMarble());
        marbleArrayList.add(new YellowMarble());
        marbleArrayList.add(new GreyMarble());
        marbleArrayList.add(new GreyMarble());
        marbleArrayList.add(new PurpleMarble());
        marbleArrayList.add(new PurpleMarble());
        marbleArrayList.add(new RedMarble());

        shuffle(marbleArrayList);
        for(int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 4; j++) {
                marketMatrix[i][j] = marbleArrayList.get(i * 4 + j);
            }
        }
        spareMarble = marbleArrayList.get(12);
    }

    /**
     * Shuffles the marbles.
     * @param marbles
     */
    public void shuffle (ArrayList<Marble> marbles) {

        Random random = new Random();

        for(int i = 0; i < marbles.size(); i++ ) {
            int index = i + random.nextInt(marbles.size() - i);

            Marble temp = marbles.get(index);
            marbles.set(index, marbles.get(i));
            marbles.set(i, temp);
        }
    }


    /**
     * Takes the resources that correspond to the marbles.
     * @param line is either 'r' for rows or 'c' for columns.
     * @param index goes from 0 to 2 for rows and 0 to 3 for columns.
     * @return a list with the items.
     * @throws IllegalArgumentException if the index is out of bound.
     * @throws IllegalChoiceException if the char is not 'r' or 'c'.
     */
    public ArrayList<Item> getResources(char line, int index) throws IllegalArgumentException, IllegalChoiceException {

        ArrayList<Item> list = new ArrayList<Item>();

        if(line == 'r'){

            if(index >= 0 && index < 3){

                for(int i = 0; i < 4; i ++ ){
                    list.add(marketMatrix[index][i].returnItem());
                }

            } else {
                throw new IllegalArgumentException("Index out of bound");
            }
        } else if (line == 'c'){

            if(index >= 0 && index < 4){

                for(int i = 0; i < 3; i++){
                    list.add(marketMatrix[i][index].returnItem());
                }

            } else {
                throw new IllegalArgumentException("Index out of bound");
            }
        } else {
            throw new IllegalChoiceException("The only admitted choices are either 'r' or 'c' ");
        }

        updateMarket(line,index);

        return list;
    }

    /**
     * Rearranges the market after a resource pick.
     * @param line the chosen line.
     * @param index the chosen index.
     */
    private void updateMarket(char line, int index){
        Marble tempMarble = spareMarble;

        if(line == 'r'){
            spareMarble = marketMatrix[index][0];

            for(int i = 0 ; i < 3 ; i++){
                marketMatrix[index][i] = marketMatrix[index][i+1];
            }

            marketMatrix[index][3] = tempMarble;

        }
        else if(line == 'c'){
            spareMarble = marketMatrix[0][index];

            for(int i = 0; i < 2; i++) {
                marketMatrix[i][index] = marketMatrix[i+1][index];
            }

            marketMatrix[2][index] = tempMarble;

        }

        notifyObservers(this);

    }

    public Marble[][] getMarketMatrix(){
        return marketMatrix;
    }

    public Marble getSpareMarble(){
        return spareMarble;
    }

    public void setMarketMatrix(Marble[][] marketMatrix) {
        this.marketMatrix = marketMatrix;
    }

    public void setSpareMarble(Marble spareMarble) {
        this.spareMarble = spareMarble;
    }

    public boolean equals(Object o){

        if (!(o instanceof Market)) return false;

        Market otherMarket = (Market) o;
        Player p = new Player();

        if(!(this.spareMarble.returnItem().equals(otherMarket.spareMarble.returnItem()))) return false;

        for(int i = 0; i < 3; i++) {

            for(int j = 0; j < 4; j++) {

                if (!(this.marketMatrix[i][j].returnItem().equals(otherMarket.marketMatrix[i][j].returnItem())))
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                stringBuilder.append(marketMatrix[i][j] +"  ");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n" + spareMarble.toString());

        return stringBuilder.toString();
    }

    @Override
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(spareMarble.print());
        for(int i = 0; i < 3; i++){
            stringBuilder.append( "\n");
            stringBuilder.append("  ");
            for(int j = 0; j < 4; j++){
                stringBuilder.append(marketMatrix[i][j].print() + " ");
            }
            stringBuilder.append("←");
        }
        stringBuilder.append("\n  ↑ ↑ ↑ ↑");
        return stringBuilder.toString();
    }

    public ArrayList<Marble> getMarbleArrayList() {
        return marbleArrayList;
    }
}


