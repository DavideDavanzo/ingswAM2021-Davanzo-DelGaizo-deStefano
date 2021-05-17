package it.polimi.ingsw.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CliView extends View {

    private final SocketHandler socketHandler;
    private final Scanner stdIn;

    public CliView(SocketHandler socketHandler){
        this.socketHandler = socketHandler;
        stdIn = new Scanner(System.in);
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
        Thread thread = new Thread(socketHandler::waitServerMessage);
        thread.start();
    }

    @Override
    public void askNumberOfPlayers() {
        System.out.println("Server: As no other available match already exists, you get to create a new one... ");
        System.out.println("Server: How many players would you like this new match to be composed of? Type a number between 1 and 4");
        try {
            socketHandler.sendMessage(new PlayersNumber(Integer.parseInt(stdIn.nextLine())));
        } catch (NumberFormatException e) {
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

        System.out.println("Server: Type (1),(2),(3) or (4) to choose the first one: ");

        int firstChoice;
        try {
            firstChoice = Integer.parseInt(stdIn.nextLine());
        } catch(NumberFormatException e) {
            firstChoice = 0;
        }
        while(firstChoice < 1 || firstChoice > 4){
            System.out.println("Error - try again");
            System.out.println("Type (1),(2),(3) or (4) to choose the first one: ");
            try {
                firstChoice = Integer.parseInt(stdIn.nextLine());
            } catch(NumberFormatException e) {
                firstChoice = 0;
            }
        }

        System.out.println("Server: Choose the second one (different from the first): ");

        int secondChoice;
        try {
            secondChoice = Integer.parseInt(stdIn.nextLine());
        } catch(NumberFormatException e) {
            secondChoice = 0;
        }
        while(secondChoice < 1 || secondChoice > 4 || secondChoice == firstChoice){
            System.out.println("Error -  try again");
            System.out.println("Choose the second one (different from the first): ");
            try {
                secondChoice = Integer.parseInt(stdIn.nextLine());
            } catch(NumberFormatException e) {
                secondChoice = 0;
            }
        }

        ArrayList<LeaderCard> reply = new ArrayList<>();
        reply.add(leaderCards.get(firstChoice-1));
        reply.add(leaderCards.get(secondChoice-1));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            sendMessage(new LeaderRequest(objectMapper.writeValueAsString(reply)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void askBlankResources(String msg) {

        int cont = Integer.parseInt(msg);
        Scanner scanner = new Scanner(System.in);
        ArrayList<Resource> resources = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("Server: Choose " + msg + " resources to start");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for(int i=0; i<cont; i++){

            System.out.println("Choose a resource: Coin, Shield, Stone or Servant");

            String temp = scanner.nextLine();

            stringBuilder.append("{\"@type\":" +"\"" + temp.toLowerCase()+ "\"" + ",\"volume\":1}");

            if(i!=cont-1)
                stringBuilder.append(",");

        }

        stringBuilder.append("]");

        sendMessage(new ResourceChoice(stringBuilder.toString()));

    }

    public void login() {
        System.out.println("Please enter your username: ");
        Scanner stdIn = new Scanner(System.in);
        socketHandler.setUsername(stdIn.nextLine());
        //TODO: validate username
        sendMessage(new LoginRequest());
    }

    @Override
    public void askQuery(String msg) {
        System.out.println("Server: " + msg);
        sendMessage(new ReplyMessage(stdIn.nextLine()));
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

    @Override
    public void checkConnection() {
        System.out.println("Received: Ping");
        sendMessage(new PingMessage());
    }

    public void sendMessage(Message message){
        socketHandler.sendMessage(message);
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
