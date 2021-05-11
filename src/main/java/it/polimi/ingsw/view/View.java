package it.polimi.ingsw.view;


import it.polimi.ingsw.network.observingPattern.Observable;
import it.polimi.ingsw.network.observingPattern.Observer;

public abstract class View extends Observable implements Observer {

    public abstract void start();

    public abstract void askNumberOfPlayers();

    public abstract void askUsername();

    public abstract void askQuery(String msg);

    public abstract void showMessage(String msg);

}
