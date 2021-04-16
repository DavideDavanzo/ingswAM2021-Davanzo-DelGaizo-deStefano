package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.SharedArea;

import java.util.Random;
import java.util.Stack;

/**
 * Class that represents Lorenzo during a solo match
 */
public class LorenzoIlMagnifico {
    private int blackCrossPosition;
    private Stack<LorenzoToken> lorenzoList = new Stack<LorenzoToken>();
    private SharedArea sharedArea;
    private CardMarket cardMarket;


    /**
     * method flips the tokens used to play a solo match
     * @return
     */
    public void flipActionToken() throws LossException {
        LorenzoToken token = lorenzoList.remove(0);
        token.flip();
        token.activate(this);
    }

    public void setLorenzoList(Stack<LorenzoToken> lorenzoList) {
        this.lorenzoList = lorenzoList;
    }

    public Stack<LorenzoToken> getLorenzoList() {
        return lorenzoList;
    }

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    public void move(int step){
        blackCrossPosition = blackCrossPosition + step;
    }

    public void shuffleToken(){

        Random random = new Random();

        for(int i = 0; i < lorenzoList.size(); i ++ ){
            int index = i + random.nextInt(lorenzoList.size() - i);

            LorenzoToken temp = lorenzoList.get(index);
            lorenzoList.set(index, lorenzoList.get(i));
            lorenzoList.set(i, temp);
        }

    }

    public SharedArea getSharedArea() {
        return sharedArea;
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }
}
