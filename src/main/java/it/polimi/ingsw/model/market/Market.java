package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exceptions.marketExceptions.IllegalChoiceException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;


import java.util.ArrayList;

public class Market {
    private Marble[][] marketMatrix = new Marble[3][4];
    private Marble spareMarble;

    public ArrayList<Item> getResources(char line, int index, Player p) throws IllegalArgumentException, IllegalChoiceException {

        ArrayList<Item> list = new ArrayList<Item>();

        if(line == 'r'){

            if(index >= 0 && index < 3){

                for(int i = 0; i < 4; i ++ ){
                    if(marketMatrix[index][i].returnItem(p) != null)
                        list.add(marketMatrix[index][i].returnItem(p));
                }

            } else {
                throw new IllegalArgumentException("Index out of bound");
            }
        } else if (line == 'c'){

            if(index >= 0 && index < 4){

                for(int i = 0; i < 3; i++){
                    if(marketMatrix[i][index].returnItem(p) != null)
                        list.add(marketMatrix[i][index].returnItem(p));
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

    private void updateMarket(char line, int index){
        Marble tempMarble = spareMarble;

        if(line == 'r'){
            spareMarble = marketMatrix[index][3];

            for(int i = 3 ; i > 0 ; i--){
                marketMatrix[index][i] = marketMatrix[index][i-1];
            }

            marketMatrix[index][0] = tempMarble;

        }
        else if(line == 'c'){
            spareMarble = marketMatrix[index][2];

            for(int i = 2; i > 0; i--){
                marketMatrix[i][index] = marketMatrix[i-1][index];
            }

            marketMatrix[0][index] = tempMarble;

        }
    }

    public Marble[][] getMatrix(){
        return marketMatrix;
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

        if(!(this.spareMarble.returnItem(p).equals(otherMarket.spareMarble.returnItem(p)))) return false;

        for(int i = 0; i < 3; i++) {

            for(int j = 0; j < 4; j++) {
                boolean one = this.marketMatrix[i][j].returnItem(p) == null;
                boolean other = otherMarket.marketMatrix[i][j].returnItem(p) == null;

                if(one || other) {
                    if (one == other) continue;
                    return false;
                }

                if (!(this.marketMatrix[i][j].returnItem(p).equals(otherMarket.marketMatrix[i][j].returnItem(p))))
                    return false;
            }
        }
        return true;
    }

}