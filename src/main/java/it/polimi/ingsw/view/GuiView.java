package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.messages.Ack;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

public class GuiView extends View{

    @Override
    public void start() {

    }

    @Override
    public void askNumberOfPlayers() {

    }

    @Override
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void askBlankResources(String msg) {

    }

    @Override
    public void onLoginReply(LoginReply message) {

    }

    @Override
    public void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves) {

    }

    @Override
    public void askToChangeWhiteMarbles(ArrayList<Item> items, int count) {

    }

    @Override
    public void checkConnection() {

    }

    @Override
    public void chooseInfo() {

    }

    @Override
    public void askCommand() {

    }

    @Override
    public void activateLeaderCards() {

    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {

    }

    @Override
    public void processAck(Ack ack) {

    }

    @Override
    public void showLogin(String msg, boolean successful) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void update(Message message) {

    }

}
