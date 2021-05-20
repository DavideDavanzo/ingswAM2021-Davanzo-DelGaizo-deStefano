package it.polimi.ingsw.network.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.network.observingPattern.Observable;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketHandler extends Observable implements Runnable{

    private String username;
    private Scanner socketIn;
    private PrintWriter socketOut;
    private final ObjectMapper objectMapper;
    private final Socket socket;

    public SocketHandler(Socket socket){
        this.socket = socket;
        try {
            socketIn = new Scanner(this.socket.getInputStream());
            socketOut = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
    }

    public void sendMessage(Message message){
        message.setUsername(username);
        try {
            socketOut.println(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //System.out.println("Sent: " + objectMapper.writeValueAsString(message));
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

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public void run() {
        String msg;
        while (!Thread.currentThread().isInterrupted()) {
            msg = socketIn.nextLine();
            //System.out.println("Received: " + msg);
            try {
                notifyObservers(objectMapper.readValue(msg , Message.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
