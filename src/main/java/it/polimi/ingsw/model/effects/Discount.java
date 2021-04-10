package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.resources.Resource;

public class Discount {

    private boolean active;
    private Resource resourceDiscount;

    public Discount() {
        this.active = false;
    }

    public Discount(Resource resourceDiscount) {
        this.active = false;
        this.resourceDiscount = resourceDiscount;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }
}
