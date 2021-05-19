package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class StockMarketResourcesReq extends Message{

    private ArrayList<Resource> resources;
    private int numExtraShelves;

    public StockMarketResourcesReq(){
        super();
    }

    public StockMarketResourcesReq(String msg){
        super(msg);
    }

    public StockMarketResourcesReq(ArrayList<Resource> resources, int numExtraShelves){
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
}
