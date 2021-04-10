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
                   list.add(marketMatrix[index][i].returnItem(p));
               }
           } else {
               throw new IllegalArgumentException("Index out of bound");
           }
       } else if (line == 'c'){
           if(index >= 0 && index < 4){
               for(int i = 0; i < 3; i++){
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
            spareMarble = marketMatrix[index][4];
            for(int i = 3 ; i > 0 ; i--){
                marketMatrix[index][i] = marketMatrix[index][i-1];
            }
            marketMatrix[index][0] = tempMarble;
        }
        else if(line == 'c'){
            spareMarble = marketMatrix[index][3];
            for(int i = 2; i > 0; i--){
                marketMatrix[i][index] = marketMatrix[i-1][index];
            }
            marketMatrix[0][index] = tempMarble;
        }
   }

    public Marble[][] getMatrix(){
        return marketMatrix;
    }

    public boolean equals(Object o){
        if (!(o instanceof Market))
                return false;
        Market otherMarket = (Market) o;
        for(Marble[] m : this.marketMatrix)

    }

}


