package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.Player;

public abstract class Requirement {

    public abstract boolean validateOn(Player p);

    public abstract Object getAttribute();

}
