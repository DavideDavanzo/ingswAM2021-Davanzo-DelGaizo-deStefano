package it.polimi.ingsw.cliTest;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class cliPrintTest {


        @Test
        public void printAllLeader(){

            LeaderCardParser leaderCardParser = new LeaderCardParser();

            for(LeaderCard leaderCard :  leaderCardParser.parse()){
              // System.out.println(leaderCard.print());
            }

        }

        @Test
        public void printAllDev(){

            DevCardParser devCardParser = new DevCardParser();

            for(DevelopmentCard card : devCardParser.parse()){
              //  System.out.println(card.print());
            }

        }

        @Test
        public void printCoffer() throws InvalidInputException, NotEnoughResourcesException {
          //  System.out.println("COFFER:\n");
            Coffer coffer = new Coffer();
            coffer.updateCoffer(new Coin(1));
            coffer.updateCoffer(new Coin(1));
            coffer.updateCoffer(new Shield(1));
            coffer.updateCoffer(new Servant(1));

           // System.out.println(coffer.print());
        }

        @Test
        public void printWarehouse() throws InvalidInputException, NotEnoughResourcesException {
           // System.out.println("WAREHOUSE:\n");
            Warehouse warehouse = new Warehouse();
            warehouse.addResourcesToShelf(new Coin(1), warehouse.getFirstShelf());
            warehouse.addResourcesToShelf(new Servant(1), warehouse.getSecondShelf());
            warehouse.addResourcesToShelf(new Shield(1), warehouse.getSecondShelf());
            warehouse.addResourcesToShelf(new Stone(3),warehouse.getThirdShelf());


          //  System.out.println(warehouse.print());
        }

        @Test
        public void printPath() throws InvalidInputException {
            //System.out.println("PATH:\n");


            Path path = new Path();

            path.getPopeTokens().get(1).isFaceUp();
            path.moveForward(8);
           // System.out.println(path.print());
        }

        @Test
        public void printMarket(){
            Market market = new Market();

            //System.out.println(market.print());
        }

        @Test
        public void printDevCardArea() throws InvalidInputException {

            DevelopmentCardsArea developmentCard = new DevelopmentCardsArea();
            ArrayList<Resource> cost = new ArrayList<Resource>();
            cost.add(new Stone(1));
            cost.add(new Shield(1));
            cost.add(new Coin(1));
            ArrayList<Resource> input = new ArrayList<Resource>();
            input.add(new Coin(1));
            input.add(new Shield(1));
            input.add(new Stone(2));
            ArrayList<Item> output = new ArrayList<Item>();
            output.add(new FaithPoint(2));
            output.add(new Coin(1));
            output.add(new Servant(1));
            Trade trade = new Trade(input,output);

            developmentCard.getFirstStack().push(new DevelopmentCard(ECardColor.PURPLE, 1, cost, trade, 7));
            developmentCard.getSecondStack().push(new DevelopmentCard(ECardColor.GREEN, 1, cost, trade, 8));
            developmentCard.getThirdStack().push(new DevelopmentCard(ECardColor.BLUE, 1, cost, trade, 9));
            developmentCard.getFirstStack().push(new DevelopmentCard(ECardColor.PURPLE, 2, cost, trade, 11));
            developmentCard.getFirstStack().push(new DevelopmentCard(ECardColor.GREEN, 3, cost, trade, 12));

           // System.out.println((developmentCard.print()));
        }

        @Test
        public void printDevMarket(){

            CardMarket cardMarket = new CardMarket();

          //  System.out.println(cardMarket.print());
        }



    }
