package it.polimi.ingsw.network.observingPattern;


import it.polimi.ingsw.network.messages.Message;

public interface Observer {

    void update(Message message);

}
