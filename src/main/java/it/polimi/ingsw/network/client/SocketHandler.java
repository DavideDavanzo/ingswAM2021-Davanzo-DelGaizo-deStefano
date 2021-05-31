package it.polimi.ingsw.network.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.network.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler extends Observable implements Runnable {

    private String username;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private final ObjectMapper objectMapper;
    private final Socket socket;

    public SocketHandler(Socket socket){
        this.socket = socket;
        try {
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        String msg = null;
        while (!socket.isClosed()) {
            try {
                msg = socketIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("Received: " + msg);
            try {
                notifyObservers(objectMapper.readValue(msg , Message.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Message message){
        message.setUsername(username);
        try {
            socketOut.println(objectMapper.writeValueAsString(message));
            //System.out.println("Sent: " + objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getSocketIn() {
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

    public void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

}
