package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.market.Marble;
import it.polimi.ingsw.model.resources.Item;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class StockMarketResourcesRequest extends Message {

    @JsonSerialize(as = ArrayList.class)
    private ArrayList<Item> resources;

    @JsonSerialize(as = int.class)
    private int numExtraShelves;

    public StockMarketResourcesRequest(){
        super();
    }

    public StockMarketResourcesRequest(String msg){
        super(msg);
    }

    public StockMarketResourcesRequest(ArrayList<Item> resources, int numExtraShelves){
        this.resources = resources;
        this.numExtraShelves = numExtraShelves;
    }

    @Override
    public void apply(View view) {
        view.askToStockMarketResources(resources, numExtraShelves);
    }

    @Override
    public void getProcessedBy(GameState gameState) throws InvalidStateException {

    }

    public ArrayList<Item> getResources() {
        return resources;
    }

    public int getNumExtraShelves() {
        return numExtraShelves;
    }

    public void setResources(ArrayList<Item> resources) {
        this.resources = resources;
    }

    public void setNumExtraShelves(int numExtraShelves) {
        this.numExtraShelves = numExtraShelves;
    }

}
