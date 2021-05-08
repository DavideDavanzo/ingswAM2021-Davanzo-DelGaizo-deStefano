package it.polimi.ingsw.network.messages;


import it.polimi.ingsw.view.View;

public class InfoMessage extends Message {

    public InfoMessage(){
        super();
    }

    public InfoMessage(String msg){
        super(msg);
    }

    @Override
    public void apply(View view) {

    }

}
