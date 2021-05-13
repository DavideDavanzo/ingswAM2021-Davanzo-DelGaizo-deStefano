package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.CliPrinter;
import it.polimi.ingsw.model.resources.Resource;

public class Discount {

    private boolean active;
    private Resource discountResource;

    public Discount() {
        this.active = false;
    }

    public Discount(Resource discountResource) {
        this.active = false;
        this.discountResource = discountResource;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public Resource getDiscountResource() {
        return discountResource;
    }





}
