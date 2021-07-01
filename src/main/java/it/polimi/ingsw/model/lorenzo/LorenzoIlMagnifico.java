package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.LinkedList;
import java.util.Random;

/**
 * Class that represents Lorenzo during a solo match.
 */
public class LorenzoIlMagnifico implements CliPrinter {

    private int blackCrossPosition;
    private LinkedList<LorenzoToken> lorenzoList;
    private CardMarket cardMarket;

    public LorenzoIlMagnifico() {
        this.blackCrossPosition = 0;
        this.lorenzoList = initTokens();
        shuffleTokens();
    }

    /**
     * Constructor that gives Lorenzo the access to che {@link CardMarket}.
     * @param cardMarket
     */
    public LorenzoIlMagnifico(CardMarket cardMarket) {
        this.blackCrossPosition = 0;
        this.lorenzoList = initTokens();
        shuffleTokens();
        this.cardMarket = cardMarket;
    }

    /**
     * Method flips the tokens used to play a solo match.
     */
    public void flipActionToken() throws LossException {
        LorenzoToken token = lorenzoList.remove();
        token.flip();
        token.activate(this);
        token.flip();
        lorenzoList.add(token);
    }

    /**
     * Flips a token and reads the corresponding action.
     * @return a String where the action is portrayed.
     * @throws LossException
     */
    public String flipTokenReadAction() throws LossException {
        StringBuilder builder = new StringBuilder();
        builder.append("Lorenzo activated ").append(lorenzoList.peek().toString()).append('\n');

        flipActionToken();

        builder.append("Current Lorenzo position: ").append(blackCrossPosition);
        return builder.toString();
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
    public void move(int step) throws LossException {
        if(blackCrossPosition + step > 23) {
            blackCrossPosition = 24;
            //TODO: notify observers if any
            throw new LossException("You lost to Lorenzo. He reached the end before you " + "🙁 🙁 🙁");
        }
        else {
            blackCrossPosition = blackCrossPosition + step;
            //TODO: notify observers if any
        }
    }

    /**
     * This method shuffles randomly all the tokens.
     */
    public void shuffleTokens(){

        Random random = new Random();

        for(int i = 0; i < lorenzoList.size(); i ++ ){
            int index = i + random.nextInt(lorenzoList.size() - i);

            LorenzoToken temp = lorenzoList.get(index);
            lorenzoList.set(index, lorenzoList.get(i));
            lorenzoList.set(i, temp);
        }

    }

    /**
     * Initializes {@link LorenzoToken}(s).
     * @return the list of tokens.
     */
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

    @Override
    public String print() {
        return getBlackCrossPosition() + Color.ANSI_BLACK.escape() + " †";
    }
}
