package it.polimi.ingsw.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.parser.Parser;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CliView extends View {

    private final SocketHandler socketHandler;
    private PlayerBoard playerBoard;
    private Parser parser;

    public CliView(SocketHandler socketHandler){
        this.socketHandler = socketHandler;
        this.playerBoard = new PlayerBoard();
        this.parser = new Parser();
    }

    @Override
    public void update(Message message) {
        message.apply(this);
    }

    @Override
    public void start() {
        socketHandler.addObserver(this);
        welcome();
        login();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(socketHandler::waitServerMessage);
        executor.submit(socketHandler::waitClientCommand);
    }

    @Override
    public void askNumberOfPlayers() {
        System.out.println("Server: As no other available match already exists, you get to create a new one... ");
        System.out.println("Server: How many players would you like this new match to be composed of? Type a number between 1 and 4");
        try{
            socketHandler.sendMessage(new PlayersNumber(Integer.parseInt(socketHandler.getStdIn().nextLine())));
        } catch(NumberFormatException e) {
            System.out.println("ERROR: wrong format");
            askNumberOfPlayers();
        }
    }

    @Override
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {
        System.out.println("Choose two of these cards:");

        for(LeaderCard card : leaderCards){
            System.out.println(card.print());
        }

        //TODO: show leader on screen

        System.out.println("Server: Type (1),(2),(3) or (4) to choose the first one: ");

        int firstChoice = Integer.parseInt(socketHandler.getStdIn().nextLine());
        while(firstChoice < 1 || firstChoice > 4){
            System.out.println("Error - try again");
            System.out.println("Type (1),(2),(3) or (4) to choose the first one: ");
            firstChoice = Integer.parseInt(socketHandler.getStdIn().nextLine());
        }

        System.out.println("Server: Choose the second one (different from the first): ");

        int secondChoice = Integer.parseInt(socketHandler.getStdIn().nextLine());
        while(firstChoice < 1 || firstChoice > 4 || secondChoice == firstChoice){
            System.out.println("Error -  try again");
            System.out.println("Choose the second one (different from the first): ");
            firstChoice = Integer.parseInt(socketHandler.getStdIn().nextLine());
        }

        ArrayList<LeaderCard> reply = new ArrayList<>();
        reply.add(leaderCards.get(firstChoice-1));
        reply.add(leaderCards.get(secondChoice-1));

        try {
            socketHandler.sendMessage(new LeaderRequest(socketHandler.getObjectMapper().writeValueAsString(reply)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void askBlankResources(String msg) {
        System.out.println("Server: Choose " + msg + " resources to start");
        int cont = Integer.parseInt(msg);
        Scanner scanner = new Scanner(System.in);
        ArrayList<Resource> resources = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i=0; i<cont; i++){
            System.out.println("Choose a resource: Coin(1), Shield(2), Stone(3) or Servant(4)");
            String temp = scanner.nextLine();

            try {
                resources.add(objectMapper.readValue("{ \"@Type\":" + temp.toLowerCase() + "\", \"volume\": 1}", Resource.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        try {
            socketHandler.sendMessage(new ResourceChoice(objectMapper.writeValueAsString(resources)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        System.out.println("Please enter your username: ");
        Scanner stdIn = new Scanner(System.in);
        socketHandler.setUsername(stdIn.nextLine());
        //TODO: validate username
        socketHandler.sendMessage(new LoginRequest());
        socketHandler.waitServerMessage();      //wait LoginReply
    }

    @Override
    public void askQuery(String msg) {
        System.out.println("Server: " + msg);
        socketHandler.sendMessage(new ReplyMessage(socketHandler.getStdIn().nextLine()));
    }

    @Override
    public void showLogin(String msg, boolean successful) {
        showMessage(msg);
    }

    @Override
    public void showError(String msg) {
        showMessage(msg);
    }

    @Override
    public void showMessage(String msg){
        System.out.println("Server: " + msg);
    }

    @Override
    public void onLoginReply(LoginReply message) {
        showMessage(message.getMsg());
        if(!message.isSuccessful())
            login();
    }

    private void welcome(){

        String rectangle[][] = new String[5][25];
        CliBuilder.shape(rectangle, 25, 5);

        rectangle[1][7] = "W";
        rectangle[1][8] = "e";
        rectangle[1][9] = "l";
        rectangle[1][10] = "c";
        rectangle[1][11] = "o";
        rectangle[1][12] = "m";
        rectangle[1][13] = "e";

        rectangle[1][15] = "t";
        rectangle[1][16] = "o";

        rectangle[2][7] = "M";
        rectangle[2][8] = "a";
        rectangle[2][9] = "e";
        rectangle[2][10] = "s";
        rectangle[2][11] = "t";
        rectangle[2][12] = "r";
        rectangle[2][13] = "i";

        rectangle[2][15] = "d";
        rectangle[2][16] = "e";
        rectangle[2][17] = "l";

        rectangle[3][7] = "R";
        rectangle[3][8] = "i";
        rectangle[3][9] = "n";
        rectangle[3][10] = "a";
        rectangle[3][11] = "s";
        rectangle[3][12] = "c";
        rectangle[3][13] = "i";
        rectangle[3][14] = "m";
        rectangle[3][15] = "e";
        rectangle[3][16] = "n";
        rectangle[3][17] = "t";
        rectangle[3][18] = "o";

        System.out.print(Color.ANSI_BLUE.escape());
        for (int r = 0; r < 5; r++) {
            System.out.println();
            for (int c = 0; c < 25; c++) {
                System.out.print(rectangle[r][c]);
            }
        }
        System.out.println();
        System.out.println(Color.ANSI_WHITE.escape());
    }
}
