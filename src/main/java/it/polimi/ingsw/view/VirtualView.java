package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardParser;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.server.ServerClientHandler;

import java.util.ArrayList;

public class VirtualView extends View {

    private final ServerClientHandler clientHandler;

    public VirtualView(ServerClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message) {
        notifyObservers(message);
    }

    @Override
    public void start() {
        System.out.println("starting virtual view...");
        clientHandler.addObserver(this);
        Thread thread = new Thread(clientHandler::waitClientMessage);
        thread.start();
    }

    @Override
    public void askQuery(String msg) {
        clientHandler.sendMessage(new QueryMessage(msg));
    }

    @Override
    public void askNumberOfPlayers() {
        clientHandler.sendMessage(new PlayersNumRequest());
    }

    @Override
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {
        LeaderCardParser parser = new LeaderCardParser();
        clientHandler.sendMessage(new LeaderRequest(parser.serialize(leaderCards)));
    }

    @Override
    public void showLogin(String msg, boolean successful) {
        clientHandler.sendMessage(new LoginReply(msg, successful));
    }

    @Override
    public void showError(String msg) {
        clientHandler.sendMessage(new ErrorMessage(msg));
    }

    @Override
    public void showMessage(String msg) {
        clientHandler.sendMessage(new InfoMessage(msg));
    }

    @Override
    public void onLoginReply(LoginReply message) {

    }

    public ServerClientHandler getClientHandler(){
        return clientHandler;
    }


}
