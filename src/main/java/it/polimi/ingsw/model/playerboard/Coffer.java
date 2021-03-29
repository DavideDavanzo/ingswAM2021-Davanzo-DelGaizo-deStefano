package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.resources.*;


/**
 * Coffer is the place where the player stores his production resources
 */
public class Coffer {

    private Resource coins = new Coin();
    private Resource stones = new Stone();
    private Resource shields = new Shield();
    private Resource servants = new Servant();

    /**
     * Modifies the number of coins
     * @param newCoins
     * @throws NotEnoughResourcesException
     */
    public void updateCoffer(Coin newCoins) throws NotEnoughResourcesException {
        coins.update(newCoins);
    }

    /**
     * Modifies the number of stones
     * @param newStones
     * @throws NotEnoughResourcesException
     */
    public void updateCoffer(Stone newStones) throws NotEnoughResourcesException {
        stones.update(newStones);
    }

    /**
     * Modifies the number of shields
     * @param newShields
     * @throws NotEnoughResourcesException
     */
    public void updateCoffer(Shield newShields) throws NotEnoughResourcesException {
        shields.update(newShields);
    }

    /**
     * Modifies the number of servants
     * @param newServants
     * @throws NotEnoughResourcesException
     */
    public void updateCoffer(Servant newServants) throws NotEnoughResourcesException {
        servants.update(newServants);
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

}
