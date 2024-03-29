package it.polimi.ingsw.network.client;


import it.polimi.ingsw.view.ClientView;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.AppFX;

import java.io.IOException;
import java.net.Socket;

import static javafx.application.Application.launch;

/**
 * Client Class. Contains the player's {@link Socket} and Username.
 */
public class Client {

    public String username;
    private Socket socket;

    private static final String CLI = "-cli";

    public Client(String username){
        this.username = username;
    }

    public Client(){}

    /**
     * Main method that starts the CLI or the GUI based on args.
     * @param args
     */
    public static void main(String[] args) {

        Client client = new Client();

        if(args[0].equals(CLI))
            client.startCli(args);
        else launch(AppFX.class, args);

    }

    /**
     * Starts the CLI.
     * @param args
     */
    public void startCli(String[] args){

        String hostName = args[1];
        int port = Integer.parseInt(args[2]);
        System.out.println("Connecting to server...");
        try {
            socket = new Socket(hostName, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connection established");

        SocketHandler socketHandler = new SocketHandler(socket);
        ClientView clientView = new CliView(socketHandler);
        clientView.start();

    }

}
