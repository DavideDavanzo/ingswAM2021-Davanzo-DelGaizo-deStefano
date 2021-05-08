package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.View;

public class ReplyMessage extends Message{

    public ReplyMessage(){
        super();
    }

    public ReplyMessage(String msg){
        super(msg);
    }

    @Override
    public void apply(View view) {
    }

}
