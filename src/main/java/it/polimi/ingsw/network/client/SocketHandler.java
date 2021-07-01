package it.polimi.ingsw.network.client;

import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler extends Observable implements Runnable {

    private String username;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private final Socket socket;

    public SocketHandler(Socket socket){
        this.socket = socket;
        try {
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            notifyObservers((Message) Parser.deserialize(msg, Message.class));
        }
        System.exit(0);
    }

    public void sendMessage(Message message){
        message.setUsername(username);
        socketOut.println(Parser.serialize(message));
    }

    public BufferedReader getSocketIn() {
        return socketIn;
    }

    public PrintWriter getSocketOut() {
        return socketOut;
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
