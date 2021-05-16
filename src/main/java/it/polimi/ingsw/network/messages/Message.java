package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.gameState.GameState;
import it.polimi.ingsw.exceptions.controllerExceptions.InvalidStateException;
import it.polimi.ingsw.view.View;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InfoMessage.class, name = "InfoMessage"),
        @JsonSubTypes.Type(value = QueryMessage.class, name = "QueryMessage"),
        @JsonSubTypes.Type(value = LoginRequest.class, name = "LoginRequest"),
        @JsonSubTypes.Type(value = LoginReply.class, name = "LoginReply"),
        @JsonSubTypes.Type(value = ReplyMessage.class, name = "ReplyMessage"),
        @JsonSubTypes.Type(value = PlayersNumRequest.class, name = "PlayersNumRequest"),
        @JsonSubTypes.Type(value = PlayersNumber.class, name = "PlayersNumber"),
        @JsonSubTypes.Type(value = LeaderRequest.class, name = "LeaderRequest"),
        @JsonSubTypes.Type(value = ErrorMessage.class, name = "ErrorMessage"),
        @JsonSubTypes.Type(value = ResourceRequest.class, name = "ResourceRequest"),
        @JsonSubTypes.Type(value = ResourceChoice.class, name = "ResourceChoice")
})
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
