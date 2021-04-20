package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.resources.*;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Coffer is the place where the player stores his production resources
 */
public class Coffer {

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

}
