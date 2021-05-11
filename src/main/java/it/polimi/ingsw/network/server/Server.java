package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int socketPort;
    private ServerSocket serverSocket;

    public Server(int port){
        socketPort = port;
    }

    public static void main(String[] args){
        Server server = new Server(Integer.parseInt(args[0]));
        server.start();
    }

    public void start(){

        try {
            serverSocket = new ServerSocket(socketPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameController gameController = new GameController();

        try{
            while(true) {

                System.out.println("Server started");
                System.out.println("Waiting for a new connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection to " + clientSocket.getLocalAddress() + " established");

                ServerClientHandler clientHandler = new ServerClientHandler(clientSocket);

                VirtualView serverVirtualView = new VirtualView(clientHandler);

                System.out.println("waiting login...");
                Message loginRequest = clientHandler.returnClientMessage();
                System.out.println("Received username: " + loginRequest.getUsername());

                serverVirtualView.getClientHandler().sendMessage(new LoginReply("OK"));

                gameController.addVirtualView(loginRequest.getUsername(), serverVirtualView);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
