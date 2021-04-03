package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class DiscountEffect extends Effect {

    private Discount discount;

    /**
     * Adctivates a discount in term of spent Resources
     * @param p is the {@link Player}
     */
    @Override
    public void applyOn(Player p) {
        addDiscount(p);
    }

    private void addDiscount(Player p) {
        //AGGIUNGERE DISCOUNT AL PLAYER
        //METODO DA TESTARE AGGIUNTO IL PLAYER
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
