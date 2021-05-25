package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.ArrayList;


/**
 * Coffer is the place where the player stores his production resources
 */
public class Coffer extends Observable implements CliPrinter {

    private Resource coins = new Coin();
    private Resource stones = new Stone();
    private Resource shields = new Shield();
    private Resource servants = new Servant();

    //add the incoming resources to the correct attribute
    public void updateCoffer(Item newResource) throws NotEnoughResourcesException, InvalidInputException {
        coins.update(newResource);
        stones.update(newResource);
        shields.update(newResource);
        servants.update(newResource);
        notifyObservers(this);
    }

    public Resource getCoins() {
        return coins;
    }

    public Resource getStones() {
        return stones;
    }

    public Resource getShields() {
        return shields;
    }

    public Resource getServants() {
        return servants;
    }

    public ArrayList<Resource> getAllCofferResources() {

        ArrayList<Resource> totalResources = new ArrayList<>();

        totalResources.add(coins);
        totalResources.add(shields);
        totalResources.add(stones);
        totalResources.add(servants);

        return totalResources;
    }

    public String printResourceCoffer(){

        StringBuilder stringBuilder = new StringBuilder();
        for(Resource resource : getAllCofferResources()){
            stringBuilder.append(resource.print());
        }
        return stringBuilder.toString();
    }



    @Override
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("╔════════════════════╗\n")
                     .append("║ "  + getCoins().getVolume() + " " + getCoins().print() + "           " + getShields().getVolume() + " " + getShields().print() + "║ \n")
                     .append("║                    ║ \n")
                     .append("║ " + getServants().getVolume() + " " + getServants().print() + "           "                      + getStones().getVolume() + " " + getStones().print() + "║\n")
                     .append("╚════════════════════╝");

        Color.ANSI_WHITE.escape();
        return stringBuilder.toString();
    }


}
