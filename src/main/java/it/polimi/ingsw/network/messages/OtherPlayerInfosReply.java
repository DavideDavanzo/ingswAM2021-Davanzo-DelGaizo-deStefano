package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.playerboard.Coffer;
import it.polimi.ingsw.model.playerboard.DevelopmentCardsArea;
import it.polimi.ingsw.model.playerboard.Warehouse;
import it.polimi.ingsw.model.playerboard.path.Path;
import it.polimi.ingsw.network.client.ClientModel;
import it.polimi.ingsw.view.View;

import java.util.LinkedList;

public class OtherPlayerInfosReply extends Message{

    @JsonSerialize(as = Warehouse.class)
    private Warehouse warehouse;
    @JsonSerialize(as = Coffer.class)
    private Coffer coffer;
    @JsonSerialize(as = Path.class)
    private Path path;
    @JsonSerialize(as = DevelopmentCardsArea.class)
    private DevelopmentCardsArea developmentCardsArea;
    private LinkedList<LeaderCard> leaderCards;

    public OtherPlayerInfosReply(Warehouse warehouse, Coffer coffer, Path path, DevelopmentCardsArea developmentCardsArea, LinkedList<LeaderCard> leaderCards) {
        this.warehouse = warehouse;
        this.coffer = coffer;
        this.path = path;
        this.developmentCardsArea = developmentCardsArea;
        this.leaderCards = leaderCards;
    }

    public OtherPlayerInfosReply(){

    }

    @Override
    public void apply(View view) {
        view.setOtherPlayerClientModel(new ClientModel(leaderCards, warehouse, coffer, path, developmentCardsArea));
        view.showOtherPlayerClientModel();
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setCoffer(Coffer coffer) {
        this.coffer = coffer;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setDevelopmentCardsArea(DevelopmentCardsArea developmentCardsArea) {
        this.developmentCardsArea = developmentCardsArea;
    }

    public void setLeaderCards(LinkedList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Coffer getCoffer() {
        return coffer;
    }

    public Path getPath() {
        return path;
    }

    public DevelopmentCardsArea getDevelopmentCardsArea() {
        return developmentCardsArea;
    }

    public LinkedList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
