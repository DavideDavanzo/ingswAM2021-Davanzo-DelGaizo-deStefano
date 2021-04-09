package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

/**
 * Class which represents the player's warehouse, where he stocks incoming resources from the market
 */
public class Warehouse {

    /**
     * It is the upper shelf, which can contain up to one resource
     */
    private final Shelf firstShelf = new Shelf(1);

    /**
     * It is the middle shelf, which can contain up to two resources
     */
    private final Shelf secondShelf = new Shelf(2);

    /**
     * It is the lower shelf, which can contain up to three resources
     */
    private final Shelf thirdShelf = new Shelf(3);

    /**
     * Extra shelves, given by a specific leader card, that contain up to two resources.
     * Unlike the other ones, these shelves have a specific type of resource which can be stock in.
     * During the game, a player will own up to two extra shelves.
     */
    private ArrayList<Shelf> extraShelves;       //not initialized cause just the players who own the specific leader card has one

    /**
     * Method to switch two of the three warehouse's main shelves
     * @param shelfOne
     * @param shelfTwo
     * @throws InvalidInputException
     */
    public void switchShelves(Shelf shelfOne, Shelf shelfTwo) throws InvalidInputException {

        if(notValid(shelfOne, shelfTwo))
            throw new InvalidInputException("These shelves cannot be switched");
        else {
            //local variable used to not loose the shelf resources during the switch
            Resource temp = shelfOne.getShelfResource();

            //assigning second shelf's resources to the first one
            shelfOne.setShelfResource(shelfTwo.getShelfResource());
            shelfTwo.setShelfResource(temp);        //assigning first shelf's resources to the second one
        }

    }

    /**
     * Assures that the given shelves are able to be switched
     * @param shelfOne
     * @param shelfTwo
     * @return
     */
    private boolean notValid(Shelf shelfOne, Shelf shelfTwo){

        //if one of these cases => not valid
        //case 1 and 2: one of the shelves is the warehouse's firstShelf, which means can contain one resource max
        //case 3 and 4: one of the shelves is the warehouse's firstShelf, which means can contain two resource max
        //case 5: one of the shelves is an extraShelf given by the leader card, these type of shelves cannot be switched
        return (shelfOne == firstShelf && shelfTwo.getShelfResource().getVolume() > 1)         //case 1
                || (shelfTwo == firstShelf && shelfOne.getShelfResource().getVolume() > 1)      //case 2
                || (shelfOne == secondShelf && shelfTwo.getShelfResource().getVolume() > 2)     //case 3
                || (shelfTwo == secondShelf && shelfOne.getShelfResource().getVolume() > 2)     //case 4
                // case 5
                || (extraShelves != null && ( extraShelves.contains(shelfOne) || extraShelves.contains(shelfTwo) ) );

    }

    /**
     * Returns all the resources currently stocked in the player's warehouse
     */
    public ArrayList<Shelf> getAllWarehouseShelves() {

        ArrayList<Shelf> warehouseShelves = new ArrayList<>();

        warehouseShelves.add(firstShelf);
        warehouseShelves.add(secondShelf);
        warehouseShelves.add(thirdShelf);
        warehouseShelves.addAll(extraShelves);

        return warehouseShelves;

    }

    public Shelf getFirstShelf() {
        return firstShelf;
    }

    public Shelf getSecondShelf() {
        return secondShelf;
    }

    public Shelf getThirdShelf() {
        return thirdShelf;
    }

    public ArrayList<Shelf> getExtraShelves() {
        return extraShelves;
    }

    public void addExtraShelf(Shelf extraShelf) {

        if(this.extraShelves == null)
            this.extraShelves = new ArrayList<>();
        this.extraShelves.add(extraShelf);

    }

}
