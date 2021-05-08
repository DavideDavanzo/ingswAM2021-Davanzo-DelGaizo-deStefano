package it.polimi.ingsw.network.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.network.observing_pattern.Observable;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketHandler extends Observable {

    private Scanner stdIn;
    private Scanner socketIn;
    private PrintWriter socketOut;
    private ObjectMapper objectMapper;
    private Socket socket;

    public SocketHandler(Socket socket){

        this.socket = socket;
        stdIn = new Scanner(System.in);

        try {
            socketIn = new Scanner(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        objectMapper = new ObjectMapper();

    }

    public void sendMessage(Message message){

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
            //sendCommand();
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

}
