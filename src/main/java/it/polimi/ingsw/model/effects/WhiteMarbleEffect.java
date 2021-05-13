package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.CliPrinter;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.ECardColor;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;

/**
 * Gives the {@link Player} an extra colored {@link Marble} that
 * will take the place of the White one in the {@link Market}
 * during his turn
 */
public class WhiteMarbleEffect extends Effect {
    private Marble marble;

    /**
     * Default Constructor
     */
    public WhiteMarbleEffect() {}

    /**
     * Main Constructor
     * @param marble is the colored marble
     */
    public WhiteMarbleEffect(Marble marble){
        this.marble = marble;
    }

    /**
     * Applies the Effect on the Player
     * Calls {@link #addExtraMarble(Player) addExtraMarble} method
     * @param p is the {@link Player}
     */
    @Override
    public void applyOn(Player p) {
        addExtraMarble(p);
    }

    /**
     * Special Getter
     * @return is Effect's Marble attribute
     */
    @Override
    public Object getAttribute() {
        return marble;
    }

    public void setMarble(Marble marble) {
        this.marble = marble;
    }

    /**
     * Gives the Player an extra marble that can be
     * accessed via {@link Player#getExtraMarbles()}
     * @param p refers to the player
     */
    private void addExtraMarble(Player p) {
        p.giveWhiteMarblePower(marble);
    }

    @Override
    public String toString() {
        return "White Marble becomes " + marble.toString();
    }

    @Override
    public String print() {
        return Color.ANSI_WHITE.escape() + "●" + " \u2192 " + marble.returnItem().print();
    }
}
