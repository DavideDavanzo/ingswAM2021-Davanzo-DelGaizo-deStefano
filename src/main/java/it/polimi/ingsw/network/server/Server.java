package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int socketPort;
    private ServerSocket serverSocket;
    private GameController gameController;

    public Server(int port){
        socketPort = port;
    }

    public static void main(String[] args){
        Server server = new Server(Integer.parseInt(args[0]));
        server.start();
    }

    public void start(){

        ExecutorService executor = Executors.newCachedThreadPool();

        System.out.println("Server started");

        try {
            serverSocket = new ServerSocket(socketPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameController = new GameController();

        try{
            while(true) {

                System.out.println("Waiting for a new connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection to " + clientSocket.getLocalAddress() + " established");

                ServerClientHandler clientHandler = new ServerClientHandler(clientSocket);

                VirtualView virtualView = new VirtualView(clientHandler);

                executor.submit(() -> waitLogin(virtualView));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void waitLogin(VirtualView virtualView){

        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Waiting login from " + virtualView.getClientHandler().getClientSocket().getLocalAddress());
            Message loginRequest = virtualView.getClientHandler().returnClientMessage();
            if(gameController.isFull())
                gameController = new GameController();
            try {
                gameController.logPlayer(loginRequest.getUsername(), virtualView);
            } catch (Exception e) {
                break;
            }
        }

    }

}
