package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerboard.Shelf;

public class ExtraShelfEffect extends Effect {
    private Shelf extraShelf;

    /**
     * Adds an extra shelf to the Player's warehouse
     * @param p is the {@link Player}
     */
    @Override
    public void applyOn(Player p) {
        addExtraShelf(p);
    }

    private void addExtraShelf(Player p) {
        extraShelf = new Shelf(2);
        // AGGIUNGERE ASSEGNAZIONE DELLO SCAFFALE AL PLAYER
        // METODO DA TESTARE AGGIUNTO IL PLAYER
    }
}
