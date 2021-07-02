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
 * The PlayerBoard is the main feature of the game. It contains a {@link Warehouse} and a
 * {@link Coffer} to store resources, a {@link DevelopmentCardsArea} to store Development Cards and
 * the {@link Path}.
 */
public class PlayerBoard {

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

    /**
     * Given a list of development cards, checks if there are enough resource in the player's warehouse and coffer to
     * activate a resource Production.
     * If so, takes input resources starting from the main shelves and sequentially towards the coffer, if needed.
     * @param chosenDevCards
     * @return
     * @throws ProductionFailException if the payment fails.
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException
     */
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

    /**
     * Puts all the incoming resources in the coffer and updates the player's faith points if needed.
     * @param totalProductionOutput
     * @return
     * @throws InvalidInputException
     * @throws NotEnoughResourcesException
     */
    public boolean produce(ArrayList<Item> totalProductionOutput) throws InvalidInputException, NotEnoughResourcesException {
        boolean cantMove = false;
        for(Item product : totalProductionOutput) {
                cantMove = path.moveForward(product.pathSteps());
                coffer.updateCoffer(product);
        }
        return cantMove;
    }

    /**
     * Takes the resources needed for the transaction from the player's warehouse and coffer. In that order.
     * @param totalInputRequired
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException
     */
    public void payRequiredResources(ArrayList<Resource> totalInputRequired) throws NotEnoughResourcesException, InvalidInputException {

        int previousVolume;
        ArrayList<Resource> totalCost = new ArrayList<>(totalInputRequired.size());
        for(Resource r : totalInputRequired) totalCost.add(r.clone());

        for(Resource resource : totalCost) {

            for (Shelf s : getWarehouse().getAllWarehouseShelves()) {

                if (!s.isEmpty()) {

                    previousVolume = s.getShelfResource().getVolume();

                    if (previousVolume >= resource.getVolume()) {

                        resource.setVolume(-resource.getVolume());
                        warehouse.addResourcesToShelf(resource, s);
                        resource.setVolume(-resource.getVolume());

                        if(s.isEmpty() || s.getShelfResource().getVolume() != previousVolume) {
                            resource.setVolume(0);
                        }

                    }
                    else {

                        try {

                            resource.setVolume(-resource.getVolume());
                            warehouse.addResourcesToShelf(resource, s);
                            resource.setVolume(-resource.getVolume());

                        } catch(NotEnoughResourcesException e) {

                            resource.setVolume(resource.getVolume() + s.getShelfResource().getVolume());

                            if(!s.isExtraShelf())
                                warehouse.emptyShelf(s);
                            else {
                                s.getShelfResource().setVolume(0);
                                warehouse.notifyObservers(warehouse);
                            }

                            if(warehouse.getExtraShelves() != null && warehouse.getExtraShelves().size() != 0){
                                for(Shelf extraShelf : warehouse.getExtraShelves()){
                                    if(!extraShelf.isEmpty()) {
                                        previousVolume = extraShelf.getShelfResource().getVolume();
                                        if (extraShelf.getShelfResource().getVolume() >= -resource.getVolume()) {
                                            warehouse.addResourcesToShelf(resource, extraShelf);
                                            if (extraShelf.isEmpty() || extraShelf.getShelfResource().getVolume() != previousVolume)
                                                resource.setVolume(0);
                                        } else {
                                            try{
                                                warehouse.addResourcesToShelf(resource, extraShelf);
                                            } catch(NotEnoughResourcesException ex) {
                                                extraShelf.emptyThisShelf();
                                                resource.setVolume(resource.getVolume() + previousVolume);
                                            }
                                        }
                                    }
                                }
                            }

                            if(resource.getVolume() != 0) {
                                coffer.updateCoffer(resource);
                                resource.setVolume(0);
                            }

                        }

                    }
                }

            }

            if(resource.getVolume() != 0) {
                resource.setVolume(-resource.getVolume());
                coffer.updateCoffer(resource);
                resource.setVolume(0);
            }

        }
    }

    /**
     * Checks, parsing the warehouse and the coffer, if the player has enough resources for the transaction.
     * @param totalInputRequired is the total input required by the transaction.
     * @return
     * @throws NotEnoughResourcesException
     */
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

    /**
     * Activates a base production.
     * @param input base production input
     * @param output base production output
     * @return true if the last {@link it.polimi.ingsw.model.playerboard.path.Square} is reached.
     * @throws NotEnoughResourcesException if the player doesn't match the production requirements.
     * @throws InvalidInputException
     */
    public boolean activateBaseProduction(ArrayList<Resource> input, Item output) throws NotEnoughResourcesException, InvalidInputException {
        payRequiredResources(input);
        coffer.updateCoffer(output);
        return path.moveForward(output.pathSteps());
    }

    /**
     * Calculates all the victory points present in the PlayerBoard, them being:
     * DevCards points, Path points and 1 point every 5 resources left.
     * @return
     */
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

        int resourcesPoints = getAllResourcePoints();

        return boardPoints + (resourcesPoints / 5);

    }

    /**
     * @return the total resources points. 1 for every resource.
     */
    public int getAllResourcePoints() {
        int resourcesPoints = 0;
        for(Shelf shelf : warehouse.getAllWarehouseShelves()){
            if(!shelf.isEmpty()) resourcesPoints += shelf.getShelfResource().getVolume();
        }
        for(Resource resource : coffer.getAllCofferResources()){
            resourcesPoints += resource.getVolume();
        }
        return resourcesPoints;
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

}
