package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.Resource;

/**
 * Gives a {@link Player} an extra shelf that is originally
 * exclusive for one specific type of {@link Resource}
 */
public class ExtraShelfEffect extends Effect {
    private Resource shelfResource;

    /**
     * Default Constructor
     */
    public ExtraShelfEffect() {}

    /**
     * Main Constructor
     * @param resource is the type of Resource linked to the Shelf
     */
    public ExtraShelfEffect(Resource resource){
        this.shelfResource = resource; // The Effect is linked at creation to a specific Resource
    }

    /**
     * Applies the Effect on the Player
     * Calls {@link #addExtraShelf(Player) addExtraShelf} method
     * @param p is the {@link Player}
     */
    @Override
    public void applyOn(Player p) {
        addExtraShelf(p);
    }

    /**
     * Special Getter
     * @return is Effect's Resource attribute
     */
    @Override
    public Object getAttribute() {
        return shelfResource;
    }

    public void setShelfResource(Resource shelfResource) {
        this.shelfResource = shelfResource;
    }

    /**
     * Creates and adds an extra shelf to the Player's {@link Warehouse}
     * The Shelf is empty and has a specific type of {@link Resource} that can be stored inside
     * @param p is the Player that activates the Effect
     */
    private void addExtraShelf(Player p) {
        Shelf extraShelf = new Shelf(2); // The Shelf is created only if the Effect is activated
        extraShelf.setShelfResource(shelfResource); // Shelf main resource is set here
        p.getPlayerBoard().getWarehouse().addExtraShelf(extraShelf);
    }

    @Override
    public String toString() {
        return "Extra Shelf that can hold " + shelfResource.toString();
    }

    @Override
    public String print() {
        return shelfResource.print() + " " + shelfResource.print();
    }
}
