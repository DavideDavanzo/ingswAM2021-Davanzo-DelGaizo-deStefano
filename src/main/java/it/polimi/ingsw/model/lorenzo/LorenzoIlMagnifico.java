package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.CardMarket;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class that represents Lorenzo during a solo match
 */
public class LorenzoIlMagnifico {

    private int blackCrossPosition;
    private LinkedList<LorenzoToken> lorenzoList;
    private CardMarket cardMarket;

    public LorenzoIlMagnifico() {
        this.blackCrossPosition = 0;
        this.lorenzoList = initTokens();
    }

    public LorenzoIlMagnifico(CardMarket cardMarket) {
        this.blackCrossPosition = 0;
        this.lorenzoList = initTokens();
        this.cardMarket = cardMarket;
    }

    /**
     * method flips the tokens used to play a solo match
     */
    public void flipActionToken() throws LossException {
        LorenzoToken token = lorenzoList.remove();
        token.flip();
        token.activate(this);
        token.flip();
        lorenzoList.add(token);
    }

    public void setLorenzoList(LinkedList<LorenzoToken> lorenzoList) {
        this.lorenzoList = lorenzoList;
    }

    public LinkedList<LorenzoToken> getLorenzoList() {
        return lorenzoList;
    }

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    /**
     * This method increases Lorenzo's position
     * @param step is used to set the value of the increment
     */
    public void move(int step){
        blackCrossPosition = blackCrossPosition + step;
    }

    /**
     * This method shuffles randomly all the tokens
     */
    public void shuffleToken(){

        Random random = new Random();

        for(int i = 0; i < lorenzoList.size(); i ++ ){
            int index = i + random.nextInt(lorenzoList.size() - i);

            LorenzoToken temp = lorenzoList.get(index);
            lorenzoList.set(index, lorenzoList.get(i));
            lorenzoList.set(i, temp);
        }

    }

    public LinkedList<LorenzoToken> initTokens() {
        LinkedList<LorenzoToken> list = new LinkedList<>();
        list.push(new TossDevCardsToken(ECardColor.GREEN));
        list.push(new TossDevCardsToken(ECardColor.YELLOW));
        list.push(new TossDevCardsToken(ECardColor.BLUE));
        list.push(new TossDevCardsToken(ECardColor.PURPLE));
        list.push(new CrossToken());
        list.push(new CrossToken());
        list.push(new CrossAndShuffleToken());
        return list;
    }


    public CardMarket getCardMarket() {
        return cardMarket;
    }
}
