package it.polimi.ingsw.network.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.network.messages.Disconnection;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.TimeoutMessage;
import it.polimi.ingsw.observingPattern.Observable;

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
    private final ObjectMapper objectMapper;
    private final Socket clientSocket;

    Thread timerThread;

    private boolean exit;

    public ServerClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            socketIn = new Scanner(this.clientSocket.getInputStream());
            socketOut = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
        timerThread = null;
        exit = false;
    }

    @Override
    public void run() {
        while (!clientSocket.isClosed()) {
            try {
                Message message = objectMapper.readValue(socketIn.nextLine(), Message.class);
                System.out.println("Received: " + objectMapper.writeValueAsString(message));
                notifyObservers(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (NoSuchElementException e){
                System.out.println("Client disconnected");
                notifyObservers(new Disconnection());
                break;
            }
        }
    }

    public void startTimer(int milliseconds){
        exit = false;
        timerThread = new Thread(() ->
                                {
                                    try {
                                        Thread.sleep(milliseconds);
                                        if(!exit) {
                                            notifyObservers(new TimeoutMessage(username));
                                            clientSocket.close();
                                        } else {
                                            System.out.println("stopping timer");
                                        }
                                    } catch (InterruptedException | IOException e) {
                                        e.printStackTrace();
                                    }
                                });
        timerThread.start();
        System.out.println("timer started");
    }


    public void stopTimer(){
        exit = true;
        System.out.println("exit = true");
        timerThread = null;
    }


    public void sendMessage(Message message){
        String msg;
        try {
            System.out.println("Sent: " + (msg = objectMapper.writeValueAsString(message)));
            socketOut.println(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Message returnClientMessage(){
        Message message = null;
        try {
            message = objectMapper.readValue(socketIn.nextLine(), Message.class);
            System.out.println("Received: " + objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Scanner getSocketIn() {
        return socketIn;
    }

    public PrintWriter getSocketOut() {
        return socketOut;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
