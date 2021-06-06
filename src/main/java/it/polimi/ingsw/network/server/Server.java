package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.network.messages.LoginReply;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observingPattern.Observer;
import it.polimi.ingsw.view.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Observer {

    private final int socketPort;
    private ServerSocket serverSocket;
    private ArrayList<GameController> gameControllers;
    private GameController gameController;
    ExecutorService executor = Executors.newCachedThreadPool();
    private HashMap<String, GameController> usernames;

    public Server(int port){
        socketPort = port;
        gameControllers = new ArrayList<>();
        gameController = new GameController();
        gameController.addObserver(this);
        gameControllers.add(gameController);
        usernames = new HashMap<String, GameController>();
    }

    public static void main(String[] args){
        Server server = new Server(Integer.parseInt(args[0]));
        server.start();
    }

    public void start(){

        System.out.println("Server started");

        try {
            serverSocket = new ServerSocket(socketPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        while (true) {
            System.out.println("Waiting login from " + virtualView.getClientHandler().getClientSocket().getLocalAddress());
            Message loginRequest = virtualView.getClientHandler().returnClientMessage();
            if(usernames.containsKey(loginRequest.getUsername())){
                if(usernames.get(loginRequest.getUsername()).verifyConnected(loginRequest.getUsername())) {
                    virtualView.sendMessage(new LoginReply("Username already logged", false));
                    continue;
                } else {
                    usernames.get(loginRequest.getUsername()).reconnect(loginRequest.getUsername(), virtualView);
                    virtualView.setUsername(loginRequest.getUsername());
                    virtualView.getClientHandler().setUsername(loginRequest.getUsername());
                    return;
                }
            }
            virtualView.setUsername(loginRequest.getUsername());
            virtualView.getClientHandler().setUsername(loginRequest.getUsername());
            if(gameController.isFull()) {
                gameController = new GameController();
                gameController.addObserver(this);
                gameControllers.add(gameController);
            }
            if(gameController.logPlayer(loginRequest.getUsername(), virtualView)) {
                usernames.put(loginRequest.getUsername(), gameController);
                System.out.println(usernames);
                return;
            }
        }

    }

    @Override
    public void update(Set<String> usernames) {
        for(String username : usernames)
            this.usernames.remove(username);
    }

    @Override
    public void update(Message message) {
        //do nothing
    }

    @Override
    public void update(Warehouse warehouse) {
        //do nothing
    }

    @Override
    public void update(Path path) {
        //do nothing
    }

    @Override
    public void update(Coffer coffer) {
        //do nothing
    }

    @Override
    public void update(DevelopmentCardsArea developmentCardsArea) {
        //do nothing
    }

    @Override
    public void update(int pathPosition) {
        //do nothing
    }

}
