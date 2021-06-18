package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;

public class Launcher {
    public static void main(String[] args) {
        if(args.length == 1) {
            Server.main(args);
        }
        else Client.main(args);
    }
}
