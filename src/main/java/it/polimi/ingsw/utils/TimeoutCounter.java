package it.polimi.ingsw.utils;

import it.polimi.ingsw.network.messages.TimeoutMessage;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.observingPattern.Observer;

import java.util.TimerTask;

public class TimeoutCounter extends TimerTask {

    private final int timeLimit;

    private final Observable observableTimer;

    private static int i = 0;

    public TimeoutCounter(int timeLimit){
        this.timeLimit = timeLimit;
        this.observableTimer = new Observable();
    }

    @Override
    public void run() {
        if (check(++i)) {
            observableTimer.notifyObservers(new TimeoutMessage());
            this.cancel();
        }
    }

    public boolean check(int i){
        if(i>timeLimit){
            return true;
        }
        return false;
    }

    public Observable getObservableTimer(){
        return observableTimer;
    }

    public void addObserver(Observer observer){
        observableTimer.addObserver(observer);
    }

}
