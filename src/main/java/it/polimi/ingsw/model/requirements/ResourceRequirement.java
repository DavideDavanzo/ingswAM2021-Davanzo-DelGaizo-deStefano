package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

public class ResourceRequirement extends Requirement {

    Resource resource;

    public ResourceRequirement() { }

    public ResourceRequirement(Resource resource) {
        this.resource = resource;
    }

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
    public Object getAttribute() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Have at least " + resource.toString();
    }
}
