package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
/**
 * This class implements one of the leader card requirement
 * it requires a certain amount of resource
 */
public class ResourceRequirement extends Requirement {

    Resource resource;

    public ResourceRequirement() { }

    public ResourceRequirement(Resource resource) {
        this.resource = resource;
    }

    /**
     * Validates the requirement on a player.
     * @param p is the Player on which the control is done.
     * @return true if the player matches the requirement.
     */
    @Override
    public boolean validateOn(Player p) {
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(resource.clone());
        try {
            return p.getPlayerBoard().possiblePayment(resources);
        } catch (NotEnoughResourcesException e) {
            return false;
        }
    }

    @Override
    public Object returnAttribute() {
        return getResource();
    }

    public Resource getResource(){
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Have at least " + resource.toString();
    }

    @Override
    public String print() {
        return resource.getVolume() + " " + resource.print() + "                                ";
    }

}
