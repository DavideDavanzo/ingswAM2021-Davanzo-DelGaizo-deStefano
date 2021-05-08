package it.polimi.ingsw.network.client;


import it.polimi.ingsw.view.CliView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public String username;
    private Socket socket;
    private View clientView;

    public Client(String username){
        this.username = username;
    }

    public static void main(String[] args) {

        System.out.println("Welcome to Maestri del Rinascimento!");
        System.out.println("Please enter your username: ");
        Scanner stdIn = new Scanner(System.in);
        Client client = new Client(stdIn.nextLine());
        client.start(args[0], Integer.parseInt(args[1]));

    }

    public void start(String hostName, int port){

        System.out.println("Connecting to server...");
        try {
            socket = new Socket(hostName, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established");
        SocketHandler socketHandler = new SocketHandler(socket);
        clientView = new CliView(socketHandler);
        clientView.start();

    }

}
