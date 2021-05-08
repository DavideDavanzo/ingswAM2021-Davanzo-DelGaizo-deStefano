package it.polimi.ingsw.network.observing_pattern;


import it.polimi.ingsw.network.messages.Message;

public interface Observer {

    void update(Message message);

}
