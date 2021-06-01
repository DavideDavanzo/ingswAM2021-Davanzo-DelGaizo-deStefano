package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.ProductionFailException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.EndGameException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.ArrayList;

/**
 * <h1>PlayerBoard</h1>
 */
public class PlayerBoard implements CliPrinter {

    private Warehouse warehouse;
    private Coffer coffer;
    private DevelopmentCardsArea developmentCardsArea;
    private Path path;

    public PlayerBoard() {
        warehouse = new Warehouse();
        coffer = new Coffer();
        path = new Path();
        developmentCardsArea = new DevelopmentCardsArea();
    }

    //given an arraylist of development cards, check if there are enough resource iun the player's warehouse and coffer.
    // If so take input resources starting from the main shelves and sequentially towards the coffer if needed
    public boolean activateProduction(ArrayList<DevelopmentCard> chosenDevCards) throws ProductionFailException, NotEnoughResourcesException, InvalidInputException {

        ArrayList<Resource> totalInputRequired = new ArrayList<>();
        ArrayList<Item> totalProductionOutput = new ArrayList<>();

        for(DevelopmentCard developmentCard : chosenDevCards)
            totalInputRequired.addAll(developmentCard.getTrade().getInput());
        for(DevelopmentCard developmentCard : chosenDevCards)
            totalProductionOutput.addAll(developmentCard.getTrade().getOutput());

        if(!possiblePayment(totalInputRequired))
            throw new ProductionFailException("Resources chosen do not match with production requirements");
        else {
            payRequiredResources(totalInputRequired);
            return produce(totalProductionOutput);
        }

    }

    //put all the incoming resource in the coffer and update the player's faith points
    public boolean produce(ArrayList<Item> totalProductionOutput) throws InvalidInputException, NotEnoughResourcesException {
        boolean cantMove = false;
        for(Item product : totalProductionOutput) {
                cantMove = path.moveForward(product.pathSteps());
                coffer.updateCoffer(product);

        }
        return cantMove;
    }

    //take from the player's warehouse and coffer the resources needed by the transaction
    public void payRequiredResources(ArrayList<Resource> totalInputRequired) throws NotEnoughResourcesException, InvalidInputException {

        int previousVolume;
        ArrayList<Resource> totalCost = new ArrayList<>(totalInputRequired.size());
        for(Resource r : totalInputRequired) totalCost.add(r.clone());

        for(Resource newResource : totalCost) {

            for (Shelf s : getWarehouse().getAllWarehouseShelves()) {

                if (!s.isEmpty()) {

                    previousVolume = s.getShelfResource().getVolume();

                    if (previousVolume >= newResource.getVolume()) {

                        newResource.setVolume(-newResource.getVolume());
                        warehouse.addResourcesToShelf(newResource, s);
                        newResource.setVolume(-newResource.getVolume());

                        if(s.isEmpty() || s.getShelfResource().getVolume() != previousVolume) {
                            newResource.setVolume(0);
                        }

                    }
                    else {

                        try {

                            newResource.setVolume(-newResource.getVolume());
                            warehouse.addResourcesToShelf(newResource, s);
                            newResource.setVolume(-newResource.getVolume());

                        } catch(NotEnoughResourcesException e) {

                            newResource.setVolume(newResource.getVolume() + s.getShelfResource().getVolume());

                            if(!s.isExtraShelf())
                                s.emptyThisShelf();
                            else
                                s.getShelfResource().setVolume(0);

                            coffer.updateCoffer(newResource);
                            newResource.setVolume(0);

                        }

                    }
                }

            }

            if(newResource.getVolume() != 0) {
                newResource.setVolume(-newResource.getVolume());
                coffer.updateCoffer(newResource);
                newResource.setVolume(0);
            }

        }
    }

