package it.polimi.ingsw.observingPattern;


import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.model.sharedarea.CardMarket;
import it.polimi.ingsw.model.sharedarea.market.Market;
import it.polimi.ingsw.network.messages.Message;

import java.util.Set;

/**
 * Observer Interface. An observer receives an update called on it by
 * the Observed object.
 */
public interface Observer {

    void update(Message message);

    void update(Warehouse warehouse);

    void update(Path path);

    void update(Coffer coffer);

    void update(DevelopmentCardsArea developmentCardsArea);

    void update(Market market);

    void update(CardMarket cardMarket);

    void update(int pathPosition);

    void update(Set<String> usernames);

}
