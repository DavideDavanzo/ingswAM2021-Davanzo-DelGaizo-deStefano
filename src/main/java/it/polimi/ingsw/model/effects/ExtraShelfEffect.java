package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerboard.Shelf;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.resources.Resource;

/**
 * Gives a {@link Player} an extra shelf that is originally
 * exclusive for one specific type of {@link Resource}
 */
public class ExtraShelfEffect extends Effect {
    private Shelf extraShelf;
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
     * Creates and adds an extra shelf to the Player's {@link Warehouse}
     * @param p
     */
    private void addExtraShelf(Player p) {
        extraShelf = new Shelf(2); // The Shelf is created only if the Effect is activated
        extraShelf.setShelfResource(shelfResource); // Shelf main resource is set here
    
    }
}