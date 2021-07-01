package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.resources.Resource;

/**
 * A Discount is defined as a resource volume.
 * When buying a card from the market, the Discount is applied if the relative
 * {@link it.polimi.ingsw.model.cards.LeaderCard} is activated by the player.
 */
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
