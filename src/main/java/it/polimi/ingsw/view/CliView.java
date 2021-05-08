package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.LoginRequest;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.ReplyMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CliView extends View {

    SocketHandler socketHandler;
    ExecutorService executor = Executors.newCachedThreadPool();

    public CliView(SocketHandler socketHandler){
        this.socketHandler = socketHandler;
    }

    @Override
    public void update(Message message) {
        message.apply(this);
    }

    @Override
    public void start(){
        socketHandler.addObserver(this);
        executor.submit(() -> socketHandler.waitServerMessage());
        executor.submit(() -> socketHandler.waitClientCommand());
    }

    @Override
    public void askNumberOfPlayers(){
        System.out.println("Server: As no other available match already exists, you get to create a new one... ");
        System.out.println("Server: How many players would you like this new match to be composed of? Type a number between 1 and 4");
        socketHandler.sendMessage(new LoginRequest(socketHandler.getStdIn().nextLine()));
    }

    @Override
    public void askQuery(String msg){
        System.out.println("Server: " + msg);
        socketHandler.sendMessage(new ReplyMessage(socketHandler.getStdIn().nextLine()));
    }

}