    //check if the player has enough resources to spend for the transaction
    public boolean possiblePayment(ArrayList<Resource> totalInputRequired) throws NotEnoughResourcesException {

        ArrayList<Resource> playerGivenInput = new ArrayList<>();
        playerGivenInput.addAll(warehouse.getAllWarehouseResources());
        playerGivenInput.addAll(coffer.getAllCofferResources());

        //
        Resource tempResourceRequire = new Coin();
        Resource tempPlayerResource = new Coin();

        for(Resource r : totalInputRequired)
            tempResourceRequire.update(r);
        for(Resource r : playerGivenInput)
            tempPlayerResource.update(r);

        if(tempPlayerResource.getVolume() < tempResourceRequire.getVolume())
            return false;

        //
        tempResourceRequire = new Stone();
        tempPlayerResource = new Stone();

        for(Resource r : totalInputRequired)
            tempResourceRequire.update(r);
        for(Resource r : playerGivenInput)
            tempPlayerResource.update(r);

        if(tempPlayerResource.getVolume() < tempResourceRequire.getVolume())
            return false;

        //
        tempResourceRequire = new Shield();
        tempPlayerResource = new Shield();

        for(Resource r : totalInputRequired)
            tempResourceRequire.update(r);
        for(Resource r : playerGivenInput)
            tempPlayerResource.update(r);

        if(tempPlayerResource.getVolume() < tempResourceRequire.getVolume())
            return false;

        //
        tempResourceRequire = new Servant();
        tempPlayerResource = new Servant();

        for(Resource r : totalInputRequired)
            tempResourceRequire.update(r);
        for(Resource r : playerGivenInput)
            tempPlayerResource.update(r);

        return tempPlayerResource.getVolume() >= tempResourceRequire.getVolume();

    }

    public boolean activateBaseProduction(ArrayList<Resource> input, Item output) throws NotEnoughResourcesException, InvalidInputException
    {
        payRequiredResources(input);
        coffer.updateCoffer(output);
        return path.moveForward(output.pathSteps());
    }

    public int calculateVictoryPoints() {

        int boardPoints = 0;

        //add victory points given by all the player's development cards
        for(DevelopmentCard playerDevCard : developmentCardsArea.getFirstStack())
            boardPoints += playerDevCard.getVictoryPoints();
        for(DevelopmentCard playerDevCard : developmentCardsArea.getSecondStack())
            boardPoints += playerDevCard.getVictoryPoints();
        for(DevelopmentCard playerDevCard : developmentCardsArea.getThirdStack())
            boardPoints += playerDevCard.getVictoryPoints();

        //add also points given by the path position and papal tokens
        boardPoints += path.getPathVictoryPoints();

        return boardPoints;

    }

