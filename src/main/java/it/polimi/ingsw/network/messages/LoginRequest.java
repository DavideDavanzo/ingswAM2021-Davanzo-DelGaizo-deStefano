package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.view.View;

public class LoginRequest extends Message {

    public LoginRequest(){}

    public LoginRequest(String msg){
        super(msg);
    }

    @Override
    public void apply(View view) {

    }


}
