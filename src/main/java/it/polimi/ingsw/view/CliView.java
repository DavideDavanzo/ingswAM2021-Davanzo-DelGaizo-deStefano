package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CliView extends View {

    private final SocketHandler socketHandler;

    public CliView(SocketHandler socketHandler){
        this.socketHandler = socketHandler;
    }

    @Override
    public void update(Message message) {
        message.apply(this);
    }

    @Override
    public void start() {
        socketHandler.addObserver(this);
        welcome();
        login();
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

    public void login() {
        System.out.println("Please enter your username: ");
        Scanner stdIn = new Scanner(System.in);
        socketHandler.setUsername(stdIn.nextLine());
        //TODO: validate username
        socketHandler.sendMessage(new LoginRequest());
        socketHandler.waitServerMessage();      //wait LoginReply
    }

    @Override
    public void askQuery(String msg) {
        System.out.println("Server: " + msg);
        socketHandler.sendMessage(new ReplyMessage(socketHandler.getStdIn().nextLine()));
    }

    @Override
    public void showLogin(String msg, boolean successful) {
        showMessage(msg);
    }

    @Override
    public void showError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showMessage(String msg){
        System.out.println("Server: " + msg);
    }

    @Override
    public void onLoginReply(LoginReply message) {
        showMessage(message.getMsg());
        if(!message.isSuccessful())
            login();
    }

    private void welcome(){
        System.out.println("Welcome to Maestri del Rinascimento!");
    }

}
