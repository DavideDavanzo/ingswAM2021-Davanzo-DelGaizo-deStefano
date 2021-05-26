package it.polimi.ingsw.network.client;


import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.AppFX;

import java.io.IOException;
import java.net.Socket;

import static javafx.application.Application.launch;

public class Client {

    public String username;
    private Socket socket;
    private View clientView;

    private static final String CLI = "-cli";

    public Client(String username){
        this.username = username;
    }

    public Client(){}

    public static void main(String[] args) {

        Client client = new Client();

        if(args[0].equals(CLI)) client.start(args[1], Integer.parseInt(args[2]));
        else launch(AppFX.class, args);

    }

    public void start(String hostName, int port){

        System.out.println("Connecting to server...");
        try {
            socket = new Socket(hostName, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connection established");

        SocketHandler socketHandler = new SocketHandler(socket);
        clientView = new CliView(socketHandler);
        clientView.start();

    }

}
