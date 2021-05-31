package it.polimi.ingsw.view.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.Trade;
import it.polimi.ingsw.model.effects.ExtraDevEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.network.client.ClientModel;
import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CliView extends View {

    private final SocketHandler socketHandler;
    private BufferedReader stdIn;
    private ClientModel clientModel;
    ExecutorService executor;

    public CliView(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        clientModel = new ClientModel();
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public synchronized void update(Message message) {
        executor.submit(() -> message.apply(this));
    }

    @Override
    public void start() {
        welcome();
        socketHandler.addObserver(this);
        executor.submit(socketHandler);
        login();
    }

    @Override
    public void login() {
        System.out.println("Please enter your username: ");
        try {
            while(stdIn.ready())
                stdIn.readLine();
            socketHandler.setUsername(stdIn.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: validate username
        sendMessage(new LoginRequest());
    }

    @Override
    public void onLoginReply(LoginReply message) {
        showMessage(message.getMsg());
        if(!message.isSuccessful())
            login();
    }

    @Override
    public void askNumberOfPlayers() {
        System.out.println("Server: As no other available match already exists, you get to create a new one... ");
        System.out.println("Server: How many players would you like this new match to be composed of? Type a number between 1 and 4");
        try {
            while(stdIn.ready())
                stdIn.readLine();
            socketHandler.sendMessage(new PlayersNumber(Integer.parseInt(stdIn.readLine())));
        } catch (NumberFormatException e) {
            System.out.println("ERROR: wrong format");
            askNumberOfPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askLeaders(ArrayList<LeaderCard> leaderCards) {

        System.out.println("Choose two of these cards:");

        for(LeaderCard card : leaderCards){
            System.out.println(card.print());
        }

        System.out.println("Server: Type (1),(2),(3) or (4) to choose the first one: ");

        int firstChoice = 0;
        try {
            while(stdIn.ready())
                stdIn.readLine();
            firstChoice = Integer.parseInt(stdIn.readLine());
        } catch(NumberFormatException e) {
            firstChoice = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(firstChoice < 1 || firstChoice > 4){
            System.out.println("Error - try again");
            System.out.println("Type (1),(2),(3) or (4) to choose the first one: ");
            try {
                firstChoice = Integer.parseInt(stdIn.readLine());
            } catch(NumberFormatException e) {
                firstChoice = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Server: Choose the second one (different from the first): ");

        int secondChoice = 0;
        try {
            secondChoice = Integer.parseInt(stdIn.readLine());
        } catch(NumberFormatException e) {
            secondChoice = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(secondChoice < 1 || secondChoice > 4 || secondChoice == firstChoice){
            System.out.println("Error -  try again");
            System.out.println("Choose the second one (different from the first): ");
            try {
                secondChoice = Integer.parseInt(stdIn.readLine());
            } catch(NumberFormatException e) {
                secondChoice = 0;
            } catch (IOException e) {
                e.printStackTrace();
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

        clientModel.setLeaderCards(reply);

    }

    @Override
    public synchronized void askCommand() {
        System.out.println("These are the commands allowed:");
        System.out.println("b -> buy a card");
        System.out.println("m -> take resources from market");
        System.out.println("p -> activate production");
        System.out.println("s -> switch shelves");
        System.out.println("t -> toss leader card");
        System.out.println("a -> activate leader card");
        System.out.println("i -> ask info");
        System.out.println("next -> pass turn");
        System.out.println("quit -> leave match");
        String cmd;
        try {
            while(stdIn.ready())
                stdIn.readLine();
            switch (cmd = stdIn.readLine().toLowerCase()) {
                case "b":
                    buyDevCard();
                    break;
                case "m":
                    getMarketResources();
                    break;
                case "p":
                    activateProduction();
                    break;
                case "s":
                    switchShelves();
                    break;
                case "t":
                    tossLeaderCards();
                    break;
                case "a":
                    activateLeaderCards();
                    break;
                case "i" :
                    chooseInfo();
                    askCommand();
                    break;
                case "next":
                    passTurn();
                    break;
                case "quit":
                    socketHandler.disconnect();
                    break;
                default:
                    System.out.println("This command does not exist. Try again");
                    askCommand();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void chooseInfo(){

        System.out.println("Which item would you like to see?");
        System.out.println("pb -> my entire player board");
        System.out.println("w -> my warehouse");
        System.out.println("ft -> my faith track");
        System.out.println("c -> my coffer");
        System.out.println("da -> my development cards");
        System.out.println("lc -> my leader cards");
        System.out.println("m -> market");
        System.out.println("cm -> card market");
        System.out.println("exit -> return to main commands");

        String cmd;
        try {
            switch (cmd = stdIn.readLine().toLowerCase()) {
                case "pb":
                    System.out.println("Leader cards:");
                    if (clientModel.getLeaderCards().size() == 0)
                        System.out.println("you do not have any leader card");
                    else {
                        for (LeaderCard leaderCard : clientModel.getLeaderCards()) {
                            System.out.println(leaderCard.print());
                        }
                    }
                    System.out.println("Faith track:");
                    System.out.println(clientModel.getFaithTrack());
                    System.out.println("Development card area:");
                    System.out.println(clientModel.getDevelopmentCardsArea());
                    System.out.println("Warehouse:");
                    System.out.println(clientModel.getWarehouse());
                    System.out.println("Coffer:");
                    System.out.println(clientModel.getCoffer());
                    break;
                case "w":
                    System.out.println("Warehouse:");
                    System.out.println(clientModel.getWarehouse());
                    break;
                case "ft":
                    System.out.println("Faith track:");
                    System.out.println(clientModel.getFaithTrack());
                    break;
                case "c":
                    System.out.println("Coffer:");
                    System.out.println(clientModel.getCoffer());
                    break;
                case "da":
                    System.out.println("Development card area:");
                    System.out.println(clientModel.getDevelopmentCardsArea());
                    break;
                case "lc":
                    System.out.println("Leader cards:");
                    if (clientModel.getLeaderCards().size() == 0)
                        System.out.println("you do not have any leader cards");
                    else {
                        for (LeaderCard leaderCard : clientModel.getLeaderCards()) {
                            System.out.println(leaderCard.print());
                        }
                    }
                    break;
                case "m":
                    sendMessage(new MarketInfoRequest());
                    System.out.println("Market:");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "cm":
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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void askBlankResources(String msg) {

        int cont = Integer.parseInt(msg);

        System.out.println("You must choose " + msg + " resource(s) to start");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for(int i=0; i<cont; i++){

            System.out.println("Choose a resource: coin, shield, stone or servant");

            String temp = null;
            try {
                while(stdIn.ready())
                    stdIn.readLine();
                temp = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!temp.toLowerCase().equals("coin") && !temp.toLowerCase().equals("shield") && !temp.toLowerCase().equals("stone") && !temp.toLowerCase().equals("servant")){
                System.out.println("Error - try again");
                System.out.println("Choose a resource: coin. shield, stone or servant");
                try {
                    temp = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            stringBuilder.append("{\"@type\":" +"\"" + temp.toLowerCase() + "\"" + ",\"volume\":1}");

            if(i!=cont-1)
                stringBuilder.append(",");

        }

        stringBuilder.append("]");

        sendMessage(new ResourceChoice(stringBuilder.toString()));

    }

    @Override
    public void activateLeaderCards() {
        int choice = 0;
        if(clientModel.getLeaderCards().size() == 0){
            System.out.println("You have no leader card");
        } else {
            System.out.println("These are your inactive leader cards:");
            for (LeaderCard leaderCard : clientModel.getLeaderCards()) {
                if(!leaderCard.isActive())
                    System.out.println(leaderCard.print());
            }
            if(clientModel.getLeaderCards().size() == 1){
                System.out.println("Do you want to activate it?");
                System.out.println("y -> yes");
                System.out.println("n -> no");
                String userInput = null;
                try {
                    userInput = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(userInput.startsWith("y"))
                    choice = 1;
                else
                    return;
            } else {
                while(choice!=1 && choice!=2){
                    System.out.println("Which one you want to activate? Type 1 or 2");
                    System.out.println("Type 0 to return to main commands");
                    try {
                        choice = Integer.parseInt(stdIn.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(choice == 0)
                        return;
                }
            }
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(choice);
            sendMessage(new ActivateLeaderCmd(temp));
        }
    }

    @Override
    public void tossLeaderCards() {
        int choice = 0;
        if(clientModel.getLeaderCards().size() == 0){
            System.out.println("You have no leader card");
        } else {
            System.out.println("These are your leader cards:");
            for (LeaderCard leaderCard : clientModel.getLeaderCards()) {
                if(!leaderCard.isActive())
                    System.out.println(leaderCard.print());
            }
            if(clientModel.getLeaderCards().stream().filter(l -> !l.isActive()).count() == 1){
                System.out.println("Do you want to discard it?");
                System.out.println("y -> yes");
                System.out.println("n -> no");
                String userInput = null;
                try {
                    userInput = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(userInput.startsWith("y"))
                    choice = 1;
                else
                    return;
            } else {
                while(choice!=1 && choice!=2){
                    System.out.println("Which one you want to discard? Type 1 or 2");
                    try {
                        choice = Integer.parseInt(stdIn.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(choice);
            sendMessage(new DiscardLeaderCmd(temp));
            clientModel.getLeaderCards().remove(choice-1);
        }
    }

    private synchronized void buyDevCard(){
        System.out.println("These are your available resources");
        System.out.println(clientModel.getWarehouse());
        System.out.println(clientModel.getCoffer());
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
        System.out.println("Or type \"exit\" to return to main commands");
        String userInput = null;
        ECardColor color = null;
        while (color == null) {
            try {
                userInput = stdIn.readLine().toLowerCase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (userInput) {
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
                case "exit" :
                    askCommand();
                    return;
                default:
                    System.out.println("ERROR - this color does not exist... try again");
            }
        }
        int level = 0;
        while(level < 1 || level > 4){
            System.out.println("Choose the level from 1 to 3");
            try{
                level = Integer.parseInt(stdIn.readLine());
            } catch (NumberFormatException e){
                System.out.println("Error - wrong format");
                level = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int slot = 0;
        System.out.println("These are your development cards");
        System.out.println(clientModel.getDevelopmentCardsArea());
        while(slot < 1 || slot > 3){
            System.out.println("On top of which stack would you like to put it?");
            System.out.println("Type a number from 1 to 3");
            try{
                slot = Integer.parseInt(stdIn.readLine());
            } catch (NumberFormatException e){
                System.out.println("Error - wrong format");
                level = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Message buyCardCmd = new BuyCardCmd(color, level, slot);
        sendMessage(buyCardCmd);

    }

    private synchronized void getMarketResources() {
        System.out.println("This is your current warehouse:");
        System.out.println(clientModel.getWarehouse());
        sendMessage(new MarketInfoRequest());
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        char line = ' ';
        while (line != 'r' && line != 'c') {
            System.out.println("Choose:");
            System.out.println("r -> row");
            System.out.println("c -> column");
            System.out.println("e -> exit, return to main commands");
            try {
                line = stdIn.readLine().charAt(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(line == 'e'){
                askCommand();
                return;
            }
        }
        int index = 0;
        while (index < 1 || (line == 'r' && index > 3) || (line == 'c' && index > 4)) {
            System.out.println("Choose index of " + (line == 'r' ? "row: type a number between 1 and 3" : "column: type a number between 1 and 4"));
            try{
                index = Integer.parseInt(stdIn.readLine());
            } catch(NumberFormatException e){
                System.out.println("Error - wrong format");
                index = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sendMessage(new MarketResourcesCmd(line, index-1));
    }

    private void activateProduction(){
        System.out.println("These are your available resources");
        System.out.println(clientModel.getWarehouse());
        System.out.println(clientModel.getCoffer());
        System.out.println("These are your development cards:");
        System.out.println();
        System.out.println(clientModel.getDevelopmentCardsArea());
        for(LeaderCard leaderCard : clientModel.getLeaderCards()){
            if(leaderCard.isActive() && leaderCard.getEffect() instanceof ExtraDevEffect)
                System.out.println(leaderCard.print());
        }
        String userInput = null;
        ArrayList<Integer> choices = new ArrayList<>();
        Trade baseTrade = null;
        int cont = 4;
        do{
            System.out.println("Choose the stack of the development card you want to activate");
            long num = clientModel.getLeaderCards().stream().filter(LeaderCard::isActive).count();
            if(num != 0) {
                System.out.println("or a leader card with extra trade effect");
                cont += num;
            }
            System.out.println("b -> base production");
            System.out.println("1 -> first stack");
            System.out.println("2 -> second stack");
            System.out.println("3 -> third stack");
            for(int i=0; i<num; i++)
                System.out.println((4+i) + " -> " + (i==0?"first":"second") + " extra shelf");
            System.out.println();
            System.out.println("Type \"activate\" to execute production");
            System.out.println("exit -> return to main commands");
            try {
                userInput = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(userInput.equals("activate"))
                break;
            else if(userInput.equals("b")) {
                baseTrade = composeBaseTrade();
            } else if(userInput.equals("exit")){
                askCommand();
                return;
            } else{
                try {
                    int temp = Integer.parseInt(userInput);
                    if (!choices.contains(temp) && temp>0 && temp<4+num) {
                        choices.add(temp);
                        cont--;
                    } else if(temp == 0){
                        askCommand();
                        return;
                    } else {
                        System.out.println("something wrong... try again");
                        continue;
                    }
                } catch (NumberFormatException e){
                    System.out.println("ERROR - wrong format");
                    continue;
                }
            }
        } while(cont != 0);

        sendMessage(new ActivateProductionCmd(baseTrade, baseTrade!=null, choices));

    }

    private Trade composeBaseTrade(){
        ArrayList<Resource> input = new ArrayList<>();
        ArrayList<Item> output = new ArrayList<>();
        System.out.println("Compose base production...");
        System.out.println("Choose the first input resource you want to trade");
        System.out.println("c -> coin");
        System.out.println("sh -> shield");
        System.out.println("st -> stone");
        System.out.println("se -> servant");
        String userInput;
        try {
            userInput = stdIn.readLine();
            while(!userInput.equals("c") && !userInput.equals("sh") && !userInput.equals("st") && !userInput.equals("se")){
                System.out.println("Error - wrong format. Try again");
                userInput = stdIn.readLine();
            }
            switch (userInput){
                case "c" :
                    input.add(new Coin(1));
                    break;
                case "sh" :
                    input.add(new Shield(1));
                    break;
                case "st" :
                    input.add(new Stone(1));
                    break;
                case "se" :
                    input.add(new Servant(1));
                    break;
                default :
                    System.out.println("Something wrong happened");
                    System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Choose the second input resource you want to trade");
        System.out.println("c -> coin");
        System.out.println("sh -> shield");
        System.out.println("st -> stone");
        System.out.println("se -> servant");
        try {
            userInput = stdIn.readLine();
            while(!userInput.equals("c") && !userInput.equals("sh") && !userInput.equals("st") && !userInput.equals("se")){
                System.out.println("Error - wrong format. Try again");
                userInput = stdIn.readLine();
            }
            switch (userInput){
                case "c" :
                    input.add(new Coin(1));
                    break;
                case "sh" :
                    input.add(new Shield(1));
                    break;
                case "st" :
                    input.add(new Stone(1));
                    break;
                case "se" :
                    input.add(new Servant(1));
                    break;
                default :
                    System.out.println("Something wrong happened");
                    System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Choose the output resource you want to produce");
        System.out.println("c -> coin");
        System.out.println("sh -> shield");
        System.out.println("st -> stone");
        System.out.println("se -> servant");
        try {
            userInput = stdIn.readLine();
            while(!userInput.equals("c") && !userInput.equals("sh") && !userInput.equals("st") && !userInput.equals("se")){
                System.out.println("Error - wrong format. Try again");
                userInput = stdIn.readLine();
            }
            switch (userInput){
                case "c" :
                    output.add(new Coin(1));
                    break;
                case "sh" :
                    output.add(new Shield(1));
                    break;
                case "st" :
                    output.add(new Stone(1));
                    break;
                case "se" :
                    output.add(new Servant(1));
                    break;
                default :
                    System.out.println("Something wrong happened");
                    System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Trade(input, output);
    }

    @Override
    public void askToStockMarketResources(ArrayList<Item> resources, int numExtraShelves) {
        ArrayList<Integer> choices = new ArrayList<>();
        String userInput = null;
        System.out.println("This is your current warehouse...");
        System.out.println(clientModel.getWarehouse());
        if(numExtraShelves == 0) {
            for (Item resource : resources) {
                System.out.println("Incoming resource: " + resource.print());
                System.out.println("Where would you want to stock it? Type 'f', 's', 't' to choose warehouse shelf or 'd' to discard");
                System.out.println("'f' -> first shelf");
                System.out.println("'s' -> second shelf");
                System.out.println("'t' -> third shelf");
                do {
                    try {
                        while (stdIn.ready())
                            stdIn.readLine();
                        switch (userInput = stdIn.readLine()) {
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
                                userInput = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while(userInput == null);
            }
        } else if(numExtraShelves == 1) {
            for (Item resource : resources) {
                    System.out.println("Incoming resource: " + resource.print());
                    System.out.println("Where would you want to stock it? Type 'f', 's', 't', \"fe\" to choose warehouse shelf or 'd' to discard");
                    System.out.println("'f' -> first shelf");
                    System.out.println("'s' -> second shelf");
                    System.out.println("'t' -> third shelf");
                    System.out.println("'fe' -> extra shelf");
                    do {
                        try{
                            while (stdIn.ready())
                                stdIn.readLine();
                            switch (userInput = stdIn.readLine()) {
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
                                    userInput = null;
                            }
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    } while(userInput == null);
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
                    do {
                        try {
                            while (stdIn.ready())
                                stdIn.readLine();
                            switch (userInput = stdIn.readLine()) {
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
                                    userInput = null;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } while(userInput == null);
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
        int temp = 0;
        ArrayList<Item> choices = new ArrayList<>();
        for(i=0; i<count; i++){
            do{
                System.out.println();
                try {
                    while(stdIn.ready())
                        stdIn.readLine();
                    temp = Integer.parseInt(stdIn.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while(temp<1 || temp>items.size());
            choices.add(items.get(temp-1).clone());
        }
        sendMessage(new ChangeWhiteMarbleReply(choices));
    }

    private void switchShelves() {
        String userInput;
        System.out.println("This is your current warehouse...");
        System.out.println(clientModel.getWarehouse());
        System.out.println("Which shelves do you want to swap? Choose as follow: (1) (2) (3) for main shelves, (4) (5) for extra shelves");

        int firstShelf = 0;

        try {
            firstShelf = Integer.parseInt(stdIn.readLine());
        } catch(NumberFormatException e) {
            firstShelf = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(firstShelf < 1 || firstShelf > 5) {
            System.out.println("Invalid shelf index");
            System.out.println("Choose as follow: (1) (2) (3) for main shelves, (4) (5) for extra shelves");
            try {
                firstShelf = Integer.parseInt(stdIn.readLine());
            } catch(NumberFormatException e) {
                firstShelf = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Choose the second one (different from the first): ");

        int secondShelf = 0;
        try {
            secondShelf = Integer.parseInt(stdIn.readLine());
        } catch(NumberFormatException e) {
            secondShelf = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(secondShelf < 1 || secondShelf > 5 || secondShelf == firstShelf) {
            System.out.println("Invalid shelf index");
            System.out.println("Choose the second one (different from the first): ");
            try {
                secondShelf = Integer.parseInt(stdIn.readLine());
            } catch(NumberFormatException e) {
                secondShelf = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sendMessage(new SwitchShelvesCmd(firstShelf, secondShelf));

    }

    private void passTurn(){
        sendMessage(new PassTurnMessage());
    }

    @Override
    public void waitTurn(){
        System.out.println("Wait for your next turn...");
    }

    @Override
    public synchronized void showMarket(Market market) {
        System.out.println(market.print());
        notify();
    }

    @Override
    public synchronized void showCardsMarket(CardMarket cardMarket) {
        System.out.println(cardMarket.print());
        notify();
    }

    private void sendMessage(Message message){
        socketHandler.sendMessage(message);
    }

    @Override
    public synchronized void processAck(Ack ack) {
        try {
            wait(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(ack.isNack())
            System.out.println("Choose other command or try with other parameters");
        askCommand();
    }

    @Override
    public void showLogin(String msg, boolean successful) {
        System.out.println(msg);
    }

    @Override
    public synchronized void showError(String msg) {
        System.out.println(msg);
    }

    @Override
    public synchronized void showMessage(String msg){
        System.out.println(msg);
    }

    @Override
    public synchronized void updateWarehouse(Warehouse warehouse) {
        clientModel.updateWarehouse(warehouse.print());
    }

    @Override
    public synchronized void updateCoffer(Coffer coffer) {
        clientModel.updateCoffer(coffer.print());
    }

    @Override
    public synchronized void updateFaithTrack(Path path) {
        clientModel.updateFaithTrack(path.print());
    }

    @Override
    public synchronized void updateDevCards(DevelopmentCardsArea developmentCardsArea) {
        clientModel.updateDevCardsArea(developmentCardsArea.print());
    }

    @Override
    public void updateActiveLeader(int index) {
        clientModel.getLeaderCards().get(index).setActive(true);
    }

    @Override
    public void disconnect() {
        //close socket?
    }

    private void welcome(){

        System.out.println(Color.ANSI_BLUE.escape() + "#     # \n" +
                            "#  #  # ###### #       ####   ####  #    # ######    #####  ####\n" +
                             "#  #  # #      #      #    # #    # ##  ## #           #   #    #\n" +
                             "#  #  # #####  #      #      #    # # ## # #####       #   #    #\n" +
                             "#  #  # #      #      #      #    # #    # #           #   #    #\n" +
                             "#  #  # #      #      #    # #    # #    # #           #   #    #\n" +
                              "## ##  ###### ######  ####   ####  #    # ######      #    ####\n"+

                             "#     #\n"+
                             "##   ##   ##    ####  ##### ###### #####   ####      ####  ######\n"+
                             "# # # #  #  #  #        #   #      #    # #         #    # #\n"+
                             "#  #  # #    #  ####    #   #####  #    #  ####     #    # #####\n"+
                             "#     # ######      #   #   #      #####       #    #    # #\n"+
                             "#     # #    # #    #   #   #      #   #  #    #    #    # #\n"+
                             "#     # #    #  ####    #   ###### #    #  ####      ####  #\n"+

                             "######\n"+
                             "#     # ###### #    #   ##   #  ####   ####    ##   #    #  ####  ######\n"+
                             "#     # #      ##   #  #  #  # #      #       #  #  ##   # #    # #\n"+
                             "######  #####  # #  # #    # #  ####   ####  #    # # #  # #      #####\n"+
                             "#   #   #      #  # # ###### #      #      # ###### #  # # #      #\n"+
                             "#    #  #      #   ## #    # # #    # #    # #    # #   ## #    # #\n"+
                             "#     # ###### #    # #    # #  ####   ####  #    # #    #  ####  ######\n" + Color.ANSI_WHITE.escape());

    }

}
