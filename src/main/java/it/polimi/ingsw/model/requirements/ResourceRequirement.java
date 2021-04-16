package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

public class ResourceRequirement extends Requirement{

    Resource resource;

    public ResourceRequirement() { }

    public ResourceRequirement(Resource resource) {
        this.resource = resource;
    }

    @Override
    public boolean validateOn(Player p) {
        //TODO: Implement with Warehouse-Coffer logic.
        return false;
    }

    @Override
    public Object getAttribute() {
        return resource;
    }

}
