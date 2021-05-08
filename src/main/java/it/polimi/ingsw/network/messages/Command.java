package it.polimi.ingsw.network.messages;

public abstract class Command extends Message {

    public Command(){
        super();
    }

    public Command(String msg){
        super(msg);
    }

}
