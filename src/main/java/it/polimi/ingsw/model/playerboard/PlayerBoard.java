package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.ProductionFailException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.resources.*;

import java.util.ArrayList;

/**
 * <h1>PlayerBoard</h1>
 */
public class PlayerBoard {

    private final Warehouse warehouse;
    private final Coffer coffer;
    private final DevelopmentCardsArea developmentCardsArea;
    private final Path path;

    public PlayerBoard() {
        warehouse = new Warehouse();
        coffer = new Coffer();
        path = new Path();
        developmentCardsArea = new DevelopmentCardsArea();
    }

    //given an arraylist of development cards, check if there are enough resource iun the player's warehouse and coffer.
    // If so take input resources starting from the main shelves and sequentially towards the coffer if needed
    public void activateProduction(ArrayList<DevelopmentCard> chosenDevCards) throws ProductionFailException, NotEnoughResourcesException, InvalidInputException {

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
            produce(totalProductionOutput);
        }

    }

    //put all the incoming resource in the coffer and update the player's faith points
    public void produce(ArrayList<Item> totalProductionOutput) throws InvalidInputException, NotEnoughResourcesException {

        for(Item product : totalProductionOutput) {
            if (product instanceof FaithPoint)
                path.moveForward((FaithPoint) product);
            else
                coffer.updateCoffer(product);
        }

    }

    //take from the player's warehouse and coffer the resources needed by the transaction
    public void payRequiredResources(ArrayList<Resource> totalInputRequired) throws NotEnoughResourcesException, InvalidInputException {

        int previousVolume;

        for(Resource newResource : totalInputRequired) {

            newResource.setVolume(-newResource.getVolume());

            for (Shelf s : getWarehouse().getAllShelves()) {
                if (s.getShelfResource() != null) {
                    previousVolume = s.getShelfResource().getVolume();
                    if (s.getShelfResource().getVolume() >= -(newResource.getVolume())) {
                        s.updateShelf(newResource);
                        if(s.getShelfResource() == null || s.getShelfResource().getVolume() != previousVolume)
                            newResource.setVolume(0);
                    } else {
                        try {
                            s.updateShelf(newResource);
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

            if(newResource.getVolume() != 0)
                coffer.updateCoffer(newResource);

        }
    }

    //check if the player has enough resources to spend for the transaction
    public boolean possiblePayment(ArrayList<Resource> totalInputRequired) throws NotEnoughResourcesException, InvalidInputException {

        ArrayList<Resource> playerGivenInput = new ArrayList<>();
        playerGivenInput.addAll(warehouse.getAllWarehouseResources());
        playerGivenInput.addAll(coffer.getAllCofferResources());

        //
        Resource tempResourceRequire = new Coin();
        Resource tempPlayerResource = new Coin();
        for(Resource r : totalInputRequired)
            if(tempResourceRequire.sameType(r))
                tempResourceRequire.update(r);

        for(Resource r : playerGivenInput)
            if(tempPlayerResource.sameType(r))
                tempPlayerResource.update(r);

            if(tempPlayerResource.getVolume() < tempResourceRequire.getVolume())
                return false;

        //
        tempResourceRequire = new Stone();
        tempPlayerResource = new Stone();
        for(Resource r : totalInputRequired)
            if(tempResourceRequire.sameType(r))
                tempResourceRequire.update(r);
        for(Resource r : playerGivenInput)
            if(tempPlayerResource.sameType(r))
                tempPlayerResource.update(r);
        if(tempPlayerResource.getVolume() < tempResourceRequire.getVolume())
            return false;

        //
        tempResourceRequire = new Shield();
        tempPlayerResource = new Shield();
        for(Resource r : totalInputRequired)
            if(tempResourceRequire.sameType(r))
                tempResourceRequire.update(r);
        for(Resource r : playerGivenInput)
            if(tempPlayerResource.sameType(r))
                tempPlayerResource.update(r);
        if(tempPlayerResource.getVolume() < tempResourceRequire.getVolume())
            return false;

        //
        tempResourceRequire = new Servant();
        tempPlayerResource = new Servant();
        for(Resource r : totalInputRequired)
            if(tempResourceRequire.sameType(r))
                tempResourceRequire.update(r);
        for(Resource r : playerGivenInput)
            if(tempPlayerResource.sameType(r))
                tempPlayerResource.update(r);
        return tempPlayerResource.getVolume() >= tempResourceRequire.getVolume();

    }

    public void activateBaseProduction(ArrayList<Resource> input, Item output) throws NotEnoughResourcesException, InvalidInputException {

        payRequiredResources(input);

        if (output instanceof FaithPoint)
            path.moveForward((FaithPoint) output);
        else
            coffer.updateCoffer((Resource) output);

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

}
