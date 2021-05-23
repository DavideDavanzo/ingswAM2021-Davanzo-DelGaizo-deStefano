package it.polimi.ingsw.observingPattern;


import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.network.messages.Message;

public interface Observer {

    void update(Message message);

    void update(Warehouse warehouse);

}
