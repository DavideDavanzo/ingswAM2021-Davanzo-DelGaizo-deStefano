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
        clientHandler.addObserver(this);
        Thread thread = new Thread(clientHandler::waitClientMessage);
        thread.start();
        clientHandler.startPinger();
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
    public void askBlankResources(String msg) {
        clientHandler.sendMessage(new ResourceRequest(msg));
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

    public void stopTimer(){
        clientHandler.stopTimer();
    }

    @Override
    public void checkConnection() {

    }

    public ServerClientHandler getClientHandler(){
        return clientHandler;
    }

    public void sendMessage(Message message){
        clientHandler.sendMessage(message);
    }

}
