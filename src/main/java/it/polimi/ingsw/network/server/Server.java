package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int socketPort;
    private ServerSocket serverSocket;
    private Socket clientSocket;

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

        try{
            while(true){
                System.out.println("Waiting for a new connection...");
                clientSocket = serverSocket.accept();
                System.out.println("Connection to " + clientSocket.getLocalAddress() + " established");
                ServerClientHandler clientHandler = new ServerClientHandler(clientSocket);
                //gameController.addClientHandler(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
