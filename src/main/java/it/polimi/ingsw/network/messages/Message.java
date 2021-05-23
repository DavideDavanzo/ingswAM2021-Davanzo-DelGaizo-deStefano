package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InfoMessage.class, name = "InfoMessage"),
        @JsonSubTypes.Type(value = PingMessage.class, name = "PingMessage"),
        @JsonSubTypes.Type(value = LoginRequest.class, name = "LoginRequest"),
        @JsonSubTypes.Type(value = LoginReply.class, name = "LoginReply"),
        @JsonSubTypes.Type(value = LeaderRequest.class, name = "LeaderRequest"),
        @JsonSubTypes.Type(value = ErrorMessage.class, name = "ErrorMessage"),
        @JsonSubTypes.Type(value = ArrangeInWarehouseCmd.class, name = "ArrangeInWarehouseCmd"),
        @JsonSubTypes.Type(value = MarketInfoRequest.class, name = "MarketInfoRequest"),
        @JsonSubTypes.Type(value = CardsMarketInfoRequest.class, name = "CardsMarketInfoRequest"),
        @JsonSubTypes.Type(value = BuyCardCmd.class, name = "BuyCardCmd"),
        @JsonSubTypes.Type(value = ChangeWhiteMarbleReply.class, name = "ChangeWhiteMarbleReply"),
        @JsonSubTypes.Type(value = ChangeWhiteMarbleRequest.class, name = "ChangeWhiteMarbleRequest"),
        @JsonSubTypes.Type(value = MarketResourcesCmd.class, name = "MarketResourcesCmd"),
        @JsonSubTypes.Type(value = PassTurnMessage.class, name = "PassTurnMessage"),
        @JsonSubTypes.Type(value = PlayersNumRequest.class, name = "PlayersNumRequest"),
        @JsonSubTypes.Type(value = PlayersNumber.class, name = "PlayersNumber"),
        @JsonSubTypes.Type(value = ResourceChoice.class, name = "ResourceChoice"),
        @JsonSubTypes.Type(value = ResourceRequest.class, name = "ResourceRequest"),
        @JsonSubTypes.Type(value = StockMarketResourcesRequest.class, name = "StockMarketResourcesRequest"),
        @JsonSubTypes.Type(value = TurnWakeMessage.class, name = "TurnWakeMessage"),
        @JsonSubTypes.Type(value = ActivateLeaderCmd.class, name = "ActivateLeaderCmd"),
        @JsonSubTypes.Type(value = WarehouseUpdate.class, name = "WarehouseUpdate"),
        @JsonSubTypes.Type(value = Ack.class, name = "Ack")
})
//TODO: declare each message @JasonSubTypes.Type
public abstract class Message implements Serializable {

    protected String msg;

    protected String username;

    public Message(){ }

    public Message(String msg){
        this.msg = msg;
    }

    public abstract void apply(View view);

    public abstract void getProcessedBy(GameState gameState) throws InvalidStateException;

    public String getMsg(){
        return msg;
    }

    public String getUsername(){
        return username;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUsername(String username){
        this.username = username;
    }

}