    public DevelopmentCardsArea getDevelopmentCardsArea() {
        return developmentCardsArea;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Coffer getCoffer() {
        return coffer;
    }

    public Path getPath() {
        return path;
    }

    public void setWarehouse(Warehouse warehouse){
        this.warehouse = warehouse;
    }

    public void setCoffer(Coffer coffer) {
        this.coffer = coffer;
    }

    public void setDevelopmentCardsArea(DevelopmentCardsArea developmentCardsArea) {
        this.developmentCardsArea = developmentCardsArea;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("       ╔═════╗\n")
                     .append("       ║ " + (warehouse.getFirstShelf().getShelfResource() != null ? warehouse.getFirstShelf().getShelfResource().getVolume() : " -") + " " + (warehouse.getFirstShelf().getShelfResource() != null ? warehouse.getFirstShelf().getShelfResource().print() : " ") + "║ " + "                       ┌─────"+ Color.ANSI_RED.escape() +"───────────────"+ Color.ANSI_WHITE.escape() +"─────────┐    ┌─────────┐ "+ Color.ANSI_RED.escape() +"   ┌─────────────────────────────┐"+ Color.ANSI_WHITE.escape() + "                 ╔════════════════════╗\n")
                     .append("       ╚═════╝" + "                        │  " + path.crossValue(5) + Color.ANSI_RED.escape() + " │ " + path.crossValue(6) +  "  │ " + path.crossValue(7) + "  │ " + path.crossValue(8) + "  │ "+ Color.ANSI_WHITE.escape()+ path.crossValue(9) +"  │ " + path.crossValue(10) + " │    │  " + path.getPopeTokens().get(1).popeToken() +"  │ "+ Color.ANSI_RED.escape() +"   │ " + path.crossValue(19) + " │ "+ path.crossValue(20) + " │ " + path.crossValue(21) + " │ " + path.crossValue(22) +" │ " + path.crossValue(23) + " │ " + path.crossValue(24) + " │"+ Color.ANSI_WHITE.escape() + "                 ║ "  + coffer.getCoins().getVolume() + " " + coffer.getCoins().print() + "           " + coffer.getShields().getVolume() + " " + coffer.getShields().print() + "║ \n")
                     .append("    ╔═══════════╗" + "                     │────"+ Color.ANSI_RED.escape() +"┌───────────────"+ Color.ANSI_WHITE.escape() +"────┐────│    │         │  "+ Color.ANSI_RED.escape() +"  │────┌────────────────────────┘"+ Color.ANSI_WHITE.escape() +"                 ║                    ║ \n")
                     .append("    ║    " + (warehouse.getSecondShelf().getShelfResource() != null ? warehouse.getSecondShelf().getShelfResource().getVolume() : " -") + " " + (warehouse.getSecondShelf().getShelfResource() != null ? warehouse.getSecondShelf().getShelfResource().print() : " ") + "   ║" + "                     │ "+ path.crossValue(4) + "  │    ┌─────────┐    │ " + path.crossValue(11) + " │    └─────────┘    │ " + path.crossValue(18) + " │      ┌─────────┐ " + "                        ║ " + coffer.getServants().getVolume() + " " + coffer.getServants().print() + "           "                      + coffer.getStones().getVolume() + " " + coffer.getStones().print() + "║\n")
                     .append("    ╚═══════════╝" + "         ┌───────────┘────│    │  " + path.getPopeTokens().get(0).popeToken() + "  │"+ Color.ANSI_RED.escape() +"    │────└───────────────────"+ Color.ANSI_WHITE.escape() +"┘────│      │  " + path.getPopeTokens().get(2).popeToken() + "  │" + "                         ╚════════════════════╝\n")
                    .append(" ╔══════════════════╗" + "     │ " + path.crossValue(0) +" │ "    + path.crossValue(1) + " │ " + path.crossValue(2) + " │ " + path.crossValue(3) + "  │    │         │ "+ Color.ANSI_RED.escape() +"   │ " + path.crossValue(12) + " │ "+ path.crossValue(13) + " │ " + path.crossValue(14) +" │ "+ path.crossValue(15) + " │ " + path.crossValue(16) + " │ "+ Color.ANSI_WHITE.escape() +path.crossValue(17) + " │      │         │\n ")
                    .append("║       " + (warehouse.getThirdShelf().getShelfResource() != null ? warehouse.getThirdShelf().getShelfResource().getVolume() : " -") + " " + (warehouse.getThirdShelf().getShelfResource() != null ? warehouse.getThirdShelf().getShelfResource().print() : " ") + "       ║" + "     └────────────────┘    └─────────┘"+ Color.ANSI_RED.escape() +"    └────────────────────────"+ Color.ANSI_WHITE.escape() +"─────┘      └─────────┘    \n")
                    .append(" ╚══════════════════╝\n");

        if (warehouse.getExtraShelves() != null) {
            stringBuilder.append("Extra shelves:\n");
            for (Shelf extraShelf : warehouse.getExtraShelves()) {
                stringBuilder.append("   ╔═══════════════╗\n")
                             .append("   ║      " + (extraShelf.getShelfResource().getVolume()) + " " + extraShelf.getShelfResource().print() + "     ║\n")
                             .append("   ╚═══════════════╝\n");
            }

            stringBuilder.append(developmentCardsArea.print());
        }
        return stringBuilder.toString();

    }
}
