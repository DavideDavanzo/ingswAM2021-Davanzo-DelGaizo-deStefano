package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.View;

public class Error extends Message {

    public Error(){
        super();
    }

    public Error(String msg){
        super(msg);
    }

    @Override
    public void apply(View view) {

    }

}
