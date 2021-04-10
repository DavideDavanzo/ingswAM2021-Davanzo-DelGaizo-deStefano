package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStub;
import it.polimi.ingsw.model.resources.Item;

/**
 *Abstract class which represents Marble
 */
public abstract class Marble {

  public abstract Item returnItem(Player p);

}