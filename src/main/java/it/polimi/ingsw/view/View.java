package it.polimi.ingsw.view;


import it.polimi.ingsw.network.observing_pattern.Observable;
import it.polimi.ingsw.network.observing_pattern.Observer;

public abstract class View extends Observable implements Observer {

    public abstract void start();

    public abstract void askNumberOfPlayers();

    public abstract void askQuery(String msg);

}
