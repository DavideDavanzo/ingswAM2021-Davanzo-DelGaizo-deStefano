package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.observingPattern.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.CliPrinter;

import java.util.ArrayList;

/**
 * Class which represents the player's warehouse, where he stocks incoming resources from the market
 */
public class Warehouse extends Observable implements CliPrinter {

    /**
     * It is the upper shelf, which can contain up to one resource
     */
    private final Shelf firstShelf;

    /**
     * It is the middle shelf, which can contain up to two resources
     */
    private final Shelf secondShelf;

    /**
     * It is the lower shelf, which can contain up to three resources
     */
    private final Shelf thirdShelf;

    /**
     * Extra shelves, given by a specific leader card, that contain up to two resources.
     * Unlike the other ones, these shelves have a specific type of resource which can be stock in.
     * During the game, a player will own up to two extra shelves.
     */
    private ArrayList<Shelf> extraShelves;       //not initialized cause just the players who own the specific leader card has one

    /**
     * Default Constructor
     */
    public Warehouse() {
        firstShelf = new Shelf(1);
        secondShelf = new Shelf(2);
        thirdShelf = new Shelf(3);
    }

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
            //local variable used not to lose the shelf resources during the switch
            Resource temp = shelfOne.getShelfResource();

            //assigning second shelf's resources to the first one
            shelfOne.setShelfResource(shelfTwo.getShelfResource());
            shelfTwo.setShelfResource(temp);        //assigning first shelf's resources to the second one
            notifyObservers(this);
        }

    }

    /**
     * Assures that the given shelves are able to be switched
     * @param shelfOne
     * @param shelfTwo
     * @return
     */
    private boolean notValid(Shelf shelfOne, Shelf shelfTwo) {

        //if one of these cases => not valid
        //case 1 and 2: one of the shelves is the warehouse's firstShelf, which means can contain one resource max
        //case 3 and 4: one of the shelves is the warehouse's secondShelf, which means can contain two resource max
        //case 5: one of the shelves is an extraShelf given by the leader card, these type of shelves cannot be switched
        return  (shelfOne == firstShelf && shelfTwo.getResourceVolume() > 1)      //case 1
                || (shelfTwo == firstShelf && shelfOne.getResourceVolume() > 1)      //case 2
                || (shelfOne == secondShelf && shelfTwo.getResourceVolume() > 2)     //case 3
                || (shelfTwo == secondShelf && shelfOne.getResourceVolume() > 2)     //case 4
                // case 5
                || (extraShelves != null && (extraShelves.contains(shelfOne) || extraShelves.contains(shelfTwo)) && !shelfOne.getShelfResource().sameType(shelfTwo.getShelfResource()));

    }

    /**
     * Returns all the shelves of the player's warehouse
     */
    public ArrayList<Shelf> getAllWarehouseShelves() {

        ArrayList<Shelf> warehouseShelves = new ArrayList<>();

        warehouseShelves.add(firstShelf);
        warehouseShelves.add(secondShelf);
        warehouseShelves.add(thirdShelf);
        if(extraShelves != null)
            warehouseShelves.addAll(extraShelves);

        return warehouseShelves;

    }

    public ArrayList<Resource> getAllWarehouseResources() {

        ArrayList<Resource> totWarehouseResources = new ArrayList<>();
        getAllWarehouseShelves().stream().filter(s -> !s.isEmpty()).map(Shelf::getShelfResource).forEach(totWarehouseResources::add);

        return totWarehouseResources;
    }

    public void addExtraShelf(Shelf extraShelf) {

        extraShelf.setAsExtraShelf();
        if(this.extraShelves == null)
            this.extraShelves = new ArrayList<>();
        this.extraShelves.add(extraShelf);
        notifyObservers(this);
    }

    //throws exception if trying to add resources to a shelf while the same type of resource is already in another shelf
    public void addResourcesToShelf(Resource newResource, Shelf shelf) throws NotEnoughResourcesException, InvalidInputException {
        //case: ADDING resources to the MAIN SHELVES only
        if(newResource.getVolume() > 0 && !shelf.isExtraShelf()) {
            if(shelf == firstShelf) {
                if ((!secondShelf.isEmpty() && getSecondShelf().getShelfResource().sameType(newResource)) || (!thirdShelf.isEmpty() && getThirdShelf().getShelfResource().sameType(newResource)))
                    throw new InvalidInputException("Different shelves cannot stock the same type of resource");
            }
            else if(shelf == secondShelf) {
                if ((!firstShelf.isEmpty() && getFirstShelf().getShelfResource().sameType(newResource)) || (!thirdShelf.isEmpty() && getThirdShelf().getShelfResource().sameType(newResource)))
                    throw new InvalidInputException("Different shelves cannot stock the same type of resource");
            }
            else if(shelf == thirdShelf) {
                if ((!firstShelf.isEmpty() && getFirstShelf().getShelfResource().sameType(newResource)) || (!secondShelf.isEmpty() && getSecondShelf().getShelfResource().sameType(newResource)))
                    throw new InvalidInputException("Different shelves cannot stock the same type of resource");
            }
        }
        shelf.updateShelf(newResource);
        notifyObservers(this);
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

    @Override
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("      ╔═════╗\n")
                     .append("      ║ " + (getFirstShelf().getShelfResource() != null ? getFirstShelf().getShelfResource().getVolume() : " -") + " " + (getFirstShelf().getShelfResource() != null ? getFirstShelf().getShelfResource().print() : " ") + "║\n")
                     .append("      ╚═════╝\n")
                     .append("   ╔═══════════╗\n")
                     .append("   ║    " + (getSecondShelf().getShelfResource() != null ? getSecondShelf().getShelfResource().getVolume() : " -") + " " + (getSecondShelf().getShelfResource() != null ? getSecondShelf().getShelfResource().print() : " ") + "   ║\n")
                     .append("   ╚═══════════╝\n")
                     .append("╔══════════════════╗\n")
                     .append("║       " + (getThirdShelf().getShelfResource() != null ? getThirdShelf().getShelfResource().getVolume() : " -") + " " + (getThirdShelf().getShelfResource() != null ? getThirdShelf().getShelfResource().print() : " ") + "       ║\n")
                     .append("╚══════════════════╝\n");

        if (extraShelves != null) {
            for (int i = 0; i < extraShelves.size() - 1; i++) {
                stringBuilder.append("   ╔═══════════╗\n")
                        .append("   ║ " + (extraShelves.get(i).getShelfResource().getVolume()) + " " + extraShelves.get(i).getShelfResource().print() + "   ║\n")
                        .append("   ╚═══════════╝\n");
            }
        }
        return stringBuilder.toString();

    }

}