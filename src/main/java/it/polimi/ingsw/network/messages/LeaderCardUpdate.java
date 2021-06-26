package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.view.ClientView;

import java.util.LinkedList;

public class LeaderCardUpdate extends Message{

    private int index;

    private LinkedList<LeaderCard> leaderCards;

    private LeaderCardUpdate(){

    }

    public LeaderCardUpdate(int index) {
        this.index = index;
    }

    public LeaderCardUpdate(LinkedList<LeaderCard> leaderCards){
        this.leaderCards = leaderCards;
    }

    @Override
    public void apply(ClientView view) {
        if(leaderCards == null)
            view.updateActiveLeader(index);
        else    view.updateLeaderCards(leaderCards);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public int getIndex() {
        return index;
    }

    public LinkedList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(LinkedList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }
}
