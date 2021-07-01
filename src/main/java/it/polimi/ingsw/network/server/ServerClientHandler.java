package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Disconnection;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.utils.Parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Handles socket connection
 */
public class ServerClientHandler extends Observable implements Runnable{

    private String username;

    private Scanner socketIn;
    private PrintWriter socketOut;
    private final Socket clientSocket;

    Thread timerThread;

    public ServerClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            socketIn = new Scanner(this.clientSocket.getInputStream());
            socketOut = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        timerThread = null;
    }

    @Override
    public void run() {
        while (!clientSocket.isClosed()) {
            try {
                Message message = (Message) Parser.deserialize(socketIn.nextLine(), Message.class);
                notifyObservers(message);
            } catch (NoSuchElementException e){
                System.out.println("Client disconnected");
                notifyObservers(new Disconnection());
                break;
            }
        }
    }

    public void sendMessage(Message message){
        String msg;
        System.out.println("Sent: " + (msg = Parser.serialize(message)));
        socketOut.println(msg);
    }

    public Message returnClientMessage(){
        Message message = null;
        message = (Message) Parser.deserialize(socketIn.nextLine(), Message.class);
        System.out.println("Received: " + Parser.serialize(message));

        return message;
    }

    public Scanner getSocketIn() {
        return socketIn;
    }

    public PrintWriter getSocketOut() {
        return socketOut;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
