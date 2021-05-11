package it.polimi.ingsw.network.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.network.observingPattern.Observable;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketHandler extends Observable {

    private String username;
    private Scanner stdIn;
    private Scanner socketIn;
    private PrintWriter socketOut;
    private final ObjectMapper objectMapper;
    private final Socket socket;

    public SocketHandler(Socket socket){

        this.socket = socket;

        stdIn = new Scanner(System.in);

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

    }

    public void waitServerMessage(){

        while (!Thread.currentThread().isInterrupted()) {
            try {
                notifyObservers(objectMapper.readValue(socketIn.nextLine(), Message.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    public void waitClientCommand(){
        while (!Thread.currentThread().isInterrupted()) {
            String userInput = stdIn.nextLine();
            //TODO: sendCommand() method
        }
    }

    public Scanner getStdIn() {
        return stdIn;
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

}
