package it.polimi.ingsw.observingPattern;


import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.network.messages.Message;

public interface Observer {

    void update(Message message);

    void update(Warehouse warehouse);

    void update(Path path);

    void update(Coffer coffer);

    void update(DevelopmentCardsArea developmentCardsArea);

    void update(int pathPosition);

}
