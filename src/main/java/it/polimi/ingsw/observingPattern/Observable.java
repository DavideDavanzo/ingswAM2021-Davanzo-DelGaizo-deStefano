package it.polimi.ingsw.observingPattern;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(Object object){
        for(Observer observer : observers){
            observer.update(object);
        }
    }

}
