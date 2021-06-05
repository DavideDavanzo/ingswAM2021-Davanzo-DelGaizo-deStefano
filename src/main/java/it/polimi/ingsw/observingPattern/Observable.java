package it.polimi.ingsw.observingPattern;

import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Observable {

    protected final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(Message message){
        for(Observer observer : observers){
            observer.update(message);
        }
    }

    public void notifyObservers(Warehouse warehouse){
        for(Observer observer : observers){
            observer.update(warehouse);
        }
    }

    public void notifyObservers(Coffer coffer){
        for(Observer observer : observers){
            observer.update(coffer);
        }
    }

    public void notifyObservers(Path path){
        for(Observer observer : observers){
            observer.update(path);
        }
    }

    public void notifyObservers(DevelopmentCardsArea developmentCardsArea){
        for(Observer observer : observers){
            observer.update(developmentCardsArea);
        }
    }

    public void notifyObservers(int pathPosition){
        for(Observer observer : observers){
            observer.update(pathPosition);
        }
    }

    public void notifyObservers(Set<String> usernames){
        for(Observer observer : observers){
            observer.update(usernames);
        }
    }

}
