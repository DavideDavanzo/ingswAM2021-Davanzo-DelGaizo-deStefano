package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.resources.*;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.ArrayList;


/**
 * Coffer is the place where the production resources are stored.
 * It can contain any quantity of every Resource: {@link Coin}, {@link Servant},
 * {@link Stone}, {@link Shield}.
 */
public class Coffer extends Observable implements CliPrinter {

    private Resource coins = new Coin();
    private Resource stones = new Stone();
    private Resource shields = new Shield();
    private Resource servants = new Servant();

    /**
     * It updates the Coffer with the specified item.
     * The resource volume can either be positive (Production) or negative(Payment)
     * @param newResource
     * @throws NotEnoughResourcesException
     * @throws InvalidInputException
     */
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

    /**
     * @return a list containing all the Coffer resources.
     */
    public ArrayList<Resource> getAllCofferResources() {

        ArrayList<Resource> totalResources = new ArrayList<>();

        totalResources.add(coins);
        totalResources.add(shields);
        totalResources.add(stones);
        totalResources.add(servants);

        return totalResources;
    }

    @Override
    public String print() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("╔════════════════════╗\n")
                     .append("║ "  + getCoins().getVolume() + " " + getCoins().print() + "           " + getShields().getVolume() + " " + getShields().print() + "║ \n")
                     .append("║                    ║ \n")
                     .append("║ " + getServants().getVolume() + " " + getServants().print() + "           "                      + getStones().getVolume() + " " + getStones().print() + "║\n")
                     .append("╚════════════════════╝\n");

        Color.ANSI_WHITE.escape();
        return stringBuilder.toString();
    }

}