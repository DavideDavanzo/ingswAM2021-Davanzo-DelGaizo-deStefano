package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CliView extends View {

    private SocketHandler socketHandler;

    public CliView(SocketHandler socketHandler){
        this.socketHandler = socketHandler;
    }

    @Override
    public void update(Message message) {
        message.apply(this);
    }

    @Override
    public void start() {
        askUsername();
        socketHandler.addObserver(this);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(socketHandler::waitServerMessage);
        executor.submit(socketHandler::waitClientCommand);
    }

    @Override
    public void askNumberOfPlayers() {
        System.out.println("Server: As no other available match already exists, you get to create a new one... ");
        System.out.println("Server: How many players would you like this new match to be composed of? Type a number between 1 and 4");
        try{
            socketHandler.sendMessage(new PlayersNumber(Integer.parseInt(socketHandler.getStdIn().nextLine())));
        } catch(NumberFormatException e) {
            System.out.println("ERROR: wrong format");
            askNumberOfPlayers();
        }
    }

    public void askUsername() {
        System.out.println("Welcome to Maestri del Rinascimento!");
        System.out.println("Please enter your username: ");
        Scanner stdIn = new Scanner(System.in);
        String username = stdIn.nextLine();
        socketHandler.setUsername(username);
        socketHandler.sendMessage(new LoginRequest());
    }

    @Override
    public void askQuery(String msg) {
        System.out.println("Server: " + msg);
        socketHandler.sendMessage(new ReplyMessage(socketHandler.getStdIn().nextLine()));
    }

    @Override
    public void showMessage(String msg){
        System.out.println("Server: " + msg);
    }

}
