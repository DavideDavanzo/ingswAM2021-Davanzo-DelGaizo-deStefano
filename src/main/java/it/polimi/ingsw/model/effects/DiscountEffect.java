package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

public class DiscountEffect extends Effect {

    private Discount discount;

    /**
     * Default Constructor
     */
    public DiscountEffect() { }

    /**
     * Main Constructor
     * @param discount is a Discount related to a certain {@link Resource}
     */
    public DiscountEffect(Discount discount) {
        this.discount = discount;
    }

    /**
     * Activates a discount in term of spent Resources
     * @param p is the {@link Player}
     */
    @Override
    public void applyOn(Player p) {
        addDiscount(p);
    }

    /**
     * Special Getter
     * @return is Effect's Discount attribute
     */
    @Override
    public Object returnAttribute() {
        return getDiscount();
    }

    public Discount getDiscount(){
        return discount;
    }

    private void addDiscount(Player p) {
        discount.activate();
        p.giveDiscount(discount);
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Discount on buy of " + discount.toString();
    }

    @Override
    public String print() {
        return discount.getDiscountResource().print() + " - 1  ";
    }
}
