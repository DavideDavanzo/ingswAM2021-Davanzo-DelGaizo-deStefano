package it.polimi.ingsw.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.network.client.ClientModel;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.network.messages.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CliView extends View {

    private final SocketHandler socketHandler;
    private final Scanner stdIn;
    private ClientModel clientModel;
    private Object lock;

    public CliView(SocketHandler socketHandler){
        this.socketHandler = socketHandler;
        stdIn = new Scanner(System.in);
        clientModel = new ClientModel();
        lock = new Object();
    }

    @Override
    public void update(Message message) {
        Thread thread = new Thread(() -> message.apply(this));
        thread.start();
    }

    @Override
    public void start() {
        socketHandler.addObserver(this);
        welcome();
        login();
        Thread thread = new Thread(socketHandler);
        thread.start();
    }

    //DONE
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

    //DONE?
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

    //DONE?
    @Override
    public void askBlankResources(String msg) {

        int cont = Integer.parseInt(msg);

        System.out.println("Choose " + msg + " resource(s) to start");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for(int i=0; i<cont; i++){

            System.out.println("Choose a resource: coin. shield, stone or servant");

            String temp = stdIn.nextLine();
            while (!temp.toLowerCase().equals("coin") && !temp.toLowerCase().equals("shield") && !temp.toLowerCase().equals("stone") && !temp.toLowerCase().equals("servant")){
                System.out.println("Error - try again");
                System.out.println("Choose a resource: coin. shield, stone or servant");
                temp = stdIn.nextLine();
            }

            stringBuilder.append("{\"@type\":" +"\"" + temp.toLowerCase()+ "\"" + ",\"volume\":1}");

            if(i!=cont-1)
                stringBuilder.append(",");

        }

        stringBuilder.append("]");

        sendMessage(new ResourceChoice(stringBuilder.toString()));

    }

    public void login() {
        System.out.println("Please enter your username: ");
        socketHandler.setUsername(stdIn.nextLine());
        //TODO: validate username
        sendMessage(new LoginRequest());
    }

    //DONE?
    @Override
    public synchronized void askCommand() {
        System.out.println("These are the commands allowed:");
        System.out.println("b -> buy a card");
        System.out.println("m -> take resources from market");
        System.out.println("p -> activate production");
        System.out.println("t -> toss leader card");
        System.out.println("a -> activate leader card");
        System.out.println("i -> ask info");
        String cmd = stdIn.nextLine();
        switch (cmd.toLowerCase()) {
            case "b":
                buyDevCard();
                break;
            case "m":
                System.out.println("going to the market");
                getMarketResources();
                break;
            case "p":
                System.out.println("activating production");
                activateProduction();
                break;
            case "t":
                System.out.println("tossing a leader card");
                break;
            case "a":
                System.out.println("activate a leader card");
                break;
            case "i":
                chooseInfo();
                break;
            default:
                System.out.println("This command does not exist. Try again");
                askCommand();
        }
    }

    public synchronized void chooseInfo(){
        System.out.println("Which item would you like to see?");
        System.out.println("w -> my warehouse");
        System.out.println("ft -> my faith track");
        System.out.println("c -> my coffer");
        System.out.println("da -> my development cards");
        System.out.println("lc -> my leader cards");
        System.out.println("m -> market");
        System.out.println("cm -> card market");
        System.out.println("exit -> return to main commands");

        String cmd = stdIn.nextLine();
        switch (cmd.toLowerCase()) {
            case "w":
                System.out.println("Warehouse:");
                System.out.println(clientModel.getPlayerBoard().getWarehouse().print());
                break;
            case "ft":
                System.out.println("Faith track:");
                System.out.println(clientModel.getPlayerBoard().getPath().print());
                break;
            case "c":
                System.out.println("Coffer:");
                System.out.println(clientModel.getPlayerBoard().getCoffer().print());
                break;
            case "da":
                System.out.println("Development card area:");
                System.out.println(clientModel.getPlayerBoard().getDevelopmentCardsArea().print());
                break;
            case "lc":
                System.out.println("Leader cards:");
                System.out.println(clientModel.getLeaderCards().get(0).print());
                System.out.println(clientModel.getLeaderCards().get(1).print());
                break;
            case "m" :
                sendMessage(new MarketInfoRequest());
                System.out.println("Market:");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "cm" :
                sendMessage(new CardsMarketInfoRequest());
                System.out.println("Cards market:");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "exit" :
                break;
            default:
                System.out.println("Error - wrong format. Try again");
                chooseInfo();
        }
        askCommand();
    }

    public synchronized void buyDevCard(){
        sendMessage(new CardsMarketInfoRequest());
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Choose color:");
        System.out.println("g -> green");
        System.out.println("b -> blue");
        System.out.println("y -> yellow");
        System.out.println("p -> purple");
        String user = stdIn.nextLine();
        ECardColor color = null;
        while (color == null) {
            switch (user.toLowerCase()) {
                case "g":
                    color = ECardColor.GREEN;
                    break;
                case "b":
                    color = ECardColor.BLUE;
                    break;
                case "y":
                    color = ECardColor.YELLOW;
                    break;
                case "p":
                    color = ECardColor.PURPLE;
                    break;
                default:
                    System.out.println("ERROR - this color does not exist... try again");
            }
        }
        int level = 0;
        while(level < 1 || level > 4){
            System.out.println("Choose the level from 1 to 4");
            try{
                level = Integer.parseInt(stdIn.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Error - wrong format");
                level = 0;
            }
        }

        Command buyCardCmd = new BuyCardCmd(color, level);
        sendMessage(buyCardCmd);

    }

    public synchronized void getMarketResources(){
        sendMessage(new MarketInfoRequest());
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        char line = ' ';
        while (line != 'r' && line != 'c') {
            System.out.println("Choose line type: row ('r') or column ('c')?");
            line = stdIn.nextLine().charAt(0);
        }
        int index = 0;
        while (index < 1 || (line == 'r' && index > 3) || (line == 'c' && index > 4)) {
            System.out.println("Choose index of " + (line == 'r' ? "row: type a number between 1 and 3" : "column: type a number between 1 and 4"));
            index = Integer.parseInt(stdIn.nextLine());
        }
        Command marketResourcesCmd = new MarketResourcesCmd(line, index);
        sendMessage(marketResourcesCmd);
    }

    public void activateProduction(){
        //ask client's model my dev area
        String userInput;
        ArrayList<Integer> choices = new ArrayList<>();
        int cont = 4;
        do{
            System.out.println("Choose the stack of the development card you want to activate");
            System.out.println("Type its number, or type 'b' to activate base production");
            System.out.println("type \"activate\" to execute production");
            userInput = stdIn.nextLine();
            if(userInput.equals("activate"))
                break;
            else if(userInput.equals("b")) {
                if(!choices.contains(0)) {
                    choices.add(0);
                    cont--;
                }
                else{
                    System.out.println("Already chosen... try again");
                    continue;
                }
            }
            else{
                try {
                    int temp = Integer.parseInt(userInput);
                    if (!choices.contains(temp) && temp>=0 && temp<4) {
                        choices.add(temp);
                        cont--;
                    }
                    else {
                        System.out.println("something wrong... try again");
                        continue;
                    }
                } catch (NumberFormatException e){
                    System.out.println("ERROR - wrong format");
                    continue;
                }
            }
        } while(cont != 0);

        //TODO: assemble and send correct command

    }

    @Override
    public void askQuery(String msg) {
        System.out.println(msg);
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
    public synchronized void showMessage(String msg){
        System.out.println(msg);
        notifyAll();
    }

    @Override
    public void onLoginReply(LoginReply message) {
        showMessage(message.getMsg());
        if(!message.isSuccessful())
            login();
    }

    @Override
    public void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves) {
        ArrayList<Integer> choices = new ArrayList<>();
        String userInput;
        System.out.println("This is your current wharehouse...");
        System.out.println(clientModel.getPlayerBoard().getWarehouse().print());
        if(numExtraShelves == 0) {
            for (Item resource : resources) {
                System.out.println("Incomin resource: " + resource.print());
                System.out.println("Where would you want to stock it? Type 'f', 's', 't' to choose warehouse shelf or 'd' to discard");
                System.out.println("'f' -> first shelf");
                System.out.println("'s' -> second shelf");
                System.out.println("'t' -> third shelf");
                switch (userInput = stdIn.nextLine()) {
                    case "d":
                        choices.add(0);
                        break;
                    case "f":
                        choices.add(1);
                        break;
                    case "s":
                        choices.add(2);
                        break;
                    case "t":
                        choices.add(3);
                        break;
                    default:
                        System.out.println("Error - command not accepted, try again");
                }
            }
        } else if(numExtraShelves == 1) {
            for (Item resource : resources) {
                System.out.println("Incomin resource: " + resource.print());
                System.out.println("Where would you want to stock it? Type 'f', 's', 't', \"fe\" to choose warehouse shelf or 'd' to discard");
                System.out.println("'f' -> first shelf");
                System.out.println("'s' -> second shelf");
                System.out.println("'t' -> third shelf");
                System.out.println("'fe' -> extra shelf");
                switch (userInput = stdIn.nextLine()) {
                    case "d":
                        choices.add(0);
                        break;
                    case "f":
                        choices.add(1);
                        break;
                    case "s":
                        choices.add(2);
                        break;
                    case "t":
                        choices.add(3);
                        break;
                    case "fe":
                        choices.add(4);
                        break;
                    default:
                        System.out.println("Error - command not accepted, try again");
                }
            }
        }else if (numExtraShelves == 2) {
            for (Item resource : resources) {
                System.out.println("Incoming resource: " + resource.print());
                System.out.println("Where would you want to stock it? Type 'f', 's', 't', \"fe\", \"se\" to choose warehouse shelf or 'd' to discard");
                System.out.println("'f' -> first shelf");
                System.out.println("'s' -> second shelf");
                System.out.println("'t' -> third shelf");
                System.out.println("'fe' -> first extra shelf");
                System.out.println("'se' -> second extra shelf");
                switch (userInput = stdIn.nextLine()) {
                    case "d":
                        choices.add(0);
                        break;
                    case "f":
                        choices.add(1);
                        break;
                    case "s":
                        choices.add(2);
                        break;
                    case "t":
                        choices.add(3);
                        break;
                    case "fe":
                        choices.add(4);
                        break;
                    case "se":
                        choices.add(5);
                        break;
                    default:
                        System.out.println("Error - command not accepted, try again");
                }
            }
        }
        sendMessage(new ArrangeInWarehouseCmd(choices));
    }

    @Override
    public void askToChangeWhiteMarbles(ArrayList<Item> items, int count) {
        System.out.println();
        System.out.println();
        int i=1;
        for (Item item : items){
            System.out.println();
        }
        int temp;
        ArrayList<Item> choices = new ArrayList<>();
        for(i=0; i<count; i++){
            do{
                System.out.println();
                temp = Integer.parseInt(stdIn.nextLine());
            }while(temp<1 || temp>items.size());
            choices.add(items.get(temp-1).clone());
        }
        sendMessage(new ChangeWhiteMarbleReply(choices));
    }

    @Override
    public void checkConnection() {
        System.out.println("Received: Ping");
        sendMessage(new PingMessage());
        System.out.println("Sent: Pong");
    }

    public void sendMessage(Message message){
        socketHandler.sendMessage(message);
    }

    public void sendMessage(Command command){
        socketHandler.sendMessage(command);
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
