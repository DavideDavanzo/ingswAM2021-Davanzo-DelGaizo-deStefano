package it.polimi.ingsw.network.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.network.messages.ErrorMessage;
import it.polimi.ingsw.network.messages.Message;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientHandler {

    private Scanner socketIn;
    private PrintWriter socketOut;
    private ObjectMapper objectMapper;
    private Socket clientSocket;

    public ServerClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void sendMessage(Message message){
        try {
            socketOut.println(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Message waitClientMessage(){
        Message message = new ErrorMessage("Null message error");
        try {
            message = objectMapper.readValue(socketIn.nextLine(), Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
    }

}
