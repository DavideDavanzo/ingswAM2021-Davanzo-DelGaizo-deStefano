package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.server.ServerClientHandler;

public class VirtualView extends View{

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
    }

    @Override
    public void askNumberOfPlayers() {
        clientHandler.sendMessage(new PlayersNumRequest());
    }

    @Override
    public void askUsername() {
    }

    @Override
    public void askQuery(String msg) {
        clientHandler.sendMessage(new QueryMessage(msg));
    }

    @Override
    public void showMessage(String msg) {

    }

    public ServerClientHandler getClientHandler(){
        return clientHandler;
    }

}
