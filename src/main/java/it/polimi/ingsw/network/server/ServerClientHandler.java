package it.polimi.ingsw.network.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PingMessage;
import it.polimi.ingsw.network.messages.TimeoutMessage;
import it.polimi.ingsw.network.observingPattern.Observable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientHandler extends Observable {

    private Scanner socketIn;
    private PrintWriter socketOut;
    private final ObjectMapper objectMapper;
    private final Socket clientSocket;

    Thread pingerThread;
    Thread timerThread;

    public ServerClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            socketIn = new Scanner(this.clientSocket.getInputStream());
            socketOut = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
        pingerThread = new Thread(() ->
                            {
                               while (!clientSocket.isClosed()){
                                   try {
                                       Thread.sleep(5000);
                                       sendMessage(new PingMessage());
                                       timerThread.start();
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               }
                            });
        timerThread = new Thread(() ->
                            {
                                try {
                                    Thread.sleep(1000);
                                    notifyObservers(new TimeoutMessage());
                                    clientSocket.close();
                                } catch (InterruptedException | IOException e) {
                                    e.printStackTrace();
                                }
                            });
    }

    public void startPinger(){
        pingerThread.start();
    }

    public void stopTimer(){
        timerThread.interrupt();
        timerThread = new Thread(() ->
                            {
                                try {
                                    Thread.sleep(1000);
                                    notifyObservers(new TimeoutMessage());
                                    clientSocket.close();
                                } catch (InterruptedException | IOException e) {
                                    e.printStackTrace();
                                }
                            });
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

    public void waitClientMessage(){
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = objectMapper.readValue(socketIn.nextLine(), Message.class);
                System.out.println("Received: " + objectMapper.writeValueAsString(message));
                notifyObservers(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
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

}
