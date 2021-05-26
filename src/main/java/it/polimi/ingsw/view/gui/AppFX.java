package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.view.gui.scene.MenuSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class AppFX extends Application {

    SocketHandler socketHandler;

    public AppFX(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
        Application.launch(AppFX.class);
    }

    @Override
    public void start(Stage primaryStage) {
        GuiView guiView = new GuiView(socketHandler);
        //client controller and view observer

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/menu_scene.xml"));

        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        MenuSceneController controller = loader.getController();


    }
}

